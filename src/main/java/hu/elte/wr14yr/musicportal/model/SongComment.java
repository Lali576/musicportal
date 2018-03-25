package hu.elte.wr14yr.musicportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "SONGCOMMENTS")
@AllArgsConstructor
public class SongComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @JoinColumn(name = "songId", nullable = false)
    @ManyToOne(targetEntity = Song.class, optional = false)
    private Song song;

    @JoinColumn(name = "userId", nullable = false)
    @ManyToOne(targetEntity = User.class, optional = false)
    private User user;

    @Column(name = "textMessage", nullable = false)
    private String textMessage;

    @Column(name = "dateTime", nullable = false)
    private LocalDateTime dateTime;
}
