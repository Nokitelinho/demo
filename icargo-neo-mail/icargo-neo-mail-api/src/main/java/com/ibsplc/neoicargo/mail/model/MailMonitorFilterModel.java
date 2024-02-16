package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailMonitorFilterModel extends BaseModel {
	private LocalDate fromDate;
	private LocalDate toDate;
	private String station;
	private String paCode;
	private String serviceLevel;
	private String type;
	private String companyCode;
	private int pageSize;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
