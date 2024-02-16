/*
 * ProductFilterVO.java Created on Jun 28, 2005
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
public class RestrictionCommodityVO extends AbstractVO implements Serializable{
	
	private static final long serialVersionUID = 896956395476583173L;
	
	private String commodity;
	
	/**
	 * Possible values are 'I-Insert','U-Update' and 'D-Delete'
	 */
	private String operationFlag;
	
	private boolean isRestricted;
	
	private String sccCode;
	/**
	 * @return Returns the sccCode.
	 */
	public String getSccCode() {
		return sccCode;
	}
	/**
	 * @param sccCode The sccCode to set.
	 */
	public void setSccCode(String sccCode) {
		this.sccCode = sccCode;
	}
	/**
	 * @return Returns the commodity.
	 */
	public String getCommodity() {
		return commodity;
	}
	/**
	 * @param commodity The commodity to set.
	 */
	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	/**
	 * @return String  returns the operationFlag.
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
