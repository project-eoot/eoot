package com.ssafy.eoot.post.repo;

import com.ssafy.eoot.post.dto.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Posts, Long> {

}
