/*
 * RateAuditDetailsForm.java Created on JUL 17,2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;


/**
 * 
 * @author A-2391
 *
 */


public class RateAuditDetailsForm extends ScreenModel {
	
	   private static final String BUNDLE = "rateAuditDetails";

	    private String bundle;

	    private static final String PRODUCT = "mail";

	    private static final String SUBPRODUCT = "mra";

	    private static final String SCREEN_ID = "mailtracking.mra.defaults.rateauditdetails";

	    
	    private String dsnNumber;
	    private String dsnDate;
	    private String dsnStatus;
	    private String origin;
	    private String destination;
	    private String gpaCode;
	    private String consignmentDocNo;
	    private String route;
	    private String noOfpcs;
	    private String updWt;
	    private String category;
	    private String subClass;
	    private String ULD;
	    private String flightNo;
	
	    private String rate;
	    private String presentWgtCharge;
	    private String auditWgtCharge;
	    private String discrepancy;
	    private String billTo;
	    private String applyAudit;
	    private String lovFlag;
	    private String flightCarCod;
	    private String listFlag;
	    private String blgBasis;
	    
	    private String rateval;
	    private String validateFrom;
	    
	    private int selectedRowIndex;
	    private String isFromListRateAuditScreen;
	    
	    private String fromScreen;
	    private String ShowDsnPopUp;
	    

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
		 * @return Returns the listFlag.
		 */
		public String getListFlag() {
			return listFlag;
		}

		/**
		 * @param listFlag The listFlag to set.
		 */
		public void setListFlag(String listFlag) {
			this.listFlag = listFlag;
		}
		/**
		 * 
		 */
		public String getScreenId() {
			// TODO Auto-generated method stub
			return SCREEN_ID;
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
 * 
 */
		public String getBundle() {
			return BUNDLE;
		}
/**
 * 
 * @return
 */
		public String getConsignmentDocNo() {
			return consignmentDocNo;
		}
		/**
		 * @param consignmentDocNo The listFlag to set.
		 */
		public void setConsignmentDocNo(String consignmentDocNo) {
			this.consignmentDocNo = consignmentDocNo;
		}
/**
 * 
 * @return
 */
		public String getDestination() {
			return destination;
		}
		/**
		 * @param destination The listFlag to set.
		 */
		public void setDestination(String destination) {
			this.destination = destination;
		}
/**
 * 
 * @return
 */
		public String getDsnDate() {
			return dsnDate;
		}
		/**
		 * @param dsnDate The listFlag to set.
		 */
		public void setDsnDate(String dsnDate) {
			this.dsnDate = dsnDate;
		}
/**
 * 
 * @return
 */
		public String getDsnNumber() {
			return dsnNumber;
		}
		/**
		 * @param dsnNumber The listFlag to set.
		 */
		public void setDsnNumber(String dsnNumber) {
			this.dsnNumber = dsnNumber;
		}
/**
 * 
 * @return
 */
		public String getDsnStatus() {
			return dsnStatus;
		}
		/**
		 * @param dsnStatus The listFlag to set.
		 */
		public void setDsnStatus(String dsnStatus) {
			this.dsnStatus = dsnStatus;
		}
/**
 * 
 * @return
 */
		public String getGpaCode() {
			return gpaCode;
		}
		/**
		 * @param gpaCode The listFlag to set.
		 */
		public void setGpaCode(String gpaCode) {
			this.gpaCode = gpaCode;
		}
		/**
		 * 
		 * @return
		 */
		public String getOrigin() {
			return origin;
		}
		/**
		 * @param origin The listFlag to set.
		 */
		public void setOrigin(String origin) {
			this.origin = origin;
		}
/**
 * 
 * @return
 */
		public String getRoute() {
			return route;
		}
		/**
		 * @param route The listFlag to set.
		 */
		public void setRoute(String route) {
			this.route = route;
		}
		/**
		 * 
		 * @return
		 */
		public String getApplyAudit() {
			return applyAudit;
		}
		/**
		 * @param applyAudit The listFlag to set.
		 */
		public void setApplyAudit(String applyAudit) {
			this.applyAudit = applyAudit;
		}
		/**
		 * 
		 * @return
		 */
		public String getAuditWgtCharge() {
			return auditWgtCharge;
		}
		/**
		 * @param auditWgtCharge The listFlag to set.
		 */
		public void setAuditWgtCharge(String auditWgtCharge) {
			this.auditWgtCharge = auditWgtCharge;
		}
		/**
		 * 
		 * @return
		 */
		public String getBillTo() {
			return billTo;
		}
		/**
		 * @param billTo The listFlag to set.
		 */
		public void setBillTo(String billTo) {
			this.billTo = billTo;
		}
		/**
		 * 
		 * @return
		 */
		public String getCategory() {
			return category;
		}
		/**
		 * @param category The listFlag to set.
		 */
		public void setCategory(String category) {
			this.category = category;
		}
		/**
		 * 
		 * @return
		 */
		public String getDiscrepancy() {
			return discrepancy;
		}
		/**
		 * @param discrepancy The listFlag to set.
		 */
		public void setDiscrepancy(String discrepancy) {
			this.discrepancy = discrepancy;
		}
		/**
		 * 
		 * @return
		 */
		public String getFlightNo() {
			return flightNo;
		}
		/**
		 * @param flightNo The listFlag to set.
		 */
		public void setFlightNo(String flightNo) {
			this.flightNo = flightNo;
		}
		/**
		 * 
		 * @return
		 */
		public String getUpdWt() {
			return updWt;
		}
		/**
		 * @param updWt The listFlag to set.
		 */
		public void setUpdWt(String updWt) {
			this.updWt = updWt;
		}
		/**
		 * 
		 * @return
		 */
		public String getNoOfpcs() {
			return noOfpcs;
		}
		/**
		 * @param noOfpcs The listFlag to set.
		 */
		public void setNoOfpcs(String noOfpcs) {
			this.noOfpcs = noOfpcs;
		}
		/**
		 * 
		 * @return
		 */
		public String getPresentWgtCharge() {
			return presentWgtCharge;
		}
		/**
		 * @param presentWgtCharge The listFlag to set.
		 */
		public void setPresentWgtCharge(String presentWgtCharge) {
			this.presentWgtCharge = presentWgtCharge;
		}
		/**
		 * 
		 * @return
		 */
		public String getRate() {
			return rate;
		}
		/**
		 * @param rate The listFlag to set.
		 */
		public void setRate(String rate) {
			this.rate = rate;
		}
		/**
		 * 
		 * @return
		 */
		public String getSubClass() {
			return subClass;
		}
		/**
		 * @param subClass The listFlag to set.
		 */
		public void setSubClass(String subClass) {
			this.subClass = subClass;
		}
		/**
		 * 
		 * @return
		 */
		public String getULD() {
			return ULD;
		}
		/**
		 * @param uld The listFlag to set.
		 */
		public void setULD(String uld) {
			ULD = uld;
		}
		/**
		 * @param bundle The listFlag to set.
		 */
		public void setBundle(String bundle) {
			this.bundle = bundle;
		}

		/**
		 * 
		 * @return
		 */
		public String getLovFlag() {
			return lovFlag;
		}
		/**
		 * @param lovFlag The listFlag to set.
		 */
		public void setLovFlag(String lovFlag) {
			this.lovFlag = lovFlag;
		}

		/**
		 * @return Returns the flightCarCod.
		 */
		public String getFlightCarCod() {
			return flightCarCod;
		}

		/**
		 * @param flightCarCod The flightCarCod to set.
		 */
		public void setFlightCarCod(String flightCarCod) {
			this.flightCarCod = flightCarCod;
		}

		public String getIsFromListRateAuditScreen() {
			return isFromListRateAuditScreen;
		}

		public void setIsFromListRateAuditScreen(String isFromListRateAuditScreen) {
			this.isFromListRateAuditScreen = isFromListRateAuditScreen;
		}

		public int getSelectedRowIndex() {
			return selectedRowIndex;
		}

		public void setSelectedRowIndex(int selectedRowIndex) {
			this.selectedRowIndex = selectedRowIndex;
		}

		/**
		 * @return the rateval
		 */
		public String getRateval() {
			return rateval;
		}

		/**
		 * @param rateval the rateval to set
		 */
		public void setRateval(String rateval) {
			this.rateval = rateval;
		}

		/**
		 * @return the validateFrom
		 */
		public String getValidateFrom() {
			return validateFrom;
		}

		/**
		 * @param validateFrom the validateFrom to set
		 */
		public void setValidateFrom(String validateFrom) {
			this.validateFrom = validateFrom;
		}

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
		 * @return the showDsnPopUp
		 */
		public String getShowDsnPopUp() {
			return ShowDsnPopUp;
		}

		/**
		 * @param showDsnPopUp the showDsnPopUp to set
		 */
		public void setShowDsnPopUp(String showDsnPopUp) {
			ShowDsnPopUp = showDsnPopUp;
		}

		
		
	
	

}
