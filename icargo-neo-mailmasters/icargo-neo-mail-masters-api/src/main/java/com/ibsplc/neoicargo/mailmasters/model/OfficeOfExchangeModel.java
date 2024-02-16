package com.ibsplc.neoicargo.mailmasters.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class OfficeOfExchangeModel extends BaseModel {
	private String code;
	private String codeDescription;
	private String countryCode;
	private String cityCode;
	private boolean isActive;
	private String operationFlag;
	private String officeCode;
	private String poaCode;
	private String mailboxId;
	private String companyCode;
	private LocalDate lastUpdateTime;
	private String lastUpdateUser;
	private String airportCode;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
