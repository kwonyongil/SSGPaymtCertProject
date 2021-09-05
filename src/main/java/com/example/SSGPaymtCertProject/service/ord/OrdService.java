package com.example.SSGPaymtCertProject.service.ord;

import com.example.SSGPaymtCertProject.domain.Item;
import com.example.SSGPaymtCertProject.domain.Ord;
import com.example.SSGPaymtCertProject.domain.OrdItem;
import com.example.SSGPaymtCertProject.domain.dto.OrdDto;
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

    @Transactional
    public OrdDto createOrd(OrdDto ordDto) {
        Ord ord = Ord.builder()
                .orderNo(ordDto.getOrderNo())
                .ororderNo(ordDto.getOrorderNo())
                .ordRcpDts(new Date())
                .build();

        ordDto.getOrdItems()
                .forEach(ordItemDto -> {
                    Item item = Item.builder()
                            .id(ordItemDto.getItem().getId())
                            .build();

                    OrdItem ordItem = OrdItem.builder()
                            .ord(ord)
                            .item(item)
                            .quantity(ordItemDto.getQuantity())
                            .ordItemStatCd(ordItemDto.getOrdItemStatCd())
                            .regpeId(ordItemDto.getRegpeId())
                            .modpeId(ordItemDto.getModpeId())
                            .build();

                    ord.addOrdItem(ordItem);
                });
        Ord newOrd = ordRepository.save(ord);
        return newOrd.toOrdDto();
    }
}
