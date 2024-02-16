package com.ibsplc.icargo.presentation.web.model.mail.mra.common;

import java.util.Collection;

public class InvoicFilter {
	
	private String gpaCode;
	private String invoicId;
	private String fromDate;
	private String toDate;
	private String mailbagId;
	private String displayPage;
    private Collection<String>selectedProcessStatus;
    private Collection<String> selectedClaimRange;  //Added by A-7929
    private Collection<String> org;
    private Collection<String> dest;
    private Collection<String> mailSubClass;
	private int totalRecords;
	private int defaultPageSize;
	private String cmpcod;
	private String invoicfilterStatus;
	private String processStatusFilter;
	private String fromScreen;

	public Collection<String> getOrg() {
		return org;
	}
	public void setOrg(Collection<String> org) {
		this.org = org;
	}
	public Collection<String> getDest() {
		return dest;
	}
	public void setDest(Collection<String> dest) {
		this.dest = dest;
	}
	public Collection<String> getMailSubClass() {
		return mailSubClass;
	}
	public void setMailSubClass(Collection<String> mailSubClass) {
		this.mailSubClass = mailSubClass;
	}

	
	public String getGpaCode() {
		return gpaCode;
	}
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	public String getInvoicId() {
		return invoicId;
	}
	public void setInvoicId(String invoicId) {
		this.invoicId = invoicId;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getMailbagId() {
		return mailbagId;
	}
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}
	
	public String getDisplayPage() {
		return displayPage;
	}

	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	public Collection<String> getSelectedProcessStatus() {
		return selectedProcessStatus;
	}
	public void setSelectedProcessStatus(Collection<String> selectedProcessStatus) {
		this.selectedProcessStatus = selectedProcessStatus;
	}

	public int getDefaultPageSize() {
		return defaultPageSize;
	}
	public void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public String getCmpcod() {
		return cmpcod;
	}
	public void setCmpcod(String cmpcod) {
		this.cmpcod = cmpcod;
	}
	
	public Collection<String> getSelectedClaimRange() {
		return selectedClaimRange;
	}
	public void setSelectedClaimRange(Collection<String> selectedClaimRange) {
		this.selectedClaimRange = selectedClaimRange;
	}
	public String getInvoicfilterStatus() {
		return invoicfilterStatus;
	}
	public void setInvoicfilterStatus(String invoicfilterStatus) {
		this.invoicfilterStatus = invoicfilterStatus;
	}
	public String getProcessStatusFilter() {
		return processStatusFilter;
	}
	public void setProcessStatusFilter(String processStatusFilter) {
		this.processStatusFilter = processStatusFilter;
	}
	public String getFromScreen() {
		return fromScreen;
	}
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

}
