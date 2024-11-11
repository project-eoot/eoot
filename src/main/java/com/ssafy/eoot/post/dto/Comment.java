package com.ssafy.eoot.post.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Comment {
    // TODO: User DTO 만들어지면 외래 키 추가
    @Id @GeneratedValue
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @NotBlank
    private String content;
}
