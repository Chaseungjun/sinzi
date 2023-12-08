package com.example.sinzi_assignment.post.service.query;

import com.example.sinzi_assignment.post.dto.PostDto;
import com.example.sinzi_assignment.post.entity.Post;
import com.example.sinzi_assignment.post.entity.PostId;
import com.example.sinzi_assignment.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostQuery {

    private final PostRepository postRepository;

    public Optional<Post> findById(final PostId postId){
        return postRepository.findById(postId);
    }

}
