package com.ssafy.eoot.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class LoginSuccessDto {
    private String memberId;
    private String lastLoginType;
    private Timestamp timestamp;
}
