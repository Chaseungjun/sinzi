package com.example.sinzi_assignment.post.controller;

import com.example.sinzi_assignment.post.dto.PostDto;
import com.example.sinzi_assignment.post.entity.PostId;
import com.example.sinzi_assignment.post.service.PostService;
import com.example.sinzi_assignment.tag.dto.TagDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping("/register")
    public ResponseEntity<PostDto.PostResponseDto> savePost(@RequestBody @Valid final PostDto.PostRegisterDto registerDto,
                                                            final List<TagDto.TagRegisterDto> tagDtoList) {
        return ResponseEntity.ok(postService.register(registerDto, tagDtoList));
    }

    @GetMapping("/list/{boardCd}")
    public ResponseEntity<List<PostDto.PostResponseDto>> findAllPostByBoard_cd(@PathVariable final String boardCd) {
        return ResponseEntity.ok(postService.findAllPostByBoard_cd(boardCd));
    }

    @GetMapping("/{postNo}/{boardCd}")
    public ResponseEntity<PostDto.PostResponseDto> getPostByPostId(@PathVariable final Integer postNo,
                                                               @PathVariable final String boardCd) {
        return ResponseEntity.ok(postService.getPostByPostId(new PostId(postNo, boardCd)));
    }

    @PatchMapping("/update")
    public ResponseEntity<PostDto.PostResponseDto> updatePost(@RequestBody @Valid final PostDto.PostUpdateDto updateDto,
                                                              final List<TagDto.TagRegisterDto> newTagDtoList) {
        return ResponseEntity.ok(postService.update(updateDto, newTagDtoList));
    }

    @DeleteMapping("/remove/{postNo}/{boardCd}")
    public ResponseEntity<Void> deletePost(@PathVariable final Integer postNo, @PathVariable final String boardCd) {
        postService.delete(new PostId(postNo, boardCd));
       return ResponseEntity.noContent().build();
    }
}
