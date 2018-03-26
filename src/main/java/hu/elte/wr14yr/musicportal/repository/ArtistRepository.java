package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Artist;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
    @Override
    Artist save(Artist album);

    Optional<Set<Artist>> findByArtistNameContaining(String artistName);

    Optional<Set<Artist>> findByKeywordsContaining(String keyword);

    Artist findArtistById(Long id);

    @Override
    void deleteById(Long id);
}
