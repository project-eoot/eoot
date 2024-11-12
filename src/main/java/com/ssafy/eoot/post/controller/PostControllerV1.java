package com.ssafy.eoot.post.controller;

import com.ssafy.eoot.exception.post.BlankContentException;
import com.ssafy.eoot.exception.post.BlankLocationException;
import com.ssafy.eoot.exception.post.BlankTagException;
import com.ssafy.eoot.exception.post.BlankTitleException;
import com.ssafy.eoot.post.dto.Post;
import com.ssafy.eoot.post.dto.PostDeleteForm;
import com.ssafy.eoot.post.service.PostServiceV1;
import com.ssafy.eoot.response.ResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/post")
public class PostControllerV1 {
    // TODO: Validation 보완
    // TODO: PostUpdateForm 만들어서 적용 (필수 요구 필드 변경)
    // TODO: 작업 별 응답 성공 CustomCode 합의 후 수정
    private final PostServiceV1 postService;

    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody Post post, BindingResult bindingResult) {
        validatePostFields(bindingResult);
        postService.insertPost(post);
        return ResponseDto.success(true, 2000);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @Valid @RequestBody Post post, BindingResult bindingResult) {
        validatePostFields(bindingResult);
        post.setId(postId);
        postService.updatePost(post);
        return ResponseDto.success(true, 2000);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId, @RequestBody PostDeleteForm form) {
        form.setPostId(postId);
        postService.deletePost(form);
        return ResponseDto.success(true, 2000);
    }

    // TODO: 추천 기준 합의되면 완성
    @GetMapping("/list/recommend")
    public ResponseEntity<?> recommendPostList() {
        return ResponseDto.success(true, 2000);
    }

    @GetMapping("/list/{pageNum}")
    public ResponseEntity<?> recentPostList(@PathVariable int pageNum) {
        Page<Post> postList = postService.recentPost(pageNum);
        return ResponseDto.success(postList, 2000);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> detailPost(@PathVariable Long postId) {
        Post post = postService.detailPost(postId);
        return ResponseDto.success(post, 2000);
    }

    private void validatePostFields(BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            bindingResult.getFieldErrors().forEach(error -> {
                switch (error.getField()) {
                    case "title": throw new BlankTitleException();
                    case "content": throw new BlankContentException();
                    case "postLocation": throw new BlankLocationException();
                    case "tags": throw new BlankTagException();
                }
            });
        }
    }
}
