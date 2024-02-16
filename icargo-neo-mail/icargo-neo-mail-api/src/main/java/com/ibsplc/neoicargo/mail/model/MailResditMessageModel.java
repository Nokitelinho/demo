package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailResditMessageModel extends BaseModel {
	private String companyCode;
	private String msgDetails;
	private String msgText;
	private LocalDate eventDate;
	private String poaCode;
	private String msgVersionNumber;
	private String carditExist;
	private String messageIdentifier;
	private String messageType;
	private String eventCode;
	private String eventPort;
	private long msgSequenceNumber;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
