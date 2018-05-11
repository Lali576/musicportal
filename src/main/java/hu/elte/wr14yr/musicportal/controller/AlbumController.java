package hu.elte.wr14yr.musicportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.elte.wr14yr.musicportal.annotation.Role;
import static hu.elte.wr14yr.musicportal.model.User.Role.*;
import hu.elte.wr14yr.musicportal.model.*;

import hu.elte.wr14yr.musicportal.service.AlbumService;
import hu.elte.wr14yr.musicportal.service.SongService;
import hu.elte.wr14yr.musicportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private UserService userService;

    @Role({ARTIST})
    @GetMapping
    public ResponseEntity<Iterable<Album>> list() {
        Iterable<Album> albums = albumService.list(userService.getLoggedInUser());
        return ResponseEntity.ok(albums);
    }

    @Role({ARTIST})
    @PostMapping("/new")
    public ResponseEntity<Album> create(@RequestBody Map<String, Object> params) throws IOException, URISyntaxException {
        ObjectMapper mapper = new ObjectMapper();
        Album album = mapper.readValue(params.get("album").toString(), Album.class);
        User user = userService.getLoggedInUser();
        //List<Genre> genres = mapper.readValue(params.get("genres").toString(), List.class);
        //List<Keyword> keywords = mapper.readValue(params.get("keywords").toString(), List.class);
        Album savedAlbum = albumService.create(album, user, null, null);

        return ResponseEntity.ok(savedAlbum);
    }

    @Role({ARTIST})
    @PutMapping("/edit/{id}")
    public ResponseEntity<Album> update(@PathVariable long id, @RequestBody Album album) throws IOException, URISyntaxException {
        album.setUser(userService.getLoggedInUser());
        Album updatedAlbum = albumService.update(album);
        return ResponseEntity.ok(updatedAlbum);
    }

    @Role({ARTIST, USER, GUEST})
    @GetMapping("/{id}")
    public ResponseEntity<Album> find(@PathVariable long id) {
        Album foundAlbum = albumService.find(id);
        return ResponseEntity.ok(foundAlbum);
    }

    @Role({ARTIST})
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) throws URISyntaxException, IOException {
        Album album = albumService.find(id);
        albumService.delete(album);

        return ResponseEntity.ok().build();
    }
}
