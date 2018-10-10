package hu.elte.wr14yr.musicportal.model.keywords;

import hu.elte.wr14yr.musicportal.model.Album;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ALBUM_KEYWORDS")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AlbumKeyword extends Keyword {

    @ManyToOne(targetEntity = Album.class, optional = false)
    @JoinColumn(name = "ALBUM_ID")
    private Album album;
}
