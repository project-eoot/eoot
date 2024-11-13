package com.ssafy.eoot.member.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
public class Member {
    @Id
    @Column(name = "member_id")
    private String id;

    @OneToMany(mappedBy = "member")
    private List<MemberLocation> locationList;

    @OneToOne(mappedBy = "imageMember")
    private MemberImage memberImage;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    private Date birth;

    @Column(nullable = false)
    private String originName;

    @Column(nullable = false)
    private String changeName;

    @Column(nullable = false)
    private Timestamp createdAt;

    @Column(nullable = false)
    private Timestamp updatedAt;

    private String lastLoginType;
}
