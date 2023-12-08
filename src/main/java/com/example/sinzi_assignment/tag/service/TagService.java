package com.example.sinzi_assignment.tag.service;

import com.example.sinzi_assignment.post.dto.PostDto;
import com.example.sinzi_assignment.postTag.entity.PostTag;
import com.example.sinzi_assignment.tag.entity.Tag;
import com.example.sinzi_assignment.tag.service.query.TagQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagQuery tagQuery;

    public List<PostDto.PostResponseDto> getPostsByTag(final String board_cd, final String tagName) {
        Optional<Tag> tagOptional = tagQuery.findByBoard_cdAndTag(board_cd, tagName);

        if (tagOptional.isPresent()) {
            List<PostTag> postTags = tagOptional.get().getPostTags();

            return postTags.stream()
                    .map(postTag -> PostDto.PostResponseDto.builder()
                            .post_sj(postTag.getPost().getPost_sj())
                            .post_cn(postTag.getPost().getPost_cn())
                            .board_cd(postTag.getBoard_cd())
                            .regstr_id(postTag.getPost().getRegstr_id())
                            .reg_dt(postTag.getPost().getReg_dt())
                            .build())
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}
