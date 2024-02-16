package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-5219
 */
@Setter
@Getter
public class ForceMajeureRequestFilterVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String source;
	private String orginAirport;
	private String destinationAirport;
	private String viaPoint;
	private String poaCode;
	private String affectedAirport;
	private int carrierID;
	private String flightNumber;
	private ZonedDateTime flightDate;
	private ZonedDateTime fromDate;
	private ZonedDateTime toDate;
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
}
