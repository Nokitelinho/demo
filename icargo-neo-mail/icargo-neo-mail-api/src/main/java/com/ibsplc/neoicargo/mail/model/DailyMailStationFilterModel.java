package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class DailyMailStationFilterModel extends BaseModel {
	private LocalDate filghtDate;
	private String flightNumber;
	private int flightCarrireID;
	private long flightSeqNumber;
	private int segSerialNumber;
	private String companyCode;
	private String origin;
	private String destination;
	private String carrierCode;
	private LocalDate flightFromDate;
	private LocalDate flightToDate;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
