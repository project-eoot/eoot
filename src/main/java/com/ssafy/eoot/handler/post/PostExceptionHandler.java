package com.ssafy.eoot.handler.post;

import com.ssafy.eoot.exception.common.ExceptionCase;
import com.ssafy.eoot.exception.post.*;
import com.ssafy.eoot.response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PostExceptionHandler {

    @ExceptionHandler(BlankTitleException.class)
    public ResponseEntity<?> blankTitleExHandle() {
        return ResponseDto.error(ExceptionCase.BLANK_TITLE_EXCEPTION);
    }

    @ExceptionHandler(BlankContentException.class)
    public ResponseEntity<?> blankContentExHandle() {
        return ResponseDto.error(ExceptionCase.BLANK_CONTENT_EXCEPTION);
    }

    @ExceptionHandler(BlankLocationException.class)
    public ResponseEntity<?> blankLocationExHandle() {
        return ResponseDto.error(ExceptionCase.BLANK_LOCATION_EXCEPTION);
    }

    @ExceptionHandler(BlankTagException.class)
    public ResponseEntity<?> blankTagExHandle() {
        return ResponseDto.error(ExceptionCase.BLANK_TAG_EXCEPTION);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<?> postNotFoundExHandle() {
        return ResponseDto.error(ExceptionCase.POST_NOT_FOUND_EXCEPTION);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<?> unauthorizedAccessExHandle() {
        return ResponseDto.error(ExceptionCase.UNAUTHORIZED_ACCESS_EXCEPTION);
    }
}
