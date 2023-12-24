package com.example.sinzi_assignment.tag.entity;

import com.example.sinzi_assignment.boardDef.entity.BoardDef;
import com.example.sinzi_assignment.post.entity.Post;
import com.example.sinzi_assignment.postTag.entity.PostTag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "TAG")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id @Comment("(PK)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAG_NO")
    private Integer tag_no;

    @Comment("(FK)")
    @Column(name = "BOARD_CD")
    private String boardCd;

    @Column(name = "TAG")
    private String tag;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "BOARD_CD", referencedColumnName  = "BOARD_CD",
//            foreignKey = @ForeignKey(name = "fk_tag_of_board_cd"))
//    private BoardDef boardDef;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    //CascadeType.ALL때문에 어느 한쪽의 엔티티가 저장되면 다른 쪽도 자동을 저장되기 때문에 빌더를 사용해서 값을 넣을 필요가 없다
    private List<PostTag> postTags = new ArrayList<>();

    public void addPost(final Post post) {
        PostTag postTag = new PostTag(post, this);
        postTags.add(postTag);
        post.getPostTags().add(postTag);
    }
    @Builder
    public Tag(final String board_cd, final String tag, final List<PostTag> postTags) {
        this.boardCd = board_cd;
        this.tag = tag;
        this.postTags = (postTags != null) ? postTags : new ArrayList<>();
    }
}
