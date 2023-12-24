package com.example.sinzi_assignment.post.controller;

import com.example.sinzi_assignment.post.dto.PostDto;
import com.example.sinzi_assignment.post.service.PostService;
import com.example.sinzi_assignment.tag.dto.TagDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping("/register")
    public ResponseEntity<PostDto.PostResponseDto> savePost(
            @RequestBody @Valid final PostDto.PostRegisterDto registerDto
    )
    {
        return ResponseEntity.ok(postService.register(registerDto));
    }

    @GetMapping("/list")
    public ResponseEntity<List<PostDto.PostResponseDto>> findAllPostByBoard_cd(@RequestParam final String boardCd) {
        return ResponseEntity.ok(postService.findAllPostByBoard_cd(boardCd));
    }

    @GetMapping("/")
    public ResponseEntity<List<PostDto.PostResponseDto>> getPostByBoardCdAndPostNo(
            @RequestParam final String boardCd,
            @RequestParam final Long postNo
    ) {
        return ResponseEntity.ok(postService.getPostDtoByBoardCdAndPostNo(boardCd, postNo));
    }

    @PatchMapping("/update")
    public ResponseEntity<PostDto.PostResponseDto> updatePost(
            @RequestBody @Valid final PostDto.PostUpdateDto updateDto
    )
    {
        return ResponseEntity.ok(postService.update(updateDto));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> deletePost(
            @RequestParam final String boardCd,
            @RequestParam final Long postNo
    )
    {
        postService.delete(boardCd, postNo);
        return ResponseEntity.noContent().build();
    }
}
