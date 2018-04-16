package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Album;
import hu.elte.wr14yr.musicportal.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AlbumRepository extends CrudRepository<Album, Long> {
    @Override
    Album save(Album album);

    @Query(value = "INSERT INTO ALBUM_GENRE (ALBUM_ID, GENRE_ID) VALUES (:ALBUM_ID, :GENRE_ID)", nativeQuery = true)
    void saveAlbumGenre(@Param("ALBUM_ID") long albumId, @Param("GENRE_ID") long genreId);

    @Query(value = "INSERT INTO ALBUM_KEYWORD (ALBUM_ID, KEYWORD_ID) VALUES (:ALBUM_ID, :KEYWORD_ID)", nativeQuery = true)
    void saveAlbumKeyword(@Param("ALBUM_ID") long albumId, @Param("KEYWORD_ID") long keywordId);

    Iterable<Album> findAllByNameContainsAllIgnoreCase(String name);

    Iterable<Album> findAllByUser(User user);

    Album findAlbumById(Long id);

    @Override
    void deleteById(Long id);

    @Query(value = "DELETE FROM ALBUM_GENRE WHERE ALBUM_ID = :ALBUM_ID", nativeQuery = true)
    void deleteAlbumGenre(@Param("ALBUM_ID") long albumId);

    @Query(value = "DELETE FROM ALBUM_KEYWORD WHERE ALBUM_ID = :ALBUM_ID", nativeQuery = true)
    void deleteAlbumKeyword(@Param("ALBUM_ID") long albumId);
}
