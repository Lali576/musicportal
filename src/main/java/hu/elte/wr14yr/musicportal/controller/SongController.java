package hu.elte.wr14yr.musicportal.controller;

import hu.elte.wr14yr.musicportal.annotation.Role;
import static hu.elte.wr14yr.musicportal.model.User.Role.ARTIST;
import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/song")
public class SongController {

    @Autowired
    private SongService songService;

    @Role({ARTIST})
    @GetMapping
    public ResponseEntity<Iterable<Song>> list() {
        return ResponseEntity.ok(null);
    }

    @Role({ARTIST})
    @PostMapping("/new")
    public ResponseEntity<Song> create(@RequestBody Song song) {
        return ResponseEntity.ok().build();
    }

    @Role({ARTIST})
    @PutMapping("/edit/{id}")
    public ResponseEntity<Song> update(@PathVariable long id) {
        Song updatedSong = null;
        return ResponseEntity.ok(updatedSong);
    }

    @Role({ARTIST})
    @GetMapping("/{id}")
    public ResponseEntity<Song> find(@PathVariable long id) {
        Song foundSong  = null;
        return ResponseEntity.ok(foundSong);
    }

    @Role({ARTIST})
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        return ResponseEntity.ok().build();
    }
}
