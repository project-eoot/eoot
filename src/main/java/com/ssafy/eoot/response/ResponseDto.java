package com.ssafy.eoot.response;

import com.ssafy.eoot.exception.common.ExceptionCase;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Builder
@Getter
public class ResponseDto {
    private boolean result;
    private Integer customCode;
    private Object data;

    public static ResponseEntity<?> success(Object o) {
        return ResponseEntity
                .status(200)
                .body(ResponseDto.builder()
                        .result(true)
                        .data(o)
                        .customCode(2000)
                        .build());
    }

    public static ResponseEntity<?> error(ExceptionCase ec) {
        return ResponseEntity
                .status(999)
                .body(ResponseDto.builder()
                        .result(false)
                        .data(ec.getMessage())
                        .customCode(ec.getExceptionCode())
                        .build());
    }
}
