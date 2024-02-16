package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class MailResditAddressVO extends AbstractVO{
	
	 private String messageType;
	 private String version;
	 private String interfaceSystem;
	 private String mode;
	 private String address;
	 private String envelopeCode;
	 private String envelopeAddress;
	 private String companyCode;
	 private String participantInterfaceSystem;
	 private String participantName;
	 private String participantType;
	 private String airportCode;
	 private String countryCode;
	 private long messageAddressSequenceNumber;
	 private String resditVersion;
	 private LocalDate lastUpdateTime;
	 private String lastUpdateUser;
    
	public String getResditVersion() {
		return resditVersion;
	}
	public void setResditVersion(String resditVersion) {
		this.resditVersion = resditVersion;
	}
	public long getMessageAddressSequenceNumber() {
		return messageAddressSequenceNumber;
	}
	public void setMessageAddressSequenceNumber(long messageAddressSequenceNumber) {
		this.messageAddressSequenceNumber = messageAddressSequenceNumber;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getInterfaceSystem() {
		return interfaceSystem;
	}
	public void setInterfaceSystem(String interfaceSystem) {
		this.interfaceSystem = interfaceSystem;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEnvelopeCode() {
		return envelopeCode;
	}
	public void setEnvelopeCode(String envelopeCode) {
		this.envelopeCode = envelopeCode;
	}
	public String getEnvelopeAddress() {
		return envelopeAddress;
	}
	public void setEnvelopeAddress(String envelopeAddress) {
		this.envelopeAddress = envelopeAddress;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getParticipantInterfaceSystem() {
		return participantInterfaceSystem;
	}
	public void setParticipantInterfaceSystem(String participantInterfaceSystem) {
		this.participantInterfaceSystem = participantInterfaceSystem;
	}
	public String getParticipantName() {
		return participantName;
	}
	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}
	public String getParticipantType() {
		return participantType;
	}
	public void setParticipantType(String participantType) {
		this.participantType = participantType;
	}
	public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	
	

}
