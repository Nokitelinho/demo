/**
 *	Java file	: 	com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.DespatchDetailsFilterVO
 *
 *	Created by	:	A-10543
 *	Created on	:	12-Sep-2023
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * Java file :
 * com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.DespatchDetailsFilterVO.java
 * Version : Name : Date : Updation
 * --------------------------------------------------- 0.1 : A-10543 :
 * 12-Sep-2023 : Draft
 */
public class DespatchDetailsFilterVO extends AbstractVO {

	private String companyCode;
	private String fromDate;
	private String gpoCode;
	private String gpoName;
	private String toDate;
	private String despatchNumber;
	private String receptacleID;
	private String originIMPC;
	private String destinationIMPC;
	private String mailClass;
	private String mailCategory;
	private boolean mailBagOperationFlag;

	public boolean isMailBagOperationFlag() {
		return mailBagOperationFlag;
	}

	public void setMailBagOperationFlag(boolean mailBagOperationFlag) {
		this.mailBagOperationFlag = mailBagOperationFlag;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getGpoCode() {
		return gpoCode;
	}

	public void setGpoCode(String gpoCode) {
		this.gpoCode = gpoCode;
	}

	public String getGpoName() {
		return gpoName;
	}

	public void setGpoName(String gpoName) {
		this.gpoName = gpoName;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getDespatchNumber() {
		return despatchNumber;
	}

	public void setDespatchNumber(String despatchNumber) {
		this.despatchNumber = despatchNumber;
	}

	public String getReceptacleID() {
		return receptacleID;
	}

	public void setReceptacleID(String receptacleID) {
		this.receptacleID = receptacleID;
	}

	public String getOriginIMPC() {
		return originIMPC;
	}

	public void setOriginIMPC(String originIMPC) {
		this.originIMPC = originIMPC;
	}

	public String getDestinationIMPC() {
		return destinationIMPC;
	}

	public void setDestinationIMPC(String destinationIMPC) {
		this.destinationIMPC = destinationIMPC;
	}

	public String getMailClass() {
		return mailClass;
	}

	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}

	public String getMailCategory() {
		return mailCategory;
	}

	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}

}
