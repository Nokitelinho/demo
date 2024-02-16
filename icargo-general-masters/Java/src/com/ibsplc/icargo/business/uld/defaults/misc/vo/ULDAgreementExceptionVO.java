/*
 * ULDAgreementExceptionVO.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1496
 *
 */
public class ULDAgreementExceptionVO extends AbstractVO{

    private String agreementNumber;
    private LocalDate agreementFromDate;
    private LocalDate agreementToDate;

   /**
    * @return Returns the agreementNumber.
    */
    public String getAgreementNumber() {
	     return agreementNumber;
	}
	/**
	 * @param agreementNumber The agreementNumber to set.
	 */
    public void setAgreementNumber(String agreementNumber) {
	        this.agreementNumber = agreementNumber;
    }

	/**
	 *@return Returns the agreementFromDate.
	 */

    public LocalDate getAgreementFromDate() {
        return agreementFromDate;
    }
    /**
     * @param agreementFromDate The agreementFromDate to set.
     */
    public void setAgreementFromDate(LocalDate agreementFromDate) {
        this.agreementFromDate = agreementFromDate;
    }

    /**
     * @return Returns the agreementToDate.
     */
    public LocalDate getAgreementToDate() {
        return agreementToDate;
    }
    /**
     * @param agreementToDate The agreementToDate to set.
     */
    public void setAgreementToDate(LocalDate agreementToDate) {
        this.agreementToDate = agreementToDate;
    }

}
