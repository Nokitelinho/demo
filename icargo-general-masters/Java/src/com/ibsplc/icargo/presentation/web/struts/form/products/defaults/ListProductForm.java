/*
 * ListProductForm.java Created on Jun 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.products.defaults;



import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1870
 *
 */

public class ListProductForm extends ScreenModel {
	private String productName = "";
	private String productCode = "";
	private String displayProductName = "";
	private String status = "";
	private boolean isRateDefined;
	private String transportMode = "";
	private String priority = "";
	private String productScc = "";
	private String fromDate = "";
	private String toDate = "";
	private String[]  checkBox;
	private String mode;
	private Page<ProductVO> listProducts;
	private String lastPageNum="0";
	private String displayPage="1";
	private String buttonStatusFlag="";
	//Added now
	private String fromListProduct="";
	
	private String countTotalFlag=""; //added by A-5201 for CR ICRD-22065

private static final String BUNDLE = "ListProduct"; // The key attribute specified in struts_config.xml file.

	private String bundle;
	
	/**the productCategory**/
	private String productCategory; //Added for ICRD-166985 by A-5117
	private String screenMode; //Added for ICRD-174091 by A-5117
	/**
	 * @return the screenMode
	 */
	public String getScreenMode() {
		return screenMode;
	}
	/**
	 * @param screenMode the screenMode to set
	 */
	public void setScreenMode(String screenMode) {
		this.screenMode = screenMode;
	}
	/**
	 * @return the productCategory
	 */
	public String getProductCategory() {
		return productCategory;
	}
	/**
	 * @param productCategory the productCategory to set
	 */
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
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
	 * @param productName
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 *
	 * @param checkBox
	 */
	public void setCheckBox(String[] checkBox) {
		this.checkBox = checkBox;
	}
	/**
	 *
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 *
	 * @param transportMode
	 */
	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}
	/**
	 *
	 * @param priority
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}
	/**
	 *
	 * @param productScc
	 */
	public void setProductScc(String productScc) {
		this.productScc = productScc;
	}
	/**
	 *
	 * @param fromDate
	 */
	public void setFromdate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 *
	 * @param toDate
	 */
	public void setTodate(String toDate) {
		this.toDate = toDate;
	}
	/**
	 *
	 * @return String
	 */
	public String getProductName() {
		return (this.productName);
	}
	/**
	 *
	 * @return String[]
	 */
	public String[]  getCheckBox() {
		return (this.checkBox);
	}
	/**
	 *
	 * @return String
	 */
	public String getStatus() {
		return (this.status);
	}
	/**
	 *
	 * @return String
	 */
	public String getTransportMode() {
		return (this.transportMode);
	}
	/**
	 *
	 * @return String
	 */
	public String getPriority() {
		return (this.priority);
	}
	/**
	 *
	 * @return String
	 */
	public String getProductScc() {
		return (this.productScc);
	}
	/**
	 *
	 * @return String
	 */
	@DateFieldId(id="ListProductsDateRange",fieldType="from")//Added By T-1925 for ICRD-9704
	public String getFromDate() {
		return (this.fromDate);
	}
	/**
	 *
	 * @return String
	 */
	@DateFieldId(id="ListProductsDateRange",fieldType="to")//Added By T-1925 for ICRD-9704
	public String getToDate() {
		return (this.toDate);
	}
	/**
	 * @param actionMapping
	 * @param servletRequest
	 */
	public void reset(ActionMapping actionMapping, ServletRequest servletRequest) {
		// Reset field values here.
	}
	/**
	 * @param actionMapping
	 * @param httpServletRequest
	 */
	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		// Reset field values here.
	}
	/**
	 * @return String
	 */
	public String getScreenId() {
		return "products.defaults.listproducts";
	}
	/**
	 * @return String
	 */
	public String getProduct() {
		return "products";
	}
	/**
	 * @return String
	 */
	public String getSubProduct() {
		return "defaults";
	}

	/**
	 * @return ActionMessages
	 */
	/*public ActionMessages validateForm(){
		ActionMessages actionMessages = new ActionMessages();
		if(!"".equals(fromDate) && !"".equals(toDate)){
		if(!DateUtilities.isLessThan(fromDate,toDate,"dd-MMM-yyyy")){
			System.out.println("Inside isLessthan start date");
			actionMessages.add(GLOBAL_MESSAGE,new ActionMessage("products.defaults.todategrtrthanfrmdate"));
		}
		}
		if("A".equals(mode)){
			for(int i=0;i<checkBox.length;i++){
				for(ProductVO productVO:listProducts){
					if(productVO.getProductCode()==checkBox[i]){
						if(productVO.getStatus().equals("Active")){
							actionMessages.add(GLOBAL_MESSAGE,new ActionMessage("products.defaults.prdisactive"));
						}
						if(productVO.getIsRateDefined()==false){
							actionMessages.add(GLOBAL_MESSAGE,new ActionMessage("ERR_1029"));
						}
					}
				}
			}
		}
		if("I".equals(mode)){
			for(int i=0;i<checkBox.length;i++){
				for(ProductVO productVO:listProducts){
					if(productVO.getProductCode()==checkBox[i]){
						if(productVO.getStatus().equals("InActive")){
							actionMessages.add(GLOBAL_MESSAGE,new ActionMessage("products.defaults.prdisinactive"));
						}
					}
				}
			}
		}

		return actionMessages;

	}*/


	/**
	 * @return Page<ProductVO>
	 */
	public Page<ProductVO> getListProducts() {
		return listProducts;
	}
	/**
	 *
	 * @param listProducts
	 */
	public void setListProducts(Page<ProductVO> listProducts) {
		this.listProducts = listProducts;
	}
	/**
	 *
	 * @return String
	 */
	public String getMode() {
		return mode;
	}
	/**
	 *
	 * @param mode
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	/**
	 *
	 * @param fromDate
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 *
	 * @param toDate
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	/**
	 *
	 * @return String
	 */
	public String getDisplayPage() {
		return displayPage;
	}
	/**
	 *
	 * @param displayPage
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	/**
	 *
	 * @return String
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}
	/**
	 *
	 * @param lastPageNum
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}
	/**
	 *
	 * @return boolean
	 */
	public boolean isRateDefined() {
		return isRateDefined;
	}
	/**
	 *
	 * @param isRateDefined
	 */
	public void setRateDefined(boolean isRateDefined) {
		this.isRateDefined = isRateDefined;
	}
	/**
	 * @return Returns the displayProductName.
	 */
	public String getDisplayProductName() {
		return displayProductName;
	}
	/**
	 * @param displayProductName The displayProductName to set.
	 */
	public void setDisplayProductName(String displayProductName) {
		this.displayProductName = displayProductName;
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
	/**
	 * @return Returns the buttonStatusFlag.
	 */
	public String getButtonStatusFlag() {
		return buttonStatusFlag;
	}
	/**
	 * @param buttonStatusFlag The buttonStatusFlag to set.
	 */
	public void setButtonStatusFlag(String buttonStatusFlag) {
		this.buttonStatusFlag = buttonStatusFlag;
	}
	/**
	 * @return Returns the fromListProduct.
	 */
	public String getFromListProduct() {
		return fromListProduct;
	}
	/**
	 * @param fromListProduct The fromListProduct to set.
	 */
	public void setFromListProduct(String fromListProduct) {
		this.fromListProduct = fromListProduct;
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
