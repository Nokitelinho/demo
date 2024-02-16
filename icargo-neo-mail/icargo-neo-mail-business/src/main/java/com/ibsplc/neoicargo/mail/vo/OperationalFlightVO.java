package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-3109
 */
@Setter
@Getter
public class OperationalFlightVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String pol;
	private String pou;
	private int carrierId;
	private String flightNumber;
	private int legSerialNumber;
	private long flightSequenceNumber;
	private String carrierCode;
	private ZonedDateTime flightDate;
	private String direction;
	private String ownAirlineCode;
	private int ownAirlineId;
	private ZonedDateTime operationTime;
	private String flightStatus;
	private ZonedDateTime arrivaltime;
	private ZonedDateTime fromDate;
	private ZonedDateTime toDate;
	private String flightOperationStatus;
	private int recordsPerPage;
	private String containerNumber;
	private String operatingReference;
	private ZonedDateTime reqDeliveryTime;
	private boolean isScanned;
	private String flightRoute;
	private String operator;
	private String airportCode;
	private ZonedDateTime actualTimeOfDeparture;
	private int segSerNum;
	private String mailStatus;
	private int totalRecords;
	private Integer pageNumber;
	private Integer absoluteIndex;
	private ZonedDateTime depFromDate;
	private ZonedDateTime depToDate;
	private ZonedDateTime arrFromDate;
	private ZonedDateTime arrToDate;
	private String lastUpdatedUser;
	private ZonedDateTime lastUpdatedTime;
	private String tailNumber;
	private boolean forUCMSendChk;
	private boolean includeAllMailTrucks;
	private ZonedDateTime actualArrivalTime;
	private String legDestination;
	private boolean atdCaptured;
	private boolean ataCaptured;
	private String destination;
	private boolean mailFlightOnly;
	private boolean transferOutOperation;
	private boolean reassignInternally;
	private boolean transferStatus;
	private String countryCode;
	private String flightType;
	private String fltOwner;
	private boolean cstatus;

	/** 
	* @param isScanned the isScanned to set
	*/
	public void setScanned(boolean isScanned) {
		this.isScanned = isScanned;
	}
}
