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
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"saltCode",
                        "hashPassword",
                        "iconPath",
                        "albums",
                        "songs",
                        "songComments",
                        "songCounters",
                        "songLikes",
                        "userToMessages",
                        "userFromMessages",
                        "playlists",
                        "keywords"})
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
    @JsonIgnore
    private String saltCode;

    @Column(name = "HASH_PASSWORD", unique = true, nullable = false)
    @JsonIgnore
    private String hashPassword;

    @ManyToOne(targetEntity = Genre.class)
    @JoinColumn(name = "FAV_GENRE_ID", nullable = false)
    private Genre favGenreId;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "BIOGRAPHY")
    private String biography;

    @Column(name = "ICON_PATH", nullable = false)
    @JsonIgnore
    private String iconPath;

    @Transient
    private File iconFile;

    @OneToMany(targetEntity = Album.class, mappedBy = "user")
    @JsonIgnore
    private Set<Album> albums;

    @OneToMany(targetEntity = Song.class, mappedBy = "user")
    @JsonIgnore
    private Set<Song> songs;

    @OneToMany(targetEntity = SongComment.class, mappedBy = "user")
    @JsonIgnore
    private Set<SongComment> songComments;

    @OneToMany(targetEntity = SongCounter.class, mappedBy = "user")
    @JsonIgnore
    private Set<SongCounter> songCounters;

    @OneToMany(targetEntity = SongLike.class, mappedBy = "user")
    @JsonIgnore
    private Set<SongLike> songLikes;

    /*@OneToMany(targetEntity = UserMessage.class, mappedBy = "userTo")
    @JsonIgnore
    private Set<UserMessage> userToMessages;*/

    /*@OneToMany(targetEntity = UserMessage.class, mappedBy = "userFrom")
    @JsonIgnore
    private Set<UserMessage> userFromMessages;*/

    @OneToMany(targetEntity = Playlist.class, mappedBy = "user")
    @JsonIgnore
    private Set<Playlist> playlists;

    @ManyToMany(targetEntity = Keyword.class, mappedBy = "users")
    @JsonIgnore
    private Set<Keyword> keywords;

    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        GUEST, USER, ARTIST
    }
}
