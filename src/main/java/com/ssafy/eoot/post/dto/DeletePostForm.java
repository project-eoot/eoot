package com.ssafy.eoot.post.dto;

import lombok.Data;

@Data
public class DeletePostForm {
    private Long postId;
    private String userId;
}
