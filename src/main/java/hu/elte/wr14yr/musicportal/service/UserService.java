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
import java.util.Base64;

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

    public User register(User user, String password, String tempIconPath) {
        user.setRole(User.Role.USER);

        SecureRandom rand = new SecureRandom();
        byte[] salt = new byte[24];
        rand.nextBytes(salt);
        String saltCode = new String(Base64.getDecoder().decode(salt));

        String passSalt = password + saltCode;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(passSalt.getBytes(StandardCharsets.UTF_8));
            String hashPassword = new String(Base64.getDecoder().decode(hash));

            user.setSaltCode(saltCode);
            user.setHashPassword(hashPassword);

        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        new File("..\\media\\" + user.getUsername() + "\\icon").mkdir();
        new File("..\\media\\" + user.getUsername() + "\\icon\\" + tempIconPath);

        return this.user = userRepository.save(user);
    }

    public User login(String username, String password) throws Exception {
         User user = userRepository.findByUsername(username);
         user.setTempPassword(password);
         if(isValid(user)) {
             this.user = user;
             return this.user;
         }
         throw new Exception();
    }

    private boolean isValid(User user) {
        String passSalt = user.getTempPassword() + user.getSaltCode();

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(passSalt.getBytes(StandardCharsets.UTF_8));
            String hashPassword = new String(Base64.getDecoder().decode(hash));

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
