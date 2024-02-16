package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class ExistingMailbagModel extends BaseModel {
	private String mailId;
	private String carrierCode;
	private String flightNumber;
	private String currentAirport;
	private String flightStatus;
	private LocalDate flightDate;
	private String reassign;
	private String containerNumber;
	private String pol;
	private long flightSequenceNumber;
	private int legSerialNumber;
	private int segmentSerialNumber;
	private String pou;
	private String containerType;
	private String finalDestination;
	private int carrierId;
	private String ubrNumber;
	private LocalDate bookingLastUpdateTime;
	private LocalDate bookingFlightDetailLastUpdTime;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
