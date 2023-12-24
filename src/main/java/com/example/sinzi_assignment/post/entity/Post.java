package com.example.sinzi_assignment.post.entity;

import com.example.sinzi_assignment.postTag.entity.PostTag;
import com.example.sinzi_assignment.tag.entity.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "POST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @Comment("(PK)")
    @Column(name = "POST_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_no;

    @Comment("(FK)")
    @Column(name = "BOARD_CD")
    private String board_cd;

    @Column(name = "POST_SJ")
    private String post_sj;

    @Column(name = "POST_CN")
    private String post_cn;

    @Column(name = "REGSTR_ID")
    private String regstr_id;

    @Column(name = "REG_DT")
    @CreationTimestamp
    private LocalDateTime reg_dt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostTag> postTags = new ArrayList<>();

    @Builder
    public Post(final String post_sj, final String post_cn, final String board_cd, final List<PostTag> postTagList) {
        this.post_sj = post_sj;
        this.post_cn = post_cn;
        this.regstr_id = UUID.randomUUID().toString();
        this.board_cd = board_cd;
        this.postTags = postTagList;
    }

    public void updatePost(final String post_sj, final String post_cn) {
        this.post_sj = post_sj;
        this.post_cn = post_cn;
    }

    public void addTag(final Tag tag) {
        PostTag postTag = new PostTag(this, tag);
        postTags.add(postTag);  // 태그객체를 게시글의 postTags 컬렉션에 추가
        tag.getPostTags().add(postTag);  //  태그객체를 태그의 postTags 컬렉션에도 추가
    }

    public void removeTag(final Tag tag) {
        postTags.stream()
                .filter(postTag -> postTag.getTag().equals(tag))
                .findFirst()
                .ifPresent(postTagToRemove -> {
                    postTags.remove(postTagToRemove);
                    postTagToRemove.remove_Post();
                    postTagToRemove.remove_Tag();
                });
    }

}
