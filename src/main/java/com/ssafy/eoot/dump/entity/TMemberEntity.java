package com.ssafy.eoot.dump.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "TEST_TBL_MEMBER")
public class TMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MEMBER_NAME")
    private String memberName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PROFILE_IMAGE")
    private String profileImage;
}
