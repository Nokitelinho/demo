/*
 * DSNPopUpVO.java Created on AUG 28, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2391
 *
 */
public class DSNPopUpVO extends AbstractVO {
	
	private String companyCode;
	
	private String dsn;
	//for UpdBillToo
	private String gpaCode;
	
	private String blgBasis;
	
	private String dsnDate;
	
	private String csgdocnum;
	
	private int csgseqnum;
	
	private int serialNumber;
	
	private int billingDetailsCount;
	
	//for pagination
	private int absoluteIndex;

	private int pageNumber;
	//Added for ICRD 21653

	private String poaCode;
	//Added for ICRD-101113
	private String receptacleSerialNo;
	private String hni;
	private String regInd;
	
	private int totalRecordCount;
	private long mailSequenceNumber;//added by A-7371 for ICRD-234334
    //Added by A-8331
	private String billingStatus;
	//Added by A-7794 as part of ICRD-232299
	private String mailSource; 
	private String transferPA;
	private String transferAirline;


	/**
	 * @return Returns the serialNumber.
	 */
	public int getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param serialNumber The serialNumber to set.
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * @return Returns the csgdocnum.
	 */
	public String getCsgdocnum() {
		return csgdocnum;
	}

	/**
	 * @param csgdocnum The csgdocnum to set.
	 */
	public void setCsgdocnum(String csgdocnum) {
		this.csgdocnum = csgdocnum;
	}

	/**
	 * @return Returns the csgseqnum.
	 */
	public int getCsgseqnum() {
		return csgseqnum;
	}

	/**
	 * @param csgseqnum The csgseqnum to set.
	 */
	public void setCsgseqnum(int csgseqnum) {
		this.csgseqnum = csgseqnum;
	}

	public DSNPopUpVO(){
		
	}

	/**
	 * @return Returns the dsn.
	 */
	public String getDsn() {
		return dsn;
	}



	/**
	 * @param dsn The dsn to set.
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}



	/**
	 * @return Returns the gpaCode.
	 */
	public String getGpaCode() {
		return gpaCode;
	}



	/**
	 * @param gpaCode The gpaCode to set.
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

/**
 * 
 * @return
 */

	public String getCompanyCode() {
		return companyCode;
	}
/**
 * 
 * @param companyCode
 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}



	/**
	 * @return Returns the dsnDate.
	 */
	public String getDsnDate() {
		return dsnDate;
	}



	/**
	 * @param dsnDate The dsnDate to set.
	 */
	public void setDsnDate(String dsnDate) {
		this.dsnDate = dsnDate;
	}



	/**
	 * @return Returns the blgBasis.
	 */
	public String getBlgBasis() {
		return blgBasis;
	}



	/**
	 * @param blgBasis The blgBasis to set.
	 */
	public void setBlgBasis(String blgBasis) {
		this.blgBasis = blgBasis;
	}

	/**
	 * @return the billingDetailsCount
	 */
	public int getBillingDetailsCount() {
		return billingDetailsCount;
	}

	/**
	 * @param billingDetailsCount the billingDetailsCount to set
	 */
	public void setBillingDetailsCount(int billingDetailsCount) {
		this.billingDetailsCount = billingDetailsCount;
	}

	/**
	 * 
	 * @return
	 */
	public int getAbsoluteIndex() {
		return absoluteIndex;
	}

	/**
	 * 
	 * @param absoluteIndex
	 */
	public void setAbsoluteIndex(int absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
	}
	/**
	 * 
	 * @return
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * 
	 * @param pageNumber
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return the poaCode
	 */
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode the poaCode to set
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}
	/**
	 * @return the receptacleSerialNo
	 */
	public String getReceptacleSerialNo() {
		return receptacleSerialNo;
	}
	/**
	 * @param receptacleSerialNo the receptacleSerialNo to set
	 */
	public void setReceptacleSerialNo(String receptacleSerialNo) {
		this.receptacleSerialNo = receptacleSerialNo;
	}
	/**
	 * @return the hni
	 */
	public String getHni() {
		return hni;
	}
	/**
	 * @param hni the hni to set
	 */
	public void setHni(String hni) {
		this.hni = hni;
	}
	/**
	 * @return the regInd
	 */
	public String getRegInd() {
		return regInd;
	}
	/**
	 * @param regInd the regInd to set
	 */
	public void setRegInd(String regInd) {
		this.regInd = regInd;
	}
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	/**
	 * @author A-7371
	 * @return
	 */
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
/**
 * @author A-7371
 * @param mailSequenceNumber
 */
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}

public String getBillingStatus() {
	return billingStatus;
}

public void setBillingStatus(String billingStatus) {
	this.billingStatus = billingStatus;
	}
//Added by A-7794 as part of ICRD-232299
public String getMailSource() {
	return mailSource;
}

public void setMailSource(String mailSource) {
	this.mailSource = mailSource;
}

/**
 * 	Getter for transferPA 
 *	Added by : A-8061 on 07-Jan-2021
 * 	Used for :
 */
public String getTransferPA() {
	return transferPA;
}

/**
 *  @param transferPA the transferPA to set
 * 	Setter for transferPA 
 *	Added by : A-8061 on 07-Jan-2021
 * 	Used for :
 */
public void setTransferPA(String transferPA) {
	this.transferPA = transferPA;
}

/**
 * 	Getter for transferAirline 
 *	Added by : A-8061 on 07-Jan-2021
 * 	Used for :
 */
public String getTransferAirline() {
	return transferAirline;
}

/**
 *  @param transferAirline the transferAirline to set
 * 	Setter for transferAirline 
 *	Added by : A-8061 on 07-Jan-2021
 * 	Used for :
 */
public void setTransferAirline(String transferAirline) {
	this.transferAirline = transferAirline;
}

	

	
	
}