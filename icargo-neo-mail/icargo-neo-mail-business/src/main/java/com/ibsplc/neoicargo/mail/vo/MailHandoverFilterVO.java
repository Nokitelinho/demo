package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-6986
 */
@Setter
@Getter
public class MailHandoverFilterVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String gpaCode;
	private String airportCode;
	private String mailClass;
	private String exchangeOffice;
	private String mailSubClass;
	private int recordsPerPage;
	private int totalRecords;
	private int pageNumber;
	private int defaultPageSize;
}
