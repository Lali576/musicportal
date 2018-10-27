package hu.elte.wr14yr.musicportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.elte.wr14yr.musicportal.annotation.Role;
import static hu.elte.wr14yr.musicportal.model.User.Role.ARTIST;
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
import java.util.*;
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

    private ObjectMapper mapper = new ObjectMapper();

    private Logger logger = Logger.getLogger(AlbumController.class.getName());

    @Role({ARTIST})
    @PostMapping("/new")
    public ResponseEntity create(MultipartHttpServletRequest request) throws IOException {
        logger.info("Album controller: enter endpoint '/new'");

        MultipartFile multipartFile = null;
        File file = null;

        Iterator<String> iterator = request.getFileNames();

        logger.info("Album controller: get file parameter");

        while (iterator.hasNext()) {
            multipartFile = request.getFile(iterator.next());
        }

        if(multipartFile != null) {
            file = fileService.convertToFile(multipartFile);
        }

        logger.info("Album controller: get parameter 'album'");

        Album album = mapper.readValue(request.getParameter("album"), Album.class);

        logger.info("Album controller: get parameter 'genres'");

        Genre[] genresArray = mapper.readValue(request.getParameter("genres"), Genre[].class);
        List<Genre> genresList = Arrays.asList(genresArray);

        logger.info("Album controller get parameter 'albumKeywords");

        AlbumKeyword[] albumKeywordsArray = mapper.readValue(request.getParameter("albumKeywords"), AlbumKeyword[].class);
        List<AlbumKeyword> albumKeywordsList = Arrays.asList(albumKeywordsArray);

        logger.info("Album controller: ger current logged in user");

        User user = userService.getLoggedInUser();

        Album savedAlbum = albumService.create(album, user, file, genresList, albumKeywordsList);

        logger.info("Album controller exit endpoint '/new'");

        if(file != null) {
            file.delete();
        }

        return ResponseEntity.ok(savedAlbum);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> find(@PathVariable long id) {
        logger.info(String.format("Album controller: enter endpoint '/%s'", id));

        Album foundAlbum = albumService.find(id);

        logger.info(String.format("Album controller: exit endpoint '/%s'", id));

        return ResponseEntity.ok(foundAlbum);
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<Album>> listAll() {
        logger.info("Album controller: enter endpoint '/list'");

        Iterable<Album> albums = albumService.listAll();

        logger.info("Album controller: exit endpoint '/list'");

        return ResponseEntity.ok(albums);
    }

    @GetMapping("/list-first-five")
    public ResponseEntity<Iterable<Album>> listFirstFive() {
        logger.info("Album controller: enter endpoint '/first-five'");

        Iterable<Album> albums = albumService.listFirstFive();

        logger.info("Album controller: exit endpoint '/first-five'");

        return ResponseEntity.ok(albums);
    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<Iterable<Album>> listByUser(@PathVariable long id) {
        logger.info(String.format("Album controller: enter endpoint '/by-user/%s'", id));

        Iterable<Album> albums = albumService.listByUser(id);

        logger.info(String.format("Album controller: exit endpoint '/by-user/%s'", id));

        return ResponseEntity.ok(albums);
    }

    @Role({ARTIST})
    @PostMapping("/update/{id}/details")
    public ResponseEntity<Album> updateDetails(@PathVariable long id, MultipartHttpServletRequest request) throws IOException {
        logger.info(String.format("Album controller: enter endpoint '/update/%s'", id));

        logger.info("Album controller: get parameter 'album'");

        Album album = mapper.readValue(request.getParameter("album"), Album.class);

        logger.info("Album controller: get current logged in user");

        album.setUser(userService.getLoggedInUser());

        logger.info("Album controller: get parameter 'genres'");

        Genre[] genresArray = mapper.readValue(request.getParameter("genres"), Genre[].class);
        List<Genre> genresList = Arrays.asList(genresArray);

        logger.info("Album controller: get parameter 'albumKeywords'");

        AlbumKeyword[] albumKeywordsArray = mapper.readValue(request.getParameter("albumKeywords"), AlbumKeyword[].class);
        List<AlbumKeyword> albumKeywordsList = Arrays.asList(albumKeywordsArray);

        Album updatedAlbum = albumService.updateDetails(album, genresList, albumKeywordsList);

        logger.info(String.format("Album controller: exit endpoint '/update/%s'", id));

        return ResponseEntity.ok(updatedAlbum);
    }

    @Role({ARTIST})
    @PostMapping("/update/{id}/cover")
    public ResponseEntity<Album> updateCoverFile(@PathVariable("id") long id, MultipartHttpServletRequest request) throws IOException {
        logger.info(String.format("Album controller: enter endpoint '/update/%s/cover'", id));

        MultipartFile multipartFile = null;
        File file = null;

        Iterator<String> iterator = request.getFileNames();

        logger.info("Album controller: get file parameter");

        while (iterator.hasNext()) {
            multipartFile = request.getFile(iterator.next());
        }

        if (multipartFile != null) {
            file = fileService.convertToFile(multipartFile);
        }

        logger.info("Album controller: get parameter 'album'");

        Album album = mapper.readValue(request.getParameter("album"), Album.class);

        Album updatedAlbum = albumService.changeCoverFile(album, file);

        if(file != null) {
            file.delete();
        }

        logger.info(String.format("Album controller: exit endpoint '/update/%s/cover'", id));

        return ResponseEntity.ok(updatedAlbum);
    }

    @Role({ARTIST})
    @DeleteMapping("/delete/{id}")
    public @ResponseBody void delete(@PathVariable long id) {
        logger.info(String.format("Album controller: enter endpoint '/delete/%s'", id));

        Album album = albumService.find(id);
        albumService.delete(album);

        logger.info(String.format("Album controller: exit endpoint '/delete/%s'", id));
    }
}