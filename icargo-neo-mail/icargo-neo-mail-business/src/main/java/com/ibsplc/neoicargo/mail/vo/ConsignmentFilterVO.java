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
public class ConsignmentFilterVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String consignmentNumber;
	private String paCode;
	private int pageNumber;
	private int totalRecords;
	private String scannedOnline;
	private String conDate;
	private String conType;
	private String routingInfo;
	private String carrierCode;
	private String fltNumber;
	private String scannedPort;
	private String fltDate;
	private String operationMode;
	private String originOfficeOfExchange;
	private String destinationOfficeOfExchange;
	private String mailCategory;
	private String mailSubClass;
	private int consignmentSequenceNumber;
	private String airportCode;
	private String consigmentOrigin;
	private String consignmentDestination;
	private boolean listflag;
	private String subType;
	private int pageSize;
	private boolean bulkDownload;
	private String containerNumber;
	private String containerJourneyId;
	private String bellyCartId;
	private ZonedDateTime consignmentFromDate;
	private ZonedDateTime consignmentToDate;
	private String transferManifestId;
}
