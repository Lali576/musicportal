package hu.elte.wr14yr.musicportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "SONGLIKE",
        uniqueConstraints = @UniqueConstraint(columnNames = {"songId", "userId"}))
@AllArgsConstructor
public class SongLike {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @JoinColumn(name = "songId", nullable = false)
    @ManyToOne(targetEntity = Song.class, optional = false)
    private Song song;

    @JoinColumn(name = "userId", nullable = false)
    @ManyToOne(targetEntity = User.class, optional = false)
    private User user;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public enum Role {
        LIKE, DISLIKE
    }
}
