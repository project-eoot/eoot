package com.ssafy.eoot.member.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class LogoutSuccessDto {
    private Boolean status;
    private String lastLoginType;
    private Timestamp timestamp;
}
