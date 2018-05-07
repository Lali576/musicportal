package hu.elte.wr14yr.musicportal.controller;

import hu.elte.wr14yr.musicportal.model.Genre;
import hu.elte.wr14yr.musicportal.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/genre")
public class GenreController {
    @Autowired
    private GenreRepository genreRepository;

    @GetMapping("/list")
    public ResponseEntity<Iterable<Genre>> list() {
        Iterable<Genre> genres = genreRepository.findAll();
        return ResponseEntity.ok(genres);
    }
}
