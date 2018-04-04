package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Artist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
    @Override
    Artist save(Artist album);

    @Query(value = "INSERT INTO ARTIST_KEYWORD (artistId, KEYWORD_ID) VALUES (:ARTIST_ID, :KEYWORD_ID)", nativeQuery = true)
    void saveArtistKeyword(@Param("ARTIST_ID") int artistId, @Param("KEYWORD_ID") int keywordId);

    Iterable<Artist> findAllByArtistNameContainsAllIgnoreCase(String artistName);

    Iterable<Artist> findAllByKeywordsContainsAllIgnoreCase(String keyword);

    Artist findArtistById(Long id);

    @Override
    void deleteById(Long id);

    @Query(value = "DELETE FROM ARTIST_KEYWORD WHERE ARTIST_ID = :ARTIST_ID", nativeQuery = true)
    void deleteArtistKeyword(@Param("ARTIST_ID") int artistId);
}
