package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-5991Used to get details reg a container assignment.
 */
@Setter
@Getter
public class ContainerAssignmentVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String airportCode;
	private String carrierCode;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private ZonedDateTime flightDate;
	private String destination;
	private String flightStatus;
	private int legSerialNumber;
	private String containerNumber;
	private String acceptanceFlag;
	private String pou;
	private String containerType;
	private String arrivalFlag;
	private String transferFlag;
	private String journeyID;
	private String remark;
	private String uldFulIndFlag;
	/** 
	* ULD RELEASE FLAG This flag is used to determine whether  the uld is released from a segment. ULD will be released at the time of  Inbound Flight Closure.
	*/
	private String releasedFlag;
	private String transactionCode;
	/** 
	* Transit Flag
	*/
	private String transitFlag;
	/** 
	* shipperBuiltCode - Contains the Shipper Code(PA Code), who build the SB ULD.
	*/
	private String shipperBuiltCode;
	private String offloadStatus;
	private String poaFlag;
	private Quantity actualWeight;
	private ZonedDateTime assignedDate;
	private long uldReferenceNo;
}
