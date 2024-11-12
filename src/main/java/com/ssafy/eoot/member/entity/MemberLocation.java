package com.ssafy.eoot.member.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class MemberLocation {
    @Id
    @Column(name = "location_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String sigungu;

    @Column(nullable = false)
    private String dong;

    @Column(nullable = false)
    private String detail;

    @Column(name = "location_lat", nullable = false)
    private BigDecimal lat;

    @Column(name = "location_lng", nullable = false)
    private BigDecimal lng;
}
