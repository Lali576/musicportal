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
import java.util.logging.Level;
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

    private ObjectMapper mapper = new ObjectMapper();

    private Logger logger = Logger.getLogger(PlaylistController.class.getName());

    @Role({USER, ARTIST})
    @PostMapping("/new")
    public ResponseEntity<Playlist> create(MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint '/new'");

        logger.log(Level.INFO, "Get parameter 'playlist'");
        Playlist playlist = mapper.readValue(request.getParameter("playlist"), Playlist.class);

        logger.log(Level.INFO, "Get current logged in user");
        User user = userService.getLoggedInUser();

        logger.log(Level.INFO, "Get parameter 'songs'");
        Song[] songsArray = mapper.readValue(request.getParameter("songs"), Song[].class);
        List<Song> songsList = Arrays.asList(songsArray);

        logger.log(Level.INFO, "Get parameter 'keywords'");
        PlaylistKeyword[] playlistKeywordsArray = mapper.readValue(request.getParameter("keywords"), PlaylistKeyword[].class);
        List<PlaylistKeyword> playlistKeywordsList = Arrays.asList(playlistKeywordsArray);

        Playlist savedPlaylist = playlistService.create(playlist, user, songsList, playlistKeywordsList);
        logger.log(Level.INFO, "Exit: endpoint '/new'");

        return ResponseEntity.ok(savedPlaylist);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> find(@PathVariable long id) {
        logger.log(Level.INFO, "Entrance: endpoint '/" + id + "'");
        Playlist foundPlaylist = playlistService.find(id);
        logger.log(Level.INFO, "Exit: endpoint '/" + id + "'");

        return ResponseEntity.ok(foundPlaylist);
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<Playlist>> listAll() {
        logger.log(Level.INFO, "Entrance: endpoint '/list'");
        Iterable<Playlist> playlist = playlistService.listAll();
        logger.log(Level.INFO, "Exit: endpoint '/list'");

        return ResponseEntity.ok(playlist);
    }

    @GetMapping("/by-user")
    public ResponseEntity<Iterable<Playlist>> list() {
        logger.log(Level.INFO, "Entrance: endpoint '/'");
        Iterable<Playlist> playlist = playlistService.list(userService.getLoggedInUser());
        logger.log(Level.INFO, "Exit: endpoint '/'");

        return ResponseEntity.ok(playlist);
    }

    @Role({USER, ARTIST})
    @PutMapping("/update/{id}")
    public ResponseEntity<Playlist> update(@PathVariable long id, MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint '/update/" + id + "'");

        logger.log(Level.INFO, "Get parameter 'playlist'");
        Playlist playlist = mapper.readValue(request.getParameter("playlist"), Playlist.class);

        logger.log(Level.INFO, "Get current logged in user");
        User user = userService.getLoggedInUser();

        logger.log(Level.INFO, "Get parameter 'songs'");
        Song[] songsArray = mapper.readValue(request.getParameter("songs"), Song[].class);
        List<Song> songsList = Arrays.asList(songsArray);

        logger.log(Level.INFO, "Get parameter 'keywords'");
        PlaylistKeyword[] playlistKeywordsArray = mapper.readValue(request.getParameter("keywords"), PlaylistKeyword[].class);
        List<PlaylistKeyword> playlistKeywordsList = Arrays.asList(playlistKeywordsArray);

        Playlist updatedPlaylist = playlistService.update(playlist, songsList, user, playlistKeywordsList);
        logger.log(Level.INFO, "Exit: endpoint '/update/" + id + "'");

        return ResponseEntity.ok(updatedPlaylist);
    }

    @Role({USER, ARTIST})
    @DeleteMapping("/delete/{id}")
    public @ResponseBody void delete(@PathVariable long id, MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint '/delete/" + id + "'");

        logger.log(Level.INFO, "Get parameter 'playlist'");
        Playlist playlist = mapper.readValue(request.getParameter("playlist"), Playlist.class);

        playlistService.delete(playlist);
        logger.log(Level.INFO, "Exit: endpoint '/delete/" + id + "'");

        //return ResponseEntity.ok().build();
    }
}
