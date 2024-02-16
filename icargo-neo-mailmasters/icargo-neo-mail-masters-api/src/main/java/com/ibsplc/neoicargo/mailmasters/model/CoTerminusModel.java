package com.ibsplc.neoicargo.mailmasters.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class CoTerminusModel extends BaseModel {
	private String gpaCode;
	private String coAirportCodes;
	private String resditModes;
	private String truckFlag;
	private String companyCode;
	private String coOperationFlag;
	private LocalDate lastUpdateTime;
	private String lastUpdateUser;
	private long seqnum;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
