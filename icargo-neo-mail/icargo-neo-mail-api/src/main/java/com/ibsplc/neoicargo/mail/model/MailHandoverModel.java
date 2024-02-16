package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailHandoverModel extends BaseModel {
	private String companyCode;
	private String gpaCode;
	private String hoAirportCodes;
	private String serviceLevels;
	private String handoverTimes;
	private String hoOperationFlags;
	private LocalDate lastUpdateTime;
	private String lastUpdateUser;
	private String mailClass;
	private String exchangeOffice;
	private String mailSubClass;
	private int serialNumber;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
