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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    private ObjectMapper mapper = new ObjectMapper();

    private Logger logger = Logger.getLogger(SearchController.class.getName());

    @GetMapping("/genres/{name}")
    public ResponseEntity<Iterable<Genre>> findGenreByName(@PathVariable String name) {
        logger.log(Level.INFO, "Entrance: endpoint '/genres/" + name + "'");
        Iterable<Genre> foundGenres = searchService.findGenreByName(name);
        logger.log(Level.INFO, "Exit: endpoint '/genres/" + name + "'");

        return ResponseEntity.ok(foundGenres);
    }

    @GetMapping("/albums/{title}")
    public ResponseEntity<Iterable<Album>> findAlbumByTitle(@PathVariable String title) {
        logger.log(Level.INFO, "Entrance: endpoint '/albums/" + title + "'");
        Iterable<Album> foundAlbums = searchService.findAlbumsByTitle(title);
        logger.log(Level.INFO, "Exit: endpoint '/genres/" + title + "'");

        return ResponseEntity.ok(foundAlbums);
    }

    @PostMapping("/albums/keyword")
    public ResponseEntity<Iterable<Album>> findAlbumByKeyword(MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint '/albums/keyword'");
        AlbumKeyword[] albumKeywordsArray = mapper.readValue(request.getParameter("keywords"), AlbumKeyword[].class);
        List<AlbumKeyword> albumKeywordsList = Arrays.asList(albumKeywordsArray);
        Iterable<Album> foundAlbums = searchService.findAlbumByAlbumKeywords(albumKeywordsList);
        logger.log(Level.INFO, "Exit: endpoint '/albums/keyword'");

        return ResponseEntity.ok(foundAlbums);
    }

    @PostMapping("/albums/genre")
    public ResponseEntity<Iterable<Album>> findAlbumByGenre(MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint '/albums/genre'");
        Genre[] genresArray = mapper.readValue(request.getParameter("genres"), Genre[].class);
        List<Genre> genres = Arrays.asList(genresArray);
        Iterable<Album> foundAlbums = searchService.findAlbumsByGenre(genres);
        logger.log(Level.INFO, "Exit: endpoint '/albums/genre'");

        return ResponseEntity.ok(foundAlbums);
    }

    @GetMapping("/songs/{title}")
    public ResponseEntity<Iterable<Song>> findSongByTitle(@PathVariable String title) {
        logger.log(Level.INFO, "Entrance: endpoint '/songs/" + title + "'");
        Iterable<Song> foundSongs = searchService.findSongsByTitle(title);
        logger.log(Level.INFO, "Exit: endpoint '/songs/" + title + "'");

        return ResponseEntity.ok(foundSongs);
    }

    @PostMapping("/songs/keyword")
    public ResponseEntity<Iterable<Song>> findSongByKeyword(MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint '/songs/keyword'");
        SongKeyword[] songKeywordsArray = mapper.readValue(request.getParameter("keywords"), SongKeyword[].class);
        List<SongKeyword> songKeywordsList = Arrays.asList(songKeywordsArray);
        Iterable<Song> foundSongs = searchService.findSongsBySongKeywords(songKeywordsList);
        logger.log(Level.INFO, "Exit: endpoint '/songs/keyword'");

        return ResponseEntity.ok(foundSongs);
    }

    @PostMapping("/songs/genre")
    public ResponseEntity<Iterable<Song>> findSongsByGenre(MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint '/songs/genre'");
        Genre[] genresArray = mapper.readValue(request.getParameter("genres"), Genre[].class);
        List<Genre> genres = Arrays.asList(genresArray);
        Iterable<Song> foundSongs = searchService.findSongsByGenres(genres);
        logger.log(Level.INFO, "Exit: endpoint '/songs/genre'");

        return ResponseEntity.ok(foundSongs);
    }

    @GetMapping("/playlists/{name}")
    public ResponseEntity<Iterable<Playlist>> findPlaylistByName(@PathVariable String name) {
        logger.log(Level.INFO, "Entrance: endpoint '/playlists/" + name + "'");
        Iterable<Playlist> foundPlaylists = searchService.findPlaylistByName(name);
        logger.log(Level.INFO, "Exit: endpoint '/playlists/" + name + "'");

        return ResponseEntity.ok(foundPlaylists);
    }

    @PostMapping("/playlists/keyword")
    public ResponseEntity<Iterable<Playlist>> findPlaylistByKeyword(MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint '/playlists/keyword'");
        PlaylistKeyword[] playlistKeywordsArray = mapper.readValue(request.getParameter("keywords"), PlaylistKeyword[].class);
        List<PlaylistKeyword> playlistKeywordsList = Arrays.asList(playlistKeywordsArray);
        Iterable<Playlist> foundPlaylists = searchService.findPlaylistByPlaylistKeywords(playlistKeywordsList);
        logger.log(Level.INFO, "Exit: endpoint '/playlists/keyword'");

        return ResponseEntity.ok(foundPlaylists);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<Iterable<User>> findUserByUsername(@PathVariable String username) {
        logger.log(Level.INFO, "Entrance: endpoint '/users/" + username + "'");
        Iterable<User> foundUsers = searchService.findUsersByUsername(username);
        logger.log(Level.INFO, "Exit: endpoint '/users/" + username + "'");

        return ResponseEntity.ok(foundUsers);
    }


    @PostMapping("/users/keyword")
    public ResponseEntity<Iterable<User>> findUserByKeyword(MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint '/users/keyword'");
        UserKeyword[] userKeywordsArray = mapper.readValue(request.getParameter("keywords"), UserKeyword[].class);
        List<UserKeyword> userKeywordsList = Arrays.asList(userKeywordsArray);
        Iterable<User> foundUsers = searchService.findUsersByUserKeywords(userKeywordsList);
        logger.log(Level.INFO, "Exit: endpoint '/users/keyword'");

        return ResponseEntity.ok(foundUsers);
    }
}