package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.model.Album;
import hu.elte.wr14yr.musicportal.model.Playlist;
import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.model.UserMessage;
import hu.elte.wr14yr.musicportal.repository.UserMessageRepository;
import hu.elte.wr14yr.musicportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.Set;

@Service
@SessionScope
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMessageRepository userMessageRepository;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private PlaylistService playlistService;

    private User user;

    public User register(User user, String password) {
        user.setRole(User.Role.USER);

        SecureRandom rand = new SecureRandom();
        byte[] salt = new byte[64];
        rand.nextBytes(salt);
        String saltCode = new String(Base64.getEncoder().encode(salt));

        String passSalt = password + saltCode;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(passSalt.getBytes(StandardCharsets.UTF_8));
            String hashPassword = Base64.getEncoder().encodeToString(hash);

            user.setSaltCode(saltCode);
            user.setHashPassword(hashPassword);

        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        new File("..\\media\\" + user.getUsername() + "\\icon").mkdir();
        File file = new File("..\\media\\" + user.getUsername() + "\\icon\\" + user.getIconFile().getName());

        user.setIconPath(file.getPath());

        return this.user = userRepository.save(user);
    }

    public Iterable<UserMessage> createUserMessage(UserMessage userMessage) {
        userMessage.setDateTime(LocalDateTime.now());
        UserMessage savedUserMessage = userMessageRepository.save(userMessage);
        return userMessageRepository.findAllByUserTo(savedUserMessage.getUserTo());
    }

    public Iterable<UserMessage> listUserMessages(User user) {
        return userMessageRepository.findAllByUserTo(user);
    }

    public User login(String username, String password) throws UserNotValidException {
        Optional<User> loginUser = userRepository.findByUsername(username);
         if(loginUser.isPresent()) {
             if(isValid(loginUser.get(), password)) {
                 user = loginUser.get();
                 return user;
             }
         }
         throw new UserNotValidException();
    }

    private boolean isValid(User user, String password) {
        String passSalt = password + user.getSaltCode();

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(passSalt.getBytes(StandardCharsets.UTF_8));
            String hashPassword = Base64.getEncoder().encodeToString(hash);

            return hashPassword.equals(user.getHashPassword());
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isLoggenIn() {
        return user != null;
    }

    public User getLoggedInUser() {
        return user;
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void logout() {
        user = null;
    }

    public void delete(long id, User user) {
        for(Album album : user.getAlbums()) {
            albumService.delete(album);
        }

        playlistService.deleteAllByUser(user);

        for(UserMessage userMessage : user.getUserFromMessages()) {
            userMessageRepository.deleteAllByUserFrom(user);
        }

        userRepository.deleteById(id);
    }
}
