package com.example.SSGPaymtCertProject.domain;

import com.example.SSGPaymtCertProject.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ORD_ITEM")
//@IdClass(OrdItemPk.class)
public class OrdItem extends BaseEntity {

    //    @EmbeddedId
    //    private OrdItemPk pk;

    @Id
    @GeneratedValue
    @Column(name = "ORD_ITEM_ID")
    private Long ordItemId;

    // @ManyToOne 은 기본 fetch 전략으로 EAGER 을 택한다.
    @ManyToOne
    @JoinColumn(name = "ORDER_NO", nullable = false)
    private Ord ord; // OrdItemId.ord 와 연결

    @ManyToOne
    @JoinColumn(name = "ITEM_ID", nullable = false)
    private Item item; // OrdItemId.item 과 연결

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @Column(name = "ORD_ITEM_STAT_CD", nullable = false)
    private String ordItemStatCd;

    @Builder
    public OrdItem(Ord ord, Item item, int quantity, String ordItemStatCd, String regpeId, String modpeId) {
        this.ord = ord;
        this.item = item;
        this.quantity = quantity;
        this.ordItemStatCd = ordItemStatCd;
        this.regpeId = regpeId;
        this.modpeId = modpeId;
    }
}
