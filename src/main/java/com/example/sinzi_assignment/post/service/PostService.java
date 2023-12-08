package com.example.sinzi_assignment.post.service;

import com.example.sinzi_assignment.post.dto.PostDto.PostRegisterDto;
import com.example.sinzi_assignment.post.dto.PostDto.PostResponseDto;
import com.example.sinzi_assignment.post.dto.PostDto.PostUpdateDto;
import com.example.sinzi_assignment.post.entity.Post;
import com.example.sinzi_assignment.post.entity.PostId;
import com.example.sinzi_assignment.post.repository.PostCustomRepository;
import com.example.sinzi_assignment.post.repository.PostRepository;
import com.example.sinzi_assignment.post.service.command.PostCommand;
import com.example.sinzi_assignment.post.service.query.PostQuery;
import com.example.sinzi_assignment.postTag.entity.PostTag;
import com.example.sinzi_assignment.postTag.service.command.PostTagCommand;
import com.example.sinzi_assignment.tag.dto.TagDto;
import com.example.sinzi_assignment.tag.entity.Tag;
import com.example.sinzi_assignment.tag.repository.TagRepository;
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
    private final PostQuery postQuery;
    private final PostRepository postRepository;
    private final TagCommand tagCommand;
    private final TagQuery tagQuery;
    private final TagRepository tagRepository;
    private final PostTagCommand postTagCommand;

    @Transactional
    public PostResponseDto register(final PostRegisterDto registerDto, final List<TagDto.TagRegisterDto> tagDtoList) {

        // 1. 태그(Tag) 객체 생성 및 저장
        List<Tag> tagList = tagDtoList.stream()
                .map(tagRegisterDto ->
                        tagQuery.findByBoard_cdAndTag(registerDto.getBoard_cd(), tagRegisterDto.getTagName())
                                .orElse(Tag.builder()
                                        .tag(tagRegisterDto.getTagName())
                                        .board_cd(registerDto.getBoard_cd())
                                        .build())
                )
                .peek(tagCommand::save)
                .collect(Collectors.toList());


        // 2. 게시글(Post) 객체 생성 및 저장
        Post post = Post.builder()
                .post_sj(registerDto.getPost_sj())
                .post_sj(registerDto.getPost_cn())
                .board_cd(registerDto.getBoard_cd())
                .build();
        postCommand.save(post);

        // 3. PostTags 객체 생성 및 저장
        List<PostTag> postTagList = tagList.stream()
                .map(tag -> PostTag.builder()
                        .post(post)
                        .tag(tag)
                        .board_cd(post.getBoard_cd())
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
                .tagName(tagList.stream().map(Tag::getTag).collect(Collectors.toList()))
                .build();
    }

    public List<PostResponseDto> findAllPostByBoard_cd(final String boardCd) {
        return postRepository.findAllPostByBoard_cd(boardCd);
    }


    public PostResponseDto getPostByPostId(final PostId postId) {
        return postRepository.getPostByPostId(postId).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public PostResponseDto update(final PostUpdateDto updateDto, final List<TagDto.TagRegisterDto> newTagDtoList) {
        Post post = postQuery.findById(new PostId(updateDto.getPost_no(), updateDto.getBoard_cd())).orElseThrow(EntityNotFoundException::new);
        post.updatePost(updateDto.getPost_sj(), updateDto.getBoard_cd());

        // 1. 기존 태그 가져오기
        List<Tag> existingTags = post.getPostTags().stream()
                .map(PostTag::getTag)
                .collect(Collectors.toList());

        // 2. 새로운 태그 생성 및 저장
        List<Tag> newTags = newTagDtoList.stream()
                .map(tagRegisterDto ->
                        tagRepository.findByBoardCdAndTag(updateDto.getBoard_cd(), tagRegisterDto.getTagName())
                                .orElse(Tag.builder()
                                        .tag(tagRegisterDto.getTagName())
                                        .board_cd(updateDto.getBoard_cd())
                                        .build())
                )
                .peek(tagCommand::save)
                .collect(Collectors.toList());

        // 3. 새로운 태그와 기존 태그 비교하여 업데이트
        List<Tag> tagsToAdd = newTags.stream()
                .filter(newTag -> !existingTags.contains(newTag))
                .collect(Collectors.toList());

        List<Tag> tagsToRemove = existingTags.stream()
                .filter(existingTag -> !newTags.contains(existingTag))
                .collect(Collectors.toList());

        // 태그 추가
        tagsToAdd.forEach(post::addTag);

        // 태그 제거
        tagsToRemove.forEach(post::removeTag);

        return PostResponseDto.builder()
                .post_sj(post.getPost_sj())
                .post_cn(post.getPost_cn())
                .board_cd(post.getBoard_cd())
                .regstr_id(post.getRegstr_id())
                .reg_dt(post.getReg_dt())
                .tagName(post.getPostTags().stream().map(pt -> pt.getTag().getTag()).collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public void delete(final PostId postId) {
        Post post = postQuery.findById(postId).orElseThrow(EntityNotFoundException::new);
        postCommand.delete(post);
    }

}
