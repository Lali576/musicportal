package hu.elte.wr14yr.musicportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.File;
import java.time.Year;
import java.util.Set;

@Entity
@Data
@Table(name = "SONGS")
@AllArgsConstructor
@JsonIgnoreProperties({"audioPath",
                        "songCounters",
                        "songLikes",
                        "playlists",
                        "genres",
                        "keywords",
                        "songComments"})
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "TITLE", unique = true, nullable = false)
    private String title;

    @Column(name = "YEAR", nullable = false)
    private Year year;

    @Column(name = "LYRICS")
    private String lyrics;

    @Column(name = "AUDIO_PATH", unique = true, nullable = false)
    private String audioPath;

    private File audioFile;

    @ManyToOne(targetEntity = Album.class, optional = false)
    @JoinColumn(name = "ALBUM_ID", referencedColumnName = "ID")
    private Album album;

    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @OneToMany(targetEntity = SongComment.class, mappedBy = "song")
    private Set<SongComment> songComments;

    @OneToMany(targetEntity = SongCounter.class, mappedBy = "song")
    private Set<SongCounter> songCounters;

    private int songCounterNumber;

    @OneToMany(targetEntity = SongLike.class, mappedBy = "song")
    private Set<SongLike> songLikes;

    private int songLikeNumber;

    private int songDislikeNumber;

    @ManyToMany(targetEntity = Playlist.class, mappedBy = "songs")
    private Set<Playlist> playlists;

    @ManyToMany(targetEntity = Genre.class, mappedBy = "songs")
    private Set<Genre> genres;

    @ManyToMany(targetEntity = Keyword.class, mappedBy = "songs")
    private Set<Keyword> keywords;
}
