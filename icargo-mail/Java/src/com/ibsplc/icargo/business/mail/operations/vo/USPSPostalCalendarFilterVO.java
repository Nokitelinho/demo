package com.ibsplc.icargo.business.mail.operations.vo;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
public class USPSPostalCalendarFilterVO extends AbstractVO {
	private String filterCalender;
	private LocalDate calValidFrom;
	private LocalDate calValidTo;
	private String calPacode;
	private String companyCode;
	private String listFlag;  //Added by A-8149 for ICRD-240246
	
	private LocalDate invoiceDate;
	private String calendarType;
	public String getFilterCalender() {
		return filterCalender;
	}
	public void setFilterCalender(String filterCalender) {
		this.filterCalender = filterCalender;
	}
	public LocalDate getCalValidFrom() {
		return calValidFrom;
	}
	public void setCalValidFrom(LocalDate calValidFrom) {
		this.calValidFrom = calValidFrom;
	}
	public LocalDate getCalValidTo() {
		return calValidTo;
	}
	public void setCalValidTo(LocalDate calValidTo) {
		this.calValidTo = calValidTo;
	}
	public String getCalPacode() {
		return calPacode;
	}
	public void setCalPacode(String calPacode) {
		this.calPacode = calPacode;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getListFlag() {
		return listFlag;
	}
	public void setListFlag(String listFlag) {
		this.listFlag = listFlag;
	}
	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getCalendarType() {
		return calendarType;
	}
	public void setCalendarType(String calendarType) {
		this.calendarType = calendarType;
	}
	
}