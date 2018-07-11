package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.controller.AlbumController;
import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.repository.AlbumRepository;
import hu.elte.wr14yr.musicportal.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private SongService songService;

    public AlbumService() {
    }

    public Album create(Album album, User user, List<Genre> genres, List<Keyword> keywords) throws URISyntaxException, IOException {
        album.setUser(user);
        album.setGenres(genres);
        album.setKeywords(keywords);

        /*
        String resourceDir = getClass().getClassLoader().getResource("\\media").toURI().getPath();
        File albumDir = new File(resourceDir+"\\"+user.getUsername()+"\\"+album.getName());
        if(!albumDir.exists()) {
            albumDir.mkdir();
        }
        File albumCoverFile = new File(albumDir.getPath()+"\\"+album.getCoverFile().getName());
        albumCoverFile.createNewFile();
        */

        //album.setCoverPath(albumCoverFile.getPath());
        album.setCoverPath("");

        Album savedAlbum = albumRepository.save(album);

        return savedAlbum;
    }

    public Iterable<Album> list(User user) {
        if(user.getRole() == User.Role.ARTIST) {
            return albumRepository.findAllByUser(user);
        } else {
            return Collections.emptyList();
        }
    }

    public Album find(long id) {
       Album album = albumRepository.findAlbumById(id);

       return album;
    }

    public Album update(Album album) throws URISyntaxException, IOException {
        String lastPath = albumRepository.findAlbumById(album.getId()).getCoverPath();
        String lastPathName = new File(lastPath).getName();

        /*if(!(album.getCoverFile().getName().equals(lastPathName))) {
            try {
                Files.delete(Paths.get(lastPath));
            } catch (IOException e) {
                e.printStackTrace();
            }

            String resourceDir = getClass().getClassLoader().getResource("\\media").toURI().getPath();
            File albumDir = new File(resourceDir+"\\"+album.getUser().getUsername()+"\\"+album.getName());
            File albumCoverFile = new File(albumDir.getPath()+"\\"+album.getCoverFile().getName());
            albumCoverFile.createNewFile();
            album.setCoverPath(albumCoverFile.getPath());
        } else {
            album.setCoverPath(lastPath);
        }*/

        album.setCoverPath("");
        songService.deleteAllByAlbum(album);

        return albumRepository.save(album);
    }

    public void deleteAllByUser(User user) throws URISyntaxException, IOException {
        for(Album album : user.getAlbums()) {
            delete(album);
        }
    }

    public void delete(Album album) throws URISyntaxException, IOException {
        songService.deleteAllByAlbum(album);
        //String resourceDir = getClass().getClassLoader().getResource("\\media").toURI().getPath();
        //File albumDir = new File(resourceDir+"\\"+album.getUser().getUsername()+"\\"+album.getName());
        //Files.delete(Paths.get(albumDir.toString()));

        albumRepository.deleteById(album.getId());
    }
}
