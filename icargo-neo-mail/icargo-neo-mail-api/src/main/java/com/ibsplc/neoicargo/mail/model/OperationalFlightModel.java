package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class OperationalFlightModel extends BaseModel {
	private String companyCode;
	private String pol;
	private String pou;
	private int carrierId;
	private String flightNumber;
	private int legSerialNumber;
	private long flightSequenceNumber;
	private String carrierCode;
	private LocalDate flightDate;
	private String direction;
	private String ownAirlineCode;
	private int ownAirlineId;
	private LocalDate operationTime;
	private String flightStatus;
	private LocalDate arrivaltime;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String flightOperationStatus;
	private int recordsPerPage;
	private String containerNumber;
	private String operatingReference;
	private LocalDate reqDeliveryTime;
	private boolean isScanned;
	private String flightRoute;
	private String operator;
	private String airportCode;
	private LocalDate actualTimeOfDeparture;
	private int segSerNum;
	private String mailStatus;
	private int totalRecords;
	private Integer pageNumber;
	private Integer absoluteIndex;
	private LocalDate depFromDate;
	private LocalDate depToDate;
	private LocalDate arrFromDate;
	private LocalDate arrToDate;
	private String lastUpdatedUser;
	private LocalDate lastUpdatedTime;
	private String tailNumber;
	private boolean forUCMSendChk;
	private boolean includeAllMailTrucks;
	private LocalDate actualArrivalTime;
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
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
