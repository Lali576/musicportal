package hu.elte.wr14yr.musicportal.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.model.tags.*;
import hu.elte.wr14yr.musicportal.model.tags.PlaylistTag;
import hu.elte.wr14yr.musicportal.model.tags.UserTag;
import hu.elte.wr14yr.musicportal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    private ObjectMapper mapper = new ObjectMapper();

    private Logger logger = Logger.getLogger(SearchController.class.getName());

    @GetMapping("/albums/{title}")
    public ResponseEntity<Iterable<Album>> findAlbumByTitle(@PathVariable String title) {
        logger.info(String.format("Search controller: enter endpoint '/albums/%s", title));

        Iterable<Album> foundAlbums = searchService.findAlbumsByTitle(title);

        logger.info(String.format("Search controller: exit endpoint '/albums/%s", title));

        return ResponseEntity.ok(foundAlbums);
    }

    @PostMapping("/albums/keyword")
    public ResponseEntity<Iterable<Album>> findAlbumByKeyword(MultipartHttpServletRequest request) throws IOException {
        logger.info("Search controller: enter endpoint '/albums/keyword'");

        AlbumTag albumKeyword = mapper.readValue(request.getParameter("keyword"), AlbumTag.class);
        Iterable<Album> foundAlbums = searchService.findAlbumByAlbumKeyword(albumKeyword);

        logger.info("Search controller: exit endpoint '/albums/keyword'");

        return ResponseEntity.ok(foundAlbums);
    }

    @PostMapping("/albums/genre")
    public ResponseEntity<Iterable<Album>> findAlbumByGenre(MultipartHttpServletRequest request) throws IOException {
        logger.info("Search controller: enter endpoint '/albums/genre'");

        Genre genre = mapper.readValue(request.getParameter("genre"), Genre.class);
        Iterable<Album> foundAlbums = searchService.findAlbumsByGenre(genre);

        logger.info("Search controller: exit endpoint '/albums/genre'");

        return ResponseEntity.ok(foundAlbums);
    }

    @GetMapping("/songs/{title}")
    public ResponseEntity<Iterable<Song>> findSongByTitle(@PathVariable String title) {
        logger.info(String.format("Search controller: enter endpoint '/songs/%s'", title));

        Iterable<Song> foundSongs = searchService.findSongsByTitle(title);

        logger.info(String.format("Search controller: exit endpoint '/songs/%s'", title));

        return ResponseEntity.ok(foundSongs);
    }

    @PostMapping("/songs/keyword")
    public ResponseEntity<Iterable<Song>> findSongByKeyword(MultipartHttpServletRequest request) throws IOException {
        logger.info("Search controller: enter endpoint '/songs/keyword'");

        SongTag songKeyword = mapper.readValue(request.getParameter("songKeyword"), SongTag.class);
        Iterable<Song> foundSongs = searchService.findSongsBySongKeywords(songKeyword);

        logger.info("Search controller: exit endpoint '/songs/keyword'");

        return ResponseEntity.ok(foundSongs);
    }

    @PostMapping("/songs/genre")
    public ResponseEntity<Iterable<Song>> findSongsByGenre(MultipartHttpServletRequest request) throws IOException {
        logger.info("Search controller: enter endpoint '/songs/genre'");

        Genre genre = mapper.readValue(request.getParameter("genre"), Genre.class);
        Iterable<Song> foundSongs = searchService.findSongsByGenre(genre);

        logger.info("Search controller: exit endpoint '/songs/genre'");

        return ResponseEntity.ok(foundSongs);
    }

    @GetMapping("/playlists/{name}")
    public ResponseEntity<Iterable<Playlist>> findPlaylistByName(@PathVariable String name) {
        logger.info(String.format("Search controller: enter endpoint '/playlists/%s'", name));

        Iterable<Playlist> foundPlaylists = searchService.findPlaylistByName(name);

        logger.info(String.format("Search controller: exit endpoint '/playlists/%s'", name));

        return ResponseEntity.ok(foundPlaylists);
    }

    @PostMapping("/playlists/keyword")
    public ResponseEntity<Iterable<Playlist>> findPlaylistByKeyword(MultipartHttpServletRequest request) throws IOException {
        logger.info("Search controller enter endpoint '/playlists/keyword'");

        PlaylistTag playlistKeyword = mapper.readValue(request.getParameter("playlistKeyword"), PlaylistTag.class);
        Iterable<Playlist> foundPlaylists = searchService.findPlaylistByPlaylistKeyword(playlistKeyword);

        logger.info("Search controller exit endpoint '/playlists/keyword'");

        return ResponseEntity.ok(foundPlaylists);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<Iterable<User>> findUserByUsername(@PathVariable String username) {
        logger.info(String.format("Search controller: enter endpoint '/users/%s'", username));

        Iterable<User> foundUsers = searchService.findUsersByUsername(username);

        logger.info(String.format("Search controller: exit endpoint '/users/%s'", username));

        return ResponseEntity.ok(foundUsers);
    }


    @PostMapping("/users/keyword")
    public ResponseEntity<Iterable<User>> findUserByKeyword(MultipartHttpServletRequest request) throws IOException {
        logger.info("Search controller: enter '/users/keyword'");

        UserTag userKeyword = mapper.readValue(request.getParameter("userKeyword"), UserTag.class);
        Iterable<User> foundUsers = searchService.findUsersByUserKeyword(userKeyword);

        logger.info("Search controller: exit '/users/keyword'");

        return ResponseEntity.ok(foundUsers);
    }
}