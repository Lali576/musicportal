package hu.elte.wr14yr.musicportal.controller;


import hu.elte.wr14yr.musicportal.annotation.Role;
import static hu.elte.wr14yr.musicportal.model.User.Role.ARTIST;
import static hu.elte.wr14yr.musicportal.model.User.Role.USER;
import static hu.elte.wr14yr.musicportal.model.User.Role.GUEST;
import hu.elte.wr14yr.musicportal.model.Album;
import hu.elte.wr14yr.musicportal.model.Playlist;
import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.service.AlbumService;
import hu.elte.wr14yr.musicportal.service.PlaylistService;
import hu.elte.wr14yr.musicportal.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SongService songService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private PlaylistService playlistService;

    @Role({ARTIST, GUEST, USER})
    @GetMapping("/albums/{name}")
    public ResponseEntity<Iterable<Album>> findAlbumByName(@PathVariable String name) {
        Iterable<Album> foundAlbums = null;
        return ResponseEntity.ok(foundAlbums);
    }

    @Role({ARTIST, GUEST, USER})
    @GetMapping("/albums/keyword/{keyword}")
    public ResponseEntity<Iterable<Album>> findAlbumByKeyword(@PathVariable String keyword) {
        Iterable<Album> foundAlbums = null;
        return ResponseEntity.ok(foundAlbums);
    }

    @Role({ARTIST, GUEST, USER})
    @GetMapping("/songs/{title}")
    public ResponseEntity<Iterable<Song>> findSongByTitle(@PathVariable String title) {
        Iterable<Song> foundSongs = null;
        return ResponseEntity.ok(foundSongs);
    }

    @Role({ARTIST, GUEST, USER})
    @GetMapping("/songs/keyword/{keyword}")
    public ResponseEntity<Iterable<Song>> findSongByKeyword(@PathVariable String keyword) {
        Iterable<Song> foundSongs = null;
        return ResponseEntity.ok(foundSongs);
    }

    @Role({ARTIST, GUEST, USER})
    @GetMapping("/playlists/{name}")
    public ResponseEntity<Iterable<Playlist>> findPlaylistByName(@PathVariable String name) {
        Iterable<Playlist> foundPlaylists = null;
        return ResponseEntity.ok(foundPlaylists);
    }

    @Role({ARTIST, GUEST, USER})
    @GetMapping("/playlists/keyword/{keyword}")
    public ResponseEntity<Iterable<Playlist>> findPlaylistByKeyword(@PathVariable String keyword) {
        Iterable<Playlist> foundPlaylists = null;
        return ResponseEntity.ok(foundPlaylists);
    }
}
