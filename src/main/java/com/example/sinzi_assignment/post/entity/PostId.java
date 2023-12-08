package com.example.sinzi_assignment.post.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Getter
@EqualsAndHashCode(of = {"board_cd", "post_no"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostId implements Serializable {

    private String board_cd;
    private int post_no;

    public PostId(final int post_no, final String board_cd) {
        this.post_no = post_no;
        this.board_cd = board_cd;
    }

    public PostId(final String board_cd) {
        this.board_cd = board_cd;
    }
}
