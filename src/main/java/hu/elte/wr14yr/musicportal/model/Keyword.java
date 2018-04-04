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
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "WORD", unique = true, nullable = false)
    private String word;

    @JoinTable(name = "SONG_KEYWORD", joinColumns = @JoinColumn(name = "KEYWORD_ID", referencedColumnName = "ID"),
                               inverseJoinColumns = @JoinColumn(name = "SONG_ID",referencedColumnName = "ID"))
    @ManyToMany(targetEntity = Song.class)
    private Set<Song> songs;

    @JoinTable(name = "PLAYLIST_KEYWORD", joinColumns = @JoinColumn(name = "KEYWORD_ID", referencedColumnName = "ID"),
                                   inverseJoinColumns = @JoinColumn(name = "PLAYLIST_ID",referencedColumnName = "ID"))
    @ManyToMany(targetEntity = Playlist.class)
    private Set<Playlist> playlists;

    @JoinTable(name = "ALBUM_KEYWORD", joinColumns = @JoinColumn(name = "KEYWORD_ID", referencedColumnName = "ID"),
                                inverseJoinColumns = @JoinColumn(name = "ALBUM_ID",referencedColumnName = "ID"))
    @ManyToMany(targetEntity = Album.class)
    private Set<Album> albums;

    @JoinTable(name = "ARTIST_KEYWORD", joinColumns = @JoinColumn(name = "KEYWORD_ID", referencedColumnName = "ID"),
                                 inverseJoinColumns = @JoinColumn(name = "ARTIST_ID", referencedColumnName = "ID"))
    @ManyToMany(targetEntity = Artist.class)
    private Set<Artist> artists;
}
