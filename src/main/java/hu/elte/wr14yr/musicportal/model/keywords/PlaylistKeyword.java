package hu.elte.wr14yr.musicportal.model.keywords;

import hu.elte.wr14yr.musicportal.model.Playlist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "PLAYLIST_KEYWORDS")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlaylistKeyword extends Keyword {

    @ManyToOne(targetEntity = Playlist.class, optional = false)
    @JoinColumn(name = "PLAYLIST_ID")
    private Playlist playlist;
}
