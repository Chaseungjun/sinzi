package com.example.sinzi_assignment.post.entity;

import com.example.sinzi_assignment.boardDef.entity.BoardDef;
import com.example.sinzi_assignment.postTag.entity.PostTag;
import com.example.sinzi_assignment.tag.entity.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "POST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(PostId.class)
public class Post {

    @Id @Comment("(PK)")
    @Column(name = "POST_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board1Seq")
    @SequenceGenerators({
            @SequenceGenerator(name = "board1Seq", sequenceName = "board1_sequence", initialValue = 1, allocationSize = 1),
            @SequenceGenerator(name = "board2Seq", sequenceName = "board2_sequence", initialValue = 1, allocationSize = 1)
    })
    private Integer post_no;

    @Id
    @Comment("(PK, FK)")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_CD", referencedColumnName = "BOARD_CD", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk_post_of_board_cd"))
    private BoardDef boardDef;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostTag> postTags = new ArrayList<>();

    @Builder
    public Post(String post_sj, String post_cn, String board_cd, List<PostTag> postTagList) {
        this.post_sj = post_sj;
        this.post_cn = post_cn;
        this.regstr_id = UUID.randomUUID().toString();
        this.board_cd = board_cd;
        this.postTags = postTagList;
    }

    public void updatePost(String post_sj, String post_cn){
        this.post_sj = post_sj;
        this.post_cn = post_cn;
    }

    public void addTag(final Tag tag) {
        PostTag postTag = new PostTag(this, tag);
        postTags.add(postTag);
        tag.getPostTags().add(postTag);
    }

    public void removeTag(Tag tag) {
        PostTag postTagToRemove = null;

        // PostTag 목록에서 제거할 PostTag를 찾음
        for (PostTag postTag : postTags) {
            if (postTag.getTag().equals(tag)) {
                postTagToRemove = postTag;
                break;
            }
        }

        // 찾은 PostTag가 있으면 제거
        if (postTagToRemove != null) {
            postTags.remove(postTagToRemove);
            tag.getPostTags().remove(postTagToRemove);
            postTagToRemove.remove_Post();
            postTagToRemove.remove_Tag();
        }
    }

}
