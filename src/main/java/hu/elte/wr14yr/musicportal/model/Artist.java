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
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "ARTIST_NAME", unique = true, nullable = false)
    private String artistName;

    @Column(name = "EMAIL_ADDRESS", unique = true, nullable = false)
    private String emailAddress;

    @OneToMany(targetEntity = Song.class, mappedBy = "artist")
    private Set<Song> songs;

    @ManyToMany(targetEntity = Keyword.class, mappedBy = "artists")
    private Set<Keyword> keywords;
}
