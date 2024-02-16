/*
 * ULDStockConfigVO.java Created on Dec 22, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.stock.vo;

import java.io.Serializable;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1496
 *
 */
public class ULDStockConfigVO  extends AbstractVO implements Serializable{
    /**
     * 
     */
	public static final String MODULE ="uld";
	/**
	 * 
	 */
	public static final String SUBMODULE ="defaults";
	/**
	 * 
	 */
	public static final String ENTITY ="uld.defaults.stock.ULDStockConfig";
    private String companyCode;
    private int airlineIdentifier;
    private String stationCode;
    
    private String uldTypeCode;
    private int minQty;
    private int maxQty;
	private LocalDate lastUpdatedTime;
	private String lastUpdatedUser;
	 private String operationFlag;
	 private String airlineCode;
	 private String uldNature;
	 
//added by ayswarya
	 
	    private long sequenceNumber;
	    
	    // Added by Preet
	    private String uldGroupCode;
		private int dwellTime;
		
		private String remarks;
	    
	    /**
		 * @return the remarks
		 */
		public String getRemarks() {
			return remarks;
		}
		/**
		 * @param remarks the remarks to set
		 */
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
		/**
		 * @return the dwellTime
		 */
		public int getDwellTime() {
			return dwellTime;
		}
		/**
		 * @param dwellTime the dwellTime to set
		 */
		public void setDwellTime(int dwellTime) {
			this.dwellTime = dwellTime;
		}
		/**
		 * @return the uldGroupCode
		 */
		public String getUldGroupCode() {
			return uldGroupCode;
		}
		/**
		 * @param uldGroupCode the uldGroupCode to set
		 */
		public void setUldGroupCode(String uldGroupCode) {
			this.uldGroupCode = uldGroupCode;
		}
		/**
		 * @return Returns the sequenceNumber.
		 */
		public long getSequenceNumber() {
			return sequenceNumber;
		}
		/**
		 * @param sequenceNumber The sequenceNumber to set.
		 */
		public void setSequenceNumber(long sequenceNumber) {
			this.sequenceNumber = sequenceNumber;
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
     * @return Returns the lastUpdatedTime.
     */
    public LocalDate getLastUpdatedTime() {
        return lastUpdatedTime;
    }
    /**
     * @param lastUpdatedTime The lastUpdatedTime to set.
     */
    public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }
    /**
     * @return Returns the lastUpdatedUser.
     */
    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }
    /**
     * @param lastUpdatedUser The lastUpdatedUser to set.
     */
    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }
    /**
     * @return Returns the maxQty.
     */
    public int getMaxQty() {
        return maxQty;
    }
    /**
     * @param maxQty The maxQty to set.
     */
    public void setMaxQty(int maxQty) {
        this.maxQty = maxQty;
    }
    /**
     * @return Returns the minQty.
     */
    public int getMinQty() {
        return minQty;
    }
    /**
     * @param minQty The minQty to set.
     */
    public void setMinQty(int minQty) {
        this.minQty = minQty;
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
	
}
