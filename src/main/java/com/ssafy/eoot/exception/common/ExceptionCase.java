package com.ssafy.eoot.exception.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCase {

    /**
     * Post 관련 Exception
     */
    IMAGE_UPLOAD_EXCEPTION(4003, "이미지 업로드 중 문제 발생"),
    POST_NOT_FOUND_EXCEPTION(4004, "게시글을 찾을 수 없음"),
    UNAUTHORIZED_ACCESS_EXCEPTION(4005, "해당 작업을 수행 할 권한이 없음"),
    BLANK_TITLE_EXCEPTION(4006, "게시글 제목이 NULL이거나 공백"),
    BLANK_CONTENT_EXCEPTION(4007, "게시글 내용이 NULL이거나 공백"),
    BLANK_COMMENT_EXCEPTION(4008, "댓글이 NULL이거나 공백"),
    BLANK_TAG_EXCEPTION(4009, "태그 내용이 NULL이거나 공백"),
    BLANK_LOCATION_EXCEPTION(4010, "주소가 NULL이거나 공백");

    private int exceptionCode;
    private String message;
}
