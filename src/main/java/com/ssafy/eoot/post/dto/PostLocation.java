package com.ssafy.eoot.post.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class PostLocation {
    @Id
    private Long locationId;

    @NotBlank
    private String sigungu;

    @NotBlank
    private String dong;

    private String detail;

    @NotBlank
    private BigDecimal locationLat;

    @NotBlank
    private BigDecimal locationLng;
}
