/* TransferDispatchForm.java Created on Feb 2, 2012
*
* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
*
* This software is the proprietary information of IBS Software Services (P) Ltd.
* Use is subject to license terms.
*/

package com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.MeasureAnnotation;

/**
 * @author A-4810
 *
 */
public class TransferDispatchForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.national.transfermail";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "MailtransferingResources";

	
	private String flightNumber;
	private String flightCarrierCode;
	private String flightDate;
	private String flightPou;
	
	private String pieces;
	
	@MeasureAnnotation(mappedValue="weightMeasure",unitType="MWT")
	private String weight;
	private Measure weightMeasure;
	
	private String selectedcont;
	private String istransfer;
	private String fromScreen;
	//Added by A-4810 as part of bug-icrd-9151.
	private String pieceavailable;
	private String weightavailable;
	/**
	 * ICRD-26699_A-4816 : Remaining no:of pieces to be transferred
	 */
	private String remainingPcs;
	/**
	 * ICRD-26699_A-4816 : Remaining weight to be transferred
	 */
	@MeasureAnnotation(mappedValue="remainingWtMeasure",unitType="MWT")
	private String remainingWt;
	private Measure remainingWtMeasure;
	private String remarks;
   
	/**
	 * 
	 * @return remainingWtMeasure
	 */
	public Measure getRemainingWtMeasure() {
		return remainingWtMeasure;
	}
   /**
    * 
    * @param remainingWtMeasure
    */
	public void setRemainingWtMeasure(Measure remainingWtMeasure) {
		this.remainingWtMeasure = remainingWtMeasure;
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
	 * @return the weightMeasure
	 */
	public Measure getWeightMeasure() {
		return weightMeasure;
	}

	/**
	 * @param weightMeasure the weightMeasure to set
	 */
	public void setWeightMeasure(Measure weightMeasure) {
		this.weightMeasure = weightMeasure;
	}

	/**
	 * @return Returns the flightDate.
	 */
	public String getFlightDate() {
		return flightDate;
	}
	/**
	 * @param flightDate The flightDate to set.
	 */
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}
	/**
	 * @return Returns the FlightPou.
	 */

	public String getFlightPou() {
		return flightPou;
	}
	/**
	 * @param flightPou The flightPou to set.
	 */
	public void setFlightPou(String flightPou) {
		this.flightPou = flightPou;
	}
	/**
	 * @return Returns the pieces.
	 */
	public String getPieces() {
		return pieces;
	}
	/**
	 * @param pieces The pieces to set.
	 */
	public void setPieces(String pieces) {
		this.pieces = pieces;
	}
	/**
	 * @return Returns the weight.
	 */
	public String getWeight() {
		return weight;
	}
	/**
	 * @param weight The weight to set.
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}
	/**
	 * @return Returns the selectedcont.
	 */
	public String getSelectedcont() {
		return selectedcont;
	}
	/**
	 * @param selectedcont The selectedcont to set.
	 */
	public void setSelectedcont(String selectedcont) {
		this.selectedcont = selectedcont;
	}
	/**
	 * @return Returns the istransfer.
	 */
	public String getIstransfer() {
		return istransfer;
	}
	/**
	 * @param istransfer The istransfer to set.
	 */
	public void setIstransfer(String istransfer) {
		this.istransfer = istransfer;
	}

	public String getFromScreen() {
		return fromScreen;
	}

	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
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
	public String getPieceavailable() {
		return pieceavailable;
	}
	public void setPieceavailable(String pieceavailable) {
		this.pieceavailable = pieceavailable;
	}
	public String getWeightavailable() {
		return weightavailable;
	}
	public void setWeightavailable(String weightavailable) {
		this.weightavailable = weightavailable;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the remainingPcs
	 */
	public String getRemainingPcs() {
		return remainingPcs;
	}

	/**
	 * @param remainingPcs the remainingPcs to set
	 */
	public void setRemainingPcs(String remainingPcs) {
		this.remainingPcs = remainingPcs;
	}

	/**
	 * @return the remainingWt
	 */
	public String getRemainingWt() {
		return remainingWt;
	}

	/**
	 * @param remainingWt the remainingWt to set
	 */
	public void setRemainingWt(String remainingWt) {
		this.remainingWt = remainingWt;
	}



	

}

