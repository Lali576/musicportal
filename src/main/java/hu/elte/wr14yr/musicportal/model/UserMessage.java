package hu.elte.wr14yr.musicportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "USER_MESSAGES")
@AllArgsConstructor
@NoArgsConstructor
public class UserMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private long id;

    @JoinColumn(name = "USER_TO_ID", nullable = false)
    @ManyToOne(targetEntity = User.class, optional = false)
    private User userTo;

    @JoinColumn(name = "USER_FROM_ID", nullable = false)
    @ManyToOne(targetEntity = User.class, optional = false)
    private User userFrom;

    @Column(name = "TEXT", nullable = false)
    private String text;

    @Column(name = "DATE_TIME", nullable = false)
    private LocalDateTime dateTime;
}
