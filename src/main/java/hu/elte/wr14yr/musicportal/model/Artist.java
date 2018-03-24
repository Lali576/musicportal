package hu.elte.wr14yr.musicportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "ARTISTS")
@AllArgsConstructor
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "artistName", unique = true, nullable = false)
    private String artistName;

    @Column(name = "emailAddress", unique = true, nullable = false)
    private String emailAddress;

    @OneToMany(targetEntity = Song.class, mappedBy = "artist")
    private Set<Song> songs;

    @ManyToMany(mappedBy = "artists")
    private Set<Keyword> keywords;
}
