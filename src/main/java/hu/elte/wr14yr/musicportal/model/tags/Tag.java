package hu.elte.wr14yr.musicportal.model.tags;

import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "WORD", nullable = false)
    private String word;
}
