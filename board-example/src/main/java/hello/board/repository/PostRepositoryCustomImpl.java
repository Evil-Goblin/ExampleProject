package hello.board.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.board.dto.PostListDto;
import hello.board.dto.QPostListDto;
import hello.board.dto.SearchCond;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static hello.board.entity.QPost.post;

public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public PostRepositoryCustomImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    public Page<PostListDto> findPostListAll(Pageable pageable) {
        List<PostListDto> postList = jpaQueryFactory
                .select(new QPostListDto(post.id.as("postId"), post.title))
                .from(post)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(post.id.desc())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(post.count())
                .from(post);

        return PageableExecutionUtils.getPage(postList, pageable, countQuery::fetchOne);
    }

    public Page<PostListDto> findPostListBySearchCond(SearchCond searchCond, Pageable pageable) {
        BooleanExpression conditionBy = conditionBy(searchCond);
        List<PostListDto> postList = jpaQueryFactory
                .select(new QPostListDto(post.id.as("postId"), post.title))
                .from(post)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .where(conditionBy)
                .orderBy(post.id.desc())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(post.count())
                .from(post)
                .where(conditionBy);

        return PageableExecutionUtils.getPage(postList, pageable, countQuery::fetchOne);
    }

    private BooleanExpression conditionBy(SearchCond searchCond) {
        switch (searchCond.getSearchBy()) {
            case TITLE -> {
                return post.title.like(searchCond.getWord());
            }
            case CONTENT -> {
                return post.content.like(searchCond.getWord());
            }
        }
        return null;
    }
}
