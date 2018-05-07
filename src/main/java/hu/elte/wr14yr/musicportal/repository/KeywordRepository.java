package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Keyword;
import org.springframework.data.repository.CrudRepository;

import java.security.Key;
import java.util.Set;

public interface KeywordRepository extends CrudRepository<Keyword, Long> {
    @Override
    Keyword save(Keyword keyword);

    Set<Keyword> findAllByWordContainsAllIgnoreCase(String keyword);
}
