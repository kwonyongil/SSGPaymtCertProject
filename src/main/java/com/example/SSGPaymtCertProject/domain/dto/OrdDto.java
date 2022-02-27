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

    private String ordNo;

    private String orordNo;

    private Date ordRcpDts;

    private List<OrdItemDto> ordItems = new ArrayList<>();

}
