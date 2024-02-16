/*
 * ProductCatalogueForm.java Created on Jun 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.products.defaults;


import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.framework.model.ScreenModel;


/**
 * @author A-1754
 *
 */
public class ProductCatalogueForm extends ScreenModel {

	private String code;
	private static final String BUNDLE = "productCatalogueResources"; // The key attribute specified in struts_config.xml file.
	private String bundle;
	private ProductVO productVO=null;
	private String productName = "";

	/**
	 * 
	 * @return
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * 
	 * @param productName
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
		/**
	 * @return Returns the productVO.
	 */
	public ProductVO getProductVO() {
		return productVO;
	}
	/**
	 * @param productVO The productVO to set.
	 */
	public void setProductVO(ProductVO productVO) {
		this.productVO = productVO;
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
}
