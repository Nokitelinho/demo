/**
 * 
 */
package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author A-8149
 *
 */
@Embeddable
public class MailServiceStandardPK implements Serializable{

	private String companyCode;
	private String gpaCode;
	private String originCode;
	private String destCode;
	private String serviceLevel;
	
	@Override
	public String toString() {
		return "MailServiceStandardPK [companyCode=" + companyCode + ", gpaCode="
				+ gpaCode + ", originCode=" + originCode + ", destCode="
				+ destCode + ", serviceLevel="
				+ serviceLevel + "]";
	}
	
	public int hashCode() {

		return new StringBuffer(companyCode).
			append(gpaCode).
			append(originCode).
			append(destCode).
			append(serviceLevel).
			toString().hashCode();
	}
	
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getGpaCode() {
		return gpaCode;
	}
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	public String getOriginCode() {
		return originCode;
	}
	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}
	public String getDestCode() {
		return destCode;
	}
	public void setDestCode(String destCode) {
		this.destCode = destCode;
	}
	public String getServiceLevel() {
		return serviceLevel;
	}
	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}
	
	
	
}
