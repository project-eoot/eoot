package com.ssafy.eoot.post.dto;

import lombok.Data;

@Data
public class PostDeleteForm {
    private Long postId;
    private String userId;
}
