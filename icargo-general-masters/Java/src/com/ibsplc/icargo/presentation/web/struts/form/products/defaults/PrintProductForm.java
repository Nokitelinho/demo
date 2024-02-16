/*
 * PrintProductForm.java Created on July 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.products.defaults;


import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2046
 *
 */
public class PrintProductForm extends ScreenModel{

private String companyCode;
private String productCode;
private String hasPreview;

/**
 * 
 * @return hasPreview
 */
public String getHasPreview(){
	return hasPreview;
}

public void setHasPreview(String hasPreview){
	this.hasPreview = hasPreview;
}
/**
 * 
 * @return companyCode
 */
public String getCompanyCode(){
return companyCode;
}
public void setCompanyCode(String companyCode){
this.companyCode=companyCode;
}
/**
 * 
 * @return productCode
 */
public String getProductCode(){
return productCode;
}
public void setProductCode(String productCode){
this.productCode=productCode;
}
/**
 *@return String
 */
public String getScreenId() {
return "products.defaults.catalogue";
}
	
/**
 * The overriden function to return the product name
 * @return String
 */ 
public String getProduct() {
return "products";
}
/**
 * The overriden function to return the sub product name
 */		
public String getSubProduct() {
return "defaults";
}
}