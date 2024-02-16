/**
 * 
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-8149
 *
 */
public class MailServiceStandardFilterVO extends AbstractVO{

	String companyCode; 
	String gpaCode;
	String orginCode; 
	String destCode; 
	String servLevel;
	String scanWaived;
	String serviceStandard ;
	String contractId;
	private int pageNumber;
	private int totalRecords;
	private int defaultPageSize;
	
	
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
	public String getOrginCode() {
		return orginCode;
	}
	public void setOrginCode(String orginCode) {
		this.orginCode = orginCode;
	}
	public String getDestCode() {
		return destCode;
	}
	public void setDestCode(String destCode) {
		this.destCode = destCode;
	}
	public String getServLevel() {
		return servLevel;
	}
	public void setServLevel(String servLevel) {
		this.servLevel = servLevel;
	}
	public String getScanWaived() {
		return scanWaived;
	}
	public void setScanWaived(String scanWaived) {
		this.scanWaived = scanWaived;
	}
	public String getServiceStandard() {
		return serviceStandard;
	}
	public void setServiceStandard(String serviceStandard) {
		this.serviceStandard = serviceStandard;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public int getDefaultPageSize() {
		return defaultPageSize;
	}
	public void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}
	
}
