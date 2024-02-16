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
public class ProductPriorityVO extends AbstractVO implements Serializable {

	private static final long serialVersionUID = 8848396595697816596L;

	private String priority;

	private String priorityDisplay;

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
     * @return Returns the priority.
     */
    public String getPriority() {
        return priority;
    }
    /**
     * @param priority The priority to set.
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }
    /**
	 * @return Returns the priority.
	 */
	public String getPriorityDisplay() {
		return priorityDisplay;
	}
	/**
	 * @param priority The priority to set.
	 */
	public void setPriorityDisplay(String priorityDisplay) {
		this.priorityDisplay = priorityDisplay;
    }
}
