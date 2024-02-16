/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.MRABatchUploadedVO.java
 *
 *	Created by	:	A-5219
 *	Created on	:	12-Nov-2021
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
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.MRABatchUploadedVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5219	:	12-Nov-2021	:	Draft
 */
public class MRABatchUploadedVO extends AbstractVO {
	
	private String companyCode;
	
	private String fileName;
	
	private long serialNumber;
	
	private String mailIdr;
	
	private LocalDate mailDate;
	
	private String consignmentDocNum;
	
	private LocalDate payDate;
	
	private double payAmount;
	
	private String currencyCode;
	
	private String paCode;
	
	private String errorCode;
	
	private boolean errorsExist;

	/**
	 * 	Getter for companyCode 
	 *	Added by : A-5219 on 12-Nov-2021
	 * 	Used for :
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 *  @param companyCode the companyCode to set
	 * 	Setter for companyCode 
	 *	Added by : A-5219 on 12-Nov-2021
	 * 	Used for :
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * 	Getter for fileName 
	 *	Added by : A-5219 on 12-Nov-2021
	 * 	Used for :
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 *  @param fileName the fileName to set
	 * 	Setter for fileName 
	 *	Added by : A-5219 on 12-Nov-2021
	 * 	Used for :
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 	Getter for serialNumber 
	 *	Added by : A-5219 on 12-Nov-2021
	 * 	Used for :
	 */
	public long getSerialNumber() {
		return serialNumber;
	}

	/**
	 *  @param serialNumber the serialNumber to set
	 * 	Setter for serialNumber 
	 *	Added by : A-5219 on 12-Nov-2021
	 * 	Used for :
	 */
	public void setSerialNumber(long serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * 	Getter for mailIdr 
	 *	Added by : A-5219 on 12-Nov-2021
	 * 	Used for :
	 */
	public String getMailIdr() {
		return mailIdr;
	}

	/**
	 *  @param mailIdr the mailIdr to set
	 * 	Setter for mailIdr 
	 *	Added by : A-5219 on 12-Nov-2021
	 * 	Used for :
	 */
	public void setMailIdr(String mailIdr) {
		this.mailIdr = mailIdr;
	}

	/**
	 * 	Getter for mailDate 
	 *	Added by : A-5219 on 12-Nov-2021
	 * 	Used for :
	 */
	public LocalDate getMailDate() {
		return mailDate;
	}

	/**
	 *  @param mailDate the mailDate to set
	 * 	Setter for mailDate 
	 *	Added by : A-5219 on 12-Nov-2021
	 * 	Used for :
	 */
	public void setMailDate(LocalDate mailDate) {
		this.mailDate = mailDate;
	}

	/**
	 * 	Getter for consignmentDocNum 
	 *	Added by : A-5219 on 12-Nov-2021
	 * 	Used for :
	 */
	public String getConsignmentDocNum() {
		return consignmentDocNum;
	}

	/**
	 *  @param consignmentDocNum the consignmentDocNum to set
	 * 	Setter for consignmentDocNum 
	 *	Added by : A-5219 on 12-Nov-2021
	 * 	Used for :
	 */
	public void setConsignmentDocNum(String consignmentDocNum) {
		this.consignmentDocNum = consignmentDocNum;
	}

	/**
	 * 	Getter for payDate 
	 *	Added by : A-5219 on 12-Nov-2021
	 * 	Used for :
	 */
	public LocalDate getPayDate() {
		return payDate;
	}

	/**
	 *  @param payDate the payDate to set
	 * 	Setter for payDate 
	 *	Added by : A-5219 on 12-Nov-2021
	 * 	Used for :
	 */
	public void setPayDate(LocalDate payDate) {
		this.payDate = payDate;
	}

	/**
	 * 	Getter for payAmount 
	 *	Added by : A-5219 on 12-Nov-2021
	 * 	Used for :
	 */
	public double getPayAmount() {
		return payAmount;
	}

	/**
	 *  @param payAmount the payAmount to set
	 * 	Setter for payAmount 
	 *	Added by : A-5219 on 12-Nov-2021
	 * 	Used for :
	 */
	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}

	/**
	 * 	Getter for currencyCode 
	 *	Added by : A-5219 on 12-Nov-2021
	 * 	Used for :
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 *  @param currencyCode the currencyCode to set
	 * 	Setter for currencyCode 
	 *	Added by : A-5219 on 12-Nov-2021
	 * 	Used for :
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * 	Getter for paCode 
	 *	Added by : A-5219 on 15-Nov-2021
	 * 	Used for :
	 */
	public String getPaCode() {
		return paCode;
	}

	/**
	 *  @param paCode the paCode to set
	 * 	Setter for paCode 
	 *	Added by : A-5219 on 15-Nov-2021
	 * 	Used for :
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public boolean isErrorsExist() {
		return errorsExist;
	}

	public void setErrorsExist(boolean errorsExist) {
		this.errorsExist = errorsExist;
	}

}
