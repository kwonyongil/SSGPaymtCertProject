package com.example.SSGPaymtCertProject.domain;

import com.example.SSGPaymtCertProject.domain.base.EnumType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum PaymtCertTypeNm implements EnumType {

    CREDITCARD("신용카드"),
    KAKAOPAY("카카오페이"),
    SSGPAYCREDIT("SSGPAY신용카드"),
    EMPTY("없음");

    private final String text;

    @Override
    public String getId() {
        return this.name();
    }

}