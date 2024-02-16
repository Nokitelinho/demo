/*
 * BlacklistStockVO.java Created on Sep 8, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;


import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1366
 *
 */

public class BlacklistStockVO extends AbstractVO {
    
	/**
	 * constant for stock holder audit product name
	 */
	public static final String STOCKHOLDER_AUDIT_PRODUCTNAME = "stockcontrol";
    /**
     * constant for stock holder audit module name
     */
	public static final String STOCKHOLDER_AUDIT_MODULENAME = "defaults";
	/**
	 * constant for stock holder entity name
	 */
	public static final String STOCKHOLDER_AUDIT_ENTITYNAME = 
		"com.ibsplc.icargo.business.stockcontrol.defaults.BlacklistStock";
    /**
	 * Added by A-2881 for ICRD-3082
	 */
	public static final String ACTION_BLACKLIST="B";
	/**
	 * Added by A-2881 for ICRD-3082
	 */
	public static final String ACTION_VOID="V";
    /**
     * Company Code
     */
    private String companyCode;
    
    /**
     * Stock Holder Code
     */
    private String stockHolderCode;
    
    //Added by A-3791 for ICRD-110209
    /**
     * First Level Stock Holder 
     */
    private String firstLevelStockHolder;
    /**
     * Second Level Stock Holder
     */
    private String secondLevelStockHolder;
    /**
     * Document Type
     */
    private String documentType;
    
    /**
     * Document Sub Type
     */
    private String documentSubType;
    
    /**
     * Range From
     */
    private String rangeFrom;
    
    /**
     * Range To
     */
    private String rangeTo;
    
    /**
     * Remarks
     */
    private String remarks;
    
    /**
     * Date of blacklisting
     */
    private LocalDate blacklistDate;
    
    /**
     * lastUpdateTime
     */
    private LocalDate lastUpdateTime;
    /**
     * lastUpdateUser
     */
    private String lastUpdateUser;
    
    /**
     * newRangeFrom
     */
    private String newRangeFrom;
    /**
     * newRangeTo
     */
    private String newRangeTo;
    /**
     * airline Identifier
     */
    private int airlineIdentifier;
    /**
     * station Code
     */
    private String stationCode;
    
    /**
     * status
     */
    private String status;
    
    /**
     * asciiRangeFrom
     */
    private Long asciiRangeFrom;
    
    /**
     * asciiRangeTo
     */
    private Long asciiRangeTo;
    
    /**
     * Flag to indicate Void/Blacklist
     */
    private String actionCode;
    
    /**
     * agent code to be set from payment advice
     */
    private String agentCode;
    
    /**
     * stockVO returned as part of validating the range
     * for voiding - Validating in STKRNG
     */
    private StockVO stockVO;
    
    /**
     * TransitStockVO returned as part of validating the range 
     * for voiding - validating in Transit Range
     */
    private TransitStockVO transitStockVO;
    
    /**
     * Added by A-2881 for ICRD-3082
     */
    private double voidingCharge;
	
	private String currencyCode;
	/**
	 * is manual
	 * Added By A-7373 for ICRD-241944
	 */
	private boolean isManual;
	 /**
	 * Added by A-4820 for ICRD-20320
	 */
	private boolean isBlacklistStock;
	public boolean isBlacklistStock() {
		return isBlacklistStock;
	}
	public void setBlacklistStock(boolean isBlacklistStock) {
		this.isBlacklistStock = isBlacklistStock;
	}
	
	 /**
     * Method for getting isManual flag
	 * @return isManual
     */
	public boolean isManual() {
		return isManual;
	}
	public void setManual(boolean isManual) {
		this.isManual = isManual;
	}
	
	private boolean isRevokeBlacklist;
	public boolean isRevokeBlacklist() {
		return isRevokeBlacklist;
	}
	public void setRevokeBlacklist(boolean isRevokeBlacklist) {
		this.isRevokeBlacklist = isRevokeBlacklist;
	}
    /**
     * Default Constructor
     *
     */
    public BlacklistStockVO(){
        
    }
    
    /**
     * Constructor for copyingVO
     * @param blacklistStockVO
     */
	public BlacklistStockVO(BlacklistStockVO blacklistStockVO) {
		this.setActionCode(blacklistStockVO.getActionCode());
		this.setAgentCode(blacklistStockVO.getAgentCode());
		this.setAirlineIdentifier(blacklistStockVO.getAirlineIdentifier());
		this.setAsciiRangeFrom(blacklistStockVO.getAsciiRangeFrom());
		this.setAsciiRangeTo(blacklistStockVO.getAsciiRangeTo());
		this.setBlacklistDate(blacklistStockVO.getBlacklistDate());
		this.setCompanyCode(blacklistStockVO.getCompanyCode());
		this.setCurrencyCode(blacklistStockVO.getCurrencyCode());
		this.setDocumentSubType(blacklistStockVO.getDocumentSubType());
		this.setDocumentType(blacklistStockVO.getDocumentType());
		this.setLastUpdateTime(blacklistStockVO.getLastUpdateTime());
		this.setLastUpdateUser(blacklistStockVO.getLastUpdateUser());
		this.setNewRangeFrom(blacklistStockVO.getNewRangeFrom());
		this.setNewRangeTo(blacklistStockVO.getNewRangeTo());
		this.setRangeFrom(blacklistStockVO.getRangeFrom());
		this.setRangeTo(blacklistStockVO.getRangeTo());
		this.setRemarks(blacklistStockVO.getRemarks());
		this.setStationCode(blacklistStockVO.getStationCode());
		this.setStationCode(blacklistStockVO.getStationCode());
		this.setStatus(blacklistStockVO.getStatus());
		this.setStockHolderCode(blacklistStockVO.getStockHolderCode());
		this.setFirstLevelStockHolder(blacklistStockVO.getFirstLevelStockHolder());
		this.setSecondLevelStockHolder(blacklistStockVO.getSecondLevelStockHolder());
		this.setStockVO(blacklistStockVO.getStockVO());
		if (blacklistStockVO.getTransitStockVO() != null) {
			this.setTransitStockVO(new TransitStockVO(blacklistStockVO
					.getTransitStockVO()));
		}
		this.setVoidingCharge(blacklistStockVO.getVoidingCharge());
	}
    
    /**
     * @return Returns the blacklistDate.
     */
    public LocalDate getBlacklistDate() {
        return blacklistDate;
    }
    /**
     * @param blacklistDate The blacklistDate to set.
     */
    public void setBlacklistDate(LocalDate blacklistDate) {
        this.blacklistDate = blacklistDate;
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
     * @return Returns the documentSubType.
     */
    public String getDocumentSubType() {
        return documentSubType;
    }
    /**
     * @param documentSubType The documentSubType to set.
     */
    public void setDocumentSubType(String documentSubType) {
        this.documentSubType = documentSubType;
    }
    /**
     * @return Returns the documentType.
     */
    public String getDocumentType() {
        return documentType;
    }
    /**
     * @param documentType The documentType to set.
     */
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
    /**
     * @return Returns the rangeFrom.
     */
    public String getRangeFrom() {
        return rangeFrom;
    }
    /**
     * @param rangeFrom The rangeFrom to set.
     */
    public void setRangeFrom(String rangeFrom) {
        this.rangeFrom = rangeFrom;
    }
    /**
     * @return Returns the rangeTo.
     */
    public String getRangeTo() {
        return rangeTo;
    }
    /**
     * @param rangeTo The rangeTo to set.
     */
    public void setRangeTo(String rangeTo) {
        this.rangeTo = rangeTo;
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
     * @return Returns the stockHolderCode.
     */
    public String getStockHolderCode() {
        return stockHolderCode;
    }
    /**
     * @param stockHolderCode The stockHolderCode to set.
     */
    public void setStockHolderCode(String stockHolderCode) {
        this.stockHolderCode = stockHolderCode;
    }
    /**
	 * @return the firstLevelStockHolder
	 */
	public String getFirstLevelStockHolder() {
		return firstLevelStockHolder;
	}
	/**
	 * @param firstLevelStockHolder the firstLevelStockHolder to set
	 */
	public void setFirstLevelStockHolder(String firstLevelStockHolder) {
		this.firstLevelStockHolder = firstLevelStockHolder;
	}
	/**
	 * @return the secondLevelStockHolder
	 */
	public String getSecondLevelStockHolder() {
		return secondLevelStockHolder;
	}
	/**
	 * @param secondLevelStockHolder the secondLevelStockHolder to set
	 */
	public void setSecondLevelStockHolder(String secondLevelStockHolder) {
		this.secondLevelStockHolder = secondLevelStockHolder;
    }
    /**
     * Method for getting last update time
     * @return lastUpdateTime
     */
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * Method for setting last update time
	 * @param lastUpdateTime
	 */
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * Method for getting last update user
	 * @return lastUpdateUser
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	/**
	 * Method for setting last update user
	 * @param lastUpdateUser
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	/**
	 * Method for getting the new start range
	 * @return newRangeFrom
	 */
	public String getNewRangeFrom() {
		return newRangeFrom;
	}
	/**
	 * Method for setting new start range
	 * @param newRangeFrom
	 */
	public void setNewRangeFrom(String newRangeFrom) {
		this.newRangeFrom = newRangeFrom;
	}
	/**
	 * Method for getting new end range
	 * @return newRangeTo
	 */
	public String getNewRangeTo() {
		return newRangeTo;
	}
	/**
	 * Method for setting new end range
	 * @param newRangeTo
	 */
	public void setNewRangeTo(String newRangeTo) {
		this.newRangeTo = newRangeTo;
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
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return
	 */
	public Long getAsciiRangeFrom() {
		return asciiRangeFrom;
	}

	/**
	 * @param asciiRangeFrom
	 */
	public void setAsciiRangeFrom(Long asciiRangeFrom) {
		this.asciiRangeFrom = asciiRangeFrom;
	}

	/**
	 * @return
	 */
	public Long getAsciiRangeTo() {
		return asciiRangeTo;
	}

	/**
	 * @param asciiRangeTo
	 */
	public void setAsciiRangeTo(Long asciiRangeTo) {
		this.asciiRangeTo = asciiRangeTo;
	}

	/**
	 * @return the actionCode
	 */
	public String getActionCode() {
		return actionCode;
	}

	/**
	 * @param actionCode the actionCode to set
	 */
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	/**
	 * @return the agentCode
	 */
	public String getAgentCode() {
		return agentCode;
	}

	/**
	 * @param agentCode the agentCode to set
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**
	 * @return the stockVO
	 */
	public StockVO getStockVO() {
		return stockVO;
	}

	/**
	 * @param stockVO the stockVO to set
	 */
	public void setStockVO(StockVO stockVO) {
		this.stockVO = stockVO;
	}

	/**
	 * @return the transitStockVO
	 */
	public TransitStockVO getTransitStockVO() {
		return transitStockVO;
	}

	/**
	 * @param transitStockVO the transitStockVO to set
	 */
	public void setTransitStockVO(TransitStockVO transitStockVO) {
		this.transitStockVO = transitStockVO;
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
	 * @return the voidingCharge
	 */
	public double getVoidingCharge() {
		return voidingCharge;
	}

	/**
	 * @param voidingCharge the voidingCharge to set
	 */
	public void setVoidingCharge(double voidingCharge) {
		this.voidingCharge = voidingCharge;
	}

	
}
