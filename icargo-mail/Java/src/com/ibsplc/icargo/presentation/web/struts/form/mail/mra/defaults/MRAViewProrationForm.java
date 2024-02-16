/*
 * MRAViewProrationForm.java Created on Aug 08, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;
/**
 * 
 * @author A-2554
 *
 */
public class MRAViewProrationForm extends ScreenModel {
	
	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID = "mailtracking.mra.defaults.viewproration";
	private static final String BUNDLE = "mailproration";
	 private String dispatch;
	 private String conDocNo;
	 private String carrierCode;
	 private String flightNo;
	 private String flightDate;
	 private String origin;
	 private String dest;	
	 private String totWt;
	 private String gpa;
	 private String gpaName;
	 private String category;
	 private String subClass;
	 
	    private String totalInUsdForPri;
		private String totalInBasForPri;
		private String totalInSdrForPri;
		private String totalInCurForPri;		
		private String totalInUsdForSec;
		private String totalInBasForSec;
		private String totalInSdrForSec;
		private String totalInCurForSec;
		private String totalSurchgInUsdForPri;
		private String totalSurchgInBasForPri;
		private String totalSurchgInSdrForPri;
		private String totalSurchgInCurForPri;		
		private String totalSurchgInUsdForSec;
		private String totalSurchgInBasForSec;
		private String totalSurchgInSdrForSec;
		private String totalSurchgInCurForSec;
	 
		//added by A-7371 as part of AWM-proration starts
		private String totalAWMProrationAmtInUsd;
		private String totalAWMProrationAmtInSdr;
		private String totalAWMProrationAmtInBaseCurr;
		private String totalAWMProrationAmtInCtrCur;
		
		private String totalAWMSurProrationAmtInUsd;
		private String totalAWMSurProrationAmtInSdr;
		private String totalAWMSurProrationAmtInBaseCurr;
		private String totalAWMSurProrationAmtInCtrCur;
		private String contractCurrency;
		//added by A-7371 as part of AWM-proration ends
		
		
	 
	 //For AirNZ CR882
	 private String fromScreen;
	 private String fromAction;
	 private String dsn;
	 
		private String sector;
		private String totalSurchgInUsd;
		private String totalSurchgInBas;
		private String totalSurchgInSdr;
		private String totalSurchgInCur;
		private String action;
		private String formStatusFlag;
		//Added by A-7794 as part of sanity testing
		private String overrideRounding;
		private String awmSpecificFlag;
		private String currencyChangeFlag;
		
	
		private String displayWeightUnit;
	 /**
	 * @return the rsn
	 */
	public String getRsn() {
		return rsn;
	}

	/**
	 * @param rsn the rsn to set
	 */
	public void setRsn(String rsn) {
		this.rsn = rsn;
	}

	private String rsn;
	 private String dsnDate;
	 private String ShowDsnPopUp;
	 private String baseCurrency;
	 
	 private String mailbagID;
	 private String parameterValue;
		/**
		 * @return the mailbagID
		 */
		public String getMailbagID() {
			return mailbagID;
		}

		/**
		 * @param mailbagID the mailbagID to set
		 */
		public void setMailbagID(String mailbagID) {
			this.mailbagID = mailbagID;
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
	 
	 
	 /**
	 * @return the baseCurrency
	 */
	public String getBaseCurrency() {
		return baseCurrency;
	}

	/**
	 * @param baseCurrency the baseCurrency to set
	 */
	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	/** (non-Javadoc)
	     * @return SCREENID  String
	     */
	    public String getScreenId() {
	        return SCREENID;
	    }

	    /** (non-Javadoc)
	     * @return PRODUCT  String
	     */
	    public String getProduct() {
	        return PRODUCT;
	    }

	    /** (non-Javadoc)
	     * @return SUBPRODUCT  String
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

	

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public String getConDocNo() {
			return conDocNo;
		}

		public void setConDocNo(String conDocNo) {
			this.conDocNo = conDocNo;
		}

		public String getDest() {
			return dest;
		}

		public void setDest(String dest) {
			this.dest = dest;
		}

		public String getDispatch() {
			return dispatch;
		}

		public void setDispatch(String dispatch) {
			this.dispatch = dispatch;
		}

		public String getFlightDate() {
			return flightDate;
		}

		public void setFlightDate(String flightDate) {
			this.flightDate = flightDate;
		}

		public String getFlightNo() {
			return flightNo;
		}

		public void setFlightNo(String flightNo) {
			this.flightNo = flightNo;
		}

		public String getGpa() {
			return gpa;
		}

		public void setGpa(String gpa) {
			this.gpa = gpa;
		}

		public String getGpaName() {
			return gpaName;
		}

		public void setGpaName(String gpaName) {
			this.gpaName = gpaName;
		}

		public String getOrigin() {
			return origin;
		}

		public void setOrigin(String origin) {
			this.origin = origin;
		}

		public String getSubClass() {
			return subClass;
		}

		public void setSubClass(String subClass) {
			this.subClass = subClass;
		}

		
		public String getTotWt() {
			return totWt;
		}

		public void setTotWt(String totWt) {
			this.totWt = totWt;
		}

		public String getCarrierCode() {
			return carrierCode;
		}

		public void setCarrierCode(String carrierCode) {
			this.carrierCode = carrierCode;
		}

		/**
		 * @return Returns the fromScreen
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
		 * @return Returns the dsn
		 */
		public String getDsn() {
			return dsn;
		}
		/**
		 * @param dsn the dsn to set
		 */
		public void setDsn(String dsn) {
			this.dsn = dsn;
		}

		/**
		 * @return the totalInBasForPri
		 */
		public String getTotalInBasForPri() {
			return totalInBasForPri;
		}

		/**
		 * @param totalInBasForPri the totalInBasForPri to set
		 */
		public void setTotalInBasForPri(String totalInBasForPri) {
			this.totalInBasForPri = totalInBasForPri;
		}

		/**
		 * @return the totalInBasForSec
		 */
		public String getTotalInBasForSec() {
			return totalInBasForSec;
		}

		/**
		 * @param totalInBasForSec the totalInBasForSec to set
		 */
		public void setTotalInBasForSec(String totalInBasForSec) {
			this.totalInBasForSec = totalInBasForSec;
		}

		/**
		 * @return the totalInCurForPri
		 */
		public String getTotalInCurForPri() {
			return totalInCurForPri;
		}

		/**
		 * @param totalInCurForPri the totalInCurForPri to set
		 */
		public void setTotalInCurForPri(String totalInCurForPri) {
			this.totalInCurForPri = totalInCurForPri;
		}

		/**
		 * @return the totalInCurForSec
		 */
		public String getTotalInCurForSec() {
			return totalInCurForSec;
		}

		/**
		 * @param totalInCurForSec the totalInCurForSec to set
		 */
		public void setTotalInCurForSec(String totalInCurForSec) {
			this.totalInCurForSec = totalInCurForSec;
		}

		/**
		 * @return the totalInSdrForPri
		 */
		public String getTotalInSdrForPri() {
			return totalInSdrForPri;
		}

		/**
		 * @param totalInSdrForPri the totalInSdrForPri to set
		 */
		public void setTotalInSdrForPri(String totalInSdrForPri) {
			this.totalInSdrForPri = totalInSdrForPri;
		}

		/**
		 * @return the totalInSdrForSec
		 */
		public String getTotalInSdrForSec() {
			return totalInSdrForSec;
		}

		/**
		 * @param totalInSdrForSec the totalInSdrForSec to set
		 */
		public void setTotalInSdrForSec(String totalInSdrForSec) {
			this.totalInSdrForSec = totalInSdrForSec;
		}

		/**
		 * @return the totalInUsdForPri
		 */
		public String getTotalInUsdForPri() {
			return totalInUsdForPri;
		}

		/**
		 * @param totalInUsdForPri the totalInUsdForPri to set
		 */
		public void setTotalInUsdForPri(String totalInUsdForPri) {
			this.totalInUsdForPri = totalInUsdForPri;
		}

		/**
		 * @return the totalInUsdForSec
		 */
		public String getTotalInUsdForSec() {
			return totalInUsdForSec;
		}

		/**
		 * @param totalInUsdForSec the totalInUsdForSec to set
		 */
		public void setTotalInUsdForSec(String totalInUsdForSec) {
			this.totalInUsdForSec = totalInUsdForSec;
		}

		/**
		 * @return the dsnDate
		 */
		public String getDsnDate() {
			return dsnDate;
		}

		/**
		 * @param dsnDate the dsnDate to set
		 */
		public void setDsnDate(String dsnDate) {
			this.dsnDate = dsnDate;
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

		/**
		 * @return the totalSurchgInUsdForPri
		 */
		public String getTotalSurchgInUsdForPri() {
			return totalSurchgInUsdForPri;
		}

		/**
		 * @param totalSurchgInUsdForPri the totalSurchgInUsdForPri to set
		 */
		public void setTotalSurchgInUsdForPri(String totalSurchgInUsdForPri) {
			this.totalSurchgInUsdForPri = totalSurchgInUsdForPri;
		}

		/**
		 * @return the totalSurchgInBasForPri
		 */
		public String getTotalSurchgInBasForPri() {
			return totalSurchgInBasForPri;
		}

		/**
		 * @param totalSurchgInBasForPri the totalSurchgInBasForPri to set
		 */
		public void setTotalSurchgInBasForPri(String totalSurchgInBasForPri) {
			this.totalSurchgInBasForPri = totalSurchgInBasForPri;
		}

		/**
		 * @return the totalSurchgInSdrForPri
		 */
		public String getTotalSurchgInSdrForPri() {
			return totalSurchgInSdrForPri;
		}

		/**
		 * @param totalSurchgInSdrForPri the totalSurchgInSdrForPri to set
		 */
		public void setTotalSurchgInSdrForPri(String totalSurchgInSdrForPri) {
			this.totalSurchgInSdrForPri = totalSurchgInSdrForPri;
		}

		/**
		 * @return the totalSurchgInCurForPri
		 */
		public String getTotalSurchgInCurForPri() {
			return totalSurchgInCurForPri;
		}

		/**
		 * @param totalSurchgInCurForPri the totalSurchgInCurForPri to set
		 */
		public void setTotalSurchgInCurForPri(String totalSurchgInCurForPri) {
			this.totalSurchgInCurForPri = totalSurchgInCurForPri;
		}

		/**
		 * @return the totalSurchgInUsdForSec
		 */
		public String getTotalSurchgInUsdForSec() {
			return totalSurchgInUsdForSec;
		}

		/**
		 * @param totalSurchgInUsdForSec the totalSurchgInUsdForSec to set
		 */
		public void setTotalSurchgInUsdForSec(String totalSurchgInUsdForSec) {
			this.totalSurchgInUsdForSec = totalSurchgInUsdForSec;
		}

		/**
		 * @return the totalSurchgInBasForSec
		 */
		public String getTotalSurchgInBasForSec() {
			return totalSurchgInBasForSec;
		}

		/**
		 * @param totalSurchgInBasForSec the totalSurchgInBasForSec to set
		 */
		public void setTotalSurchgInBasForSec(String totalSurchgInBasForSec) {
			this.totalSurchgInBasForSec = totalSurchgInBasForSec;
		}

		/**
		 * @return the totalSurchgInSdrForSec
		 */
		public String getTotalSurchgInSdrForSec() {
			return totalSurchgInSdrForSec;
		}

		/**
		 * @param totalSurchgInSdrForSec the totalSurchgInSdrForSec to set
		 */
		public void setTotalSurchgInSdrForSec(String totalSurchgInSdrForSec) {
			this.totalSurchgInSdrForSec = totalSurchgInSdrForSec;
		}

		/**
		 * @return the totalSurchgInCurForSec
		 */
		public String getTotalSurchgInCurForSec() {
			return totalSurchgInCurForSec;
		}

		/**
		 * @param totalSurchgInCurForSec the totalSurchgInCurForSec to set
		 */
		public void setTotalSurchgInCurForSec(String totalSurchgInCurForSec) {
			this.totalSurchgInCurForSec = totalSurchgInCurForSec;
		}

		/**
		 * @return the fromAction
		 */
		public String getFromAction() {
			return fromAction;
		}

		/**
		 * @param fromAction the fromAction to set
		 */
		public void setFromAction(String fromAction) {
			this.fromAction = fromAction;
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

		/**
		 * @return the totalSurchgInUsd
		 */
		public String getTotalSurchgInUsd() {
			return totalSurchgInUsd;
		}

		/**
		 * @param totalSurchgInUsd the totalSurchgInUsd to set
		 */
		public void setTotalSurchgInUsd(String totalSurchgInUsd) {
			this.totalSurchgInUsd = totalSurchgInUsd;
		}

		/**
		 * @return the totalSurchgInBas
		 */
		public String getTotalSurchgInBas() {
			return totalSurchgInBas;
		}

		/**
		 * @param totalSurchgInBas the totalSurchgInBas to set
		 */
		public void setTotalSurchgInBas(String totalSurchgInBas) {
			this.totalSurchgInBas = totalSurchgInBas;
		}

		/**
		 * @return the totalSurchgInSdr
		 */
		public String getTotalSurchgInSdr() {
			return totalSurchgInSdr;
		}

		/**
		 * @param totalSurchgInSdr the totalSurchgInSdr to set
		 */
		public void setTotalSurchgInSdr(String totalSurchgInSdr) {
			this.totalSurchgInSdr = totalSurchgInSdr;
		}

		/**
		 * @return the totalSurchgInCur
		 */
		public String getTotalSurchgInCur() {
			return totalSurchgInCur;
		}

		/**
		 * @param totalSurchgInCur the totalSurchgInCur to set
		 */
		public void setTotalSurchgInCur(String totalSurchgInCur) {
			this.totalSurchgInCur = totalSurchgInCur;
		}

		/**
		 * @return the formStatusFlag
		 */
		public String getFormStatusFlag() {
			return formStatusFlag;
		}

		/**
		 * @param formStatusFlag the formStatusFlag to set
		 */
		public void setFormStatusFlag(String formStatusFlag) {
			this.formStatusFlag = formStatusFlag;
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
		 * 
		 * @return awmSpecificFlag
		 */
		 public String getAwmSpecificFlag() {
				return awmSpecificFlag;
			}
		/**
		 * 
		 * @param awmSpecificFlag the awmSpecificFlag to set
		 */
			public void setAwmSpecificFlag(String awmSpecificFlag) {
				this.awmSpecificFlag = awmSpecificFlag;
			}

		public String getTotalAWMProrationAmtInUsd() {
			return totalAWMProrationAmtInUsd;
		}

		public void setTotalAWMProrationAmtInUsd(String totalAWMProrationAmtInUsd) {
			this.totalAWMProrationAmtInUsd = totalAWMProrationAmtInUsd;
		}

		public String getTotalAWMProrationAmtInSdr() {
			return totalAWMProrationAmtInSdr;
		}

		public void setTotalAWMProrationAmtInSdr(String totalAWMProrationAmtInSdr) {
			this.totalAWMProrationAmtInSdr = totalAWMProrationAmtInSdr;
		}

		public String getTotalAWMProrationAmtInBaseCurr() {
			return totalAWMProrationAmtInBaseCurr;
		}

		public void setTotalAWMProrationAmtInBaseCurr(String totalAWMProrationAmtInBaseCurr) {
			this.totalAWMProrationAmtInBaseCurr = totalAWMProrationAmtInBaseCurr;
		}

		public String getTotalAWMProrationAmtInCtrCur() {
			return totalAWMProrationAmtInCtrCur;
		}

		public void setTotalAWMProrationAmtInCtrCur(String totalAWMProrationAmtInCtrCur) {
			this.totalAWMProrationAmtInCtrCur = totalAWMProrationAmtInCtrCur;
		}

		public String getTotalAWMSurProrationAmtInUsd() {
			return totalAWMSurProrationAmtInUsd;
		}

		public void setTotalAWMSurProrationAmtInUsd(String totalAWMSurProrationAmtInUsd) {
			this.totalAWMSurProrationAmtInUsd = totalAWMSurProrationAmtInUsd;
		}

		public String getTotalAWMSurProrationAmtInSdr() {
			return totalAWMSurProrationAmtInSdr;
		}

		public void setTotalAWMSurProrationAmtInSdr(String totalAWMSurProrationAmtInSdr) {
			this.totalAWMSurProrationAmtInSdr = totalAWMSurProrationAmtInSdr;
		}

		public String getTotalAWMSurProrationAmtInBaseCurr() {
			return totalAWMSurProrationAmtInBaseCurr;
		}

		public void setTotalAWMSurProrationAmtInBaseCurr(String totalAWMSurProrationAmtInBaseCurr) {
			this.totalAWMSurProrationAmtInBaseCurr = totalAWMSurProrationAmtInBaseCurr;
		}

		public String getTotalAWMSurProrationAmtInCtrCur() {
			return totalAWMSurProrationAmtInCtrCur;
		}

		public void setTotalAWMSurProrationAmtInCtrCur(String totalAWMSurProrationAmtInCtrCur) {
			this.totalAWMSurProrationAmtInCtrCur = totalAWMSurProrationAmtInCtrCur;
		}

		public String getContractCurrency() {
			return contractCurrency;
		}

		public void setContractCurrency(String contractCurrency) {
			this.contractCurrency = contractCurrency;
			}

		public String getCurrencyChangeFlag() {
			return currencyChangeFlag;
		}

		public void setCurrencyChangeFlag(String currencyChangeFlag) {
			this.currencyChangeFlag = currencyChangeFlag;
			}
		/**
		 * 	Getter for displayWeightUnit 
		 *	Added by : A-8061 on 03-Dec-2019
		 * 	Used for :
		 */
		public String getDisplayWeightUnit() {
			return displayWeightUnit;
		}

		/**
		 *  @param displayWeightUnit the displayWeightUnit to set
		 * 	Setter for displayWeightUnit 
		 *	Added by : A-8061 on 03-Dec-2019
		 * 	Used for :
		 */
		public void setDisplayWeightUnit(String displayWeightUnit) {
			this.displayWeightUnit = displayWeightUnit;
		}


}
