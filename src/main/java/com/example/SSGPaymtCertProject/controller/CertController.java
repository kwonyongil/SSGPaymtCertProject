package com.example.SSGPaymtCertProject.controller;

import com.example.SSGPaymtCertProject.domain.dto.CertReqDto;
import com.example.SSGPaymtCertProject.domain.dto.CertResDto;
import com.example.SSGPaymtCertProject.service.cert.CertService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @since 2022. 02. 24
 * @author kwon-yong-il
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/cert")
public class CertController {
    private static final Logger logger = LoggerFactory.getLogger(CertController.class);

    private final CertService certService;

    /**
     * @param paymtCertDemndDto 인증요청Dto
     * @return CertResDto
     *         <h2>인증 정보 등록</h2>
     * @Valid 는 JSR-303 표준 스펙으로써 제약 조건이 부여된 객체에 대해 빈 검증기(Bean Validator)를 이용해서 검증하도록 지시하는 어노테이션이다.
     * Spring 에서는 LocalValidatorFactoryBean 을 이용해 JSR 표준의 검증 기능을 사용할 수 있는데,
     * LocalValidatorFactoryBean 은 JSR-303의 검증 기능을 이용할 수 있도록 해주는 일종의 어댑터에 해당한다.
     * JSR 표준의 빈 검증 기술의 특징은 객체의 필드에 달린 제약조건 어노테이션을 참고해 검증을 편리하게 할 수 있다는 것이다.
     * 이 빈 검증 기능을 이용하려면 LocalValidatorFactoryBean 을 빈으로 등록하고 VaidationService 를 제공해주어야 하는데,
     * SpringBoot 에서는 의존성만 추가하면 검증을 위한 빈들이 자동으로 등록된다.
     *
     * 모든 요청은 프론트 컨트롤러인 디스패처 서블릿을 통해 컨트롤러로 전달된다.
     * 그리고 컨트롤러의 메소드를 호출하는 과정에는 메소드의 값을 처리해주는 ArgumentResolver 가 동작하는데,
     * @Valid 역시 ArgumentResolver 에 의해 처리가 된다.
     * @RequestBody 가 있는 경우에는 Json 메세지를 객체로 변환해주는 ArgumentResolver 의 구현체
     * RequestResponseBodyMethodProcessor 가 처리하는데, 내부에 @Valid 로 시작하는 어노테이션이 있을 경우 유효성 검사를 진행한다.
     * (@Valid 가 아니라 커스텀 어노테이션인 @ValidMangKyu 여도 동작한다.)
     * ModelAttribute 를 사용중이라면 ModelAttributeMethodProcessor 에 의해 @Valid 가 처리된다.
     *
     * 그리고 검증에 오류가 있다면 MethodArgumentNotValidException 예외가 발생하게 되고,
     * 디스패처 서블릿에 기본으로 등록된 예외 리졸버(Exception Resolver)인 DefaultHandlerExceptionResolver 에 의해
     * 400 BadRequest 에러가 발생할 것이다.
     */
    @PostMapping(value = "/setup")
    public CertResDto setup(@RequestBody @Valid CertReqDto paymtCertDemndDto) {

        CertResDto certResDto = certService.insertPaymtCertDemnd(paymtCertDemndDto);

        return certResDto;
    }
}
