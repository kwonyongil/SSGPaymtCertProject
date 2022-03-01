package com.example.SSGPaymtCertProject;

import com.example.SSGPaymtCertProject.config.SentryConfig;
import com.example.SSGPaymtCertProject.config.WebSecurityConfig;
import com.example.SSGPaymtCertProject.controller.CertController;
import com.example.SSGPaymtCertProject.controller.EnumViewController;
import com.example.SSGPaymtCertProject.service.cert.CertService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
/**
 * MVC 를 위한 테스트.
 * 웹에서 테스트하기 힘든 컨트롤러를 테스트하는 데 적합.
 * 웹상에서 요청과 응답에 대해 테스트할 수 있음.
 * 시큐리티, 필터까지 자동으로 테스트하며, 수동으로 추가/삭제 가능.
 * @SpringBootTest 어노테이션보다 가볍게 테스트할 수 있음.
 * 다음과 같은 내용만 스캔하도록 제한함.
 * @Controller, @ControllerAdvice, @JsonComponent, Converter,
 * GenericConverter, Filter, HandlerInterceptor,
 */
//@WebMvcTest(value = CertController.class,
@WebMvcTest(controllers = {
        CertController.class,
        EnumViewController.class},
        // excludeFilters : Component scan 에서 제외하고자 하는 클래스 정의
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        classes = {WebSecurityConfig.class, SentryConfig.class})
)
/**
 *  webAppContextSetup(webApplicationContext)
 *  addFilters 를 false 로 하여 filter 로 인한 문제를 회피한다. ex. cors
 */
@AutoConfigureMockMvc(addFilters = false)
/**
 * getDocumentRequest 에 선언된 uri 와 동일 기능을 제공, 아래의 우선순위로 적용
 * 1. @AutoConfigureRestDocs 에 uri 정보가 선언되어있으면 적용 없으면 2 단계로
 * 2. getDocumentRequest 에 uri 정보가 설정되어있으면 적용 없으면 3 단계로
 * 3. 기본설정값 적용 http://localhost:8080
 */
// @AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
@AutoConfigureRestDocs(uriHost = "docs.api.com")
/**
 * 테스트 빌드시 Spring Context 가 계속적으로 생성되서
 * Spring REST Docs 파일이 많아질수록 테스트 수행 속도가 느리다.
 * 상속을 받도록 하면 매번 Spring Context 를 띄우지 않는다.
 */
public abstract class ApiDocumentationTest {

    /**
     * @AutoConfigureMockMvc
     * 상속해줘야 하므로 protected 로 설정
     */
    @Autowired
    protected MockMvc mockMvc;

    /**
     * spring boot 사용시 (spring-boot-starter-web 에 포함되어 있음)
     * JacksonAutoConfiguration 에 의해 등록된 빈이 없는 경우에
     * 자동으로  Jackson2ObjectMapperBuilder , ObjectMapper 빈 등록
     * 상속해줘야 하므로 protected 로 설정
     */
    @Autowired
    protected ObjectMapper objectMapper;

    /**
     * CertController 에 빈 주입을 위해서는 Mock 빈도 공통으로 선언
     */
    @MockBean // mocking 을 하기위해 @MockBean 을 선언
    protected CertService certService;
}
