package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Artist;
import org.springframework.data.repository.CrudRepository;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
    @Override
    Artist save(Artist album);

    Iterable<Artist> findAllByArtistNameContainsAllIgnoreCase(String artistName);

    Iterable<Artist> findAllByKeywordsContainsAllIgnoreCase(String keyword);

    Artist findArtistById(Long id);

    @Override
    void deleteById(Long id);
}
