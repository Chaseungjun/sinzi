package com.example.sinzi_assignment.tag.service.command;

import com.example.sinzi_assignment.tag.entity.Tag;
import com.example.sinzi_assignment.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagCommand {

    private final TagRepository tagRepository;

    public void save(final Tag tag){
        tagRepository.save(tag);
    }


}
