/*
 * DespatchVO.java Created on Mar 18, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import java.util.Collection;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3229
 *
 */
public class DespatchVO extends AbstractVO{	

	private String despatchNumber;
	private String origin;
	private String destination;
	private String category;
	private String subClass;
	private String gpaCode;
	private String gpaName;	
	private String billingCurrency;
	private Collection<MailFlownVO> mailFlowndetails;
   // private Collection<MailGPABillingVO> mailGPABillingdetails;
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return the despatchNumber
	 */
	public String getDespatchNumber() {
		return despatchNumber;
	}
	/**
	 * @param despatchNumber the despatchNumber to set
	 */
	public void setDespatchNumber(String despatchNumber) {
		this.despatchNumber = despatchNumber;
	}
	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * @return the gpaCode
	 */
	public String getGpaCode() {
		return gpaCode;
	}
	/**
	 * @param gpaCode the gpaCode to set
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	/**
	 * @return the gpaName
	 */
	public String getGpaName() {
		return gpaName;
	}
	/**
	 * @param gpaName the gpaName to set
	 */
	public void setGpaName(String gpaName) {
		this.gpaName = gpaName;
	}
	/**
	 * @return the mailFlowndetails
	 */
	public Collection<MailFlownVO> getMailFlowndetails() {
		return mailFlowndetails;
	}
	/**
	 * @param mailFlowndetails the mailFlowndetails to set
	 */
	public void setMailFlowndetails(Collection<MailFlownVO> mailFlowndetails) {
		this.mailFlowndetails = mailFlowndetails;
	}
	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}
	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}	
	/**
	 * @return the subClass
	 */
	public String getSubClass() {
		return subClass;
	}
	/**
	 * @param subClass the subClass to set
	 */
	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}
	/**
	 * @return the billingCurrency
	 */
	public String getBillingCurrency() {
		return billingCurrency;
	}
	/**
	 * @param billingCurrency the billingCurrency to set
	 */
	public void setBillingCurrency(String billingCurrency) {
		this.billingCurrency = billingCurrency;
	}
	/**
	 * @return the mailGPABillingdetails
	 *//*
	public Collection<MailGPABillingVO> getMailGPABillingdetails() {
		return mailGPABillingdetails;
	}
	*//**
	 * @param mailGPABillingdetails the mailGPABillingdetails to set
	 *//*
	public void setMailGPABillingdetails(
			Collection<MailGPABillingVO> mailGPABillingdetails) {
		this.mailGPABillingdetails = mailGPABillingdetails;
	}*/
}
