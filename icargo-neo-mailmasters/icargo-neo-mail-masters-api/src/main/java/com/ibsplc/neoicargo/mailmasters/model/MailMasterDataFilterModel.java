package com.ibsplc.neoicargo.mailmasters.model;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailMasterDataFilterModel extends BaseModel {
	private String companyCode;
	private String masterType;
	private int noOfDaysToConsider;
	private int lastScanTime;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
