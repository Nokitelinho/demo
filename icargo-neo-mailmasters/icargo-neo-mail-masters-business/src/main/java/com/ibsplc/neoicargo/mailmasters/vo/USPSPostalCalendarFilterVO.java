package com.ibsplc.neoicargo.mailmasters.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

@Setter
@Getter
public class USPSPostalCalendarFilterVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String filterCalender;
	private ZonedDateTime calValidFrom;
	private ZonedDateTime calValidTo;
	private String calPacode;
	private String companyCode;
	private String listFlag;
	private ZonedDateTime invoiceDate;
	private String calendarType;
}
