package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailTransitFilterModel extends BaseModel {
	private String airportCode;
	private String fromDate;
	private String toDate;
	private LocalDate flightFromDate;
	private LocalDate flightToDate;
	private String flightNumber;
	private int pageNumber;
	private int pageSize;
	private int totalRecordsCount;
	private String flighFromTime;
	private String flightToTime;
	private LocalDate flightDate;
	private String segmentDestination;
	private String carrierCode;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
