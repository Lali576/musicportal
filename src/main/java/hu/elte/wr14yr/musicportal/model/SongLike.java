package hu.elte.wr14yr.musicportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "SONG_LIKES",
        uniqueConstraints = @UniqueConstraint(columnNames = {"SONG_ID", "USER_ID"}))
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"song",
                        "user"})
public class SongLike {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false)
    private long id;

    @JoinColumn(name = "SONG_ID", nullable = false)
    @ManyToOne(targetEntity = Song.class, optional = false)
    private Song song;

    @JoinColumn(name = "USER_ID", nullable = false)
    @ManyToOne(targetEntity = User.class, optional = false)
    private User user;

    @Column(name = "SONG_TYPE")
    @Enumerated(value = EnumType.STRING)
    private Type type;

    public enum Type {
        LIKE, DISLIKE
    }
}
