package hu.elte.wr14yr.musicportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.elte.wr14yr.musicportal.model.Album;
import hu.elte.wr14yr.musicportal.model.Playlist;
import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.model.tags.AlbumTag;
import hu.elte.wr14yr.musicportal.model.tags.PlaylistTag;
import hu.elte.wr14yr.musicportal.model.tags.UserTag;
import hu.elte.wr14yr.musicportal.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    private ObjectMapper mapper = new ObjectMapper();

    private Logger logger = Logger.getLogger(TagController.class.getName());

    @PostMapping("/list-by-album")
    public ResponseEntity<Iterable<AlbumTag>> listAlbumTagsByAlbum(MultipartHttpServletRequest request) throws IOException {
        logger.info("Tag controller: enter endpoint '/list-by-album'");

        Album album = mapper.readValue(request.getParameter("album"), Album.class);

        Iterable<AlbumTag> albumTags = tagService.listAlbumTagsByAlbum(album);

        logger.info("Tag controller: exit endpoint '/list-by-album'");

        return ResponseEntity.ok(albumTags);
    }

    @PostMapping("/list-by-playlist")
    public ResponseEntity<Iterable<PlaylistTag>> listPlaylistTagsByPlaylist(MultipartHttpServletRequest request) throws IOException {
        logger.info("Tag controller: enter endpoint '/list-by-playlist'");

        Playlist playlist = mapper.readValue(request.getParameter("playlist"), Playlist.class);

        Iterable<PlaylistTag> playlistTags = tagService.listPlaylistTagsByPlaylist(playlist);

        logger.info("Tag controller: exit endpoint '/list-by-playlist'");

        return ResponseEntity.ok(playlistTags);
    }

    @GetMapping("/list-by-user/{id}")
    public ResponseEntity<Iterable<UserTag>> listUserTagsByAlbum(@PathVariable long id) {
        logger.info("Tag controller: enter endpoint '/list-by-user'");

        Iterable<UserTag> userTags = tagService.listUserTagsByUser(id);

        logger.info("Tag controller: exit endpoint '/list-by-user'");

        return ResponseEntity.ok(userTags);
    }


}
