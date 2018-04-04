package hu.elte.wr14yr.musicportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Year;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "SONGS")
@AllArgsConstructor
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

    @OneToMany(targetEntity = SongComment.class, mappedBy = "song")
    private Set<SongComment> songComments;

    @OneToMany(targetEntity = SongCounter.class, mappedBy = "song")
    private Set<SongCounter> songCounters;

    @OneToMany(targetEntity = SongLike.class, mappedBy = "song")
    private Set<SongLike> songLikes;

    @ManyToOne(targetEntity = Album.class, optional = false)
    @JoinTable(name="SONG_ALBUM", joinColumns = @JoinColumn(name = "SONG_ID", referencedColumnName = "ID"),
                           inverseJoinColumns = @JoinColumn(name = "ALBUM_ID", referencedColumnName = "ID"))
    private Album album;

    @ManyToOne(targetEntity = Artist.class, optional = false)
    @JoinTable(name="SONG_ARTIST", joinColumns = @JoinColumn(name = "SONG_ID", referencedColumnName = "ID"),
                            inverseJoinColumns = @JoinColumn(name = "ARTIST_ID", referencedColumnName = "ID"))
    private Artist artist;

    @ManyToMany(targetEntity = Playlist.class, mappedBy = "songs")
    private Set<Playlist> playlists;

    @ManyToMany(targetEntity = Genre.class, mappedBy = "songs")
    private Set<Genre> genres;

    @ManyToMany(targetEntity = Keyword.class, mappedBy = "songs")
    private Set<Keyword> keywords;
}
