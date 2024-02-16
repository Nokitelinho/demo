/*
 * ProductsDefaultsAuditVO.java Created on Aug 23, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults.vo;

import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;


/**
 * @author A-1366
 *
 */
public class ProductsDefaultsAuditVO extends AuditVO {

    /*
     * Audit Action code of inserting product
     */
    public static final String PRODUCT_INSERT_ACTIONCODE = "PRDDEFINS";

    /*
     * Audit Action code of updating product
     */
    public static final String PRODUCT_UPDATE_ACTIONCODE = "PRDDEFUPD";

    /*
     * Audit Action code of deleting product
     */
    public static final String PRODUCT_DELETE_ACTIONCODE = "PRDDEFDEL";

    /*
     * Audit Action code of inserting sub product
     */
    public static final String SUBPRODUCT_INSERT_ACTIONCODE = "PRDSUBINS";

    /*
     * Audit Action code of updating product
     */
    public static final String SUBPRODUCT_UPDATE_ACTIONCODE = "PRDSUBUPD";

    /*
     * Audit Action code of deleting product
     */
    public static final String SUBPRODUCT_DELETE_ACTIONCODE = "PRDSUBDEL";
    
    public static final String PRODUCT_CREATE = "PRDDEFAUDCRT";
    
    public static final String PRODUCT_UPDATE = "PRDDEFAUDUPD";

    /**
     * Company Code
     */
    private String companyCode;

    /**
     * Product Code
     */
    private String productCode;

    /**
     * Additional Information
     */
    private String additionalInfo;

    
    private String productName;

    /**
     * This constructor invokes the constructor of the super class - AuditVO
     * @param product
     * @param moduleName
     * @param entityName
     */
    public ProductsDefaultsAuditVO(String product, String moduleName,
            String entityName){
    	 super(product,moduleName,entityName);
    	
    }

    /**
     * @return Returns the additionalInfo.
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }
    /**
     * @param additionalInfo The additionalInfo to set.
     */
    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
    /**
     * @return Returns the companyCode.
     */
    public String getCompanyCodes() {
        return companyCode;
    }
    /**
     * @param companyCode The companyCode to set.
     */
   /* public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }*/
    
    /**
     * @return Returns the productCode.
     */
    public String getProductCode() {
        return productCode;
    }
    /**
     * @param productCode The productCode to set.
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

	/**
	 * @return Returns the productName.
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName The productName to set.
	 */
	public void setProductName(String productName) {
		this.productName = productName;
    }
    
    
}
