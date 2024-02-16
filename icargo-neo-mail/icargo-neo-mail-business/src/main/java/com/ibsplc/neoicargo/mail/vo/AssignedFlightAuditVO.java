package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-5991
 */
@Setter
@Getter
public class AssignedFlightAuditVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	/** 
	* The airportCode
	*/
	private String airportCode;
	/** 
	* The carrierId
	*/
	private int carrierId;
	/** 
	* The flightNumber
	*/
	private String flightNumber;
	/** 
	* The legSerialNumber
	*/
	private int legSerialNumber;
	/** 
	* The flightSequenceNumber
	*/
	private long flightSequenceNumber;

	public AssignedFlightAuditVO(String arg0, String arg1, String arg2) {
		super();

	}
}
