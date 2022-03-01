package com.example.SSGPaymtCertProject.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface DocumnetLinkGenerator {

    // popup class를 만들어줌
    static String generateLinkCode(DocUrl docUrl) {
        return String.format("link:common/%s.html[%s %s,role=\"popup\"]", docUrl.pageId, docUrl.text, "코드");
    }

    static String generateText(DocUrl docUrl) {
        return String.format("%s %s", docUrl.text, "코드명");
    }

    @RequiredArgsConstructor
    enum DocUrl {
        PAYMT_CERT_TYPE_NM("paymtCertTypeNm", "인증타입명"),
        ;

        private final String pageId;
        @Getter
        private final String text;
    }
}