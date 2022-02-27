package com.example.SSGPaymtCertProject.domain;

import com.example.SSGPaymtCertProject.domain.base.BaseEntity;
import com.example.SSGPaymtCertProject.domain.dto.ItemDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@ToString(exclude = "ordItems")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ITEM")
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // 사실 디폴트가 AUTO
    @Column(name = "ITEM_ID")
    private Long id;

    @Column(name = "ITEM_NM", nullable = false)
    private String itemNm;

    // 생략가능
    private Long price;

    // @OneToMany 는 기본 전략으로 LAZY 를 택한다.
    // CascadeType ALL : PERSIST, MERGE, REMOVE, REFRESH, DETACH 모두 적용
    // orphanRemoval : 부모객체가 삭제됬을 때 자식객체 까지 삭제
    @OneToMany(
            mappedBy = "item",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<OrdItem> ordItems = new ArrayList<>();


    @Builder
    public Item(String itemNm, Long price, String regpeId, String modpeId) {
        this.itemNm = itemNm;
        this.price = price;
        this.regpeId = regpeId;
        this.modpeId = modpeId;
    }

    public ItemDto toItemDto() {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(this.id);
        itemDto.setItemNm(this.itemNm) ;
        itemDto.setPrice(this.price);
        itemDto.setRegpeId(this.regpeId);
        itemDto.setModpeId(this.modpeId);
        return itemDto;
    }
}
