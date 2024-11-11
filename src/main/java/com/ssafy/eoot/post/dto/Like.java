package com.ssafy.eoot.post.dto;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Like {
    // TODO : User DTO 만들어지면 외래 키 추가
    @ManyToOne(fetch = FetchType.LAZY)
    @Id @JoinColumn(name = "post_id")
    private Post post;
}
