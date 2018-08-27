package hu.elte.wr14yr.musicportal.model.keywords;

import hu.elte.wr14yr.musicportal.model.Song;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "SONG_KEYWORD")
@AllArgsConstructor
@NoArgsConstructor
public class SongKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "WORD", nullable = false)
    private String word;

    @ManyToOne(targetEntity = Song.class, optional = false)
    @JoinColumn(name = "SONG_ID")
    private Song song;
}
