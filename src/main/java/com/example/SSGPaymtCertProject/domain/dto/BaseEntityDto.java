package com.example.SSGPaymtCertProject.domain.dto;

import lombok.*;

import java.util.Date;

@EqualsAndHashCode
@ToString
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseEntityDto {

    protected String regpeId;

    private Date regDts;

    protected String modpeId;

    private Date modDts;
}
