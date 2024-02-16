/*
 * ULDStockConfigFilterVO.java Created on Dec 22, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.stock.vo;

import java.io.Serializable;
import java.util.Collection;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1496
 *
 */
public class ULDStockConfigFilterVO  extends AbstractVO implements Serializable{
    
    private String companyCode;
    private int airlineIdentifier;
    private String airlineCode;
    private String stationCode;
    private String uldGroupCode;
    private String uldTypeCode;
    private String uldNature;
    private boolean viewByNatureFlag;
    private int pageNumber;
    //added by nisha for AirNZ 315
    private String agentCode;
    private String  sort;
    //Added for CRQ_AirNZ511
    private Collection<String> airports;
    private Collection<String> uldTypes;
    private String printStatus;
    //Added as part of ICRD-334152
    private String ownerAirline;
    //Added as part of ICRD-334152
    private int ownerAirlineIdentifier;
    
    // Added by Preet for QF1012 
    private int absoluteIndex;
    
    //added by a-3045 for CR QF1012 starts
    /**
     * Level Type Airport
	 */
    public static final String LEVELTYPE_AIRPORT = "ARP";
    /**
     * Level Type Country
	 */
    public static final String LEVELTYPE_COUNTRY = "CNT";
    /**
     * Level Type Region
	 */
    public static final String LEVELTYPE_REGION = "REG";
    /**
     * Level Type Head-Quarters
	 */
    public static final String LEVELTYPE_HEADQUARTERS = "HDQ";
    /**
     * 
     */
    public static final String LEVELTYPE_LOCATION = "FAC";
    
    public static final String LEVELTYPE_AGENT = "AGT";
    //Added by A-8445 for CR IASCB-43732
    /**
     * Level Type Airport Group
	 */
    public static final String LEVELTYPE_AIRPORTGROUP = "ARPGRP";
    
    private String levelType;
    private String levelValue;
    //added by a-3045 for CR QF1012 ends  
    
    
    private int totalRecordsCount;//a-5505 for bug ICRD-123103   
    
    /**
	 * @return the airports
	 */
	public Collection<String> getAirports() {
		return airports;
	}
	/**
	 * @param airports the airports to set
	 */
	public void setAirports(Collection<String> airports) {
		this.airports = airports;
	}
	/**
	 * @return the uldTypes
	 */
	public Collection<String> getUldTypes() {
		return uldTypes;
	}
	/**
	 * @param uldTypes the uldTypes to set
	 */
	public void setUldTypes(Collection<String> uldTypes) {
		this.uldTypes = uldTypes;
	}
	/**
	 * @return Returns the viewByNatureFlag.
	 */
	public boolean getViewByNatureFlag() {
		return viewByNatureFlag;
	}
	/**
	 * @param viewByNatureFlag The viewByNatureFlag to set.
	 */
	public void setViewByNatureFlag(boolean viewByNatureFlag) {
		this.viewByNatureFlag = viewByNatureFlag;
	}
	/**
	 * @return Returns the uldNature.
	 */
	public String getUldNature() {
		return uldNature;
	}
	/**
	 * @param uldNature The uldNature to set.
	 */
	public void setUldNature(String uldNature) {
		this.uldNature = uldNature;
	}
	/**
     * @return Returns the airlineIdentifier.
     */
    public int getAirlineIdentifier() {
        return airlineIdentifier;
    }
    /**
     * @param airlineIdentifier The airlineIdentifier to set.
     */
    public void setAirlineIdentifier(int airlineIdentifier) {
        this.airlineIdentifier = airlineIdentifier;
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
     * @return Returns the uldGroupCode.
     */
    public String getUldGroupCode() {
        return uldGroupCode;
    }
    /**
     * @param uldGroupCode The uldGroupCode to set.
     */
    public void setUldGroupCode(String uldGroupCode) {
        this.uldGroupCode = uldGroupCode;
    }
    /**
     * @return Returns the uldTypeCode.
     */
    public String getUldTypeCode() {
        return uldTypeCode;
    }
    /**
     * @param uldTypeCode The uldTypeCode to set.
     */
    public void setUldTypeCode(String uldTypeCode) {
        this.uldTypeCode = uldTypeCode;
    }
	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}
	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	/**
	 * @return Returns the pageNumber.
	 */
	public int getPageNumber() {
		return pageNumber;
	}
	/**
	 * @param pageNumber The pageNumber to set.
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	/**
	 * @return Returns the agentCode.
	 */
	public String getAgentCode() {
		return agentCode;
	}
	/**
	 * @param agentCode The agentCode to set.
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	/**
	 * @return the absoluteIndex
	 */
	public int getAbsoluteIndex() {
		return absoluteIndex;
	}
	/**
	 * @param absoluteIndex the absoluteIndex to set
	 */
	public void setAbsoluteIndex(int absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
	}
	/**
	 * @return the levelType
	 */
	public String getLevelType() {
		return levelType;
	}
	/**
	 * @param levelType the levelType to set
	 */
	public void setLevelType(String levelType) {
		this.levelType = levelType;
	}
	/**
	 * @return the levelValue
	 */
	public String getLevelValue() {
		return levelValue;
	}
	/**
	 * @param levelValue the levelValue to set
	 */
	public void setLevelValue(String levelValue) {
		this.levelValue = levelValue;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getPrintStatus() {
		return printStatus;
	}
	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}
	public int getTotalRecordsCount() {
		return totalRecordsCount;
	}
	public void setTotalRecordsCount(int totalRecordsCount) {
		this.totalRecordsCount = totalRecordsCount;
	}
	public int getOwnerAirlineIdentifier() {
		return ownerAirlineIdentifier;
	}
	public void setOwnerAirlineIdentifier(int ownerAirlineIdentifier) {
		this.ownerAirlineIdentifier = ownerAirlineIdentifier;
	}
	public String getOwnerAirline() {
		return ownerAirline;
	}
	public void setOwnerAirline(String ownerAirline) {
		this.ownerAirline = ownerAirline;
	}
}
