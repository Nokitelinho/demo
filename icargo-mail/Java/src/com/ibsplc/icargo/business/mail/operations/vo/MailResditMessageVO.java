
package com.ibsplc.icargo.business.mail.operations.vo;


import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;


/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.MailResditMessageVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8061	:	28-Jun-2019	:	Draft
 */
public class MailResditMessageVO extends AbstractVO {

	private String companyCode;
	private String msgDetails;
	private String msgText;
	private LocalDate eventDate;
	private String poaCode;
	private String msgVersionNumber;
	private String carditExist;
	private String messageIdentifier;
	private String messageType;
	private String eventCode;
	private String eventPort;
	private long msgSequenceNumber;
	
	
	
	public String getPoaCode() {
		return poaCode;
	}
	
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}
	
	public String getCarditExist() {
		return carditExist;
	}

	public void setCarditExist(String carditExist) {
		this.carditExist = carditExist;
	}
	
	public String getMessageIdentifier() {
		return messageIdentifier;
	}
	
	public void setMessageIdentifier(String messageIdentifier) {
		this.messageIdentifier = messageIdentifier;
	}

	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public LocalDate getEventDate() {
		return eventDate;
	}
	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}
	public String getMsgText() {
		return msgText;
	}
	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}
	public String getMsgDetails() {
		return msgDetails;
	}
	public void setMsgDetails(String msgDetails) {
		this.msgDetails = msgDetails;
	}

	public String getMsgVersionNumber() {
		return msgVersionNumber;
	}
	public void setMsgVersionNumber(String msgVersionNumber) {
		this.msgVersionNumber = msgVersionNumber;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	public String getEventCode() {
		return eventCode;
	}
	
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	/**
	 * 	Getter for eventPort 
	 *	Added by : A-8061 on 28-Jun-2019
	 * 	Used for :
	 */
	public String getEventPort() {
		return eventPort;
	}

	/**
	 *  @param eventPort the eventPort to set
	 * 	Setter for eventPort 
	 *	Added by : A-8061 on 28-Jun-2019
	 * 	Used for :
	 */
	public void setEventPort(String eventPort) {
		this.eventPort = eventPort;
	}
	public long getMsgSequenceNumber() {
		return msgSequenceNumber;
	}
	public void setMsgSequenceNumber(long msgSequenceNumber) {
		this.msgSequenceNumber = msgSequenceNumber;
	}
}
