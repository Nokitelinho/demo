package com.ibsplc.neoicargo.mail.model;

import java.util.Calendar;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class ForceMajeureRequestModel extends BaseModel {
	private String companyCode;
	private String forceMajuereID;
	private long sequenceNumber;
	private long mailSeqNumber;
	private String mailID;
	private String airportCode;
	private String carrierCode;
	private String flightNumber;
	private LocalDate flightDate;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String source;
	private Measure weight;
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
	private LocalDate requestDate;
	private String loadScan;
	private String recieveScan;
	private String deliveryScan;
	private String lateDeliveryScan;
	private String allScan;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
