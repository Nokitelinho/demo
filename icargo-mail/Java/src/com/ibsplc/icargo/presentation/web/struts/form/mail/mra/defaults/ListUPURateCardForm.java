/*
 * ListUPURateCardForm.java Created on Jan 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1556
 *
 */
public class ListUPURateCardForm extends ScreenModel {

	//added by A-5175 for QF CR ICRD-21098 starts
	public static final String PAGINATION_MODE_FROM_FILTER="FILTER";
	
	public static final String PAGINATION_MODE_FROM_NAVIGATION="NAVIGATION";
	//ends
	private static final String BUNDLE = "listupuratecardresources";

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID ="mailtracking.mra.defaults.listupuratecard";

	private String companyCode;

	private String rateCardID;

	private String rateCardStatus;

	private String startDate;

	private String endDate;

	private String displayPage = "1";

	private String lastPageNum="";

	private String[] rowCount;

	private String changeStatusFlag;

	private String viewStatus;
	
	private String navigationMode;
	//Added by A-5166 for ICRD-17262 Starts
	private String rateCardIDNew;

	private String mailDistFactor;
	
	private String svTkm;
	
	private String salTkm;
	
	private String airmialTkm;
	
	private String validFrom;
	
	private String validTo;
	
	private String copyFlag;
	//Added by A-5166 for ICRD-17262 Ends

	/**
	 * 	Getter for navigationMode 
	 *	Added by : A-5175 on 16-Oct-2012
	 * 	Used for : ICRD-21098
	 */
	public String getNavigationMode() {
		return navigationMode;
	}

	/**
	 *  @param navigationMode the navigationMode to set
	 * 	Setter for navigationMode 
	 *	Added by : A-5175 on 16-Oct-2012
	 * 	Used for : ICRD-21098
	 */
	public void setNavigationMode(String navigationMode) {
		this.navigationMode = navigationMode;
	}

	/**
	 * @return Returns the changeStatusFlag.
	 */
	public String getChangeStatusFlag() {
		return changeStatusFlag;
	}

	/**
	 * @param changeStatusFlag The changeStatusFlag to set.
	 */
	public void setChangeStatusFlag(String changeStatusFlag) {
		this.changeStatusFlag = changeStatusFlag;
	}

	/**
	 *
	 * @return SCREENID
	 */
    public String getScreenId() {
        return SCREENID;
    }

    /**
	 *
	 * @return PRODUCT
	 */
    public String getProduct() {
        return PRODUCT;
    }

    /**
	 *
	 * @return SUBPRODUCT
	 */
    public String getSubProduct() {
        return SUBPRODUCT;
    }

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the endDate.
	 */
	@DateFieldId(id="ListUPURateCardDateRange",fieldType="to")//Added By T-1925 for ICRD-9704
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return Returns the rateCardID.
	 */
	public String getRateCardID() {
		return rateCardID;
	}

	/**
	 * @param rateCardID The rateCardID to set.
	 */
	public void setRateCardID(String rateCardID) {
		this.rateCardID = rateCardID;
	}

	/**
	 * @return Returns the rateCardStatus.
	 */
	public String getRateCardStatus() {
		return rateCardStatus;
	}

	/**
	 * @param rateCardStatus The rateCardStatus to set.
	 */
	public void setRateCardStatus(String rateCardStatus) {
		this.rateCardStatus = rateCardStatus;
	}

	/**
	 * @return Returns the startDate.
	 */
	@DateFieldId(id="ListUPURateCardDateRange",fieldType="from")//Added By T-1925 for ICRD-9704
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @return Returns the displayPage.
	 */
	public String getDisplayPage() {
		return displayPage;
	}

	/**
	 * @param displayPage The displayPage to set.
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	/**
	 * @return Returns the lastPageNum.
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}

	/**
	 * @param lastPageNum The lastPageNum to set.
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	/**
	 * @return Returns the rowCount.
	 */
	public String[] getRowCount() {
		return rowCount;
	}

	/**
	 * @param rowCount The rowCount to set.
	 */
	public void setRowCount(String[] rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * @return Returns the viewStatus.
	 */
	public String getViewStatus() {
		return viewStatus;
	}

	/**
	 * @param viewStatus The viewStatus to set.
	 */
	public void setViewStatus(String viewStatus) {
		this.viewStatus = viewStatus;
	}
	/**
	 * 	Getter for RateCardIdNew 
	 *	Added by : A-5166 on 07-Feb-2013
	 * 	Used for : ICRD-17262
	 */
	public String getRateCardIDNew() {
		return rateCardIDNew;
	}

	/**
	 *  @param RateCardIDNew 
	 * 	Setter for RateCardIDNew 
	 *	Added by : A-5166 on 07-Feb-2013
	 * 	Used for : ICRD-17262
	 */
	public void setRateCardIDNew(String rateCardIDNew) {
		this.rateCardIDNew = rateCardIDNew;
	}

	/**
	 * 	Getter for MailDistFactor 
	 *	Added by : A-5166 on 07-Feb-2013
	 * 	Used for : ICRD-17262
	 */
	public String getMailDistFactor() {
		return mailDistFactor;
	}

	/**
	 *  @param MailDistFactor 
	 * 	Setter for MailDistFactor 
	 *	Added by : A-5166 on 07-Feb-2013
	 * 	Used for : ICRD-17262
	 */
	public void setMailDistFactor(String mailDistFactor) {
		this.mailDistFactor = mailDistFactor;
	}

	





	/**
	 * 	Getter for AirmialTkm 
	 *	Added by : A-5166 on 07-Feb-2013
	 * 	Used for : ICRD-17262
	 */
	public String getAirmialTkm() {
		return airmialTkm;
	}

	/**
	 *  @param AirmialTkm 
	 * 	Setter for AirmialTkm 
	 *	Added by : A-5166 on 07-Feb-2013
	 * 	Used for : ICRD-17262
	 */
	public void setAirmialTkm(String airmialTkm) {
		this.airmialTkm = airmialTkm;
	}

	/**
	 * 	Getter for salTkm 
	 *	Added by : A-5526 on 25-AUG-2014
	 * 	Used for : ICRD-58891
	 */
	public String getSalTkm() {
		return salTkm;
	}
	/**  @param salTkm 
	 * 	Setter for salTkm 
	 *		Added by : A-5526 on 25-AUG-2014
	 * 	Used for : ICRD-58891
	 */
	public void setSalTkm(String salTkm) {
		this.salTkm = salTkm;
	}
	/**
	 * 	Getter for svTkm 
	 *	Added by : A-5526 on 25-AUG-2014
	 * 	Used for : ICRD-58891
	 */
	public String getSvTkm() {
		return svTkm;
	}
	 /**  @param svTkm 
	 * 	Setter for svTkm 
	 *		Added by : A-5526 on 25-AUG-2014
	 * 	Used for : ICRD-58891
	 */
	public void setSvTkm(String svTkm) {
		this.svTkm = svTkm;
	}
	/**
	 * 	Getter for ValidFrom 
	 *	Added by : A-5166 on 07-Feb-2013
	 * 	Used for : ICRD-17262
	 */
	public String getValidFrom() {
		return validFrom;
	}

	/**
	 *  @param ValidFrom 
	 * 	Setter for ValidFrom 
	 *	Added by : A-5166 on 07-Feb-2013
	 * 	Used for : ICRD-17262
	 */
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	/**
	 * 	Getter for ValidTo 
	 *	Added by : A-5166 on 07-Feb-2013
	 * 	Used for : ICRD-17262
	 */
	public String getValidTo() {
		return validTo;
	}

	/**
	 *  @param ValidTo 
	 * 	Setter for ValidTo 
	 *	Added by : A-5166 on 07-Feb-2013
	 * 	Used for : ICRD-17262
	 */
	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}

	/**
	 * 	Getter for CopyFlag 
	 *	Added by : A-5166 on 07-Feb-2013
	 * 	Used for : ICRD-17262
	 */
	public String getCopyFlag() {
		return copyFlag;
	}

	/**
	 *  @param CopyFlag 
	 * 	Setter for CopyFlag 
	 *	Added by : A-5166 on 07-Feb-2013
	 * 	Used for : ICRD-17262
	 */
	public void setCopyFlag(String copyFlag) {
		this.copyFlag = copyFlag;
	}

}
