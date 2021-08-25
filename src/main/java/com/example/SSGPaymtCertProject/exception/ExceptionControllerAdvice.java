package com.example.SSGPaymtCertProject.exception;

import io.sentry.Sentry;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RestControllerAdvice
/**
 * @since 2021. 08. 26
 * @author yang-jin-yoon
 *<h1>RestController 에서 발생하는 모든 에러를 핸들링할 수 있는 컨트롤러</h1>
 * <p>
 *     sentry 적용 방식으론 1. 시스템 환경변수, 2. 자바시스템 프로퍼티에 적용, 3. 코드상으로 적용 이 존재함
 * </p>
 * <p>참고 링크 : https://zorba91.tistory.com/311</p>
 */
public class ExceptionControllerAdvice {

    @ExceptionHandler
    public Object ExceptionHandler(Exception e){
        Sentry.captureException(e);

        return e;
    }
}
