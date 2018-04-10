package hu.elte.wr14yr.musicportal.controller;

import hu.elte.wr14yr.musicportal.model.Album;
import hu.elte.wr14yr.musicportal.model.Playlist;
import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.service.AlbumService;
import hu.elte.wr14yr.musicportal.service.PlaylistService;
import hu.elte.wr14yr.musicportal.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SongService songService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private PlaylistService playlistService;

    @GetMapping("/albums/{name}")
    public List<Album> findAlbumByName(@PathVariable String name) {
        return null;
    }

    @GetMapping("/albums/keyword/{keyword}")
    public List<Album> findAlbumByKeyword(@PathVariable String keyword) {
        return null;
    }

    @GetMapping("/songs/{title}")
    public List<Song> findSongByTitle(@PathVariable String title) {
        return null;
    }

    @GetMapping("/songs/keyword/{keyword}")
    public List<Song> findSongByKeyword(@PathVariable String keyword) {
        return null;
    }

    @GetMapping("/playlists/{name}")
    public List<Playlist> findPlaylistByName(@PathVariable String name) {
        return null;
    }

    @GetMapping("/playlists/keyword/{keyword}")
    public List<Playlist> findPlaylistByKeyword(@PathVariable String keyword) {
        return null;
    }
}
