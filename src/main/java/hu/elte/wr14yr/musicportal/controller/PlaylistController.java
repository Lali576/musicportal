package hu.elte.wr14yr.musicportal.controller;

import hu.elte.wr14yr.musicportal.annotation.Role;
import hu.elte.wr14yr.musicportal.model.Playlist;
import static hu.elte.wr14yr.musicportal.model.User.Role.USER;
import static hu.elte.wr14yr.musicportal.model.User.Role.ARTIST;
import hu.elte.wr14yr.musicportal.service.PlaylistService;
import hu.elte.wr14yr.musicportal.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private SongService songService;

    @Role({USER, ARTIST})
    @GetMapping
    public ResponseEntity<Iterable<Playlist>> list() {
        Iterable<Playlist> playlists = null;
        return ResponseEntity.ok(playlists);
    }

    @Role({USER, ARTIST})
    @PostMapping("/new")
    public ResponseEntity<Playlist> create(@RequestBody Playlist playlist) {
        return ResponseEntity.ok(null);
    }

    @Role({USER, ARTIST})
    @GetMapping("/{id}")
    public ResponseEntity<Playlist> find(@PathVariable long id) {
        Playlist foundPlaylist = null;
        return ResponseEntity.ok(foundPlaylist);
    }

    @Role({USER, ARTIST})
    @PutMapping("/edit/{id}")
    public ResponseEntity<Playlist> update(@PathVariable long id) {
        Playlist updatedPlaylist = null;
        return ResponseEntity.ok(updatedPlaylist);
    }

    @Role({USER, ARTIST})
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        return ResponseEntity.ok().build();
    }
}
