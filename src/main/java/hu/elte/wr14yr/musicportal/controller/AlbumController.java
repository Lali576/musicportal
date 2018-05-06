package hu.elte.wr14yr.musicportal.controller;

import hu.elte.wr14yr.musicportal.annotation.Role;
import hu.elte.wr14yr.musicportal.model.*;

import static hu.elte.wr14yr.musicportal.model.User.Role.ARTIST;
import static hu.elte.wr14yr.musicportal.model.User.Role.GUEST;
import static hu.elte.wr14yr.musicportal.model.User.Role.USER;

import hu.elte.wr14yr.musicportal.service.AlbumService;
import hu.elte.wr14yr.musicportal.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.Set;

@RestController
@RequestMapping("/api/album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private SongService songService;

    //@Role({ARTIST})
    @GetMapping
    public ResponseEntity<Iterable<Album>> list(User user) {
        Iterable<Album> albums = albumService.list(user);
        return ResponseEntity.ok(albums);
    }

    //@Role({ARTIST})
    @PostMapping("/new")
    public ResponseEntity<Album> create(@RequestBody Album album,
                                        @RequestBody User user,
                                        @RequestBody Set<Song> songs,
                                        @RequestBody Set<Genre> genres,
                                        @RequestBody Set<Keyword> keywords) {
        Album savedAlbum = albumService.create(album, user, songs, genres, keywords);

        return ResponseEntity.ok(savedAlbum);
    }

    //@Role({ARTIST})
    @PutMapping("/edit/{id}")
    public ResponseEntity<Album> update(@PathVariable long id, @RequestBody Album album) {
        Album updatedAlbum = albumService.update(album);
        return ResponseEntity.ok(updatedAlbum);
    }

    //@Role({ARTIST, USER, GUEST})
    @GetMapping("/{id}")
    public ResponseEntity<Album> find(@PathVariable long id) {
        Album foundAlbum = albumService.find(id);
        return ResponseEntity.ok(foundAlbum);
    }

    //@Role({ARTIST})
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id, @RequestBody Album album) {
        albumService.delete(album);

        return ResponseEntity.ok().build();
    }
}
