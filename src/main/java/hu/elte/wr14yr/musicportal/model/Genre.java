package hu.elte.wr14yr.musicportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "GENRES")
@AllArgsConstructor
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @JoinTable(name = "SONGGENRE")
    @ManyToMany(targetEntity = Song.class)
    @Column(name = "songId")
    private Set<Song> songs;
}
