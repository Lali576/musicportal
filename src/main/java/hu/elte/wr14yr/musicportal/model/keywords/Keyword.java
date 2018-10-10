package hu.elte.wr14yr.musicportal.model.keywords;

import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "WORD", nullable = false)
    private String word;
}
