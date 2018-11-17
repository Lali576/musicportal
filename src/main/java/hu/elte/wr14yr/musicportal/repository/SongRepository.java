package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SongRepository extends CrudRepository<Song, Long>{
    @Override
    Song save(Song song);

    List<Song> findAll();

    List<Song> findAllByTitleContainsAllIgnoreCase(String title);

    List<Song> findFirst5ByOrderBySongCounters();

    List<Song> findAllByAlbum(Album album);

    List<Song> findAllByUserId(long id);

    List<Song> findAllByPlaylist(Playlist playlist);

    Song findSongById(long id);

    @Override
    void deleteById(Long id);
}
