package com.ibsplc.neoicargo.mailmasters.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class PostalAdministrationDetailsModel extends BaseModel {
	private String companyCode;
	private String poaCode;
	private String parCode;
	private String sernum;
	private String billingSource;
	private String billingFrequency;
	private String profInv;
	private String settlementCurrencyCode;
	private LocalDate validFrom;
	private LocalDate validTo;
	private String operationFlag;
	private String parameterValue;
	private String partyIdentifier;
	private String detailedRemarks;
	private String parameterType;
	private String passValue;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
