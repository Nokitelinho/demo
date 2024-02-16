package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-3109
 */
@Setter
@Getter
public class MonitorMailSLAVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	/** 
	* Mail status - Accepted
	*/
	public static final String MAILSTATUS_ACCEPTED = "ACP";
	/** 
	* Mail status - Arrived
	*/
	public static final String MAILSTATUS_ARRIVED = "ARR";
	/** 
	* Mail status - Delivered
	*/
	public static final String MAILSTATUS_DELIVERED = "DLV";
	/** 
	* Mail status - Manifested
	*/
	public static final String MAILSTATUS_MANIFESTED = "MFT";
	/** 
	* Mail status - Offloaded
	*/
	public static final String MAILSTATUS_OFFLOADED = "OFL";
	/** 
	* Service level activity - Acceptance to departure
	*/
	public static final String SLAACTIVITY_ACCEPTANCE_TO_DEPARTURE = "A";
	/** 
	* Service level activity - Arrival to delivery
	*/
	public static final String SLAACTIVITY_ARRIVAL_TO_DELIVERY = "D";
	/** 
	* Company code
	*/
	private String companyCode;
	/** 
	* Mail bag number
	*/
	private String mailBagNumber;
	/** 
	* Service level activity(SLA). Possible values can be ACP-Accepted, ARR-Arrived, DLV-Delivered,OFL-Offloaded, MFT-Manifested
	*/
	private String activity;
	/** 
	* Operation flag
	*/
	private String operationFlag;
	/** 
	* Scan date and time
	*/
	private ZonedDateTime scanTime;
	/** 
	* Airline identifier
	*/
	private int airlineIdentifier;
	/** 
	* Airline code
	*/
	private String airlineCode;
	/** 
	* Flight carrier identifier
	*/
	private int flightCarrierIdentifier;
	/** 
	* Flight carrier code
	*/
	private String flightCarrierCode;
	/** 
	* Flight number
	*/
	private String flightNumber;
}
