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
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Column(name = "YEAR", nullable = false)
    private Year year;

    @Column(name = "COVER_PATH", unique = true, nullable = false)
    private String coverPath;

    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinTable(name="ALBUM_USER", joinColumns = @JoinColumn(name = "ALBUM_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"))
    private User user;

    @OneToMany(targetEntity = Song.class, mappedBy = "album")
    private Set<Song> songs;

    @ManyToMany(targetEntity = Keyword.class, mappedBy = "albums")
    private Set<Keyword> keywords;
}
