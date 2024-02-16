package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MonitorMailSLAModel extends BaseModel {
	private String companyCode;
	private String mailBagNumber;
	private String activity;
	private String operationFlag;
	private LocalDate scanTime;
	private int airlineIdentifier;
	private String airlineCode;
	private int flightCarrierIdentifier;
	private String flightCarrierCode;
	private String flightNumber;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
