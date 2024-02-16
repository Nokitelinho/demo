/*
 * InvoicClaimsEnquiryForm.java Created on Aug 02, 2007
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
 * @author A-2270
 *
 */
public class InvoicClaimsEnquiryForm extends ScreenModel {
			
		private static final String BUNDLE ="invoicClaimsEnquiry";
		
		//private String bundle;
		
		private final static String PRODUCT = "mail";
		
		private final static String SUBPRODUCT = "mra";
		
		private final static String SCREENID = "mailtracking.mra.defaults.invoicclaimsenquiry";

		private String companyCode;
		private String gpaCode;
		private String fromDate;
		private String toDate;
		private String[] rowId;
		private String claimType;
		private String claimStatus;
		private String  lastPageNum = "0";
		private String  displayPage="1";
		private String absoluteIndex = "0";
		
		// Added by A-5183 for < ICRD-21098 > Starts
		
		public static final String PAGINATION_MODE_FROM_LIST = "LIST";
		public static final String PAGINATION_MODE_FROM_NAVIGATION_LINK = "NAVIGATION";
		private String navigationMode;
		
		public String getNavigationMode() {
			return navigationMode;
		}

		public void setNavigationMode(String navigationMode) {
			this.navigationMode = navigationMode;
		}
			
		// Added by A-5183 for < ICRD-21098 > Ends
		
		/**
		 * @return Returns the screenId.
		 */
		public String getScreenId() {
		    return SCREENID;
		}
		
		/**
		 * @return Returns the product.
		 */
		public String getProduct() {
		    return PRODUCT;
		}
		
		/**
		 * @return Returns the subProduct.
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
		 * @return the companyCode
		 */
		public String getCompanyCode() {
			return companyCode;
		}

		/**
		 * @param companyCode the companyCode to set
		 */
		public void setCompanyCode(String companyCode) {
			this.companyCode = companyCode;
		}

		/**
		 * @return the fromDate
		 */
		@DateFieldId(id="INVOICClaimEnquiryDateRange",fieldType="from")//Added By T-1925 for ICRD-9704
		public String getFromDate() {
			return fromDate;
		}

		/**
		 * @param fromDate the fromDate to set
		 */
		public void setFromDate(String fromDate) {
			this.fromDate = fromDate;
		}

		/**
		 * @return the gpaCode
		 */
		public String getGpaCode() {
			return gpaCode;
		}

		/**
		 * @param gpaCode the gpaCode to set
		 */
		public void setGpaCode(String gpaCode) {
			this.gpaCode = gpaCode;
		}

		/**
		 * @return the rowId
		 */
		public String[] getRowId() {
			return rowId;
		}

		/**
		 * @param rowId the rowId to set
		 */
		public void setRowId(String[] rowId) {
			this.rowId = rowId;
		}

		/**
		 * @return the toDate
		 */
		@DateFieldId(id="INVOICClaimEnquiryDateRange",fieldType="to")//Added By T-1925 for ICRD-9704
		public String getToDate() {
			return toDate;
		}

		/**
		 * @param toDate the toDate to set
		 */
		public void setToDate(String toDate) {
			this.toDate = toDate;
		}

		/**
		 * @return the absoluteIndex
		 */
		public String getAbsoluteIndex() {
			return absoluteIndex;
		}

		/**
		 * @param absoluteIndex the absoluteIndex to set
		 */
		public void setAbsoluteIndex(String absoluteIndex) {
			this.absoluteIndex = absoluteIndex;
		}

		/**
		 * @return the displayPage
		 */
		public String getDisplayPage() {
			return displayPage;
		}

		/**
		 * @param displayPage the displayPage to set
		 */
		public void setDisplayPage(String displayPage) {
			this.displayPage = displayPage;
		}

		/**
		 * @return the lastPageNum
		 */
		public String getLastPageNum() {
			return lastPageNum;
		}

		/**
		 * @param lastPageNum the lastPageNum to set
		 */
		public void setLastPageNum(String lastPageNum) {
			this.lastPageNum = lastPageNum;
		}

		/**
		 * @return the claimStatus
		 */
		public String getClaimStatus() {
			return claimStatus;
		}

		/**
		 * @param claimStatus the claimStatus to set
		 */
		public void setClaimStatus(String claimStatus) {
			this.claimStatus = claimStatus;
		}

		/**
		 * @return the claimType
		 */
		public String getClaimType() {
			return claimType;
		}

		/**
		 * @param claimType the claimType to set
		 */
		public void setClaimType(String claimType) {
			this.claimType = claimType;
		}




}
