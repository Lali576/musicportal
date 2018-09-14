package hu.elte.wr14yr.musicportal;

import hu.elte.wr14yr.musicportal.model.Genre;
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

import java.util.ArrayList;
import java.util.List;

@EnableWebMvc
@SpringBootApplication
public class MusicportalApplication implements WebMvcConfigurer {

	@Autowired
	private HandlerInterceptor authInterceptor;

	@EventListener
    private void seed(ContextRefreshedEvent event) {
        seedGenresTable();
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

	@Autowired
	private GenreRepository genreRepository;

    private void seedGenresTable() {
        List<String> genres = new ArrayList<String>(){{
            add("Alternative Rock");
            add("Ambient");
            add("Classical");
            add("Country");
            add("Dance & EDM");
            add("Dancehall");
            add("Deep House");
            add("Disco");
            add("Drum & Bass");
            add("Dubstep");
            add("Electronic");
            add("Folk & Singer-Songwriter");
            add("Hip-hop & Rap");
            add("House");
            add("Indie");
            add("Jazz & Blues");
            add("Latin");
            add("Metal");
            add("Piano");
            add("Pop");
            add("R&B & Soul");
            add("Reggae");
            add("Reggaeton");
            add("Rock");
            add("Soundtrack");
            add("Techno");
            add("Trance");
            add("Trap");
            add("Triphop");
            add("World");
        }};
        for (String name : genres) {
            String sql = "SELECT NAME FROM GENRES G WHERE G.NAME = '" + name + "' LIMIT 1";
            List<Genre> g = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
            if(g == null || g.size() <= 0) {
                Genre genre = new Genre();
                genre.setName(name);
                genreRepository.save(genre);
            }
        }
    }

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor);
	}

	public static void main(String[] args) {
		SpringApplication.run(MusicportalApplication.class, args);
	}
}
