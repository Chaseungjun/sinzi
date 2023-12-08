package com.example.sinzi_assignment.tag.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class TagDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class TagRegisterDto {
        private String board_cd;
        private String tagName;

        @Builder
        public TagRegisterDto(
                final String board_cd,
                final String tagName
        ) {
            this.board_cd = board_cd;
            this.tagName = tagName;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class TagUpdateDto {
        private String board_cd;
        private String tag;

        @Builder
        public TagUpdateDto(
                final String board_cd,
                final String tag
        ) {
            this.board_cd = board_cd;
            this.tag = tag;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class TagResponseDto {
        private String tag;
        private String post_sj;
        private String post_cn;
        private String board_cd;
        private String regstr_id;
        private LocalDateTime reg_dt;
        @Builder
        public TagResponseDto(
                final String tag,
                final String post_sj,
                final String post_cn,
                final String board_cd,
                final String regstr_id,
                final LocalDateTime reg_dt
        ) {
            this.tag = tag;
            this.post_sj = post_sj;
            this.post_cn = post_cn;
            this.board_cd = board_cd;
            this.regstr_id = regstr_id;
            this.reg_dt = reg_dt;
        }
    }

}
