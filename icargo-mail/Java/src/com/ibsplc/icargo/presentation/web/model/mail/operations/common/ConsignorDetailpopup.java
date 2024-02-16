/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailbagFilter.java Created on	:	12-Apr-2022
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

/**
 * Java file :
 * com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailbagFilter.java
 * Version : Name : Date : Updation
 * ---------------------------------------------------
 *  0.1 : A-10383 :  12-Apr-2022: Draft
 */
public class ConsignorDetailpopup{
	
	String agenttype;
	String agentId;
	String isoCountryCode;
	String expiryDate;
	String mailbagid;
	private String editStatus;
	private String mailseqnum;
	private String sernum;
	
	public String getMailseqnum() {
		return mailseqnum;
	}
	public void setMailseqnum(String mailseqnum) {
		this.mailseqnum = mailseqnum;
	}
	public String getSernum() {
		return sernum;
	}
	public void setSernum(String sernum) {
		this.sernum = sernum;
	}
	public String getEditStatus() {
		return editStatus;
	}
	public void setEditStatus(String editStatus) {
		this.editStatus = editStatus;
	}
	public String getMailbagid() {
		return mailbagid;
	}
	public void setMailbagid(String mailbagid) {
		this.mailbagid = mailbagid;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getAgenttype() {
		return agenttype;
	}
	public void setAgenttype(String agenttype) {
		this.agenttype = agenttype;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getIsoCountryCode() {
		return isoCountryCode;
	}
	public void setIsoCountryCode(String isoCountryCode) {
		this.isoCountryCode = isoCountryCode;
	}
	
	 
}