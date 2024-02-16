package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-5219
 */
@Setter
@Getter
public class ForceMajeureRequestVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String forceMajuereID;
	private long sequenceNumber;
	private long mailSeqNumber;
	private String mailID;
	private String airportCode;
	private String carrierCode;
	private String flightNumber;
	private ZonedDateTime flightDate;
	private ZonedDateTime fromDate;
	private ZonedDateTime toDate;
	private String source;
	private Quantity weight;
	private String originAirport;
	private String destinationAirport;
	private String consignmentDocNumber;
	private String status;
	private String requestRemarks;
	private String approvalRemarks;
	private String filterParameters;
	private String lastUpdatedUser;
	private String type;
	private int carrierID;
	private int flightSeqNum;
	private ZonedDateTime requestDate;
	private String loadScan;
	private String recieveScan;
	private String deliveryScan;
	private String lateDeliveryScan;
	private String allScan;
}
