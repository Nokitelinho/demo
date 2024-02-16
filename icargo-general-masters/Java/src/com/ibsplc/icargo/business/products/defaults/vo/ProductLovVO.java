/*
 * ProductEventVO.java Created on Jul 4, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults.vo;

import java.io.Serializable;

import java.util.Set;



import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.util.suggest.MasterTypeMapper;
import com.ibsplc.xibase.util.suggest.vo.SuggestRequestVO;
import com.ibsplc.xibase.util.suggest.vo.SuggestResponseVO;


/**
 * 
 * @author A-1358
 *
 */
public class ProductLovVO implements Serializable,MasterTypeMapper  {

	//private static final long serialVersionUID = 5731325278040158572L;

	private String productCode;

    private String productName;
    
    private LocalDate startDate;
    
    private LocalDate endDate;
    
    private Set<String> productScc;
    
    private Set<String> productTransportMode;
    
    private Set<String> productPriority;
    
    // Added as part of CR ICRD-237928 by A-8154
    private String overrideCapacity;
  
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
    
    public LocalDate getStartDate(){
    	return startDate;
    }
    
    public void setStartDate(LocalDate startDate){
    	this.startDate=startDate;
    }
    
    public LocalDate getEndDate(){
    	return endDate;
    }
    
    public void setEndDate(LocalDate endDate){
    	this.endDate=endDate;
    }
	public Set<String> getProductPriority() {
		return productPriority;
	}
	public void setProductPriority(Set<String> productPriority) {
		this.productPriority = productPriority;
	}
	public Set<String> getProductScc() {
		return productScc;
	}
	public void setProductScc(Set<String> productScc) {
		this.productScc = productScc;
	}
	public Set<String> getProductTransportMode() {
		return productTransportMode;
	}
	public void setProductTransportMode(Set<String> productTransportMode) {
		this.productTransportMode = productTransportMode;
	}
	
	public String getOverrideCapacity() {
		return overrideCapacity;
	}
	public void setOverrideCapacity(String overrideCapacity) {
		this.overrideCapacity = overrideCapacity;
	}
	@Override
	public SuggestResponseVO getMappedVO() {
		SuggestResponseVO responseVO=new SuggestResponseVO();
		responseVO.setCode(this.productName);
		responseVO.setDescription(this.productCode); 
		return responseVO;
	}
	@Override
	public void setMappingVO(SuggestRequestVO requestVO) {
		// TODO Auto-generated method stub
	}	
	
}
