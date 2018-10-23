package hu.elte.wr14yr.musicportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Column(name = "ICON_FILE_URL")
    private String iconFileURL;

    @OneToMany(targetEntity = User.class, mappedBy = "countryId")
    private List<User> users;

    public static Map<String, String> countries;
    static {
        countries.put("", "");
    }
}