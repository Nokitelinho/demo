package com.ibsplc.neoicargo.mailmasters.vo;

import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-3109
 */
@Setter
@Getter
public class MLDConfigurationVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String companyCode;
	private String carrierCode;
	private int carrierIdentifier;
	private String airportCode;
	private String allocatedRequired;
	private String upliftedRequired;
	private String deliveredRequired;
	private String hNDRequired;
	private String receivedRequired;
	private String operationFlag;
	private String mldversion;
	private String stagedRequired;
	private String nestedRequired;
	private String receivedFromFightRequired;
	private String transferredFromOALRequired;
	private String receivedFromOALRequired;
	private String returnedRequired;

	/** 
	* Method is to get hNDRequired
	* @return
	*/
	public String gethNDRequired() {
		return hNDRequired;
	}

	/** 
	* Method is to set hNDRequired
	* @param hNDRequired
	*/
	public void sethNDRequired(String hNDRequired) {
		this.hNDRequired = hNDRequired;
	}
}
