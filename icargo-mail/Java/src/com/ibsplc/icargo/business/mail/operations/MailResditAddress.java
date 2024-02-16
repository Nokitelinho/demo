/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.MailRdtMaster.java
 *
 *	Created by	:	A-6991
 *	Created on	:	17-Jul-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;


import java.util.Calendar;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.operations.vo.MailResditAddressVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;



/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.MailRdtMaster.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-6991	:	17-Jul-2018	:	Draft
 */
@Entity
@Table(name = "MALRDTMSGADDDTL")
public class MailResditAddress {
	
	 private MailResditAddressPK mailResditAddressPK;
	 private String messageType;
	 private String version;
	 private String interfaceSystem;
	 private String mode;
	 private String address;
	 private String envelopeCode;
	 private String envelopeAddress;
	 private String participantInterfaceSystem;
	 private String participantName;
	 private String participantType;
	 private String airportCode;
	 private String countryCode;
	 private String resditVersion;
	 private Calendar lastUpdateTime;
	 private String lastUpdateUser;
	
	 
	@Column(name = "RDTVER")
	public String getResditVersion() {
		return resditVersion;
	}
	public void setResditVersion(String resditVersion) {
		this.resditVersion = resditVersion;
	}
	@Column(name = "MSGTYP")
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	@Column(name = "MSGVER")
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	@Column(name = "INFSYS")
	public String getInterfaceSystem() {
		return interfaceSystem;
	}
	public void setInterfaceSystem(String interfaceSystem) {
		this.interfaceSystem = interfaceSystem;
	}
	
	@Column(name = "MSGMOD")
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	@Column(name = "MODADD")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name = "ENVCOD")
	public String getEnvelopeCode() {
		return envelopeCode;
	}
	public void setEnvelopeCode(String envelopeCode) {
		this.envelopeCode = envelopeCode;
	}
	
	@Column(name = "ENVADD")
	public String getEnvelopeAddress() {
		return envelopeAddress;
	}
	public void setEnvelopeAddress(String envelopeAddress) {
		this.envelopeAddress = envelopeAddress;
	}
	
	@Column(name = "PTYINFSYS")
	public String getParticipantInterfaceSystem() {
		return participantInterfaceSystem;
	}
	public void setParticipantInterfaceSystem(String participantInterfaceSystem) {
		this.participantInterfaceSystem = participantInterfaceSystem;
	}
	
	@Column(name = "PTYNAM")
	public String getParticipantName() {
		return participantName;
	}
	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}
	
	@Column(name = "PTYTYP")
	public String getParticipantType() {
		return participantType;
	}
	public void setParticipantType(String participantType) {
		this.participantType = participantType;
	}
	
	@Column(name = "ARPCOD")
	public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	
	@Column(name = "CNTCOD")
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	
	@Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	@Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	
	
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "messageAddressSequenceNumber", column = @Column(name = "MSGADDSEQNUM"))
			 })
	public MailResditAddressPK getMailResditAddressPK() {
		return mailResditAddressPK;
	}
	public void setMailResditAddressPK(MailResditAddressPK mailResditAddressPK) {
		this.mailResditAddressPK = mailResditAddressPK;
	}
	
    public MailResditAddress() {
		
	}
	
	public MailResditAddress(MailResditAddressVO mailResditAddressVO) throws SystemException {
		
		populatePK(mailResditAddressVO);
		populateAttributes(mailResditAddressVO);
		
		
	}
	
	private void populatePK(MailResditAddressVO mailResditAddressVO) {
		MailResditAddressPK pk=new MailResditAddressPK();
		pk.setCompanyCode(mailResditAddressVO.getCompanyCode());
		this.mailResditAddressPK = pk;
		}
	private void populateAttributes(MailResditAddressVO mailResditAddressVO) {
		setVersion(mailResditAddressVO.getVersion());
		setInterfaceSystem(mailResditAddressVO.getInterfaceSystem());
		setMode(mailResditAddressVO.getMode());
		setAddress(mailResditAddressVO.getAddress());
		setEnvelopeCode(mailResditAddressVO.getEnvelopeCode());
		setEnvelopeAddress(mailResditAddressVO.getEnvelopeAddress());
		setParticipantInterfaceSystem(mailResditAddressVO.getParticipantInterfaceSystem());
		setParticipantName(mailResditAddressVO.getParticipantName());
		setParticipantType(mailResditAddressVO.getParticipantType());
		setAirportCode(mailResditAddressVO.getAirportCode());
		setCountryCode(mailResditAddressVO.getCountryCode());
		setMessageType(mailResditAddressVO.getMessageType());
		setResditVersion(mailResditAddressVO.getResditVersion());
		
		
	}
	
	
	
	
	
}
	
	

