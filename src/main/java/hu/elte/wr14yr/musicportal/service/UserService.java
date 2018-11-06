package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.exception.UserNotValidException;
import hu.elte.wr14yr.musicportal.gda.GoogleDriveApi;
import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.model.tags.UserTag;
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
    private TagService tagService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    public FileService fileService;

    private Logger logger = Logger.getLogger(UserService.class.getName());

    private User loggedInUser = null;

    public User register(User user, String password, File iconFile, List<UserTag> userTags) {
        logger.info("User service: new user is going to be saved in database MusicPortal");

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

        logger.info("User service: password has been salted and hashed successfully");

        loggedInUser = userRepository.save(user);
        logger.info("User service: new user has been successfully saved in database MusicPortal");

        if(userTags != null) {
            tagService.createUserTags(userTags, loggedInUser);
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
        logger.info("User service: new user has been updated with folder and file id's");

        return loggedInUser;
    }

    public User login(String username, String password) throws UserNotValidException {
        logger.info(String.format("User service: trying to login with username %s", username));

        Optional<User> loginUser = userRepository.findByUsername(username);
        if(loginUser.isPresent()) {
            logger.info(String.format("User service: User with username %s was found", username));

            logger.info("User service: trying to check password correction");

            if(isValid(loginUser.get(), password)) {
                loggedInUser = loginUser.get();

                logger.info(String.format("User service: user with username %s" +
                        " is valid, logging in was successful", loggedInUser.getUsername()));

                return loggedInUser;
            }
        }

        logger.warning(String.format("User service: User with username was not found! Logging in was unsuccessful", username));

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
        logger.info("User service: checking: is user logged in at the current session");

        return loggedInUser != null;
    }

    public User getLoggedInUser() {
        logger.info(String.format("User service: getting current logged in user named %s", loggedInUser.getUsername()));

        return loggedInUser;
    }

    public User getUser(Long id) {
        logger.info(String.format("User service: user with id %s is going to be searched", id));

        return userRepository.findUserById(id);
    }

    public User updateDetails(Country country, Genre favGenre, List<UserTag> userTags) {
        logger.info(String.format("User service: user named %s" +
                "'s  data are going to be updated", loggedInUser.getUsername()));

        loggedInUser.setCountryId(country);
        loggedInUser.setFavGenreId(favGenre);
        loggedInUser = userRepository.save(loggedInUser);

        logger.info(String.format("User service: user named %s" +
                "'s data has been updated successfully", loggedInUser.getUsername()));

        tagService.deleteAllUserTagsByUser(loggedInUser);

        if(userTags != null) {
            tagService.createUserTags(userTags, loggedInUser);
        }

        return loggedInUser;
    }

    public User updatePassword(String oldPassword, String newPassword) {
        logger.info(String.format("User service: user named %s" +
                "'s password is going to be changed", loggedInUser.getUsername()));

        logger.info("User service: checking old password validation");

        if(isValid(loggedInUser, oldPassword)) {
            String newPassSalt = newPassword + loggedInUser.getSaltCode();
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(newPassSalt.getBytes(StandardCharsets.UTF_8));
                String hashPassword = Base64.getEncoder().encodeToString(hash);
                loggedInUser.setHashPassword(hashPassword);
                loggedInUser = userRepository.save(loggedInUser);

                logger.info(String.format("User service: user named %s" +
                        "'s password has been changed successfully", loggedInUser.getUsername()));
            } catch(NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } else {
            logger.severe(String.format("User service: user named %s" +
                    "'s old password is invalid!", loggedInUser.getUsername()));
        }

        return loggedInUser;
    }

    public User changeEmailAddress(String newEmailAddress) {
        logger.info(String.format("User service: user named %s" +
                "'s email address is going to be changed", loggedInUser.getUsername()));

        if(!(newEmailAddress.equals(loggedInUser.getEmailAddress()))) {
            loggedInUser.setEmailAddress(newEmailAddress);
            loggedInUser = userRepository.save(loggedInUser);

            logger.info(String.format("User service: user named %s" +
                    "'s e-mail address has been changed successfully", loggedInUser.getUsername()));
        } else {
            logger.log(Level.SEVERE, "User service: user named " +
                    loggedInUser.getUsername() + "'s old e-mail address is equal to the new one!");
        }


        return loggedInUser;
    }

    public User changeImageFile(File iconFile) {
        logger.info(String.format("User service: user named %s" +
                "'s icon image file is going to be changed", loggedInUser.getUsername()));

        if(loggedInUser.getIconFileGdaId().equals("")) {
            if(iconFile.exists()) {
                String iconFileGdaId = fileService.uploadFile(iconFile, loggedInUser.getUserIconFolderGdaId());
                loggedInUser.setIconFileGdaId(iconFileGdaId);
            }
        } else {
            if(iconFile.exists()) {
                fileService.updateFile(loggedInUser.getIconFileGdaId(), iconFile);
            } else {
                fileService.delete(loggedInUser.getIconFileGdaId());
                loggedInUser.setIconFileGdaId("");
            }
        }

        loggedInUser = userRepository.save(loggedInUser);

        logger.info(String.format("User service: user named %s" +
                "'s icon image file has been changed successfully", loggedInUser.getUsername()));

        return loggedInUser;
    }

    public User updateBiography(String biography) {
        logger.info(String.format("User service: user named %s" +
                "'s biography is going to be updated", loggedInUser.getUsername()));

        loggedInUser.setEmailAddress(biography);
        loggedInUser = userRepository.save(loggedInUser);

        logger.info(String.format("User service: user named %s" +
                "'s biography has been updated successfully", loggedInUser.getUsername()));

        return loggedInUser;
    }

    public void logout() {
        logger.info(String.format("User service: logout current user named %s", loggedInUser.getUsername()));

        loggedInUser = null;

        logger.info("User service: previous login user was logged out");
    }

    public void delete(long id) {
        logger.info(String.format("User service: user named %s is going to be deleted", loggedInUser.getUsername()));

        albumService.deleteAllByUser(loggedInUser);

        playlistService.deleteAllByUser(loggedInUser);

        userMessageRepository.deleteAllByUserFrom(loggedInUser);

        tagService.deleteAllUserTagsByUser(loggedInUser);

        logger.info("User service: user's storing folder is going to delete from Google Drive");

        fileService.delete(loggedInUser.getUserFolderGdaId());

        logger.info("User service: user's storing folder has been successfully deleted from Google Drive");

        logger.info(String.format("User service: user with id number %s" +
                " is going to delete from database MusicPortal", id));

        userRepository.deleteById(id);

        logger.info(String.format("User service: user with id number %s" +
                " has been successfully deleted from database MusicPortal", id));


        loggedInUser = null;
    }

    public Iterable<UserMessage> createUserMessage(UserMessage userMessage) {
        logger.info(String.format("User service: new user message has started to be saved(from %s to %s )",
                userMessage.getUserFrom().getUsername(),
                userMessage.getUserTo().getUsername()));

        userMessage.setDate(new Date());
        UserMessage savedUserMessage = userMessageRepository.save(userMessage);

        logger.info("User service: new user message has been saved successfully");

        return userMessageRepository.findAllByUserTo(savedUserMessage.getUserTo());
    }

    public Iterable<UserMessage> listUserMessages(User user) {
        logger.info(String.format("User service: user messages for user named %s" +
                " are going to be listed", user.getUsername()));

        return userMessageRepository.findAllByUserTo(user);
    }
}