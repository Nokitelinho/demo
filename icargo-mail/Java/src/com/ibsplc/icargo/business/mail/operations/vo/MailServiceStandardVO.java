/**
 * 
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-8149
 *
 */
public class MailServiceStandardVO extends AbstractVO{

	private String gpaCode;
	private String companyCode;
	private String originCode;
	private String destinationCode;
	private String servicelevel;
	private String servicestandard;
	private String contractid;
	private String scanWaived;
	private String lastUpdateUser;
	private LocalDate lastUpdateTime;
	private String operationFlag; 
	
	
	public String getGpaCode() {
		return gpaCode;
	}
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getOperationFlag() {
		return operationFlag;
	}
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	public String getOriginCode() {
		return originCode;
	}
	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}
	public String getDestinationCode() {
		return destinationCode;
	}
	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}
	public String getServicelevel() {
		return servicelevel;
	}
	public void setServicelevel(String servicelevel) {
		this.servicelevel = servicelevel;
	}
	public String getServicestandard() {
		return servicestandard;
	}
	public void setServicestandard(String servicestandard) {
		this.servicestandard = servicestandard;
	}
	public String getContractid() {
		return contractid;
	}
	public void setContractid(String contractid) {
		this.contractid = contractid;
	}
	public String getScanWaived() {
		return scanWaived;
	}
	public void setScanWaived(String scanWaived) {
		this.scanWaived = scanWaived;
	}
	
	
	
	
	
}
