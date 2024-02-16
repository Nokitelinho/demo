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

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1358
 *
 */
public class ProductSCCVO extends AbstractVO implements Serializable {

    private static final long serialVersionUID = -7552449930105767564L;

	private String scc;

    /**
     * Possible values are 'I-Insert','U-Update' and 'D-Delete'
     */
    private String operationFlag;

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
     * @return Returns the scc.
     */
    public String getScc() {
        return scc;
    }
    /**
     * @param scc The scc to set.
     */
    public void setScc(String scc) {
        this.scc = scc;
    }
}
