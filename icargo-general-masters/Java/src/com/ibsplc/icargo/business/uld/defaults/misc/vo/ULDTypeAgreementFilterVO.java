/*
 * ULDTypeAgreementFilterVO.java Created on Dec 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;


import java.io.Serializable;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1496
 *
 */
public class ULDTypeAgreementFilterVO  extends AbstractVO
				implements Serializable{
    
    private String uldType;
    private String stationCode;
	/**
	 * @return Returns the stationCode.
	 */
	public String getStationCode() {
		return stationCode;
	}
	/**
	 * @param stationCode The stationCode to set.
	 */
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	/**
	 * @return Returns the uldType.
	 */
	public String getUldType() {
		return uldType;
	}
	/**
	 * @param uldType The uldType to set.
	 */
	public void setUldType(String uldType) {
		this.uldType = uldType;
	}
    
    
   
    

}
