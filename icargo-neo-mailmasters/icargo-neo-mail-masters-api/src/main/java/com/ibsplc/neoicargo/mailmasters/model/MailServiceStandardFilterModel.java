package com.ibsplc.neoicargo.mailmasters.model;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailServiceStandardFilterModel extends BaseModel {
	private String companyCode;
	private String gpaCode;
	private String orginCode;
	private String destCode;
	private String servLevel;
	private String scanWaived;
	private String serviceStandard;
	private String contractId;
	private int pageNumber;
	private int totalRecords;
	private int defaultPageSize;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
