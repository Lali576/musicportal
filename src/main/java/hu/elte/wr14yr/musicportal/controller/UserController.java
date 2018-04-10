package hu.elte.wr14yr.musicportal.controller;

import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.service.AlbumService;
import hu.elte.wr14yr.musicportal.service.SongService;
import hu.elte.wr14yr.musicportal.service.UserMessageService;
import hu.elte.wr14yr.musicportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMessageService userMessageService;

    private AlbumService albumService;

    @Autowired
    private SongService songService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return null;
    }

    @PostMapping("/login")
    public User login(@PathVariable User user) {
        return null;
    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable long id) {

    }

    @GetMapping("/{id}")
    public User find(@PathVariable long id) {
        return null;
    }

    @PostMapping("/logout")
    public void logout(@RequestBody User user) {

    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id) {

    }
}
