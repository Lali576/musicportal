package hu.elte.wr14yr.musicportal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "PLAYLISTS")
@AllArgsConstructor
@JsonIgnoreProperties({"keywords",
                        "songs"})
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @ManyToMany(targetEntity = Keyword.class, mappedBy = "playlists")
    private Set<Keyword> keywords;

    @JoinTable(name = "SONG_PLAYLIST", joinColumns = @JoinColumn(name = "PLAYLIST_ID", referencedColumnName = "ID"),
                                inverseJoinColumns = @JoinColumn(name = "SONG_ID", referencedColumnName = "ID"))
    @ManyToMany(targetEntity = Song.class)
    private Set<Song> songs;
}
