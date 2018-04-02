package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Album;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AlbumRepository extends CrudRepository<Album, Long> {
    @Override
    Album save(Album album);

    @Query(value = "INSERT INTO ALBUMKEYWORD (albumId, keywordId) VALUES (:albumId, :keywordId)", nativeQuery = true)
    void saveAlbumKeyword(@Param("albumId") int albumId, @Param("keywordId") int keywordId);

    Iterable<Album> findAllByNameContainsAllIgnoreCase(String name);

    Iterable<Album> findAllByKeywordsContainsAllIgnoreCase(String keyword);

    Album findAlbumById(Long id);

    @Override
    void deleteById(Long id);

    @Query(value = "DELETE FROM ALBUMKEYWORD WHERE albumId = :albumId", nativeQuery = true)
    void deleteAlbumKeyword(@Param("albumId") int albumId);
}
