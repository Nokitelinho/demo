package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-3109
 */
@Setter
@Getter
public class TransferManifestFilterVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String referenceNumber;
	private ZonedDateTime fromDate;
	private ZonedDateTime toDate;
	private String inCarrierCode;
	private String inFlightNumber;
	private ZonedDateTime inFlightDate;
	private String outCarrierCode;
	private String outFlightNumber;
	private ZonedDateTime outFlightDate;
	private int pageNumber;
	private String companyCode;
	private String airportCode;
	private String transferStatus;
	private int totalRecordsCount;
}
