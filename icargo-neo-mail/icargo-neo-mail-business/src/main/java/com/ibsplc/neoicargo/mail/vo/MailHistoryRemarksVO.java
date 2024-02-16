package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

@Setter
@Getter
public class MailHistoryRemarksVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	/** 
	*/
	private static final long serialVersionUID = 1L;
	private String companyCode;
	private long mailSequenceNumber;
	private long remarkSerialNumber;
	private String remark;
	private ZonedDateTime remarkDate;
	private String userName;
}
