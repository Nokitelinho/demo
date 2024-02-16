package com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux;

import com.ibsplc.icargo.framework.model.ScreenModel;

public class FormulaUxLovForm extends ScreenModel{

	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "formulaUxLovResources";
	
	private String bundle;
	//FOR LOV
	private String[] basis;

    private String[] condition;

    private String[] component;

	private String[] offset;

	private String[] operator;
	
	private String[] logicOperator;
	
	private String[] operationFlags;

	private String title;

	private String formCount;
	
	private String lovTxtFieldName;

	private String lovDescriptionTxtFieldName;
	
	private String selectedValues = "";

	private String[] selectCheckBox;

	private int index;  
	
	private String multiselect;
	
	private String code;
	private String description;
	private String serviceResponseFlag;
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
	 * @return the basis
	 */
	public String[] getBasis() {
		return basis;
	}

	/**
	 * @param basis the basis to set
	 */
	public void setBasis(String[] basis) {
		this.basis = basis;
	}

	/**
	 * @return the condition
	 */
	public String[] getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(String[] condition) {
		this.condition = condition;
	}

	/**
	 * @return the component
	 */
	public String[] getComponent() {
		return component;
	}

	/**
	 * @param component the component to set
	 */
	public void setComponent(String[] component) {
		this.component = component;
	}

	/**
	 * @return the offset
	 */
	public String[] getOffset() {
		return offset;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(String[] offset) {
		this.offset = offset;
	}

	/**
	 * @return the operator
	 */
	public String[] getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String[] operator) {
		this.operator = operator;
	}
	/**
	 * @return the logicOperator
	 */
	public String[] getLogicOperator() {
		return logicOperator;
	}

	/**
	 * @param logicOperator the logicOperator to set
	 */
	public void setLogicOperator(String[] logicOperator) {
		this.logicOperator = logicOperator;
	}

	/**
	 * @return the title
	 */
	
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the formCount
	 */
	public String getFormCount() {
		return formCount;
	}

	/**
	 * @param formCount the formCount to set
	 */
	public void setFormCount(String formCount) {
		this.formCount = formCount;
	}

	/**
	 * @return the operationFlags
	 */
	public String[] getOperationFlags() {
		return operationFlags;
	}

	/**
	 * @param operationFlags the operationFlags to set
	 */
	public void setOperationFlags(String[] operationFlags) {
		this.operationFlags = operationFlags;
	}

	/**
	 * @return the lovTxtFieldName
	 */
	public String getLovTxtFieldName() {
		return lovTxtFieldName;
	}

	/**
	 * @param lovTxtFieldName the lovTxtFieldName to set
	 */
	public void setLovTxtFieldName(String lovTxtFieldName) {
		this.lovTxtFieldName = lovTxtFieldName;
	}

	/**
	 * @return the lovDescriptionTxtFieldName
	 */
	public String getLovDescriptionTxtFieldName() {
		return lovDescriptionTxtFieldName;
	}

	/**
	 * @param lovDescriptionTxtFieldName the lovDescriptionTxtFieldName to set
	 */
	public void setLovDescriptionTxtFieldName(String lovDescriptionTxtFieldName) {
		this.lovDescriptionTxtFieldName = lovDescriptionTxtFieldName;
	}

	/**
	 * @return the selectedValues
	 */
	public String getSelectedValues() {
		return selectedValues;
	}

	/**
	 * @param selectedValues the selectedValues to set
	 */
	public void setSelectedValues(String selectedValues) {
		this.selectedValues = selectedValues;
	}

	/**
	 * @return the selectCheckBox
	 */
	public String[] getSelectCheckBox() {
		return selectCheckBox;
	}

	/**
	 * @param selectCheckBox the selectCheckBox to set
	 */
	public void setSelectCheckBox(String[] selectCheckBox) {
		this.selectCheckBox = selectCheckBox;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the multiselect
	 */
	public String getMultiselect() {
		return multiselect;
	}

	/**
	 * @param multiselect the multiselect to set
	 */
	public void setMultiselect(String multiselect) {
		this.multiselect = multiselect;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the serviceResponseFlag
	 */
	public String getServiceResponseFlag() {
		return serviceResponseFlag;
	}

	/**
	 * @param serviceResponseFlag the serviceResponseFlag to set
	 */
	public void setServiceResponseFlag(String serviceResponseFlag) {
		this.serviceResponseFlag = serviceResponseFlag;
	}

	
}
