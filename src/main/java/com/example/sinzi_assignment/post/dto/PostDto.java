package com.example.sinzi_assignment.post.dto;


import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

public class PostDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PostRegisterDto {
        @NotBlank(message = "제목을 입력해주세요")
        private String post_sj;
        @NotBlank(message = "내용을 입력해주세요")
        private String post_cn;
        @NotBlank
        private String board_cd;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PostUpdateDto {

        @NotBlank
        private int post_no;
        @NotBlank(message = "제목을 입력해주세요")
        private String post_sj;
        @NotBlank(message = "내용을 입력해주세요")
        private String post_cn;
        @NotBlank
        private String board_cd;

        @Builder
        public PostUpdateDto(
                final String post_sj,
                final String post_cn
        ) {
            this.post_sj = post_sj;
            this.post_cn = post_cn;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PostResponseDto {
        private String post_sj;
        private String post_cn;
        private String board_cd;
        private String regstr_id;
        private LocalDateTime reg_dt;
        private List<String> tagName;

        @Builder
        @QueryProjection
        public PostResponseDto(
                final String post_sj,
                final String post_cn,
                final String board_cd,
                final String regstr_id,
                final LocalDateTime reg_dt,
                final List<String> tagName
        ) {
            this.post_sj = post_sj;
            this.post_cn = post_cn;
            this.board_cd = board_cd;
            this.regstr_id = regstr_id;
            this.reg_dt = reg_dt;
            this.tagName = tagName;
        }
    }

}
