package hu.elte.wr14yr.musicportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.File;
import java.time.Year;
import java.util.List;

@Entity
@Data
@Table(name = "SONGS")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"audioPath",
                        "songCounters",
                        "songLikes",
                        "playlists",
                        "genres",
                        "keywords",
                        "songComments"})
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Transient
    private File audioFile;

    @ManyToOne(targetEntity = Album.class, optional = false)
    @JoinColumn(name = "ALBUM_ID", referencedColumnName = "ID")
    private Album album;

    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @OneToMany(targetEntity = SongComment.class, mappedBy = "song")
    private List<SongComment> songComments;

    @OneToMany(targetEntity = SongCounter.class, mappedBy = "song")
    private List<SongCounter> songCounters;

    @Transient
    private int songCounterNumber;

    @OneToMany(targetEntity = SongLike.class, mappedBy = "song")
    private List<SongLike> songLikes;

    @Transient
    private int songLikeNumber;

    @Transient
    private int songDislikeNumber;

    @ManyToMany(targetEntity = Playlist.class, mappedBy = "songs")
    private List<Playlist> playlists;

    @ManyToMany(targetEntity = Genre.class, mappedBy = "songs")
    private List<Genre> genres;

    @ManyToMany(targetEntity = Keyword.class, mappedBy = "songs")
    private List<Keyword> keywords;
}
