package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.controller.AlbumController;
import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.repository.AlbumRepository;
import hu.elte.wr14yr.musicportal.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private SongService songService;

    public Album create(Album album, User user, Set<Song> songs, Set<Genre> genres, Set<Keyword> keywords) {
        album.setUser(user);
        album.setSongs(songs);
        album.setGenres(genres);
        album.setKeywords(keywords);

        Album savedAlbum = albumRepository.save(album);

        for(Song song : songs) {
            song.setAlbum(album);
            song.setUser(user);
            song.setGenres(genres);
            song.setKeywords(keywords);

            songRepository.save(song);
        }

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
