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
        private String tagName;

        @Builder
        public TagRegisterDto(
                final String tagName
        ) {
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
        private Integer tag_no;
        private String tag;
        private String board_cd;

        @Builder
        public TagResponseDto(
                final Integer tag_no,
                final String tag,
                final String board_cd
        ) {
            this.tag_no = tag_no;
            this.tag = tag;
            this.board_cd = board_cd;
        }
    }

}
