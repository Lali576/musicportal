package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Playlist;
import hu.elte.wr14yr.musicportal.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PlaylistRepository extends CrudRepository<Playlist, Long> {
    @Override
    Playlist save(Playlist playlist);

    @Query(value = "INSERT INTO PLAYLIST_KEYWORD (PLAYLIST_ID, KEYWORD_ID) VALUES (:PLAYLIST_ID, :KEYWORD_ID)", nativeQuery = true)
    void savePlaylistKeyword(@Param("PLAYLIST_ID") int playlistId, @Param("KEYWORD_ID") int keywordId);

    Iterable<Playlist> findAllByNameContainsAllIgnoreCase(String name);

    Iterable<Playlist> findAllByKeywordsContainsAllIgnoreCase(String keyword);

    Iterable<Playlist> findAllByUser(User user);

    Playlist findPlaylistById(Long id);

    @Override
    void deleteById(Long id);

    void deleteAllByUser(User user);

    @Query(value = "DELETE FROM PLAYLIST_KEYWORD WHERE PLAYLIST_ID = :PLAYLIST_ID", nativeQuery = true)
    void deletePlaylistKeyword(@Param("PLAYLIST_ID") int playlistId);
}
