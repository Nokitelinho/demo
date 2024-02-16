/*
 * ViewMailFlightSectorRevenueForm.java created on Feb 28, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-3429
 *
 */
public class ViewMailFlightSectorRevenueForm extends ScreenModel {

	private static final String BUNDLE = "viewFlightSectorRevenue";

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID = "mailtracking.mra.defaults.viewflightsectorrevenue";

	private String flightNo;

	private String carrierCode;

	private String flightDate;

	private String flightStatus;

	private String sector;

	private String flightSectorStatus;

	private String duplicateFlightFlag;

	private String listSegmentsFlag;

	private String[] selectedRows;
	
	private String selectedDsn;

	private double totGrossWeight;

	private double totWeightCharge;

	private double totNetRevenue;
	
	private String count;
	
	private String fromScreen;
	
	private String sectorCtrlFlg;

	/**
	 * @return the fromScreen
	 */
	public String getFromScreen() {
		return fromScreen;
	}

	/**
	 * @param fromScreen the fromScreen to set
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	/**
	 * @return the count
	 */
	public String getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(String count) {
		this.count = count;
	}

	/**
	 * @return the totGrossWeight
	 */
	public double getTotGrossWeight() {
		return totGrossWeight;
	}

	/**
	 * @param totGrossWeight the totGrossWeight to set
	 */
	public void setTotGrossWeight(double totGrossWeight) {
		this.totGrossWeight = totGrossWeight;
	}

	/**
	 * @return the totNetRevenue
	 */
	public double getTotNetRevenue() {
		return totNetRevenue;
	}

	/**
	 * @param totNetRevenue the totNetRevenue to set
	 */
	public void setTotNetRevenue(double totNetRevenue) {
		this.totNetRevenue = totNetRevenue;
	}

	/**
	 * @return the totWeightCharge
	 */
	public double getTotWeightCharge() {
		return totWeightCharge;
	}

	/**
	 * @param totWeightCharge the totWeightCharge to set
	 */
	public void setTotWeightCharge(double totWeightCharge) {
		this.totWeightCharge = totWeightCharge;
	}

	

	/**
	 * @return the listSegmentsFlag
	 */
	public String getListSegmentsFlag() {
		return listSegmentsFlag;
	}

	/**
	 * @param listSegmentsFlag the listSegmentsFlag to set
	 */
	public void setListSegmentsFlag(String listSegmentsFlag) {
		this.listSegmentsFlag = listSegmentsFlag;
	}

	/**
	 * @return the duplicateFlightFlag
	 */
	public String getDuplicateFlightFlag() {
		return duplicateFlightFlag;
	}

	/**
	 * @param duplicateFlightFlag the duplicateFlightFlag to set
	 */
	public void setDuplicateFlightFlag(String duplicateFlightFlag) {
		this.duplicateFlightFlag = duplicateFlightFlag;
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
	 * @return the flightDate
	 */
	public String getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate the flightDate to set
	 */
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return the flightNo
	 */
	public String getFlightNo() {
		return flightNo;
	}

	/**
	 * @param flightNo the flightNo to set
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	/**
	 * @return the flightSectorStatus
	 */
	public String getFlightSectorStatus() {
		return flightSectorStatus;
	}

	/**
	 * @param flightSectorStatus the flightSectorStatus to set
	 */
	public void setFlightSectorStatus(String flightSectorStatus) {
		this.flightSectorStatus = flightSectorStatus;
	}

	/**
	 * @return the flightStatus
	 */
	public String getFlightStatus() {
		return flightStatus;
	}

	/**
	 * @param flightStatus the flightStatus to set
	 */
	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	}

	/**
	 * @return the carrierCode
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode the carrierCode to set
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return the sector
	 */
	public String getSector() {
		return sector;
	}

	/**
	 * @param sector the sector to set
	 */
	public void setSector(String sector) {
		this.sector = sector;
	}

	public String[] getSelectedRows() {
		return selectedRows;
	}

	public void setSelectedRows(String[] selectedRows) {
		this.selectedRows = selectedRows;
	}

	public String getSelectedDsn() {
		return selectedDsn;
	}

	public void setSelectedDsn(String selectedDsn) {
		this.selectedDsn = selectedDsn;
	}

	/**
	 * @return the sectorCtrlFlg
	 */
	public String getSectorCtrlFlg() {
		return sectorCtrlFlg;
	}

	/**
	 * @param sectorCtrlFlg the sectorCtrlFlg to set
	 */
	public void setSectorCtrlFlg(String sectorCtrlFlg) {
		this.sectorCtrlFlg = sectorCtrlFlg;
	}

}