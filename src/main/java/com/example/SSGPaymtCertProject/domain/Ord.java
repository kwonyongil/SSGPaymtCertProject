package com.example.SSGPaymtCertProject.domain;

import com.example.SSGPaymtCertProject.domain.base.BaseEntity;
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
    private Long ordrNo;

    @Column(name = "ORORDER_NO", nullable = false)
    private Long orordNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ORD_RCP_DTS", nullable = false)
    private Date ordRcpDts;

    // @OneToMany Fetch기본전략은 LAZY이지만 유저 데이타를 이용할 일이 많으므로 그룹은 즉시 로딩을 하도록 한다.
    @OneToMany(mappedBy = "ord", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrdItem> ordItems = new ArrayList<>();

    @Builder
    public Ord(Long ordrNo, Long orordNo, Date ordRcpDts) {
        this.ordrNo = ordrNo;
        this.orordNo = orordNo;
        this.ordRcpDts = ordRcpDts;
    }

    //==연관관계 메서드==//
    public void addOrdItem(OrdItem ordItem) {
        ordItems.add(ordItem);
    }
}
