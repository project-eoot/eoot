package com.ssafy.eoot.post.service;

import com.ssafy.eoot.exception.post.PostNotFoundException;
import com.ssafy.eoot.exception.post.UnauthorizedAccessException;
import com.ssafy.eoot.post.dto.PostDeleteForm;
import com.ssafy.eoot.post.dto.Post;
import com.ssafy.eoot.post.repo.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PostServiceV1 {
    // TODO: 추천 게시글 리스트 조회 메소드 작성
    private final PostRepository postRepository;

    @Transactional
    public void insertPost(Post post) {
        postRepository.save(post);
    }

    // TODO: User 완성 시 진짜 post.getUserId()로 변경
    @Transactional
    public void updatePost(Post post) {
        if (isPostAuthor("Post.getUserId()", post.getId())) {
            postRepository.save(post);
        } else {
            throw new UnauthorizedAccessException();
        }
    }

    @Transactional
    public void deletePost(PostDeleteForm form) {
        if (isPostAuthor(form.getUserId(), form.getPostId())) {
            postRepository.deleteById(form.getPostId());
        } else {
            throw new UnauthorizedAccessException();
        }
    }

    // TODO: 파라미터 수정 필요 시 수정
    @Transactional
    public Page<Post> recentPost(int pageNum) {
        return postRepository.findAll(PageRequest.of(20*(pageNum-1), 20*pageNum, Sort.by(Sort.Order.desc("updatedAt"))));
    }

    // TODO: User 클래스 완성 시 .getId() -> .getUserId()로 수정
    private boolean isPostAuthor(String userId, Long postId) {
        return postRepository.findById(postId)
               .orElseThrow(() -> new PostNotFoundException())
               .getId().equals(userId);
    }
}
