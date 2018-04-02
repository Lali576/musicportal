package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Artist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
    @Override
    Artist save(Artist album);

    @Query(value = "INSERT INTO ARTISTKEYWORD (artistId, keywordId) VALUES (:artistId, :keywordId)", nativeQuery = true)
    void saveArtistKeyword(@Param("artistId") int artistId, @Param("keywordId") int keywordId);

    Iterable<Artist> findAllByArtistNameContainsAllIgnoreCase(String artistName);

    Iterable<Artist> findAllByKeywordsContainsAllIgnoreCase(String keyword);

    Artist findArtistById(Long id);

    @Override
    void deleteById(Long id);

    @Query(value = "DELETE FROM ARTISTKEYWORD WHERE artistId = :artistId", nativeQuery = true)
    void deleteArtistKeyword(@Param("artistId") int artistId);
}
