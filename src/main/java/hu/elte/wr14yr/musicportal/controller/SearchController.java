package hu.elte.wr14yr.musicportal.controller;

import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    private Logger logger = Logger.getLogger(SearchController.class.getName());

    @GetMapping("/albums/{searchWord}")
    public ResponseEntity<Iterable<Album>> findAlbumByTitle(@PathVariable("searchWord") String searchWord) {
        logger.info(String.format("Search controller: enter endpoint '/albums/%s'", searchWord));

        Iterable<Album> foundAlbums = searchService.findAlbumsByTitle(searchWord);

        logger.info(String.format("Search controller: exit endpoint '/albums/%s'", searchWord));

        return ResponseEntity.ok(foundAlbums);
    }

    @GetMapping("/albums/tag/{tagWord}")
    public ResponseEntity<Iterable<Album>> findAlbumByTag(@PathVariable("tagWord") String tagWord) {
        logger.info(String.format("Search controller: enter endpoint '/albums/tag/%s'", tagWord));

        Iterable<Album> foundAlbums = searchService.findAlbumByAlbumTag(tagWord);

        logger.info(String.format("Search controller: exit endpoint '/albums/tag/%s'", tagWord));

        return ResponseEntity.ok(foundAlbums);
    }

    @GetMapping("/albums/genre/{genreWord}")
    public ResponseEntity<Iterable<Album>> findAlbumByGenre(@PathVariable("genreWord") String genreWord) {
        logger.info(String.format("Search controller: enter endpoint '/albums/genre/%s'", genreWord));

        Iterable<Album> foundAlbums = searchService.findAlbumsByGenre(genreWord);

        logger.info(String.format("Search controller: exit endpoint '/albums/genre/%s'", genreWord));

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

    @GetMapping("/playlists/tag/{tagWord}")
    public ResponseEntity<Iterable<Playlist>> findPlaylistByTag(@PathVariable("tagWord") String tagWord) {
        logger.info(String.format("Search controller: enter endpoint '/playlists/tag/%s'", tagWord));

        Iterable<Playlist> foundPlaylists = searchService.findPlaylistByPlaylistTag(tagWord);

        logger.info(String.format("Search controller: exit endpoint '/playlists/tag/%s'", tagWord));

        return ResponseEntity.ok(foundPlaylists);
    }

    @GetMapping("/users/tag/{tagWord}")
    public ResponseEntity<Iterable<User>> findUserByTag(@PathVariable("tagWord") String tagWord) {
        logger.info(String.format("Search controller: enter '/users/tag/%s'", tagWord));

        Iterable<User> foundUsers = searchService.findUsersByUserTag(tagWord);

        logger.info(String.format("Search controller: exit '/users/tag/%s'", tagWord));

        return ResponseEntity.ok(foundUsers);
    }

    @GetMapping("/users/genre/{genreWord}")
    public ResponseEntity<Iterable<User>> findUserByGenre(@PathVariable("genreWord") String genreWord) {
        logger.info(String.format("Search controller: enter endpoint '/users/genre/%s'", genreWord));

        Iterable<User> foundUsers = searchService.findUsersByGenre(genreWord);

        logger.info(String.format("Search controller: exit endpoint '/users/genre/%s'", genreWord));

        return ResponseEntity.ok(foundUsers);
    }
}