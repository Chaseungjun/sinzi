package com.example.sinzi_assignment.tag.repository;

import com.example.sinzi_assignment.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {

     Optional<Tag> findByBoardCdAndTag(String board_cd, String tagName);
}
