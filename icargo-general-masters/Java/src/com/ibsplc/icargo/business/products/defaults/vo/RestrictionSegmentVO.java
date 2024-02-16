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
public class RestrictionSegmentVO extends AbstractVO implements Serializable {

    private static final long serialVersionUID = 6664899378366619366L;

	private String origin;

    private String destination;

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
     * @return String 
     */
    public String getOrigin() {
        return origin;
    }
    /**
     * @param origin The segment to set.
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }
/**
 * 
 * @return String
 */
    public String getDestination() {
	    return destination;
    }
/**
 * 
 * @param destination
 */
	public void setDestination(String destination) {
	   this.destination = destination;
    }
    /**
	 * @return Returns the operationFlag.
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
	/**
	 * @return int
	 */
	public int hashCode() {
		return new StringBuilder().append(origin)
			.append(destination).append(isRestricted).append(operationFlag).toString().hashCode();
	}
/**
 * @param other
 * @return boolean
 */
	public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}
}
