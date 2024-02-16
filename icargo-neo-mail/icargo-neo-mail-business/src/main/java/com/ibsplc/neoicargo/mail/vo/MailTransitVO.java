package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

@Setter
@Getter
public class MailTransitVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String carrierCode;
	private String mailBagDestination;
	private String totalNoImportBags;
	private String totalWeightImportBags;
	private String countOfExportNonAssigned;
	private String totalWeightOfExportNotAssigned;
}
