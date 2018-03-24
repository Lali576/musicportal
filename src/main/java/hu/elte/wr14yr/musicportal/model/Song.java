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
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "title", unique = true, nullable = false)
    private String title;

    @Column(name = "year", nullable = false)
    private Year year;

    @Column(name = "audioPath", unique = true, nullable = false)
    private String audioPath;

    @Column(name = "lyrics")
    private String lyrics;

    @ManyToOne(targetEntity = Album.class)
    @JoinTable(name="SONGALBUM",
            joinColumns = @JoinColumn(name = "songId",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "albumId",
                    referencedColumnName = "id"))
    private Album album;

    @ManyToOne(targetEntity = Album.class)
    @JoinTable(name="SONGARTIST",
            joinColumns = @JoinColumn(name = "songId",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "artistId",
                    referencedColumnName = "id"))
    private Artist artist;

    @OneToMany(targetEntity = SongCounter.class, mappedBy = "song")
    private List<SongCounter> songCounters;

    @OneToMany(targetEntity = SongComment.class, mappedBy = "song")
    private List<SongComment> songComments;

    @OneToMany(targetEntity = SongLike.class, mappedBy = "song")
    private List<SongLike> songLikes;

    @ManyToMany(targetEntity = Keyword.class, mappedBy = "songs")
    @Column(name = "keywordId")
    private Set<Keyword> keywords;

    @ManyToMany(targetEntity = Playlist.class, mappedBy = "songs")
    @Column(name = "playlistId")
    private Set<Playlist> playlists;
}
