package com.example.sinzi_assignment.post.repository;

import com.example.sinzi_assignment.post.dto.PostDto;
import com.example.sinzi_assignment.post.entity.PostId;

import java.util.List;
import java.util.Optional;

public interface PostCustomRepository {
    List<PostDto.PostResponseDto> findAllPostByBoard_cd(String boardCd);

    Optional<PostDto.PostResponseDto> getPostByPostId(PostId postId);
}
