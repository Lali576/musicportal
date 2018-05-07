package hu.elte.wr14yr.musicportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "SONG_COUNTERS",
        uniqueConstraints = @UniqueConstraint(columnNames = {"SONG_ID", "USER_ID", "IP_ADDRESS"}))
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreType
public class SongCounter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;

    @JoinColumn(name = "SONG_ID", nullable = false)
    @ManyToOne(targetEntity = Song.class, optional = false)
    private Song song;

    @JoinColumn(name = "USER_ID")
    @ManyToOne(targetEntity = User.class)
    private User user;

    @Column(name = "IP_ADDRESS", nullable = false)
    private String ipAddress;
}
