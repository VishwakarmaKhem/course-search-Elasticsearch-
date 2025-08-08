package com.SkillBridge.course_search.exception;

import com.SkillBridge.course_search.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    public ResponseEntity<ErrorResponse> GeneralSearchingExceptionHandler(GeneralSearchingException e){
        String message = e.getMessage();
        ErrorResponse response = new ErrorResponse(message, false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
