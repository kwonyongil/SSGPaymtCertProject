package com.example.SSGPaymtCertProject.controller;

import com.example.SSGPaymtCertProject.ApiDocumentationTest;
import com.example.SSGPaymtCertProject.response.ApiResponseDto;
import com.example.SSGPaymtCertProject.util.CustomResponseFieldsSnippet;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @since 2022. 02. 30
 * @author kwon-yong-il
 * /docs 를 endpoint로 가지는 Controller 를 test
 * test 패키지에 만들면 테스트 수행때만 동작하기에 실제 운영에서는 호출되지 않음
 */
public class CommonDocumentationTest extends ApiDocumentationTest {

    @Autowired
    private MockMvc mockMvc;
    /**
     * spring boot 사용시 (spring-boot-starter-web 에 포함되어 있음)
     * JacksonAutoConfiguration 에 의해 등록된 빈이 없는 경우에
     * 자동으로  Jackson2ObjectMapperBuilder , ObjectMapper 빈 등록
     */
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void commons() throws Exception {

        //when
        ResultActions result = this.mockMvc.perform(
                // /docs 를 endpoint 로 가지는 Controller 를 test 패키지에 만듬
                // test 패키지에 만들면 테스트 수행때만 동작하기에 실제 운영에서는 호출되지 않는다.
                get("/docs")
                        .accept(MediaType.APPLICATION_JSON)
        );

        MvcResult mvcResult = result.andReturn();
        Docs docs = getData(mvcResult);

        //then
        result.andExpect(status().isOk())
                .andDo(document("common",
                        customResponseFields("custom-response", null,
                                attributes(key("title").value("공통응답")),
                                subsectionWithPath("data").description("데이터"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지")
                        ),
                        /**
                         * response-fields 스니펫을 이미 선언했기에 모든 응답필드는 해당 response-fields 를 사용
                         *그렇게 되면 불필요한 포맷과 필수 여부가 테이블에 나타나서 보기가 좋지 않다.
                         * 그래서 특정 이름의 스니펫을 사용할수 있게 CustomResponseFieldsSnippet 를 만듬
                         */
                        customResponseFields("custom-response", beneathPath("data.paymtCertTypeNm").withSubsectionId("paymtCertTypeNm"),
                                attributes(key("title").value("인증타입명")),
                                enumConvertFieldDescriptor(docs.getPaymtCertTypeNm())
                        )
                ));
    }

    private static FieldDescriptor[] enumConvertFieldDescriptor(Map<String, String> enumValues) {

        return enumValues.entrySet().stream()
                .map(x -> fieldWithPath(x.getKey()).description(x.getValue()))
                .toArray(FieldDescriptor[]::new);
    }

    Docs getData(MvcResult result) throws IOException {
        ApiResponseDto<Docs> apiResponseDto = objectMapper.readValue(result.getResponse().getContentAsByteArray(),
                new TypeReference<ApiResponseDto<Docs>>() {
                });

        return apiResponseDto.getData();
    }

    public static CustomResponseFieldsSnippet customResponseFields(String type,
                                                                   PayloadSubsectionExtractor<?> subsectionExtractor,
                                                                   Map<String, Object> attributes, FieldDescriptor... descriptors) {
        return new CustomResponseFieldsSnippet(type, subsectionExtractor, Arrays.asList(descriptors), attributes
                , true);
    }
}