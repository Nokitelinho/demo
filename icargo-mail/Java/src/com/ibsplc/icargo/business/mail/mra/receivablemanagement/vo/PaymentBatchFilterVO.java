/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchFilterVO.java
 *
 *	Created by	:	A-4809
 *	Created on	:	11-Nov-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchFilterVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	11-Nov-2021	:	Draft
 */
public class PaymentBatchFilterVO extends AbstractVO{

	private String companyCode;
	private String paCode;
	private LocalDate batchFrom;
	private LocalDate batchTo;
	private String batchStatus;

	private int pageNumber;
	private int defaultPageSize;
	private int displayPage;
	private int totalRecordCount;
	private String batchId;
	private String fileName;

	private String source;

	private int settlementBatchSequenceNumber;

	private String fileType;

	private String processIdentifier;
	
	private LocalDate batchDate;

	/**
	 * 	Getter for companyCode
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 *  @param companyCode the companyCode to set
	 * 	Setter for companyCode
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * 	Getter for batchFrom
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public LocalDate getBatchFrom() {
		return batchFrom;
	}
	/**
	 *  @param batchFrom the batchFrom to set
	 * 	Setter for batchFrom
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public void setBatchFrom(LocalDate batchFrom) {
		this.batchFrom = batchFrom;
	}
	/**
	 * 	Getter for batchStatus
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public String getBatchStatus() {
		return batchStatus;
	}
	/**
	 *  @param batchStatus the batchStatus to set
	 * 	Setter for batchStatus
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}
	/**
	 * 	Getter for pageNumber
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public int getPageNumber() {
		return pageNumber;
	}
	/**
	 *  @param pageNumber the pageNumber to set
	 * 	Setter for pageNumber
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	/**
	 * 	Getter for defaultPageSize
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public int getDefaultPageSize() {
		return defaultPageSize;
	}
	/**
	 *  @param defaultPageSize the defaultPageSize to set
	 * 	Setter for defaultPageSize
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}
	/**
	 * 	Getter for displayPage
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public int getDisplayPage() {
		return displayPage;
	}
	/**
	 *  @param displayPage the displayPage to set
	 * 	Setter for displayPage
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public void setDisplayPage(int displayPage) {
		this.displayPage = displayPage;
	}
	/**
	 * 	Getter for totalRecordCount
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	/**
	 *  @param totalRecordCount the totalRecordCount to set
	 * 	Setter for totalRecordCount
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	/**
	 * 	Getter for paCode
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public String getPaCode() {
		return paCode;
	}
	/**
	 *  @param paCode the paCode to set
	 * 	Setter for paCode
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	/**
	 * 	Getter for batchTo
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public LocalDate getBatchTo() {
		return batchTo;
	}
	/**
	 *  @param batchTo the batchTo to set
	 * 	Setter for batchTo
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public void setBatchTo(LocalDate batchTo) {
		this.batchTo = batchTo;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public int getSettlementBatchSequenceNumber() {
		return settlementBatchSequenceNumber;
	}
	public void setSettlementBatchSequenceNumber(int settlementBatchSequenceNumber) {
		this.settlementBatchSequenceNumber = settlementBatchSequenceNumber;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getProcessIdentifier() {
		return processIdentifier;
	}
	public void setProcessIdentifier(String processIdentifier) {
		this.processIdentifier = processIdentifier;
	}
	
	public LocalDate getBatchDate() {
		return batchDate;
	}
	public void setBatchDate(LocalDate batchDate) {
		this.batchDate = batchDate;
	}



}
