package com.ibsplc.neoicargo.mailmasters.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-3109
 */
@Setter
@Getter
public class MailSubClassVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	/** 
	* The mail subclass code
	*/
	private String code;
	/** 
	* The description for the mail subclass code
	*/
	private String description;
	private String operationFlag;
	private String companyCode;
	private String subClassGroup;
	private ZonedDateTime lastUpdateTime;
	private String lastUpdateUser;
}
