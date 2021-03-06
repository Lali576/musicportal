package hu.elte.wr14yr.musicportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "SONGS")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"songTags",
                        "songComments",
                        "songCounters",
                        "songLikes",
                        "playlist"})
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "TITLE", unique = true, nullable = false)
    private String title;

    @Column(name = "LYRICS", columnDefinition = "LONGTEXT")
    private String lyrics;

    @Column(name = "AUDIO_FILE_GDA_ID")
    private String audioFileGdaId;

    @Column(name = "DURATION")
    private int duration;

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

    @OneToMany(targetEntity = SongLike.class, mappedBy = "song")
    private List<SongLike> songLikes;

    @ManyToMany(targetEntity = Playlist.class, mappedBy = "songs")
    private List<Playlist> playlist;
}
