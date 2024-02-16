/*
 * InvoicEnquiryDetailsForm.java Created on Jul 30, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2270
 *
 */
public class InvoicEnquiryDetailsForm extends ScreenModel {
			
		private static final String BUNDLE ="invoicEnquiryDetails";
		
		//private String bundle;
		
		private final static String PRODUCT = "mail";
		
		private final static String SUBPRODUCT = "mra";
		
		private final static String SCREENID = "mailtracking.mra.defaults.invoicenquirydetails";

		private String companyCode;
		private String invoiceKey;
		private String receptacleIdentifier;
		private String sectorOrigin;
		private String sectorDestination;
		private String[] rowId;
	




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
		 * @return the invoiceKey
		 */
		public String getInvoiceKey() {
			return invoiceKey;
		}

		/**
		 * @param invoiceKey the invoiceKey to set
		 */
		public void setInvoiceKey(String invoiceKey) {
			this.invoiceKey = invoiceKey;
		}

		

		

		/**
		 * @return the receptacleIdentifier
		 */
		public String getReceptacleIdentifier() {
			return receptacleIdentifier;
		}

		/**
		 * @param receptacleIdentifier the receptacleIdentifier to set
		 */
		public void setReceptacleIdentifier(String receptacleIdentifier) {
			this.receptacleIdentifier = receptacleIdentifier;
		}

		/**
		 * @return the sectorDestination
		 */
		public String getSectorDestination() {
			return sectorDestination;
		}

		/**
		 * @param sectorDestination the sectorDestination to set
		 */
		public void setSectorDestination(String sectorDestination) {
			this.sectorDestination = sectorDestination;
		}

		/**
		 * @return the sectorOrigin
		 */
		public String getSectorOrigin() {
			return sectorOrigin;
		}

		/**
		 * @param sectorOrigin the sectorOrigin to set
		 */
		public void setSectorOrigin(String sectorOrigin) {
			this.sectorOrigin = sectorOrigin;
		}

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




}
