package com.example.sinzi_assignment.postTag.service.command;

import com.example.sinzi_assignment.postTag.entity.PostTag;
import com.example.sinzi_assignment.postTag.repository.PostTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostTagCommand {

    private final PostTagRepository postTagRepository;

    public PostTag save(PostTag postTag){
        return postTagRepository.save(postTag);
    }

    public List<PostTag> saveAll(List<PostTag> postTag){
        return postTagRepository.saveAll(postTag);
    }
}
