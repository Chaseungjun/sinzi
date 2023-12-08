package com.example.sinzi_assignment.boardDef.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;


@Entity
@Getter
@Table(name = "BOARD_DEF")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardDef {

    @Id @Comment("(PK)")
    @Column(name = "BOARD_CD")
    private String board_cd;

    @Column(name = "BOARD_NM")
    private String board_nm;
}
