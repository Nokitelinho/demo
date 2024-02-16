package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-3109
 */
@Setter
@Getter
public class UldResditVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String eventCode;
	private String eventAirport;
	private ZonedDateTime eventDate;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private String uldNumber;
	private String resditSentFlag;
	private long resditSequenceNum;
	private String processedStatus;
	private long messageSequenceNumber;
	private String carditKey;
	private String paOrCarrierCode;
	private String containerJourneyId;
	private int uldEventSequenceNumber;
	/** 
	* The interchangeControlReference
	*/
	private String interchangeControlReference;
	/** 
	* shipperBuiltCode - Contains the Shipper Code(PA Code), who build the SB ULD.
	*/
	private String shipperBuiltCode;
}
