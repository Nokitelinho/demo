/*
 * CheckAvailabilityForm.java Created on Jun 28, 2005
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
public class CheckAvailabilityForm extends ScreenModel {

	private String origin="";
	private String destination="";
	private String commodity="";
	private String code="";
	private String name="";

	private static final String BUNDLE = "checkAvailabilityResources"; // The key attribute specified in struts_config.xml file.

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
		 * @return Returns the commodity.
		 */
		public String getCommodity() {
			return commodity;
		}
		/**
		 * @param commodity The commodity to set.
		 */
		public void setCommodity(String commodity) {
			this.commodity = commodity;
		}
		/**
		 * @return Returns the destination.
		 */
		public String getDestination() {
			return destination;
		}
		/**
		 * @param destination The destination to set.
		 */
		public void setDestination(String destination) {
			this.destination = destination;
		}
		/**
		 * @return Returns the origin.
		 */
		public String getOrigin() {
			return origin;
		}
		/**
		 * @param origin The origin to set.
		 */
		public void setOrigin(String origin) {
			this.origin = origin;
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
		 * 
		 * @return
		 */
		public String getName() {
			return name;
		}
		/**
		 * 
		 * @param name
		 */
		public void setName(String name) {
			this.name = name;
		}
}
