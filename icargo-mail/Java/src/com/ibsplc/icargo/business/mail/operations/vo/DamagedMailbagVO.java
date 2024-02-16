/*
 * DamagedMailbagVO.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-5991
 *
 */
public class DamagedMailbagVO extends AbstractVO {
    private String companyCode;    
    private String dsn;
    private String originExchangeOffice;
    private String destinationExchangeOffice;
    private String mailClass;
    private int year; 
    private String mailbagId;
    private String damageCode;
    private String airportCode;
    private String damageDescription;
    private String userCode;
    private LocalDate damageDate;
    private String remarks;
    /*
     * I - inbound , O -outbound
     */
    private String operationType;
    private String operationFlag;
    
    private String returnedFlag;
    private String paCode;
    
    private String flightNumber;
    private String carrierCode;
  //added by A-5844 for ICRD-67196
    private String subClassCode; 
    private String subClassGroup; 
    //private double weight; 
    private Measure weight;//added by A-7371
    private double declaredValue; 
    private LocalDate flightDate; 
    private String damageType; 
    private String flightOrigin; 
    private String flightDestination; 
    private String currencyCode;
    private double declaredValueTot;
    private String totCurrencyCode;
    
  //Added by A-7929 as part of IASCB-35577 starts
  	private LocalDate lastUpdateTime;
    private String fileName;
 
  	
  	public LocalDate getLastUpdateTime() {
  		return lastUpdateTime;
  	}

  	public void setLastUpdateTime(LocalDate lastUpdateTime) {
  		this.lastUpdateTime = lastUpdateTime;
  	}

  	
  	//Added by A-7929 as part of IASCB-35577 ends
    /**
	 * @return the carrierCode
	 */
	public String getCarrierCode() {
		return carrierCode;
	}
	/**
	 * 
	 * @return weight
	 */
	public Measure getWeight() {
		return weight;
	}
	/**
	 * 
	 * @param weight
	 */
	public void setWeight(Measure weight) {
		this.weight = weight;
	}
	/**
	 * @param carrierCode the carrierCode to set
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	/**
	 * @return the flightNumber
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
	/**
	 * @param flightNumber the flightNumber to set
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	/**
	 * @return Returns the paCode.
	 */
	public String getPaCode() {
		return paCode;
	}
	/**
	 * @param paCode The paCode to set.
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	/**
	 * @return Returns the returnedFlag.
	 */
	public String getReturnedFlag() {
		return returnedFlag;
	}
	/**
	 * @param returnedFlag The returnedFlag to set.
	 */
	public void setReturnedFlag(String returnedFlag) {
		this.returnedFlag = returnedFlag;
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
     * @return Returns the airportCode.
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
     * @return Returns the damageCode.
     */
    public String getDamageCode() {
        return damageCode;
    }
    /**
     * @param damageCode The damageCode to set.
     */
    public void setDamageCode(String damageCode) {
        this.damageCode = damageCode;
    }
    /**
     * @return Returns the damageDate.
     */
    public LocalDate getDamageDate() {
        return damageDate;
    }
    /**
     * @param damageDate The damageDate to set.
     */
    public void setDamageDate(LocalDate damageDate) {
        this.damageDate = damageDate;
    }
    /**
     * @return Returns the damageDescription.
     */
    public String getDamageDescription() {
        return damageDescription;
    }
    /**
     * @param damageDescription The damageDescription to set.
     */
    public void setDamageDescription(String damageDescription) {
        this.damageDescription = damageDescription;
    }
    /**
     * @return Returns the destinationExchangeOffice.
     */
    public String getDestinationExchangeOffice() {
        return destinationExchangeOffice;
    }
    /**
     * @param destinationExchangeOffice The destinationExchangeOffice to set.
     */
    public void setDestinationExchangeOffice(String destinationExchangeOffice) {
        this.destinationExchangeOffice = destinationExchangeOffice;
    }
    /**
     * @return Returns the dsn.
     */
    public String getDsn() {
        return dsn;
    }
    /**
     * @param dsn The dsn to set.
     */
    public void setDsn(String dsn) {
        this.dsn = dsn;
    }
    /**
     * @return Returns the mailbagId.
     */
    public String getMailbagId() {
        return mailbagId;
    }
    /**
     * @param mailbagId The mailbagId to set.
     */
    public void setMailbagId(String mailbagId) {
        this.mailbagId = mailbagId;
    }
    /**
     * @return Returns the mailClass.
     */
    public String getMailClass() {
        return mailClass;
    }
    /**
     * @param mailClass The mailClass to set.
     */
    public void setMailClass(String mailClass) {
        this.mailClass = mailClass;
    }
    /**
     * @return Returns the operationType.
     */
    public String getOperationType() {
        return operationType;
    }
    /**
     * @param operationType The operationType to set.
     */
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
    /**
     * @return Returns the originExchangeOffice.
     */
    public String getOriginExchangeOffice() {
        return originExchangeOffice;
    }
    /**
     * @param originExchangeOffice The originExchangeOffice to set.
     */
    public void setOriginExchangeOffice(String originExchangeOffice) {
        this.originExchangeOffice = originExchangeOffice;
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
     * @return Returns the userCode.
     */
    public String getUserCode() {
        return userCode;
    }
    /**
     * @param userCode The userCode to set.
     */
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
    /**
     * @return Returns the year.
     */
    public int getYear() {
        return year;
    }
    /**
     * @param year The year to set.
     */
    public void setYear(int year) {
        this.year = year;
    }
	/**
	 * @return the subClassCode
	 */
	public String getSubClassCode() {
		return subClassCode;
	}
	/**
	 * @param subClassCode the subClassCode to set
	 */
	public void setSubClassCode(String subClassCode) {
		this.subClassCode = subClassCode;
	}
	/**
	 * @return the subClassGroup
	 */
	public String getSubClassGroup() {
		return subClassGroup;
	}
	/**
	 * @param subClassGroup the subClassGroup to set
	 */
	public void setSubClassGroup(String subClassGroup) {
		this.subClassGroup = subClassGroup;
	}
	/**
	 * @return the weight
	 */
	/*public double getWeight() {
		return weight;
	}
	*//**
	 * @param weight the weight to set
	 *//*
	public void setWeight(double weight) {
		this.weight = weight;
	}*/
	/**
	 * @return the declaredValue
	 */
	public double getDeclaredValue() {
		return declaredValue;
	}
	/**
	 * @param declaredValue the declaredValue to set
	 */
	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
	}
	/**
	 * @return the flightDate
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}
	/**
	 * @param flightDate the flightDate to set
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}
	/**
	 * @return the damageType
	 */
	public String getDamageType() {
		return damageType;
	}
	/**
	 * @param damageType the damageType to set
	 */
	public void setDamageType(String damageType) {
		this.damageType = damageType;
	}
	/**
	 * @return the flightOrigin
	 */
	public String getFlightOrigin() {
		return flightOrigin;
	}
	/**
	 * @param flightOrigin the flightOrigin to set
	 */
	public void setFlightOrigin(String flightOrigin) {
		this.flightOrigin = flightOrigin;
	}
	/**
	 * @return the flightDestination
	 */
	public String getFlightDestination() {
		return flightDestination;
	}
	/**
	 * @param flightDestination the flightDestination to set
	 */
	public void setFlightDestination(String flightDestination) {
		this.flightDestination = flightDestination;
    }
	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}
	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	/**
	 * @return the declaredValueTot
	 */
	public double getDeclaredValueTot() {
		return declaredValueTot;
	}
	/**
	 * @param declaredValueTot the declaredValueTot to set
	 */
	public void setDeclaredValueTot(double declaredValueTot) {
		this.declaredValueTot = declaredValueTot;
	}
	/**
	 * @return the totCurrencyCode
	 */
	public String getTotCurrencyCode() {
		return totCurrencyCode;
	}
	/**
	 * @param totCurrencyCode the totCurrencyCode to set
	 */
	public void setTotCurrencyCode(String totCurrencyCode) {
		this.totCurrencyCode = totCurrencyCode;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	
}
