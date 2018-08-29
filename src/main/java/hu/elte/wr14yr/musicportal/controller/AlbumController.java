package hu.elte.wr14yr.musicportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.elte.wr14yr.musicportal.annotation.Role;
import static hu.elte.wr14yr.musicportal.model.User.Role.*;
import hu.elte.wr14yr.musicportal.model.*;

import hu.elte.wr14yr.musicportal.model.keywords.AlbumKeyword;
import hu.elte.wr14yr.musicportal.service.AlbumService;
import hu.elte.wr14yr.musicportal.service.FileService;
import hu.elte.wr14yr.musicportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    private Logger logger = Logger.getLogger(AlbumController.class.getName());

    @Role({ARTIST})
    @PostMapping("/new")
    public ResponseEntity<Album> create(MultipartHttpServletRequest request) throws IOException, URISyntaxException {
        logger.log(Level.INFO, "Entrance: endpoint '/new'");
        MultipartFile multipartFile = null;

        Iterator<String> iterator = request.getFileNames();

        logger.log(Level.INFO, "Get file parameter");
        while (iterator.hasNext()) {
            multipartFile = request.getFile(iterator.next());
        }

        File file = fileService.convertToFile(multipartFile);

        ObjectMapper mapper = new ObjectMapper();

        logger.log(Level.INFO, "Get parameter 'album'");
        Album album = mapper.readValue(request.getParameter("album").toString(), Album.class);

        logger.log(Level.INFO, "Get parameter 'genres'");
        Genre[] genresArray = mapper.readValue(request.getParameter("genres").toString(), Genre[].class);
        List<Genre> genresList = Arrays.asList(genresArray);

        logger.log(Level.INFO, "Get parameter 'keywords'");
        AlbumKeyword[] albumKeywordsArray = mapper.readValue(request.getParameter("keywords").toString(), AlbumKeyword[].class);
        List<AlbumKeyword> albumKeywordsList = Arrays.asList(albumKeywordsArray);

        logger.log(Level.INFO, "Get current logged in user");
        User user = userService.getLoggedInUser();

        Album savedAlbum = albumService.create(album, user, file, genresList, albumKeywordsList);
        logger.log(Level.INFO, "Exit: endpoint '/new'");

        return ResponseEntity.ok(savedAlbum);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> find(@PathVariable long id) {
        logger.log(Level.INFO, "Entrance: endpoint '/" + id + "'");
        Album foundAlbum = albumService.find(id);
        logger.log(Level.INFO, "Exit: endpoint '/" + id + "'");

        return ResponseEntity.ok(foundAlbum);
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<Album>> listAll() {
        logger.log(Level.INFO, "Entrance: endpoint '/list'");
        Iterable<Album> albums = albumService.listAll();
        logger.log(Level.INFO, "Exit: endpoint '/list'");

        return ResponseEntity.ok(albums);
    }

    @GetMapping
    public ResponseEntity<Iterable<Album>> list() {
        logger.log(Level.INFO, "Entrance: endpoint '/'");
        Iterable<Album> albums = albumService.list(userService.getLoggedInUser());
        logger.log(Level.INFO, "Exit: endpoint '/'");

        return ResponseEntity.ok(albums);
    }

    @Role({ARTIST})
    @PutMapping("/update/{id}/details")
    public ResponseEntity<Album> updateDetails(@PathVariable long id, MultipartHttpServletRequest request) throws IOException, URISyntaxException {
        logger.log(Level.INFO, "Entrance: endpoint '/update/" + id + "'");
        ObjectMapper mapper = new ObjectMapper();

        logger.log(Level.INFO, "Get parameter 'album'");
        Album album = mapper.readValue(request.getParameter("album").toString(), Album.class);

        logger.log(Level.INFO, "Get current logged in user");
        album.setUser(userService.getLoggedInUser());

        logger.log(Level.INFO, "Get parameter 'genres'");
        Genre[] genresArray = mapper.readValue(request.getParameter("genres").toString(), Genre[].class);
        List<Genre> genresList = Arrays.asList(genresArray);

        logger.log(Level.INFO, "Get parameter 'keywords'");
        AlbumKeyword[] albumKeywordsArray = mapper.readValue(request.getParameter("keywords").toString(), AlbumKeyword[].class);
        List<AlbumKeyword> albumKeywordsList = Arrays.asList(albumKeywordsArray);

        Album updatedAlbum = albumService.updateDetails(album, genresList, albumKeywordsList);
        logger.log(Level.INFO, "Exit: endpoint '/update/" + id + "'");

        return ResponseEntity.ok(updatedAlbum);
    }

    @Role({ARTIST})
    @PutMapping("/update/{id}/cover")
    public ResponseEntity<Album> updateCoverFile(@PathVariable("id") long id, MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint '/update/" + id + "/cover'");
        MultipartFile multipartFile = null;

        Iterator<String> iterator = request.getFileNames();

        logger.log(Level.INFO, "Get file parameter");
        while (iterator.hasNext()) {
            multipartFile = request.getFile(iterator.next());
        }

        File file = fileService.convertToFile(multipartFile);

        ObjectMapper mapper = new ObjectMapper();

        logger.log(Level.INFO, "Get parameter 'album'");
        Album album = mapper.readValue(request.getParameter("album").toString(), Album.class);

        Album updatedAlbum = albumService.changeCoverFile(album, file);
        logger.log(Level.INFO, "Exit: endpoint '/update/" + id + "/cover'");

        return ResponseEntity.ok(updatedAlbum);
    }

    @Role({ARTIST})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable long id, MultipartHttpServletRequest request) throws IOException {
        logger.log(Level.INFO, "Entrance: endpoint '/delete/" + id + "'");
        ObjectMapper mapper = new ObjectMapper();

        logger.log(Level.INFO, "Get parameter 'album'");
        Album album = mapper.readValue(request.getParameter("album").toString(), Album.class);

        albumService.delete(album);
        logger.log(Level.INFO, "Exit: endpoint '/delete/" + id + "'");

        return ResponseEntity.status(204).build();
    }
}
