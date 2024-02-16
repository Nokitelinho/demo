/*
 * InvoicFilterVO.java Created on Nov 20, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.gpareporting.vo;

import java.util.Collection;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-8464
 *
 */
public class InvoicFilterVO extends AbstractVO{
	
 //this is the filter needed for input for list query in sql	    
	private String gpaCode;
	private String invoicId;
	private String fromDate;
	private String toDate;
	private String mailbagId;
    private int pageNumber;
    private Collection<String> selectedProcessStatus;
    private Collection<String> selectedClaimRange; 
    private Collection<String> org;
    private Collection<String> dest;
    private Collection<String> mailSubClass;
	private int totalRecords;
	private int defaultPageSize;
	private String cmpcod;
	private String invoicfilterStatus;
	private String processStatusFilter;
	private String claimType;
	private String action;
	private String claimRefNum;
	private String resditRequired;
	private String triggerPoint;
	private String fromScreen;
	private String fileName;
	private String invoicRefId;
	private String claimFileName;

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

	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
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
	public String getInvoicfilterStatus() {
		return invoicfilterStatus;
	}
	public void setInvoicfilterStatus(String invoicfilterStatus) {
		this.invoicfilterStatus = invoicfilterStatus;
	}

	public Collection<String> getSelectedClaimRange() {
		return selectedClaimRange;
	}
	public void setSelectedClaimRange(Collection<String> selectedClaimRange) {
		this.selectedClaimRange = selectedClaimRange;
	}
	public String getProcessStatusFilter() {
		return processStatusFilter;
	}
	public void setProcessStatusFilter(String processStatusFilter) {
		this.processStatusFilter = processStatusFilter;
	}
	public String getClaimType() {
		return claimType;
	}
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getClaimRefNum() {
		return claimRefNum;
	}
	public void setClaimRefNum(String claimRefNum) {
		this.claimRefNum = claimRefNum;
	}
	public String getResditRequired() {
		return resditRequired;
	}
	public void setResditRequired(String resditRequired) {
		this.resditRequired = resditRequired;
	}
	/**
	 * 	Getter for triggerPoint 
	 *	Added by : A-8061 on 26-Jun-2019
	 * 	Used for :
	 */
	public String getTriggerPoint() {
		return triggerPoint;
	}
	/**
	 *  @param triggerPoint the triggerPoint to set
	 * 	Setter for triggerPoint 
	 *	Added by : A-8061 on 26-Jun-2019
	 * 	Used for :
	 */
	public void setTriggerPoint(String triggerPoint) {
		this.triggerPoint = triggerPoint;
	}
	public String getFromScreen() {
		return fromScreen;
	}
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}
	/**
	 * 	Getter for fileName 
	 *	Added by : A-5219 on 10-Jun-2020
	 * 	Used for :
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 *  @param fileName the fileName to set
	 * 	Setter for fileName 
	 *	Added by : A-5219 on 10-Jun-2020
	 * 	Used for :
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 	Getter for invoicRefId 
	 *	Added by : A-5219 on 10-Jun-2020
	 * 	Used for :
	 */
	public String getInvoicRefId() {
		return invoicRefId;
	}
	/**
	 *  @param invoicRefId the invoicRefId to set
	 * 	Setter for invoicRefId 
	 *	Added by : A-5219 on 10-Jun-2020
	 * 	Used for :
	 */
	public void setInvoicRefId(String invoicRefId) {
		this.invoicRefId = invoicRefId;
	}
	public String getClaimFileName() {
		return claimFileName;
	}
	public void setClaimFileName(String claimFileName) {
		this.claimFileName = claimFileName;
	}
}
