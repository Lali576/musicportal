package hu.elte.wr14yr.musicportal.controller;

import hu.elte.wr14yr.musicportal.annotation.Role;
import static hu.elte.wr14yr.musicportal.model.User.Role.ARTIST;
import static hu.elte.wr14yr.musicportal.model.User.Role.GUEST;
import static hu.elte.wr14yr.musicportal.model.User.Role.USER;

import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/song")
public class SongController {

    @Autowired
    private SongService songService;

    @Role({ARTIST})
    @GetMapping
    public ResponseEntity<Iterable<Song>> list(User user) {
        Iterable<Song> songs = songService.list(user);
        return ResponseEntity.ok(songs);
    }

    @Role({ARTIST})
    @PostMapping("/new")
    public ResponseEntity<Song> create(@RequestBody Song song,
                                       @RequestBody User user,
                                       @RequestBody Album album,
                                       @RequestBody Set<Genre> genres,
                                       @RequestBody Set<Keyword> keywords) {
        Song savedSong = songService.create(song, user, album, genres, keywords);

        return ResponseEntity.ok(savedSong);
    }

    @Role({ARTIST})
    @PutMapping("/edit/{id}")
    public ResponseEntity<Song> update(@PathVariable long id, @RequestBody Song song) {
        Song updatedSong = songService.update(song);
        return ResponseEntity.ok(updatedSong);
    }

    @Role({ARTIST, USER, GUEST})
    @GetMapping("/{id}")
    public ResponseEntity<Song> find(@PathVariable long id) {
        Song foundSong  = songService.find(id);
        return ResponseEntity.ok(foundSong);
    }

    @Role({ARTIST})
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id, @RequestBody Song song) {
        songService.delete(song);

        return ResponseEntity.ok().build();
    }
}
