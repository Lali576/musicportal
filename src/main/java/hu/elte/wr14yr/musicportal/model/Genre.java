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
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @OneToMany(targetEntity = User.class, mappedBy = "favGenreId")
    private Set<User> users;

    @JoinTable(name = "ALBUM_GENRE", joinColumns = @JoinColumn(name = "GENRE_ID", referencedColumnName = "ID"),
                             inverseJoinColumns = @JoinColumn(name = "ALBUM_ID", referencedColumnName = "ID"))
    @ManyToMany(targetEntity = Album.class)
    private Set<Album> albums;

    @JoinTable(name = "SONG_GENRE", joinColumns = @JoinColumn(name = "GENRE_ID", referencedColumnName = "ID"),
                             inverseJoinColumns = @JoinColumn(name = "SONG_ID", referencedColumnName = "ID"))
    @ManyToMany(targetEntity = Song.class)
    private Set<Song> songs;
}
