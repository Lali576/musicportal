package hu.elte.wr14yr.musicportal.controller;

import hu.elte.wr14yr.musicportal.model.Genre;
import hu.elte.wr14yr.musicportal.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/genre")
public class GenreController {
    @Autowired
    private GenreRepository genreRepository;

    private Logger logger = Logger.getLogger(GenreRepository.class.getName());

    @GetMapping("/list")
    public ResponseEntity<Iterable<Genre>> list() {
        logger.log(Level.INFO, "Entrance: endpoint '/list'");
        Iterable<Genre> genres = genreRepository.findAll();
        logger.log(Level.INFO, "Exit: endpoint '/list'");

        return ResponseEntity.ok(genres);
    }
}
