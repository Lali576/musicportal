package hu.elte.wr14yr.musicportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @Column(name = "EMAIL_ADDRESS", unique = true, nullable = false)
    private String emailAddress;

    @Column(name = "SALT_CODE", unique = true, nullable = false)
    private String saltCode;

    @Column(name = "HASH_PASSWORD", unique = true, nullable = false)
    private String hashPassword;

    @ManyToOne(targetEntity = Genre.class)
    @JoinColumn(name = "FAV_GENRE_ID", nullable = false)
    private Genre favGenreId;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "BIOGRAPHY")
    private String biography;

    @Column(name = "ICON_PATH", nullable = false)
    private String iconPath;

    @OneToMany(targetEntity = Album.class, mappedBy = "user")
    private Set<Album> albums;

    @OneToMany(targetEntity = Song.class, mappedBy = "user")
    private Set<Song> songs;

    @OneToMany(targetEntity = SongComment.class, mappedBy = "user")
    private Set<SongComment> songComments;

    @OneToMany(targetEntity = SongCounter.class, mappedBy = "user")
    private Set<SongCounter> songCounters;

    @OneToMany(targetEntity = SongLike.class, mappedBy = "user")
    private Set<SongLike> songLikes;

    @OneToMany(targetEntity = UserMessage.class, mappedBy = "userTo")
    private Set<UserMessage> userToMessages;

    @OneToMany(targetEntity = UserMessage.class, mappedBy = "userFrom")
    private Set<UserMessage> userFromMessages;

    @OneToMany(targetEntity = Playlist.class, mappedBy = "user")
    private Set<Playlist> playlists;

    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        GUEST, USER, ARTIST
    }
}
