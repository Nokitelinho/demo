package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-3109
 */
@Setter
@Getter
public class PartnerCarrierVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String companyCode;
	private String ownCarrierCode;
	private String partnerCarrierCode;
	private String airportCode;
	private String partnerCarrierId;
	private String partnerCarrierName;
	private String operationFlag;
	private ZonedDateTime lastUpdateTime;
	private String lastUpdateUser;
	private String MldTfdReq;
}
