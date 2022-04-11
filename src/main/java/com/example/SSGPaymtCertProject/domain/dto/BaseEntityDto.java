package com.example.SSGPaymtCertProject.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

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

    @JsonCreator
    public BaseEntityDto(@JsonProperty("regpeId") String regpeId,
                  @JsonProperty("regDts") Date regDts,
                  @JsonProperty("modpeId") String modpeId,
                  @JsonProperty("modDts") Date modDts) {
        this.regpeId = regpeId;
        this.regDts = regDts;
        this.modpeId = modpeId;
        this.modDts = modDts;
    }
}
