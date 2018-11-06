package hu.elte.wr14yr.musicportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.elte.wr14yr.musicportal.annotation.Role;
import static hu.elte.wr14yr.musicportal.model.User.Role.ARTIST;
import static hu.elte.wr14yr.musicportal.model.User.Role.USER;

import hu.elte.wr14yr.musicportal.model.Country;
import hu.elte.wr14yr.musicportal.model.Genre;
import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.model.UserMessage;
import hu.elte.wr14yr.musicportal.model.tags.UserTag;
import hu.elte.wr14yr.musicportal.service.FileService;
import hu.elte.wr14yr.musicportal.exception.UserNotValidException;
import hu.elte.wr14yr.musicportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    public FileService fileService;

    private ObjectMapper mapper = new ObjectMapper();

    private Logger logger = Logger.getLogger(UserController.class.getName());

    @GetMapping("/login/get")
    public ResponseEntity<User> getLoginUser() {
        logger.info("User controller: enter endpoint '/login/get'");

        User user = (userService.isLoggedIn()) ? userService.getLoggedInUser() : null;

        logger.info("User controller: exit endpoint '/login/get'");


        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(MultipartHttpServletRequest request) throws IOException {
        logger.info("User controller: enter endpoint '/register'");

        MultipartFile multipartFile = null;
        File file = null;

        Iterator<String> iterator = request.getFileNames();

        logger.info("User controller: get file parameter");

        while (iterator.hasNext()) {
            multipartFile = request.getFile(iterator.next());
        }

        if(multipartFile != null) {
            file = fileService.convertToFile(multipartFile);
        }

        logger.info("User controller: get parameter 'user'");

        User user = mapper.readValue(request.getParameter("user"), User.class);

        logger.info("User controller: get parameter 'password'");

        String password = request.getParameter("password");

        logger.info("User controller: get parameter 'userKeywords'");

        UserTag[] userKeywordsArray = mapper.readValue(request.getParameter("userKeywords"), UserTag[].class);
        List<UserTag> userKeywordsList = Arrays.asList(userKeywordsArray);

        User savedUser = userService.register(user, password, file, userKeywordsList);

        if(file != null) {
            file.delete();
        }

        logger.info("User controller: exit endpoint '/register'");

        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(MultipartHttpServletRequest request) {
        logger.info("User controller: enter endpoint '/login'");

        try {
            logger.info("User controller: get parameter 'username'");

            String username = request.getParameter("username");

            logger.info("User controller: get parameter 'password'");

            String password = request.getParameter("password");

            logger.info("User controller: exit endpoint '/login'");

            return ResponseEntity.ok(userService.login(username, password));
        } catch (UserNotValidException e) {
            logger.severe("User controller: required user was not found");

            return ResponseEntity.badRequest().build();
        }
    }

    @Role({ARTIST, USER})
    @GetMapping("/logout")
    public ResponseEntity logout() {
        logger.info("User controller: enter endpoint '/logout'");

        userService.logout();

        logger.info("User controller: exit endpoint '/logout'");

        return ResponseEntity.status(204).build();
    }

    @Role({ARTIST, USER})
    @PutMapping("/update/{id}/details")
    public ResponseEntity<User> updateDetails(@PathVariable("id") long id, MultipartHttpServletRequest request) throws IOException {
        logger.info(String.format("User controller: enter endpoint '/update/%s/details'", id));

        logger.info("User controller: get parameter 'country'");

        Country country = mapper.readValue(request.getParameter("country"), Country.class);

        logger.info("User controller: get parameter 'favGenre'");

        Genre favGenre = mapper.readValue(request.getParameter("favGenre"), Genre.class);

        logger.info("User controller: get parameter 'userKeywords'");

        UserTag[] userKeywordsArray = mapper.readValue(request.getParameter("userKeywords"), UserTag[].class);
        List<UserTag> userKeywordsList = Arrays.asList(userKeywordsArray);

        User updatedUser = userService.updateDetails(country, favGenre, userKeywordsList);

        logger.info(String.format("User controller: exit endpoint '/update/%s/details'", id));

        return ResponseEntity.ok(updatedUser);
    }

    @Role({ARTIST, USER})
    @PutMapping("/update/{id}/email")
    public ResponseEntity<User> updateEmailAddress(@PathVariable("id") long id, MultipartHttpServletRequest request) {
        logger.info(String.format("User controller: enter endpoint '/update/%s/email'", id));

        String emailAddress = request.getParameter("emailAddress");
        User updatedUser = userService.changeEmailAddress(emailAddress);

        logger.info(String.format("User controller: exit endpoint '/update/%s/email'", id));

        return ResponseEntity.ok(updatedUser);
    }

    @Role({ARTIST, USER})
    @PutMapping("/update/{id}/password")
    public ResponseEntity<User> updatePassword(@PathVariable("id") long id, MultipartHttpServletRequest request) {
        logger.info(String.format("User controller: enter endpoint '/update/%s/password'", id));

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        User updatedUser = userService.updatePassword(oldPassword, newPassword);

        logger.info(String.format("User controller: exit endpoint '/update/%s/password'", id));

        return ResponseEntity.ok(updatedUser);
    }

    @Role({ARTIST, USER})
    @PostMapping("/update/{id}/icon")
    public ResponseEntity<User> updateIconFile(@PathVariable("id") long id, MultipartHttpServletRequest request) throws IOException {
        logger.info(String.format("User controller: enter endpoint '/update/%s/icon'", id));

        MultipartFile multipartFile = null;
        File file = null;

        Iterator<String> iterator = request.getFileNames();

        logger.info("User controller: get file parameter");

        while (iterator.hasNext()) {
            multipartFile = request.getFile(iterator.next());
        }

        if (multipartFile != null) {
            file = fileService.convertToFile(multipartFile);
        }

        User updatedUser = userService.changeImageFile(file);

        if(file != null) {
            file.delete();
        }

        logger.info(String.format("User controller: exit endpoint '/update/%s/icon'", id));

        return ResponseEntity.ok(updatedUser);
    }

    @Role({ARTIST, USER})
    @PutMapping("/update/{id}/biography")
    public ResponseEntity<User> updateBiography(@PathVariable("id") long id, MultipartHttpServletRequest request) {
        logger.info(String.format("User controller: enter endpoint '/update/%s/biography'", id));

        String biography = request.getParameter("biography");
        User updatedUser = userService.updateBiography(biography);

        logger.info(String.format("User controller: exit endpoint '/update/%s/biography'", id));

        return ResponseEntity.ok(updatedUser);
    }

    @Role({ARTIST, USER})
    @GetMapping("/{id}")
    public ResponseEntity<User> find(@PathVariable long id) {
        logger.info(String.format("User controller: enter endpoint '/%s'", id));

        User foundUser = userService.getUser(id);

        logger.info(String.format("User controller: exit endpoint '/%s'", id));

        return ResponseEntity.ok(foundUser);
    }

    @Role({ARTIST, USER})
    @DeleteMapping("/delete/{id}")
    public @ResponseBody void delete(@PathVariable long id) {
        logger.info(String.format("User controller: enter endpoint '/delete/%s'", id));

        userService.delete(id);

        logger.info(String.format("User controller: enter endpoint '/delete/%s'", id));
    }

    @Role({ARTIST, USER})
    @PostMapping("/messages/new")
    public ResponseEntity<Iterable<UserMessage>> createUserMessage(MultipartHttpServletRequest request) throws IOException {
        logger.info("User controller: enter endpoint '/messages/new'");

        logger.info("User controller: get parameter 'userMessage'");

        UserMessage userMessage = mapper.readValue(request.getParameter("userMessage"), UserMessage.class);

        Iterable<UserMessage> userMessages = userService.createUserMessage(userMessage);

        logger.info("User controller: enter endpoint '/messages/new'");

        return ResponseEntity.ok(userMessages);
    }

    @Role({ARTIST, USER})
    @GetMapping("/messages/{id}")
    public ResponseEntity<Iterable<UserMessage>> listUserMessages(@PathVariable("id") long id) {
        logger.info(String.format("User controller: enter endpoint '/messages/%s'", id));

        Iterable<UserMessage> userMessages = userService.listUserMessages(userService.getLoggedInUser());

        logger.info(String.format("User controller: exit endpoint '/messages/%s'", id));

        return ResponseEntity.ok(userMessages);
    }
}