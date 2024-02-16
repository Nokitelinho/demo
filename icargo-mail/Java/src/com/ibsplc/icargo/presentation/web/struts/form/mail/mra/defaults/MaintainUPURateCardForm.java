/*
 * MaintainUPURateCardForm.java Created on Jan 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1556
 *
 */
/**
 * @author A-2391
 *
 */
public class MaintainUPURateCardForm extends ScreenModel {

	private static final String BUNDLE ="maintainUPURateCard";
	//private String bundle;

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID = "mailtracking.mra.defaults.upuratecard.maintainupuratecard";

	private String rateCardId;
	private String status;
	private String description;
	private String validFrom;
	private String validTo;
	private String mialDistFactor;
	private String svTkm;
	private String salTkm;
	private String cpTkm;
	private String airmialTkm;
	private String exchangeRate;
	private String screenStatus;
    private String opFlag;
    private String listStatus;
    
	private String[] origin;
	private String[] destination;
	private String[] iataKilometre;
	private String[] mailKilometre;
	private String[] svSdr;
	private String[] salSdr;
	private String[] cpSdr;
	private String[] airmailSdr;
	private String[] svBaseCurr;
	private String[] salBaseCurr;
	private String[] cpBaseCurr;
	private String[] airmailBaseCurr;
	private String[] validFromRateLine;
	private String[] validToRateLine;
	private String[] rateLineStatus;
	private String[] check;
	private String[] popCheck;
	private String fromPage;
	private String pageFlag;
	private String lastPageNum="0";
	private String displayPage="1";

	/*for change status pop up*/

	private String popupStatus;

	/*for add rate line pop up*/
	private String[] popupOrigin;
	private String[] popupDestn;
	private String okFlag;
	private String[] operationFlag;

	private String selectedIndexes;
	private String orgDstLevel;
	/**
	 * @return Returns the selectedIndexes.
	 */
	public String getSelectedIndexes() {
		return selectedIndexes;
	}
	/**
	 * @param selectedIndexes The selectedIndexes to set.
	 */
	public void setSelectedIndexes(String selectedIndexes) {
		this.selectedIndexes = selectedIndexes;
	}
	/**
     * @return okFlag
     */
    public String getOkFlag() {
		return okFlag;
	}
    /**
	 * @param okFlag
	 */
	public void setOkFlag(String okFlag) {
		this.okFlag = okFlag;
	}
	/**
     * @return okFlag
     */

	public String getPopupStatus() {
		return popupStatus;
	}
	 /**
	 * @param popupStatus
	 */
	public void setPopupStatus(String popupStatus) {
		this.popupStatus = popupStatus;
	}

	/**
     * @return SCREENID
     */

    public String getScreenId() {
        return SCREENID;
    }

    /**
     * @return PRODUCT
     */
    public String getProduct() {
        return PRODUCT;
    }

    /**
     * @return SUBPRODUCT
     */
    public String getSubProduct() {
        return SUBPRODUCT;
    }
    /**
     * @return okFlag
     */
	public static String getBUNDLE() {
		return BUNDLE;
	}
	/**
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		//this.bundle = bundle;
	}
	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}
	/**
     * @return okFlag
     */
	public static String getSCREENID() {
		return SCREENID;
	}
	/**
     * @return okFlag
     */
	public String[] getAirmailBaseCurr() {
		return airmailBaseCurr;
	}
	/**
	 * @param airmailBaseCurr
	 */
	public void setAirmailBaseCurr(String[] airmailBaseCurr) {
		this.airmailBaseCurr = airmailBaseCurr;
	}
	/**
     * @return okFlag
     */
	public String[] getAirmailSdr() {
		return airmailSdr;
	}
	/**
	 * @param airmailSdr
	 */
	public void setAirmailSdr(String[] airmailSdr) {
		this.airmailSdr = airmailSdr;
	}
	/**
     * @return okFlag
     */
	public String getAirmialTkm() {
		return airmialTkm;
	}
	/**
	 * @param airmialTkm The bundle to set.
	 */
	public void setAirmialTkm(String airmialTkm) {
		this.airmialTkm = airmialTkm;
	}
	/**
     * @return okFlag
     */
	public String[] getCpBaseCurr() {
		return cpBaseCurr;
	}
	/**
	 * @param cpBaseCurr The bundle to set.
	 */
	public void setCpBaseCurr(String[] cpBaseCurr) {
		this.cpBaseCurr = cpBaseCurr;
	}
	/**
     * @return cpSdr
     */
	public String[] getCpSdr() {
		return cpSdr;
	}
	/**
	 * @param cpSdr The bundle to set.
	 */
	public void setCpSdr(String[] cpSdr) {
		this.cpSdr = cpSdr;
	}
	/**
     * @return cpTkm
     */
	public String getCpTkm() {
		return cpTkm;
	}
	/**
	 * @param cpTkm The bundle to set.
	 */
	public void setCpTkm(String cpTkm) {
		this.cpTkm = cpTkm;
	}
	/**
     * @return description
     */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The bundle to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
     * @return destination
     */
	public String[] getDestination() {
		return destination;
	}
	/**
	 * @param destination The bundle to set.
	 */
	public void setDestination(String[] destination) {
		this.destination = destination;
	}
	/**
     * @return emsBaseCurr
     */
	public String[] getSvBaseCurr() {
		return svBaseCurr;
	}
	/**
	 * @param emsBaseCurr The bundle to set.
	 */
	public void setSvBaseCurr(String[] svBaseCurr) {
		this.svBaseCurr = svBaseCurr;
	}
	/**
     * @return emsSdr
     */
	public String[] getSvSdr() {
		return svSdr;
	}
	/**
	 * @param emsSdr The bundle to set.
	 */
	public void setSvSdr(String[] svSdr) {
		this.svSdr = svSdr;
	}
	/**
     * @return emsTkm
     */
	public String getSvTkm() {
		return svTkm;
	}
	/**
	 * @param emsTkm The bundle to set.
	 */
	public void setSvTkm(String svTkm) {
		this.svTkm = svTkm;
	}
	/**
     * @return exchangeRate
     */
	public String getExchangeRate() {
		return exchangeRate;
	}
	/**
	 * @param exchangeRate The bundle to set.
	 */
	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	/**
     * @return iataKilometre
     */
	public String[] getIataKilometre() {
		return iataKilometre;
	}
	/**
	 * @param iataKilometre The bundle to set.
	 */
	public void setIataKilometre(String[] iataKilometre) {
		this.iataKilometre = iataKilometre;
	}
	/**
     * @return mailKilometre
     */
	public String[] getMailKilometre() {
		return mailKilometre;
	}
	/**
	 * @param mailKilometre The bundle to set.
	 */
	public void setMailKilometre(String[] mailKilometre) {
		this.mailKilometre = mailKilometre;
	}
	/**
     * @return mialDistFactor
     */
	public String getMialDistFactor() {
		return mialDistFactor;
	}
	/**
	 * @param mialDistFactor The bundle to set.
	 */
	public void setMialDistFactor(String mialDistFactor) {
		this.mialDistFactor = mialDistFactor;
	}
	/**
     * @return origin
     */
	public String[] getOrigin() {
		return origin;
	}
	/**
	 * @param origin The bundle to set.
	 */
	public void setOrigin(String[] origin) {
		this.origin = origin;
	}
	/**
     * @return PRODUCT
     */
	public String getPRODUCT() {
		return PRODUCT;
	}
	/**
     * @return rateCardId
     */
	public String getRateCardId() {
		return rateCardId;
	}
	/**
	 * @param rateCardId The bundle to set.
	 */

	public void setRateCardId(String rateCardId) {
		this.rateCardId = rateCardId;
	}
	/**
     * @return rateLineStatus
     */
	public String[] getRateLineStatus() {
		return rateLineStatus;
	}
	/**
	 * @param rateLineStatus The bundle to set.
	 */
	public void setRateLineStatus(String[] rateLineStatus) {
		this.rateLineStatus = rateLineStatus;
	}
	/**
     * @return salBaseCurr
     */
	public String[] getSalBaseCurr() {
		return salBaseCurr;
	}
	/**
	 * @param salBaseCurr The bundle to set.
	 */
	public void setSalBaseCurr(String[] salBaseCurr) {
		this.salBaseCurr = salBaseCurr;
	}
	/**
     * @return salTkm
     */
	public String getSalTkm() {
		return salTkm;
	}
	/**
	 * @param salTkm The bundle to set.
	 */
	public void setSalTkm(String salTkm) {
		this.salTkm = salTkm;
	}
	/**
     * @return Status
     */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The bundle to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
     * @return SUBPRODUCT
     */
	public String getSUBPRODUCT() {
		return SUBPRODUCT;
	}
	/**
     * @return validFrom
     */
	public String getValidFrom() {
		return validFrom;
	}
	/**
	 * @param validFrom The bundle to set.
	 */
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}
	/**
     * @return validFromRateLine
     */
	public String[] getValidFromRateLine() {
		return validFromRateLine;
	}
	/**
	 * @param validFromRateLine The bundle to set.
	 */
	public void setValidFromRateLine(String[] validFromRateLine) {
		this.validFromRateLine = validFromRateLine;
	}
	/**
     * @return validTo
     */
	public String getValidTo() {
		return validTo;
	}
	/**
	 * @param validTo The bundle to set.
	 */
	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}
	/**
     * @return validToRateLine
     */
	public String[] getValidToRateLine() {
		return validToRateLine;
	}
	/**
	 * @param validToRateLine The bundle to set.
	 */
	public void setValidToRateLine(String[] validToRateLine) {
		this.validToRateLine = validToRateLine;
	}
	/**
     * @return popupDestn
     */
	public String[] getPopupDestn() {
		return popupDestn;
	}
	/**
	 * @param popupDestn The bundle to set.
	 */
	public void setPopupDestn(String popupDestn[]) {
		this.popupDestn = popupDestn;
	}
	/**
     * @return popupOrigin
     */
	public String[] getPopupOrigin() {
		return popupOrigin;
	}
	/**
	 * @param popupOrigin The bundle to set.
	 */
	public void setPopupOrigin(String[] popupOrigin) {
		this.popupOrigin = popupOrigin;
	}
	/**
     * @return check
     */
	public String[] getCheck() {
		return check;
	}
	/**
	 * @param check The bundle to set.
	 */
	public void setCheck(String[] check) {
		this.check = check;
	}
	/**
     * @return fromPage
     */
	public String getFromPage() {
		return fromPage;
	}
	/**
	 * @param fromPage The bundle to set.
	 */
	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}
	/**
     * @return popCheck
     */
	public String[] getPopCheck() {
		return popCheck;
	}
	/**
	 * @param popCheck The bundle to set.
	 */
	public void setPopCheck(String[] popCheck) {
		this.popCheck = popCheck;
	}
	/**
     * @return screenStatus
     */
	public String getScreenStatus() {
		return screenStatus;
	}
	/**
	 * @param  screenStatus to set.
	 */
	public void setScreenStatus(String screenStatus) {
		this.screenStatus = screenStatus;
	}
	/**
     * @return displayPage
     */
	public String getDisplayPage() {
		return displayPage;
	}
	/**
	 * @param displayPage The bundle to set.
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	/**
     * @return lastPageNum
     */
	public String getLastPageNum() {
		return lastPageNum;
	}
	/**
	 * @param lastPageNum The bundle to set.
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}
	/**
     * @return salSdr
     */
	public String[] getSalSdr() {
		return salSdr;
	}
	/**
	 * @param salSdr The bundle to set.
	 */
	public void setSalSdr(String[] salSdr) {
		this.salSdr = salSdr;
	}
	/**
	 * @return Returns the pageFlag.
	 */
	public String getPageFlag() {
		return pageFlag;
	}
	/**
	 * @param pageFlag The pageFlag to set.
	 */
	public void setPageFlag(String pageFlag) {
		this.pageFlag = pageFlag;
	}
	/**
	 * @return Returns the opFlag.
	 */
	public String getOpFlag() {
		return opFlag;
	}
	/**
	 * @param opFlag The opFlag to set.
	 */
	public void setOpFlag(String opFlag) {
		this.opFlag = opFlag;
	}
	/**
	 * @return Returns the listStatus.
	 */
	public String getListStatus() {
		return listStatus;
	}
	/**
	 * @param listStatus The listStatus to set.
	 */
	public void setListStatus(String listStatus) {
		this.listStatus = listStatus;
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
	 * 	Getter for orgDstLevel 
	 *	Added by : A-5219 on 15-Oct-2020
	 * 	Used for :
	 */
	public String getOrgDstLevel() {
		return orgDstLevel;
	}
	/**
	 *  @param orgDstLevel the orgDstLevel to set
	 * 	Setter for orgDstLevel 
	 *	Added by : A-5219 on 15-Oct-2020
	 * 	Used for :
	 */
	public void setOrgDstLevel(String orgDstLevel) {
		this.orgDstLevel = orgDstLevel;
	}

}
