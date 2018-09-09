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

    private ObjectMapper mapper = new ObjectMapper();

    private Logger logger = Logger.getLogger(SongController.class.getName());

    @Role({ARTIST})
    @PostMapping("/new")
    public ResponseEntity<Song> create(MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint '/new'");
        MultipartFile multipartFile = null;
        File file = null;

        Iterator<String> iterator = request.getFileNames();

        logger.log(Level.INFO, "Get file parameter");
        while (iterator.hasNext()) {
            multipartFile = request.getFile(iterator.next());
        }

        if(multipartFile != null) {
            file = fileService.convertToFile(multipartFile);
        }

        logger.log(Level.INFO, "Get parameter 'song'");
        Song song = mapper.readValue(request.getParameter("song"), Song.class);

        logger.log(Level.INFO, "Get current logged in user");
        User user = userService.getLoggedInUser();

        logger.log(Level.INFO, "Get parameter 'album'");
        Album album = mapper.readValue(request.getParameter("album"), Album.class);

        logger.log(Level.INFO, "Get parameter 'genres'");
        Genre[] genresArray = mapper.readValue(request.getParameter("genres"), Genre[].class);
        List<Genre> genresList = Arrays.asList(genresArray);

        logger.log(Level.INFO, "Get parameter 'keywords'");
        SongKeyword[] songKeywordsArray = mapper.readValue(request.getParameter("keywords"), SongKeyword[].class);
        List<SongKeyword> songKeywordsList = Arrays.asList(songKeywordsArray);

        Song savedSong = songService.create(song, user, album, file, genresList, songKeywordsList);
        logger.log(Level.INFO, "Exit: endpoint '/new'");

        if(file != null) {
            file.delete();
        }

        return ResponseEntity.ok(savedSong);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> find(@PathVariable long id) {
        logger.log(Level.INFO, "Entrance: endpoint '/" + id + "'");
        Song foundSong  = songService.find(id);
        logger.log(Level.INFO, "Exit: endpoint '/" + id + "'");

        return ResponseEntity.ok(foundSong);
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<Song>> listAll() {
        logger.log(Level.INFO, "Entrance: endpoint '/list'");
        Iterable<Song> songs = songService.listAll();
        logger.log(Level.INFO, "Exit: endpoint '/list'");

        return ResponseEntity.ok(songs);
    }

    @GetMapping("/by-user")
    public ResponseEntity<Iterable<Song>> list() {
        logger.log(Level.INFO, "Entrance: endpoint '/'");
        Iterable<Song> songs = songService.list(userService.getLoggedInUser());
        logger.log(Level.INFO, "Exit: endpoint '/'");

        return ResponseEntity.ok(songs);
    }

    @PostMapping("/by-album")
    public ResponseEntity<Iterable<Song>> songsByAlbum(MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint '/by-album'");

        logger.log(Level.INFO, "Get parameter 'album'");
        Album album = mapper.readValue(request.getParameter("album"), Album.class);

        Iterable<Song> songs = songService.listByAlbum(album);
        logger.log(Level.INFO, "Exit: endpoint '/by-album'");

        return ResponseEntity.ok(songs);
    }

    @PostMapping("/by-playlist")
    public ResponseEntity<Iterable<Song>> songsByPlaylist(MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint '/by-playlist'");

        logger.log(Level.INFO, "Get parameter 'playlist'");
        Playlist playlist = mapper.readValue(request.getParameter("playlist"), Playlist.class);

        Iterable<Song> songs = songService.listByPlaylist(playlist);
        logger.log(Level.INFO, "Entrance: endpoint '/by-playlist'");

        return ResponseEntity.ok(songs);
    }

    @Role({ARTIST})
    @PutMapping("/update/{id}")
    public ResponseEntity<Song> update(@PathVariable long id, MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint '/update/" + id + "'");
        MultipartFile multipartFile = null;
        File file = null;

        Iterator<String> iterator = request.getFileNames();

        logger.log(Level.INFO, "Get file parameter");
        while (iterator.hasNext()) {
            multipartFile = request.getFile(iterator.next());
        }

        if(multipartFile != null) {
            file = fileService.convertToFile(multipartFile);
        }

        logger.log(Level.INFO, "Get parameter 'song'");
        Song song = mapper.readValue(request.getParameter("song"), Song.class);

        logger.log(Level.INFO, "Get current logged in user");
        User user = userService.getLoggedInUser();

        logger.log(Level.INFO, "Get parameter 'album'");
        Album album = mapper.readValue(request.getParameter("album"), Album.class);

        Song updatedSong = songService.update(song,album,user,file);
        logger.log(Level.INFO, "Exit: endpoint '/update/" + id + "'");

        return ResponseEntity.ok(updatedSong);
    }

    @Role({ARTIST})
    @DeleteMapping("/delete/{id}")
    public @ResponseBody void delete(@PathVariable long id) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint '/delete/" + id + "'");
        Song song = songService.find(id);
        songService.delete(song);
        logger.log(Level.INFO, "Entrance: endpoint '/delete/" + id + "'");

        //return ResponseEntity.ok().build();
    }

    @Role({ARTIST, USER})
    @PostMapping("/comments/new")
    public ResponseEntity<Iterable<SongComment>> createSongComment(MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint '/comment/new'");

        logger.log(Level.INFO, "Get parameter 'songComment'");
        SongComment songComment = mapper.readValue(request.getParameter("songComment"), SongComment.class);

        logger.log(Level.INFO, "Get current logged in user");
        User user = userService.getLoggedInUser();

        logger.log(Level.INFO, "Get parameter 'song'");
        Song song = mapper.readValue(request.getParameter("song"), Song.class);

        Iterable<SongComment> songComments = songService.createSongComment(songComment, user, song);
        logger.log(Level.INFO, "Exit: endpoint '/comment/new'");

        return ResponseEntity.ok(songComments);
    }

    @Role({ARTIST, USER})
    @PostMapping("/comments/{id}")
    public ResponseEntity<Iterable<SongComment>> listSongComments(@PathVariable("id") long id, MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint '/comments/" + id + "'");

        logger.log(Level.INFO, "Get parameter 'song'");
        Song song = mapper.readValue(request.getParameter("song"), Song.class);

        Iterable<SongComment> songComments = songService.listSongComments(song);
        logger.log(Level.INFO, "Entrance: endpoint '/comments/" + id + "'");

        return ResponseEntity.ok(songComments);
    }

    @Role({ARTIST, USER})
    @PostMapping("/like/new")
    public ResponseEntity<Integer> createSongLike(MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint '/like/new'");
        ObjectMapper mapper = new ObjectMapper();

        logger.log(Level.INFO, "Get parameter 'songLike'");
        SongLike songLike = mapper.readValue(request.getParameter("songLike"), SongLike.class);

        logger.log(Level.INFO, "Get current logged in user");
        User user = userService.getLoggedInUser();

        logger.log(Level.INFO, "Get parameter 'song'");
        Song song = mapper.readValue(request.getParameter("song"), Song.class);

        int likeNumber = songService.saveSongLike(songLike, user, song);
        logger.log(Level.INFO, "Exit: endpoint '/like/new'");

        return  ResponseEntity.ok(likeNumber);
    }

    @Role({ARTIST, USER})
    @PostMapping("/like/{id}")
    public ResponseEntity<int[]> countSongLikes(@PathVariable("id") long id, MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint '/like/" + id + "'");

        logger.log(Level.INFO, "Get parameter 'song'");
        Song song = mapper.readValue(request.getParameter("song"), Song.class);

        int[] likesNumbers = songService.countLikesDivided(song);
        logger.log(Level.INFO, "Entrance: endpoint '/like/" + id + "'");

        return ResponseEntity.ok(likesNumbers);
    }

    @PostMapping("/counter/new")
    public ResponseEntity<Integer> createSongCounter(MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint '/counter/new'");

        logger.log(Level.INFO, "Get parameter 'songCounter'");
        SongCounter songCounter = mapper.readValue(request.getParameter("songCounter"), SongCounter.class);

        logger.log(Level.INFO, "Get parameter 'song'");
        Song song = mapper.readValue(request.getParameter("song"), Song.class);

        logger.log(Level.INFO, "Get current logged in user");
        User user = userService.getLoggedInUser();

        int counterNumber = songService.saveSongCounter(songCounter, song, user);
        logger.log(Level.INFO, "Entrance: endpoint '/counter/new'");

        return  ResponseEntity.ok(counterNumber);
    }

    @PostMapping("/counter/{id}")
    public ResponseEntity<Integer> countSongCounter(@PathVariable("id") long id, MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint '/counter/" + id + "'");

        logger.log(Level.INFO, "Get parameter 'song'");
        Song  song = mapper.readValue(request.getParameter("song"), Song.class);

        int counterNumber = songService.countSongCounterNumber(song);
        logger.log(Level.INFO, "Exit: endpoint '/counter/" + id + "'");

        return ResponseEntity.ok(counterNumber);
    }
}
