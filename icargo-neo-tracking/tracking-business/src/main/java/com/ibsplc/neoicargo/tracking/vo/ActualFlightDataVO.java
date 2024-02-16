package com.ibsplc.neoicargo.tracking.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ActualFlightDataVO extends AbstractVO {
    private String carrierCode;
    private String flightNumber;
    private LocalDateTime milestoneTime;
}
