package hu.elte.wr14yr.musicportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hu.elte.wr14yr.musicportal.model.tags.UserTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "USERS")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"saltCode",
                        "hashPassword",
                        "albums",
                        "songs",
                        "songComments",
                        "songCounters",
                        "songLikes",
                        "userToMessages",
                        "userFromMessages",
                        "playlist",
                        "userTags"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
    @JoinColumn(name = "FAV_GENRE_ID")
    private Genre favGenreId;

    @ManyToOne(targetEntity = Country.class, optional = false)
    @JoinColumn(name = "COUNTRY_ID")
    private Country countryId;

    @Column(name = "BIOGRAPHY", columnDefinition = "TEXT")
    private String biography;

    @Column(name = "USER_FOLDER_GDA_ID", unique = true)
    private String userFolderGdaId;

    @Column(name = "USER_ALBUMS_FOLDER_GDA_ID", unique = true)
    private String userAlbumsFolderGdaId;

    @Column(name = "USER_ICON_FOLDER_GDA_ID", unique = true)
    private String userIconFolderGdaId;

    @Column(name = "ICON_FILE_GDA_ID")
    private String iconFileGdaId;

    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(targetEntity = Album.class, mappedBy = "user")
    private List<Album> albums;

    @OneToMany(targetEntity = Song.class, mappedBy = "user")
    private List<Song> songs;

    @OneToMany(targetEntity = SongComment.class, mappedBy = "user")
    private List<SongComment> songComments;

    @OneToMany(targetEntity = SongCounter.class, mappedBy = "user")
    private List<SongCounter> songCounters;

    @OneToMany(targetEntity = SongLike.class, mappedBy = "user")
    private List<SongLike> songLikes;

    @OneToMany(targetEntity = UserMessage.class, mappedBy = "userTo")
    private List<UserMessage> userToMessages;

    @OneToMany(targetEntity = UserMessage.class, mappedBy = "userFrom")
    private List<UserMessage> userFromMessages;

    @OneToMany(targetEntity = Playlist.class, mappedBy = "user")
    private List<Playlist> playlist;

    @OneToMany(targetEntity = UserTag.class, mappedBy = "user")
    private List<UserTag> userTags;

    public enum Role {
        GUEST, USER, ARTIST
    }
}
