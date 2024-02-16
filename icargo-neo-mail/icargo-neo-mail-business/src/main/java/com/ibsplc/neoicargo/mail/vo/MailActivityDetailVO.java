package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-5991
 */
@Setter
@Getter
public class MailActivityDetailVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	/** 
	* SLA status - Success
	*/
	public static final String SLASTATUS_SUCCESS = "S";
	/** 
	* SLA status - Failure
	*/
	public static final String SLASTATUS_FAILURE = "F";
	/** 
	* Monitoring mode - Alarm
	*/
	public static final String MONITORING_MODE_ALARM = "ALARM";
	/** 
	* Monitoring mode - Chaser
	*/
	public static final String MONITORING_MODE_CHASER = "CHASER";
	/** 
	* Monitoring mode - Next Chaser
	*/
	public static final String MONITORING_MODE_NEXT_CHASER = "NEXT_CHASER";
	/** 
	* Company code
	*/
	private String companyCode;
	/** 
	* Mail bag number
	*/
	private String mailBagNumber;
	/** 
	* Service level activity. Possible values can be A-Acceptance to Departure, D-Arrival to Delivery
	*/
	private String serviceLevelActivity;
	/** 
	* Airline identifier
	*/
	private int airlineIdentifier;
	/** 
	* Airline code
	*/
	private String airlineCode;
	/** 
	* GPA code
	*/
	private String gpaCode;
	/** 
	* SLA identifier
	*/
	private String slaIdentifier;
	/** 
	* Flight carrier identifier
	*/
	private int flightCarrierId;
	/** 
	* Flight number
	*/
	private String flightNumber;
	/** 
	* Flight carrier code
	*/
	private String flightCarrierCode;
	/** 
	* Planned date and time
	*/
	private ZonedDateTime plannedTime;
	/** 
	* Actual date and time
	*/
	private ZonedDateTime actualTime;
	/** 
	* SLA status. Possible values can be S-Success, F-Failure
	*/
	private String slaStatus;
	/** 
	* Mail category code
	*/
	private String mailCategory;
	/** 
	* Service time in Hours
	*/
	private int serviceTime;
	/** 
	* Alert status. Possible values can be Y - Alert message sent, N - Alert message not sent
	*/
	private String alertStatus;
	/** 
	* Number of chasers sent
	*/
	private int numberOfChasers;
	private int alertTime;
	private int chaserTime;
	private int chaserFrequency;
	private int maximumNumberOfChasers;
}
