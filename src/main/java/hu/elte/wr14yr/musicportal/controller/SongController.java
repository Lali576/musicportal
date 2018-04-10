package hu.elte.wr14yr.musicportal.controller;

import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.service.SongCommentService;
import hu.elte.wr14yr.musicportal.service.SongCounterService;
import hu.elte.wr14yr.musicportal.service.SongLikeService;
import hu.elte.wr14yr.musicportal.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/song")
public class SongController {

    @Autowired
    private SongService songService;

    @Autowired
    private SongLikeService songLikeService;

    @Autowired
    private SongCommentService songCommentService;

    @Autowired
    private SongCounterService songCounterService;

    @PostMapping("/new")
    public Song create(@RequestBody Song song) {
        return null;
    }

    @PutMapping("/edit/{id}")
    public Song update(@PathVariable long id) {
        return null;
    }

    @GetMapping("/{id}")
    public Song find(@PathVariable long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {

    }
}
