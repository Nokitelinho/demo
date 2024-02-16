/*
 * MailSubClassForm.java Created on June 08, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2047
 *
 */
public class MailSubClassForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.masters.subclass";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "mailSubClassResources";

	private String bundle;
	
	private String subClassFilter;
	
	private String[] rowId;
	private String[] code;
	private String[] description;
	private String[] operationFlag;
	private String[] subClassGroup;

	/**
	 * @return screenId
	 */
	public String getScreenId() {
		return SCREEN_ID;
	}

	/**
	 * @return product
	 */
	public String getProduct() {
		return PRODUCT_NAME;
	}

	/**
	 * @return subProduct
	 */
	public String getSubProduct() {
		return SUBPRODUCT_NAME;
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
	 * @return Returns the subClassFilter.
	 */
	public String getSubClassFilter() {
		return subClassFilter;
	}

	/**
	 * @param subClassFilter The subClassFilter to set.
	 */
	public void setSubClassFilter(String subClassFilter) {
		this.subClassFilter = subClassFilter;
	}

	/**
	 * @return Returns the code.
	 */
	public String[] getCode() {
		return code;
	}

	/**
	 * @param code The code to set.
	 */
	public void setCode(String[] code) {
		this.code = code;
	}

	/**
	 * @return Returns the description.
	 */
	public String[] getDescription() {
		return description;
	}

	/**
	 * @param description The description to set.
	 */
	public void setDescription(String[] description) {
		this.description = description;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String[] getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String[] operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return Returns the rowId.
	 */
	public String[] getRowId() {
		return rowId;
	}

	/**
	 * @param rowId The rowId to set.
	 */
	public void setRowId(String[] rowId) {
		this.rowId = rowId;
	}

	/**
	 * @return Returns the subClassGroup.
	 */
	public String[] getSubClassGroup() {
		return subClassGroup;
	}

	/**
	 * @param subClassGroup The subClassGroup to set.
	 */
	public void setSubClassGroup(String[] subClassGroup) {
		this.subClassGroup = subClassGroup;
	}

	
}
