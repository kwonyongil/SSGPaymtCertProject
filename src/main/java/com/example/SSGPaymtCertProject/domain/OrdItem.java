package com.example.SSGPaymtCertProject.domain;

import com.example.SSGPaymtCertProject.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ORD_ITEM")
public class OrdItem extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "ORD_ITEM_ID")
    private Long ordItemId;

    // @ManyToOne은 기본 fetch전략으로 EAGER을 택한다.
    @ManyToOne
    @JoinColumn(name = "ORDER_NO", nullable = false)
    private Ord ord; // UserGroupId.user와 연결

    @ManyToOne
    @JoinColumn(name = "ITEM_ID", nullable = false)
    private Item item; // UserGroupId.grp와 연결

    @Column(name = "ORD_ITEM_STAT_CD", nullable = false)
    private String ordItemStatCd;

    @Builder
    public OrdItem(Ord ord, Item item, String ordItemStatCd, String regpeId, String modpeId) {
        this.ord = ord;
        this.item = item;
        this.ordItemStatCd = ordItemStatCd;
        this.regpeId = regpeId;
        this.modpeId = modpeId;
    }
}
