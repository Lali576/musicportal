package hu.elte.wr14yr.musicportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "KEYWORDS")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"songs",
                        "playlist",
                        "albums",
                        "users"})
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "WORD", nullable = false)
    private String word;

    @JoinTable(name = "SONG_KEYWORD", joinColumns = @JoinColumn(name = "KEYWORD_ID", referencedColumnName = "ID"),
                               inverseJoinColumns = @JoinColumn(name = "SONG_ID",referencedColumnName = "ID"))
    @ManyToMany(targetEntity = Song.class)
    private List<Song> songs;

    @JoinTable(name = "PLAYLIST_KEYWORD", joinColumns = @JoinColumn(name = "KEYWORD_ID", referencedColumnName = "ID"),
                                   inverseJoinColumns = @JoinColumn(name = "PLAYLIST_ID",referencedColumnName = "ID"))
    @ManyToMany(targetEntity = Playlist.class)
    private List<Playlist> playlist;

    @JoinTable(name = "ALBUM_KEYWORD", joinColumns = @JoinColumn(name = "KEYWORD_ID", referencedColumnName = "ID"),
                                inverseJoinColumns = @JoinColumn(name = "ALBUM_ID",referencedColumnName = "ID"))
    @ManyToMany(targetEntity = Album.class)
    private List<Album> albums;

    @JoinTable(name = "USER_KEYWORD", joinColumns = @JoinColumn(name = "KEYWORD_ID", referencedColumnName = "ID"),
                                 inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"))
    @ManyToMany(targetEntity = User.class)
    private List<User> users;
}
