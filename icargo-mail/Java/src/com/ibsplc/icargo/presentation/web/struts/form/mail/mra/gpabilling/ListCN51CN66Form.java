/*
 * ListCN51CN66Form.java Created on Jan 10, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1556
 *
 */
public class ListCN51CN66Form extends ScreenModel {


private static final String BUNDLE ="listcn51cn66";

	//private String bundle;

	private static final String PRODUCT = "mail";

	private  static final String SUBPRODUCT = "mra";

	private static final String SCREENID = "mailtracking.mra.gpabilling.listcn51cn66";

	private String invoiceNumber;

	private String gpaCode;

	private String gpaName;

	private String airlineCode;

	private String category;

	private String origin;

	private String destination;

	private String[] observations;

	private String [] operationFlag;

	private String invokingScreen;

	private String btnStatus;

	private String saveBtnStatus;

	private double totalBilledAmount;
	
	private String fromScreen;

	private String accEntryFlag;
	
	private String[] ccaRefNos;
	//added for cr ICRD-7370
	private String [] serviceTax;
	private String [] tds;
	private String [] netAmount;
	private String restrictionFlag;
	private String receptacleSerialNumber;
	private String lastPageNum = "0";
	private String checkButton;
	private String reportSpecificFlag;
	private String selectedRow;
	private String reportRestrictionFlag;
	private String dsnNumber;
	private String invoiceStatus;
	private String invStatusDesc;
	/**
	 * Added for ICRD-189966
	 */
	private String overrideRounding;
	private String fileName;
	private String gpaType;
	/**
	 * @return the checkButton
	 */
	public String getCheckButton() {
		return checkButton;
	}
	/**
	 * @param checkButton the checkButton to set
	 */
	public void setCheckButton(String checkButton) {
		this.checkButton = checkButton;
	}
	/**
	 * @return the countTotalFlag
	 */
	public String getCountTotalFlag() {
		return countTotalFlag;
	}
	/**
	 * @param countTotalFlag the countTotalFlag to set
	 */
	public void setCountTotalFlag(String countTotalFlag) {
		this.countTotalFlag = countTotalFlag;
	}

	private String countTotalFlag = "";
    /**
	 * @return the absoluteIndex
	 */
	public String getAbsoluteIndex() {
		return absoluteIndex;
	}
	/**
	 * @param absoluteIndex the absoluteIndex to set
	 */
	public void setAbsoluteIndex(String absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
	}

	private String absoluteIndex="";
	/**
	 * @return the lastPageNumber
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}
	/**
	 * @param lastPageNumber the lastPageNumber to set
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}
	/**
	 * @return the displayPage
	 */
	public String getDisplayPage() {
		return displayPage;
	}
	/**
	 * @param displayPage the displayPage to set
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	

	private String displayPage = "1";
	private String displayPageCN66 = "1";
	
	/**
	 * @return the displayPageCN66
	 */
	public String getDisplayPageCN66() {
		return displayPageCN66;
	}
	/**
	 * @param displayPageCN66 the displayPageCN66 to set
	 */
	public void setDisplayPageCN66(String displayPageCN66) {
		this.displayPageCN66 = displayPageCN66;
	}

	private String cn66LastPageNumber = "0";

	/**
	 * @return the cn66LastPageNumber
	 */
	public String getCn66LastPageNumber() {
		return cn66LastPageNumber;
	}
	/**
	 * @param cn66LastPageNumber the cn66LastPageNumber to set
	 */
	public void setCn66LastPageNumber(String cn66LastPageNumber) {
		this.cn66LastPageNumber = cn66LastPageNumber;
	}
	/**
	 * @return the cn66lDisplayPage
	 */
	
	

	
	
	/**
	 * @return the receptacleSerialNumber
	 */
	public String getReceptacleSerialNumber() {
		return receptacleSerialNumber;
	}
	/**
	 * @param receptacleSerialNumber the receptacleSerialNumber to set
	 */
	public void setReceptacleSerialNumber(String receptacleSerialNumber) {
		this.receptacleSerialNumber = receptacleSerialNumber;
	}
	/**
	 * @return the highestNumberIndicator
	 */
	public String getHighestNumberIndicator() {
		return highestNumberIndicator;
	}
	/**
	 * @param highestNumberIndicator the highestNumberIndicator to set
	 */
	public void setHighestNumberIndicator(String highestNumberIndicator) {
		this.highestNumberIndicator = highestNumberIndicator;
	}
	/**
	 * @return the registeredIndicator
	 */
	public String getRegisteredIndicator() {
		return registeredIndicator;
	}
	/**
	 * @param registeredIndicator the registeredIndicator to set
	 */
	public void setRegisteredIndicator(String registeredIndicator) {
		this.registeredIndicator = registeredIndicator;
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

	private String highestNumberIndicator;
	private String registeredIndicator;
	private String parameterValue;
    /**
	 * @return the restrictionFlag
	 */
	public String getRestrictionFlag() {
		return restrictionFlag;
	}
	/**
	 * @param restrictionFlag the restrictionFlag to set
	 */
	public void setRestrictionFlag(String restrictionFlag) {
		this.restrictionFlag = restrictionFlag;
	}
    /**
	 * @return Returns the ccaRefNos.
	 */
	public String[] getCcaRefNos() {
		return ccaRefNos;
	}

	/**
	 * @param ccaRefNos The ccaRefNos to set.
	 */
	public void setCcaRefNos(String[] ccaRefNos) {
		this.ccaRefNos = ccaRefNos;
	}

	/**
	 * @return Returns the fromScreen.
	 */
	public String getFromScreen() {
		return fromScreen;
	}

	/**
	 * @param fromScreen The fromScreen to set.
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	/**
	 * @return Returns the totalBilledAmount.
	 */
	public double getTotalBilledAmount() {
		return totalBilledAmount;
	}

	/**
	 * @param totalBilledAmount The totalBilledAmount to set.
	 */
	public void setTotalBilledAmount(double totalBilledAmount) {
		this.totalBilledAmount = totalBilledAmount;
	}

	/**
	 * @return Returns the btnStatus.
	 */
	public String getBtnStatus() {
		return btnStatus;
	}

	/**
	 * @param btnStatus The btnStatus to set.
	 */
	public void setBtnStatus(String btnStatus) {
		this.btnStatus = btnStatus;
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
	 * @return Returns the category.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category The category to set.
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return Returns the destination.
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination The destination to set.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
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
	 * @return Returns the gpaName.
	 */
	public String getGpaName() {
		return gpaName;
	}

	/**
	 * @param gpaName The gpaName to set.
	 */
	public void setGpaName(String gpaName) {
		this.gpaName = gpaName;
	}

	/**
	 * @return Returns the invoiceNumber.
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber The invoiceNumber to set.
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return Returns the observations.
	 */
	public String[] getObservations() {
		return observations;
	}

	/**
	 * @param observations The observations to set.
	 */
	public void setObservations(String[] observations) {
		this.observations = observations;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String[] getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String[] operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return Returns the origin.
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin The origin to set.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return Returns the screenId.
	 */
    public String getScreenId() {
        return SCREENID;
    }

    /**
	 * @return Returns the product.
	 */
    public String getProduct() {
        return PRODUCT;
    }

    /**
	 * @return Returns the subproduct.
	 */
    public String getSubProduct() {
        return SUBPRODUCT;
    }

    /**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

//	/**
//	 * @param bundle The bundle to set.
//	 */
//	public void setBundle(String bundle) {
//		this.bundle = bundle;
//	}

	/**
	 * @return Returns the invokingScreen.
	 */
	public String getInvokingScreen() {
		return invokingScreen;
	}

	/**
	 * @param invokingScreen The invokingScreen to set.
	 */
	public void setInvokingScreen(String invokingScreen) {
		this.invokingScreen = invokingScreen;
	}

	/**
	 * @return Returns the saveBtnStatus.
	 */
	public String getSaveBtnStatus() {
		return saveBtnStatus;
	}

	/**
	 * @param saveBtnStatus The saveBtnStatus to set.
	 */
	public void setSaveBtnStatus(String saveBtnStatus) {
		this.saveBtnStatus = saveBtnStatus;
	}

	public String getAccEntryFlag() {
		return accEntryFlag;
	}

	public void setAccEntryFlag(String accEntryFlag) {
		this.accEntryFlag = accEntryFlag;
	}

	/**
	 * @return the serviceTax
	 */
	public String[] getServiceTax() {
		return serviceTax;
	}

	/**
	 * @param serviceTax the serviceTax to set
	 */
	public void setServiceTax(String[] serviceTax) {
		this.serviceTax = serviceTax;
	}

	/**
	 * @return the tds
	 */
	public String[] getTds() {
		return tds;
	}

	/**
	 * @param tds the tds to set
	 */
	public void setTds(String[] tds) {
		this.tds = tds;
	}

	/**
	 * @return the netAmount
	 */
	public String[] getNetAmount() {
		return netAmount;
	}

	/**
	 * @param netAmount the netAmount to set
	 */
	public void setNetAmount(String[] netAmount) {
		this.netAmount = netAmount;
	}
	/**
	 * @param reportSpecificFlag the reportSpecificFlag to set
	 */
	public void setReportSpecificFlag(String reportSpecificFlag) {
		this.reportSpecificFlag = reportSpecificFlag;
	}
	/**
	 * @return the reportSpecificFlag
	 */
	public String getReportSpecificFlag() {
		return reportSpecificFlag;
	}
	/**
	 * @return the selectedRow
	 */
	public String getSelectedRow() {
		return selectedRow;
	}
	/**
	 * @param selectedRow the selectedRow to set
	 */
	public void setSelectedRow(String selectedRow) {
		this.selectedRow = selectedRow;
	}
	/**
	 * @return the reportRestrictionFlag
	 */
	public String getReportRestrictionFlag() {
		return reportRestrictionFlag;
	}
	/**
	 * @param reportRestrictionFlag the reportRestrictionFlag to set
	 */
	public void setReportRestrictionFlag(String reportRestrictionFlag) {
		this.reportRestrictionFlag = reportRestrictionFlag;
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
	/**
	 * 	Getter for dsnNumber 
	 *	Added by : A-6991 on 30-Aug-2017
	 * 	Used for : ICRD-211662
	 */
	public String getDsnNumber() {
		return dsnNumber;
	}
	/**
	 *  @param dsnNumber the dsnNumber to set
	 * 	Setter for dsnNumber 
	 *	Added by : A-6991 on 30-Aug-2017
	 * 	Used for : ICRD-211662
	 */
	public void setDsnNumber(String dsnNumber) {
		this.dsnNumber = dsnNumber;
	}
	/**
	 * 	Getter for invoiceStatus 
	 *	Added by : A-6991 on 08-Sep-2017
	 * 	Used for : ICRD-211662
	 */
	public String getInvoiceStatus() {
		return invoiceStatus;
	}
	/**
	 *  @param invoiceStatus the invoiceStatus to set
	 * 	Setter for invoiceStatus 
	 *	Added by : A-6991 on 08-Sep-2017
	 * 	Used for : ICRD-211662
	 */
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	/**
	 * 	Getter for fileName
	 *	Added by : A-5219 on 25-May-2021
	 * 	Used for :
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 *  @param fileName the fileName to set
	 * 	Setter for fileName
	 *	Added by : A-5219 on 25-May-2021
	 * 	Used for :
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 	Getter for gpaType
	 *	Added by : A-5219 on 25-May-2021
	 * 	Used for :
	 */
	public String getGpaType() {
		return gpaType;
	}
	/**
	 *  @param gpaType the gpaType to set
	 * 	Setter for gpaType
	 *	Added by : A-5219 on 25-May-2021
	 * 	Used for :
	 */
	public void setGpaType(String gpaType) {
		this.gpaType = gpaType;
	}
	/**
	 * 	Getter for invStatusDesc
	 *	Added by : A-5219 on 25-May-2021
	 * 	Used for :
	 */
	public String getInvStatusDesc() {
		return invStatusDesc;
	}
	/**
	 *  @param invStatusDesc the invStatusDesc to set
	 * 	Setter for invStatusDesc
	 *	Added by : A-5219 on 25-May-2021
	 * 	Used for :
	 */
	public void setInvStatusDesc(String invStatusDesc) {
		this.invStatusDesc = invStatusDesc;
	}


}
