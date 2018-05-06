package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.controller.AlbumController;
import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.repository.AlbumRepository;
import hu.elte.wr14yr.musicportal.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AlbumService {

    Logger logger = Logger.getLogger(AlbumService.class.getName());

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private SongService songService;

    public AlbumService() {
    }

    public Album create(Album album, User user, Set<Song> songs, Set<Genre> genres, Set<Keyword> keywords) {
        album.setUser(user);
        album.setSongs(songs);
        album.setGenres(genres);
        album.setKeywords(keywords);

        Album savedAlbum = albumRepository.save(album);
        logger.log(Level.INFO, "Album named " + album.getName() + " was saved in database.");

        for(Song song : songs) {
            song.setAlbum(album);
            song.setUser(user);
            song.setGenres(genres);
            song.setKeywords(keywords);

            songRepository.save(song);
            logger.log(Level.INFO, "Song named " + song.getTitle() + " was saved in database.");
        }
        logger.log(Level.INFO, "Album named " + album.getName() + "'s all songs saving in database was completed.");

        new File("..\\media\\" + user.getUsername() + "\\" + album.getName()).mkdir();
        new File("..\\media\\" + user.getUsername() + "\\" + album.getName() + "\\" + album.getCoverFile().getName());
        logger.log(Level.INFO, "Album's cover file was saved in its own directory.");

        return savedAlbum;
    }

    public Iterable<Album> list(User user) {
        if(user.getRole() == User.Role.ARTIST || user.getRole() == User.Role.USER) {
            return albumRepository.findAllByUser(user);
        } else {
            return Collections.emptyList();
        }
    }

    public Album find(long id) {
       Album album = albumRepository.findAlbumById(id);
       album.setSongs(songRepository.findAllByAlbum(album));

       return album;
    }

    public Album update(Album album) {
        String lastPath = albumRepository.findAlbumById(album.getId()).getCoverPath();
        if(!(album.getCoverFile().getName().equals(lastPath))) {
            try {
                Files.delete(Paths.get(lastPath));
                String path = album.getCoverFile().getName();
                new File("..\\media\\" + album.getUser().getUsername() + "\\" + album.getName() + "\\" + path);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return albumRepository.save(album);
    }

    public void deleteAllByUser(User user) {
        for(Album album : user.getAlbums()) {
            delete(album);
        }
    }

    public void delete(Album album) {
        songService.deleteAllByAlbum(album);

        albumRepository.deleteById(album.getId());
    }
}
