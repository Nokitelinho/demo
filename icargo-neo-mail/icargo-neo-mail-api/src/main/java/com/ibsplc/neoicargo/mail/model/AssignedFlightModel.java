package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class AssignedFlightModel extends BaseModel {
	private String companyCode;
	private String airportCode;
	private int carrierId;
	private String flightNumber;
	private int legSerialNumber;
	private long flightSequenceNumber;
	private String carrierCode;
	private LocalDate flightDate;
	private String flightStatus;
	private LocalDate lastUpdateTime;
	private String lastUpdateUser;
	private String importFlightStatus;
	private String exportFlightStatus;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
