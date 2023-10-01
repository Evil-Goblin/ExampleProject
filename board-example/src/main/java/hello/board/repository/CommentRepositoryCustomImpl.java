package hello.board.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.board.dto.CommentListDto;
import hello.board.dto.QCommentListDto;
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

    public Page<CommentListDto> commentsOfPost(Post post, Pageable pageable) {
        List<CommentListDto> fetch = jpaQueryFactory
                .select(new QCommentListDto(comment.content, comment.depth))
                .from(comment)
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
        updateNodeNumber(replyComment);

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

    private void updateNodeNumber(Comment replyComment) {
        jpaQueryFactory
                .update(comment)
                .set(comment.leftNode, new CaseBuilder()
                        .when(leftNodeGOE(replyComment))
                        .then(comment.leftNode.add(2))
                        .otherwise(comment.leftNode))
                .set(comment.rightNode, comment.rightNode.add(2))
                .where(
                        rootCommentEqualTo(replyComment)
                                .and(
                                        leftNodeGOE(replyComment)
                                                .or(rightNodeGOE(replyComment))
                                )
                )
                .execute();
    }

    private BooleanExpression rightNodeGOE(Comment replyComment) {
        return comment.rightNode.goe(replyComment.getLeftNode());
    }

    private BooleanExpression leftNodeGOE(Comment replyComment) {
        return comment.leftNode.goe(replyComment.getLeftNode());
    }

    private BooleanExpression rootCommentEqualTo(Comment replyComment) {
        return comment.rootComment.eq(replyComment.getRootComment());
    }
}
