/*
 * CN51CN66VO.java Created on Jan 8, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling.vo;


import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;	 
import java.util.Collection;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1556
 *
 */
public class CN51CN66VO extends AbstractVO {

    //TODO Collection<CN51DetailsVO>
    private Page<CN51DetailsVO> cn51DetailsVOs;
    //TODO Collection<CN66DetailsVO>
    private Page<CN66DetailsVO> cn66DetailsVOs;
    
    private String airlineCode;
    private String emailToaddress;
    private String gpaCode;
    private String invoiceNumber;
//Added as part of ICRD-234283 by A-5526
    private boolean isRebillInvoice;
    private String companyCode;
    private String invoiceStatus;
    private Collection<CN66DetailsVO> cn66DetailsVOsColln;
    private Collection<CN51DetailsVO> cn51DetailsVOsColln;
    private String overrideRounding;//added by a-7871 for ICRD-214766
	/**
	 * @return the overrideRounding
	 */
	public String getOverrideRouding() {
		return overrideRounding;
	}
	/**
	 * @param overrideRounding the overrideRounding to set
	 */
	public void setOverrideRouding(String overrideRounding) {
		this.overrideRounding = overrideRounding;
	}
	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}
	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	/**
	 * @return Returns the cn51DetailsVOs.
	 */
	public Page<CN51DetailsVO> getCn51DetailsVOs() {
		return cn51DetailsVOs;
	}
	/**
	 * @param cn51DetailsVOs The cn51DetailsVOs to set.
	 */
	public void setCn51DetailsVOs(Page<CN51DetailsVO> cn51DetailsVOs) {
		this.cn51DetailsVOs = cn51DetailsVOs;
	}
	/**
	 * @return Returns the cn66DetailsVOs.
	 */
	public Page<CN66DetailsVO> getCn66DetailsVOs() {
		return cn66DetailsVOs;
	}
	/**
	 * @param cn66DetailsVOs The cn66DetailsVOs to set.
	 */
	public void setCn66DetailsVOs(Page<CN66DetailsVO> cn66DetailsVOs) {
		this.cn66DetailsVOs = cn66DetailsVOs;
	}
	/**
	 * 	Getter for emailToaddress 
	 *	Added by : A-4809 on 09-Jan-2014
	 * 	Used for : ICRD-42160
	 */
	public String getEmailToaddress() {
		return emailToaddress;
	}
	/**
	 *  @param emailToaddress the emailToaddress to set
	 * 	Setter for emailToaddress 
	 *	Added by : A-4809 on 09-Jan-2014
	 * 	Used for : ICRD-42160
	 */
	public void setEmailToaddress(String emailToaddress) {
		this.emailToaddress = emailToaddress;
	}
	/**
	 * 	Getter for cn66DetailsVOsColln 
	 *	Added by : A-4809 on 10-Feb-2014
	 * 	Used for :
	 */
	public Collection<CN66DetailsVO> getCn66DetailsVOsColln() {
		return cn66DetailsVOsColln;
	}
	/**
	 * 	Getter for cn51DetailsVOsColln 
	 *	Added by : A-4809 on 10-Feb-2014
	 * 	Used for :
	 */
	public Collection<CN51DetailsVO> getCn51DetailsVOsColln() {
		return cn51DetailsVOsColln;
	}
	/**
	 *  @param cn66DetailsVOsColln the cn66DetailsVOsColln to set
	 * 	Setter for cn66DetailsVOsColln 
	 *	Added by : A-4809 on 10-Feb-2014
	 * 	Used for :
	 */
	public void setCn66DetailsVOsColln(Collection<CN66DetailsVO> cn66DetailsVOsColln) {
		this.cn66DetailsVOsColln = cn66DetailsVOsColln;
	}
	/**
	 *  @param cn51DetailsVOsColln the cn51DetailsVOsColln to set
	 * 	Setter for cn51DetailsVOsColln 
	 *	Added by : A-4809 on 10-Feb-2014
	 * 	Used for :
	 */
	public void setCn51DetailsVOsColln(Collection<CN51DetailsVO> cn51DetailsVOsColln) {
		this.cn51DetailsVOsColln = cn51DetailsVOsColln;
	}
	/**
	 * 	Getter for gpaCode 
	 *	Added by : A-4809 on 13-Feb-2014
	 * 	Used for :
	 */
	public String getGpaCode() {
		return gpaCode;
	}
	/**
	 * 	Getter for invoiceNumber 
	 *	Added by : A-4809 on 13-Feb-2014
	 * 	Used for :
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	/**
	 *  @param gpaCode the gpaCode to set
	 * 	Setter for gpaCode 
	 *	Added by : A-4809 on 13-Feb-2014
	 * 	Used for :
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	/**
	 *  @param invoiceNumber the invoiceNumber to set
	 * 	Setter for invoiceNumber 
	 *	Added by : A-4809 on 13-Feb-2014
	 * 	Used for :
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	/**
	 * 	Getter for companyCode 
	 *	Added by : A-4809 on 13-Feb-2014
	 * 	Used for :
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 *  @param companyCode the companyCode to set
	 * 	Setter for companyCode 
	 *	Added by : A-4809 on 13-Feb-2014
	 * 	Used for :
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * 	Getter for invoiceStatus 
	 *	Added by : A-6991 on 13-Sep-2017
	 * 	Used for :
	 */
	public String getInvoiceStatus() {
		return invoiceStatus;
	}
	/**
	 *  @param invoiceStatus the invoiceStatus to set
	 * 	Setter for invoiceStatus 
	 *	Added by : A-6991 on 13-Sep-2017
	 * 	Used for :
	 */
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	/**
	 * Getter for Rebill invoice status
	 * 
	 * @return true if rebill invoice
	 */
	public boolean isRebillInvoice() {
		return isRebillInvoice;
	}

	/**
	 * Setter for rebill invoice status
	 * 
	 * @param isRebillInvoice
	 */
	public void setRebillInvoice(boolean isRebillInvoice) {
		this.isRebillInvoice = isRebillInvoice;
	}

}
