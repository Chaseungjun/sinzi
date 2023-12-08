package com.example.sinzi_assignment.post.service.command;

import com.example.sinzi_assignment.post.entity.Post;
import com.example.sinzi_assignment.post.entity.PostId;
import com.example.sinzi_assignment.post.repository.PostRepository;
import com.example.sinzi_assignment.tag.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostCommand {

    private final PostRepository postRepository;

    public Post save(final Post post){
        return postRepository.save(post);
    }

    public void delete(final Post post){
        postRepository.delete(post);
    }
}
