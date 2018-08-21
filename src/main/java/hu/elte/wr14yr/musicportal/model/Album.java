package hu.elte.wr14yr.musicportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
                        "keywords"})
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "TITLE", unique = true, nullable = false)
    private String title;

    @Column(name = "DATE", nullable = false)
    private Date date;

    @Column(name = "ALBUM_FOLDER_GDA_ID", unique = true)
    private String albumFolderGdaId;

    @Column(name = "COVER_FILE_GDA_ID", unique = true)
    private String coverFileGdaId;

    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @OneToMany(targetEntity = Song.class, mappedBy = "album")
    private List<Song> songs;

    @ManyToMany(targetEntity = Genre.class, mappedBy = "albums")
    private List<Genre> genres;

    @ManyToMany(targetEntity = Keyword.class, mappedBy = "albums")
    private List<Keyword> keywords;

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        SINGLE, EP, LP
    }
}
