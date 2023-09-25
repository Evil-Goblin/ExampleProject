package hello.board.entity;

import jakarta.persistence.*;
import lombok.*;

@ToString(of = {"id", "content", "active"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseEntity {

    public static final int MAX_DEPTH = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Lob
    private String content;

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

    public Comment(Post post, String content) {
        this.post = post;
        this.content = content;
    }

    @Builder
    private Comment(Post post, String content, Long depth, Long leftNode, Long rightNode, Comment rootComment, Comment parent) {
        this.post = post;
        this.content = content;
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

    public Comment newReplyComment(String content) {
        if (this.depth >= MAX_DEPTH) {
            throw new IllegalStateException(/*MAXIMUM depth*/);
        }

        return Comment.builder()
                .post(this.post)
                .content(content)
                .depth(this.depth + 1)
                .leftNode(this.rightNode)
                .rightNode(this.rightNode + 1)
                .rootComment(this.rootComment)
                .parent(this)
                .build();
    }

    private void setRootComment(Comment comment) {
        this.rootComment = comment;
    }

    public String getContent() {
        return content;
    }

    public Long getLeftNode() {
        return leftNode;
    }
}
