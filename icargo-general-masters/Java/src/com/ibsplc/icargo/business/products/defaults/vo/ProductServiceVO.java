/*
 * ProductServiceVO.java Created on Jul 4, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults.vo;

import java.io.Serializable;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-1358
 *
 */

public class ProductServiceVO extends AbstractVO implements Serializable {

	//private static final long serialVersionUID = 1435014433012280768L;

	private String companyCode;

    private String productCode;
    
    /**
     * Possible values are 'I-Insert','U-Update' and 'D-Delete'
     */
    private String operationFlag;

    private String serviceCode;
    
    private String serviceName;

    private String serviceDescription;
    
    private boolean transportationPlanExist;

   /**
	 * @return Returns the transportationPlanExist.
	 */
	public boolean isTransportationPlanExist() {
		return transportationPlanExist;
	}

	/**
	 * @param transportationPlanExist The transportationPlanExist to set.
	 */
	public void setTransportationPlanExist(boolean transportationPlanExist) {
		this.transportationPlanExist = transportationPlanExist;
	}

/**
     * @return Returns the companyCode.
     */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
     * @param companyCode The companyCode to set.
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

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
     * @return Returns the serviceCode.
     */
    public String getServiceCode() {
        return serviceCode;
    }

    /**
     * @param serviceCode The serviceCode to set.
     */
    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }
    
    /**
     * @return Returns the serviceName.
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName The serviceName to set.
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    /**
     * @return Returns the serviceDescription.
     */
    public String getServiceDescription() {
        return serviceDescription;
    }

    /**
     * @param serviceDescription The serviceDescription to set.
     */
    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
}
