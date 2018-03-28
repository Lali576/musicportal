package hu.elte.wr14yr.musicportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "SONGCOUNTERS",
        uniqueConstraints = @UniqueConstraint(columnNames = {"songId", "userId", "ipAddress"}))
@AllArgsConstructor
public class SongCounter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @JoinColumn(name = "songId", nullable = false)
    @ManyToOne(targetEntity = Song.class, optional = false)
    private Song song;

    @JoinColumn(name = "userId")
    @ManyToOne(targetEntity = User.class)
    private User user;

    @Column(name = "ipAddress", nullable = false)
    private String ipAddress;
}
