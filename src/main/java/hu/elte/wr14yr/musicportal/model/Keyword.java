package hu.elte.wr14yr.musicportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "KEYWORDS")
@AllArgsConstructor
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @JoinTable(name = "ALBUMKEYWORD")
    @ManyToMany(targetEntity = Album.class)
    @Column(name = "albumId")
    private Set<Album> albums;

    @JoinTable(name = "ARTISTKEYWORD")
    @ManyToMany(targetEntity = Artist.class)
    @Column(name = "artistId")
    private Set<Artist> artists;

    @JoinTable(name = "PLAYLISTKEYWORD")
    @ManyToMany(targetEntity = Playlist.class)
    @Column(name = "playlistId")
    private Set<Playlist> playlists;

    @JoinTable(name = "SONGKEYWORD")
    @ManyToMany(targetEntity = Song.class)
    @Column(name = "songId")
    private Set<Song> songs;
}
