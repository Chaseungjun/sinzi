package com.example.sinzi_assignment.post.repository;

import com.example.sinzi_assignment.post.entity.Post;
import com.example.sinzi_assignment.post.entity.PostId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, PostId> , PostCustomRepository{
}
