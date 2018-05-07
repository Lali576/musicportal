package hu.elte.wr14yr.musicportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "SONG_LIKES",
        uniqueConstraints = @UniqueConstraint(columnNames = {"SONG_ID", "USER_ID"}))
@AllArgsConstructor
@JsonIgnoreType
@NoArgsConstructor
public class SongLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;

    @JoinColumn(name = "SONG_ID", nullable = false)
    @ManyToOne(targetEntity = Song.class, optional = false)
    private Song song;

    @JoinColumn(name = "USER_ID", nullable = false)
    @ManyToOne(targetEntity = User.class, optional = false)
    private User user;

    @Column(name = "ROLE")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public enum Role {
        LIKE, DISLIKE
    }
}
