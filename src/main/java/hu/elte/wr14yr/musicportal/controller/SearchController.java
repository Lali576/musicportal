package hu.elte.wr14yr.musicportal.controller;

import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    private Logger logger = Logger.getLogger(SearchController.class.getName());

    @GetMapping("/albums/{searchWord}")
    public ResponseEntity<Iterable<Album>> findAlbumByTitle(@PathVariable("searchWord") String searchWord) {
        logger.info(String.format("Search controller: enter endpoint '/albums/%s", searchWord));

        Iterable<Album> foundAlbums = searchService.findAlbumsByTitle(searchWord);

        logger.info(String.format("Search controller: exit endpoint '/albums/%s", searchWord));

        return ResponseEntity.ok(foundAlbums);
    }

    @PostMapping("/albums/tag")
    public ResponseEntity<Iterable<Album>> findAlbumByTag(MultipartHttpServletRequest request) {
        logger.info("Search controller: enter endpoint '/albums/tag'");

        String tagWord = request.getParameter("tag");
        Iterable<Album> foundAlbums = searchService.findAlbumByAlbumTag(tagWord);

        logger.info("Search controller: exit endpoint '/albums/tag'");

        return ResponseEntity.ok(foundAlbums);
    }

    @PostMapping("/albums/genre")
    public ResponseEntity<Iterable<Album>> findAlbumByGenre(MultipartHttpServletRequest request) {
        logger.info("Search controller: enter endpoint '/albums/genre'");

        String genreWord = request.getParameter("genre");
        Iterable<Album> foundAlbums = searchService.findAlbumsByGenre(genreWord);

        logger.info("Search controller: exit endpoint '/albums/genre'");

        return ResponseEntity.ok(foundAlbums);
    }

    @GetMapping("/songs/{searchWord}")
    public ResponseEntity<Iterable<Song>> findSongByTitle(@PathVariable("searchWord") String searchWord) {
        logger.info(String.format("Search controller: enter endpoint '/songs/%s'", searchWord));

        Iterable<Song> foundSongs = searchService.findSongsByTitle(searchWord);

        logger.info(String.format("Search controller: exit endpoint '/songs/%s'", searchWord));

        return ResponseEntity.ok(foundSongs);
    }

    @GetMapping("/playlists/{searchWord}")
    public ResponseEntity<Iterable<Playlist>> findPlaylistByName(@PathVariable("searchWord") String searchWord) {
        logger.info(String.format("Search controller: enter endpoint '/playlists/%s'", searchWord));

        Iterable<Playlist> foundPlaylists = searchService.findPlaylistByName(searchWord);

        logger.info(String.format("Search controller: exit endpoint '/playlists/%s'", searchWord));

        return ResponseEntity.ok(foundPlaylists);
    }

    @PostMapping("/playlists/tag")
    public ResponseEntity<Iterable<Playlist>> findPlaylistByTag(MultipartHttpServletRequest request) {
        logger.info("Search controller enter endpoint '/playlists/tag'");

        String tagWord = request.getParameter("tag");
        Iterable<Playlist> foundPlaylists = searchService.findPlaylistByPlaylistTag(tagWord);

        logger.info("Search controller exit endpoint '/playlists/tag'");

        return ResponseEntity.ok(foundPlaylists);
    }

    @PostMapping("/users/tag")
    public ResponseEntity<Iterable<User>> findUserByTag(MultipartHttpServletRequest request) {
        logger.info("Search controller: enter '/users/tag'");

        String tagWord = request.getParameter("tag");
        Iterable<User> foundUsers = searchService.findUsersByUserTag(tagWord);

        logger.info("Search controller: exit '/users/tag'");

        return ResponseEntity.ok(foundUsers);
    }

    @PostMapping("/users/genre")
    public ResponseEntity<Iterable<User>> findUserByGenre(MultipartHttpServletRequest request) {
        logger.info("Search controller: enter endpoint '/users/genre'");

        String genreWord = request.getParameter("genre");
        Iterable<User> foundUsers = searchService.findUsersByGenre(genreWord);

        logger.info("Search controller: exit endpoint '/users/genre'");

        return ResponseEntity.ok(foundUsers);
    }
}