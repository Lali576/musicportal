package hu.elte.wr14yr.musicportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.elte.wr14yr.musicportal.annotation.Role;
import static hu.elte.wr14yr.musicportal.model.User.Role.*;
import hu.elte.wr14yr.musicportal.model.*;

import hu.elte.wr14yr.musicportal.service.AlbumService;
import hu.elte.wr14yr.musicportal.service.SongService;
import hu.elte.wr14yr.musicportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/album")
public class AlbumController {

    private MultipartFile multipartFile = null;
    private String albumCoverFilePath = null;
    private final String assetFolderPath = "C:\\MusicPortal\\src\\main\\frontend\\src\\assets";

    @Autowired
    private AlbumService albumService;

    @Autowired
    private UserService userService;

    @Role({ARTIST})
    @GetMapping
    public ResponseEntity<Iterable<Album>> list() {
        Iterable<Album> albums = albumService.list(userService.getLoggedInUser());
        return ResponseEntity.ok(albums);
    }

    @PostMapping("/file")
    public ResponseEntity file(MultipartHttpServletRequest request) throws IOException, URISyntaxException {
        Iterator<String> iterator = request.getFileNames();

        while (iterator.hasNext()) {
            multipartFile = request.getFile(iterator.next());
        }

        String albumName = multipartFile.getName();
        File resourceDir = new File(assetFolderPath+"\\media\\"+userService.getLoggedInUser().getUsername(), albumName);
        if (!resourceDir.exists())
            resourceDir.mkdirs();

        File albumCoverFile = new File(resourceDir, multipartFile.getOriginalFilename());
        if (!albumCoverFile.exists()) {
            albumCoverFile.createNewFile();
        }


        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(albumCoverFile);
            outputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null)
                outputStream.close();
        }

        albumCoverFilePath = "\\assets\\media\\"+userService.getLoggedInUser().getUsername()+"\\"+albumName+"\\"+albumCoverFile.getName();

        return ResponseEntity.status(200).build();
    }

    @Role({ARTIST})
    @PostMapping("/new")
    public ResponseEntity<Album> create(@RequestBody Map<String, Object> params) throws IOException, URISyntaxException {
        ObjectMapper mapper = new ObjectMapper();
        Album album = mapper.readValue(params.get("album").toString(), Album.class);
        User user = userService.getLoggedInUser();
        Album savedAlbum = albumService.create(album, user, null);
        multipartFile = null;
        albumCoverFilePath = null;
        return ResponseEntity.ok(savedAlbum);
    }

    @Role({ARTIST})
    @PutMapping("/edit/{id}")
    public ResponseEntity<Album> update(@PathVariable long id, @RequestBody Album album) throws IOException, URISyntaxException {
        album.setUser(userService.getLoggedInUser());
        Album updatedAlbum = albumService.update(album, null);
        return ResponseEntity.ok(updatedAlbum);
    }

    @Role({ARTIST, USER, GUEST})
    @GetMapping("/{id}")
    public ResponseEntity<Album> find(@PathVariable long id) {
        Album foundAlbum = albumService.find(id);
        return ResponseEntity.ok(foundAlbum);
    }

    @Role({ARTIST})
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) throws URISyntaxException, IOException {
        Album album = albumService.find(id);
        albumService.delete(album);

        return ResponseEntity.ok().build();
    }
}
