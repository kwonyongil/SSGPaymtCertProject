package com.example.SSGPaymtCertProject.controller;

import com.example.SSGPaymtCertProject.domain.base.EnumType;
import com.example.SSGPaymtCertProject.domain.PaymtCertTypeNm;
import com.example.SSGPaymtCertProject.response.ApiResponseCode;
import com.example.SSGPaymtCertProject.response.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @since 2022. 02. 30
 * @author kwon-yong-il
 * /docs 를 endpoint로 가지는 Controller 를 test 패키지에 만듬
 * test 패키지에 만들면 테스트 수행때만 동작하기에 실제 운영에서는 호출되지 않음
 */
@RestController
public class EnumViewController {
    /**
     * @return
     * <h2>docs 엔드포인트 테스트 컨트롤러</h2>
     */
    @GetMapping("/docs")
    public ApiResponseDto<Docs> findAll() {

        Map<String, String> apiResponseCodes = getDocs(ApiResponseCode.values());
        Map<String, String> paymtCertTypeNm = getDocs(PaymtCertTypeNm.values());

        return ApiResponseDto.createOK(
                Docs.testBuilder()
                .apiResponseCodes(apiResponseCodes)
                .paymtCertTypeNm(paymtCertTypeNm)
                .build()
        );

    }

    private Map<String, String> getDocs(EnumType[] enumTypes) {
        return Arrays.stream(enumTypes)
                .collect(Collectors.toMap(EnumType::getId, EnumType::getText));
    }
}
