package hu.elte.wr14yr.musicportal.controller;

import hu.elte.wr14yr.musicportal.model.Album;
import hu.elte.wr14yr.musicportal.service.AlbumService;
import hu.elte.wr14yr.musicportal.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private SongService songService;

    @PostMapping("/new")
    public Album create(@RequestBody Album album) {
        return null;
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Album> update(@PathVariable long id, @RequestBody Album album) {
        Album updatedAlbum = null;
        return ResponseEntity.ok(updatedAlbum);
    }

    @GetMapping("/{id}")
    public Album find(@PathVariable long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {

    }
}
