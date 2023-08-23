package com.oldteam.movienote.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class HttpException extends RuntimeException {

    private HttpStatus status;
    private HttpExceptionCode code;

    public HttpException() {
    }

    public HttpException(HttpStatus status, HttpExceptionCode code, String message) {
        super(message);
        this.status = status;
        this.code = code;
    }

    public HttpException(HttpStatus status, HttpExceptionCode code, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.code = code;
    }

    public HttpException(HttpStatus status, HttpExceptionCode code, Throwable cause) {
        super(cause);
        this.status = status;
        this.code = code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public HttpExceptionCode getCode() {
        return code;
    }

}
