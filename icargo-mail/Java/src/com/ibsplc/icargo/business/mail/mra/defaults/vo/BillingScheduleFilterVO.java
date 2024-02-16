package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-9498
 *
 */
public class BillingScheduleFilterVO extends AbstractVO {
	private String companyCode;
	private String periodNumber;
	private int serialNumber;
	private int totalRecordCount;
	private String displayPage;
	private String pageSize;
	private String billingPeriod;
	private String billingType;
	private int tagId;
	private int year;
	private LocalDate billingPeriodFromDate;
	private LocalDate billingPeriodToDate;
	private String source;
	
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getPeriodNumber() {
		return periodNumber;
	}

	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getBillingPeriod() {
		return billingPeriod;
	}

	public void setBillingPeriod(String billingPeriod) {
		this.billingPeriod = billingPeriod;
	}

	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	public String getDisplayPage() {
		return displayPage;
	}

	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public String getBillingType() {
		return billingType;
	}

	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	

	/**
	 * 	Getter for billingPeriodFromDate 
	 *	Added by : A-8061 on 06-May-2021
	 * 	Used for :
	 */
	public LocalDate getBillingPeriodFromDate() {
		return billingPeriodFromDate;
	}

	/**
	 *  @param billingPeriodFromDate the billingPeriodFromDate to set
	 * 	Setter for billingPeriodFromDate 
	 *	Added by : A-8061 on 06-May-2021
	 * 	Used for :
	 */
	public void setBillingPeriodFromDate(LocalDate billingPeriodFromDate) {
		this.billingPeriodFromDate = billingPeriodFromDate;
	}

	/**
	 * 	Getter for billingPeriodToDate 
	 *	Added by : A-8061 on 06-May-2021
	 * 	Used for :
	 */
	public LocalDate getBillingPeriodToDate() {
		return billingPeriodToDate;
	}

	/**
	 *  @param billingPeriodToDate the billingPeriodToDate to set
	 * 	Setter for billingPeriodToDate 
	 *	Added by : A-8061 on 06-May-2021
	 * 	Used for :
	 */
	public void setBillingPeriodToDate(LocalDate billingPeriodToDate) {
		this.billingPeriodToDate = billingPeriodToDate;
	}

	/**
	 * 	Getter for source 
	 *	Added by : A-8061 on 10-Jun-2021
	 * 	Used for :
	 */
	public String getSource() {
		return source;
	}

	/**
	 *  @param source the source to set
	 * 	Setter for source 
	 *	Added by : A-8061 on 10-Jun-2021
	 * 	Used for :
	 */
	public void setSource(String source) {
		this.source = source;
	}

}
