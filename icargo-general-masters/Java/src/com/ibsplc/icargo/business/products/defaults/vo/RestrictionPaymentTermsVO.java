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
public class RestrictionPaymentTermsVO extends AbstractVO implements Serializable {

    private static final long serialVersionUID = 8540193441092655783L;

	private String paymentTerm;

    /**
     * Possible values are 'I-Insert','U-Update' and 'D-Delete'
     */
    private String operationFlag;

	private boolean isRestricted;

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
    /**
     * @return Returns the paymentTerm.
     */
    public String getPaymentTerm() {
        return paymentTerm;
    }
    /**
     * @param paymentTerm The paymentTerm to set.
     */
    public void setPaymentTerm(String paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    /**
	 * @return boolean
	 */
	public boolean getIsRestricted() {
		return isRestricted;
	}
	/**
	 * @param isRestricted
	 */
	public void setIsRestricted(boolean isRestricted) {
		this.isRestricted = isRestricted;
    }
}
