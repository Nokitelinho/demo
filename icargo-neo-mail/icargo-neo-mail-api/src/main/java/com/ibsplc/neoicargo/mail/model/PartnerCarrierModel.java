package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class PartnerCarrierModel extends BaseModel {
	private String companyCode;
	private String ownCarrierCode;
	private String partnerCarrierCode;
	private String airportCode;
	private String partnerCarrierId;
	private String partnerCarrierName;
	private String operationFlag;
	private LocalDate lastUpdateTime;
	private String lastUpdateUser;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
