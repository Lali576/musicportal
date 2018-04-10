package hu.elte.wr14yr.musicportal.controller;

import hu.elte.wr14yr.musicportal.model.Playlist;
import hu.elte.wr14yr.musicportal.service.PlaylistService;
import hu.elte.wr14yr.musicportal.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private SongService songService;

    @PostMapping("/new")
    public void create(@RequestBody Playlist playlist) {

    }

    @GetMapping("/{id}")
    public Playlist find(@PathVariable long id) {
        return null;
    }

    @PutMapping("/edit/{id}")
    public Playlist update(@PathVariable long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {

    }
}
