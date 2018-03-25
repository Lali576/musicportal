package hu.elte.wr14yr.musicportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "PLAYLISTS")
@AllArgsConstructor
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinTable(name = "USERPLAYLIST",
            joinColumns = @JoinColumn(name = "playlistId",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "userId",
                    referencedColumnName = "id"))
    private User user;

    @ManyToMany(mappedBy = "playlists")
    @Column(name = "keywordId")
    private Set<Keyword> keywords;

    @ManyToMany(targetEntity = Song.class)
    @Column(name = "songId")
    private Set<Song> songs;
}
