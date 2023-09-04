package com.oldteam.movienote.api.domain.movie.repository;

import com.oldteam.movienote.api.domain.movie.condition.MovieReviewSearchCondition;
import com.oldteam.movienote.core.domain.movie.MovieReview;
import com.oldteam.movienote.core.utils.Querydsl5RepositorySupport;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    public MovieReviewQuerydslRepositoryImpl() {
        super(MovieReview.class);
    }

    @Override
    public Page<MovieReview> findAllByCondition(MovieReviewSearchCondition searchCondition, Pageable pageable) {
        return applyPagination(pageable, query -> query
                .selectFrom(movieReview)
                .leftJoin(movieReview.movie).fetchJoin()
                .innerJoin(movieReview.member, member).fetchJoin()
                .leftJoin(member.uploadFile).fetchJoin()
                .where(
                        containsQuery(searchCondition.getQuery()),
                        isLike(searchCondition.getIsLike(), searchCondition.getMemberId())
                )
        );
    }

    private BooleanExpression containsQuery(String query) {
        return StringUtils.isNotEmpty(query) ? movieReview.title.contains(query).or(movieReview.content.contains(query)) : null;
    }

    private BooleanExpression isLike(Boolean isLike, Long memberId) {
        if (!isLike || memberId == null) {
            return null;
        }

        return movieReview.likeList.any().movieReview.member.id.eq(memberId);

    }


}

