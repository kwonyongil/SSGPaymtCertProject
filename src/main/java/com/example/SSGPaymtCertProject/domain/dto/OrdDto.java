package com.example.SSGPaymtCertProject.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@ToString
@Getter
@Setter
@NoArgsConstructor
public class OrdDto extends BaseEntityDto {

    @NotNull
    private String ordNo;

    private String orordNo;

    private Date ordRcpDts;

    private List<OrdItemDto> ordItems = new ArrayList<>();

    @JsonCreator
    @Builder
    public OrdDto(@JsonProperty("ordNo") String ordNo,
                  @JsonProperty("orordNo") String orordNo,
                  @JsonProperty("ordRcpDts") Date ordRcpDts) {
        this.ordNo = ordNo;
        this.orordNo = orordNo;
        this.ordRcpDts = ordRcpDts;
    }

}
