package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-5991
 */
@Setter
@Getter
public class DailyMailStationFilterVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private ZonedDateTime filghtDate;
	private String flightNumber;
	private int flightCarrireID;
	private long flightSeqNumber;
	private int segSerialNumber;
	private String companyCode;
	private String origin;
	private String destination;
	private String carrierCode;
	private ZonedDateTime flightFromDate;
	private ZonedDateTime flightToDate;
}
