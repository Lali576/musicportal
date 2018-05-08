package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Keyword;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface KeywordRepository extends CrudRepository<Keyword, Long> {
    @Override
    Keyword save(Keyword keyword);

    List<Keyword> findAllByWordContainsAllIgnoreCase(String keyword);
}
