package com.example.SSGPaymtCertProject.domain.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = false)
@ToString
@Getter
@Setter
@NoArgsConstructor
public class ItemDto extends BaseEntityDto {

    private Long id;

    private String itemNm;

    private Long price;

}
