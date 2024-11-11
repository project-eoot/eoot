package com.ssafy.eoot.post.controller;

import com.ssafy.eoot.post.dto.Post;
import com.ssafy.eoot.post.dto.PostDeleteForm;
import com.ssafy.eoot.post.service.PostServiceV1;
import com.ssafy.eoot.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/post")
public class PostControllerV1 {
    // TODO: Validation 추가
    // TODO: 작업 별 응답 성공 CustomCode 합의 후 수정
    private final PostServiceV1 postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        postService.insertPost(post);
        return ResponseDto.success(true, 2000);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody Post post) {
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

    @GetMapping("/list/recommend")
    public ResponseEntity<?> recommendPostList() {
        return ResponseDto.success(true, 2000);
    }

    @GetMapping("/list/recent")
    public ResponseEntity<?> recentPostList() {
        return ResponseDto.success(true, 2000);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> detailPost() {
        return ResponseDto.success(true, 2000);
    }
}
