package hu.elte.wr14yr.musicportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.elte.wr14yr.musicportal.annotation.Role;
import static hu.elte.wr14yr.musicportal.model.User.Role.ARTIST;
import static hu.elte.wr14yr.musicportal.model.User.Role.USER;

import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.model.tags.SongTag;
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
        logger.info("Song controller: enter endpoint '/new'");

        MultipartFile multipartFile = null;
        File file = null;

        Iterator<String> iterator = request.getFileNames();

        logger.info("Song controller: get file parameter");

        while (iterator.hasNext()) {
            multipartFile = request.getFile(iterator.next());
        }

        if(multipartFile != null) {
            file = fileService.convertToFile(multipartFile);
        }

        logger.info("Song controller: get parameter  'song'");

        Song song = mapper.readValue(request.getParameter("song"), Song.class);

        logger.info("Song controller: get current logged in user");

        User user = userService.getLoggedInUser();

        logger.info("Song controller: get parameter  'album'");

        Album album = mapper.readValue(request.getParameter("album"), Album.class);

        logger.info("Song controller: get parameter  'genres'");

        Genre[] genresArray = mapper.readValue(request.getParameter("genres"), Genre[].class);
        List<Genre> genresList = Arrays.asList(genresArray);

        logger.info("Song controller: get parameter  'songTags'");

        SongTag[] songTagsArray = mapper.readValue(request.getParameter("songTags"), SongTag[].class);
        List<SongTag> songTagsList = Arrays.asList(songTagsArray);

        Song savedSong = songService.create(song, user, album, file, genresList, songTagsList);

        if(file != null) {
            file.delete();
        }

        logger.info("Song controller: exit endpoint '/new'");

        return ResponseEntity.ok(savedSong);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> find(@PathVariable long id) {
        logger.info(String.format("Song controller: enter endpoint '/%s'", id));

        Song foundSong  = songService.find(id);

        logger.info(String.format("Song controller: exit endpoint '/%s'", id));

        return ResponseEntity.ok(foundSong);
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<Song>> listAll() {
        logger.info("Song controller: enter endpoint '/list'");

        Iterable<Song> songs = songService.listAll();

        logger.info("Song controller: exit endpoint '/list'");

        return ResponseEntity.ok(songs);
    }

    @GetMapping("/list-first-five")
    public ResponseEntity<Iterable<Song>>  listFirstFive() {
        logger.info("Song controller: enter endpoint '/list-first-five'");

        Iterable<Song> songs = songService.listFirstFive();

        logger.info("Song controller: enter endpoint '/list-first-five'");

        return ResponseEntity.ok(songs);
    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<Iterable<Song>> listByUser(@PathVariable long id) {
        logger.info(String.format("Song controller: enter endpoint '/by-user/%s'", id));

        Iterable<Song> songs = songService.listByUser(id);

        logger.info(String.format("Song controller: exit endpoint '/by-user/%s'", id));

        return ResponseEntity.ok(songs);
    }

    @PostMapping("/by-album")
    public ResponseEntity<Iterable<Song>> listByAlbum(MultipartHttpServletRequest request) throws IOException {
        logger.info("Song controller: enter endpoint '/by-album'");

        logger.info("Song controller: ger parameter 'album'");

        Album album = mapper.readValue(request.getParameter("album"), Album.class);
        Iterable<Song> songs = songService.listByAlbum(album);

        logger.info("Song controller: exit endpoint '/by-album'");

        return ResponseEntity.ok(songs);
    }

    @PostMapping("/by-playlist")
    public ResponseEntity<Iterable<Song>> listByPlaylist(MultipartHttpServletRequest request) throws IOException {
        logger.info("Song controller: enter endpoint '/by-playlist'");

        logger.info("Song controller: get parameter 'playlist'");

        Playlist playlist = mapper.readValue(request.getParameter("playlist"), Playlist.class);

        Iterable<Song> songs = songService.listByPlaylist(playlist);

        logger.info("Song controller: enter endpoint '/by-playlist'");

        return ResponseEntity.ok(songs);
    }

    @Role({ARTIST})
    @PutMapping("/update/{id}")
    public ResponseEntity<Song> update(@PathVariable long id, MultipartHttpServletRequest request) throws IOException {
        logger.info(String.format("Song controller: enter endpoint '/update/%s'", id));

        MultipartFile multipartFile = null;
        File file = null;

        Iterator<String> iterator = request.getFileNames();

        logger.info("Song controller: get file parameter");

        while (iterator.hasNext()) {
            multipartFile = request.getFile(iterator.next());
        }

        if(multipartFile != null) {
            file = fileService.convertToFile(multipartFile);
        }

        logger.info("Song controller: get parameter 'song'");

        Song song = mapper.readValue(request.getParameter("song"), Song.class);

        logger.info("Song controller: get current logged in user");

        User user = userService.getLoggedInUser();

        logger.info("Song controller: get parameter 'album'");

        Album album = mapper.readValue(request.getParameter("album"), Album.class);

        Song updatedSong = songService.update(song,album,user,file);

        if(file != null) {
            file.delete();
        }

        logger.info(String.format("Song controller: exit endpoint '/update/%s'", id));

        return ResponseEntity.ok(updatedSong);
    }

    @Role({ARTIST})
    @DeleteMapping("/delete/{id}")
    public @ResponseBody void delete(@PathVariable long id) {
        logger.info(String.format("Song controller: enter endpoint '/delete/%s'", id));

        Song song = songService.find(id);
        songService.delete(song);

        logger.info(String.format("Song controller: exit endpoint '/delete/%s'", id));
    }

    @Role({ARTIST, USER})
    @PostMapping("/comments/new")
    public ResponseEntity<Iterable<SongComment>> createSongComment(MultipartHttpServletRequest request) throws IOException {
        logger.info("Song controller: enter endpoint '/comment/new");

        logger.info("Song controller: get parameter 'songComment'");
        SongComment songComment = mapper.readValue(request.getParameter("songComment"), SongComment.class);

        logger.info("Song controller: get current logged in user");
        User user = userService.getLoggedInUser();

        logger.info("Song controller: get parameter 'song'");

        Song song = mapper.readValue(request.getParameter("song"), Song.class);

        Iterable<SongComment> songComments = songService.createSongComment(songComment, user, song);

        logger.info("Song controller: exit endpoint '/comment/new'");

        return ResponseEntity.ok(songComments);
    }

    @Role({ARTIST, USER})
    @PostMapping("/comments/{id}")
    public ResponseEntity<Iterable<SongComment>> listSongComments(@PathVariable("id") long id, MultipartHttpServletRequest request) throws IOException {
        logger.info(String.format("Song controller: enter endpoint '/comments/%s'", id));

        logger.info("Song controller: get parameter 'song'");

        Song song = mapper.readValue(request.getParameter("song"), Song.class);

        Iterable<SongComment> songComments = songService.listSongComments(song);

        logger.info(String.format("Song controller: exit endpoint '/comment/%s'", id));

        return ResponseEntity.ok(songComments);
    }

    @Role({ARTIST, USER})
    @PostMapping("/like/new")
    public ResponseEntity<Integer> createSongLike(MultipartHttpServletRequest request) throws IOException {
        logger.info("Song controller: enter endpoint '/like/new'");

        ObjectMapper mapper = new ObjectMapper();

        logger.info("Song controller: get parameter 'songLike'");

        SongLike songLike = mapper.readValue(request.getParameter("songLike"), SongLike.class);

        logger.info("Song controller: get current logged in user");

        User user = userService.getLoggedInUser();

        logger.info("Song controller: get parameter 'song'");

        Song song = mapper.readValue(request.getParameter("song"), Song.class);

        int likeNumber = songService.saveSongLike(songLike, user, song);

        logger.info("Song controller: exit endpoint '/like/new'");

        return  ResponseEntity.ok(likeNumber);
    }

    @Role({ARTIST, USER})
    @PostMapping("/like/{id}")
    public ResponseEntity<int[]> countSongLikes(@PathVariable("id") long id, MultipartHttpServletRequest request) throws IOException {
        logger.info(String.format("Song controller: enter endpoint '/like/%s'", id));

        logger.info("Song controller: get parameter 'song'");

        Song song = mapper.readValue(request.getParameter("song"), Song.class);

        int[] likesNumbers = songService.countLikesDivided(song);

        logger.info(String.format("Song controller: exit endpoint '/like/%s'", id));

        return ResponseEntity.ok(likesNumbers);
    }

    @Role({ARTIST, USER})
    @PostMapping("/counter/new")
    public ResponseEntity<Integer> createSongCounter(MultipartHttpServletRequest request) throws IOException {
        logger.info("Song controller: enter endpoint '/counter/new'");

        logger.info("Song controller: get parameter 'songCounter'");

        SongCounter songCounter = mapper.readValue(request.getParameter("songCounter"), SongCounter.class);

        logger.info("Song controller: get parameter 'song'");

        Song song = mapper.readValue(request.getParameter("song"), Song.class);

        logger.info("Song controller: get current logged in user");

        User user = userService.getLoggedInUser();

        int counterNumber = songService.saveSongCounter(songCounter, song, user);

        logger.info("Song controller: exit endpoint '/counter/new'");

        return  ResponseEntity.ok(counterNumber);
    }

    @Role({ARTIST, USER})
    @PostMapping("/counter/{id}")
    public ResponseEntity<Integer> countSongCounter(@PathVariable("id") long id, MultipartHttpServletRequest request) throws IOException {
        logger.info(String.format("Song controller: enter endpoint '/counter/%s'", id));

        logger.info("Song controller: get parameter 'song'");

        Song  song = mapper.readValue(request.getParameter("song"), Song.class);

        int counterNumber = songService.countSongCounterNumber(song);

        logger.info(String.format("Song controller: exit endpoint '/counter/%s'", id));

        return ResponseEntity.ok(counterNumber);
    }
}
