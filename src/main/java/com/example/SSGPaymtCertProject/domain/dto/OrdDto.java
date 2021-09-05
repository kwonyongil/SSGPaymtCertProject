package com.example.SSGPaymtCertProject.domain.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@ToString
@Getter
@Setter
@NoArgsConstructor
public class OrdDto extends BaseEntityDto {

    private Long orderNo;

    private Long ororderNo;

    private Date ordRcpDts;

    private List<OrdItemDto> ordItems = new ArrayList<>();

}
