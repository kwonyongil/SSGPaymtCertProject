package com.example.SSGPaymtCertProject.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @since 2021. 09. 02
 * @author kwon-yong-il
 *         <p>
 *         학습을 위해 생성한 복합키 클래스, 실제는 사용하지 않는다.
 *         </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
//@Embeddable
public class OrdItemPk implements Serializable {

//    @Column(name = "ORDER_NO")
    private long orderNo;

//    @Column(name = "ITEM_ID")
    private long itemId;
}