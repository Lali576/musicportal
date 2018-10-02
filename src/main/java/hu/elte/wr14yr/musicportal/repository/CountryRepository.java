package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Country;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CountryRepository extends CrudRepository<Country, Long> {
    @Override
    Country save(Country country);

    List<Country> findAll();
}
