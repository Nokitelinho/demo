/** ProductLovForm.java Created on Jun 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ux;

import java.util.Collection;

import com.ibsplc.icargo.business.products.defaults.vo.ProductLovVO;
import com.ibsplc.icargo.framework.model.ScreenModel;

import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * The Class ProductLovForm.
 *
 * @author A-1358
 */
public class ProductLovForm extends ScreenModel {
	
	/** The code. */
	private String code = "";
	private String multiselect;
	/** The product name. */
	private String productName = "";
	private String lovTxtFieldName;
	private String lovDescriptionTxtFieldName;
	/** The start date. */
	private String startDate = "";
	private String selectedValues = "";
	private String lastPageNum = "";
	private String pagination;
	private String defaultPageSize="10";
	private String totalRecordsCount="";
	/**
	 * 	Getter for multiselect 
	 *	Added by : A-4803 on 09-Apr-2014
	 * 	Used for :
	 */
	public String getMultiselect() {
		return multiselect;
	}

	/**
	 *  @param multiselect the multiselect to set
	 * 	Setter for multiselect 
	 *	Added by : A-4803 on 09-Apr-2014
	 * 	Used for :
	 */
	public void setMultiselect(String multiselect) {
		this.multiselect = multiselect;
	}

	/**
	 * 	Getter for lovTxtFieldName 
	 *	Added by : A-4803 on 09-Apr-2014
	 * 	Used for :
	 */
	public String getLovTxtFieldName() {
		return lovTxtFieldName;
	}

	/**
	 *  @param lovTxtFieldName the lovTxtFieldName to set
	 * 	Setter for lovTxtFieldName 
	 *	Added by : A-4803 on 09-Apr-2014
	 * 	Used for :
	 */
	public void setLovTxtFieldName(String lovTxtFieldName) {
		this.lovTxtFieldName = lovTxtFieldName;
	}

	/**
	 * 	Getter for lovDescriptionTxtFieldName 
	 *	Added by : A-4803 on 09-Apr-2014
	 * 	Used for :
	 */
	public String getLovDescriptionTxtFieldName() {
		return lovDescriptionTxtFieldName;
	}

	/**
	 *  @param lovDescriptionTxtFieldName the lovDescriptionTxtFieldName to set
	 * 	Setter for lovDescriptionTxtFieldName 
	 *	Added by : A-4803 on 09-Apr-2014
	 * 	Used for :
	 */
	public void setLovDescriptionTxtFieldName(String lovDescriptionTxtFieldName) {
		this.lovDescriptionTxtFieldName = lovDescriptionTxtFieldName;
	}

	/** The end date. */
	private String endDate = "";

	/** The priority object. */
	private String priorityObject = "";

	/** The transport mode object. */
	private String transportModeObject = "";

	/** The page product lov. */
	private Page<ProductLovVO> pageProductLov = null;

	/** The coll product lov vo. */
	private Collection<ProductLovVO> collProductLovVO = null;

	/** The last page number. */
	private String lastPageNumber = "0";

	/** The display page. */
	private String displayPage = "1";

	/** The product checked. */
	private String[] productChecked;

	/** The next action. */
	private String nextAction;

	/** The product object. */
	private String productObject;

	/** The form number. */
	private String formNumber;

	/** The product code field. */
	private String productCodeField = "";

	/** The source screen. */
	private String sourceScreen = "";

	/** The Constant BUNDLE. */
	private static final String BUNDLE = "ProductLov"; // The key attribute
														// specified in
														// struts_config.xml
														// file.

	/** The bundle. */
	private String bundle;

	/** The active products. */
	private String activeProducts;

	/** The booking date. */
	private String bookingDate;

	/** The row index. */
	private String rowIndex;
	
	/** The invoke function. */
	private String invokeFunction; 

	/** The single select. */
	private String singleSelect;

	/**
	 * Gets the bundle.
	 *
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * Sets the bundle.
	 *
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	/**
	 * Gets the form number.
	 *
	 * @return Returns the formNumber.
	 */
	public String getFormNumber() {
		return formNumber;
	}

	/**
	 * Sets the form number.
	 *
	 * @param formNumber The formNumber to set.
	 */
	public void setFormNumber(String formNumber) {
		this.formNumber = formNumber;
	}

	/**
	 * Gets the product object.
	 *
	 * @return Returns the productObject.
	 */
	public String getProductObject() {
		return productObject;
	}

	/**
	 * Sets the product object.
	 *
	 * @param productObject The productObject to set.
	 */
	public void setProductObject(String productObject) {
		this.productObject = productObject;
	}

	/**
	 * Gets the screen id.
	 *
	 * @return String
	 */
	public String getScreenId() {
		return "UIPDS007";
	}

	/**
	 * Gets the product.
	 *
	 * @return String
	 */
	public String getProduct() {
		return "products";
	}

	/**
	 * Gets the sub product.
	 *
	 * @return String
	 */
	public String getSubProduct() {
		return "defaults";
	}

	/**
	 * Gets the display page.
	 *
	 * @return displayPage
	 */
	public String getDisplayPage() {
		return displayPage;
	}

	/**
	 * Sets the display page.
	 *
	 * @param displayPage the new display page
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	/**
	 * Gets the end date.
	 *
	 * @return endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * Sets the end date.
	 *
	 * @param endDate the new end date
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * Gets the last page number.
	 *
	 * @return lastPageNumber
	 */
	public String getLastPageNumber() {
		return lastPageNumber;
	}

	/**
	 * Sets the last page number.
	 *
	 * @param lastPageNumber the new last page number
	 */
	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}

	/**
	 * Gets the next action.
	 *
	 * @return nextAction
	 */
	public String getNextAction() {
		return nextAction;
	}

	/**
	 * Sets the next action.
	 *
	 * @param nextAction the new next action
	 */
	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}

	/**
	 * Gets the product checked.
	 *
	 * @return productChecked
	 */
	public String[] getProductChecked() {
		return productChecked;
	}

	/**
	 * Sets the product checked.
	 *
	 * @param productChecked the new product checked
	 */
	public void setProductChecked(String[] productChecked) {
		this.productChecked = productChecked;
	}

	/**
	 * Gets the product name.
	 *
	 * @return productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * Sets the product name.
	 *
	 * @param productName the new product name
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * Gets the start date.
	 *
	 * @return startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date.
	 *
	 * @param startDate the new start date
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * Gets the code.
	 *
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the page product lov.
	 *
	 * @return Returns the pageProductLov.
	 */
	public Page<ProductLovVO> getPageProductLov() {
		return pageProductLov;
	}

	/**
	 * Sets the page product lov.
	 *
	 * @param pageProductLov The pageProductLov to set.
	 */
	public void setPageProductLov(Page<ProductLovVO> pageProductLov) {
		this.pageProductLov = pageProductLov;
	}

	/**
	 * Gets the coll product lov vo.
	 *
	 * @return Returns the collProductLovVO.
	 */
	public Collection<ProductLovVO> getCollProductLovVO() {
		return collProductLovVO;
	}

	/**
	 * Sets the coll product lov vo.
	 *
	 * @param collProductLovVO The collProductLovVO to set.
	 */
	public void setCollProductLovVO(Collection<ProductLovVO> collProductLovVO) {
		this.collProductLovVO = collProductLovVO;
	}

	/**
	 * Gets the product code field.
	 *
	 * @return Returns the productCodeField.
	 */
	public String getProductCodeField() {
		return productCodeField;
	}

	/**
	 * Sets the product code field.
	 *
	 * @param productCodeField The productCodeField to set.
	 */
	public void setProductCodeField(String productCodeField) {
		this.productCodeField = productCodeField;
	}

	/**
	 * Gets the source screen.
	 *
	 * @return the source screen
	 */
	public String getSourceScreen() {
		return sourceScreen;
	}

	/**
	 * Sets the source screen.
	 *
	 * @param sourceScreen the new source screen
	 */
	public void setSourceScreen(String sourceScreen) {
		this.sourceScreen = sourceScreen;
	}

	/**
	 * Gets the active products.
	 *
	 * @return Returns the activeProducts.
	 */
	public String getActiveProducts() {
		return activeProducts;
	}

	/**
	 * Sets the active products.
	 *
	 * @param activeProducts The activeProducts to set.
	 */
	public void setActiveProducts(String activeProducts) {
		this.activeProducts = activeProducts;
	}

	/**
	 * Gets the booking date.
	 *
	 * @return Returns the bookingDate.
	 */
	public String getBookingDate() {
		return bookingDate;
	}

	/**
	 * Sets the booking date.
	 *
	 * @param bookingDate The bookingDate to set.
	 */
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	/**
	 * Gets the priority object.
	 *
	 * @return the priority object
	 */
	public String getPriorityObject() {
		return priorityObject;
	}

	/**
	 * Sets the priority object.
	 *
	 * @param priorityObject the new priority object
	 */
	public void setPriorityObject(String priorityObject) {
		this.priorityObject = priorityObject;
	}

	/**
	 * Gets the transport mode object.
	 *
	 * @return the transport mode object
	 */
	public String getTransportModeObject() {
		return transportModeObject;
	}

	/**
	 * Sets the transport mode object.
	 *
	 * @param transportModeObject the new transport mode object
	 */
	public void setTransportModeObject(String transportModeObject) {
		this.transportModeObject = transportModeObject;
	}

	/**
	 * Sets the row index.
	 *
	 * @param rowIndex the new row index
	 */
	public void setRowIndex(String rowIndex){
		this.rowIndex = rowIndex;
	}

	/**
	 * Gets the row index.
	 *
	 * @return the row index
	 */
	public String getRowIndex(){
		return rowIndex;
	}
	// added for ICRD-9311 
	/**
	 * Gets the invoke function.
	 *
	 * @return the invoke function
	 */
	public String getInvokeFunction() {
		return invokeFunction;
	}

	/**
	 * Sets the invoke function.
	 *
	 * @param invokeFunction the new invoke function
	 */
	public void setInvokeFunction(String invokeFunction) {
		this.invokeFunction = invokeFunction;
	}

	/**
	 * 	Getter for selectedValues 
	 *	Added by : A-4803 on 09-Apr-2014
	 * 	Used for :
	 */
	public String getSelectedValues() {
		return selectedValues;
	}

	/**
	 *  @param selectedValues the selectedValues to set
	 * 	Setter for selectedValues 
	 *	Added by : A-4803 on 09-Apr-2014
	 * 	Used for :
	 */
	public void setSelectedValues(String selectedValues) {
		this.selectedValues = selectedValues;
	}

	/**
	 * 	Getter for lastPageNum 
	 *	Added by : A-4803 on 11-Apr-2014
	 * 	Used for :
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}

	/**
	 *  @param lastPageNum the lastPageNum to set
	 * 	Setter for lastPageNum 
	 *	Added by : A-4803 on 11-Apr-2014
	 * 	Used for :
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	/**
	 * Gets the single select.
	 *
	 * @return the singleSelect
	 */
	public String getSingleSelect() {
		return singleSelect;
	}

	/**
	 * Sets the single select.
	 *
	 * @param singleSelect the singleSelect to set
	 */
	public void setSingleSelect(String singleSelect) {
		this.singleSelect = singleSelect;
	}
	public String getPagination() {
		return pagination;
	}
	public void setPagination(String pagination) {
		this.pagination = pagination;
	}
	public String getDefaultPageSize() {
		return defaultPageSize;
	}
	public void setDefaultPageSize(String defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}
	public String getTotalRecordsCount() {
		return totalRecordsCount;
	}
	public void setTotalRecordsCount(String totalRecordsCount) {
		this.totalRecordsCount = totalRecordsCount;
	}


	
	
	

}
