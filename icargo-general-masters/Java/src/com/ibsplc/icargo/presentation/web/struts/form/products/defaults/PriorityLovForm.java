/*
 * PriorityLovForm.java Created on Jun 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.products.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

// TODO: Auto-generated Javadoc
/**
 * The Class PriorityLovForm.
 *
 * @author A-1754
 */
public class PriorityLovForm extends ScreenModel {
	
	/** Variable for handling the screen code textbox. */
	private String code;

	/** Variable for handling the screen description textbox. */
	private String description;

	/** Variable for getting the last displayed page number. */
	private String lastPageNumber = "0";

	/** Variable for getting the displayed page. */
	private String displayPage = "1";

	/** Array for getting the checked priority. */
	private String[] priorityChecked;

	/** getter method for prioirtyChecked variable. */
	private String nextAction;
	
	//added by A-6841 for ICRD-181290 starts
	/** The source. */
	private String source;
	
	/** The multiselect. */
	private String multiselect;
	
	/** The form count. */
	private String formCount;
	
	/** The lov txt field name. */
	private String lovTxtFieldName;
	
	/** The lov description txt field name. */
	private String lovDescriptionTxtFieldName;
	
	/** The index. */
	private String index;
	
	/** The lovaction. */
	private String lovaction;
	
	/** The selected values. */
	private String selectedValues;
	//added by A-6841 for ICRD-181290 Ends
	
	/** The selected data. */
	private String selectedData = "N";


	/** The Constant BUNDLE. */
	private static final String BUNDLE = "PriorityLov"; // The key attribute specified in struts_config.xml file.

	/** The bundle. */
		private String bundle;


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
	 * Gets the selected data.
	 *
	 * @return Returns the selectedData.
	 */
	public String getSelectedData() {
		return selectedData;
	}

	/**
	 * Sets the selected data.
	 *
	 * @param selectedData The selectedData to set.
	 */
	public void setSelectedData(String selectedData) {
		this.selectedData = selectedData;
	}

	/**
	 * Gets the next action.
	 *
	 * @return Returns the nextAction.
	 */
	public String getNextAction() {
		return nextAction;
	}

	/**
	 * Sets the next action.
	 *
	 * @param nextAction The nextAction to set.
	 */
	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}

	/**
	 * Gets the priority checked.
	 *
	 * @return priorityChecked
	 */
	public String[] getPriorityChecked() {
		return priorityChecked;
	}

	/**
	 * setter Method for priorityChecked variable.
	 *
	 * @param priorityChecked the new priority checked
	 */
	public void setPriorityChecked(String[] priorityChecked) {
		this.priorityChecked = priorityChecked;
	}

	/**
	 * Method for getting the Description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Method for setting the Description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter method for code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Setter method for code.
	 *
	 * @param priorityCode the new code
	 */
	public void setCode(String priorityCode) {
		this.code = priorityCode;
	}

	/**
	 * Getter Method for displayPage.
	 *
	 * @return the display page
	 */
	public String getDisplayPage() {
		return displayPage;
	}

	/**
	 * Setter method for displayPage.
	 *
	 * @param displayPage the new display page
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	/**
	 * Getter method for lastPageNumebr.
	 *
	 * @return the last page number
	 */
	public String getLastPageNumber() {
		return lastPageNumber;
	}

	/**
	 * Setter method for lastPageNumber.
	 *
	 * @param lastPageNumber the new last page number
	 */
	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}

	/**
	 * The overiden function to return the screen id.
	 *
	 * @return products.defaults.maintainproduct
	 */
	public String getScreenId() {
		return "products.defaults.maintainproduct";
	}

	/**
	 * The overriden function to return the product name.
	 *
	 * @return products
	 */
	public String getProduct() {
		return "products";
	}

	/**
	 * The overriden function to return the sub product name.
	 *
	 * @return defaults
	 */
	public String getSubProduct() {
		return "defaults";
	}
	/**
	 * Gets the form count.
	 *
	 * @return the form count
	 */
	public String getFormCount() {
		return formCount;
	}
	/**
	 * Sets the form count.
	 *
	 * @param formCount the new form count
	 */
	public void setFormCount(String formCount) {
		this.formCount = formCount;
	}
	/**
	 * Gets the lov txt field name.
	 *
	 * @return the lov txt field name
	 */
	public String getLovTxtFieldName() {
		return lovTxtFieldName;
	}
	/**
	 * Sets the lov txt field name.
	 *
	 * @param lovTxtFieldName the new lov txt field name
	 */
	public void setLovTxtFieldName(String lovTxtFieldName) {
		this.lovTxtFieldName = lovTxtFieldName;
	}
	/**
	 * Gets the lov description txt field name.
	 *
	 * @return the lov description txt field name
	 */
	public String getLovDescriptionTxtFieldName() {
		return lovDescriptionTxtFieldName;
	}
	/**
	 * Sets the lov description txt field name.
	 *
	 * @param lovDescriptionTxtFieldName the new lov description txt field name
	 */
	public void setLovDescriptionTxtFieldName(String lovDescriptionTxtFieldName) {
		this.lovDescriptionTxtFieldName = lovDescriptionTxtFieldName;
	}
	/**
	 * Gets the index.
	 *
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}
	/**
	 * Sets the index.
	 *
	 * @param index the new index
	 */
	public void setIndex(String index) {
		this.index = index;
	}
	/**
	 * Gets the source.
	 *
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	/**
	 * Sets the source.
	 *
	 * @param source the new source
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * Gets the multiselect.
	 *
	 * @return the multiselect
	 */
	public String getMultiselect() { 
		return multiselect;
	}
	/**
	 * Sets the multiselect.
	 *
	 * @param multiselect the new multiselect
	 */
	public void setMultiselect(String multiselect) {
		this.multiselect = multiselect;
	}
	/**
	 * Gets the lovaction.
	 *
	 * @return the lovaction
	 */
	public String getLovaction() {
		return lovaction;
	}
	/**
	 * Sets the lovaction.
	 *
	 * @param lovaction the new lovaction
	 */
	public void setLovaction(String lovaction) {
		this.lovaction = lovaction;
	}
	/**
	 * Gets the selected values.
	 *
	 * @return the selected values
	 */
	public String getSelectedValues() {
		return selectedValues;
	}
	/**
	 * Sets the selected values.
	 *
	 * @param selectedValues the new selected values
	 */
	public void setSelectedValues(String selectedValues) {
		this.selectedValues = selectedValues;
	}

}
