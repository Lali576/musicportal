package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.*;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface SongRepository extends CrudRepository<Song, Long>{
    @Override
    Song save(Song song);

    Optional<Set<Song>> findAllByTitleContaining(String title);

    Optional<Set<Song>> findAllByAlbumIn(Album album);

    Optional<Set<Song>> findAllByArtist(Artist artist);

    Optional<Set<Song>> findAllByKeywordsContaining(String keyword);

    Optional<Set<Song>> findAllByPlaylists(Playlist playlist);

    Optional<Set<Song>> findAllByGenres(Genre genre);

    @Override
    void deleteById(Long id);
}
