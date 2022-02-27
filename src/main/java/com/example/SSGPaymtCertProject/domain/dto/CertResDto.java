package com.example.SSGPaymtCertProject.domain.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = false)
@ToString
@Getter
@Setter
@NoArgsConstructor
public class CertResDto {

    private boolean success;
    private String code;
    private String message;
    private CertDataResDto data;
}
