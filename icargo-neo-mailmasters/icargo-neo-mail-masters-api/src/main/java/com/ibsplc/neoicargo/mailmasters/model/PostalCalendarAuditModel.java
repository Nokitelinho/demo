package com.ibsplc.neoicargo.mailmasters.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class PostalCalendarAuditModel extends BaseModel {
	private String postalCode;
	private String period;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
