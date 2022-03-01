package com.example.SSGPaymtCertProject.domain.dto;

import com.example.SSGPaymtCertProject.domain.Paymt;
import com.example.SSGPaymtCertProject.domain.PaymtCertTypeNm;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = false)
@ToString
@Getter
@Setter
@NoArgsConstructor
public class CertReqDto extends BaseEntityDto{

    private Long pgPaymtCertId;

    // NotBlank 는 문자열에만 사용
    @NotBlank
    private String paymtMeansCd;

    @NotBlank
    private String ordNo;

    // 주의! Long 타입은 NotNull 사용
    @NotNull
    private Long paymtAmt;

    @NotBlank
    private String ordpeNm;

    @NotNull
    private Long ordpeId;

    @NotBlank
    private String itemNm;

    @NotNull
    private Long itemId;

    @NotBlank
    private String paymtCertRstUrl;

    @NotBlank
    private String certChnlNm;

    @NotNull
    private PaymtCertTypeNm paymtCertTypeNm;

    private String crdcoCd;

    /**
     *  주 테이블에 외래 키
     *  주 객체가 대상 객체를 참조하는 것처럼 주 테이블에 외래 키를 두고 대상 테이블을 참조한다.
     *  외래 키를 객체 참조와 비슷하게 사용할 수 있어서 객체지향 개발자들이 선호한다.
     *  이 방법의 장점은 주 테이블이 외래 키를 가지고 있으므로
     *  주 테이블만 확인해도 대상 테이블과 연관관계가 있는지 알 수 있다.
     *
     *  대상 테이블에 외래 키
     *  전통적인 데이터베이스 개발자들은 보통 대상 테이블에 외래 키를 두는 것을 선호한다.
     *  이 방법의 장점은 테이블 관계를 일대일에서 일대다로 변경할 때 테이블 구조를 그대로 유지할 수 있다.
     *
     *  대상테이블이 n개 될 여지가 있는 테이블이라고 생각하면된다. (1인 테이블이 주 테이블)
     *  1:N 관계가 될 수 있다.인증 1번에 승인 여러번 구조
     *  즉 주테이블은 인증 테이블
     *
     *  아래는 주 테이블에 외래 키를 두는 '양뱡향' 방식이다.
     *
     *  주의 : 프록시를 사용할 때 외래 키를 직접 관리하지 않는 일대일 관계는 지연 로딩으로 설정해도 즉시 로딩된다.
     *  예를 들어 방금 본 예제에서 PaymtCertDemnd.paymt 는 지연 로딩할 수 있지만,
     *  Paymt.paymtCertDemnd 는 지연 로딩으로 설정해도 즉시 로딩된다.
     *  이것은 프록시의 한계 때문에 발생하는 문제인데 프록시 대신에
     *  bytecode instrumentation 을 사용하면 해결할 수 있다.
     *  자세한 내용과 다양한 해결책은 다음 URL 을 참고하자.
     *  https://developer.jboss.org/wiki/SomeExplanationsOnLazyLoadingone-to-one
     *
     *  해결 방법
     *  구조 변경하기
     *  양방향 매핑이 반드시 필요한 상황인지 다시한번 생각해본다.
     *  OneToOne -> OneToMany 또는 ManyToOne 관계로 변경이 가능한지 생각해본다.
     *  구조를 유지한채 해결하기
     *  PAYMT 를 조회할때 PAYMT_CERT_DEMND 도 함께 조회한다. (Fetch Join)
     *  batch fetch size 를 사용한다.
     *
     *  결론
     *  OneToOne 양방향 매핑에서 연관관계의 주인이 아닌 쪽에서 조회하게 되면 프록시 객체를 생성할 수 없기 때문에
     *  지연 로딩으로 설정해도 즉시 로딩으로 동작하게 된다.
     *  그 이유는 프록시는 null 을 감쌀 수 없기 때문에 참조하고 있는 객체가 null 인지 null 이 아닌지 확인하는 쿼리를 실행해야 하기 때문이다.
     */
    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, // 외래키 직접 관리하여 지연로딩 가능 (OneToOne 기본은 EAGER)
            orphanRemoval = true)
    @JoinColumn(name = "PAYMT_ID")
    @ToString.Exclude
    private Paymt paymt;

    @Builder
    public CertReqDto(String paymtMeansCd, String ordNo, Long paymtAmt,
                      String ordpeNm, Long ordpeId, String itemNm, Long itemId,
                      String paymtCertRstUrl, String certChnlNm, PaymtCertTypeNm paymtCertTypeNm,
                      String regpeId, String modpeId,String crdcoCd) {
        this.paymtMeansCd = paymtMeansCd;
        this.ordNo = ordNo;
        this.paymtAmt = paymtAmt;
        this.ordpeNm = ordpeNm;
        this.ordpeId = ordpeId;
        this.itemNm = itemNm;
        this.itemId = itemId;
        this.paymtCertRstUrl = paymtCertRstUrl;
        this.certChnlNm = certChnlNm;
        this.paymtCertTypeNm = paymtCertTypeNm;
        this.regpeId = regpeId;
        this.modpeId = modpeId;
        this.crdcoCd = crdcoCd;
    }
}
