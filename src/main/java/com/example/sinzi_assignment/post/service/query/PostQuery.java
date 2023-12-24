package com.example.sinzi_assignment.post.service.query;

import com.example.sinzi_assignment.post.entity.Post;
import com.example.sinzi_assignment.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostQuery {

    private final PostRepository postRepository;

//    public Optional<Post> findByBoard_cdAndPost_no(final String boardCd, final Long postNo){
//       return postRepository.findByBoard_cdAndPost_no(boardCd, postNo);
//    }

}
