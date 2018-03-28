package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.*;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface SongRepository extends CrudRepository<Song, Long>{
    @Override
    Song save(Song song);

    Iterable<Song> findAllByTitleContaining(String title);

    Iterable<Song> findAllByAlbumIn(Album album);

    Iterable<Song> findAllByArtist(Artist artist);

    Iterable<Song> findAllByKeywordsContaining(String keyword);

    Iterable<Song> findAllByPlaylists(Playlist playlist);

    Iterable<Song> findAllByGenres(Genre genre);

    @Override
    void deleteById(Long id);
}
