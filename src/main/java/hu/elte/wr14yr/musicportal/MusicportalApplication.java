package hu.elte.wr14yr.musicportal;

import hu.elte.wr14yr.musicportal.model.Country;
import hu.elte.wr14yr.musicportal.model.Genre;
import hu.elte.wr14yr.musicportal.repository.CountryRepository;
import hu.elte.wr14yr.musicportal.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;
import java.util.Map;

@EnableWebMvc
@SpringBootApplication
public class MusicportalApplication implements WebMvcConfigurer {

	@Autowired
	private HandlerInterceptor authInterceptor;

    @Autowired
    private JdbcTemplate jdbcTemplate;

	@EventListener
    private void seedGenreData(ContextRefreshedEvent event) {
        for (String name : Genre.genres) {
            String sql = "SELECT NAME FROM GENRES G WHERE G.NAME = '" + name + "' LIMIT 1";
            List<Genre> g = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
            if(g == null || g.size() <= 0) {
                Genre genre = new Genre();
                genre.setName(name);
                genreRepository.save(genre);
            }
        }

        for (Map.Entry<String, String> entry : Country.countries.entrySet()) {
            String name = entry.getKey();
            String iconFileURL = entry.getValue();
            String sql = "SELECT NAME FROM COUNTRIES C WHERE C.NAME = '" + name + "' LIMIT 1";
            List<Country> c = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
            if(c == null || c.size() <= 0) {
                Country country = new Country();
                country.setName(name);
                country.setIconFileURL(iconFileURL);
                countryRepository.save(country);
            }
        }
    }

    @Autowired
    private CountryRepository countryRepository;

	@Autowired
	private GenreRepository genreRepository;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor);
	}

	public static void main(String[] args) {
		SpringApplication.run(MusicportalApplication.class, args);
	}
}
