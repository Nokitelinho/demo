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
public class CarditTransportationVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	/** 
	* Arrival place of flight
	*/
	private String arrivalPort;
	/** 
	* arrivalPlaceName of flight
	*/
	private String arrivalPlaceName;
	/** 
	* Departure place of flight
	*/
	private String departurePort;
	/** 
	* Departure place name of flight
	*/
	private String departurePlaceName;
	/** 
	* Departure time of flight
	*/
	private ZonedDateTime departureTime;
	/** 
	* Arrival date of flight
	*/
	private ZonedDateTime arrivalDate;
	/** 
	* carrier identification
	*/
	private int carrierID;
	/** 
	* This field is a code list qualifier indicating carrier code
	*/
	private String carrierCode;
	/** 
	* carrier name
	*/
	private String carrierName;
	/** 
	* This field indicates code list responsible agency for place of arrival
	*/
	private String arrivalCodeListAgency;
	/** 
	* This field indicates code list responsible agency for place of departure
	*/
	private String departureCodeListAgency;
	/** 
	* This field indicates code list responsible agency for carrier eg: 3 -IATA, 139 - UPU
	*/
	private String agencyForCarrierCodeList;
	/** 
	* This field indicates conveyance reference number
	*/
	private String conveyanceReference;
	/** 
	* This field indicates mode of transport eg: air, road , rail
	*/
	private String modeOfTransport;
	/** 
	* This field indicates transport stage qualifier eg: 10 - pre carriage transport eg: 20 - main carriage transport
	*/
	private String transportStageQualifier;
	/** 
	* This field is Transport Leg Rate
	*/
	private String transportLegRate;
	private String flightNumber;
	private long flightSequenceNumber;
	private int legSerialNumber;
	private int transportSerialNum;
	private int segmentSerialNum;
	private String transportIdentification;
	private String contractReference;
}
