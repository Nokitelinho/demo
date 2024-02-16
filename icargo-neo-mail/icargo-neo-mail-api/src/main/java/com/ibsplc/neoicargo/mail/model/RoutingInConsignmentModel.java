package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class RoutingInConsignmentModel extends BaseModel {
	private String pou;
	private String onwardFlightNumber;
	private LocalDate onwardFlightDate;
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
	private Measure weight;
	private int segmentSerialNumber;
	private String originOfficeOfExchange;
	private String destinationOfficeOfExchange;
	private String mailCategoryCode;
	private String mailSubClass;
	private int year;
	private int legSerialNumber;
	private int previousNoOfPieces;
	private Measure previousWeight;
	private boolean invalidFlightFlag;
	private boolean acceptanceFlag;
	private String dsn;
	private String mailClass;
	private boolean offloadFlag;
	private boolean isFlightClosed;
	private String remarks;
	private int recievedNoOfPieces;
	private Measure recievedWeight;
	private LocalDate scheduledArrivalDate;
	private String flightCarrierCode;
	private String polAirportName;
	private String pouAirportName;
	private String transportStageQualifier;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
