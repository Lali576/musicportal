package hu.elte.wr14yr.musicportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.elte.wr14yr.musicportal.model.*;

import hu.elte.wr14yr.musicportal.service.AlbumService;
import hu.elte.wr14yr.musicportal.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private SongService songService;

    //@Role({ARTIST})
    @GetMapping
    public ResponseEntity<Iterable<Album>> list(@RequestBody User user) {
        Iterable<Album> albums = albumService.list(user);
        return ResponseEntity.ok(albums);
    }

    //@Role({ARTIST})
    @PostMapping("/new")
    public ResponseEntity<Album> create(@RequestBody Map<String, Object> params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Album album = mapper.readValue(params.get("album").toString(), Album.class);
        User user = mapper.readValue(params.get("user").toString(), User.class);
        List<Song> songs = mapper.readValue(params.get("songs").toString(), List.class);
        List<Genre> genres = mapper.readValue(params.get("genres").toString(), List.class);
        List<Keyword> keywords = mapper.readValue(params.get("keywords").toString(), List.class);
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
