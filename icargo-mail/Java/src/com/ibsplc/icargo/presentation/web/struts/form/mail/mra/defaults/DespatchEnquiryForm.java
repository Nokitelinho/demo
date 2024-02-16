/*
 * DespatchEnquiryForm.java Created on Jun 25,2008
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
public class DespatchEnquiryForm extends ScreenModel{

	

		private static final String BUNDLE = "despatchEnquiry";

			//private String bundle;

			private static final String PRODUCT = "mail";

			private static final String SUBPRODUCT = "mra";

			private static final String SCREENID =
				"mailtracking.mra.defaults.despatchenquiry";

			private String despatchNum;
			private String dsnFilterDate;
			private String conDocNum;
			private String origin;
			private String dstn;
			private String category;
			private String desClass;
			private String subClass;
			private String stdPcs;
			private String stdWt;
			private String despatchDate;
			private String route;
			private String despatchEnqTyp;
			private String gpaCode;
			private String gpaName;
			private String currency;
			private String finYear;
			private String jvNum;
			private String remarks;
		
		
			private String lovClicked;
			private String blgBasis;
			private String listed;
			
//			added for AirNZ846
			
			private String fromGPABillingInvoiceEnquiry;
			private String counter;
			

//			Variable for checking from which screen this Form One screen is coming 
			
			// Added by A-2107 for the integration with Unaccounted Dispatches
			private String closeFlag;
			private String fromScreen;
			private String selectedDispatch;
		
			//Added for USPS Reporting panel
			
			private String paCode;
			private String paName;
			private String country;
			private String financialYear;
			private String billingCurrency;
			
			//for pagination
			private String absIndex="";
			private String pageNum="";
			private String lastPageNumber="";
			private String displayPage="1";
			
			private String showDsnPopUp;

			
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

			/**
			 * @return the selectedDispatch
			 */
			public String getSelectedDispatch() {
				return selectedDispatch;
			}

			/**
			 * @param selectedDispatch the selectedDispatch to set
			 */
			public void setSelectedDispatch(String selectedDispatch) {
				this.selectedDispatch = selectedDispatch;
			}

			/**
			 * @return the closeFlag
			 */
			public String getCloseFlag() {
				return closeFlag;
			}

			/**
			 * @param closeFlag the closeFlag to set
			 */
			public void setCloseFlag(String closeFlag) {
				this.closeFlag = closeFlag;
			}

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
			 * @return Returns the dsnFilterDate.
			 */
			public String getDsnFilterDate() {
				return dsnFilterDate;
			}

			/**
			 * @param dsnFilterDate The dsnFilterDate to set.
			 */
			public void setDsnFilterDate(String dsnFilterDate) {
				this.dsnFilterDate = dsnFilterDate;
			}

			/**
			 * @return Returns the listed.
			 */
			public String getListed() {
				return listed;
			}

			/**
			 * @param listed The listed to set.
			 */
			public void setListed(String listed) {
				this.listed = listed;
			}

			/**
			 * @return Returns the lovClicked.
			 */
			public String getLovClicked() {
				return lovClicked;
			}

			/**
			 * @param lovClicked The lovClicked to set.
			 */
			public void setLovClicked(String lovClicked) {
				this.lovClicked = lovClicked;
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
			 * @return Returns the conDocNum.
			 */
			public String getConDocNum() {
				return conDocNum;
			}

			/**
			 * @param conDocNum The conDocNum to set.
			 */
			public void setConDocNum(String conDocNum) {
				this.conDocNum = conDocNum;
			}

			/**
			 * @return Returns the currency.
			 */
			public String getCurrency() {
				return currency;
			}

			/**
			 * @param currency The currency to set.
			 */
			public void setCurrency(String currency) {
				this.currency = currency;
			}

			/**
			 * @return Returns the desClass.
			 */
			public String getDesClass() {
				return desClass;
			}

			/**
			 * @param desClass The desClass to set.
			 */
			public void setDesClass(String desClass) {
				this.desClass = desClass;
			}

			/**
			 * @return Returns the despatchDate.
			 */
			public String getDespatchDate() {
				return despatchDate;
			}

			/**
			 * @param despatchDate The despatchDate to set.
			 */
			public void setDespatchDate(String despatchDate) {
				this.despatchDate = despatchDate;
			}

			/**
			 * @return Returns the despatchEnqTyp.
			 */
			public String getDespatchEnqTyp() {
				return despatchEnqTyp;
			}

			/**
			 * @param despatchEnqTyp The despatchEnqTyp to set.
			 */
			public void setDespatchEnqTyp(String despatchEnqTyp) {
				this.despatchEnqTyp = despatchEnqTyp;
			}

			/**
			 * @return Returns the dstn.
			 */
			public String getDstn() {
				return dstn;
			}

			/**
			 * @param dstn The dstn to set.
			 */
			public void setDstn(String dstn) {
				this.dstn = dstn;
			}

			/**
			 * @return Returns the finYear.
			 */
			public String getFinYear() {
				return finYear;
			}

			/**
			 * @param finYear The finYear to set.
			 */
			public void setFinYear(String finYear) {
				this.finYear = finYear;
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
			 * @return Returns the jvNum.
			 */
			public String getJvNum() {
				return jvNum;
			}

			/**
			 * @param jvNum The jvNum to set.
			 */
			public void setJvNum(String jvNum) {
				this.jvNum = jvNum;
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
			 * @return Returns the remarks.
			 */
			public String getRemarks() {
				return remarks;
			}

			/**
			 * @param remarks The remarks to set.
			 */
			public void setRemarks(String remarks) {
				this.remarks = remarks;
			}

			/**
			 * @return Returns the route.
			 */
			public String getRoute() {
				return route;
			}

			/**
			 * @param route The route to set.
			 */
			public void setRoute(String route) {
				this.route = route;
			}

			/**
			 * @return Returns the stdPcs.
			 */
			public String getStdPcs() {
				return stdPcs;
			}

			/**
			 * @param stdPcs The stdPcs to set.
			 */
			public void setStdPcs(String stdPcs) {
				this.stdPcs = stdPcs;
			}

			/**
			 * @return Returns the stdWt.
			 */
			public String getStdWt() {
				return stdWt;
			}

			/**
			 * @param stdWt The stdWt to set.
			 */
			public void setStdWt(String stdWt) {
				this.stdWt = stdWt;
			}

			/**
			 * @return Returns the subClass.
			 */
			public String getSubClass() {
				return subClass;
			}

			/**
			 * @param subClass The subClass to set.
			 */
			public void setSubClass(String subClass) {
				this.subClass = subClass;
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
			 * @return Returns the despatchNum.
			 */
			public String getDespatchNum() {
				return despatchNum;
			}

			/**
			 * @param despatchNum The despatchNum to set.
			 */
			public void setDespatchNum(String despatchNum) {
				this.despatchNum = despatchNum;
			}

			/**
			 * @return Returns the fromGPABillingInvoiceEnquiry.
			 */
			public String getFromGPABillingInvoiceEnquiry() {
				return fromGPABillingInvoiceEnquiry;
			}
			/**
			 * @param fromGPABillingInvoiceEnquiry The fromGPABillingInvoiceEnquiry to set.
			 */
			public void setFromGPABillingInvoiceEnquiry(String fromGPABillingInvoiceEnquiry) {
				this.fromGPABillingInvoiceEnquiry = fromGPABillingInvoiceEnquiry;
			}
			/**
			 * @return Returns the counter.
			 */
			public String getCounter() {
				return counter;
			}
			/**
			 * @param counter The counter to set.
			 */
			public void setCounter(String counter) {
				this.counter = counter;
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
			 * @return Returns the billingCurrency.
			 */
			public String getBillingCurrency() {
				return billingCurrency;
			}
			/**
			 * @param billingCurrency The billingCurrency to set.
			 */
			public void setBillingCurrency(String billingCurrency) {
				this.billingCurrency = billingCurrency;
			}
			/**
             * @return Returns the country
             */
			public String getCountry() {
				return country;
			}
			/**
			 * @param country The country to set.
			 */
			public void setCountry(String country) {
				this.country = country;
			}
			/**
             * @return Returns the financialYear
             */
			public String getFinancialYear() {
				return financialYear;
			}
			/**
			 * @param financialYear The financialYear to set.
			 */
			public void setFinancialYear(String financialYear) {
				this.financialYear = financialYear;
			}
			/**
             * @return Returns the paCode
             */
			public String getPaCode() {
				return paCode;
			}
			/**
			 * @param paCode The paCode to set.
			 */
			public void setPacode(String paCode) {
				this.paCode = paCode;
			}
            /**
             * @return Returns the paName
             */
			public String getPaName() {
				return paName;
			}
			/**
			 * @param paName The paName to set.
			 */
			public void setPaName(String paName) {
				this.paName = paName;
			}
			 /**
             * @return Returns the absIndex
             */
			public String getAbsIndex() {
				return absIndex;
			}
			/**
			 * @param absIndex The absIndex to set.
			 */
			public void setAbsIndex(String absIndex) {
				this.absIndex = absIndex;
			}
			 /**
             * @return Returns the displayPage
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
             * @return Returns the lastPageNumber
             */
			public String getLastPageNumber() {
				return lastPageNumber;
			}
			/**
			 * @param lastPageNumber The lastPageNumber to set.
			 */
			public void setLastPageNumber(String lastPageNumber) {
				this.lastPageNumber = lastPageNumber;
			}
			 /**
             * @return Returns the pageNum
             */
			public String getPageNum() {
				return pageNum;
			}
			/**
			 * @param pageNum The pageNum to set.
			 */
			public void setPageNum(String pageNum) {
				this.pageNum = pageNum;
			}

		
			

			
			  

			
		}



