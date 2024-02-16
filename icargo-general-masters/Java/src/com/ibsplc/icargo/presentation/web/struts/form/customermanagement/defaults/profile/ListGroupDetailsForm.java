/*
 * ListGroupDetailsForm.java Created on Dec 21, 2021
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile;

import com.ibsplc.icargo.framework.model.ScreenModel;


/**
 * @author A-2569
 *
 */
public class ListGroupDetailsForm extends ScreenModel {


	private static final String BUNDLE = "maintainregcustomerform";
	private static final String PRODUCT = "customermanagement";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "customermanagement.defaults.maintainregcustomer";


	private String bundle;
	private String customerCode;
	private String companyCode;
	private String groupType;
	private String groupName;
	private String groupDescription;
	private String category;
	
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	 public String getScreenId() {
	        return SCREENID;
	 }
	 public String getProduct() {
	        return PRODUCT;
	 }
	 public String getSubProduct() {
	        return SUBPRODUCT;
	 }
	 public String getBundle() {
			return BUNDLE;
	 }
	 public void setBundle(String bundle) {
			this.bundle = bundle;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupDescription() {
		return groupDescription;
	}
	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}	
	 
}
