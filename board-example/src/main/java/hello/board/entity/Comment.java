package hello.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Lob
    private String comment;

    @Column(nullable = false)
    private Long depth = 0L;

    @Column(nullable = false)
    private Long leftNode = 1L;

    @Column(nullable = false)
    private Long rightNode = 2L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "root_comment_id")
    private Comment rootComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parent;

    @Column(nullable = false)
    private boolean active = true;

    public Comment(Post post, String comment) {
        this.post = post;
        this.comment = comment;
    }

    @Builder
    private Comment(Post post, String comment, Long depth, Long leftNode, Long rightNode, Comment rootComment, Comment parent) {
        this.post = post;
        this.comment = comment;
        this.depth = depth;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.rootComment = rootComment;
        this.parent = parent;
    }

    static public Comment newComment(Post post, String comment) {
        Comment comment1 = new Comment(post, comment);
        comment1.setRootComment(comment1);
        return comment1;
    }

    public Comment newReplyComment(Post post, String comment) {
        return Comment.builder()
                .post(post)
                .comment(comment)
                .depth(this.depth++)
                .leftNode(this.rightNode)
                .rightNode(this.rightNode + 1)
                .rootComment(this.rootComment)
                .parent(this)
                .build();
    }

    private void setRootComment(Comment comment) {
        this.rootComment = comment;
    }
}
