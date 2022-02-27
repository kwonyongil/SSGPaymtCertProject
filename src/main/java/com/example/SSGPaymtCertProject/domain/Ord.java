package com.example.SSGPaymtCertProject.domain;

import com.example.SSGPaymtCertProject.domain.base.BaseEntity;
import com.example.SSGPaymtCertProject.domain.dto.OrdDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "ordItems")
@Entity
@Table(name = "ORD")
public class Ord extends BaseEntity {

    /**
     * 자동생성 전략 사용하지 않음
     */
    @Id
    @Column(name = "ORD_NO")
    private String ordNo;

    @Column(name = "ORORD_NO", nullable = false)
    private String orordNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ORD_RCP_DTS", nullable = false)
    private Date ordRcpDts;

    @OneToMany(mappedBy = "ord", cascade = CascadeType.ALL)
    private List<OrdItem> ordItems = new ArrayList<>();

    @Builder
    public Ord(String ordNo, String orordNo, Date ordRcpDts, String regpeId, String modpeId) {
        /**
         *  해당 생성자 코드에는 UUID 생성 코드가 있어 객체를 생성할 시 반드시 Id 값을 보장
         *  객체에 대한 생성자를 하나로 두고 그것을 @Builder을 통해서 사용하는 것이 효율적
         */
        this.ordNo = UUID.randomUUID().toString();
        this.orordNo = orordNo;
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
        ordDto.setOrdNo(this.ordNo);
        ordDto.setOrordNo(this.orordNo) ;
        ordDto.setOrdRcpDts(this.ordRcpDts);
        ordDto.setRegpeId(this.regpeId);
        ordDto.setModpeId(this.modpeId);
        return ordDto;
    }
}
