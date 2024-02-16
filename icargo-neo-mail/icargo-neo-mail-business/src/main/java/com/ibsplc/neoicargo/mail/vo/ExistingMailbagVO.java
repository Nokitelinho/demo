package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-5991
 */
@Setter
@Getter
public class ExistingMailbagVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private static final long serialVersionUID = 1L;
	private String mailId;
	private String carrierCode;
	private String flightNumber;
	private String currentAirport;
	private String flightStatus;
	private ZonedDateTime flightDate;
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
	private ZonedDateTime bookingLastUpdateTime;
	private ZonedDateTime bookingFlightDetailLastUpdTime;
}
