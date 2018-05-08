package hu.elte.wr14yr.musicportal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"userTo"})
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

    @Column(name = "DATE_TIME", nullable = true)
    private LocalDateTime dateTime;
}
