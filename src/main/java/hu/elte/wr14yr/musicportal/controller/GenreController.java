package hu.elte.wr14yr.musicportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Mult;
import hu.elte.wr14yr.musicportal.model.Album;
import hu.elte.wr14yr.musicportal.model.Genre;
import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/genre")
public class GenreController {
    @Autowired
    private GenreRepository genreRepository;

    private ObjectMapper mapper = new ObjectMapper();

    private Logger logger = Logger.getLogger(GenreController.class.getName());

    @GetMapping("/list")
    public ResponseEntity<Iterable<Genre>> list() {
        logger.info("Genre controller: enter endpoint '/list'");

        Iterable<Genre> genres = genreRepository.findAll();

        logger.info("Genre controller: exit endpoint '/list'");

        return ResponseEntity.ok(genres);
    }

    @PostMapping("/list-by-album")
    public ResponseEntity<Iterable<Genre>> listByAlbum(MultipartHttpServletRequest request) throws IOException {
        logger.info("Genre controller: enter endpoint '/list-by-album'");

        Album album = mapper.readValue(request.getParameter("album"), Album.class);

        Iterable<Genre> genres = genreRepository.findAllByAlbums(album);

        logger.info("Genre controller: exit endpoint '/list-by-album'");

        return ResponseEntity.ok(genres);
    }
}
