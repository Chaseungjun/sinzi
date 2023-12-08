package com.example.sinzi_assignment.postTag.entity;

import com.example.sinzi_assignment.boardDef.entity.BoardDef;
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

    @Id @Comment("(PK)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_TAG_ID")
    private Integer board_tag_id;

    @Comment("(FK)")
    @Column(name = "POST_NO")
    private int post_no;

    @Comment("(FK)")
    @Column(name = "BOARD_CD")
    private String board_cd;

    @Comment("(FK)")
    @Column(name = "TAG_NO")
    private int tag_no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "POST_NO", referencedColumnName = "POST_NO", insertable = false, updatable = false,
                    foreignKey = @ForeignKey(name = "fk_post_of_postNo")),
            @JoinColumn(name = "BOARD_CD", referencedColumnName = "BOARD_CD", insertable = false, updatable = false,
                    foreignKey = @ForeignKey(name = "fk_post_of_board_cd"))
    })
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TAG_NO", insertable = false, updatable = false, referencedColumnName = "TAG_NO",
            foreignKey = @ForeignKey(name = "fk_post_of_tag"))
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_CD", referencedColumnName  = "BOARD_CD", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk_postTag_of_board_cd"))
    private BoardDef boardDef;

    @Builder
    public PostTag(int post_no, String board_cd, int tag_no, Post post, Tag tag, BoardDef boardDef) {
        this.post_no = post_no;
        this.board_cd = board_cd;
        this.tag_no = tag_no;
        this.post = post;
        this.tag = tag;
        this.boardDef = boardDef;
    }
    @Builder
    public PostTag(Post post, Tag tag) {
        this.post = post;
        this.tag = tag;
        this.board_cd = post.getBoard_cd();
    }

    public void remove_Tag(){
        if (this.tag != null){
            this.tag.getPostTags().remove(this);
        }
    }
    public void remove_Post(){
        if (this.post != null){
            this.post.getPostTags().remove(this);
        }
    }

}
