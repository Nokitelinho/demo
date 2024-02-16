
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;


public class MailPerformanceForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.mailperformance";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "mailPerformanceResources";

	private String bundle;
	
	private String pacode;
	private String airport;
	private String filterResdit;
	private  String receivedFromTruck;
	private String screenFlag;
	
	public String getScreenFlag() {
		return screenFlag;
	}

	public void setScreenFlag(String screenFlag) {
		this.screenFlag = screenFlag;
	}
	
	private String[] rowId;
	private String[] operationFlag;
	private String disableSave;
	private  String[] airportCodes;
	private  String[] resditModes;
	private String[] truckFlag;
	
	private long[] seqnum;
	
	public long[] getSeqnum() {
		return seqnum;
	}

	public void setSeqnum(long[] seqnum) {
		this.seqnum = seqnum;
	}

	public String[] getResditModes() {
		return resditModes;
	}

	public void setResditModes(String[] resditModes) {
		this.resditModes = resditModes;
	}

	public String[] getAirportCodes() {
		return airportCodes;
	}

	public void setAirportCodes(String[] airportCodes) {
		this.airportCodes = airportCodes;
	}

	public String[] getTruckFlag() {
		return truckFlag;
	}

	public void setTruckFlag(String[] truckFlag) {
		this.truckFlag = truckFlag;
	}

	public String getFilterResdit() {
		return filterResdit;
	}

	public void setFilterResdit(String filterResdit) {
		this.filterResdit = filterResdit;
	}


	public String getPacode() {
		return pacode;
	}

	public void setPacode(String pacode) {
		this.pacode = pacode;
	}

	

	public String getReceivedFromTruck() {
		return receivedFromTruck;
	}

	public void setReceivedFromTruck(String receivedFromTruck) {
		this.receivedFromTruck = receivedFromTruck;
	}

	//Coterminus end

	/**
	 * @return screenId
	 */
	public String getScreenId() {
		return SCREEN_ID;
	}

	/**
	 * @return product
	 */
	public String getProduct() {
		return PRODUCT_NAME;
	}

	/**
	 * @return subProduct
	 */
	public String getSubProduct() {
		return SUBPRODUCT_NAME;
	}

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	/**
	 * @return Returns the airport.
	 */
	public String getAirport() {
		return airport;
	}

	/**
	 * @param airport The airport to set.
	 */
	public void setAirport(String airport) {
		this.airport = airport;
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
	 * @return Returns the rowId.
	 */
	public String[] getRowId() {
		return rowId;
	}

	/**
	 * @param rowId The rowId to set.
	 */
	public void setRowId(String[] rowId) {
		this.rowId = rowId;
	}

	/**
	 * @return Returns the disableSave.
	 */
	public String getDisableSave() {
		return this.disableSave;
	}

	/**
	 * @param disableSave The disableSave to set.
	 */
	public void setDisableSave(String disableSave) {
		this.disableSave = disableSave;
	}

}
