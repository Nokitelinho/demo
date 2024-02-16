package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class ConsignmentFilterModel extends BaseModel {
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
	private LocalDate consignmentFromDate;
	private LocalDate consignmentToDate;
	private String transferManifestId;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
