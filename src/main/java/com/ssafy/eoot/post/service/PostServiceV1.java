package com.ssafy.eoot.post.service;

import com.ssafy.eoot.exception.post.PostNotFoundException;
import com.ssafy.eoot.exception.post.UnauthorizedAccessException;
import com.ssafy.eoot.post.dto.DeletePostForm;
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
    // TODO: User 클래스 완성 시 isPostAuthor .getId() -> .getUserId()로 수정
    private final PostRepository postRepository;

    @Transactional
    public void upsertPost(Post post) {
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(DeletePostForm form) {
        if (isPostAuthor(form)) {
            postRepository.deleteById(form.getPostId());
        } else {
            throw new UnauthorizedAccessException();
        }
    }

    @Transactional
    public Page<Post> recentPost(int pageNum) {
        return postRepository.findAll(PageRequest.of(20*(pageNum-1), 20*pageNum, Sort.by(Sort.Order.desc("updatedAt"))));
    }

    private boolean isPostAuthor(DeletePostForm form) {
        return postRepository.findById(form.getPostId())
               .orElseThrow(() -> new PostNotFoundException())
               .getId().equals(form.getUserId());
    }
}
