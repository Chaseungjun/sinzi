package com.example.sinzi_assignment.postTag.entity;

import com.example.sinzi_assignment.post.entity.Post;
import com.example.sinzi_assignment.tag.entity.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Table(name = "POST_TAG")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostTag {

    @Id
    @Comment("(PK)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_TAG_ID")
    private Integer board_tag_id;

//    @Comment("(FK)")
//    @Column(name = "POST_NO")
//    private Long post_no;

    @Comment("(FK)")
    @Column(name = "BOARD_CD")
    private String board_cd;

//    @Comment("(FK)")
//    @Column(name = "TAG_NO")
//    private int tag_no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_NO",
            foreignKey = @ForeignKey(name = "fk_post_of_postNo"))
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TAG_NO", referencedColumnName = "TAG_NO",
            foreignKey = @ForeignKey(name = "fk_post_of_tag"))
    private Tag tag;

    @Builder
    public PostTag(Post post, Tag tag) {
        this.post = post;
        this.tag = tag;
        this.board_cd = post.getBoard_cd();
    }

    public void remove_Tag() {
        if (this.tag != null) {
            this.tag.getPostTags().remove(this);
        }
    }

    public void remove_Post() {
        if (this.post != null) {
            this.post.getPostTags().remove(this);
        }
    }
}
