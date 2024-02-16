/*
 * ListCCAForm.java created on Feb 28, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel; 
import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
/**
 * @author A-2270
 *
 */
public class ListCCAForm extends ScreenModel {

	private static final String BUNDLE = "listCCA";

	// private String bundle;

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID = "mailtracking.mra.defaults.listcca";

	private String ccaNum;

	private String ccaType;

	private String dsn;
	private String dsnDate;
	private String receptacleSerialNumber;
	private String overrideRounding;//added by a-7871 for ICRD-214766
	private String diplayWgt;	
	private String diplayWgtUnit;	
	
	public String getDiplayWgt() {
		return diplayWgt;
	}

	public void setDiplayWgt(String diplayWgt) {
		this.diplayWgt = diplayWgt;
	}
	
	public String getDiplayWgtUnit() {
		return diplayWgtUnit;
	}

	public void setDiplayWgtUnit(String diplayWgtUnit) {
		this.diplayWgtUnit = diplayWgtUnit;
	}

	/**
	 * @return the overrideRounding
	 */
	public String getOverrideRounding() {
		return overrideRounding;
	}

	/**
	 * @param overrideRounding the overrideRounding to set
	 */
	public void setOverrideRounding(String overrideRounding) {
		this.overrideRounding = overrideRounding;
	}

	public String getReceptacleSerialNumber() {
		return receptacleSerialNumber;
	}

	public void setReceptacleSerialNumber(String receptacleSerialNumber) {
		this.receptacleSerialNumber = receptacleSerialNumber;
	}

	public String getHighestNumberIndicator() {
		return highestNumberIndicator;
	}

	public void setHighestNumberIndicator(String highestNumberIndicator) {
		this.highestNumberIndicator = highestNumberIndicator;
	}

	public String getOriginOfficeOfExchange() {
		return originOfficeOfExchange;
	}

	public void setOriginOfficeOfExchange(String originOfficeOfExchange) {
		this.originOfficeOfExchange = originOfficeOfExchange;
	}

	public String getDestinationOfficeOfExchange() {
		return destinationOfficeOfExchange;
	}

	public void setDestinationOfficeOfExchange(String destinationOfficeOfExchange) {
		this.destinationOfficeOfExchange = destinationOfficeOfExchange;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSubClass() {
		return subClass;
	}

	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}

	public String getMailCategory() {
		return mailCategory;
	}

	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}

	private String highestNumberIndicator;
	private String registeredIndicator;
	public String getRegisteredIndicator() {
		return registeredIndicator;
	}

	public void setRegisteredIndicator(String registeredIndicator) {
		this.registeredIndicator = registeredIndicator;
	}

	private String originOfficeOfExchange;
	private String destinationOfficeOfExchange;
	
	private String year;
	private String subClass;
	private String mailCategory;
	private String ccaStatus ;
	private String billingStatus;
	private String issueParty;

	private String airlineCode;

	private String gpaCode;

	private String gpaName;

	private String frmDate;

	private String toDate;

	private String displayPage="1";

	private String lastPageNum="0";

	private String[] selectedRows;

	private String count;
	
	private String comboFlag;
	//Added for ICRD 7352
	private String origin;
	private String destination;
	
	private String countTotalFlag=""; //added by A-5201 for CR ICRD-21098
	private String mailbagId;
	
	 private String mcacreationtype; //Added by A-7540
	 
	 public String getMcacreationtype() {
			return mcacreationtype;
		}

	public void setMcacreationtype(String mcacreationtype) {
			this.mcacreationtype = mcacreationtype;
		}	

    /**
	 * @return the mailbagId
	 */
	public String getMailbagId() {
		return mailbagId;
	}

	/**
	 * @param mailbagId the mailbagId to set
	 */
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}

	/**
	 * @return the parameterValue
	 */
	public String getParameterValue() {
		return parameterValue;
	}

	/**
	 * @param parameterValue the parameterValue to set
	 */
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	private String parameterValue;
	/**
	 * @return Returns the comboFlag.
	 */
	public String getComboFlag() {
		return comboFlag;
	}

	/**
	 * @param comboFlag The comboFlag to set.
	 */
	public void setComboFlag(String comboFlag) {
		this.comboFlag = comboFlag;
	}

	/**
	 * @return the count
	 */
	public String getCount() {
		return count;
	}

	/**
	 * @param count
	 *  the count to set
	 */
	public void setCount(String count) {
		this.count = count;
	}










	/**
	 * 
	 */
	public String getScreenId() {
		// TODO Auto-generated method stub
		return SCREENID;
	}

	/**
	 * 
	 */
	public String getProduct() {
		// TODO Auto-generated method stub
		return PRODUCT;
	}

	/**
	 * 
	 */
	public String getSubProduct() {
		// TODO Auto-generated method stub
		return SUBPRODUCT;
	}

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode
	 *            The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * @return the ccaStatus
	 */
	public String getCcaStatus() {
		return ccaStatus;
	}

	/**
	 * @param ccaStatus
	 *            the ccaStatus to set
	 */
	public void setCcaStatus(String ccaStatus) {
		this.ccaStatus = ccaStatus;
	}

	/**
	 * @return Returns the ccaNum.
	 */
	public String getCcaNum() {
		return ccaNum;
	}

	/**
	 * @param ccaNum
	 *            The ccaNum to set.
	 */
	public void setCcaNum(String ccaNum) {
		this.ccaNum = ccaNum;
	}

	/**
	 * @return Returns the ccaType.
	 */
	public String getCcaType() {
		return ccaType;
	}

	/**
	 * @param ccaType
	 *            The ccaType to set.
	 */
	public void setCcaType(String ccaType) {
		this.ccaType = ccaType;
	}

	/**
	 * @return Returns the dsn.
	 */
	public String getDsn() {
		return dsn;
	}

	/**
	 * @param dsn
	 *            The dsn to set.
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

	/**
	 * @return Returns the dsnDate.
	 */
	public String getDsnDate() {
		return dsnDate;
	}

	/**
	 * @param dsnDate
	 *            The dsnDate to set.
	 */
	public void setDsnDate(String dsnDate) {
		this.dsnDate = dsnDate;
	}

	/**
	 * @return Returns the frmDate.
	 * Added by A-5135 for icrd-16381 
	 */
	@ DateFieldId(id="ListMailCorrectionAdviceDateRange",fieldType="from")/*Added By A-5131 for ICRD-9704*/
	public String getFrmDate() {
		return frmDate;
	}

	/**
	 * @param frmDate
	 *            The frmDate to set.
	 */
	public void setFrmDate(String frmDate) {
		this.frmDate = frmDate;
	}
	
	/**
	 * @return Returns the gpaCode.
	 */
	public String getGpaCode() {
		return gpaCode;
	}

	/**
	 * @param gpaCode
	 *            The gpaCode to set.
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	/**
	 * @return Returns the gpaName.
	 */
	public String getGpaName() {
		return gpaName;
	}

	/**
	 * @param gpaName
	 *            The gpaName to set.
	 */
	public void setGpaName(String gpaName) {
		this.gpaName = gpaName;
	}

	/**
	 * @return Returns the issueParty.
	 */
	public String getIssueParty() {
		return issueParty;
	}

	/**
	 * @param issueParty
	 *            The issueParty to set.
	 */
	public void setIssueParty(String issueParty) {
		this.issueParty = issueParty;
	}

	/**
	 * @return Returns the toDate.
	 *  Added by A-5135 for icrd-16381 
	 */
	@ DateFieldId(id="ListMailCorrectionAdviceDateRange",fieldType="to")/*Added By A-5131 for ICRD-9704*/
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 *            The toDate to set.
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the selectedRows
	 */
	public String[] getSelectedRows() {
		return selectedRows;
	}
	/**
	 * @param selectedRows
	 *            the selectedRows to set
	 */
	public void setSelectedRows(String[] selectedRows) {
		this.selectedRows = selectedRows;
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
	
	//added by A-5201 for CR ICRD-21098 starts
	public String getCountTotalFlag() {
		return countTotalFlag;
	}

	public void setCountTotalFlag(String countTotalFlag) {
		this.countTotalFlag = countTotalFlag;
	}
	//added by A-5201 for CR ICRD-21098 end

	/**
	 * 	Getter for billingStatus 
	 *	Added by : A-6991 on 04-Oct-2017
	 * 	Used for : ICRD-211662
	 */
	public String getBillingStatus() {
		return billingStatus;
	}

	/**
	 *  @param billingStatus the billingStatus to set
	 * 	Setter for billingStatus 
	 *	Added by : A-6991 on 04-Oct-2017
	 * 	Used for :  ICRD-211662
	 */ 
	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}
	public String getDisplayPage() {
		return displayPage;
	}
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	public String getLastPageNum() {
		return lastPageNum;
	}
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

}
