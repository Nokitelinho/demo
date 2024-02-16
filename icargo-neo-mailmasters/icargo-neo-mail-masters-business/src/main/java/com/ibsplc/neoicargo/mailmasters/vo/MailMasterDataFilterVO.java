package com.ibsplc.neoicargo.mailmasters.vo;

import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

@Setter
@Getter
public class MailMasterDataFilterVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String masterType;
	private int noOfDaysToConsider;
	private int lastScanTime;
}
