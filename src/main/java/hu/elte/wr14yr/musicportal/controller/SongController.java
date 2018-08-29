package hu.elte.wr14yr.musicportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.elte.wr14yr.musicportal.annotation.Role;
import static hu.elte.wr14yr.musicportal.model.User.Role.ARTIST;
import static hu.elte.wr14yr.musicportal.model.User.Role.GUEST;
import static hu.elte.wr14yr.musicportal.model.User.Role.USER;

import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.model.keywords.SongKeyword;
import hu.elte.wr14yr.musicportal.service.FileService;
import hu.elte.wr14yr.musicportal.service.SongService;
import hu.elte.wr14yr.musicportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/song")
public class SongController {

    @Autowired
    private SongService songService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    private Logger logger = Logger.getLogger(SongController.class.getName());

    @Role({ARTIST})
    @PostMapping("/new")
    public ResponseEntity<Song> create(MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "");
        MultipartFile multipartFile = null;

        Iterator<String> iterator = request.getFileNames();

        logger.log(Level.INFO, "Get file parameter");
        while (iterator.hasNext()) {
            multipartFile = request.getFile(iterator.next());
        }

        File file = fileService.convertToFile(multipartFile);

        ObjectMapper mapper = new ObjectMapper();
        Song song = mapper.readValue(request.getParameter("song").toString(), Song.class);
        User user = userService.getLoggedInUser();
        Album album = mapper.readValue(request.getParameter("album").toString(), Album.class);
        Genre[] genresArray = mapper.readValue(request.getParameter("genres").toString(), Genre[].class);
        List<Genre> genresList = Arrays.asList(genresArray);
        SongKeyword[] songKeywordsArray = mapper.readValue(request.getParameter("keywords").toString(), SongKeyword[].class);
        List<SongKeyword> songKeywordsList = Arrays.asList(songKeywordsArray);
        Song savedSong = songService.create(song, user, album, file, genresList, songKeywordsList);
        logger.log(Level.INFO, "");

        return ResponseEntity.ok(savedSong);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> find(@PathVariable long id) {
        logger.log(Level.INFO, "");
        Song foundSong  = songService.find(id);
        logger.log(Level.INFO, "");

        return ResponseEntity.ok(foundSong);
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<Song>> listAll() {
        Iterable<Song> songs = songService.listAll();
        return ResponseEntity.ok(songs);
    }

    @GetMapping
    public ResponseEntity<Iterable<Song>> list() {
        Iterable<Song> songs = songService.list(userService.getLoggedInUser());
        return ResponseEntity.ok(songs);
    }

    @PostMapping("/by-album")
    public ResponseEntity<Iterable<Song>> songsByAlbum(MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "");
        ObjectMapper mapper = new ObjectMapper();
        Album album = mapper.readValue(request.getParameter("album").toString(), Album.class);
        Iterable<Song> songs = songService.listByAlbum(album);
        logger.log(Level.INFO, "");

        return ResponseEntity.ok(songs);
    }

    @PostMapping("/by-playlist")
    public ResponseEntity<Iterable<Song>> songsByPlaylist(MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "");
        ObjectMapper mapper = new ObjectMapper();
        Playlist playlist = mapper.readValue(request.getParameter("playlist").toString(), Playlist.class);
        Iterable<Song> songs = songService.listByPlaylist(playlist);
        logger.log(Level.INFO, "");

        return ResponseEntity.ok(songs);
    }

    @Role({ARTIST, USER})
    @PostMapping("/comments/new")
    public ResponseEntity<Iterable<SongComment>> createSongComment(MultipartHttpServletRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SongComment songComment = mapper.readValue(request.getParameter("songComment").toString(), SongComment.class);
        Song song = mapper.readValue(request.getParameter("song").toString(), Song.class);
        Iterable<SongComment> songComments = songService.createSongComment(songComment, song);

        return ResponseEntity.ok(songComments);
    }

    @Role({ARTIST, USER})
    @PostMapping("/comments/{id}")
    public ResponseEntity<Iterable<SongComment>> listSongComments(@PathVariable("id") long id, MultipartHttpServletRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Song song = mapper.readValue(request.getParameter("song").toString(), Song.class);
        Iterable<SongComment> songComments = songService.listSongComments(song);

        return ResponseEntity.ok(songComments);
    }

    @Role({ARTIST, USER})
    @PostMapping("/like/new")
    public ResponseEntity<Integer> createSongLike(MultipartHttpServletRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SongLike songLike = mapper.readValue(request.getParameter("songLike").toString(), SongLike.class);
        Song song = mapper.readValue(request.getParameter("song").toString(), Song.class);
        int likeNumber = songService.saveSongLike(songLike, song);

        return  ResponseEntity.ok(likeNumber);
    }

    @Role({ARTIST, USER})
    @PostMapping("/like/{id}")
    public ResponseEntity<int[]> countSongLikes(@PathVariable("id") long id, MultipartHttpServletRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Song song = mapper.readValue(request.getParameter("song").toString(), Song.class);
        int[] likesNumbers = songService.countLikesDivided(song);

        return ResponseEntity.ok(likesNumbers);
    }

    @PostMapping("/counter/new")
    public ResponseEntity<Integer> createSongCounter(MultipartHttpServletRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SongCounter songCounter = mapper.readValue(request.getParameter("songCounter").toString(), SongCounter.class);
        Song song = mapper.readValue(request.getParameter("song").toString(), Song.class);
        User user = mapper.readValue(request.getParameter("user").toString(), User.class);
        int counterNumber = songService.saveSongCounter(songCounter, song, user);

        return  ResponseEntity.ok(counterNumber);
    }

    @PostMapping("/counter/{id}")
    public ResponseEntity<Integer> countSongCounter(@PathVariable("id") long id, MultipartHttpServletRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Song  song = mapper.readValue(request.getParameter("song").toString(), Song.class);
        int counterNumber = songService.countSongCounterNumber(song);

        return ResponseEntity.ok(counterNumber);
    }

    @Role({ARTIST})
    @PutMapping("/update/{id}")
    public ResponseEntity<Song> update(@PathVariable long id, MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "");
        MultipartFile multipartFile = null;

        Iterator<String> iterator = request.getFileNames();

        logger.log(Level.INFO, "Get file parameter");
        while (iterator.hasNext()) {
            multipartFile = request.getFile(iterator.next());
        }

        File file = fileService.convertToFile(multipartFile);

        ObjectMapper mapper = new ObjectMapper();
        Song song = mapper.readValue(request.getParameter("song").toString(), Song.class);
        User user = userService.getLoggedInUser();
        Album album = mapper.readValue(request.getParameter("album").toString(), Album.class);
        Song updatedSong = songService.update(song,album,user,file);
        logger.log(Level.INFO, "");

        return ResponseEntity.ok(updatedSong);
    }

    @Role({ARTIST})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable long id, MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "");
        ObjectMapper mapper = new ObjectMapper();
        Song song = mapper.readValue(request.getParameter("album").toString(), Song.class);
        songService.delete(song);
        logger.log(Level.INFO, "");

        return ResponseEntity.ok().build();
    }
}
