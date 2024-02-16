package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.business.msgbroker.config.handling.vo.MessageAddressFilterVO;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class SecurityScreeningValidationFilterVO extends AbstractVO {
	private String companyCode;
	private String originAirport;
	private String originCountry;
	private String destinationAirport;
	private String destinationCountry;
	private String transactionAirport;
	private String transactionCountry;
	private String securityStatusCode;
	private String applicableTransaction;
	private String flightType;
	private String originAirportCountryGroup;
	private String destAirportCountryGroup;
	private String txnAirportCountryGroup;
	private String txnAirportGroup;
	private String securityStatusCodeGroup;
	private String mailbagId;
	private boolean appRegValReq;
	private String appRegOriginCountryGroup;
	private String appRegDestCountryGroup;
	private String subClass;
	private String appRegFlg;
	private String appRegDestArp;
	private boolean transferInTxn;
	private boolean securityValNotReq;
	private String securityValNotRequired;
	private String appRegTransistCountryGroup;
	private String appRegTransistCountry;
	private String transistAirport;
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
	public String getOriginCountry() {
		return originCountry;
	}
	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}
	public String getDestinationCountry() {
		return destinationCountry;
	}
	public void setDestinationCountry(String destinationCountry) {
		this.destinationCountry = destinationCountry;
	}
	public String getTransactionCountry() {
		return transactionCountry;
	}
	public void setTransactionCountry(String transactionCountry) {
		this.transactionCountry = transactionCountry;
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
	public String getOriginAirportCountryGroup() {
		return originAirportCountryGroup;
	}
	public void setOriginAirportCountryGroup(String originAirportCountryGroup) {
		this.originAirportCountryGroup = originAirportCountryGroup;
	}
	public String getDestAirportCountryGroup() {
		return destAirportCountryGroup;
	}
	public void setDestAirportCountryGroup(String destAirportCountryGroup) {
		this.destAirportCountryGroup = destAirportCountryGroup;
	}
	public String getTxnAirportCountryGroup() {
		return txnAirportCountryGroup;
	}
	public void setTxnAirportCountryGroup(String txnAirportCountryGroup) {
		this.txnAirportCountryGroup = txnAirportCountryGroup;
	}
	public String getTxnAirportGroup() {
		return txnAirportGroup;
	}
	public void setTxnAirportGroup(String txnAirportGroup) {
		this.txnAirportGroup = txnAirportGroup;
	}
	public String getSecurityStatusCodeGroup() {
		return securityStatusCodeGroup;
	}
	public void setSecurityStatusCodeGroup(String securityStatusCodeGroup) {
		this.securityStatusCodeGroup = securityStatusCodeGroup;
	}
	public String getMailbagId() {
		return mailbagId;
	}
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}
	public boolean isAppRegValReq() {
		return appRegValReq;
	}
	public void setAppRegValReq(boolean appRegValReq) {
		this.appRegValReq = appRegValReq;
	}
	public String getAppRegOriginCountryGroup() {
		return appRegOriginCountryGroup;
	}
	public void setAppRegOriginCountryGroup(String appRegOriginCountryGroup) {
		this.appRegOriginCountryGroup = appRegOriginCountryGroup;
	}
	public String getAppRegDestCountryGroup() {
		return appRegDestCountryGroup;
	}
	public void setAppRegDestCountryGroup(String appRegDestCountryGroup) {
		this.appRegDestCountryGroup = appRegDestCountryGroup;
	}
	public String getSubClass() {
		return subClass;
	}
	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}
	public String getAppRegFlg() {
		return appRegFlg;
	}
	public void setAppRegFlg(String appRegFlg) {
		this.appRegFlg = appRegFlg;
	}
	public boolean isTransferInTxn() {
		return transferInTxn;
	}
	public void setTransferInTxn(boolean transferInTxn) {
		this.transferInTxn = transferInTxn;
	}
	public String getAppRegDestArp() {
		return appRegDestArp;
	}
	public void setAppRegDestArp(String appRegDestArp) {
		this.appRegDestArp = appRegDestArp;
	}
	public boolean isSecurityValNotReq() {
		return securityValNotReq;
	}
	public void setSecurityValNotReq(boolean securityValNotReq) {
		this.securityValNotReq = securityValNotReq;
	}
	public String getAppRegTransistCountryGroup() {
		return appRegTransistCountryGroup;
	}
	public void setAppRegTransistCountryGroup(String appRegTransistCountryGroup) {
		this.appRegTransistCountryGroup = appRegTransistCountryGroup;
	}
	public String getAppRegTransistCountry() {
		return appRegTransistCountry;
	}
	public void setAppRegTransistCountry(String appRegTransistCountry) {
		this.appRegTransistCountry = appRegTransistCountry;
	}
	public String getTransistAirport() {
		return transistAirport;
	}
	public void setTransistAirport(String transistAirport) {
		this.transistAirport = transistAirport;
	}
	public String getSecurityValNotRequired() {
		return securityValNotRequired;
	}
	public void setSecurityValNotRequired(String securityValNotRequired) {
		this.securityValNotRequired = securityValNotRequired;
	}
	@Override
	public int hashCode() {
		return SecurityScreeningValidationFilterVO.this.toString().hashCode();
	}
	
	/* (non-Javadoc)
	 * @see com.ibsplc.xibase.server.framework.vo.AbstractVO#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(150);
		sbul.append(this.companyCode).append('-').append(this.originAirport)
			.append('-').append(this.originCountry).append('-')
			.append(this.destinationAirport).append('-').append(this.destinationCountry)
			.append('-').append(this.transactionAirport).append('-')
			.append(this.transactionCountry).append('-')
			.append(this.securityStatusCode).append('-').append(this.applicableTransaction).append('-').append(this.flightType).append('-').append(this.originAirportCountryGroup)
			.append('-').append(this.destAirportCountryGroup).append('-').append(this.txnAirportCountryGroup).append('-').append(this.txnAirportGroup).append('-').append(this.securityStatusCodeGroup)
			.append('-').append(this.appRegOriginCountryGroup).append('-').append(this.appRegDestCountryGroup).append('-').append(this.subClass).append('-').append(this.appRegFlg)
			.append('-').append(this.appRegDestArp).append(this.appRegTransistCountryGroup).append(this.appRegTransistCountry).append(transistAirport).append(securityValNotRequired);
		return sbul.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			{
			return false;
			}
		if(obj instanceof SecurityScreeningValidationFilterVO){
			return obj.hashCode() == SecurityScreeningValidationFilterVO.this.hashCode();
		}else
			{
			return false;
			}
	}
	
	

}
