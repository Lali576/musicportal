package hu.elte.wr14yr.musicportal.model.tags;

import hu.elte.wr14yr.musicportal.model.Song;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "SONG_TAGS")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SongTag extends Tag {

    @ManyToOne(targetEntity = Song.class, optional = false)
    @JoinColumn(name = "SONG_ID")
    private Song song;
}
