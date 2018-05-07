package hu.elte.wr14yr.musicportal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "GENRES")
@AllArgsConstructor
@NoArgsConstructor
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @OneToMany(targetEntity = User.class, mappedBy = "favGenreId")
    @JsonIgnore
    private List<User> users;

    @JoinTable(name = "ALBUM_GENRE", joinColumns = @JoinColumn(name = "GENRE_ID", referencedColumnName = "ID"),
                             inverseJoinColumns = @JoinColumn(name = "ALBUM_ID", referencedColumnName = "ID"))
    @ManyToMany(targetEntity = Album.class)
    @JsonIgnore
    private Set<Album> albums;

    @JoinTable(name = "SONG_GENRE", joinColumns = @JoinColumn(name = "GENRE_ID", referencedColumnName = "ID"),
                             inverseJoinColumns = @JoinColumn(name = "SONG_ID", referencedColumnName = "ID"))
    @ManyToMany(targetEntity = Song.class)
    @JsonIgnore
    private Set<Song> songs;
}
