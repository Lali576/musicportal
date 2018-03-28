package hu.elte.wr14yr.musicportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "USERMESSAGES")
@AllArgsConstructor
@NoArgsConstructor
public class UserMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @JoinColumn(name = "userToId", nullable = false)
    @ManyToOne(targetEntity = User.class, optional = false)
    private User userTo;

    @JoinColumn(name = "userFromId", nullable = false)
    @ManyToOne(targetEntity = User.class, optional = false)
    private User userFrom;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "dateTime", nullable = false)
    private LocalDateTime dateTime;
}
