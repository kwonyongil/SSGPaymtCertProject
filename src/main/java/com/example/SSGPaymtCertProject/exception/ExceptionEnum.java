package com.example.SSGPaymtCertProject.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ExceptionEnum {
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E0001"),
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, "E0002"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E0003"),

    ERROR_USER_BAD_CREDENTIALS(HttpStatus.UNAUTHORIZED, "S0001", "잘못된 회원 정보입니다."),
    ERROR_USER_REDUPLICATION(HttpStatus.UNAUTHORIZED, "S0002", "이미 존재하는 회원입니다."),
    ERROR_USER_NOTFOUND(HttpStatus.UNAUTHORIZED, "S0002", "존재하지 않는 회원입니다."),

    ERROR_ITEM_NOTFOUND(HttpStatus.UNAUTHORIZED, "I0002", "존재하지 않는 상품입니다.");

    private final HttpStatus status;
    private final String code;
    private String message;

    ExceptionEnum(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    ExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
