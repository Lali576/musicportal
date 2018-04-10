package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Album;
import hu.elte.wr14yr.musicportal.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AlbumRepository extends CrudRepository<Album, Long> {
    @Override
    Album save(Album album);

    @Query(value = "INSERT INTO ALBUM_KEYWORD (ALBUM_ID, KEYWORD_ID) VALUES (:ALBUM_ID, :KEYWORD_ID)", nativeQuery = true)
    void saveAlbumKeyword(@Param("ALBUM_ID") long albumId, @Param("KEYWORD_ID") long keywordId);

    @Query(value = "INSERT INTO ALBUM_USER (USER_ID, ALBUM_ID) VALUES (:USER_ID, :ALBUM_ID)", nativeQuery = true)
    void saveAlbumUser(@Param("USER_ID") long userId, @Param("ALBUM_ID") long albumId);

    Iterable<Album> findAllByNameContainsAllIgnoreCase(String name);

    Iterable<Album> findAllByKeywordsContainsAllIgnoreCase(String keyword);

    Iterable<Album> findAllByUser(User user);

    Album findAlbumById(Long id);

    @Override
    void deleteById(Long id);

    void deleteAllByUser(User user);

    @Query(value = "DELETE FROM ALBUM_KEYWORD WHERE ALBUM_ID = :ALBUM_ID", nativeQuery = true)
    void deleteAlbumKeyword(@Param("ALBUM_ID") long albumId);

    @Query(value = "DELETE FROM ALBUM_USER WHERE ALBUM_ID = :ALBUM_ID", nativeQuery = true)
    void deleteAlbumUser(@Param("") long id);
}
