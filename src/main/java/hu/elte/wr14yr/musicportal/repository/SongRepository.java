package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.annotation.security.PermitAll;
import java.util.Optional;
import java.util.Set;

public interface SongRepository extends CrudRepository<Song, Long>{
    @Override
    Song save(Song song);

    /*
    @Query(value = "INSERT INTO SONG_GENRE (SONG_ID, GENRE_ID) VALUES (:SONG_ID, :GENRE_ID)", nativeQuery = true)
    void saveSongGenre(@Param("SONG_ID") long songId, @Param("GENRE_ID") long genreId);

    @Query(value = "INSERT INTO SONG_KEYWORD (SONG_ID, KEYWORD_ID) VALUES (:SONG_ID, :KEYWORD_ID)", nativeQuery = true)
    void saveSongKeyword(@Param("SONG_ID") long songId, @Param("KEYWORD_ID") long keywordId);

    @Query(value = "INSERT INTO SONG_PLAYLIST (SONG_ID, PLAYLIST_ID) VALUES (:SONG_ID, :PLAYLIST_ID)", nativeQuery = true)
    void saveSongPlaylist(@Param("SONG_ID") long songId, @Param("PLAYLIST_ID") long playlistId);
    */

    Set<Song> findAllByTitleContainsAllIgnoreCase(String title);

    Set<Song> findAllByAlbum(Album album);

    Set<Song> findAllByUser(User user);

    Set<Song> findAllByPlaylists(Playlist playlist);

    Set<Song> findAllByGenres(Genre genre);

    Set<Song> findAllByKeywords(Keyword keyword);

    Song findSongById(long id);

    @Override
    void deleteById(Long id);

    /*
    @Query(value = "DELETE FROM SONG_GENRE WHERE SONG_ID = :SONG_ID", nativeQuery = true)
    void deleteSongGenre(@Param("SONG_ID") long songId);

    @Query(value = "DELETE FROM SONG_KEYWORD WHERE SONG_ID = :SONG_ID", nativeQuery = true)
    void deleteSongKeyword(@Param("SONG_ID") long songId);

    @Query(value = "DELETE FROM SONG_PLAYLIST WHERE SONG_ID = :SONG_ID", nativeQuery = true)
    void deleteSongPlaylist(@Param("SONG_ID") long songId);
    */
}
