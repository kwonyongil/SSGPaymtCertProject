package com.example.SSGPaymtCertProject.controller;

import com.example.SSGPaymtCertProject.ApiDocumentationTest;
import com.example.SSGPaymtCertProject.domain.PaymtCertTypeNm;
import com.example.SSGPaymtCertProject.domain.dto.CertDataResDto;
import com.example.SSGPaymtCertProject.domain.dto.CertReqDto;
import com.example.SSGPaymtCertProject.domain.dto.CertResDto;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.SSGPaymtCertProject.util.ApiDocumentUtils.getDocumentRequest;
import static com.example.SSGPaymtCertProject.util.ApiDocumentUtils.getDocumentResponse;
import static com.example.SSGPaymtCertProject.util.DocumentAttributeProvider.getChnlExample;
import static com.example.SSGPaymtCertProject.util.DocumnetLinkGenerator.DocUrl.PAYMT_CERT_TYPE_NM;
import static com.example.SSGPaymtCertProject.util.DocumnetLinkGenerator.generateLinkCode;
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
 *         <h3>인증 통합 테스트</h3>
 */
public class CertControllerTest extends ApiDocumentationTest {

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
        data.setPaymtCertTypeNm(PaymtCertTypeNm.CREDITCARD);
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
                .paymtCertTypeNm(PaymtCertTypeNm.CREDITCARD)
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
                                fieldWithPath("certChnlNm").type(JsonFieldType.STRING).attributes(getChnlExample()).description("인증체널명"),
                                // fieldWithPath("birthDate").type(JsonFieldType.STRING).attributes(getDateFormat()).description("생년월일"), // 양식 추가
                                fieldWithPath("paymtCertTypeNm").type(JsonFieldType.STRING).description(generateLinkCode(PAYMT_CERT_TYPE_NM)),
                                /** 기본적으로 제공하는 스니펫에는 없는 필드이기에 스니펫을 customizing (optional 필수값 여부!)
                                 *  커스텀 스니펫 적용 (스니펫은 mustache 문법을 사용)
                                 *  https://github.com/spring-projects/spring-restdocs/tree/main/spring-restdocs-core/src/main/resources/org/springframework/restdocs/templates/asciidoctor
                                 *  request-fields 의 스니펫을 customizing 하려면
                                 *  src/test/resources/org/springframework/restdocs/templates/asciidoctor` 경로에
                                 *  `request-fields.snippet 파일 추가
                                 *  mustache 문법 중 }} 은 param 이 비어있거나 false 일때 작동됩니다. 그래서 }true} 을 사용하여 optional 이 false 인 것을 필수로 표현
                                 */
                                fieldWithPath("crdcoCd").type(JsonFieldType.STRING).description("카드사코드").optional() // 필수값 여부 위해
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                fieldWithPath("data.pgPaymtCertId").type(JsonFieldType.NUMBER).description("인증키"),
                                fieldWithPath("data.paymtAmt").type(JsonFieldType.NUMBER).description("결제금액"),
                                fieldWithPath("data.paymtMeansCd").type(JsonFieldType.STRING).description("결제수단코드"),
                                fieldWithPath("data.certPageUrl").type(JsonFieldType.STRING).description("인증PageUrl"),
                                fieldWithPath("data.paymtCertTypeNm").type(JsonFieldType.STRING).description(generateLinkCode(PAYMT_CERT_TYPE_NM)),
                                fieldWithPath("data.fnccoCd").type(JsonFieldType.STRING).description("제휴사코드")
                        )
                ));
    }

}
