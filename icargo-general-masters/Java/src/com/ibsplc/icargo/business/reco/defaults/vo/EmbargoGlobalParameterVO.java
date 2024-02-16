/*
 * EmbargoParameterVO.java Created on Jul 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary 
 * information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1358
 *
 */
public class EmbargoGlobalParameterVO extends AbstractVO {

    private String companyCode;
       
    private String parameterCode;
    
	private String parameterDescription;
	
	/*
	 * Attaches the embargo with an entity in the system. This is
	 * inorder to perform validation of the parameter
	 */
	private String entityReference;
	
	/*
	 * Indicates whether the parameter is capable of holding multiple values
	 */
	private boolean isMultiple;
	
	/*
	 * For optimistic locking
	 */
	private LocalDate lastUpdateTime;
	
	/*
	 * For optimistic locking
	 */
	private String lastUpdateUser;     	
	
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
     * @return Returns the entityReference.
     */
    public String getEntityReference() {
        return entityReference;
    }
    
    /**
     * @param entityReference The entityReference to set.
     */
    public void setEntityReference(String entityReference) {
        this.entityReference = entityReference;
    }
    
    /**
     * @return Returns the isMultiple.
     */
    public boolean isMultiple() {
        return isMultiple;
    }
    
    /**
     * @param isMultiple The isMultiple to set.
     */
    public void setMultiple(boolean isMultiple) {
        this.isMultiple = isMultiple;
    }
    
    
    /**
     * @return Returns the parameterCode.
     */
    public String getParameterCode() {
        return parameterCode;
    }
    /**
     * @param parameterCode The parameterCode to set.
     */
    public void setParameterCode(String parameterCode) {
        this.parameterCode = parameterCode;
    }
    
    /**
     * @return Returns the parameterDescription.
     */
    public String getParameterDescription() {
        return parameterDescription;
    }
    
    /**
     * @param parameterDescription The parameterDescription to set.
     */
    public void setParameterDescription(String parameterDescription) {
        this.parameterDescription = parameterDescription;
    }
    
    /**
     * @return Returns the lastUpdateTime.
     */
    public LocalDate getLastUpdateTime() {
        return lastUpdateTime;
    }
    
    /**
     * @param lastUpdateTime The lastUpdateTime to set.
     */
    public void setLastUpdateTime(LocalDate lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
    
    /**
     * @return Returns the lastUpdateUser.
     */
    public String getLastUpdateUser() {
        return lastUpdateUser;
    }
    
    /**
     * @param lastUpdateUser The lastUpdateUser to set.
     */
    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }    
}
