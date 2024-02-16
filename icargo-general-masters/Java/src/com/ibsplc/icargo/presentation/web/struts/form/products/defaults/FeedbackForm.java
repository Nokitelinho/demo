/*
 * FeedbackForm.java Created on Jun 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.products.defaults;


import com.ibsplc.icargo.framework.model.ScreenModel;


/**
 * @author A-1754
 *
 */
public class FeedbackForm extends ScreenModel {

	private String name="";
	private String email="";
	private String address="";
	private String code="";
	private String comments="";
	private String saveSuccessful="N";
	private String mode="N";

	private static final String BUNDLE = "feedbackResources"; // The key attribute specified in struts_config.xml file.

		private String bundle;


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
		 * @return Returns the address.
		 */
		public String getAddress() {
			return address;
		}
		/**
		 * @param address The address to set.
		 */
		public void setAddress(String address) {
			this.address = address;
		}
		/**
		 * @return Returns the comments.
		 */
		public String getComments() {
			return comments;
		}
		/**
		 * @param comments The comments to set.
		 */
		public void setComments(String comments) {
			this.comments = comments;
		}
		/**
		 * @return Returns the email.
		 */
		public String getEmail() {
			return email;
		}
		/**
		 * @param email The email to set.
		 */
		public void setEmail(String email) {
			this.email = email;
		}
		/**
		 * @return Returns the name.
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name The name to set.
		 */
		public void setName(String name) {
			this.name = name;
		}
	/**
	 * The overiden function to return the screen id
	 * @return String
	 */
		public String getScreenId() {
			return "products.defaults.feeback";
		}
	/**
	 * The overriden function to return the product name
	 *  @return String
	 */
		public String getProduct() {
			return "products";
		}
		/**
		 * The overriden function to return the sub product name
		 *  @return String
		 */
		public String getSubProduct() {
			return "defaults";
		}
		/**
		 * @return Returns the code.
		 */
		public String getCode() {
			return code;
		}
		/**
		 * @param code The code to set.
		 */
		public void setCode(String code) {
			this.code = code;
		}
		/**
		 * @return Returns the mode.
		 */
		public String getMode() {
			return mode;
		}
		/**
		 * @param mode The mode to set.
		 */
		public void setMode(String mode) {
			this.mode = mode;
		}
		/**
		 * @return Returns the saveSuccessful.
		 */
		public String getSaveSuccessful() {
			return saveSuccessful;
		}
		/**
		 * @param saveSuccessful The saveSuccessful to set.
		 */
		public void setSaveSuccessful(String saveSuccessful) {
			this.saveSuccessful = saveSuccessful;
		}
		
}
