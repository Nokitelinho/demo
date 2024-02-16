package com.ibsplc.neoicargo.mailmasters.vo;

import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-6986
 */
@Setter
@Getter
public class IncentiveConfigurationFilterVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	/** 
	* The ModuleName
	*/
	public static final String MODULE = "mail";
	/** 
	* The SubModuleName
	*/
	public static final String SUBMODULE = "operations";
	private String companyCode;
	private String paCode;
}
