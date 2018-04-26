package hu.elte.wr14yr.musicportal.controller;

import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.service.AlbumService;
import hu.elte.wr14yr.musicportal.service.SongService;
import hu.elte.wr14yr.musicportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private SongService songService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user, @RequestBody String password) {
        User savedUser = userService.register(user);

        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody String username, @RequestBody String password) {
        try {
            return ResponseEntity.ok(userService.login(username, password));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> update(@PathVariable long id, User user) {
        User updatedUser = userService.update(user);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> find(@PathVariable long id) {
        User foundUser = userService.getLoggedInUser();
        return ResponseEntity.ok(foundUser);
    }

    @PostMapping("/logout")
    public ResponseEntity logout() {
        userService.logout();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable long id, User user) {
        userService.delete(id, user);
        return ResponseEntity.ok().build();
    }
}
