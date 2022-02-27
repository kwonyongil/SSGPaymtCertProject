package com.example.SSGPaymtCertProject.service.ord;

import com.example.SSGPaymtCertProject.domain.Item;
import com.example.SSGPaymtCertProject.domain.Ord;
import com.example.SSGPaymtCertProject.domain.OrdItem;
import com.example.SSGPaymtCertProject.domain.dto.OrdDto;
import com.example.SSGPaymtCertProject.repository.ItemRepository;
import com.example.SSGPaymtCertProject.repository.OrdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @since 2021. 09. 01
 * @author kwon-yong-il
 */
@Service
@RequiredArgsConstructor
public class OrdService {

    private final OrdRepository ordRepository;

    private final ItemRepository itemRepository;

    /**
     * @param ordDto 주문Dto
     * @return OrdDto 주문Dto
     */
    @Transactional
    public OrdDto createOrd(OrdDto ordDto) {
        // 주문 데이타를 생성한다.
        Ord ord = Ord.builder()
                .ordNo(ordDto.getOrdNo())
                .orordNo(ordDto.getOrordNo())
                .ordRcpDts(new Date())
                .build();

        ordDto.getOrdItems()
                .forEach(ordItemDto -> {
                    /**
                     * 다대일 저장
                     * JPA 는 엔티티를 저장할 때 객체와 연관관계를 만들어주는 측면이 조금 불편하다.
                     * findById()를 사용하면 데이터의 조회가 이뤄져서 연관관계의 엔티티를 조회하기 위한 select + insert쿼리가 수행된다.
                     * 개선하기위해 엔티티의 id 값만 설정한 임시 엔티티를 만들어주는 방식과
                     * getOne()을 사용한 방식이 있다.
                     * 임시로 엔티티를 만들어서 연관 관계를 만드는 방법은 기존의 db 데이터와 불일치 문제를 야기할 수 있다.
                     * getOne()을 사용할시 다른 필드에 접근했을 경우 db 에 존재하지 않는다면 예외를 반환하고
                     * 존재하는 경우 lazy 방식으로 조회하기 때문에 데이터의 불일치 문제를 해결할 수 있다.
                     * 따라서 연관 관계를 갖는 엔티티를 저장할 때, 연관된 엔티티 조회시 getOne()을 사용하는 것이 성능 개선에 도움이 된다.
                     * getById() 는 원래 getOne() 이었으나 해당 메소드가 Deprecated 되고 대체되었습니다.
                     */
                    Item item = itemRepository.getById(ordItemDto.getItem().getId());

                    OrdItem ordItem = OrdItem.builder()
                            .ord(ord)
                            .item(item)
                            .quantity(ordItemDto.getQuantity())
                            .ordItemStatCd(ordItemDto.getOrdItemStatCd())
                            .regpeId(ordItemDto.getRegpeId())
                            .modpeId(ordItemDto.getModpeId())
                            .build();
                    // cascade 되었기 때문에 ordItem 도 함께 저장
                    ord.addOrdItem(ordItem);
                });
        Ord newOrd = ordRepository.save(ord);
        return newOrd.toOrdDto();
    }
}
