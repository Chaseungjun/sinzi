package com.example.sinzi_assignment.post.service;

import com.example.sinzi_assignment.post.dto.PostDto.PostRegisterDto;
import com.example.sinzi_assignment.post.dto.PostDto.PostResponseDto;
import com.example.sinzi_assignment.post.dto.PostDto.PostUpdateDto;
import com.example.sinzi_assignment.post.entity.Post;
import com.example.sinzi_assignment.post.repository.PostRepository;
import com.example.sinzi_assignment.post.service.command.PostCommand;
import com.example.sinzi_assignment.postTag.entity.PostTag;
import com.example.sinzi_assignment.postTag.service.command.PostTagCommand;
import com.example.sinzi_assignment.tag.dto.TagDto;
import com.example.sinzi_assignment.tag.entity.Tag;
import com.example.sinzi_assignment.tag.service.command.TagCommand;
import com.example.sinzi_assignment.tag.service.query.TagQuery;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostCommand postCommand;

    private final TagCommand tagCommand;
    private final TagQuery tagQuery;
    private final PostTagCommand postTagCommand;
    private final PostRepository postRepository;

    @Transactional
    public PostResponseDto register(final PostRegisterDto registerDto) {

        // 1. 태그(Tag) 객체 생성 및 저장
        List<Tag> tagList = registerDto.getTagDtoList().stream()
                .map(tagName ->
                        tagQuery.findByBoard_cdAndTag(registerDto.getBoard_cd(), tagName)
                                .orElse(Tag.builder()
                                        .tag(tagName)
                                        .board_cd(registerDto.getBoard_cd())
                                        .build())
                )
                .peek(tagCommand::save)
                .collect(Collectors.toList());


        // 2. 게시글(Post) 객체 생성 및 저장
        Post post = Post.builder()
                .post_sj(registerDto.getPost_sj())
                .post_cn(registerDto.getPost_cn())
                .board_cd(registerDto.getBoard_cd())
                .build();
        postCommand.save(post);

        // 3. PostTags 객체 생성 및 저장
        List<PostTag> postTagList = tagList.stream()
                .map(tag -> PostTag.builder()
                        .post(post)
                        .tag(tag)
                        .build()
                )
                .collect(Collectors.toList());
        postTagCommand.saveAll(postTagList);

        return PostResponseDto.builder()
                .post_sj(post.getPost_sj())
                .post_cn(post.getPost_cn())
                .board_cd(post.getBoard_cd())
                .regstr_id(post.getRegstr_id())
                .reg_dt(post.getReg_dt())
                .tagResponseDtoList(
                        tagList.stream().map(tag ->
                                        TagDto.TagResponseDto.builder()
                                                .tag_no(tag.getTag_no())
                                                .board_cd(tag.getBoardCd())
                                                .tag(tag.getTag())
                                                .build())
                                .collect(Collectors.toList())
                )
                .build();
    }

    public List<PostResponseDto> findAllPostByBoard_cd(final String boardCd) {
        return postRepository.findAllPostByBoard_cd(boardCd);
    }


    public List<PostResponseDto> getPostDtoByBoardCdAndPostNo(final String boardCd, final Long postNo) {
        return postRepository.getPostDtoByBoardCdAndPostNo(boardCd, postNo);
    }

    @Transactional
    public PostResponseDto update(final PostUpdateDto updateDto) {
        Post post = postRepository.getPostByBoardCdAndPostNo(updateDto.getBoard_cd(), updateDto.getPost_no()).orElseThrow(EntityNotFoundException::new);
        post.updatePost(updateDto.getPost_sj(), updateDto.getBoard_cd());

        // 1. 기존 태그 가져오기
        List<Tag> existingTags = post.getPostTags().stream()
                .map(PostTag::getTag)
                .collect(Collectors.toList());

        // 2. 새로운 태그 생성 및 저장
        List<Tag> newTags = updateDto.getTagDtoList().stream()
                .map(newTag ->
                        tagQuery.findByBoard_cdAndTag(updateDto.getBoard_cd(), newTag)
                                .orElse(Tag.builder()
                                        .tag(newTag)
                                        .board_cd(updateDto.getBoard_cd())
                                        .build())
                )
                .peek(tagCommand::save)
                .collect(Collectors.toList());

        // 3. 새로운 태그와 기존 태그 비교하여 업데이트
        List<Tag> tagsToAdd = newTags.stream()
                .filter(newTag -> !existingTags.contains(newTag)) // 새로운 태그가 기존 게시글의 태그리스트에 존재하지 않으면 리스트에 추가
                .collect(Collectors.toList());

        List<Tag> tagsToRemove = existingTags.stream()
                .filter(existingTag -> !newTags.contains(existingTag))
                //기존 게시글의 태그리스트 중에서 새로운 태그 목록에 포함되지 않은 태그들을 리스트에 추가. 기존 태그 중에서 새로운 태그 목록에 없어진 기존 태그들이 포함된다
                .collect(Collectors.toList());

        // 태그 추가
        tagsToAdd.forEach(post::addTag);

        // 태그 제거
        tagsToRemove.forEach(post::removeTag);

        // 기존의 태그는 DB에서 제거
        tagsToRemove.forEach(tagCommand::delete);

        return PostResponseDto.builder()
                .post_sj(post.getPost_sj())
                .post_cn(post.getPost_cn())
                .board_cd(post.getBoard_cd())
                .regstr_id(post.getRegstr_id())
                .reg_dt(post.getReg_dt())
                .tagResponseDtoList(
                        newTags.stream().map(tag ->
                                        TagDto.TagResponseDto.builder()
                                                .tag_no(tag.getTag_no())
                                                .tag(tag.getTag())
                                                .board_cd(tag.getBoardCd())
                                                .build()
                                )
                                .collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public void delete(final String boardCd, final Long postNo) {
        Post post = postRepository.getPostByBoardCdAndPostNo(boardCd, postNo).orElseThrow(EntityNotFoundException::new);
        postCommand.delete(post);
    }
}
