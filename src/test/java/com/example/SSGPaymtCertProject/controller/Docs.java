package com.example.SSGPaymtCertProject.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @since 2022. 02. 30
 * @author kwon-yong-il
 * RestDoc Enum 결과용 클래스
 */
@Getter
// Lombok 1.8.0 이상 버전부터
// @Data (@Getter) 와 @Builder 를 동시에 사용할 경우 private 기본 생성자를 생성해주지 않도록 변경되어 발생
// 기본 생성자 추가
@NoArgsConstructor
public class Docs {

    Map<String, String> apiResponseCodes;

    Map<String, String> paymtCertTypeNm;
    // Map<String, String> jobs;

    // 빌더 메소드를 설정해 줄 수 있다.
    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    private Docs(Map<String, String> apiResponseCodes, Map<String, String> paymtCertTypeNm) {
        this.apiResponseCodes = apiResponseCodes;
        this.paymtCertTypeNm = paymtCertTypeNm;
    }
}