package hu.elte.wr14yr.musicportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.elte.wr14yr.musicportal.annotation.Role;
import static hu.elte.wr14yr.musicportal.model.User.Role.ARTIST;
import static hu.elte.wr14yr.musicportal.model.User.Role.USER;

import hu.elte.wr14yr.musicportal.model.Genre;
import hu.elte.wr14yr.musicportal.model.Keyword;
import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.model.UserMessage;
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
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    public FileService fileService;

    @GetMapping("/login/get")
    public ResponseEntity<User> getLoginUser() {
        logger.log(Level.INFO, "Entrance: endpoint '/login/get'");
        User user = (userService.isLoggedIn()) ? userService.getLoggedInUser() : null;
        logger.log(Level.INFO, "Exit: endpoint '/login/get'");

        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint '/register'");
        MultipartFile multipartFile = null;

        Iterator<String> iterator = request.getFileNames();

        logger.log(Level.INFO, "Get file parameter");
        while (iterator.hasNext()) {
            multipartFile = request.getFile(iterator.next());
        }

        File file = fileService.convertToFile(multipartFile);

        ObjectMapper mapper = new ObjectMapper();

        logger.log(Level.INFO, "Get parameter 'user'");
        User user = mapper.readValue(request.getParameter("user").toString(), User.class);

        logger.log(Level.INFO, "Get parameter 'password'");
        String password = request.getParameter("password").toString();

        logger.log(Level.INFO, "Get parameter 'keywords'");
        Keyword[] keywordsArray = mapper.readValue(request.getParameter("keywords").toString(), Keyword[].class);
        List<Keyword> keywordsList = Arrays.asList(keywordsArray);

        User savedUser = userService.register(user, password, file, keywordsList);

        file.delete();
        logger.log(Level.INFO, "Exit: endpoint '/register'");

        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(MultipartHttpServletRequest request) {
        logger.log(Level.INFO, "Entrance: endpoint '/login'");
        try {
            logger.log(Level.INFO, "Get parameter 'username'");
            String username = request.getParameter("username").toString();
            logger.log(Level.INFO, "Get parameter 'password'");
            String password = request.getParameter("password").toString();
            logger.log(Level.INFO, "Exit: endpoint '/login'");

            return ResponseEntity.ok(userService.login(username, password));
        } catch (UserNotValidException e) {
            logger.log(Level.SEVERE, "Required user was not found!");

            return ResponseEntity.badRequest().build();
        }
    }

    @Role({ARTIST, USER})
    @GetMapping("/messages")
    public ResponseEntity<Iterable<UserMessage>> listUserMessages() {
        logger.log(Level.INFO, "Entrance: endpoint 'messages'");
        Iterable<UserMessage> userMessages = userService.listUserMessages(userService.getLoggedInUser());
        logger.log(Level.INFO, "Exit: endpoint 'messages'");

        return ResponseEntity.ok(userMessages);
    }

    @Role({ARTIST, USER})
    @PostMapping("/messages/new")
    public ResponseEntity<Iterable<UserMessage>> createUserMessage(MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint 'messages/new'");
        ObjectMapper mapper = new ObjectMapper();

        UserMessage userMessage = mapper.readValue(request.getParameter("userMessage").toString(), UserMessage.class);

        Iterable<UserMessage> userMessages = userService.createUserMessage(userMessage);
        logger.log(Level.INFO, "Exit: endpoint 'messages/new'");

        return ResponseEntity.ok(userMessages);
    }

    @Role({ARTIST, USER})
    @PutMapping("/update/{id}/details")
    public ResponseEntity<User> updateDetails(@PathVariable("id") long id, MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint '/update/" + id + "/details'");
        ObjectMapper mapper = new ObjectMapper();

        logger.log(Level.INFO, "Get parameter 'fullName'");
        String fullName = request.getParameter("fullName").toString();

        logger.log(Level.INFO, "Get parameter 'favGenre'");
        Genre favGenre = mapper.readValue(request.getParameter("favGenre").toString(), Genre.class);

        logger.log(Level.INFO, "Get parameter 'keywords'");
        Keyword[] keywordsArray = mapper.readValue(request.getParameter("keywords").toString(), Keyword[].class);
        List<Keyword> keywordList = Arrays.asList(keywordsArray);

        User updatedUser = userService.updateDetails(fullName, favGenre, keywordList);
        logger.log(Level.INFO, "Exit: endpoint '/update/" + id + "/details'");

        return ResponseEntity.ok(updatedUser);
    }

    @Role({ARTIST, USER})
    @PutMapping("update/{id}/email")
    public ResponseEntity<User> updateEmailAddress(@PathVariable("id") long id, MultipartHttpServletRequest request) {
        logger.log(Level.INFO, "Entrance: endpoint '/update/" + id + "/email'");
        String emailAddress = request.getParameter("emailAddress");
        User updatedUser = userService.updateEmailAddress(emailAddress);
        logger.log(Level.INFO, "Exit: endpoint '/update/" + id + "/email'");

        return ResponseEntity.ok(updatedUser);
    }

    @Role({ARTIST, USER})
    @PutMapping("/update/{id}/password")
    public ResponseEntity updatePassword(@PathVariable("id") long id, MultipartHttpServletRequest request) {
        logger.log(Level.INFO, "Entrance: endpoint '/update/" + id + "/password'");
        String password = request.getParameter("password");
        User updatedUser = userService.updatePassword(password);
        logger.log(Level.INFO, "Exit: endpoint '/update/" + id + "/password'");

        return ResponseEntity.ok(updatedUser);
    }

    @Role({ARTIST, USER})
    @PutMapping("/update/{id}/icon")
    public ResponseEntity<User> updateIconFile(@PathVariable("id") long id, MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint '/update/" + id + "/icon'");
        MultipartFile multipartFile = null;

        Iterator<String> iterator = request.getFileNames();

        logger.log(Level.INFO, "Get file parameter");
        while (iterator.hasNext()) {
            multipartFile = request.getFile(iterator.next());
        }

        File file = fileService.convertToFile(multipartFile);

        User updatedUser = userService.updateImageFile(file);

        file.delete();
        logger.log(Level.INFO, "Exit: endpoint '/update/" + id + "/icon'");

        return ResponseEntity.ok(updatedUser);
    }

    @Role({ARTIST, USER})
    @PutMapping("/update/{id}/biography")
    public ResponseEntity<User> updateBiography(@PathVariable("id") long id, MultipartHttpServletRequest request) {
        logger.log(Level.INFO, "Entrance: endpoint '/update/" + id + "/biography'");
        String biography = request.getParameter("biography");
        User updatedUser = userService.updateBiography(biography);
        logger.log(Level.INFO, "Exit: endpoint '/update/" + id + "/biography'");

        return ResponseEntity.ok(updatedUser);
    }

    @Role({ARTIST, USER})
    @GetMapping("/{id}")
    public ResponseEntity<User> find(@PathVariable long id) {
        logger.log(Level.INFO, "Entrance: endpoint '/" + id + "'");
        User foundUser = userService.getLoggedInUser();
        logger.log(Level.INFO, "Exit: endpoint '/" + id + "'");
        return ResponseEntity.ok(foundUser);
    }

    @Role({ARTIST, USER})
    @PostMapping("/logout")
    public ResponseEntity logout() {
        logger.log(Level.INFO, "Entrance: endpoint '/logout'");
        userService.logout();
        logger.log(Level.INFO, "Exit: endpoint '/logout'");

        return ResponseEntity.status(204).build();
    }

    @Role({ARTIST, USER})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable long id) throws IOException, URISyntaxException {
        logger.log(Level.INFO, "Entrance: endpoint '/delete/" + id + "'");
        userService.delete(id);
        logger.log(Level.INFO, "Exit: endpoint '/delete" + id + "'");

        return ResponseEntity.status(204).build();
    }
}
