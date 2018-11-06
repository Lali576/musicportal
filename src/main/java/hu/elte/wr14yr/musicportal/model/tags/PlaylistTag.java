package hu.elte.wr14yr.musicportal.model.tags;

import hu.elte.wr14yr.musicportal.model.Playlist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "PLAYLIST_TAGS")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlaylistTag extends Tag {

    @ManyToOne(targetEntity = Playlist.class, optional = false)
    @JoinColumn(name = "PLAYLIST_ID")
    private Playlist playlist;
}
