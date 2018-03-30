package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface SongRepository extends CrudRepository<Song, Long>{
    @Override
    Song save(Song song);

    Iterable<Song> findAllByTitleContainsAllIgnoreCase(String title);

    Iterable<Song> findAllByAlbumIn(Album album);

    Iterable<Song> findAllByArtist(Artist artist);

    Iterable<Song> findAllByKeywordsContainsAllIgnoreCase(String keyword);

    Iterable<Song> findAllByPlaylists(Playlist playlist);

    Iterable<Song> findAllByGenres(Genre genre);

    @Override
    void deleteById(Long id);

    void deleteAllByAlbum(Album album);

    @Query(value = "INSERT INTO SONGALBUM (songId, albumId) VALUES (:songId,:albumId)", nativeQuery = true)
    void saveSongAlbum(@Param("songId") int songId, @Param("albumId") int albumId);

    @Query(value = "DELETE FROM SONGALBUM WHERE songId = :songId AND albumId = :albumId", nativeQuery = true)
    void deleteSongAlbum(@Param("songId") int songId, @Param("albumId") int albumId);

    @Query(value = "INSERT INTO SONGGENRE (songId, genreId) VALUES (:songId, :genreId)", nativeQuery = true)
    void saveSongGenre(@Param("songId") int songId, @Param("genreId") int genreId);

    @Query(value = "DELETE FROM SONGGENRE WHERE songId = :songId AND genreId = :genreId", nativeQuery = true)
    void deleteSongGenre(@Param("songId") int songId, @Param("genreId") int genreId);
}
