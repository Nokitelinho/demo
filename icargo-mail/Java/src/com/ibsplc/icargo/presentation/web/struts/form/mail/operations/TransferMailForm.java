/*
 * TransferMailForm.java Created on Oct 04, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1876
 *
 */
public class TransferMailForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.transfermail";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "transferMailResources";


	private String assignToFlight;
	private String flightCarrierCode;
	private String flightNumber;
	private String flightDate;
	private String carrierCode;
	private String destination;
	private String[] selectMail;
	private String closeFlag;
	private String duplicateFlightStatus;
	private String mailbag;
	private String initialFocus;
	private int carrierID;
	private String fromScreen;
	private String scanDate;
	private String mailScanTime;

	private String selectMode;
	private String hideRadio;
	private String hidScanTime;

	private String dummyCarCod;
	private String frmCarCod;
	private String frmFltNum;
	private String frmFltDat;
	private String printTransferManifestFlag;

	private String preassignFlag;

	private String transferManifestId;
	
	//Added as a part of ICRD-114630 by a-7871 starts
		private String embargoFlag;
		
		private boolean canCheckEmbargo = true;
		
		/**
		 * @return the canCheckEmbargo
		 */
		public boolean isCanCheckEmbargo() {
		    return this.canCheckEmbargo;
		  }
		/**
		 * @param canCheckEmbargo the canCheckEmbargo to set
		 */
		  public void setCanCheckEmbargo(boolean canCheckEmbargo) {
		    this.canCheckEmbargo = canCheckEmbargo;
		  }
		  /**
			 * @return the embargoFlag
			 */
		public String getEmbargoFlag() {
			return embargoFlag;
		}
		/**
		 * @param embargoFlag the embargoFlag to set
		 */
		public void setEmbargoFlag(String embargoFlag) {
			this.embargoFlag = embargoFlag;
		}
		//Added as a part of ICRD-114630 by a-7871 ends


	public String getTransferManifestId() {
			return transferManifestId;
		}

		public void setTransferManifestId(String transferManifestId) {
			this.transferManifestId = transferManifestId;
		}


	public String getPreassignFlag() {
		return preassignFlag;
	}

	public void setPreassignFlag(String preassignFlag) {
		this.preassignFlag = preassignFlag;
	}


	/**
     * @return SCREEN_ID - String
     */
    public String getScreenId() {
        return SCREEN_ID;
    }

    /**
     * @return PRODUCT_NAME - String
     */
    public String getProduct() {
        return PRODUCT_NAME;
    }

    /**
     * @return SUBPRODUCT_NAME - String
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
	 * @return Returns the flightCarrierCode.
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	/**
	 * @param flightCarrierCode The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the closeFlag.
	 */
	public String getCloseFlag() {
		return this.closeFlag;
	}

	/**
	 * @param closeFlag The closeFlag to set.
	 */
	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}

	/**
	 * @return Returns the assignToFlight.
	 */
	public String getAssignToFlight() {
		return this.assignToFlight;
	}

	/**
	 * @param assignToFlight The assignToFlight to set.
	 */
	public void setAssignToFlight(String assignToFlight) {
		this.assignToFlight = assignToFlight;
	}

	/**
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return this.carrierCode;
	}

	/**
	 * @param carrierCode The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the duplicateFlightStatus.
	 */
	public String getDuplicateFlightStatus() {
		return this.duplicateFlightStatus;
	}

	/**
	 * @param duplicateFlightStatus The duplicateFlightStatus to set.
	 */
	public void setDuplicateFlightStatus(String duplicateFlightStatus) {
		this.duplicateFlightStatus = duplicateFlightStatus;
	}

	/**
	 * @return Returns the initialFocus.
	 */
	public String getInitialFocus() {
		return this.initialFocus;
	}

	/**
	 * @param initialFocus The initialFocus to set.
	 */
	public void setInitialFocus(String initialFocus) {
		this.initialFocus = initialFocus;
	}

	/**
	 * @return Returns the flightDate.
	 */
	public String getFlightDate() {
		return this.flightDate;
	}

	/**
	 * @param flightDate The flightDate to set.
	 */
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return Returns the selectMail.
	 */
	public String[] getSelectMail() {
		return this.selectMail;
	}

	/**
	 * @param selectMail The selectMail to set.
	 */
	public void setSelectMail(String[] selectMail) {
		this.selectMail = selectMail;
	}

	/**
	 * @return Returns the mailbag.
	 */
	public String getMailbag() {
		return this.mailbag;
	}

	/**
	 * @param mailbag The mailbag to set.
	 */
	public void setMailbag(String mailbag) {
		this.mailbag = mailbag;
	}

	/**
	 * @return Returns the carrierID.
	 */
	public int getCarrierID() {
		return this.carrierID;
	}

	/**
	 * @param carrierID The carrierID to set.
	 */
	public void setCarrierID(int carrierID) {
		this.carrierID = carrierID;
	}

	/**
	 * @return Returns the fromScreen.
	 */
	public String getFromScreen() {
		return this.fromScreen;
	}

	/**
	 * @param fromScreen The fromScreen to set.
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	public String getMailScanTime() {
		return mailScanTime;
	}

	public void setMailScanTime(String mailScanTime) {
		this.mailScanTime = mailScanTime;
	}

	public String getScanDate() {
		return scanDate;
	}

	public void setScanDate(String scanDate) {
		this.scanDate = scanDate;
	}

	public String getSelectMode() {
		return selectMode;
	}

	public void setSelectMode(String selectMode) {
		this.selectMode = selectMode;
	}

	public String getHideRadio() {
		return hideRadio;
	}

	public void setHideRadio(String hideRadio) {
		this.hideRadio = hideRadio;
	}

	public String getHidScanTime() {
		return hidScanTime;
	}

	public void setHidScanTime(String hidScanTime) {
		this.hidScanTime = hidScanTime;
	}

	/**
	 * @return the dummyCarCod
	 */
	public String getDummyCarCod() {
		return dummyCarCod;
	}

	/**
	 * @param dummyCarCod the dummyCarCod to set
	 */
	public void setDummyCarCod(String dummyCarCod) {
		this.dummyCarCod = dummyCarCod;
	}

	/**
	 * @return the printTransferManifestFlag
	 */
	public String getPrintTransferManifestFlag() {
		return printTransferManifestFlag;
	}

	/**
	 * @param printTransferManifestFlag the printTransferManifestFlag to set
	 */
	public void setPrintTransferManifestFlag(String printTransferManifestFlag) {
		this.printTransferManifestFlag = printTransferManifestFlag;
	}

	public String getFrmFltDat() {
		return frmFltDat;
	}

	public void setFrmFltDat(String frmFltDat) {
		this.frmFltDat = frmFltDat;
	}

	public String getFrmFltNum() {
		return frmFltNum;
	}

	public void setFrmFltNum(String frmFltNum) {
		this.frmFltNum = frmFltNum;
	}

	public String getFrmCarCod() {
		return frmCarCod;
	}

	public void setFrmCarCod(String frmCarCod) {
		this.frmCarCod = frmCarCod;
	}

}
