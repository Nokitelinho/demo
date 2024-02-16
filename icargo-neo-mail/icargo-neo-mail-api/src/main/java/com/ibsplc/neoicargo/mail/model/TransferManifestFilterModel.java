package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class TransferManifestFilterModel extends BaseModel {
	private String referenceNumber;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String inCarrierCode;
	private String inFlightNumber;
	private LocalDate inFlightDate;
	private String outCarrierCode;
	private String outFlightNumber;
	private LocalDate outFlightDate;
	private int pageNumber;
	private String companyCode;
	private String airportCode;
	private String transferStatus;
	private int totalRecordsCount;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
