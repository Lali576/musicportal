package hu.elte.wr14yr.musicportal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "SONG_COMMENTS")
@AllArgsConstructor
public class SongComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private long id;

    @JoinColumn(name = "SONG_ID", nullable = false)
    @ManyToOne(targetEntity = Song.class, optional = false)
    @JsonIgnore
    private Song song;

    @JoinColumn(name = "USER_ID", nullable = false)
    @ManyToOne(targetEntity = User.class, optional = false)
    private User user;

    @Column(name = "TEXT_MESSAGE", nullable = false)
    private String textMessage;

    @Column(name = "DATE_TIME", nullable = false)
    private LocalDateTime dateTime;
}
