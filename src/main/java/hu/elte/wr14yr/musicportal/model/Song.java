package hu.elte.wr14yr.musicportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Year;

@Entity
@Data
@Table(name = "SONGS")
@AllArgsConstructor
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "title", unique = true, nullable = false)
    private String title;

    @Column(name = "year", nullable = false)
    private Year year;

    @Column(name = "audioPath", unique = true, nullable = false)
    private String audioPath;

    @Column(name = "lyrics", columnDefinition = "default 'instrumental song'")
    private String lyrics;
}
