package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class SecurityScreeningValidationVO extends AbstractVO{
	private String companyCode;
	private String validationType;
	private String errorType;
	private String originAirport;
	private String orgArpExcFlg;
	private String destinationAirport;
	private String destArpExcFlg;
	private String transactionAirport;
	private String trnArpExcFlg;
	private String securityStatusCode;
	private String securityStatusCodeExcFlg;
	private String applicableTransaction;
	private String flightType;
	private String flightTypeExcFlg;
	private String mailbagID;
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getOriginAirport() {
		return originAirport;
	}
	public void setOriginAirport(String originAirport) {
		this.originAirport = originAirport;
	}
	public String getDestinationAirport() {
		return destinationAirport;
	}
	public void setDestinationAirport(String destinationAirport) {
		this.destinationAirport = destinationAirport;
	}
	public String getTransactionAirport() {
		return transactionAirport;
	}
	public void setTransactionAirport(String transactionAirport) {
		this.transactionAirport = transactionAirport;
	}
	public String getSecurityStatusCode() {
		return securityStatusCode;
	}
	public void setSecurityStatusCode(String securityStatusCode) {
		this.securityStatusCode = securityStatusCode;
	}
	public String getApplicableTransaction() {
		return applicableTransaction;
	}
	public void setApplicableTransaction(String applicableTransaction) {
		this.applicableTransaction = applicableTransaction;
	}
	public String getFlightType() {
		return flightType;
	}
	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}
	public String getValidationType() {
		return validationType;
	}
	public void setValidationType(String validationType) {
		this.validationType = validationType;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public String getOrgArpExcFlg() {
		return orgArpExcFlg;
	}
	public void setOrgArpExcFlg(String orgArpExcFlg) {
		this.orgArpExcFlg = orgArpExcFlg;
	}
	public String getDestArpExcFlg() {
		return destArpExcFlg;
	}
	public void setDestArpExcFlg(String destArpExcFlg) {
		this.destArpExcFlg = destArpExcFlg;
	}
	public String getTrnArpExcFlg() {
		return trnArpExcFlg;
	}
	public void setTrnArpExcFlg(String trnArpExcFlg) {
		this.trnArpExcFlg = trnArpExcFlg;
	}
	public String getSecurityStatusCodeExcFlg() {
		return securityStatusCodeExcFlg;
	}
	public void setSecurityStatusCodeExcFlg(String securityStatusCodeExcFlg) {
		this.securityStatusCodeExcFlg = securityStatusCodeExcFlg;
	}
	public String getFlightTypeExcFlg() {
		return flightTypeExcFlg;
	}
	public void setFlightTypeExcFlg(String flightTypeExcFlg) {
		this.flightTypeExcFlg = flightTypeExcFlg;
	}
	public String getMailbagID() {
		return mailbagID;
	}
	public void setMailbagID(String mailbagID) {
		this.mailbagID = mailbagID;
	}
	

}
