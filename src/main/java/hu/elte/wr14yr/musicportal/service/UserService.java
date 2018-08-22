package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.exception.UserNotValidException;
import hu.elte.wr14yr.musicportal.gda.GoogleDriveApi;
import hu.elte.wr14yr.musicportal.model.Album;
import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.model.UserMessage;
import hu.elte.wr14yr.musicportal.repository.UserMessageRepository;
import hu.elte.wr14yr.musicportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    @Autowired
    public FileService fileService;

    private Logger logger = Logger.getLogger(UserService.class.getName());

    private User user = null;

    public User register(User user, String password, File file) {

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

        this.user = userRepository.save(user);

        String userFolderGdaId = fileService.uploadFolder(this.user.getUsername(), GoogleDriveApi.MAIN_FOLDER_ID);

        String iconFileGdaId = fileService.uploadFile(file, userFolderGdaId);

        userRepository.updateFolderGdaId(this.user.getId(), userFolderGdaId);
        userRepository.updateFileGdaId(this.user.getId(), iconFileGdaId);

        this.user.setUserFolderGdaId(userFolderGdaId);
        this.user.setIconFileGdaId(iconFileGdaId);

        return this.user;
    }

    public Iterable<UserMessage> createUserMessage(UserMessage userMessage) {
        userMessage.setDate(new Date());
        UserMessage savedUserMessage = userMessageRepository.save(userMessage);
        return userMessageRepository.findAllByUserTo(savedUserMessage.getUserTo());
    }

    public Iterable<UserMessage> listUserMessages(User user) {
        return userMessageRepository.findAllByUserTo(user);
    }

    public User login(String username, String password) throws UserNotValidException {
        logger.log(Level.INFO, "Trying to login with username " + username);
        Optional<User> loginUser = userRepository.findByUsername(username);
        if(loginUser.isPresent()) {
            logger.log(Level.INFO, "User with username " + username + " was found");
            logger.log(Level.INFO, "Trying to check password correction");
            if(isValid(loginUser.get(), password)) {
                user = loginUser.get();
                logger.log(Level.INFO, "User with username " + user.getUsername() + " is valid, logging in was successful");
                return user;
            }
        }
        logger.log(Level.WARNING, "User with username " + username + " was not found! Logging in was unsuccessful");
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

    public boolean isLoggedIn() {
        logger.log(Level.INFO, "Checking: is user logged in at the current session");
        return user != null;
    }

    public User getLoggedInUser() {
        logger.log(Level.INFO, "Getting current logged in user named " + user.getUsername());
        return user;
    }

    public User update(User user, File file) {
        logger.log(Level.INFO, "User named " + user.getUsername() + "'s  datas are going to update in database MusicPortal");

        this.user = userRepository.save(user);

        fileService.delete(user.getIconFileGdaId());

        String iconFileGdaId = fileService.uploadFile(file, user.getUserFolderGdaId());

        userRepository.updateFileGdaId(this.user.getId(), iconFileGdaId);

        this.user.setIconFileGdaId(iconFileGdaId);

        return this.user;
    }

    public void logout() {
        logger.log(Level.INFO, "Logout current user named " + user.getUsername());
        user = null;
        logger.log(Level.INFO, "Previous login user was logged out");
    }

    public void delete(long id) throws IOException, URISyntaxException {

        albumService.deleteAllByUser(user);

        playlistService.deleteAllByUser(user);


        for(UserMessage userMessage : user.getUserFromMessages()) {
            userMessageRepository.deleteAllByUserFrom(user);
        }

        //Delete keywords

        isLoggedIn();

        logger.log(Level.INFO, "User's icon picture file is going to delete from Google Drive", user.getIconFileGdaId());
        fileService.delete(user.getIconFileGdaId());
        logger.log(Level.INFO, "User's icon picture file was successfully deleted from Google Drive");

        logger.log(Level.INFO, "User's storing folder is going to delete from Google Drive", user.getUserFolderGdaId());
        fileService.delete(user.getUserFolderGdaId());
        logger.log(Level.INFO, "User's storing folder was successfully deleted from Google Drive");

        logger.log(Level.INFO, "User with id number " + id + " is going to delete from database MusicPortal");
        userRepository.deleteById(id);
        logger.log(Level.INFO, "User with id number " + id + " was successfully deleted from database MusicPortal");

        user = null;
    }
}
