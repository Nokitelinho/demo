package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-8952
 */
@Setter
@Getter
public class ArriveAndImportMailVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private int offset;
	private boolean isArrivalAndDeliveryMarkedTogether;

	/** 
	* @param isArrivalAndDeliveryMarkedTogether the isArrivalAndDeliveryMarkedTogether to set
	*/
	public void setArrivalAndDeliveryMarkedTogether(boolean isArrivalAndDeliveryMarkedTogether) {
		this.isArrivalAndDeliveryMarkedTogether = isArrivalAndDeliveryMarkedTogether;
	}
}
