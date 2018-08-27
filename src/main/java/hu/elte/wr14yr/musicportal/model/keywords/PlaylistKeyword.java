package hu.elte.wr14yr.musicportal.model.keywords;

import hu.elte.wr14yr.musicportal.model.Playlist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "USER_KEYWORDS")
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "WORD", nullable = false)
    private String word;

    @ManyToOne(targetEntity = Playlist.class, optional = false)
    @JoinColumn(name = "PLAYLIST_ID")
    private Playlist playlist;
}
