package com.ibsplc.neoicargo.cca.vo;

import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CCAWorkflowVO extends AbstractVO {

    private static final long serialVersionUID = 1L;

    private CcaStatus ccaStatus;

    private String userName;

    private LocalDateTime requestedDate;

}
