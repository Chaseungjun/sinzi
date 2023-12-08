package com.example.sinzi_assignment.tag.service.query;

import com.example.sinzi_assignment.tag.entity.Tag;
import com.example.sinzi_assignment.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagQuery {

    private final TagRepository tagRepository;

    public Optional<Tag> findByBoard_cdAndTag(final String board_cd, final String tagName){
        return tagRepository.findByBoardCdAndTag(board_cd, tagName);
    }
}
