package com.ssafy.eoot.member.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class MemberImage {
    @Id
    @Column(name = "image_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member imageMember;

    @Column(nullable = false)
    private String originName;

    @Column(nullable = false)
    private String changeName;

    @Column(nullable = false)
    private Timestamp createdAt;

    @Column(nullable = false)
    private Timestamp updatedAt;
}
