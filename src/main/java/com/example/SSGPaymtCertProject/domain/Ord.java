package com.example.SSGPaymtCertProject.domain;

import com.example.SSGPaymtCertProject.domain.base.BaseEntity;
import com.example.SSGPaymtCertProject.domain.dto.OrdDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "ordItems")
@Entity
@Table(name = "ORD")
public class Ord extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "ORDER_NO")
    private Long orderNo;

    @Column(name = "ORORDER_NO", nullable = false)
    private Long ororderNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ORD_RCP_DTS", nullable = false)
    private Date ordRcpDts;

    // @OneToMany Fetch 기본전략은 LAZY 이지만 유저 데이타를 이용할 일이 많으므로 그룹은 즉시 로딩을 하도록 한다.
    @OneToMany(mappedBy = "ord", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrdItem> ordItems = new ArrayList<>();

    @Builder
    public Ord(Long orderNo, Long ororderNo, Date ordRcpDts, String regpeId, String modpeId) {
        this.orderNo = orderNo;
        this.ororderNo = ororderNo;
        this.ordRcpDts = ordRcpDts;
        this.regpeId = regpeId;
        this.modpeId = modpeId;
    }

    //==연관관계 메서드==//
    public void addOrdItem(OrdItem ordItem) {
        ordItems.add(ordItem);
    }

    public OrdDto toOrdDto() {
        OrdDto ordDto = new OrdDto();
        ordDto.setOrderNo(this.orderNo);
        ordDto.setOrorderNo(this.ororderNo) ;
        ordDto.setOrdRcpDts(this.ordRcpDts);
        ordDto.setRegpeId(this.regpeId);
        ordDto.setModpeId(this.modpeId);
        return ordDto;
    }
}
