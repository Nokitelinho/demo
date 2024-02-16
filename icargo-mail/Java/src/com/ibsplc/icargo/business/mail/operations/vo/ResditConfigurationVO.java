/*
 * ResditConfigurationVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-3109
 *
 */
public class ResditConfigurationVO extends AbstractVO {
	
    private String companyCode;
    
    private int carrierId;
    
    private Collection<ResditTransactionDetailVO> resditTransactionDetails;
    
    /**
     * @return Returns the carrierId.
     */
    public int getCarrierId() {
        return carrierId;
    }
    /**
     * @param carrierId The carrierId to set.
     */
    public void setCarrierId(int carrierId) {
        this.carrierId = carrierId;
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
     * @return Returns the resditTransactionDetails.
     */
    public Collection<ResditTransactionDetailVO> getResditTransactionDetails() {
        return resditTransactionDetails;
    }
    /**
     * @param resditTransactionDetails The resditTransactionDetails to set.
     */
    public void setResditTransactionDetails(
    		Collection<ResditTransactionDetailVO> resditTransactionDetails) {
        this.resditTransactionDetails = resditTransactionDetails;
    }
}
