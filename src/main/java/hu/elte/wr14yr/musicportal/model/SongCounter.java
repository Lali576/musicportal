package hu.elte.wr14yr.musicportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "SONG_COUNTERS",
        uniqueConstraints = @UniqueConstraint(columnNames = {"SONG_ID", "USER_ID"}))
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"user",
                        "song"})
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
}
