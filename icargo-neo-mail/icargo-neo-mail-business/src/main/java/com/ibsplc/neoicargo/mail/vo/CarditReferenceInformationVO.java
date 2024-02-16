package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-5991 
 */
@Setter
@Getter
public class CarditReferenceInformationVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	/** 
	* Transport Contract Reference Qualifier
	*/
	private String transportContractReferenceQualifier;
	/** 
	* Consignment Contract Reference Number
	*/
	private String consignmentContractReferenceNumber;
	/** 
	* CARDIT Type
	*/
	private String carditType;
	/** 
	* CARDIT Key
	*/
	private String carditKey;
	/** 
	* Reference Number
	*/
	private String refNumber;
	/** 
	* Reference Qualifier
	*/
	private String rffQualifier;
	private String orgin;
	private String destination;
	private String contractRef;
}
