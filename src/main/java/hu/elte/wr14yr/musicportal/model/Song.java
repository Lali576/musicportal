package hu.elte.wr14yr.musicportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.File;
import java.util.List;

@Entity
@Data
@Table(name = "SONGS")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"songComments",
                        "songCounters",
                        "songLikes",
                        "playlist",
                        "genres",
                        "keywords"})
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "TITLE", unique = true, nullable = false)
    private String title;

    @Column(name = "LYRICS")
    private String lyrics;

    @Column(name = "AUDIO_FILE_GDA_ID", unique = true)
    private String audioFileGdaId;

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

    @ManyToMany(targetEntity = Playlist.class, mappedBy = "songs")
    private List<Playlist> playlist;

    @ManyToMany(targetEntity = Genre.class, mappedBy = "songs")
    private List<Genre> genres;

    @ManyToMany(targetEntity = Keyword.class, mappedBy = "songs")
    private List<Keyword> keywords;
}
