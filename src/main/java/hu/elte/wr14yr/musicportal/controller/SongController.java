package hu.elte.wr14yr.musicportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.elte.wr14yr.musicportal.annotation.Role;
import static hu.elte.wr14yr.musicportal.model.User.Role.ARTIST;
import static hu.elte.wr14yr.musicportal.model.User.Role.GUEST;
import static hu.elte.wr14yr.musicportal.model.User.Role.USER;

import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.service.SongService;
import hu.elte.wr14yr.musicportal.service.UserService;
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

    @Autowired
    private UserService userService;

    @Role({ARTIST})
    @GetMapping
    public ResponseEntity<Iterable<Song>> list() {
        Iterable<Song> songs = songService.list(userService.getLoggedInUser());
        return ResponseEntity.ok(songs);
    }

    @PostMapping("/albumby")
    public ResponseEntity<Iterable<Song>> songsByAlbum(@RequestBody Album album) {
        Iterable<Song> songs = songService.listByAlbum(album);
        return ResponseEntity.ok(songs);
    }

    @PostMapping("/playlistby")
    public ResponseEntity<Iterable<Song>> songsByPlaylist(@RequestBody Playlist playlist) {
        Iterable<Song> songs = songService.listByPlaylist(playlist);
        return ResponseEntity.ok(songs);
    }

    @Role({ARTIST, USER})
    @PostMapping("/comments")
    public ResponseEntity<Iterable<SongComment>> listSongComments(@RequestBody Song song) {
        Iterable<SongComment> songComments = songService.listSongComments(song);
        return ResponseEntity.ok(songComments);
    }

    @Role({ARTIST, USER})
    @PostMapping("/comments/new")
    public ResponseEntity<Iterable<SongComment>> createSongComment(@RequestBody SongComment songComment) {
        Iterable<SongComment> songComments = songService.createSongComment(songComment);
        return ResponseEntity.ok(songComments);
    }

    /*
        SongLike, SongCounter
     */

    @Role({ARTIST})
    @PostMapping("/new")
    public ResponseEntity<Song> create(@RequestBody Map<String, Object> params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Song song = mapper.readValue(params.get("song").toString(), Song.class);
        User user = userService.getLoggedInUser();
        Album album = mapper.readValue(params.get("album").toString(), Album.class);
        //List<Genre> genres = mapper.readValue(params.get("genres").toString(), List.class);
        //List<Keyword> keywords = mapper.readValue(params.get("keywords").toString(), List.class);
        Song savedSong = songService.create(song, user, album, null, null);

        return ResponseEntity.ok(savedSong);
    }

    @Role({ARTIST})
    @PutMapping("/edit/{id}")
    public ResponseEntity<Song> update(@PathVariable long id, @RequestBody Map<String, Object> params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Song song = mapper.readValue(params.get("song").toString(), Song.class);
        User user = userService.getLoggedInUser();
        Album album = mapper.readValue(params.get("album").toString(), Album.class);
        Song updatedSong = songService.update(song,album,user,null,null);
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
