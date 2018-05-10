package hu.elte.wr14yr.musicportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.File;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "ALBUMS")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"coverPath",
                        "user",
                        "songs",
                        "genres",
                        "keywords"})
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Column(name = "DATE", nullable = false)
    private Date date;

    //unique=true for real
    @Column(name = "COVER_PATH", unique = false, nullable = false)
    private String coverPath;

    @Transient
    private File coverFile;

    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @OneToMany(targetEntity = Song.class, mappedBy = "album")
    private List<Song> songs;

    @ManyToMany(targetEntity = Genre.class, mappedBy = "albums")
    private List<Genre> genres;

    @ManyToMany(targetEntity = Keyword.class, mappedBy = "albums")
    private List<Keyword> keywords;
}
