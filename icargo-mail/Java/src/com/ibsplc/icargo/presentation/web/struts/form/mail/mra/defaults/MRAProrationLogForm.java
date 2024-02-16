	/*
	 *  MRAProrationLogForm.java Created on Sep 17, 2008
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
	package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

	import com.ibsplc.icargo.framework.model.ScreenModel;


	/**
	 * @author A-3229
	 *
	 */
	public class MRAProrationLogForm extends ScreenModel {
		
		
		private static final String BUNDLE = "mraprorationlogresources";
		private static final String PRODUCT = "mail";
		private static final String SUBPRODUCT = "mra.defaults";
		private static final String SCREENID = "mailtracking.mra.defaults.prorationlog";
		
		private String dsn;
		
		//Pk fields
		
		private String companyCode;
		
		private String billingBasis;
		
		private String csgDocumentNumber;
		
		private String poaCode;
		
		private String csgSequenceNumber;
		
		private String serialNumber;
		
		private String versionNumber;
		
		
		private String[] versionNo;
		
		private String[] carrierCode;
		
		private String[] carriageFrom;
		
		private String[] carriageTo;
		
		private String[] triggerPoint;
		
		private String[] dateTime;
		
		private String[] user;
		
		private String[] remarks;
		
		//For checkbox
		
		private String[] checkBoxLog;
		
		//Hidden field for view proration
		
		private String prorateFlag;
 
		private String fromScreen;
		
		private String showDsnPopUp;
		

		/**
		 * @return Returns the SCREENID.
		 */
	    public String getScreenId() {
	        return SCREENID;
	    }

	    /**
		 * @return Returns the PRODUCT.
		 */
	    public String getProduct() {
	        return PRODUCT;
	    }
	    /**
		 * @return Returns the SUBPRODUCT.
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
		

		/**
		 * @return Returns the dsn
		 */

		public String getDsn() {
			return dsn;
		}
		/**
		 * @param dsn the dsn to set.
		 */

		public void setDsn(String dsn) {
			this.dsn = dsn;
		}



		/**
		 * @return Returns the companyCode
		 */
		public String getCompanyCode() {
			return companyCode;
		}

		/**
		 * @param companyCode the companyCode to set.
		 */
		public void setCompanyCode(String companyCode) {
			this.companyCode = companyCode;
		}

		/**
		 * @return Returns the billingBasis
		 */
		public String getBillingBasis() {
			return billingBasis;
		}

		/**
		 * @param billingBasis the billingBasis to set.
		 */
		public void setBillingBasis(String billingBasis) {
			this.billingBasis = billingBasis;
		}

		/**
		 * @return Returns the csgDocumentNumber
		 */
		public String getCsgDocumentNumber() {
			return csgDocumentNumber;
		}
		/**
		 * @param csgDocumentNumber the csgDocumentNumber to set.
		 */

		public void setCsgDocumentNumber(String csgDocumentNumber) {
			this.csgDocumentNumber = csgDocumentNumber;
		}
		/**
		 * @return Returns the csgSequenceNumber
		 */

		public String getCsgSequenceNumber() {
			return csgSequenceNumber;
		}
		/**
		 * @param csgSequenceNumber the csgSequenceNumber to set.
		 */
		public void setCsgSequenceNumber(String csgSequenceNumber) {
			this.csgSequenceNumber = csgSequenceNumber;
		}

		/**
		 * @return Returns the poaCode
		 */
		public String getPoaCode() {
			return poaCode;
		}

		/**
		 * @param poaCode the poaCode to set.
		 */
		public void setPoaCode(String poaCode) {
			this.poaCode = poaCode;
		}

		/**
		 * @return Returns the carriageFrom
		 */
		public String[] getCarriageFrom() {
			return carriageFrom;
		}

		/**
		 * @param carriageFrom the carriageFrom to set.
		 */
		public void setCarriageFrom(String[] carriageFrom) {
			this.carriageFrom = carriageFrom;
		}

		/**
		 * @return Returns the carriageTo
		 */
		public String[] getCarriageTo() {
			return carriageTo;
		}
		/**
		 * @param carriageTo the carriageTo to set.
		 */

		public void setCarriageTo(String[] carriageTo) {
			this.carriageTo = carriageTo;
		}

		/**
		 * @return Returns the carrierCode
		 */
		public String[] getCarrierCode() {
			return carrierCode;
		}
		/**
		 * @param carrierCode the carrierCode to set.
		 */

		public void setCarrierCode(String[] carrierCode) {
			this.carrierCode = carrierCode;
		}

		/**
		 * @return Returns the checkBoxLog
		 */
		public String[] getCheckBoxLog() {
			return checkBoxLog;
		}

		/**
		 * @param checkBoxLog the checkBoxLog to set.
		 */
		public void setCheckBoxLog(String[] checkBoxLog) {
			this.checkBoxLog = checkBoxLog;
		}

		/**
		 * @return Returns the dateTime
		 */
		public String[] getDateTime() {
			return dateTime;
		}

		/**
		 * @param dateTime the dateTime to set.
		 */
		public void setDateTime(String[] dateTime) {
			this.dateTime = dateTime;
		}

		/**
		 * @return Returns the remarks
		 */
		public String[] getRemarks() {
			return remarks;
		}
		/**
		 * @param remarks the remarks to set.
		 */

		public void setRemarks(String[] remarks) {
			this.remarks = remarks;
		}

		/**
		 * @return Returns the serialNumber
		 */
		public String getSerialNumber() {
			return serialNumber;
		}

		/**
		 * @param serialNumber the serialNumber to set.
		 */
		public void setSerialNumber(String serialNumber) {
			this.serialNumber = serialNumber;
		}

		/**
		 * @return Returns the triggerPoint
		 */
		public String[] getTriggerPoint() {
			return triggerPoint;
		}

		/**
		 * @param triggerPoint the triggerPoint to set.
		 */
		public void setTriggerPoint(String[] triggerPoint) {
			this.triggerPoint = triggerPoint;
		}

		/**
		 * @return Returns the user
		 */
		public String[] getUser() {
			return user;
		}

		/**
		 * @param user the user to set.
		 */
		public void setUser(String[] user) {
			this.user = user;
		}

		/**
		 * @return Returns the versionNo
		 */
		public String[] getVersionNo() {
			return versionNo;
		}

		/**
		 * @param versionNo the versionNo to set.
		 */
		public void setVersionNo(String[] versionNo) {
			this.versionNo = versionNo;
		}

		/**
		 * @return Returns the versionNumber
		 */
		public String getVersionNumber() {
			return versionNumber;
		}

		/**
		 * @param versionNumber the versionNumber to set.
		 */
		public void setVersionNumber(String versionNumber) {
			this.versionNumber = versionNumber;
		}

		/**
		 * @return Returns the prorateFlag
		 */
		public String getProrateFlag() {
			return prorateFlag;
		}

		/**
		 * @param prorateFlag the prorateFlag to set.
		 */
		public void setProrateFlag(String prorateFlag) {
			this.prorateFlag = prorateFlag;
		}
		
		/**
		 * @return Returns the fromScreen
		 */
		public String getFromScreen() {
			return fromScreen;
		}
		/**
		 * @param fromScreen the fromScreen to set.
		 */

		public void setFromScreen(String fromScreen) {
			this.fromScreen = fromScreen;
		}

		/**
		 * @return the showDsnPopUp
		 */
		public String getShowDsnPopUp() {
			return showDsnPopUp;
		}

		/**
		 * @param showDsnPopUp the showDsnPopUp to set
		 */
		public void setShowDsnPopUp(String showDsnPopUp) {
			this.showDsnPopUp = showDsnPopUp;
		}

		
		

	}


