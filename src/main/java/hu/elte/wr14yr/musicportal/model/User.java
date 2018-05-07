package hu.elte.wr14yr.musicportal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.File;
import java.util.Set;

@Entity
@Data
@Table(name = "USERS")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne(targetEntity = Genre.class, optional = false)
    @JoinColumn(name = "FAV_GENRE_ID", nullable = false)
    @JsonIgnoreProperties("users")
    private Genre favGenreId;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "BIOGRAPHY")
    private String biography;

    @Column(name = "ICON_PATH", nullable = false)
    private String iconPath;

    @Transient
    private File iconFile;

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

    @ManyToMany(targetEntity = Keyword.class, mappedBy = "users")
    private Set<Keyword> keywords;

    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        GUEST, USER, ARTIST
    }
}
