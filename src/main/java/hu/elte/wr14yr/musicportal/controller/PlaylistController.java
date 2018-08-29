package hu.elte.wr14yr.musicportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.elte.wr14yr.musicportal.annotation.Role;
import hu.elte.wr14yr.musicportal.model.Playlist;

import static hu.elte.wr14yr.musicportal.model.User.Role.GUEST;
import static hu.elte.wr14yr.musicportal.model.User.Role.USER;
import static hu.elte.wr14yr.musicportal.model.User.Role.ARTIST;

import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.model.keywords.PlaylistKeyword;
import hu.elte.wr14yr.musicportal.service.PlaylistService;
import hu.elte.wr14yr.musicportal.service.SongService;
import hu.elte.wr14yr.musicportal.service.UserService;
import jdk.nashorn.internal.runtime.regexp.joni.ApplyCaseFoldArg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private UserService userService;

    @Autowired
    private SongService songService;

    private Logger logger = Logger.getLogger(PlaylistController.class.getName());

    @Role({USER, ARTIST})
    @PostMapping("/new")
    public ResponseEntity<Playlist> create(MultipartHttpServletRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Playlist playlist = mapper.readValue(request.getParameter("playlist").toString(), Playlist.class);
        User user = userService.getLoggedInUser();
        Song[] songsArray = mapper.readValue(request.getParameter("songs").toString(), Song[].class);
        List<Song> songsList = Arrays.asList(songsArray);
        PlaylistKeyword[] playlistKeywordsArray = mapper.readValue(request.getParameter("keywords").toString(), PlaylistKeyword[].class);
        List<PlaylistKeyword> playlistKeywordsList = Arrays.asList(playlistKeywordsArray);
        Playlist savedPlaylist = playlistService.create(playlist, user, songsList, playlistKeywordsList);

        return ResponseEntity.ok(savedPlaylist);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> find(@PathVariable long id) {
        Playlist foundPlaylist = playlistService.find(id);
        return ResponseEntity.ok(foundPlaylist);
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<Playlist>> listAll() {
        Iterable<Playlist> playlist = playlistService.listAll();
        return ResponseEntity.ok(playlist);
    }

    @GetMapping
    public ResponseEntity<Iterable<Playlist>> list() {
        Iterable<Playlist> playlist = playlistService.list(userService.getLoggedInUser());
        return ResponseEntity.ok(playlist);
    }

    @Role({USER, ARTIST})
    @PutMapping("/update/{id}")
    public ResponseEntity<Playlist> update(@PathVariable long id, MultipartHttpServletRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Playlist playlist = mapper.readValue(request.getParameter("playlist").toString(), Playlist.class);
        User user = userService.getLoggedInUser();
        Song[] songsArray = mapper.readValue(request.getParameter("songs").toString(), Song[].class);
        List<Song> songsList = Arrays.asList(songsArray);
        PlaylistKeyword[] playlistKeywordsArray = mapper.readValue(request.getParameter("keywords").toString(), PlaylistKeyword[].class);
        List<PlaylistKeyword> playlistKeywordsList = Arrays.asList(playlistKeywordsArray);
        Playlist updatedPlaylist = playlistService.update(playlist, songsList, user, playlistKeywordsList);
        return ResponseEntity.ok(updatedPlaylist);
    }

    @Role({USER, ARTIST})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable long id, MultipartHttpServletRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Playlist playlist = mapper.readValue(request.getParameter("playlist").toString(), Playlist.class);

        playlistService.delete(playlist);

        return ResponseEntity.ok().build();
    }
}
