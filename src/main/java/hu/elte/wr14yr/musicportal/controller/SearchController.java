package hu.elte.wr14yr.musicportal.controller;


import hu.elte.wr14yr.musicportal.annotation.Role;
import static hu.elte.wr14yr.musicportal.model.User.Role.ARTIST;
import static hu.elte.wr14yr.musicportal.model.User.Role.USER;
import static hu.elte.wr14yr.musicportal.model.User.Role.GUEST;

import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Key;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Role({ARTIST, GUEST, USER})
    @GetMapping("/genres/{name}")
    public ResponseEntity<Iterable<Genre>> findGenreByName(@PathVariable String name) {
        Iterable<Genre> foundGenres = searchService.findGenreByName(name);
        return ResponseEntity.ok(foundGenres);
    }

    @Role({ARTIST, GUEST, USER})
    @GetMapping("/keywords/{word}")
    public ResponseEntity<Iterable<Keyword>> findKeywordByWord(@PathVariable String word) {
        Iterable<Keyword> foundKeywords = searchService.findKeywordByWord(word);
        return ResponseEntity.ok(foundKeywords);
    }

    @Role({ARTIST, GUEST, USER})
    @GetMapping("/albums/{name}")
    public ResponseEntity<Iterable<Album>> findAlbumByName(@PathVariable String name) {
        Iterable<Album> foundAlbums = searchService.findAlbumByName(name);
        return ResponseEntity.ok(foundAlbums);
    }

    @Role({ARTIST, GUEST, USER})
    @GetMapping("/albums/keyword/{keyword}")
    public ResponseEntity<Iterable<Album>> findAlbumByKeyword(@RequestBody  Keyword keyword, @PathVariable String word) {
        Iterable<Album> foundAlbums = searchService.findAlbumByKeyword(keyword);
        return ResponseEntity.ok(foundAlbums);
    }

    @Role({ARTIST, GUEST, USER})
    @GetMapping("/albums/genre/{name}")
    public ResponseEntity<Iterable<Album>> findAlbumByGenre(@RequestBody Genre genre, @PathVariable String name) {
        Iterable<Album> foundAlbums = searchService.findAlbumByGenre(genre);
        return ResponseEntity.ok(foundAlbums);
    }

    @Role({ARTIST, GUEST, USER})
    @GetMapping("/songs/{title}")
    public ResponseEntity<Iterable<Song>> findSongByTitle(@PathVariable String title) {
        Iterable<Song> foundSongs = searchService.findSongByTitle(title);
        return ResponseEntity.ok(foundSongs);
    }

    @Role({ARTIST, GUEST, USER})
    @GetMapping("/songs/keyword/{keyword}")
    public ResponseEntity<Iterable<Song>> findSongByKeyword(@RequestBody Keyword keyword, @PathVariable String word) {
        Iterable<Song> foundSongs = null;
        return ResponseEntity.ok(foundSongs);
    }

    @Role({ARTIST, GUEST, USER})
    @GetMapping("/playlists/{name}")
    public ResponseEntity<Iterable<Playlist>> findPlaylistByName(@PathVariable String name) {
        Iterable<Playlist> foundPlaylists = searchService.findPlaylistByName(name);
        return ResponseEntity.ok(foundPlaylists);
    }

    @Role({ARTIST, GUEST, USER})
    @GetMapping("/playlists/keyword/{keyword}")
    public ResponseEntity<Iterable<Playlist>> findPlaylistByKeyword(@PathVariable String keyword) {
        Iterable<Playlist> foundPlaylists = null;
        return ResponseEntity.ok(foundPlaylists);
    }

    @Role({ARTIST, GUEST, USER})
    @GetMapping("/users/{username}")
    public ResponseEntity<Iterable<User>> findUserByUsername(@PathVariable String username) {
        Iterable<User> foundUsers = searchService.findUserByUsername(username);
        return ResponseEntity.ok(foundUsers);
    }

    @Role({ARTIST, GUEST, USER})
    @GetMapping("/users/{keyword}")
    public ResponseEntity<Iterable<User>> findUserByKeyword(@PathVariable String keyword) {
        Iterable<User> foundUsers = null;
        return ResponseEntity.ok(foundUsers);
    }
}
