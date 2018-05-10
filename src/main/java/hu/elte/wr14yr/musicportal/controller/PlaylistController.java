package hu.elte.wr14yr.musicportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.elte.wr14yr.musicportal.annotation.Role;
import hu.elte.wr14yr.musicportal.model.Keyword;
import hu.elte.wr14yr.musicportal.model.Playlist;

import static hu.elte.wr14yr.musicportal.model.User.Role.GUEST;
import static hu.elte.wr14yr.musicportal.model.User.Role.USER;
import static hu.elte.wr14yr.musicportal.model.User.Role.ARTIST;

import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.service.PlaylistService;
import hu.elte.wr14yr.musicportal.service.SongService;
import hu.elte.wr14yr.musicportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private UserService userService;

    @Autowired
    private SongService songService;

    @Role({USER, ARTIST})
    @GetMapping
    public ResponseEntity<Iterable<Playlist>> list() {
        Iterable<Playlist> playlists = playlistService.list(userService.getLoggedInUser());
        return ResponseEntity.ok(playlists);
    }

    @Role({USER, ARTIST})
    @PostMapping("/new")
    public ResponseEntity<Playlist> create(@RequestBody Map<String, Object> params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Playlist playlist = mapper.readValue(params.get("playlist").toString(), Playlist.class);
        User user = userService.getLoggedInUser();
        List<Song> songs = mapper.readValue(params.get("songs").toString(), List.class);
        //List<Keyword> keywords = mapper.readValue(params.get("keywords").toString(), List.class);
        Playlist savedPlaylist = playlistService.create(playlist, user, songs, null);

        return ResponseEntity.ok(savedPlaylist);
    }

    @Role({USER, ARTIST, GUEST})
    @GetMapping("/{id}")
    public ResponseEntity<Playlist> find(@PathVariable long id) {
        Playlist foundPlaylist = playlistService.find(id);
        return ResponseEntity.ok(foundPlaylist);
    }

    @Role({USER, ARTIST})
    @PutMapping("/edit/{id}")
    public ResponseEntity<Playlist> update(@PathVariable long id, @RequestBody Playlist playlist) {
        Playlist updatedPlaylist = playlistService.update(playlist);
        return ResponseEntity.ok(updatedPlaylist);
    }

    @Role({USER, ARTIST})
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        playlistService.delete(id);

        return ResponseEntity.ok().build();
    }
}
