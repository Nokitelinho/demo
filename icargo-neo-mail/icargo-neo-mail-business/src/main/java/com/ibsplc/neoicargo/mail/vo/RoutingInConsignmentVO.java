package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-3109
 */
@Setter
@Getter
public class RoutingInConsignmentVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String pou;
	private String onwardFlightNumber;
	private ZonedDateTime onwardFlightDate;
	private String onwardCarrierCode;
	private int onwardCarrierId;
	private long onwardCarrierSeqNum;
	private String companyCode;
	private String consignmentNumber;
	private String paCode;
	private int consignmentSequenceNumber;
	private int routingSerialNumber;
	private String operationFlag;
	private String pol;
	private String isDuplicateFlightChecked;
	private int noOfPieces;
	private Quantity weight;
	private int segmentSerialNumber;
	private String originOfficeOfExchange;
	private String destinationOfficeOfExchange;
	private String mailCategoryCode;
	private String mailSubClass;
	private int year;
	private int legSerialNumber;
	private int previousNoOfPieces;
	private Quantity previousWeight;
	private boolean invalidFlightFlag;
	private boolean acceptanceFlag;
	private String dsn;
	private String mailClass;
	private boolean offloadFlag;
	private boolean isFlightClosed;
	private String remarks;
	private int recievedNoOfPieces;
	private Quantity recievedWeight;
	private ZonedDateTime scheduledArrivalDate;
	private String flightCarrierCode;
	private String polAirportName;
	private String pouAirportName;
	private String transportStageQualifier;
}
