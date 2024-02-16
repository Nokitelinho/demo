package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailHistoryRemarksModel extends BaseModel {
	private String companyCode;
	private long mailSequenceNumber;
	private long remarkSerialNumber;
	private String remark;
	private LocalDate remarkDate;
	private String userName;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
