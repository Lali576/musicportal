package hu.elte.wr14yr.musicportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.elte.wr14yr.musicportal.model.Album;
import hu.elte.wr14yr.musicportal.model.Playlist;
import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.model.tags.AlbumTag;
import hu.elte.wr14yr.musicportal.model.tags.PlaylistTag;
import hu.elte.wr14yr.musicportal.model.tags.SongTag;
import hu.elte.wr14yr.musicportal.model.tags.UserTag;
import hu.elte.wr14yr.musicportal.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/list-by-song")
    public ResponseEntity<Iterable<SongTag>> listSongTagsBySong(MultipartHttpServletRequest request) throws IOException {
        logger.info("Tag controller: enter endpoint '/list-by-song'");

        Song song = mapper.readValue(request.getParameter("song"), Song.class);

        Iterable<SongTag> songTags = tagService.listSongTagsBySong(song);

        logger.info("Tag controller: exit endpoint '/list-by-song'");

        return ResponseEntity.ok(songTags);
    }

    @PostMapping("/list-by-playlist")
    public ResponseEntity<Iterable<PlaylistTag>> listPlaylistTagsByPlaylist(MultipartHttpServletRequest request) throws IOException {
        logger.info("Tag controller: enter endpoint '/list-by-playlist'");

        Playlist playlist = mapper.readValue(request.getParameter("playlist"), Playlist.class);

        Iterable<PlaylistTag> playlistTags = tagService.listPlaylistTagsByPlaylist(playlist);

        logger.info("Tag controller: exit endpoint '/list-by-playlist'");

        return ResponseEntity.ok(playlistTags);
    }

    @PostMapping("/list-by-user")
    public ResponseEntity<Iterable<UserTag>> listUserTagsByAlbum(MultipartHttpServletRequest request) throws IOException {
        logger.info("Tag controller: enter endpoint '/list-by-user'");

        User user = mapper.readValue(request.getParameter("user"), User.class);

        Iterable<UserTag> userTags = tagService.listUserTagsByUser(user);

        logger.info("Tag controller: exit endpoint '/list-by-user'");

        return ResponseEntity.ok(userTags);
    }


}
