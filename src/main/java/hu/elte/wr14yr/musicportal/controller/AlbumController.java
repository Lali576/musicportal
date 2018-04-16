package hu.elte.wr14yr.musicportal.controller;

import hu.elte.wr14yr.musicportal.annotation.Role;
import hu.elte.wr14yr.musicportal.model.Album;
import static hu.elte.wr14yr.musicportal.model.User.Role.ARTIST;
import hu.elte.wr14yr.musicportal.service.AlbumService;
import hu.elte.wr14yr.musicportal.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private SongService songService;

    @Role({ARTIST})
    @GetMapping
    public ResponseEntity<Iterable<Album>> list() {
        Iterable<Album> albums = null;
        return ResponseEntity.ok(albums);
    }

    @Role({ARTIST})
    @PostMapping("/new")
    public ResponseEntity<Album> create(@RequestBody Album album) {
        return null;
    }

    @Role({ARTIST})
    @PutMapping("/edit/{id}")
    public ResponseEntity<Album> update(@PathVariable long id, @RequestBody Album album) {
        Album updatedAlbum = null;
        return ResponseEntity.ok(updatedAlbum);
    }

    @Role({ARTIST})
    @GetMapping("/{id}")
    public ResponseEntity<Album> find(@PathVariable long id) {
        Album foundAlbum = null;
        return ResponseEntity.ok(foundAlbum);
    }

    @Role({ARTIST})
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        return ResponseEntity.ok().build();
    }
}
