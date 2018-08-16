package hu.elte.wr14yr.musicportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.elte.wr14yr.musicportal.annotation.Role;
import static hu.elte.wr14yr.musicportal.model.User.Role.ARTIST;
import static hu.elte.wr14yr.musicportal.model.User.Role.USER;

import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.model.UserMessage;
import hu.elte.wr14yr.musicportal.service.FileService;
import hu.elte.wr14yr.musicportal.service.UserNotValidException;
import hu.elte.wr14yr.musicportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private MultipartFile multipartFile = null;
    private String userIconFilePath = null;
    private final String assetFolderPath = "C:\\MusicPortal\\src\\main\\frontend\\src\\assets";
    //private Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    public FileService fileService;

    @GetMapping("/get")
    public ResponseEntity<User> getLoginUser() {
        User user = userService.getLoggedInUser();
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(MultipartHttpServletRequest request) throws IOException, URISyntaxException {
        MultipartFile multipartFile = null;

        Iterator<String> iterator = request.getFileNames();

        while (iterator.hasNext()) {
            multipartFile = request.getFile(iterator.next());
        }

        ObjectMapper mapper = new ObjectMapper();

        String password = request.getParameter("password").toString();

        User user = mapper.readValue(request.getParameter("user").toString(), User.class);

        File file = convertToFile(multipartFile);

        User savedUser = userService.register(user, password, file);

        file.delete();

        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String, Object> params) {
        try {
            String username = params.get("username").toString();
            String password = params.get("password").toString();
            return ResponseEntity.ok(userService.login(username, password));
        } catch (UserNotValidException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /*
    @Role({ARTIST, USER})
    @GetMapping("/messages")
    public ResponseEntity<Iterable<UserMessage>> listUserMessages() {
        Iterable<UserMessage> userMessages = userService.listUserMessages(userService.getLoggedInUser());
        return ResponseEntity.ok(userMessages);
    }

    @Role({ARTIST, USER})
    @PostMapping("/messages/new")
    public ResponseEntity<Iterable<UserMessage>> createUserMessage(@RequestBody UserMessage userMessage) {
        Iterable<UserMessage> userMessages = userService.createUserMessage(userMessage);
        return ResponseEntity.ok(userMessages);
    }
    */

    @Role({ARTIST, USER})
    @PutMapping("/update/{id}")
    public ResponseEntity<User> update(@PathVariable long id, User user) throws IOException, URISyntaxException {
        User updatedUser = userService.update(user);
        return ResponseEntity.ok(updatedUser);
    }

    @Role({ARTIST, USER})
    @GetMapping("/{id}")
    public ResponseEntity<User> find(@PathVariable long id) {
        User foundUser = userService.getLoggedInUser();
        return ResponseEntity.ok(foundUser);
    }

    @Role({ARTIST, USER})
    @PostMapping("/logout")
    public ResponseEntity logout() {
        userService.logout();
        return ResponseEntity.status(204).build();
    }

    @Role({ARTIST, USER})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable long id, User user) throws IOException, URISyntaxException {
        userService.delete(id, user);
        return ResponseEntity.ok().build();
    }

    private File convertToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
