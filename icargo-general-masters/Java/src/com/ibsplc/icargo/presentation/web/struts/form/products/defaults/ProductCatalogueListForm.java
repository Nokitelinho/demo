/*
 * ProductCatalogueListForm.java Created on Jun 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.products.defaults;


import org.apache.struts.upload.FormFile;

import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


/**
 * @author A-1754
 *
 */
public class ProductCatalogueListForm extends ScreenModel {

	private String lastPageNumber="0";
	private String prdCode;
	private String displayPage="1";
	private String[] productChecked;
	private boolean iconChecked;
	private static final String BUNDLE = "productCatalogueListResources"; // The key attribute specified in struts_config.xml file.
	private String bundle;
	private boolean isProductIconPresent;
	private Page<ProductVO> pageProductCatalogue=null;
	private FormFile uploadFile ;
	
	// Added by A-5183 for < ICRD-22065 > Starts
	
	public static final String PAGINATION_MODE_FROM_LIST = "LIST";
	public static final String PAGINATION_MODE_FROM_NAVIGATION_LINK = "NAVIGATION";
	private String navigationMode;
	
	public String getNavigationMode() {
		return navigationMode;
	}

	public void setNavigationMode(String navigationMode) {
		this.navigationMode = navigationMode;
	}
		
	// Added by A-5183 for < ICRD-22065 > Ends
	
	public FormFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(FormFile uploadFile) {
		this.uploadFile = uploadFile;
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
	 * @return Returns the displayPage.
	 */
	public String getDisplayPage() {
		return displayPage;
	}
	/**
	 * @param displayPage The displayPage to set.
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	/**
	 * @return Returns the lastPageNumber.
	 */
	public String getLastPageNumber() {
		return lastPageNumber;
	}
	/**
	 * @param lastPageNumber The lastPageNumber to set.
	 */
	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
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
		 * @return Returns the pageProductCatalogue.
		 */
		public Page<ProductVO> getPageProductCatalogue() {
			return pageProductCatalogue;
		}
		/**
		 * @param pageProductCatalogue The pageProductCatalogue to set.
		 */
		public void setPageProductCatalogue(Page<ProductVO> pageProductCatalogue) {
			this.pageProductCatalogue = pageProductCatalogue;
		}
		/**
		 * @return Returns the productChecked.
		 */
		public String[] getProductChecked() {
			return productChecked;
		}
		/**
		 * @param productChecked The productChecked to set.
		 */
		public void setProductChecked(String[] productChecked) {
			this.productChecked = productChecked;
		}

		/**
		 * @return Returns the prdCode.
		 */
		public String getPrdCode() {
			return prdCode;
		}

		/**
		 * @param prdCode The prdCode to set.
		 */
		public void setPrdCode(String prdCode) {
			this.prdCode = prdCode;
		}

		/**
		 * @return Returns the isProductIconPresent.
		 */
		public boolean isProductIconPresent() {
			return isProductIconPresent;
		}
		/**
		 * 
		 * @return
		 */
		public boolean getIsProductIconPresent(){
			return (isProductIconPresent);
		}
		/**
		 * 
		 * @param isProductIconPresent
		 */
		public void setIsProductIconPresent(boolean isProductIconPresent){
			setProductIconPresent(isProductIconPresent);
		}
		/**
		 * @param isProductIconPresent The isProductIconPresent to set.
		 */
		public void setProductIconPresent(boolean isProductIconPresent) {
			this.isProductIconPresent = isProductIconPresent;
		}

		/**
		 * @return Returns the iconChecked.
		 */
		public boolean getIconChecked() {
			return iconChecked;
		}

		/**
		 * @param iconChecked The iconChecked to set.
		 */
		public void setIconChecked(boolean iconChecked) {
			this.iconChecked = iconChecked;
		}
}
