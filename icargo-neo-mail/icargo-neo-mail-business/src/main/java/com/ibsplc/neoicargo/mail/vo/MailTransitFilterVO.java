package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

@Setter
@Getter
public class MailTransitFilterVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String airportCode;
	private String fromDate;
	private String toDate;
	private ZonedDateTime flightFromDate;
	private ZonedDateTime flightToDate;
	private String flightNumber;
	private int pageNumber;
	private int pageSize;
	private int totalRecordsCount;
	private String flighFromTime;
	private String flightToTime;
	private ZonedDateTime flightDate;
	private String segmentDestination;
	private String carrierCode;
}
