package com.example.SSGPaymtCertProject.controller;

import com.example.SSGPaymtCertProject.config.SentryConfig;
import com.example.SSGPaymtCertProject.config.WebSecurityConfig;
import com.example.SSGPaymtCertProject.domain.dto.CertDataResDto;
import com.example.SSGPaymtCertProject.domain.dto.CertReqDto;
import com.example.SSGPaymtCertProject.domain.dto.CertResDto;
import com.example.SSGPaymtCertProject.service.cert.CertService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.SSGPaymtCertProject.util.ApiDocumentUtils.getDocumentRequest;
import static com.example.SSGPaymtCertProject.util.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *  @since 2022. 02. 28
 *  @author kwon-yong-il
 *         <h2>RestDoc</h2>
 *         <h3>통합 테스트</h3>
 */
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
@WebMvcTest(value = CertController.class,
        // excludeFilters : Component scan 에서 제외하고자 하는 클래스 정의
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        classes = {WebSecurityConfig.class, SentryConfig.class}))
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
public class CertControllerTest {

    /**
     * @AutoConfigureMockMvc
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * spring boot 사용시 (spring-boot-starter-web 에 포함되어 있음)
     * JacksonAutoConfiguration 에 의해 등록된 빈이 없는 경우에
     * 자동으로  Jackson2ObjectMapperBuilder , ObjectMapper 빈 등록
     */
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean // mocking 을 하기위해 @MockBean 을 선언
    private CertService certService;

    @Test
    public void 인증정보_셋업_테스트() throws Exception {

        //given
        CertResDto response = new CertResDto();
        CertDataResDto data = new CertDataResDto();
        response.setCode("0000");
        response.setMessage("Success");
        response.setSuccess(true);
        data.setPgPaymtCertId(1L);
        data.setPaymtAmt(1000L);
        data.setPaymtMeansCd("100");
        data.setCertPageUrl("http://127.0.0.1/cert/gate/" + 1L);
        data.setFnccoCd("01");
        response.setData(data);
        // eq 는 일치해야하는 조건, any 는 해당 클래스기만 하면
        // given(certService.insertPaymtCertDemnd(eq(1L), any(CertReqDto.class)))
        // insertPaymtCertDemnd 메서드를 CertReqDto 타입 인자로 호출하면 위 response 를 리턴해라 라는 뜻
        given(certService.insertPaymtCertDemnd(any(CertReqDto.class)))
                .willReturn(response); // mocking 을 하여 예상응답값을 받음

        //when
        CertReqDto certReqDto = CertReqDto.builder()
                .paymtMeansCd("100")
                .ordNo("UU1")
                .paymtAmt(1000L)
                .ordpeNm("권용일")
                .ordpeId(1L)
                .itemNm("노트북")
                .itemId(1L)
                .paymtCertRstUrl("127.0.0.1/cert/gate")
                .certChnlNm("pc")
                .paymtCertTypeNm("card")
                .crdcoCd("01")
                .build();

        ResultActions result = this.mockMvc.perform(
                post("/cert/setup")
                        .content(objectMapper.writeValueAsString(certReqDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)

        );

        //then
        result.andExpect(status().isOk())
                // .andExpect(content().json("{'code':'0000'}"))
                // test 수행시 ./build/generated-snippets/ 하위에
                // 지정한 문자열의 폴더 하위에 문서가 작성됩니다.
                .andDo(document("cert-setup",
                        getDocumentRequest(),
                        getDocumentResponse(),
//                        pathParameters(
//                                parameterWithName("id").description("아이디")
//                        ),
// requestFields 는 모든 필드를 반드시 문서화
// relaxedRequestFields 는 모든 필드를 반드시 문서화하지 않아도 된다.
//                        requestFields(
                        relaxedRequestFields(

                                fieldWithPath("paymtMeansCd").type(JsonFieldType.STRING).description("결제수단"),
                                fieldWithPath("ordNo").type(JsonFieldType.STRING).description("주문번호"),
                                fieldWithPath("paymtAmt").type(JsonFieldType.NUMBER).description("결제금액"),
                                fieldWithPath("ordpeNm").type(JsonFieldType.STRING).description("주문자명"),
                                fieldWithPath("ordpeId").type(JsonFieldType.NUMBER).description("주문자ID"),
                                fieldWithPath("itemNm").type(JsonFieldType.STRING).description("아이템명"),
                                fieldWithPath("itemId").type(JsonFieldType.NUMBER).description("아이템ID"),
                                fieldWithPath("paymtCertRstUrl").type(JsonFieldType.STRING).description("인증결과URL"),
                                fieldWithPath("certChnlNm").type(JsonFieldType.STRING).description("인증체널명"),
                                fieldWithPath("paymtCertTypeNm").type(JsonFieldType.STRING).description("인증타입명"),
                                fieldWithPath("crdcoCd").type(JsonFieldType.STRING).description("카드사코드")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                fieldWithPath("data.pgPaymtCertId").type(JsonFieldType.NUMBER).description("인증키"),
                                fieldWithPath("data.paymtAmt").type(JsonFieldType.NUMBER).description("결제금액"),
                                fieldWithPath("data.paymtMeansCd").type(JsonFieldType.STRING).description("결제수단코드"),
                                fieldWithPath("data.certPageUrl").type(JsonFieldType.STRING).description("인증PageUrl"),
                                fieldWithPath("data.fnccoCd").type(JsonFieldType.STRING).description("제휴사코드")
                        )
                ));
    }

}
