package com.example.sinzi_assignment.post.repository;

import com.example.sinzi_assignment.post.dto.PostDto;
import com.example.sinzi_assignment.post.entity.Post;
import com.example.sinzi_assignment.tag.entity.QTag;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static com.example.sinzi_assignment.post.entity.QPost.post;
import static com.example.sinzi_assignment.postTag.entity.QPostTag.postTag;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

public class PostCustomRepositoryImpl implements PostCustomRepository {

    public PostCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private JPAQueryFactory queryFactory;

    @Override
    public List<PostDto.PostResponseDto> findAllPostByBoard_cd(String boardCd) {
        return queryFactory
                .selectFrom(post)
                .leftJoin(post.postTags, postTag)
                .leftJoin(postTag.tag, QTag.tag1)
                .where(post.board_cd.eq(boardCd))
                .distinct()
                .transform(groupBy(post.post_no).list(
                        Projections.constructor(
                                PostDto.PostResponseDto.class,
                                post.post_sj,
                                post.post_cn,
                                post.board_cd,
                                post.regstr_id,
                                post.reg_dt,
                                Projections.list(postTag.tag.tag)
                        )
                ));
    }

    @Override
    public List<PostDto.PostResponseDto> getPostDtoByBoardCdAndPostNo(String boardCd, Long postNo) {
        return queryFactory
                .selectFrom(post)
                .leftJoin(post.postTags, postTag)
                .leftJoin(postTag.tag, QTag.tag1)
                .where(post.board_cd.eq(boardCd).and(post.post_no.eq(postNo)))
                .distinct()
                .transform(groupBy(post.post_no).list(
                        Projections.constructor(
                                PostDto.PostResponseDto.class,
                                post.post_sj,
                                post.post_cn,
                                post.board_cd,
                                post.regstr_id,
                                post.reg_dt,
                                Projections.list(postTag.tag.tag)
                        )
                ));


    }

    @Override
    public Optional<Post> getPostByBoardCdAndPostNo(String boardCd, Long postNo) {
        Post result = queryFactory.selectFrom(post)
                .leftJoin(post.postTags, postTag).fetchJoin()
                .where(post.post_no.eq(postNo).and(post.board_cd.eq(boardCd)))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
