/*
 * OfficeOfExchangeVO.java Created on Jun 30, 2016
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
public class OfficeOfExchangeVO extends AbstractVO {
    /**
     * Variable indicating the exchange office code
     */
    private String code;

    /**
     * Variable storing the exchange office description
     */
    private String codeDescription;

    /**
     * Variable indicating the countryCode
     */
    private String countryCode;

    /**
     * Variable indicating the city code
     */
    private String cityCode;

    /**
     * Flag indicating if the data is active or not
     */
    private boolean isActive;

    private String operationFlag;

    /**
     * Flag indicating if the data is active or not
     */
    private String officeCode;

    /**
     * The postal administration code
     */
    private String poaCode;
  //Added as part of CRQ ICRD-111886 by A-5526
    private String mailboxId;
    
    
    private String companyCode;
    
    private LocalDate lastUpdateTime;
    
    private String lastUpdateUser;
    
    //a-5945 starts
    private String airportCode;
    //a-5945 ends
    /**
     * @return Returns the cityCode.
     */
    public String getCityCode() {
        return cityCode;
    }
    /**
     * @param cityCode The cityCode to set.
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
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
     * @return Returns the codeDescription.
     */
    public String getCodeDescription() {
        return codeDescription;
    }
    /**
     * @param codeDescription The codeDescription to set.
     */
    public void setCodeDescription(String codeDescription) {
        this.codeDescription = codeDescription;
    }
    /**
     * @return Returns the countryCode.
     */
    public String getCountryCode() {
        return countryCode;
    }
    /**
     * @param countryCode The countryCode to set.
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    /**
     * @return Returns the isActive.
     */
    public boolean isActive() {
        return isActive;
    }
    /**
     * @param isActive The isActive to set.
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    /**
     * @return Returns the officeCode.
     */
    public String getOfficeCode() {
        return officeCode;
    }
    /**
     * @param officeCode The officeCode to set.
     */
    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
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
    /**
     * @return Returns the poaCode.
     */
    public String getPoaCode() {
        return poaCode;
    }
    /**
     * @param poaCode The poaCode to set.
     */
    public void setPoaCode(String poaCode) {
        this.poaCode = poaCode;
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
	//A-5945 starts
	/**
	 * @return airportCode
	 */
	public String getAirportCode() {
		return airportCode;
	}
	
	/**
     * @param airportCode The airportCode to set.
     */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	//A-5945 ends
	
	 public String getMailboxId() {
			return mailboxId;
		}
		public void setMailboxId(String mailboxId) {
			this.mailboxId = mailboxId;
		}
	
}
