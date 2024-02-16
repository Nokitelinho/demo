/*
 * VersionNumberLOVForm.java Created on Apr 04, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractVersionLOVVO;
import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * 
 * @author a-2518 
 * 
 */
public class VersionNumberLOVForm extends ScreenModel {

	private static final String BUNDLE = "versionnumberlov";

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID = "mailtracking.mra.defaults.versionnumberlov";

	private String code;

	private String name;

	private String multiselect;

	private String lovaction;

	private String formCount;

	private String lovTxtFieldName;

	private String lovDescriptionTxtFieldName;

	private String selectedValues = "";

	private String[] selectCheckBox;

	private Collection<MailContractVersionLOVVO> versionNumbers;

	private int index;

	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            The code to set.
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return Returns the formCount.
	 */
	public String getFormCount() {
		return formCount;
	}

	/**
	 * @param formCount
	 *            The formCount to set.
	 */
	public void setFormCount(String formCount) {
		this.formCount = formCount;
	}

	/**
	 * @return Returns the index.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            The index to set.
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return Returns the lovaction.
	 */
	public String getLovaction() {
		return lovaction;
	}

	/**
	 * @param lovaction
	 *            The lovaction to set.
	 */
	public void setLovaction(String lovaction) {
		this.lovaction = lovaction;
	}

	/**
	 * @return Returns the lovDescriptionTxtFieldName.
	 */
	public String getLovDescriptionTxtFieldName() {
		return lovDescriptionTxtFieldName;
	}

	/**
	 * @param lovDescriptionTxtFieldName
	 *            The lovDescriptionTxtFieldName to set.
	 */
	public void setLovDescriptionTxtFieldName(String lovDescriptionTxtFieldName) {
		this.lovDescriptionTxtFieldName = lovDescriptionTxtFieldName;
	}

	/**
	 * @return Returns the lovTxtFieldName.
	 */
	public String getLovTxtFieldName() {
		return lovTxtFieldName;
	}

	/**
	 * @param lovTxtFieldName
	 *            The lovTxtFieldName to set.
	 */
	public void setLovTxtFieldName(String lovTxtFieldName) {
		this.lovTxtFieldName = lovTxtFieldName;
	}

	/**
	 * @return Returns the multiselect.
	 */
	public String getMultiselect() {
		return multiselect;
	}

	/**
	 * @param multiselect
	 *            The multiselect to set.
	 */
	public void setMultiselect(String multiselect) {
		this.multiselect = multiselect;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the selectCheckBox.
	 */
	public String[] getSelectCheckBox() {
		return selectCheckBox;
	}

	/**
	 * @param selectCheckBox
	 *            The selectCheckBox to set.
	 */
	public void setSelectCheckBox(String[] selectCheckBox) {
		this.selectCheckBox = selectCheckBox;
	}

	/**
	 * @return Returns the selectedValues.
	 */
	public String getSelectedValues() {
		return selectedValues;
	}

	/**
	 * @param selectedValues
	 *            The selectedValues to set.
	 */
	public void setSelectedValues(String selectedValues) {
		this.selectedValues = selectedValues;
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
	 * @return Returns the versionNumbers.
	 */
	public Collection<MailContractVersionLOVVO> getVersionNumbers() {
		return versionNumbers;
	}

	/**
	 * @param versionNumbers
	 *            The versionNumbers to set.
	 */
	public void setVersionNumbers(
			Collection<MailContractVersionLOVVO> versionNumbers) {
		this.versionNumbers = versionNumbers;
	}

}
