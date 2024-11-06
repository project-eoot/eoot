package com.ssafy.eoot.post.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class PostImages {
    // TODO : 이미지 타입 필드 회의 완료 후 추가
    @Id
    private Long imageId;

    @NotBlank
    private String originName;

    @NotBlank
    private String changeName;

    @NotBlank
    private Timestamp createdAt;

    private int seqNum;
}
