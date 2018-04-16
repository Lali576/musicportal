package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.repository.AlbumRepository;
import hu.elte.wr14yr.musicportal.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private SongService songService;

    public Album create(Album album, User user, Set<Song> songs, Set<Genre> genres, Set<Keyword> keywords) {
        //albumRepository.saveAlbumUser(album.getId(), user.getId());

        for(Song song : songs) {
            //songService.create(song, user, album, genres, keywords);
        }

        for(Genre genre : genres) {
            //albumRepository.saveAlbumGenre(album.getId(), genre.getId());
        }

        for(Keyword keyword : keywords) {
            //albumRepository.saveAlbumKeyword(album.getId(), keyword.getId());
        }

        return albumRepository.save(album);
    }

    public Iterable<Album> list() {
        return null;
    }

    public Album find(Album album) {
        return null;
    }

    public Album update(Album album) {
        return null;
    }

    public void delete() {

    }
}
