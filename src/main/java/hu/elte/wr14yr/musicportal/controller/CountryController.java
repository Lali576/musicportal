package hu.elte.wr14yr.musicportal.controller;

import hu.elte.wr14yr.musicportal.model.Country;
import hu.elte.wr14yr.musicportal.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/country")
public class CountryController {
    @Autowired
    private CountryRepository countryRepository;

    private Logger logger = Logger.getLogger(CountryController.class.getName());

    @GetMapping("/list")
    public ResponseEntity<Iterable<Country>> list() {
        logger.info("Country controller: enter endpoint '/list'");

        Iterable<Country> countries = countryRepository.findAllByOrderByName();

        logger.info("Country controller: exit endpoint '/list'");

        return ResponseEntity.ok(countries);
    }
}
