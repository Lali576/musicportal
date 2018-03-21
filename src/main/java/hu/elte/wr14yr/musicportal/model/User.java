package hu.elte.wr14yr.musicportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "userName", unique = true, nullable = false)
    private String userName;

    @Column(name = "emailAddress", unique = true, nullable = false)
    private String emailAddress;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "favGenreId")
    private long favGenreId;

    @Column(name = "fullName", nullable = false)
    private String fullName;

    @Column(name = "iconPath", nullable = false)
    private String iconPath;

    @OneToMany(targetEntity = UserMessage.class, mappedBy = "userTo")
    private List<UserMessage> messages;

    @OneToMany(targetEntity = SongCounter.class, mappedBy = "user")
    private List<SongCounter> songCounters;

    @OneToMany(targetEntity = SongComment.class, mappedBy = "user")
    private List<SongComment> songComments;

    @OneToMany(targetEntity = SongLike.class, mappedBy = "user")
    private List<SongLike> songLikes;
}
