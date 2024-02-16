/*
 * ULDVO.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.vo.micro.server;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2052
 *
 */
public class ULDMicroVO extends AbstractVO {
    /**
     * module
     */
	public static final String MODULE ="uld";
	/**
	 * submodule
	 */
	public static final String SUBMODULE ="defaults";
	/**
	 * entity
	 */
	public static final String ENTITY ="uld.defaults.ULD";


    private String companyCode;

    private String uldNumber;

    private String currentStation;

    private String location;

    private String operationalFlag;

    private String remarks;

    private String locationType;

    private String lastUpdateUser;
	/**
	 * For optimistic locking
	 */
    private String lastUpdateTime;

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
	/**
	 * @return String Returns the locationType.
	 */
	public String getLocationType() {
		return this.locationType;
	}
	/**
	 * @param locationType The locationType to set.
	 */
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

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
     * @return Returns the currentStation.
     */
    public String getCurrentStation() {
        return currentStation;
    }
    /**
     * @param currentStation The currentStation to set.
     */
    public void setCurrentStation(String currentStation) {
        this.currentStation = currentStation;
    }

    /**
     * @return Returns the location.
     */
    public String getLocation() {
        return location;
    }
    /**
     * @param location The location to set.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return Returns the uldNumber.
     */
    public String getUldNumber() {
        return uldNumber;
    }
    /**
     * @param uldNumber The uldNumber to set.
     */
    public void setUldNumber(String uldNumber) {
        this.uldNumber = uldNumber;
    }

	/**
	 *
	 * @param operationalFlag The operationalFlag to be set.
	 */
	public void setOperationalFlag(String operationalFlag){
		this.operationalFlag=operationalFlag;
	}
	/**
	 *
	 * @return Returns the OperationalFlag
	 */
	public String getOperationalFlag(){
		return operationalFlag;
	}
	/**
	 * @return Returns the lastUpdateTime.
	 */
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * @param lastUpdateTime The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
