package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.exception.UserNotValidException;
import hu.elte.wr14yr.musicportal.gda.GoogleDriveApi;
import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.model.keywords.UserKeyword;
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
    private KeywordService keywordService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    public FileService fileService;

    private Logger logger = Logger.getLogger(UserService.class.getName());

    private User loggedInUser = null;

    public User register(User user, String password, File iconFile, List<UserKeyword> userKeywords) {
        logger.log(Level.INFO, "User service: new user is going to be saved in database MusicPortal");
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
        logger.log(Level.INFO, "User service: password has been salted and hashed successfully");

        loggedInUser = userRepository.save(user);
        logger.log(Level.INFO, "User service: new user has been successfully saved in database MusicPortal");

        if(userKeywords != null) {
            keywordService.createUserKeywords(userKeywords, loggedInUser);
        }

        String userFolderGdaId = fileService.uploadFolder(loggedInUser.getUsername(), GoogleDriveApi.MAIN_FOLDER_ID);
        loggedInUser.setUserFolderGdaId(userFolderGdaId);

        String userAlbumsFolderGdaId = fileService.uploadFolder("albums", userFolderGdaId);
        loggedInUser.setUserAlbumsFolderGdaId(userAlbumsFolderGdaId);

        String userIconFolderGdaId = fileService.uploadFolder("icon", userFolderGdaId);
        loggedInUser.setUserIconFolderGdaId(userIconFolderGdaId);

        if(iconFile != null) {
            String iconFileGdaId = fileService.uploadFile(iconFile, userIconFolderGdaId);
            loggedInUser.setIconFileGdaId(iconFileGdaId);
        }

        loggedInUser = userRepository.save(loggedInUser);
        logger.log(Level.INFO, "User service: new user has been updated with folder and file id's");

        return loggedInUser;
    }

    public User login(String username, String password) throws UserNotValidException {
        logger.log(Level.INFO, "User service: trying to login with username " + username);
        Optional<User> loginUser = userRepository.findByUsername(username);
        if(loginUser.isPresent()) {
            logger.log(Level.INFO, "User service: User with username " + username + " was found");
            logger.log(Level.INFO, "User service: trying to check password correction");
            if(isValid(loginUser.get(), password)) {
                loggedInUser = loginUser.get();
                logger.log(Level.INFO, "User service: user with username " +
                        loggedInUser.getUsername() + " is valid, logging in was successful");

                return loggedInUser;
            }
        }

        logger.log(Level.WARNING, "User service: User with username " +
                username + " was not found! Logging in was unsuccessful");
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
        logger.log(Level.INFO, "User service: checking: is user logged in at the current session");
        return loggedInUser != null;
    }

    public User getLoggedInUser() {
        logger.log(Level.INFO, "User service: getting current logged in user named " + loggedInUser.getUsername());
        return loggedInUser;
    }

    public User updateDetails(Country country, Genre favGenre, List<UserKeyword> userKeywords) {
        logger.log(Level.INFO, "User service: user named " +
                loggedInUser.getUsername() + "'s  data are going to be updated");

        loggedInUser.setCountryId(country);
        loggedInUser.setFavGenreId(favGenre);
        loggedInUser = userRepository.save(loggedInUser);
        logger.log(Level.INFO, "User service: user named " +
                loggedInUser.getUsername() + "'s data has been updated successfully");

        keywordService.deleteAllUserKeywordsByUser(loggedInUser);

        if(userKeywords != null) {
            keywordService.createUserKeywords(userKeywords, loggedInUser);
        }

        return loggedInUser;
    }

    public User updatePassword(String oldPassword, String newPassword) {
        logger.log(Level.INFO, "User service: user named " +
                loggedInUser.getUsername() + "'s password is going to be changed");

        logger.log(Level.INFO, "User service: checking old password validation");

        if(isValid(loggedInUser, oldPassword)) {
            String newPassSalt = newPassword + loggedInUser.getSaltCode();

            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(newPassSalt.getBytes(StandardCharsets.UTF_8));
                String hashPassword = Base64.getEncoder().encodeToString(hash);
                loggedInUser.setHashPassword(hashPassword);
                loggedInUser = userRepository.save(loggedInUser);
                logger.log(Level.INFO, "User service: user named " +
                        loggedInUser.getUsername() + "'s password has been changed successfully");
            } catch(NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } else {
            logger.log(Level.SEVERE, "User service: Error! Old password in not equal!");
        }

        return loggedInUser;
    }

    public User changeEmailAddress(String emailAddress) {
        logger.log(Level.INFO, "User service: user named " +
                loggedInUser.getUsername() + "'s email address is going to be changed");
        loggedInUser.setEmailAddress(emailAddress);
        loggedInUser = userRepository.save(loggedInUser);
        logger.log(Level.INFO, "User service: user named " +
                loggedInUser.getUsername() + "'s email address has been changed successfully");

        return loggedInUser;
    }

    public User changeImageFile(File iconFile) {
        logger.log(Level.INFO, "User service: user named " +
                loggedInUser.getUsername() + "'s icon image file is going to be changed");
        String iconFileGdaId = "";
        if(iconFile != null) {
            iconFileGdaId = fileService.updateFile(loggedInUser.getIconFileGdaId(), iconFile);
            loggedInUser.setIconFileGdaId(iconFileGdaId);
        } else {
            fileService.delete(loggedInUser.getIconFileGdaId());
            loggedInUser.setIconFileGdaId(null);
        }
        loggedInUser = userRepository.save(loggedInUser);
        logger.log(Level.INFO, "User service: user named " +
                loggedInUser.getUsername() + "'s icon image file has been changed successfully");

        return loggedInUser;
    }

    public User updateBiography(String biography) {
        logger.log(Level.INFO, "User service: user named " +
                loggedInUser.getUsername() + "'s biography is going to be updated");
        loggedInUser.setEmailAddress(biography);
        loggedInUser = userRepository.save(loggedInUser);
        logger.log(Level.INFO, "User service: user named " +
                loggedInUser.getUsername() + "'s biography has been updated successfully");

        return loggedInUser;
    }

    public void logout() {
        logger.log(Level.INFO, "User service: logout current user named " + loggedInUser.getUsername());
        loggedInUser = null;
        logger.log(Level.INFO, "User service: previous login user was logged out");
    }

    public void delete(long id) {
        logger.log(Level.INFO, "User service: user named " +
                loggedInUser.getUsername() + " is going to be deleted");

        albumService.deleteAllByUser(loggedInUser);

        playlistService.deleteAllByUser(loggedInUser);

        userMessageRepository.deleteAllByUserFrom(loggedInUser);

        keywordService.deleteAllUserKeywordsByUser(loggedInUser);

        logger.log(Level.INFO, "User service: user's storing folder is going to delete from Google Drive", loggedInUser.getUserFolderGdaId());
        fileService.delete(loggedInUser.getUserFolderGdaId());
        logger.log(Level.INFO, "User service: user's storing folder has been successfully deleted from Google Drive");

        logger.log(Level.INFO, "User service: user with id number " + id + " is going to delete from database MusicPortal");
        userRepository.deleteById(id);
        logger.log(Level.INFO, "User service: user with id number " + id + " has been successfully deleted from database MusicPortal");

        loggedInUser = null;
    }

    public Iterable<UserMessage> createUserMessage(UserMessage userMessage) {
        logger.log(Level.INFO, "User service: new user message has started to be saved" +
                "\n(from " + userMessage.getUserFrom().getUsername() + " to " + userMessage.getUserTo().getUsername() + ")");
        userMessage.setDate(new Date());
        UserMessage savedUserMessage = userMessageRepository.save(userMessage);
        logger.log(Level.INFO, "User service: new user message has been saved successfully");

        return userMessageRepository.findAllByUserTo(savedUserMessage.getUserTo());
    }

    public Iterable<UserMessage> listUserMessages(User user) {
        logger.log(Level.INFO, "User service: user messages for user named " + user.getUsername() + " are going to be listed");
        return userMessageRepository.findAllByUserTo(user);
    }
}
