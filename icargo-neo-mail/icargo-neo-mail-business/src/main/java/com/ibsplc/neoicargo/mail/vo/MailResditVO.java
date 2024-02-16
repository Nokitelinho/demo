package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * TODO Add the purpose of this class
 * @author A-3109
 */
@Setter
@Getter
public class MailResditVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String mailId;
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
	private ZonedDateTime lastUpdateTime;
	private String lastUpdateUser;
	private String partyIdentifier;
	private String mailboxID;
	private long messageIdentifier;
	private boolean fromDeviationList;
	private String senderIdentifier;
	private String recipientIdentifier;
	private String dependantEventCode;
	private String paOrCarrierCode;
	/** 
	* The mailEventSequenceNumber
	*/
	private int mailEventSequenceNumber;
	/** 
	* The interchangeControlReference
	*/
	private String interchangeControlReference;
	/** 
	* The reconciliationStatus
	*/
	private String reconciliationStatus;
	private long mailSequenceNumber;
}
