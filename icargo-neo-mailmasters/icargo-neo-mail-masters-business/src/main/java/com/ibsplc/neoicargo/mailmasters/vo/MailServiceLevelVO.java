package com.ibsplc.neoicargo.mailmasters.vo;

import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import java.time.ZonedDateTime;

/** 
 * @author A-6986
 */
@Setter
@Getter
public class MailServiceLevelVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String poaCode;
	private String mailCategory;
	private String mailClass;
	private String mailSubClass;
	private String mailServiceLevel;
	private ZonedDateTime lastUpdatedTime;
	private String lastUpdatedUser;
}
