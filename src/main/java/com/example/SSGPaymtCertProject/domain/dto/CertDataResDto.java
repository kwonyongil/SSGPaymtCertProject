package com.example.SSGPaymtCertProject.domain.dto;

import com.example.SSGPaymtCertProject.domain.PaymtCertTypeNm;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@ToString
@Getter
@Setter
@NoArgsConstructor
public class CertDataResDto {
    private Long pgPaymtCertId;

    private Long paymtAmt;

    private String paymtMeansCd;

    private String certPageUrl;

    private PaymtCertTypeNm paymtCertTypeNm;

    private String fnccoCd;
}
