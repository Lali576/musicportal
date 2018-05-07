package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Genre;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    Set<Genre> findAll();
    Set<Genre> findAllByNameContainsAllIgnoreCase(String name);
}