/*
 * ListFeedbackForm.java Created on Jun 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.products.defaults;


import com.ibsplc.icargo.business.products.defaults.vo.ProductFeedbackVO;
import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


/**
 * @author A-1754
 *
 */
public class ListFeedbackForm extends ScreenModel {
	private String productCode="";
	private String productName="";
	private String fromDate="";
	private String toDate="";
	private String[] checkbox=null;
	private String lastPageNumber="0";
	private String displayPage="1";
	private Page<ProductFeedbackVO> pageProductFeedback=null;
	private static final String BUNDLE = "listfeedbackResources"; // The key attribute specified in struts_config.xml file.
	
	private String countTotalFlag=""; //added by A-5201 for CR ICRD-22065

		private String bundle;


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
	 * The overiden function to return the screen id
	 * @return String
	 */
		public String getScreenId() {
			return "products.defaults.feeback";
		}
	/**
	 * The overriden function to return the product name
	 *  @return String
	 */
		public String getProduct() {
			return "products";
		}
		/**
		 * The overriden function to return the sub product name
		 *  @return String
		 */
		public String getSubProduct() {
			return "defaults";
		}
		/**
		 * @return Returns the checkbox.
		 */
		public String[] getCheckbox() {
			return checkbox;
		}
		/**
		 * @param checkbox The checkbox to set.
		 */
		public void setCheckbox(String[] checkbox) {
			this.checkbox = checkbox;
		}
		/**
		 * @return Returns the displayPage.
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
		 * @return Returns the fromDate.
		 */
		@DateFieldId(id="ProductFeedbackDateRange",fieldType="from")//Added By T-1925 for ICRD-9704
		public String getFromDate() {
			return fromDate;
		}
		/**
		 * @param fromDate The fromDate to set.
		 */
		public void setFromDate(String fromDate) {
			this.fromDate = fromDate;
		}
		/**
		 * @return Returns the lastPageNumber.
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
		 * @return Returns the productName.
		 */
		public String getProductName() {
			return productName;
		}
		/**
		 * @param productName The productName to set.
		 */
		public void setProductName(String productName) {
			this.productName = productName;
		}
		/**
		 * @return Returns the toDate.
		 */
		@DateFieldId(id="ProductFeedbackDateRange",fieldType="to")//Added By T-1925 for ICRD-9704
		public String getToDate() {
			return toDate;
		}
		/**
		 * @param toDate The toDate to set.
		 */
		public void setToDate(String toDate) {
			this.toDate = toDate;
		}
		/**
		 * @return Returns the pageProductFeedback.
		 */
		public Page<ProductFeedbackVO> getPageProductFeedback() {
			return pageProductFeedback;
		}
		/**
		 * @param pageProductFeedback The pageProductFeedback to set.
		 */
		public void setPageProductFeedback(Page<ProductFeedbackVO> pageProductFeedback) {
			this.pageProductFeedback = pageProductFeedback;
		}
		/**
		 * @return Returns the productCode.
		 */
		public String getProductCode() {
			return productCode;
		}
		/**
		 * @param productCode The productCode to set.
		 */
		public void setProductCode(String productCode) {
			this.productCode = productCode;
		}
		
		//added by A-5201 for CR ICRD-22065 starts
		public String getCountTotalFlag() {
			return countTotalFlag;
		}

		public void setCountTotalFlag(String countTotalFlag) {
			this.countTotalFlag = countTotalFlag;
		}
		//added by A-5201 for CR ICRD-22065 end
	
}
