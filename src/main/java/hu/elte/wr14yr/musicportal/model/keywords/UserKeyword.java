package hu.elte.wr14yr.musicportal.model.keywords;

import hu.elte.wr14yr.musicportal.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "USER_KEYWORDS")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserKeyword extends Keyword {

    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = "USER_ID")
    private User user;
}
