package hu.elte.wr14yr.musicportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @OneToMany(targetEntity = User.class, mappedBy = "favGenreId")
    private List<User> users;

    @JoinTable(name = "ALBUM_GENRE", joinColumns = @JoinColumn(name = "GENRE_ID", referencedColumnName = "ID"),
                             inverseJoinColumns = @JoinColumn(name = "ALBUM_ID", referencedColumnName = "ID"))
    @ManyToMany(targetEntity = Album.class)
    private List<Album> albums;

    @JoinTable(name = "SONG_GENRE", joinColumns = @JoinColumn(name = "GENRE_ID", referencedColumnName = "ID"),
                             inverseJoinColumns = @JoinColumn(name = "SONG_ID", referencedColumnName = "ID"))
    @ManyToMany(targetEntity = Song.class)
    private List<Song> songs;
}
