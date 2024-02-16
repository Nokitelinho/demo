package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-5991
 */
@Setter
@Getter
public class AssignedFlightVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String airportCode;
	private int carrierId;
	private String flightNumber;
	private int legSerialNumber;
	private long flightSequenceNumber;
	private String carrierCode;
	private ZonedDateTime flightDate;
	private String flightStatus;
	private ZonedDateTime lastUpdateTime;
	private String lastUpdateUser;
	private String importFlightStatus;
	private String exportFlightStatus;
	private String exportClosingFlag;
	/** 
	* module
	*/
	public static final String MODULE = "mail";
	/** 
	* submodule
	*/
	public static final String SUBMODULE = "operations";
	/** 
	* entity
	*/
	public static final String ENTITY = "mail.operations.AssignedFlight";
@Override
	public String getBusinessId(){
	return this.carrierId+","+this.flightNumber+","+this.flightSequenceNumber+","+this.legSerialNumber;
	}
}
