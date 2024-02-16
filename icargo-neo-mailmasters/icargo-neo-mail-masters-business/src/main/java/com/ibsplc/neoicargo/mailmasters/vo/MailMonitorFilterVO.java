package com.ibsplc.neoicargo.mailmasters.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

@Setter
@Getter
public class MailMonitorFilterVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private ZonedDateTime fromDate;
	private ZonedDateTime toDate;
	private String station;
	private String paCode;
	private String serviceLevel;
	private String type;
	private String companyCode;
	private int pageSize;
}
