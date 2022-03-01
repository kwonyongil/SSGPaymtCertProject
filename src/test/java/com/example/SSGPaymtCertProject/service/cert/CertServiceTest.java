package com.example.SSGPaymtCertProject.service.cert;

import com.example.SSGPaymtCertProject.domain.PaymtCertTypeNm;
import com.example.SSGPaymtCertProject.domain.dto.CertDataResDto;
import com.example.SSGPaymtCertProject.domain.dto.CertReqDto;
import com.example.SSGPaymtCertProject.domain.dto.CertResDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
public class CertServiceTest {

    @Autowired
    private CertService certService;

    /**
     * 픽스처 1, 2
     */
    CertReqDto cert1;

    CertReqDto cert2;


    /**
     * 단위테스트 시작 전 공통적으로 초기화되는 코드 - {@link CertReqDto} Dto 생성하고 기본 정보를 등록하는 등의 작업 진행
     */
    @BeforeEach
    public void setUp() {

        this.cert1 = CertReqDto.builder()
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
                .regpeId("yong")
                .modpeId("yong")
                .crdcoCd("01")
                .build();

        this.cert2 = CertReqDto.builder()
                .paymtMeansCd("150")
                .ordNo("UU2")
                .paymtAmt(10000L)
                .ordpeNm("권용일2")
                .ordpeId(2L)
                .itemNm("노트북")
                .itemId(2L)
                .paymtCertRstUrl("127.0.0.1/cert/gate")
                .certChnlNm("pc")
                .paymtCertTypeNm(PaymtCertTypeNm.KAKAOPAY)
                .regpeId("yong")
                .modpeId("yong")
                .build();

    }

    @Test
    public void 인증저장_테스트() {

        CertResDto certResDto = certService.insertPaymtCertDemnd(cert1);
        CertResDto certResDto2 = certService.insertPaymtCertDemnd(cert2);
        CertDataResDto certDataResDto = certResDto.getData();
        CertDataResDto certDataResDto2 = certResDto2.getData();

        // 조회가 잘 된다면, 저장이 잘 되었음을 알 수 있다.
        then(certResDto.getCode()).isEqualTo("0000");
        then(certDataResDto.getPaymtAmt()).isEqualTo(1000L);
        then(certDataResDto.getFnccoCd()).isEqualTo("01");
        then(certDataResDto.getCertPageUrl()).isEqualTo("http://127.0.0.1/cert/gate/" + certDataResDto.getPgPaymtCertId());
        then(certDataResDto2.getFnccoCd()).isEqualTo(null);
    }

    @AfterEach
    public void tearDown() {
    }
}
