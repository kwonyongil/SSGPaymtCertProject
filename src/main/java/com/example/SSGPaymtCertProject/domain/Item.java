package com.example.SSGPaymtCertProject.domain;

import com.example.SSGPaymtCertProject.domain.base.BaseEntity;
import lombok.*;
import org.springframework.security.core.SpringSecurityCoreVersion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
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

    private Long price;

    // @OneToMany는 기본 전략으로 LAZY를 택한다.
    // CasadeType ALL : PERSIST, MERGE, REMOVE, REFRESH, DETACH 모두 적용
    // orphanRemoval : 부모객체가 삭제됬을 때 자식객체 까지 삭제
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrdItem> ordItems = new ArrayList<>();

    @Builder
    public Item(String itemNm, Long price) {
        this.itemNm = itemNm;
        this.price = price;
    }
}
