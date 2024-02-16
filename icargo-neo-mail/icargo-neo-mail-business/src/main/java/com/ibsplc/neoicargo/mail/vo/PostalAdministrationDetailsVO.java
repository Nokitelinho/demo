package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-3109
 */
@Setter
@Getter
public class PostalAdministrationDetailsVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String companyCode;
	private String poaCode;
	private String parCode;
	private String sernum;
	private String billingSource;
	private String billingFrequency;
	private String profInv;
	private String settlementCurrencyCode;
	private ZonedDateTime validFrom;
	private ZonedDateTime validTo;
	private String operationFlag;
	private String parameterValue;
	private String partyIdentifier;
	private String detailedRemarks;
	private String parameterType;
	private String passValue;
}
