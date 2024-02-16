/*
 * FormOneVO.java Created on July 13, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling.vo;



import java.util.Collection;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;

/**
 * @author A-3434
 *
 */

public class FormOneVO extends AbstractVO {

    private String companyCode;

    private int airlineIdr;

    private String airlineCode;

    private String airlineNumber;

    private String clearancePeriod;

    private String interlineBillingType;

    private String classType;

    private double exchangeRateBillingCurrency;

    private double exchangeRateListingCurrency;

    private String listingCurrency;

    private boolean webIchFlag;

    private String billingCurrency;

    private Money  baseTotalAmt;

    private Money  listingTotalAmt;

    private Money  billingTotalAmt;

    private String invStatus;
    
    private String formOneStatus;

    private String operationFlag;

    private Collection<InvoiceInFormOneVO> invoiceInFormOneVOs;

    // added by sony for Clearance House Form1 report generation
    private String airlineName;

    private String ownAirlineNumber;

    private String ownAirlineCode;

    private String ownAirlineName;

    private String zone;

    private String billingType;

    private Money  passengerAmount;

    private Money  cargoAmount;

    private Money  utpAmount;

    private Money  missAmount;
    
    /* Added by A-2414 for optimistic locking */
	private String lastUpdateUser;
	private LocalDate lastUpdateTime;
	private String lastUpdateUserBlg;

	private LocalDate lastUpdateTimeBlg;

    /**
	 * @return the lastUpdateTimeBlg
	 */
	public LocalDate getLastUpdateTimeBlg() {
		return lastUpdateTimeBlg;
	}

	/**
	 * @param lastUpdateTimeBlg the lastUpdateTimeBlg to set
	 */
	public void setLastUpdateTimeBlg(LocalDate lastUpdateTimeBlg) {
		this.lastUpdateTimeBlg = lastUpdateTimeBlg;
	}

	/**
	 * @return the lastUpdateUserBlg
	 */
	public String getLastUpdateUserBlg() {
		return lastUpdateUserBlg;
	}

	/**
	 * @param lastUpdateUserBlg the lastUpdateUserBlg to set
	 */
	public void setLastUpdateUserBlg(String lastUpdateUserBlg) {
		this.lastUpdateUserBlg = lastUpdateUserBlg;
	}

	/**
     * @return Returns the airlineCode.
     */
    public String getAirlineCode() {
        return airlineCode;
    }

    /**
     * @param airlineCode
     *            The airlineCode to set.
     */
    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    /**
     * @return Returns the airlineIdr.
     */
    public int getAirlineIdr() {
        return airlineIdr;
    }

    /**
     * @param airlineIdr
     *            The airlineIdr to set.
     */
    public void setAirlineIdr(int airlineIdr) {
        this.airlineIdr = airlineIdr;
    }

    /**
     * @return Returns the airlineNumber.
     */
    public String getAirlineNumber() {
        return airlineNumber;
    }

    /**
     * @param airlineNumber
     *            The airlineNumber to set.
     */
    public void setAirlineNumber(String airlineNumber) {
        this.airlineNumber = airlineNumber;
    }

    /**
     * @return Returns the baseTotalAmt.
     */
    public Money  getBaseTotalAmt() {
        return baseTotalAmt;
    }

    /**
     * @param baseTotalAmt
     *            The baseTotalAmt to set.
     */
    public void setBaseTotalAmt(Money  baseTotalAmt) {
        this.baseTotalAmt = baseTotalAmt;
    }

    /**
     * @return Returns the billingCurrency.
     */
    public String getBillingCurrency() {
        return billingCurrency;
    }

    /**
     * @param billingCurrency
     *            The billingCurrency to set.
     */
    public void setBillingCurrency(String billingCurrency) {
        this.billingCurrency = billingCurrency;
    }

    /**
     * @return Returns the billingTotalAmt.
     */
    public Money  getBillingTotalAmt() {
        return billingTotalAmt;
    }

    /**
     * @param billingTotalAmt
     *            The billingTotalAmt to set.
     */
    public void setBillingTotalAmt(Money  billingTotalAmt) {
        this.billingTotalAmt = billingTotalAmt;
    }

    /**
     * @return Returns the classType.
     */
    public String getClassType() {
        return classType;
    }

    /**
     * @param classType
     *            The classType to set.
     */
    public void setClassType(String classType) {
        this.classType = classType;
    }

    /**
     * @return Returns the clearancePeriod.
     */
    public String getClearancePeriod() {
        return clearancePeriod;
    }

    /**
     * @param clearancePeriod
     *            The clearancePeriod to set.
     */
    public void setClearancePeriod(String clearancePeriod) {
        this.clearancePeriod = clearancePeriod;
    }

    /**
     * @return Returns the companyCode.
     */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
     * @param companyCode
     *            The companyCode to set.
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    /**
     * @return Returns the exchangeRateBillingCurrency.
     */
    public double  getExchangeRateBillingCurrency() {
        return exchangeRateBillingCurrency;
    }

    /**
     * @param exchangeRateBillingCurrency
     *            The exchangeRateBillingCurrency to set.
     */
    public void setExchangeRateBillingCurrency(double  exchangeRateBillingCurrency) {
        this.exchangeRateBillingCurrency = exchangeRateBillingCurrency;
    }

    /**
     * @return Returns the exchangeRateListingCurrency.
     */
    public double  getExchangeRateListingCurrency() {
        return exchangeRateListingCurrency;
    }

    /**
     * @param exchangeRateListingCurrency
     *            The exchangeRateListingCurrency to set.
     */
    public void setExchangeRateListingCurrency(double  exchangeRateListingCurrency) {
        this.exchangeRateListingCurrency = exchangeRateListingCurrency;
    }

    /**
     * @return Returns the interlineBillingType.
     */
    public String getInterlineBillingType() {
        return interlineBillingType;
    }

    /**
     * @param interlineBillingType
     *            The interlineBillingType to set.
     */
    public void setInterlineBillingType(String interlineBillingType) {
        this.interlineBillingType = interlineBillingType;
    }

   

    /**
     * @return Returns the listingTotalAmt.
     */
    public Money  getListingTotalAmt() {
        return listingTotalAmt;
    }

    /**
     * @param listingTotalAmt
     *            The listingTotalAmt to set.
     */
    public void setListingTotalAmt(Money  listingTotalAmt) {
        this.listingTotalAmt = listingTotalAmt;
    }

    /**
     * @return Returns the status.
     */
    public String getInvStatus() {
        return invStatus;
    }

    /**
     * @param status
     *            The status to set.
     */
    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    /**
     * @return Returns the listingCurrency.
     */
    public String getListingCurrency() {
        return listingCurrency;
    }

    /**
     * @param listingCurrency
     *            The listingCurrency to set.
     */
    public void setListingCurrency(String listingCurrency) {
        this.listingCurrency = listingCurrency;
    }

    /**
     * @return Returns the webIchFlag.
     */
    public boolean isWebIchFlag() {
        return webIchFlag;
    }

    /**
     * @param webIchFlag
     *            The webIchFlag to set.
     */
    public void setWebIchFlag(boolean webIchFlag) {
        this.webIchFlag = webIchFlag;
    }

    /**
     * @return Returns the operationFlag.
     */
    public String getOperationFlag() {
        return operationFlag;
    }

    /**
     * @param operationFlag
     *            The operationFlag to set.
     */
    public void setOperationFlag(String operationFlag) {
        this.operationFlag = operationFlag;
    }

    /**
     * @return Returns the airlineName.
     */
    public String getAirlineName() {
        return airlineName;
    }

    /**
     * @param airlineName
     *            The airlineName to set.
     */
    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    /**
     * @return Returns the ownAirlineCode.
     */
    public String getOwnAirlineCode() {
        return ownAirlineCode;
    }

    /**
     * @param ownAirlineCode
     *            The ownAirlineCode to set.
     */
    public void setOwnAirlineCode(String ownAirlineCode) {
        this.ownAirlineCode = ownAirlineCode;
    }

    /**
     * @return Returns the ownairlineName.
     */
    public String getOwnAirlineName() {
        return ownAirlineName;
    }

    /**
     * @param ownAirlineName
     *            The ownAirlineName to set.
     */
    public void setOwnAirlineName(String ownAirlineName) {
        this.ownAirlineName = ownAirlineName;
    }

    /**
     * @return Returns the ownAirlineNumber.
     */
    public String getOwnAirlineNumber() {
        return ownAirlineNumber;
    }

    /**
     * @param ownAirlineNumber
     *            The ownAirlineNumber to set.
     */
    public void setOwnAirlineNumber(String ownAirlineNumber) {
        this.ownAirlineNumber = ownAirlineNumber;
    }

    /**
     * @return Returns the zone.
     */
    public String getZone() {
        return zone;
    }

    /**
     * @param zone
     *            The zone to set.
     */
    public void setZone(String zone) {
        this.zone = zone;
    }

    /**
     * @return billingType
     */
    public String getBillingType() {
        return billingType;
    }

    /**
     * @param billingType
     */
    public void setBillingType(String billingType) {
        this.billingType = billingType;
    }

    /**
     * @return cargoAmount
     */
    public Money  getCargoAmount() {
        return cargoAmount;
    }

    /**
     * @param cargoAmount
     */
    public void setCargoAmount(Money  cargoAmount) {
        this.cargoAmount = cargoAmount;
    }

    /**
     * @return missAmount
     */
    public Money  getMissAmount() {
        return missAmount;
    }

    /**
     * @param missAmount
     */
    public void setMissAmount(Money  missAmount) {
        this.missAmount = missAmount;
    }

    /**
     * @return passengerAmount
     */
    public Money  getPassengerAmount() {
        return passengerAmount;
    }

    /**
     * @param passengerAmount
     */
    public void setPassengerAmount(Money  passengerAmount) {
        this.passengerAmount = passengerAmount;
    }

    /**
     * @return utpAmount
     */
    public Money  getUtpAmount() {
        return utpAmount;
    }

    /**
     * @param utpAmount
     */
    public void setUtpAmount(Money  utpAmount) {
        this.utpAmount = utpAmount;
    }
	
	//Added by Meera for Outward Form1 report CR		
	private String currencyName;

	/**
	 * @return Returns the currencyName.
	 */
	public String getCurrencyName() {
		return currencyName;
	}

	/**
	 * @param currencyName The currencyName to set.
	 */
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	/**
	 * @return Returns the lastUpdateTime.
	 */
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

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
	 * @return Returns the invoiceInFormOneVOs.
	 */
	public Collection<InvoiceInFormOneVO> getInvoiceInFormOneVOs() {
		return invoiceInFormOneVOs;
	}

	/**
	 * @param invoiceInFormOneVOs The invoiceInFormOneVOs to set.
	 */
	public void setInvoiceInFormOneVOs(
			Collection<InvoiceInFormOneVO> invoiceInFormOneVOs) {
		this.invoiceInFormOneVOs = invoiceInFormOneVOs;
	}

	/**
	 * @return Returns the formOneStatus.
	 */
	public String getFormOneStatus() {
		return formOneStatus;
	}

	/**
	 * @param formOneStatus The formOneStatus to set.
	 */
	public void setFormOneStatus(String formOneStatus) {
		this.formOneStatus = formOneStatus;
	}

}
