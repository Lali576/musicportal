package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Playlist;
import hu.elte.wr14yr.musicportal.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PlaylistRepository extends CrudRepository<Playlist, Long> {
    @Override
    Playlist save(Playlist playlist);

    @Query(value = "INSERT INTO PLAYLISTKEYWORD (playlistId, keywordId) VALUES (:playlistId, :keywordId)", nativeQuery = true)
    void savePlaylistKeyword(@Param("playlistId") int playlistId, @Param("keywordId") int keywordId);

    Iterable<Playlist> findAllByNameContainsAllIgnoreCase(String name);

    Iterable<Playlist> findAllByUser(User user);

    Playlist findPlaylistById(Long id);

    Iterable<Playlist> findAllByKeywordsContainsAllIgnoreCase(String keyword);

    @Override
    void deleteById(Long id);

    void deleteAllByUser(User user);

    @Query(value = "DELETE FROM PLAYLISTKEYWORD WHERE playlistId = :playlistId", nativeQuery = true)
    void deletePlaylistKeyword(@Param("playlistId") int playlistId);
}
