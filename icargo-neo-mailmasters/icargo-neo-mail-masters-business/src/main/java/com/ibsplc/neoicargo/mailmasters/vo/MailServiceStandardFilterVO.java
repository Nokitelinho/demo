package com.ibsplc.neoicargo.mailmasters.vo;

import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-8149
 */
@Setter
@Getter
public class MailServiceStandardFilterVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	String companyCode;
	String gpaCode;
	String orginCode;
	String destCode;
	String servLevel;
	String scanWaived;
	String serviceStandard;
	String contractId;
	private int pageNumber;
	private int totalRecords;
	private int defaultPageSize;
}
