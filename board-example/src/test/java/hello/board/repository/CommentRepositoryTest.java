package hello.board.repository;

import hello.board.entity.Comment;
import hello.board.entity.Post;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("comment가 저장된다.")
    void insertCommentTest() {
        Post post = new Post("title", "content");
        postRepository.save(post);

        String commentContent = "content";
        Comment comment = Comment.newComment(post, commentContent);
        commentRepository.save(comment);

        Page<Comment> comments = commentRepository.commentsOfPost(post, PageRequest.of(0, 10));
        List<Comment> content = comments.getContent();
        assertThat(content.size()).isEqualTo(1);
        assertThat(content.get(0).getContent()).isEqualTo(commentContent);
    }

    @Test
    @DisplayName("하나의 post에 comment를 두개 저장한다.")
    void insertCommentTwiceTest() {
        Post post = new Post("title", "content");
        postRepository.save(post);

        String commentContent = "content";

        Comment commentA = Comment.newComment(post, commentContent);
        commentRepository.save(commentA);
        Comment commentB = Comment.newComment(post, commentContent);
        commentRepository.save(commentB);

        Page<Comment> comments = commentRepository.commentsOfPost(post, PageRequest.of(0, 10));
        List<Comment> content = comments.getContent();
        assertThat(content.size()).isEqualTo(2);
        for (Comment comment : content) {
            assertThat(comment.getContent()).isEqualTo(commentContent);
        }
    }

    @Test
    @DisplayName("페이징을 통해 페이지 사이즈만큼 조회된다.")
    void selectUsingPageTest() {
        Post post = new Post("title", "content");
        postRepository.save(post);

        String commentContent = "content";

        for (int i = 0; i < 20; i++) {
            Comment comment = Comment.newComment(post, commentContent);
            commentRepository.save(comment);
        }

        Page<Comment> comments = commentRepository.commentsOfPost(post, PageRequest.of(0, 10));
        List<Comment> content = comments.getContent();
        assertThat(content.size()).isEqualTo(10);
        assertThat(comments.getTotalElements()).isEqualTo(20);
        assertThat(comments.getTotalPages()).isEqualTo(2);
        assertThat(comments.getNumber()).isEqualTo(0);
        assertThat(comments.getNumberOfElements()).isEqualTo(10);

        comments = commentRepository.commentsOfPost(post, PageRequest.of(1, 10));
        content = comments.getContent();
        assertThat(content.size()).isEqualTo(10);
        assertThat(comments.getTotalElements()).isEqualTo(20);
        assertThat(comments.getTotalPages()).isEqualTo(2);
        assertThat(comments.getNumber()).isEqualTo(1);
        assertThat(comments.getNumberOfElements()).isEqualTo(10);

        comments = commentRepository.commentsOfPost(post, PageRequest.of(2, 10));
        content = comments.getContent();
        assertThat(content.size()).isEqualTo(0);
        assertThat(comments.getTotalElements()).isEqualTo(20);
        assertThat(comments.getTotalPages()).isEqualTo(2);
        assertThat(comments.getNumber()).isEqualTo(2);
        assertThat(comments.getNumberOfElements()).isEqualTo(0);
    }

    @Test
    @DisplayName("대댓글 작성")
    void saveReplyCommentTest() {
        Post post = new Post("title", "content");
        postRepository.save(post);

        String commentContent = "content";
        Comment comment = Comment.newComment(post, commentContent);
        commentRepository.save(comment);

        Comment replyComment = comment.newReplyComment("newContent");
        commentRepository.saveReplyComment(replyComment);

        List<Comment> all = commentRepository.findAll();
        assertThat(all.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("대댓글 작성시 순서가 재정렬된다.")
    void sortedWhenSaveReplyCommentTest() {
        Post post = new Post("title", "content");
        postRepository.save(post);

        String commentContent = "content";
        Comment commentA = Comment.newComment(post, commentContent);
        commentRepository.save(commentA);

        Comment commentB = Comment.newComment(post, commentContent);
        commentRepository.save(commentB);

        Comment replyCommentA = commentA.newReplyComment("newContent");
        commentRepository.saveReplyComment(replyCommentA);

        List<Comment> content = commentRepository.commentsOfPost(post, PageRequest.of(0, 10)).getContent();
        assertThat(content.get(0)).isEqualTo(commentB);
        assertThat(content.get(1)).isEqualTo(replyCommentA);
        assertThat(content.get(2)).isEqualTo(commentA);
    }

    @Test
    @DisplayName("대댓글의 최대 깊이는 5이다.")
    void maximumReplyCommentTest() {
        Post post = new Post("title", "content");
        postRepository.save(post);

        String commentContent = "content";
        Comment commentA = Comment.newComment(post, commentContent);
        commentRepository.save(commentA);
        Comment tmp = commentA;
        for (int i = 0; i < 5; i++) {
            Comment replyCommentA = tmp.newReplyComment("newContent");
            commentRepository.saveReplyComment(replyCommentA);
            tmp = replyCommentA;
        }

        Comment finalTmp = tmp;
        assertThatThrownBy(() -> finalTmp.newReplyComment("OverMaximumReplyComment"))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("대댓글을 작성할 때마다 노드번호가 두개 씩 증가한다.")
    void increaseNodeNumberTest() {
        Post post = new Post("title", "content");
        postRepository.save(post);

        String commentContent = "content";
        Comment commentA = Comment.newComment(post, commentContent);
        commentRepository.save(commentA);

        for (int i = 1; i <= 2; i++) {
            List<Comment> resultList = em.createQuery("select c from Comment c where c.id = :commentId and (c.leftNode = :nodeNumber or c.rightNode = :nodeNumber)", Comment.class)
                    .setParameter("commentId", commentA.getId())
                    .setParameter("nodeNumber", i)
                    .getResultList();

            assertThat(resultList.size()).isEqualTo(1);
        }

        Comment commentB = Comment.newComment(post, commentContent);
        commentRepository.save(commentB);

        for (int i = 1; i <= 2; i++) {
            List<Comment> resultList = em.createQuery("select c from Comment c where c.id = :commentId and (c.leftNode = :nodeNumber or c.rightNode = :nodeNumber)", Comment.class)
                    .setParameter("commentId", commentB.getId())
                    .setParameter("nodeNumber", i)
                    .getResultList();

            assertThat(resultList.size()).isEqualTo(1);
        }

        Comment replyCommentA = saveReplyAndClear(commentA, "newContent");

        for (int i = 1; i <= 4; i++) {
            List<Comment> resultList = em.createQuery("select c from Comment c where c.rootComment = :rootComment and (c.leftNode = :nodeNumber or c.rightNode = :nodeNumber)", Comment.class)
                    .setParameter("rootComment", commentA)
                    .setParameter("nodeNumber", i)
                    .getResultList();

            assertThat(resultList.size()).isEqualTo(1);
        }

        Comment rereplyCommentA = saveReplyAndClear(replyCommentA, "newReplyContent");

        Comment replyCommentB = saveReplyAndClear(commentB, "newContent");

        for (int i = 1; i <= 6; i++) {
            List<Comment> resultList = em.createQuery("select c from Comment c where c.rootComment = :rootComment and (c.leftNode = :nodeNumber or c.rightNode = :nodeNumber)", Comment.class)
                    .setParameter("rootComment", commentA)
                    .setParameter("nodeNumber", i)
                    .getResultList();

            assertThat(resultList.size()).isEqualTo(1);
        }

        for (int i = 1; i <= 4; i++) {
            List<Comment> resultList = em.createQuery("select c from Comment c where c.rootComment = :rootComment and (c.leftNode = :nodeNumber or c.rightNode = :nodeNumber)", Comment.class)
                    .setParameter("rootComment", commentB)
                    .setParameter("nodeNumber", i)
                    .getResultList();

            assertThat(resultList.size()).isEqualTo(1);
        }
    }

    private Comment saveReplyAndClear(Comment replyCommentA, String newReplyContent) {
        Comment rereplyCommentA = replyCommentA.newReplyComment(newReplyContent);
        commentRepository.saveReplyComment(rereplyCommentA);

        em.flush();
        em.clear();
        return rereplyCommentA;
    }
}
