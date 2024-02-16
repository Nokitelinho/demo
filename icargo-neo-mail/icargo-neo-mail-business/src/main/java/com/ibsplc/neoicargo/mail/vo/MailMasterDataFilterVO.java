package com.ibsplc.neoicargo.mail.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MailMasterDataFilterVO extends AbstractVO {
	private static final long serialVersionUID = 1L;
	private String companyCode;
	private String masterType;
	private int noOfDaysToConsider;
	private int lastScanTime;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
