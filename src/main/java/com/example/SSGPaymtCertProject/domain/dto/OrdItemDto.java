package com.example.SSGPaymtCertProject.domain.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = false)
@ToString
@Getter
@Setter
@NoArgsConstructor
public class OrdItemDto extends BaseEntityDto {

    private Long ordItemId;

    private OrdDto ord;

    private ItemDto item;

    private Integer quantity;

    private String ordItemStatCd;
}
