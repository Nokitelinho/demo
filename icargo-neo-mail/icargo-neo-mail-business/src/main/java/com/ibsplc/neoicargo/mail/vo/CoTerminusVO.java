package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

@Setter
@Getter
public class CoTerminusVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String gpaCode;
	private String coAirportCodes;
	private String resditModes;
	private String truckFlag;
	private String companyCode;
	private String coOperationFlag;
	private ZonedDateTime lastUpdateTime;
	private String lastUpdateUser;
	private long seqnum;
}
