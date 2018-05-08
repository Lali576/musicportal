package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Keyword;
import hu.elte.wr14yr.musicportal.model.Playlist;
import hu.elte.wr14yr.musicportal.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlaylistRepository extends CrudRepository<Playlist, Long> {
    @Override
    Playlist save(Playlist playlist);

    /*
    @Query(value = "INSERT INTO PLAYLIST_KEYWORD (PLAYLIST_ID, KEYWORD_ID) VALUES (:PLAYLIST_ID, :KEYWORD_ID)", nativeQuery = true)
    void savePlaylistKeyword(@Param("PLAYLIST_ID") long playlistId, @Param("KEYWORD_ID") long keywordId);
    */

    List<Playlist> findAllByNameContainsAllIgnoreCase(String name);

    List<Playlist> findAllByKeywords(Keyword keyword);

    List<Playlist> findAllByUser(User user);

    Playlist findPlaylistById(Long id);

    @Override
    void deleteById(Long id);

    /*
    @Query(value = "DELETE FROM PLAYLIST_KEYWORD WHERE PLAYLIST_ID = :PLAYLIST_ID", nativeQuery = true)
    void deletePlaylistKeyword(@Param("PLAYLIST_ID") long playlistId);
    */
}
