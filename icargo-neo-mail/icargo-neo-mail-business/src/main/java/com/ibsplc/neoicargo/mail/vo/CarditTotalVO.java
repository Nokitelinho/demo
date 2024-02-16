package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * TODO Add the purpose of this class
 * @author A-5991
 */
@Setter
@Getter
public class CarditTotalVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	/** 
	* Number of receptacles If value is null default value is set as -1
	*/
	private String numberOfReceptacles;
	/** 
	* Weight of receptacles If value is null default value is set as -1
	*/
	private Quantity weightOfReceptacles;
	private String mailClassCode;
	private Quantity totalWeight;
}
