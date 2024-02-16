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
public class ScreeningDetailpopup {
	
	private String location;
	private String method;
	private String securityStatus;
	private String source;
	private String date;
	private String time;
	private String status;
	private String mailbagid;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getSecurityStatus() {
		return securityStatus;
	}
	public void setSecurityStatus(String securityStatus) {
		this.securityStatus = securityStatus;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMailbagid() {
		return mailbagid;
	}
	public void setMailbagid(String mailbgid) {
		this.mailbagid = mailbgid;
	}
	
	
}