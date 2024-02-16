/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.ConsignmentMailPopUpVO.java 
 *
 *	Created by	:	A-7531
 *	Created on	:	17-Jul-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class ConsignmentMailPopUpVO extends AbstractVO{
	
	  private String orginOfficeOfExchange;
	  
	  private String destOfficeOfExchange;
	  
	  private String mailCategory;
	  
	  private String mailClassType;
	  
	  private String mailSubClass;
	  
	  private  String mailYear;
	  
	  private String mailDsn;
	  
	  private String highestNumberIndicator;
	  
	  private String registeredIndicator;
	  
	  private String rsnRangeFrom;
	  
	  private String rsnRangeTo;
	  
	  private int totalReceptacles;
	  
	  
	public String getOrginOfficeOfExchange() {
		return orginOfficeOfExchange;
	}
	public void setOrginOfficeOfExchange(String orginOfficeOfExchange) {
		this.orginOfficeOfExchange = orginOfficeOfExchange;
	}
	public String getDestOfficeOfExchange() {
		return destOfficeOfExchange;
	}
	public void setDestOfficeOfExchange(String destOfficeOfExchange) {
		this.destOfficeOfExchange = destOfficeOfExchange;
	}
	public String getMailCategory() {
		return mailCategory;
	}
	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}
	public String getMailClassType() {
		return mailClassType;
	}
	public void setMailClassType(String mailClassType) {
		this.mailClassType = mailClassType;
	}
	public String getMailSubClass() {
		return mailSubClass;
	}
	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}

	public String getMailDsn() {
		return mailDsn;
	}
	public void setMailDsn(String mailDsn) {
		this.mailDsn = mailDsn;
	}
	public String getHighestNumberIndicator() {
		return highestNumberIndicator;
	}
	public void setHighestNumberIndicator(String highestNumberIndicator) {
		this.highestNumberIndicator = highestNumberIndicator;
	}
	public String getRegisteredIndicator() {
		return registeredIndicator;
	}
	public void setRegisteredIndicator(String registeredIndicator) {
		this.registeredIndicator = registeredIndicator;
	}
	public String getRsnRangeFrom() {
		return rsnRangeFrom;
	}
	public void setRsnRangeFrom(String rsnRangeFrom) {
		this.rsnRangeFrom = rsnRangeFrom;
	}
	public String getRsnRangeTo() {
		return rsnRangeTo;
	}
	public void setRsnRangeTo(String rsnRangeTo) {
		this.rsnRangeTo = rsnRangeTo;
	}
	public int getTotalReceptacles() {
		return totalReceptacles;
	}
	public void setTotalReceptacles(int totalReceptacles) {
		this.totalReceptacles = totalReceptacles;
	}
	public String getMailYear() {
		return mailYear;
	}
	public void setMailYear(String mailYear) {
		this.mailYear = mailYear;
	}

}
