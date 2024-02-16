/**
 * 
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-6986
 *
 */
public class ParameterUxLovForm extends ScreenModel{

	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "parameterUxLovResources";
	
	private String bundle;
	//FOR LOV
	private String code;
	
	private String description;
	
    private String[] parameterCode;

    private String[] parameterValue;
    
    private String[] parameterDisplayCode;

	private String[] excludeFlag;

	private String title;

	/*private String formCount;
	
	private String lovTxtFieldName;

	private String lovDescriptionTxtFieldName;*/
	
	private String selectedValues = "";

	private String[] selectCheckBox;

	private int index;  
	
	private String multiSelect;
	
	private String mailSubClass;
	private String parameter;
	private String txtFieldName;
	private String dispTxtFieldName;
	private String excludeIncludeFlag;
	private String serviceResponseFlag;
	private String excludeTxtFieldName;
	private String displayParameter;

	
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
	 * @return the parameterCode
	 */
	public String[] getParameterCode() {
		return parameterCode;
	}

	/**
	 * @param parameterCode the parameterCode to set
	 */
	public void setParameterCode(String[] parameterCode) {
		this.parameterCode = parameterCode;
	}

	/**
	 * @return the parameterValue
	 */
	public String[] getParameterValue() {
		return parameterValue;
	}

	/**
	 * @param parameterValue the parameterValue to set
	 */
	public void setParameterValue(String[] parameterValue) {
		this.parameterValue = parameterValue;
	}

	/**
	 * @return the excludeFlag
	 */
	public String[] getExcludeFlag() {
		return excludeFlag;
	}

	/**
	 * @param excludeFlag the excludeFlag to set
	 */
	public void setExcludeFlag(String[] excludeFlag) {
		this.excludeFlag = excludeFlag;
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
/*	public String getFormCount() {
		return formCount;
	}*/

	/**
	 * @param formCount the formCount to set
	 */
	/*public void setFormCount(String formCount) {
		this.formCount = formCount;
	}*/

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
	 * @return the parameterDisplayValue
	 */
	public String[] getParameterDisplayCode() {
		return parameterDisplayCode;
	}

	/**
	 * @param parameterDisplayValue the parameterDisplayValue to set
	 */
	public void setParameterDisplayCode(String[] parameterDisplayCode) {
		this.parameterDisplayCode = parameterDisplayCode;
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
	/*public String getLovTxtFieldName() {
		return lovTxtFieldName;
	}*/

	/**
	 * @param lovTxtFieldName the lovTxtFieldName to set
	 */
	/*public void setLovTxtFieldName(String lovTxtFieldName) {
		this.lovTxtFieldName = lovTxtFieldName;
	}*/

	/**
	 * @return the lovDescriptionTxtFieldName
	 */
	/*public String getLovDescriptionTxtFieldName() {
		return lovDescriptionTxtFieldName;
	}*/

	/**
	 * @param lovDescriptionTxtFieldName the lovDescriptionTxtFieldName to set
	 */
	/*public void setLovDescriptionTxtFieldName(String lovDescriptionTxtFieldName) {
		this.lovDescriptionTxtFieldName = lovDescriptionTxtFieldName;
	}*/

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
	 * @return the mailSubClass
	 */
	public String getMailSubClass() {
		return mailSubClass;
	}

	/**
	 * @param mailSubClass the mailSubClass to set
	 */
	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}
	/**
	 * @return the parameter
	 */
	public String getParameter() {
		return parameter;
	}
	/**
	 * @param parameter the parameter to set
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	/**
	 * @return the txtFieldName
	 */
	public String getTxtFieldName() {
		return txtFieldName;
	}
	/**
	 * @param txtFieldName the txtFieldName to set
	 */
	public void setTxtFieldName(String txtFieldName) {
		this.txtFieldName = txtFieldName;
	}

	/**
	 * @return the excludeIncludeFlag
	 */
	public String getExcludeIncludeFlag() {
		return excludeIncludeFlag;
	}

	/**
	 * @param excludeIncludeFlag the excludeIncludeFlag to set
	 */
	public void setExcludeIncludeFlag(String excludeIncludeFlag) {
		this.excludeIncludeFlag = excludeIncludeFlag;
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
	 * @return the excludeTxtFieldName
	 */
	public String getExcludeTxtFieldName() {
		return excludeTxtFieldName;
	}

	/**
	 * @param excludeTxtFieldName the excludeTxtFieldName to set
	 */
	public void setExcludeTxtFieldName(String excludeTxtFieldName) {
		this.excludeTxtFieldName = excludeTxtFieldName;
	}

	/**
	 * @return the displayParameter
	 */
	public String getDisplayParameter() {
		return displayParameter;
	}

	/**
	 * @param displayParameter the displayParameter to set
	 */
	public void setDisplayParameter(String displayParameter) {
		this.displayParameter = displayParameter;
	}

	/**
	 * @return the dispTxtFieldName
	 */
	public String getDispTxtFieldName() {
		return dispTxtFieldName;
	}

	/**
	 * @param dispTxtFieldName the dispTxtFieldName to set
	 */
	public void setDispTxtFieldName(String dispTxtFieldName) {
		this.dispTxtFieldName = dispTxtFieldName;
	}

	


}
