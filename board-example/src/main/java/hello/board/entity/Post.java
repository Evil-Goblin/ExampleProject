package hello.board.entity;

import jakarta.persistence.*;

@Entity
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Long id;

    private String title;

    @Lob
    private String content;
}
