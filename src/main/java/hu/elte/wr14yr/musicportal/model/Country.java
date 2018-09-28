package hu.elte.wr14yr.musicportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "COUNTRIES")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"users"})
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @OneToMany(targetEntity = User.class, mappedBy = "countryId")
    private List<User> users;

    public static List<String> countries = new ArrayList<String>(){{
        add("Magyarország");
        add("Nagy-Brittania");
        add("Németország");
        add("Franciaország");
        add("Spanyolország");
        add("Olaszország");
        add("USA");
        add("Kanada");
        add("Brazília");
        add("Oroszország");
        add("India");
        add("Kína");
        add("Japán");
        add("Ausztrália");
    }};
}
