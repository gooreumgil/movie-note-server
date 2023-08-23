package com.oldteam.movienote.api.advice;

import com.oldteam.movienote.common.exception.HttpException;
import com.oldteam.movienote.common.exception.dto.ErrorResDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(HttpException.class)
    protected Object handleHttpException(HttpException e, HttpServletRequest request) {
        ErrorResDto errorResDto = new ErrorResDto(e.getMessage(), e.getCode().name());
        return ResponseEntity.status(e.getStatus()).body(errorResDto);
    }

}
