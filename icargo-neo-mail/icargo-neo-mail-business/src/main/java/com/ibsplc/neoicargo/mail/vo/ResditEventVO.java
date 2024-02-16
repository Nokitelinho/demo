package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-3109
 */
@Setter
@Getter
public class ResditEventVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	/** 
	* Code of the Resdit Event
	*/
	private String resditEventCode;
	/** 
	* Status of the Resdit Event
	*/
	private String resditEventStatus;
	/** 
	* ConsignmentId of the receptacles for this resdit event
	*/
	private String consignmentNumber;
	/** 
	* Port at which the resditEvent happened
	*/
	private String eventPort;
	/** 
	* Unique Id for this resditEvent
	*/
	private String uniqueIdForFlag;
	/** 
	* Unique Id for this resditEvent
	*/
	private int messageSequenceNumber;
	private String actualResditEvent;
	private String paCode;
	private Collection<MailbagVO> resditMailbagVOs;
	private String resditVersion;
	private String eventPortName;
	private String carditExist;
	/** 
	* Actual Sender Id
	*/
	private String actualSenderId;
	private String interchangeControlReference;
	/** 
	* Event Date Added as part of ICRD-181309 
	*/
	private ZonedDateTime eventDate;
	private boolean m49Resdit;
	private String msgText;
	private String msgDetails;
	private boolean isMsgEventLocationEnabled;
	private boolean isPartialResdit;
	private String partyName;
	private String carrierCode;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private String shipmentPrefix;
	private String masterDocumentNumber;
	private String reciever;
	/** 
	* For stamping in MALRDT, and to be displayed in Mail History screen
	*/
	private String senderIdentifier;
	private String recipientIdentifier;

	/** 
	* @param isMsgEventLocationEnabled the isMsgEventLocationEnabled to setSetter for isMsgEventLocationEnabled  Added by : A-8061 on 26-Jan-2020 Used for :
	*/
	public void setMsgEventLocationEnabled(boolean isMsgEventLocationEnabled) {
		this.isMsgEventLocationEnabled = isMsgEventLocationEnabled;
	}

	/** 
	* @param isPartialResdit the isPartialResdit to setSetter for isPartialResdit  Added by : A-8061 on 26-Jan-2020 Used for :
	*/
	public void setPartialResdit(boolean isPartialResdit) {
		this.isPartialResdit = isPartialResdit;
	}
}
