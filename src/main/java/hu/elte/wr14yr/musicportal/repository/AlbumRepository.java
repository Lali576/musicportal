package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Album;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AlbumRepository extends CrudRepository<Album, Long> {
    @Override
    Album save(Album album);

    @Query(value = "INSERT INTO ALBUM_KEYWORD (ALBUM_ID, KEYWORD_ID) VALUES (:ALBUM_ID, :KEYWORD_ID)", nativeQuery = true)
    void saveAlbumKeyword(@Param("ALBUM_ID") int albumId, @Param("KEYWORD_ID") int keywordId);

    Iterable<Album> findAllByNameContainsAllIgnoreCase(String name);

    Iterable<Album> findAllByKeywordsContainsAllIgnoreCase(String keyword);

    Album findAlbumById(Long id);

    @Override
    void deleteById(Long id);

    @Query(value = "DELETE FROM ALBUM_KEYWORD WHERE ALBUM_ID = :ALBUM_ID", nativeQuery = true)
    void deleteAlbumKeyword(@Param("ALBUM_ID") int albumId);
}
