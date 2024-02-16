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
public class RestrictionStationVO extends AbstractVO implements Serializable {
	
	private static final long serialVersionUID = 705113661515854551L;
	
	private String station;
	
	/**
	 * Possible values are 'I-Insert','U-Update' and 'D-Delete'
	 */
	private String operationFlag;
	
	private boolean isRestricted;
	
	private boolean isOrigin;
	
	
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
	 * @return Returns the station.
	 */
	public String getStation() {
		return station;
	}
	/**
	 * @param station The station to set.
	 */
	public void setStation(String station) {
		this.station = station;
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
	/**
	 * 
	 * @return boolean
	 */
	public boolean getIsOrigin() {
		return isOrigin;
	}
	/**
	 * @param isOrigin 
	 */
	public void setIsOrigin(boolean isOrigin) {
		this.isOrigin = isOrigin;
	}
	
}
