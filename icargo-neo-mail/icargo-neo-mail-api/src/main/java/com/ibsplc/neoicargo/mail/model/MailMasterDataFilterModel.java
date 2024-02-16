package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.neoicargo.common.modal.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MailMasterDataFilterModel extends BaseModel {
	private static final long serialVersionUID = 1L;
	private String companyCode;
	private String masterType;
	private int noOfDaysToConsider;
	private int lastScanTime;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
