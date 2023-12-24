package com.example.sinzi_assignment.post.repository;

import com.example.sinzi_assignment.post.dto.PostDto;
import com.example.sinzi_assignment.post.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostCustomRepository {
    List<PostDto.PostResponseDto> findAllPostByBoard_cd(String boardCd);

    List<PostDto.PostResponseDto> getPostDtoByBoardCdAndPostNo(String boardCd, Long postNo);

    Optional<Post> getPostByBoardCdAndPostNo(String boardCd, Long postNo);

}
