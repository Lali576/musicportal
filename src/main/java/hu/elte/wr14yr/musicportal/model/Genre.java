package hu.elte.wr14yr.musicportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "GENRES")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"users",
                        "albums",
                        "songs"})
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @OneToMany(targetEntity = User.class, mappedBy = "favGenreId")
    private List<User> users;

    @ManyToMany(targetEntity = Album.class, mappedBy = "genres")
    private List<Album> albums;

    public static List<String> genres = new ArrayList<String>(){{
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
}
