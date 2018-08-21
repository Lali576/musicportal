package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SongRepository extends CrudRepository<Song, Long>{
    @Override
    Song save(Song song);

    List<Song> findAllByTitleContainsAllIgnoreCase(String title);

    List<Song> findAllByAlbum(Album album);

    List<Song> findAllByUser(User user);

    List<Song> findAllByPlaylist(Playlist playlist);

    List<Song> findAllByGenres(Genre genre);

    List<Song> findAllByKeywords(Keyword keyword);

    Song findSongById(long id);

    @Override
    void deleteById(Long id);
}
