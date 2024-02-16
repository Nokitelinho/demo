/*
 * ListSubProductForm.java Created on Jul 7, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.products.defaults;

import javax.servlet.ServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import com.ibsplc.icargo.business.products.defaults.vo.SubProductVO;
import com.ibsplc.icargo.framework.model.ScreenModel;
import static org.apache.struts.action.ActionMessages.GLOBAL_MESSAGE;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1870
 *
 */
public class ListSubProductForm extends ScreenModel  {
	private String productName = "";
	private String productCode = "";
	private String status = "";
	private String transportMode = "";
	private String priority = "";
	private String productScc = "";
	private String checkList = "";

	private String[]  checkBox;
	private String subProductCode = "";
	private String versionNumber = "";
	private String lastPageNum="0";

	private String displayPage="1";
	private String startDate = "";
	private String endDate = "";
	private Page<SubProductVO> listSubProducts=null;
	private String mode;
	private String buttonStatusFlag="";

	//Added now
	
	private String fromListSubproduct;

private static final String BUNDLE = "ListSubProduct"; // The key attribute specified in struts_config.xml file.

	private String bundle;

	private String navigationMode;//added by a-5505 for the bug ICRD-124986

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
	 * @return screenid
	 */
	public String getScreenId() {
		return "products.defaults.listsubproducts";
	}
   /**
    * @return producr name
    */
	public String getProduct() {
		return "products";
	}
    /**
     * @return subProduct name
     */
	public String getSubProduct() {
		return "defaults";
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
	 * @return  Page<SubProductVO>
	 */
	public Page<SubProductVO> getListSubProducts() {
		return listSubProducts;
	}
	/**
	 *
	 * @param listSubProducts
	 */
	public void setListSubProducts(Page<SubProductVO> listSubProducts) {
		this.listSubProducts = listSubProducts;
	}
	/**
	 *
	 * @return String
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 *
	 * @param startDate
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 *
	 * @return String
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 *
	 * @param endDate
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	public void setLastPageNumber(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}
	/**
	 *
	 * @return String[]
	 */
	public String[] getCheckBox() {
		return checkBox;
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
	 * @param productName
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 *
	 * @param productCode
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
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
	 * @return String
	 */
	public String getProductName() {
		return (this.productName);
	}
	/**
	 *
	 * @return String
	 */
	public String getProductCode() {
		return (this.productCode);
	}
	/**
	 *
	 * @return String
	 */
	public String getStatus() {
		return (this.status);
	}
	/**
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
	 * @param actionMapping
	 * @param servletRequest
	 */
	public void reset(ActionMapping actionMapping, ServletRequest servletRequest) {
		// Reset field values here.
	}

	/**
	 * @return ActionMessages
	 */

	public ActionMessages validateForm(){
		ActionMessages actionMessages = new ActionMessages();
		if(("").equals(productName)){
			actionMessages.add(GLOBAL_MESSAGE,new ActionMessage("ERR_1001"));
		}
		if(("A").equals(this.getMode())){
			for(int i=0;i<checkBox.length;i++){
				for(SubProductVO subProductVO:listSubProducts){
					if(subProductVO.getProductCode()!=null && subProductVO.getProductCode().equals(checkBox[i])){
						if(("Active").equals(subProductVO.getStatus())){
							actionMessages.add(GLOBAL_MESSAGE,new ActionMessage("ERR_012"));
						}
						if(("New").equals(subProductVO.getStatus())){
							actionMessages.add(GLOBAL_MESSAGE,new ActionMessage("ERR_015"));
						}
					}
				}
			}
		}
		if(("I").equals(this.getMode())){
			for(int i=0;i<checkBox.length;i++){
				for(SubProductVO subProductVO:listSubProducts){
					if(subProductVO.getProductCode()!=null && subProductVO.getProductCode().equals(checkBox[i])){
						if(("InActive").equals(subProductVO.getStatus())){
							actionMessages.add(GLOBAL_MESSAGE,new ActionMessage("ERR_013"));
						}
					}
				}
			}
		}


		return actionMessages;

	}
	/**
	 *
	 * @return String
	 */

	public String getSubProductCode() {
		return subProductCode;
	}
	/**
	 *
	 * @param subProductCode
	 */
	public void setSubProductCode(String subProductCode) {
		this.subProductCode = subProductCode;
	}
	/**
	 *
	 * @return String
	 */
	public String  getVersionNumber() {
		return versionNumber;
	}
	/**
	 *
	 * @param versionNumber
	 */
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}
	/**
	 * 
	 * @return
	 */
	public String getCheckList() {
		return checkList;
	}
	/**
	 * 
	 * @param checkList
	 */
	public void setCheckList(String checkList) {
		this.checkList = checkList;
	}
	
	//Added now
	
	/**
	 * @return Returns the fromListSubproduct.
	 */
	public String getFromListSubproduct() {
		return fromListSubproduct;
	}
	/**
	 * @param fromListSubproduct The fromListSubproduct to set.
	 */
	public void setFromListSubproduct(String fromListSubproduct) {
		this.fromListSubproduct = fromListSubproduct;
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
	 * 
	 * @return
	 */
	public String getNavigationMode() {
		return navigationMode;
	}
	/**
	 * 
	 * @param navigationMode
	 */
	public void setNavigationMode(String navigationMode) {
		this.navigationMode = navigationMode;
	}





}
