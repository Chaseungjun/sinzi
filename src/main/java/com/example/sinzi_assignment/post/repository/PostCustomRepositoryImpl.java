package com.example.sinzi_assignment.post.repository;

import com.example.sinzi_assignment.post.dto.PostDto;
import com.example.sinzi_assignment.post.dto.QPostDto_PostResponseDto;
import com.example.sinzi_assignment.post.entity.PostId;
import com.example.sinzi_assignment.post.entity.QPost;
import com.example.sinzi_assignment.postTag.entity.QPostTag;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import com.querydsl.core.types.dsl.*;

import java.util.List;
import java.util.Optional;

import static com.example.sinzi_assignment.post.entity.QPost.post;
import static com.example.sinzi_assignment.postTag.entity.QPostTag.postTag;
import static com.querydsl.core.group.GroupBy.list;


public class PostCustomRepositoryImpl implements PostCustomRepository {

    public PostCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private JPAQueryFactory queryFactory;

    @Override
    public List<PostDto.PostResponseDto> findAllPostByBoard_cd(String boardCd) {
        return queryFactory.select(
                        new QPostDto_PostResponseDto(
                                post.post_sj,
                                post.post_cn,
                                post.board_cd,
                                post.regstr_id,
                                post.reg_dt,
                                list(postTag.tag.tag)
                        )
                ).from(post)
                .leftJoin(post.postTags, postTag).fetchJoin()
                .where(post.board_cd.eq(boardCd))
                .fetch();
    }

    @Override
    public Optional<PostDto.PostResponseDto> getPostByPostId(PostId postId) {
        PostDto.PostResponseDto result = queryFactory.select(
                        new QPostDto_PostResponseDto(
                                post.post_sj,
                                post.post_cn,
                                post.board_cd,
                                post.regstr_id,
                                post.reg_dt,
                                list(postTag.tag.tag)

                        )
                ).from(post)
                .where(post.post_no.eq(postId.getPost_no()).and(post.board_cd.eq(postId.getBoard_cd())))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
