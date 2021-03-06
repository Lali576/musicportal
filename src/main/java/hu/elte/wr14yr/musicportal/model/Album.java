package hu.elte.wr14yr.musicportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hu.elte.wr14yr.musicportal.model.tags.AlbumTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "ALBUMS")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"songs",
                        "genres",
                        "albumTags"})
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "TITLE", unique = true, nullable = false)
    private String title;

    @Column(name = "DATE", nullable = false)
    private Date date;

    @Column(name = "ALBUM_FOLDER_GDA_ID", unique = true)
    private String albumFolderGdaId;

    @Column(name = "ALBUM_SONGS_FOLDER_GDA_ID", unique = true)
    private String albumSongsFolderGdaId;

    @Column(name = "ALBUM_COVER_FOLDER_GDA_ID", unique = true)
    private String albumCoverFolderGdaId;

    @Column(name = "COVER_FILE_GDA_ID")
    private String coverFileGdaId;

    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(targetEntity = Song.class, mappedBy = "album")
    private List<Song> songs;

    @JoinTable(name = "ALBUM_GENRE", joinColumns = @JoinColumn(name = "ALBUM_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "GENRE_ID", referencedColumnName = "ID"))
    @ManyToMany(targetEntity = Genre.class)
    private List<Genre> genres;

    @OneToMany(targetEntity = AlbumTag.class, mappedBy = "album")
    private List<AlbumTag> albumTags;

    public enum Type {
        SINGLE, EP, LP
    }
}
