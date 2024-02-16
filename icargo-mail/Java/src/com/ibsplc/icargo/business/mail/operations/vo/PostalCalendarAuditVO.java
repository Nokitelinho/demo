/**
 * 
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.PostalCalendarAuditVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5219	:	13-Aug-2020	:	Draft
 */
public class PostalCalendarAuditVO  extends AuditVO {
	
	public static final String AUDIT_MODULENAME = "mail";

	/** submodule name. */
    public static final String AUDIT_SUBMODULENAME = "operations";

    /** Entity name. */
    public static final String AUDIT_ENTITY = "mail.operations.postalcalendar";
    
    public static final String POSTAL_CALENDAR_CREATED ="MALCALMSTCRT";
    
    public static final String POSTAL_CALENDAR_UPDATED ="MALCALMSTUPD";
    
    public static final String POSTAL_CALENDAR_DELETED ="MALCALMSTDEL";
    
    private String postalCode;
    
    private String period;
    
    private LocalDate fromDate;
    
    private LocalDate toDate;
    
    
    public PostalCalendarAuditVO(String moduleName, String subModuleName,String entityName) {
		super(moduleName, subModuleName, entityName);

	}


	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}


	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}


	/**
	 * @return the period
	 */
	public String getPeriod() {
		return period;
	}


	/**
	 * @param period the period to set
	 */
	public void setPeriod(String period) {
		this.period = period;
	}


	/**
	 * @return the fromDate
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}


	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}


	/**
	 * @return the toDate
	 */
	public LocalDate getToDate() {
		return toDate;
	}


	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

}
