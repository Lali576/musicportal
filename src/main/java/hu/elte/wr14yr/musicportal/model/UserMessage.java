package hu.elte.wr14yr.musicportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "USER_MESSAGES")
@AllArgsConstructor
@NoArgsConstructor
public class UserMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;

    @JoinColumn(name = "USER_TO_ID", nullable = false)
    @ManyToOne(targetEntity = User.class, optional = false)
    private User userTo;

    @JoinColumn(name = "USER_FROM_ID", nullable = false)
    @ManyToOne(targetEntity = User.class, optional = false)
    private User userFrom;

    @Column(name = "TEXT_MESSAGE", nullable = false)
    private String textMessage;

    @Column(name = "DATE", nullable = true)
    private Date date;
}
