package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.exception.UserNotValidException;
import hu.elte.wr14yr.musicportal.gda.GoogleDriveApi;
import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.repository.KeywordRepository;
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
import java.util.List;
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
    private KeywordRepository keywordRepository;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    public FileService fileService;

    private Logger logger = Logger.getLogger(UserService.class.getName());

    private User loggedInUser = null;

    public User register(User user, String password, File file, List<Keyword> keywords) {
        logger.log(Level.INFO, "Given password is going to being salted and hashed");
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
        logger.log(Level.INFO, "Password has been salted and hashed successfully");

        loggedInUser = userRepository.save(user);

        String userFolderGdaId = fileService.uploadFolder(loggedInUser.getUsername(), GoogleDriveApi.MAIN_FOLDER_ID);

        String iconFileGdaId = fileService.uploadFile(file, userFolderGdaId);

        loggedInUser.setUserFolderGdaId(userFolderGdaId);
        loggedInUser.setIconFileGdaId(iconFileGdaId);

        for(Keyword keyword : keywords) {
            keywordRepository.save(keyword);
        }

        loggedInUser.setKeywords(keywords);

        loggedInUser = userRepository.save(loggedInUser);

        return loggedInUser;
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
                loggedInUser = loginUser.get();
                logger.log(Level.INFO, "User with username " + loggedInUser.getUsername() + " is valid, logging in was successful");
                return loggedInUser;
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
        return loggedInUser != null;
    }

    public User getLoggedInUser() {
        logger.log(Level.INFO, "Getting current logged in user named " + loggedInUser.getUsername());
        return loggedInUser;
    }

    public User updateDetails(String fullName, Genre favGenre, List<Keyword> keywords) {
        logger.log(Level.INFO, "User named " + loggedInUser.getUsername() + "'s  data are going to update in database MusicPortal");

        loggedInUser.setFullName(fullName);
        loggedInUser.setFavGenreId(favGenre);
        loggedInUser.setKeywords(keywords);
        loggedInUser = userRepository.save(loggedInUser);

        return loggedInUser;
    }

    public User updatePassword(String password) {
        String passSalt = password + loggedInUser.getSaltCode();

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(passSalt.getBytes(StandardCharsets.UTF_8));
            String hashPassword = Base64.getEncoder().encodeToString(hash);
            loggedInUser.setHashPassword(hashPassword);
            loggedInUser = userRepository.save(loggedInUser);

            return loggedInUser;
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    public User updateEmailAddress(String emailAddress) {
        loggedInUser.setEmailAddress(emailAddress);
        loggedInUser = userRepository.save(loggedInUser);

        return loggedInUser;
    }

    public User updateImageFile(File iconFile) {
        String iconFileGdaId = fileService.updateFile(loggedInUser.getIconFileGdaId(), iconFile);
        loggedInUser.setIconFileGdaId(iconFileGdaId);
        loggedInUser = userRepository.save(loggedInUser);

        return loggedInUser;
    }

    public User updateBiography(String biography) {
        loggedInUser.setEmailAddress(biography);
        loggedInUser = userRepository.save(loggedInUser);

        return loggedInUser;
    }

    public void logout() {
        logger.log(Level.INFO, "Logout current user named " + loggedInUser.getUsername());
        loggedInUser = null;
        logger.log(Level.INFO, "Previous login user was logged out");
    }

    public void delete(long id) throws IOException, URISyntaxException {

        albumService.deleteAllByUser(loggedInUser);

        playlistService.deleteAllByUser(loggedInUser);

        userMessageRepository.deleteAllByUserFrom(loggedInUser);

        //Delete keywords

        isLoggedIn();

        logger.log(Level.INFO, "User's icon picture file is going to delete from Google Drive", loggedInUser.getIconFileGdaId());
        fileService.delete(loggedInUser.getIconFileGdaId());
        logger.log(Level.INFO, "User's icon picture file was successfully deleted from Google Drive");

        logger.log(Level.INFO, "User's storing folder is going to delete from Google Drive", loggedInUser.getUserFolderGdaId());
        fileService.delete(loggedInUser.getUserFolderGdaId());
        logger.log(Level.INFO, "User's storing folder was successfully deleted from Google Drive");

        logger.log(Level.INFO, "User with id number " + id + " is going to delete from database MusicPortal");
        userRepository.deleteById(id);
        logger.log(Level.INFO, "User with id number " + id + " was successfully deleted from database MusicPortal");

        loggedInUser = null;
    }
}
