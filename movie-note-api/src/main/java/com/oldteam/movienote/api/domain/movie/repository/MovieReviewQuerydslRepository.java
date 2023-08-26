package com.oldteam.movienote.api.domain.movie.repository;

import com.oldteam.movienote.api.domain.movie.condition.MovieReviewSearchCondition;
import com.oldteam.movienote.core.domain.movie.MovieReview;
import com.oldteam.movienote.core.utils.Querydsl5RepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.oldteam.movienote.core.domain.member.QMember.member;
import static com.oldteam.movienote.core.domain.movie.QMovieReview.movieReview;

public interface MovieReviewQuerydslRepository {
    Page<MovieReview> findAllByCondition(MovieReviewSearchCondition searchCondition, Pageable pageable);
}

@Slf4j
@Repository
class MovieReviewQuerydslRepositoryImpl extends Querydsl5RepositorySupport implements MovieReviewQuerydslRepository {

    public MovieReviewQuerydslRepositoryImpl(Class<?> domainClass) {
        super(MovieReview.class);
    }

    @Override
    public Page<MovieReview> findAllByCondition(MovieReviewSearchCondition searchCondition, Pageable pageable) {
        return applyPagination(pageable, query -> query
                .selectFrom(movieReview)
                .innerJoin(movieReview.movie).fetchJoin()
                .innerJoin(movieReview.member, member).fetchJoin()
                .leftJoin(member.uploadFile).fetchJoin()
                .where(
                        containsContent(searchCondition.getQuery()),
                        containsTitle(searchCondition.getQuery())
                )
        );
    }

    private BooleanExpression containsContent(String query) {
        return query != null ? movieReview.content.contains(query) : null;
    }

    private BooleanExpression containsTitle(String query) {
        return query != null ? movieReview.title.contains(query) : null;
    }


}

