/*
 * MailSubClassVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-3109
 *
 */
public class MailSubClassVO extends AbstractVO {
    /**
     * The mail subclass code
     */
     private String code;

     /**
     * The description for the mail subclass code
     */
     private String description;

     
     private String operationFlag;
     
     private String companyCode;
     
     private String subClassGroup;
     
     private LocalDate lastUpdateTime;
     
     private String lastUpdateUser;

    /**
	 * @return Returns the subClassGroup.
	 */
	public String getSubClassGroup() {
		return subClassGroup;
	}
	/**
	 * @param subClassGroup The subClassGroup to set.
	 */
	public void setSubClassGroup(String subClassGroup) {
		this.subClassGroup = subClassGroup;
	}
	/**
     * @return companyCode
     */
    public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
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
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
}
