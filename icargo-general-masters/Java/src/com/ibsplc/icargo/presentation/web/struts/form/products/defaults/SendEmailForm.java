/*
 * SendEmailForm.java Created on Jun 28, 2005
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
public class SendEmailForm extends ScreenModel {
	private String code="";
	private String name="";
	private String email="";
	private String friendName="";
	private String friendEmail="";

	private static final String BUNDLE = "sendEmailResources"; // The key attribute specified in struts_config.xml file.

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
	 * The overiden function to return the screen id
	 * @return String
	 */
		public String getScreenId() {
			return "products.defaults.catalogue";
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
		 * @return Returns the friendEmail.
		 */
		public String getFriendEmail() {
			return friendEmail;
		}
		/**
		 * @param friendEmail The friendEmail to set.
		 */
		public void setFriendEmail(String friendEmail) {
			this.friendEmail = friendEmail;
		}
		/**
		 * @return Returns the friendName.
		 */
		public String getFriendName() {
			return friendName;
		}
		/**
		 * @param friendName The friendName to set.
		 */
		public void setFriendName(String friendName) {
			this.friendName = friendName;
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
}
