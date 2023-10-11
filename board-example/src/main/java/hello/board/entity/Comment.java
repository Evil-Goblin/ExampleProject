package hello.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString(of = {"id", "content", "leftNode", "rightNode", "active"})
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

    @Column(nullable = false)
    private boolean active = true;

    public Comment(Post post, String content) {
        this.post = post;
        this.content = content;
    }

    @Builder
    private Comment(Post post, String content, Long depth, Long leftNode, Long rightNode, Comment rootComment) {
        this.post = post;
        this.content = content;
        this.depth = depth;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.rootComment = rootComment;
    }

    static public Comment newComment(Post post, String content) {
        Comment comment = new Comment(post, content);
        comment.setRootComment(comment);

        return comment;
    }

    public Comment newReplyComment(String content) {
        if (this.depth >= MAX_DEPTH) {
            throw new IllegalStateException("작성 가능한 댓글 깊이를 초과하였습니다.");
        }

        return Comment.builder()
                .post(post)
                .content(content)
                .depth(depth + 1)
                .leftNode(rightNode)
                .rightNode(rightNode + 1)
                .rootComment(rootComment)
                .build();
    }

    public void deactivate() {
        active = false;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    private void setRootComment(Comment comment) {
        this.rootComment = comment;
    }
}
