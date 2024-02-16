package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-5991
 */
@Setter
@Getter
public class ContainerAuditVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String containerNumber;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int legSerialNumber;
	private String assignedPort;
	private String triggerPnt;

	/** 
	* @param arg0
	* @param arg1
	* @param arg2
	*/

}
