package hu.elte.wr14yr.musicportal.model.keywords;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hu.elte.wr14yr.musicportal.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "USER_KEYWORDS")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"user"})
public class UserKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "WORD", nullable = false)
    private String word;

    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = "USER_ID")
    private User user;
}
