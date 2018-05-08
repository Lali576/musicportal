package hu.elte.wr14yr.musicportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.elte.wr14yr.musicportal.annotation.Role;
import static hu.elte.wr14yr.musicportal.model.User.Role.ARTIST;
import static hu.elte.wr14yr.musicportal.model.User.Role.GUEST;
import static hu.elte.wr14yr.musicportal.model.User.Role.USER;

import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/song")
public class SongController {

    @Autowired
    private SongService songService;

    //@Role({ARTIST})
    @GetMapping
    public ResponseEntity<Iterable<Song>> list(User user) {
        Iterable<Song> songs = songService.list(user);
        return ResponseEntity.ok(songs);
    }

    //@Role({ARTIST, USER})
    @GetMapping("/comments")
    public ResponseEntity<Iterable<SongComment>> listSongComments(@RequestBody Song song) {
        Iterable<SongComment> songComments = songService.listSongComments(song);
        return ResponseEntity.ok(songComments);
    }

    //@Role({ARTIST, USER})
    @PostMapping("/comments/new")
    public ResponseEntity<Iterable<SongComment>> createSongComment(SongComment songComment) {
        Iterable<SongComment> songComments = songService.createSongComment(songComment);
        return ResponseEntity.ok(songComments);
    }

    /*
        SongLike, SongCounter
     */

    //@Role({ARTIST})
    @PostMapping("/new")
    public ResponseEntity<Song> create(@RequestBody Map<String, Object> params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Song song = mapper.readValue(params.get("song").toString(), Song.class);
        User user = mapper.readValue(params.get("user").toString(), User.class);
        Album album = mapper.readValue(params.get("album").toString(), Album.class);
        List<Genre> genres = mapper.readValue(params.get("genres").toString(), List.class);
        List<Keyword> keywords = mapper.readValue(params.get("keywords").toString(), List.class);
        Song savedSong = songService.create(song, user, album, genres, keywords);

        return ResponseEntity.ok(savedSong);
    }

    //@Role({ARTIST})
    @PutMapping("/edit/{id}")
    public ResponseEntity<Song> update(@PathVariable long id, @RequestBody Song song) {
        Song updatedSong = songService.update(song);
        return ResponseEntity.ok(updatedSong);
    }

    //@Role({ARTIST, USER, GUEST})
    @GetMapping("/{id}")
    public ResponseEntity<Song> find(@PathVariable long id) {
        Song foundSong  = songService.find(id);
        return ResponseEntity.ok(foundSong);
    }

    //@Role({ARTIST})
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id, @RequestBody Song song) {
        songService.delete(song);

        return ResponseEntity.ok().build();
    }
}
