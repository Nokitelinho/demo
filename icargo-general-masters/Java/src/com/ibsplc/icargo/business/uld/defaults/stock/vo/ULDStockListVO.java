/*
 * ULDStockListVO.java Created on Dec 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.stock.vo;

import java.io.Serializable;
import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1496
 *
 */
public class ULDStockListVO  extends AbstractVO implements Serializable{
    
    private String airlineCode;
    private int airlineIdentifier;
    private String stationCode;
    private String uldGroupCode;
    private String uldTypeCode;
    private int available;
    private int damaged;
    private int loaned;
    private int nonOperational;
    private int total;
    private int owned;
    private int maxQty;
    private int minQty;
    private String uldNature;
    private LocalDate stockDate;
    //Added by A-4393 for ICRD-269790
    private boolean discrepancyCheck;
    private Collection<ULDStockListVO> uldStockLists; 
    private int inStock;
    private int inFlight;
    private String levelValue;
    //added by a-3278 for CR QF1199 on 08Apr09
    private int balance;
    //a-3278 ends
    
    //Added as part of ICRD-334152
    private int ownerAirlineIdentifier;
    /*
     * Done for CR QF1012
     */
    private int systemAvailable;
    private int off;
    
    

    
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
	/**
	 * @return the inFlight
	 */
	public int getInFlight() {
		return inFlight;
	}
	/**
	 * @param inFlight the inFlight to set
	 */
	public void setInFlight(int inFlight) {
		this.inFlight = inFlight;
	}
	/**
	 * @return the inStock
	 */
	public int getInStock() {
		return inStock;
	}
	/**
	 * @param inStock the inStock to set
	 */
	public void setInStock(int inStock) {
		this.inStock = inStock;
	}
	/**
	 * @return the uldStockLists
	 */
	public Collection<ULDStockListVO> getUldStockLists() {
		return uldStockLists;
	}
	/**
	 * @param uldStockLists the uldStockLists to set
	 */
	public void setUldStockLists(Collection<ULDStockListVO> uldStockLists) {
		this.uldStockLists = uldStockLists;
	}
	/**
	 * @return the stockDate
	 */
	public LocalDate getStockDate() {
		return stockDate;
	}
	/**
	 * @param stockDate the stockDate to set
	 */
	public void setStockDate(LocalDate stockDate) {
		this.stockDate = stockDate;
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
     * @return Returns the available.
     */
    public int getAvailable() {
        return available;
    }
    /**
     * @param available The available to set.
     */
    public void setAvailable(int available) {
        this.available = available;
    }
    /**
     * @return Returns the damaged.
     */
    public int getDamaged() {
        return damaged;
    }
    /**
     * @param damaged The damaged to set.
     */
    public void setDamaged(int damaged) {
        this.damaged = damaged;
    }
    /**
     * @return Returns the loaned.
     */
    public int getLoaned() {
        return loaned;
    }
    /**
     * @param loaned The loaned to set.
     */
    public void setLoaned(int loaned) {
        this.loaned = loaned;
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
     * @return Returns the nonOperational.
     */
    public int getNonOperational() {
        return nonOperational;
    }
    /**
     * @param nonOperational The nonOperational to set.
     */
    public void setNonOperational(int nonOperational) {
        this.nonOperational = nonOperational;
    }
    /**
     * @return Returns the owned.
     */
    public int getOwned() {
        return owned;
    }
    /**
     * @param owned The owned to set.
     */
    public void setOwned(int owned) {
        this.owned = owned;
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
     * @return Returns the total.
     */
    public int getTotal() {
        return total;
    }
    /**
     * @param total The total to set.
     */
    public void setTotal(int total) {
        this.total = total;
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
	 * @return the off
	 */
	public int getOff() {
		return off;
	}
	/**
	 * @param off the off to set
	 */
	public void setOff(int off) {
		this.off = off;
	}
	/**
	 * @return the systemAvailable
	 */
	public int getSystemAvailable() {
		return systemAvailable;
	}
	/**
	 * @param systemAvailable the systemAvailable to set
	 */
	public void setSystemAvailable(int systemAvailable) {
		this.systemAvailable = systemAvailable;
	}
	/**
	 * @return the balance
	 */
	public int getBalance() {
		return balance;
	}
	/**
	 * @param balance the balance to set
	 */
	public void setBalance(int balance) {
		this.balance = balance;
	}
	/**
	 * 
	 * 	Method		:	ULDStockListVO.isDiscrepancyCheck
	 *	Added by 	:	A-4393 on 21-Jun-2018
	 * 	Used for 	:	ICRD-269790
	 *	Parameters	:	@return 
	 *	Return type	: 	boolean
	 */
	public boolean isDiscrepancyCheck() {
		return discrepancyCheck;
	}
	/**
	 * 
	 * 	Method		:	ULDStockListVO.setDiscrepancyCheck
	 *	Added by 	:	A-4393 on 21-Jun-2018
	 * 	Used for 	:	ICRD-269790
	 *	Parameters	:	@param discrepancyCheck 
	 *	Return type	: 	void
	 */
	public void setDiscrepancyCheck(boolean discrepancyCheck) {
		this.discrepancyCheck = discrepancyCheck;
	}
	//Added as part of ICRD-334152
	public int getOwnerAirlineIdentifier() {
		return ownerAirlineIdentifier;
	}
	public void setOwnerAirlineIdentifier(int ownerAirlineIdentifier) {
		this.ownerAirlineIdentifier = ownerAirlineIdentifier;
	}
}
