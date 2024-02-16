package com.ibsplc.neoicargo.cca.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CcaChangedStatusVO {

    private String masterNumber;

    private String ccaNumber;

    private String oldStatus;

    private String newStatus;
}
