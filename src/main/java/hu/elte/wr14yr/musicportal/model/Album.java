package hu.elte.wr14yr.musicportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Year;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "ALBUMS")
@AllArgsConstructor
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "year", nullable = false)
    private Year year;

    @Column(name = "coverPath", unique = true, nullable = false)
    private String coverPath;

    @ManyToMany(mappedBy = "albums")
    private Set<Keyword> keywords;

    @OneToMany(targetEntity = Song.class, mappedBy = "album")
    private Set<Song> songs;
}
