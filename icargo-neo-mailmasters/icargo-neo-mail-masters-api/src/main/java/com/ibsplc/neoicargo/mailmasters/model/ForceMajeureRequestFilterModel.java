package com.ibsplc.neoicargo.mailmasters.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class ForceMajeureRequestFilterModel extends BaseModel {
	private String companyCode;
	private String source;
	private String orginAirport;
	private String destinationAirport;
	private String viaPoint;
	private String poaCode;
	private String affectedAirport;
	private int carrierID;
	private String flightNumber;
	private LocalDate flightDate;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String filterParameters;
	private String reqRemarks;
	private String apprRemarks;
	private String lastUpdatedUser;
	private int totalRecords;
	private int pageNumber;
	private String currentAirport;
	private String transactionCode;
	private int txnSerialNumber;
	private int defaultPageSize;
	private String forceMajeureID;
	private String status;
	private String sortingField;
	private String sortOrder;
	private String scanType;
	private String scanTypeDetail;
	private String mailbagId;
	private String consignmentNo;
	private String airportCode;
	private String carrierCode;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
