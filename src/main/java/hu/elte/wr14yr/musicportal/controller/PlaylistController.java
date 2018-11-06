package hu.elte.wr14yr.musicportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.elte.wr14yr.musicportal.annotation.Role;
import hu.elte.wr14yr.musicportal.model.Playlist;

import static hu.elte.wr14yr.musicportal.model.User.Role.USER;
import static hu.elte.wr14yr.musicportal.model.User.Role.ARTIST;

import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.model.tags.PlaylistTag;
import hu.elte.wr14yr.musicportal.service.PlaylistService;
import hu.elte.wr14yr.musicportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private UserService userService;

    private ObjectMapper mapper = new ObjectMapper();

    private Logger logger = Logger.getLogger(PlaylistController.class.getName());

    @Role({USER, ARTIST})
    @PostMapping("/new")
    public ResponseEntity<Playlist> create(MultipartHttpServletRequest request) throws IOException {
        logger.info("Playlist controller: enter endpoint '/new'");

        logger.info("Playlist controller: get parameter 'playlist'");

        Playlist playlist = mapper.readValue(request.getParameter("playlist"), Playlist.class);

        logger.info("Playlist controller: get current logged in user");

        User user = userService.getLoggedInUser();

        logger.info("Playlist controller: get parameter 'songs'");

        Song[] songsArray = mapper.readValue(request.getParameter("songs"), Song[].class);
        List<Song> songsList = Arrays.asList(songsArray);

        logger.info("Playlist controller: get parameter 'playlistTags'");

        PlaylistTag[] playlistTagsArray = mapper.readValue(request.getParameter("playlistTags"), PlaylistTag[].class);
        List<PlaylistTag> playlistTagsList = Arrays.asList(playlistTagsArray);

        Playlist savedPlaylist = playlistService.create(playlist, user, songsList, playlistTagsList);

        logger.info("Playlist controller: exit endpoint '/new'");

        return ResponseEntity.ok(savedPlaylist);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> find(@PathVariable long id) {
        logger.info(String.format("Playlist controller: enter endpoint '/%s'", id));

        Playlist foundPlaylist = playlistService.find(id);

        logger.info(String.format("Playlist controller: exit endpoint '/%s'", id));

        return ResponseEntity.ok(foundPlaylist);
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<Playlist>> listAll() {
        logger.info("Playlist controller: enter endpoint '/list'");

        Iterable<Playlist> playlists = playlistService.listAll();

        logger.info("Playlist controller: exit endpoint '/list'");

        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/list-first-five")
    public ResponseEntity<Iterable<Playlist>> listFirstFive() {
        logger.info("Playlist controller: enter endpoint '/list-first-five'");

        Iterable<Playlist> playlists = playlistService.listFirstFive();

        logger.info("Playlist controller: exit endpoint '/list-first-five'");

        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<Iterable<Playlist>> list(@PathVariable long id) {
        logger.info(String.format("Playlist controller: enter endpoint '/by-user/%s'", id));

        Iterable<Playlist> playlist = playlistService.listByUser(id);

        logger.info(String.format("Playlist controller: exit endpoint '/by-user/%s'", id));

        return ResponseEntity.ok(playlist);
    }

    @Role({USER, ARTIST})
    @PostMapping("/update/{id}")
    public ResponseEntity<Playlist> update(@PathVariable long id, MultipartHttpServletRequest request) throws IOException {
        logger.info(String.format("Playlist controller: enter endpoint '/update/%s'", id));

        logger.info("Playlist controller: get parameter 'playlist'");

        Playlist playlist = mapper.readValue(request.getParameter("playlist"), Playlist.class);

        logger.info("Playlist controller: get current logged int user");

        User user = userService.getLoggedInUser();

        logger.info("Playlist controller: get parameter 'songs'");

        Song[] songsArray = mapper.readValue(request.getParameter("songs"), Song[].class);
        List<Song> songsList = Arrays.asList(songsArray);

        logger.info("Playlist controller: get parameter 'playlistTags'");

        PlaylistTag[] playlistTagsArray = mapper.readValue(request.getParameter("playlistTags"), PlaylistTag[].class);
        List<PlaylistTag> playlistTagsList = Arrays.asList(playlistTagsArray);

        Playlist updatedPlaylist = playlistService.update(playlist, songsList, user, playlistTagsList);

        logger.info(String.format("Playlist controller: exit endpoint '/update/%s'", id));

        return ResponseEntity.ok(updatedPlaylist);
    }

    @Role({USER, ARTIST})
    @DeleteMapping("/delete/{id}")
    public @ResponseBody void delete(@PathVariable long id, MultipartHttpServletRequest request) throws IOException {
        logger.info(String.format("Playlist controller: enter endpoint '/delete/%s'", id));

        logger.info("Playlist controller: get parameter 'playlist'");

        Playlist playlist = mapper.readValue(request.getParameter("playlist"), Playlist.class);

        playlistService.delete(playlist);

        logger.info(String.format("Playlist controller: exit endpoint '/delete/%s'", id));
    }
}
