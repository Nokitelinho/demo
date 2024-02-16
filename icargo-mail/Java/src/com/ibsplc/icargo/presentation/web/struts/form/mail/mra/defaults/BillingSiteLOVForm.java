/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingSiteLOVForm.java
 *
 *	Created by	:	A-5219
 *	Created on	:	15-Nov-2013
 *
 *  Copyright 2013 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;


import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteLOVVO;
import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * The Class BillingSiteLOVForm.
 *
 * @author A-5219
 */
public class BillingSiteLOVForm extends ScreenModel{

	/** The Constant PRODUCT. */
	private static final String PRODUCT = "mail";
	
	/** The Constant SUBPRODUCT. */
	private static final String SUBPRODUCT = "mra";
	
	/** The Constant SCREENID. */
	private static final String SCREENID = "mailtracking.mra.defaults.billingsitelov";
	
	/** The Constant BUNDLE. */
	private static final String BUNDLE="billingsitelovresource";
	

	/** The lovaction. */
	private String lovaction;
	
	/** The code. */
	private String code;
	
	/** The description. */
	private String description;
	
	/** The selected values. */
	private String selectedValues = "";

	/** The pagination. */
	private String pagination;

	/** The multiselect. */
	private String multiselect;

	/** The form count. */
	private String formCount;

	/** The lov txt field name. */
	private String lovTxtFieldName;
	
	/** The lov name txt field name. */
	private String lovNameTxtFieldName;

	/** The lov description txt field name. */
	private String lovDescriptionTxtFieldName;
	
	/** The last page num. */
	private String lastPageNum = "";

	/** The display page. */
	private String displayPage = "1";
	
	/** The index. */
	private int index;
	
	/** The billing site code. */
	private String billingSiteCode;	
	
	/** The select check box. */
	private String[] selectCheckBox;
	
	/** The billing site lov. */
	private Page<BillingSiteLOVVO> billingSiteLov;
	
	/** The from screen. */
	private String fromScreen;
	/**
 	 * Gets the Screen Id.
 	 *
 	 * @return Returns ScreenId.
 	 */
	public String getScreenId() {
		
		return SCREENID;
	}

	/**
 	 * Gets the  Product.
 	 *
 	 * @return Returns the  Product.
 	 */
	public String getProduct() {
		
		return PRODUCT;
	}

	 /**
 	 * Gets the sub Product.
 	 *
 	 * @return Returns the sub Product.
 	 */
	public String getSubProduct() {
		
		return SUBPRODUCT;
	}

	
	
	 /**
 	 * Gets the billing site code.
 	 *
 	 * @return Returns the billingSiteCode.
 	 */
	public String getBillingSiteCode() {
		return billingSiteCode;
	}

	/**
	 * Sets the billing site code.
	 *
	 * @param billingSiteCode The billingSiteCode to set.
	 */
	public void setBillingSiteCode(String billingSiteCode) {
		this.billingSiteCode = billingSiteCode;
	}

	/**
	 * Gets the bundle.
	 *
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	
	/**
	 * Gets the select check box.
	 *
	 * @return Returns the selectCheckBox.
	 */
	public String[] getSelectCheckBox() {
		return selectCheckBox;
	}

	/**
	 * Sets the select check box.
	 *
	 * @param selectCheckBox The selectCheckBox to set.
	 */
	public void setSelectCheckBox(String[] selectCheckBox) {
		this.selectCheckBox = selectCheckBox;
	}

	/**
	 * Gets the billing site lov.
	 *
	 * @return Returns the billingSiteLov.
	 */
	public Page<BillingSiteLOVVO> getBillingSiteLov() {
		return billingSiteLov;
	}

	/**
	 * Sets the billing site lov.
	 *
	 * @param billingSiteLov The billingSiteLov to set.
	 */
	public void setBillingSiteLov(Page<BillingSiteLOVVO> billingSiteLov) {
		this.billingSiteLov = billingSiteLov;
	}

	/**
	 * Gets the display page.
	 *
	 * @return Returns the displayPage.
	 */
	public String getDisplayPage() {
		return displayPage;
	}

	/**
	 * Sets the display page.
	 *
	 * @param displayPage The displayPage to set.
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	/**
	 * Gets the form count.
	 *
	 * @return Returns the formCount.
	 */
	public String getFormCount() {
		return formCount;
	}

	/**
	 * Sets the form count.
	 *
	 * @param formCount The formCount to set.
	 */
	public void setFormCount(String formCount) {
		this.formCount = formCount;
	}

	/**
	 * Gets the index.
	 *
	 * @return Returns the index.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Sets the index.
	 *
	 * @param index The index to set.
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * Gets the last page num.
	 *
	 * @return Returns the lastPageNum.
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}

	/**
	 * Sets the last page num.
	 *
	 * @param lastPageNum The lastPageNum to set.
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	/**
	 * Gets the lovaction.
	 *
	 * @return Returns the lovaction.
	 */
	public String getLovaction() {
		return lovaction;
	}

	/**
	 * Sets the lovaction.
	 *
	 * @param lovaction The lovaction to set.
	 */
	public void setLovaction(String lovaction) {
		this.lovaction = lovaction;
	}

	/**
	 * Gets the lov description txt field name.
	 *
	 * @return Returns the lovDescriptionTxtFieldName.
	 */
	public String getLovDescriptionTxtFieldName() {
		return lovDescriptionTxtFieldName;
	}

	/**
	 * Sets the lov description txt field name.
	 *
	 * @param lovDescriptionTxtFieldName The lovDescriptionTxtFieldName to set.
	 */
	public void setLovDescriptionTxtFieldName(String lovDescriptionTxtFieldName) {
		this.lovDescriptionTxtFieldName = lovDescriptionTxtFieldName;
	}

	/**
	 * Gets the lov name txt field name.
	 *
	 * @return Returns the lovNameTxtFieldName.
	 */
	public String getLovNameTxtFieldName() {
		return lovNameTxtFieldName;
	}

	/**
	 * Sets the lov name txt field name.
	 *
	 * @param lovNameTxtFieldName The lovNameTxtFieldName to set.
	 */
	public void setLovNameTxtFieldName(String lovNameTxtFieldName) {
		this.lovNameTxtFieldName = lovNameTxtFieldName;
	}

	/**
	 * Gets the lov txt field name.
	 *
	 * @return Returns the lovTxtFieldName.
	 */
	public String getLovTxtFieldName() {
		return lovTxtFieldName;
	}

	/**
	 * Sets the lov txt field name.
	 *
	 * @param lovTxtFieldName The lovTxtFieldName to set.
	 */
	public void setLovTxtFieldName(String lovTxtFieldName) {
		this.lovTxtFieldName = lovTxtFieldName;
	}

	/**
	 * Gets the multiselect.
	 *
	 * @return Returns the multiselect.
	 */
	public String getMultiselect() {
		return multiselect;
	}

	/**
	 * Sets the multiselect.
	 *
	 * @param multiselect The multiselect to set.
	 */
	public void setMultiselect(String multiselect) {
		this.multiselect = multiselect;
	}

	/**
	 * Gets the pagination.
	 *
	 * @return Returns the pagination.
	 */
	public String getPagination() {
		return pagination;
	}

	/**
	 * Sets the pagination.
	 *
	 * @param pagination The pagination to set.
	 */
	public void setPagination(String pagination) {
		this.pagination = pagination;
	}

	/**
	 * Gets the selected values.
	 *
	 * @return Returns the selectedValues.
	 */
	public String getSelectedValues() {
		return selectedValues;
	}

	/**
	 * Sets the selected values.
	 *
	 * @param selectedValues The selectedValues to set.
	 */
	public void setSelectedValues(String selectedValues) {
		this.selectedValues = selectedValues;
	}

	/**
	 * Gets the code.
	 *
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code The code to set.
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the description.
	 *
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * Sets the from screen.
	 *
	 * @param fromScreen the fromScreen to set
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}
	/**
	 * Gets the from screen.
	 *
	 * @return the fromScreen
	 */
	public String getFromScreen() {
		return fromScreen;
	}


	
}
