package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailOnHandDetailsModel extends BaseModel {
	private String companyCode;
	private String currentAirport;
	private String destination;
	private String subclassGroup;
	private String uldCount;
	private int mailTrolleyCount;
	private int noOfDaysInCurrentLoc;
	private LocalDate scnDate;
	private String scanPort;
	private String uldCountDisplay;
	private int noOfMTSpace;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
