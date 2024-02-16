package com.ibsplc.neoicargo.mailmasters.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class USPSPostalCalendarFilterModel extends BaseModel {
	private String filterCalender;
	private LocalDate calValidFrom;
	private LocalDate calValidTo;
	private String calPacode;
	private String companyCode;
	private String listFlag;
	private LocalDate invoiceDate;
	private String calendarType;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
