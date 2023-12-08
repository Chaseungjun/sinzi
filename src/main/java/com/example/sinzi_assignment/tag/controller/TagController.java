package com.example.sinzi_assignment.tag.controller;

import com.example.sinzi_assignment.post.dto.PostDto;
import com.example.sinzi_assignment.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    @GetMapping("/{board_cd}/{tagName}")
    public List<PostDto.PostResponseDto> getPostsByTag(@PathVariable final String board_cd, @PathVariable final String tagName) {
        return tagService.getPostsByTag(board_cd, tagName);
    }
}
