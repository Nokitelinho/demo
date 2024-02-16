package com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux;

import com.ibsplc.icargo.framework.model.ScreenModel;

public class BasisUxLovForm extends ScreenModel{

	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "basisUxLovResources";
	
	private String bundle;
	//FOR LOV
	
    private String formula;

    private String formulaPanel;

	private String operator;

	private String title;

	private String formCount;
	
	private String lovTxtFieldName;

	private String lovDescriptionTxtFieldName;
	
	private String selectedValues = "";

	private String[] selectCheckBox;

	private int index;  
	
	private String multiSelect;
	
	private String serviceResponseFlag;
	
	private String code;
	
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
	 * @return the formula
	 */
	public String getFormula() {
		return formula;
	}

	/**
	 * @param formula the formula to set
	 */
	public void setFormula(String formula) {
		this.formula = formula;
	}

	/**
	 * @return the formulaPanel
	 */
	public String getFormulaPanel() {
		return formulaPanel;
	}

	/**
	 * @param formulaPanel the formulaPanel to set
	 */
	public void setFormulaPanel(String formulaPanel) {
		this.formulaPanel = formulaPanel;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
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
	 * @return the multiSelect
	 */
	public String getMultiSelect() {
		return multiSelect;
	}

	/**
	 * @param multiSelect the multiSelect to set
	 */
	public void setMultiSelect(String multiSelect) {
		this.multiSelect = multiSelect;
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

	
	
}
