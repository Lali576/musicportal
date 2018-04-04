package hu.elte.wr14yr.musicportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "PLAYLISTS")
@AllArgsConstructor
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinTable(name = "USER_PLAYLIST", joinColumns = @JoinColumn(name = "PLAYLIST_ID", referencedColumnName = "ID"),
                                inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"))
    private User user;

    @ManyToMany(targetEntity = Keyword.class, mappedBy = "playlists")
    private Set<Keyword> keywords;

    @JoinTable(name = "SONG_PLAYLIST", joinColumns = @JoinColumn(name = "PLAYLIST_ID", referencedColumnName = "ID"),
                                inverseJoinColumns = @JoinColumn(name = "SONG_ID", referencedColumnName = "ID"))
    @ManyToMany(targetEntity = Song.class)
    private Set<Song> songs;
}
