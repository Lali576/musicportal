package hu.elte.wr14yr.musicportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.elte.wr14yr.musicportal.annotation.Role;
import static hu.elte.wr14yr.musicportal.model.User.Role.ARTIST;
import static hu.elte.wr14yr.musicportal.model.User.Role.USER;

import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.model.UserMessage;
import hu.elte.wr14yr.musicportal.service.UserNotValidException;
import hu.elte.wr14yr.musicportal.service.UserService;
import jdk.nashorn.internal.objects.NativeJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody Map<String, Object> params) throws IOException, URISyntaxException {
        String password = params.get("password").toString();
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(params.get("user").toString(), User.class);
        User savedUser = userService.register(user, password);
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
}
