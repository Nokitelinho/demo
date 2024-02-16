package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailTransitModel extends BaseModel {
	private String carrierCode;
	private String mailBagDestination;
	private String totalNoImportBags;
	private String totalWeightImportBags;
	private String countOfExportNonAssigned;
	private String totalWeightOfExportNotAssigned;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
