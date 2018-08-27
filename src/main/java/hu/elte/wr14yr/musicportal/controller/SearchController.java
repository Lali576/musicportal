package hu.elte.wr14yr.musicportal.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.model.keywords.AlbumKeyword;
import hu.elte.wr14yr.musicportal.model.keywords.PlaylistKeyword;
import hu.elte.wr14yr.musicportal.model.keywords.SongKeyword;
import hu.elte.wr14yr.musicportal.model.keywords.UserKeyword;
import hu.elte.wr14yr.musicportal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    //@Role({ARTIST, GUEST, USER})
    @GetMapping("/genres/{name}")
    public ResponseEntity<Iterable<Genre>> findGenreByName(@PathVariable String name) {
        Iterable<Genre> foundGenres = searchService.findGenreByName(name);
        return ResponseEntity.ok(foundGenres);
    }

    //@Role({ARTIST, GUEST, USER})
    /*
    @GetMapping("/keywords/{word}")
    public ResponseEntity<Iterable<UserKeyword>> findKeywordByWord(@PathVariable String word) {
        Iterable<UserKeyword> foundKeywords = searchService.findKeywordByWord(word);
        return ResponseEntity.ok(foundKeywords);
    }
    */

    //@Role({ARTIST, GUEST, USER})
    @GetMapping("/albums/{name}")
    public ResponseEntity<Iterable<Album>> findAlbumByName(@PathVariable String name) {
        Iterable<Album> foundAlbums = searchService.findAlbumByName(name);
        return ResponseEntity.ok(foundAlbums);
    }

    //@Role({ARTIST, GUEST, USER})
    @PostMapping("/albums/keyword/{word}")
    public ResponseEntity<Iterable<Album>> findAlbumByKeyword(@PathVariable String word, MultipartHttpServletRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        AlbumKeyword[] albumKeywordsArray = mapper.readValue(request.getParameter("keywords").toString(), AlbumKeyword[].class);
        List<AlbumKeyword> albumKeywordsList = Arrays.asList(albumKeywordsArray);
        Iterable<Album> foundAlbums = searchService.findAlbumByAlbumKeywords(albumKeywordsList);

        return ResponseEntity.ok(foundAlbums);
    }

    //@Role({ARTIST, GUEST, USER})
    @PostMapping("/albums/genre/{name}")
    public ResponseEntity<Iterable<Album>> findAlbumByGenre(@RequestBody Genre genre, @PathVariable String name) {
        Iterable<Album> foundAlbums = searchService.findAlbumByGenre(genre);
        return ResponseEntity.ok(foundAlbums);
    }

    //@Role({ARTIST, GUEST, USER})
    @GetMapping("/songs/{title}")
    public ResponseEntity<Iterable<Song>> findSongByTitle(@PathVariable String title) {
        Iterable<Song> foundSongs = searchService.findSongByTitle(title);
        return ResponseEntity.ok(foundSongs);
    }

    //@Role({ARTIST, GUEST, USER})
    @PostMapping("/songs/keyword/{word}")
    public ResponseEntity<Iterable<Song>> findSongByKeyword(@PathVariable String word, MultipartHttpServletRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SongKeyword[] songKeywordsArray = mapper.readValue(request.getParameter("keywords").toString(), SongKeyword[].class);
        List<SongKeyword> songKeywordsList = Arrays.asList(songKeywordsArray);
        Iterable<Song> foundSongs = searchService.findSongBySongKeywords(songKeywordsList);
        return ResponseEntity.ok(foundSongs);
    }

    @PostMapping("/songs/genre/{name}")
    public ResponseEntity<Iterable<Song>> findSongsByGenre(@RequestBody Genre genre, @PathVariable String name) {
        Iterable<Song> foundSongs = searchService.findSongByGenre(genre);
        return ResponseEntity.ok(foundSongs);
    }

    //@Role({ARTIST, GUEST, USER})
    @GetMapping("/playlists/{name}")
    public ResponseEntity<Iterable<Playlist>> findPlaylistByName(@PathVariable String name) {
        Iterable<Playlist> foundPlaylists = searchService.findPlaylistByName(name);
        return ResponseEntity.ok(foundPlaylists);
    }

    //@Role({ARTIST, GUEST, USER})
    @PostMapping("/playlists/keyword/{word}")
    public ResponseEntity<Iterable<Playlist>> findPlaylistByKeyword(@PathVariable String word, MultipartHttpServletRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PlaylistKeyword[] playlistKeywordsArray = mapper.readValue(request.getParameter("keywords").toString(), PlaylistKeyword[].class);
        List<PlaylistKeyword> playlistKeywordsList = Arrays.asList(playlistKeywordsArray);
        Iterable<Playlist> foundPlaylists = searchService.findPlaylistByPlaylistKeywords(playlistKeywordsList);
        return ResponseEntity.ok(foundPlaylists);
    }

    //@Role({ARTIST, GUEST, USER})
    @GetMapping("/users/{username}")
    public ResponseEntity<Iterable<User>> findUserByUsername(@PathVariable String username) {
        Iterable<User> foundUsers = searchService.findUserByUsername(username);
        return ResponseEntity.ok(foundUsers);
    }

    //@Role({ARTIST, GUEST, USER})
    @PostMapping("/users/{word}")
    public ResponseEntity<Iterable<User>> findUserByKeyword(@PathVariable String word, MultipartHttpServletRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        UserKeyword[] userKeywordsArray = mapper.readValue(request.getParameter("keywords").toString(), UserKeyword[].class);
        List<UserKeyword> userKeywordsList = Arrays.asList(userKeywordsArray);
        Iterable<User> foundUsers = searchService.findUsersByUserKeywords(userKeywordsList);
        return ResponseEntity.ok(foundUsers);
    }
}
