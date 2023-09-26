package hello.board.repository;

import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.board.entity.Comment;
import hello.board.entity.Post;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static hello.board.entity.QComment.comment;


@Repository
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    public CommentRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    public Page<Comment> commentsOfPost(Post post, Pageable pageable) {
        List<Comment> fetch = jpaQueryFactory
                .selectFrom(comment)
                .where(comment.post.eq(post))
                .orderBy(comment.rootComment.id.desc(), comment.leftNode.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(comment.count())
                .from(comment)
                .where(comment.post.eq(post));

        return PageableExecutionUtils.getPage(fetch, pageable, countQuery::fetchOne);
    }

    public void saveReplyComment(Comment replyComment) {
        updateNodeNumber(replyComment.getLeftNode());

        em.persist(replyComment);
    }

    public long deleteByPost(Post post) {
        long leafCount = jpaQueryFactory
                .delete(comment)
                .where(comment.post.eq(post)
                        .and(comment.rootComment.ne(comment)))
                .execute();

        long rootCount = jpaQueryFactory
                .delete(comment)
                .where(comment.post.eq(post)
                        .and(comment.rootComment.eq(comment)))
                .execute();
        return leafCount + rootCount;
    }

    private void updateNodeNumber(Long nodeNumber) {
        jpaQueryFactory
                .update(comment)
                .set(comment.leftNode, new CaseBuilder()
                        .when(comment.leftNode.goe(nodeNumber))
                        .then(comment.leftNode.add(2))
                        .otherwise(comment.leftNode))
                .set(comment.rightNode, comment.rightNode.add(2))
                .where(comment.leftNode.goe(nodeNumber)
                        .or(comment.rightNode.goe(nodeNumber)))
                .execute();
    }
}
