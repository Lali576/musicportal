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

    @Column(name = "lyrics")
    private String lyrics;

    @Column(name = "audioPath", unique = true, nullable = false)
    private String audioPath;

    @OneToMany(targetEntity = SongComment.class, mappedBy = "song")
    private Set<SongComment> songComments;

    @OneToMany(targetEntity = SongCounter.class, mappedBy = "song")
    private Set<SongCounter> songCounters;

    @OneToMany(targetEntity = SongLike.class, mappedBy = "song")
    private Set<SongLike> songLikes;

    @ManyToOne(targetEntity = Album.class, optional = false)
    @JoinTable(name="SONGALBUM",
            joinColumns = @JoinColumn(name = "songId",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "albumId",
                    referencedColumnName = "id"))
    private Album album;

    @ManyToOne(targetEntity = Artist.class, optional = false)
    @JoinTable(name="SONGARTIST",
            joinColumns = @JoinColumn(name = "songId",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "artistId",
                    referencedColumnName = "id"))
    private Artist artist;

    @ManyToMany(targetEntity = Playlist.class, mappedBy = "songs")
    @Column(name = "playlistId")
    private Set<Playlist> playlists;

    @ManyToMany(targetEntity = Genre.class, mappedBy = "songs")
    private Set<Genre> genres;

    @ManyToMany(targetEntity = Keyword.class, mappedBy = "songs")
    @Column(name = "keywordId")
    private Set<Keyword> keywords;
}
