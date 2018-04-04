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

    @Query(value = "INSERT INTO SONG_ALBUM (SONG_ID, ALBUM_ID) VALUES (:SONG_ID,:ALBUM_ID)", nativeQuery = true)
    void saveSongAlbum(@Param("SONG_ID") int songId, @Param("ALBUM_ID") int albumId);

    @Query(value = "INSERT INTO SONG_GENRE (SONG_ID, GENRE_ID) VALUES (:SONG_ID, :GENRE_ID)", nativeQuery = true)
    void saveSongGenre(@Param("SONG_ID") int songId, @Param("GENRE_ID") int genreId);

    @Query(value = "INSERT INTO SONG_KEYWORD (SONG_ID, KEYWORD_ID) VALUES (:SONG_ID, :KEYWORD_ID)", nativeQuery = true)
    void saveSongKeyword(@Param("SONG_ID") int songId, @Param("KEYWORD_ID") int keywordId);

    @Query(value = "INSERT INTO SONG_PLAYLIST (SONG_ID, PLAYLIST_ID) VALUES (:SONG_ID, :PLAYLIST_ID)", nativeQuery = true)
    void saveSongPlaylist(@Param("SONG_ID") int songId, @Param("PLAYLIST_ID") int playlistId);

    Iterable<Song> findAllByTitleContainsAllIgnoreCase(String title);

    Iterable<Song> findAllByAlbum(Album album);

    Iterable<Song> findAllByArtist(Artist artist);

    Iterable<Song> findAllByKeywordsContainsAllIgnoreCase(String keyword);

    Iterable<Song> findAllByKeywords(Keyword keyword);

    Iterable<Song> findAllByPlaylists(Playlist playlist);

    Iterable<Song> findAllByGenres(Genre genre);

    @Override
    void deleteById(Long id);

    void deleteAllByAlbum(Album album);

    @Query(value = "DELETE FROM SONG_ALBUM WHERE SONG_ID = :SONG_ID", nativeQuery = true)
    void deleteSongAlbum(@Param("SONG_ID") int songId);

    @Query(value = "DELETE FROM SONG_GENRE WHERE SONG_ID = :SONG_ID", nativeQuery = true)
    void deleteSongGenre(@Param("SONG_ID") int songId);

    @Query(value = "DELETE FROM SONG_KEYWORD WHERE SONG_ID = :SONG_ID", nativeQuery = true)
    void deleteSongKeyword(@Param("SONG_ID") int songId);

    @Query(value = "DELETE FROM SONG_PLAYLIST WHERE SONG_ID = :SONG_ID", nativeQuery = true)
    void deleteSongPlaylist(@Param("SONG_ID") int songId);
}
