package hu.elte.wr14yr.musicportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hu.elte.wr14yr.musicportal.model.keywords.PlaylistKeyword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "PLAYLISTS")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"songs"})
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @Column(name = "DATE", nullable = false)
    private Date date;

    @JoinTable(name = "SONG_PLAYLIST", joinColumns = @JoinColumn(name = "PLAYLIST_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "SONG_ID", referencedColumnName = "ID"))
    @ManyToMany(targetEntity = Song.class)
    private List<Song> songs;

    @OneToMany(targetEntity = PlaylistKeyword.class, mappedBy = "playlist")
    private List<PlaylistKeyword> playlistKeywords;
}
