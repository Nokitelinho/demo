/*
 * CN51DetailsVO.java Created on Jan 8, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling.vo;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1556
 * @author a-2270   added fields for display purpose in ListCN51CN66 Screen
 *
 */

public class CN51DetailsVO extends AbstractVO {

    private String companyCode;
    private String invoiceNumber;
    private String gpaCode;
    private int sequenceNumber;
    private String origin;
    private String destination;
    private String mailCategoryCode;
    private String mailSubclass;
    private double totalWeight;
    private int totalPieces;
    private double applicableRate;
    private Money totalAmount;
    private double totAmt;//added by a-7871 for ICRD-214766
    private String fileName;
    private String gpaType;
    
	/**
	 * @return the totAmt
	 */
	public double getTotAmt() {
		return totAmt;
	}
	/**
	 * @param totAmt the totAmt to set
	 */
	public void setTotAmt(double totAmt) {
		this.totAmt = totAmt;
	}
    private String billingCurrencyCode;
    private String billingPeriod;
    private String invoiceStatus;
    private String mailCharge;
    private String surCharge;
    private String operationFlag;
    //Added by prem
    private Money totalBilledAmount;
    
    private Money vatAmount;
    private double vatAmt;//added by a-7871 for ICRD-214766

    // added by Sandeep for display purpose in ListCN51CN66 Screen starts

    /**
	 * @return the vatAmt
	 */
	public double getVatAmt() {
		return vatAmt;
	}
	/**
	 * @param vatAmt the vatAmt to set
	 */
	public void setVatAmt(double vatAmt) {
		this.vatAmt = vatAmt;
	}
    private double totalWeightLC;

    private double totalWeightCP;

    private double applicableRateLC;

    private double applicableRateCP;

    private double totalAmountLC;

    private double totalAmountCP;
    //Added by A-4809 for Empty bags and EMS Starts
    private double totalWeightSV;
    private double totalWeightEMS;
    private double applicableRateSV;
    private double applicableRateEMS;
    private double totalAmountSV;
    private double totalAmountEMS;
    //Added by A-4809 for Empty bags and EMS Ends
    
    //Added by A-7929 for ICRD-260958 starts
    private double mailChgSV;
    private double surChgSV;
    private double mailChgEMS;
    private double surChgEMS;
  //Added by A-7929 for ICRD-260958 starts    
    private String checkFlag;
    
    private double rate;       // added by A-9002 for report structure change 
    private double amount;
    private double mailChrg;
    private double surChrg;
    
    public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getMailChrg() {
		return mailChrg;
	}
	public void setMailChrg(double mailChrg) {
		this.mailChrg = mailChrg;
	}
	public double getSurChrg() {
		return surChrg;
	}
	public void setSurChrg(double surChrg) {
		this.surChrg = surChrg;
	}	
    /**
     * @return mailChgSV
     */
    public double getMailChgSV() {
		return mailChgSV;
	}
    /**
     * @param mailChgSV
     */
	public void setMailChgSV(double mailChgSV) {
		this.mailChgSV = mailChgSV;
	}
	/**
	 * @return surChgSV
	 */
	public double getSurChgSV() {
		return surChgSV;
	}
	/**
	 * @param surChgSV
	 */
	public void setSurChgSV(double surChgSV) {
		this.surChgSV = surChgSV;
	}
	/**
	 * @return mailChgEMS
	 */
	public double getMailChgEMS() {
		return mailChgEMS;
	}
	/**
	 * @param mailChgEMS
	 */
	public void setMailChgEMS(double mailChgEMS) {
		this.mailChgEMS = mailChgEMS;
	}
	/**
	 * @return surChgEMS
	 */
	public double getSurChgEMS() {
		return surChgEMS;
	}
	/**
	 * @param surChgEMS
	 */
	public void setSurChgEMS(double surChgEMS) {
		this.surChgEMS = surChgEMS;
	}
	
    private String sector;
    private double valCharges;
    private double totalNetAmount;	//Added for ICRD-107524
    
    // Added for 76551
    private double mailChgLC;
    private double surChgLC;
    private double mailChgCP;
    private double surChgCP;
    private String overrideRounding;
    private double scalarTotalBilledAmount;
    //Added by A-6991 for ICRD-211662
    private int invSerialNumber;
           
    /**
	 * @return the mailChgLC
	 */
	public double getMailChgLC() {
		return mailChgLC;
	}
	/**
	 * @param mailChgLC the mailChgLC to set
	 */
	public void setMailChgLC(double mailChgLC) {
		this.mailChgLC = mailChgLC;
	}
	/**
	 * @return the surChgLC
	 */
	public double getSurChgLC() {
		return surChgLC;
	}
	/**
	 * @param surChgLC the surChgLC to set
	 */
	public void setSurChgLC(double surChgLC) {
		this.surChgLC = surChgLC;
	}
	/**
	 * @return the mailChgCP
	 */
	public double getMailChgCP() {
		return mailChgCP;
	}
	/**
	 * @param mailChgCP the mailChgCP to set
	 */
	public void setMailChgCP(double mailChgCP) {
		this.mailChgCP = mailChgCP;
	}
	/**
	 * @return the surChgCP
	 */
	public double getSurChgCP() {
		return surChgCP;
	}
	/**
	 * @param surChgCP the surChgCP to set
	 */
	public void setSurChgCP(double surChgCP) {
		this.surChgCP = surChgCP;
	}
	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}
	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	/**
	 * @return the recordSize
	 */
	public int getRecordSize() {
		return recordSize;
	}
	/**
	 * @param recordSize the recordSize to set
	 */
	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}
	/**
	 * @return the totalRecordCount
	 */
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	/**
	 * @param totalRecordCount the totalRecordCount to set
	 */
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	private int pageNumber;
	private int recordSize;
	private int totalRecordCount;

    // added by Sandeep Ends
    private String gpaName;
    /**
     * @author a-3447 for Bug 27970
     */
    private String mailCategoryCodedisp;
    //Added for report
    private String monthFlag;
    private String actualSubCls;
    
    private double weightLC;
    private double weightCP;
    
    private Money totalAmtinLC;
    private Money totalAmtinCP;
  //added for cr ICRD-7370
	private double serviceTax;
	private double tds;
	private double netAmount;
	private double grossAmount;
	private LocalDate flightDate;
	private String distance;
	private String poaAddress;
	//field identifies whether consolidated rate and srv tax configured in pa master
	private String conRatTax;
	private double weight;    
	private String unitCode;
    private String city;
    private String pinCode;
    private String airlineCode;
    
    private String displayWgtUnit;      
    private String displayWgtUnitLC;    
    private String displayWgtUnitCP;   
    private String displayWgtUnitSV;   
    private String displayWgtUnitEMS;   
    
    public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
    
    public String getDisplayWgtUnit() {
		return displayWgtUnit;
	}
	public void setDisplayWgtUnit(String displayWgtUnit) {
		this.displayWgtUnit = displayWgtUnit;
	}
	public String getDisplayWgtUnitLC() {
		return displayWgtUnitLC;
	}
	public void setDisplayWgtUnitLC(String displayWgtUnitLC) {
		this.displayWgtUnitLC = displayWgtUnitLC;
	}
	public String getDisplayWgtUnitCP() {
		return displayWgtUnitCP;
	}
	public void setDisplayWgtUnitCP(String displayWgtUnitCP) {
		this.displayWgtUnitCP = displayWgtUnitCP;
	}
	public String getDisplayWgtUnitSV() {
		return displayWgtUnitSV;
	}
	public void setDisplayWgtUnitSV(String displayWgtUnitSV) {
		this.displayWgtUnitSV = displayWgtUnitSV;
	}
	public String getDisplayWgtUnitEMS() {
		return displayWgtUnitEMS;
	}
	public void setDisplayWgtUnitEMS(String displayWgtUnitEMS) {
		this.displayWgtUnitEMS = displayWgtUnitEMS;
	}
	    
    /**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the pinCode
	 */
	public String getPinCode() {
		return pinCode;
	}
	/**
	 * @param pinCode the pinCode to set
	 */
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	/**
	 * @return the unitCode
	 */
	public String getUnitCode() {
		return unitCode;
	}
	/**
	 * @param unitCode the unitCode to set
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
    /**
	 * @return the conRatTax
	 */
	public String getConRatTax() {
		return conRatTax;
	}
	/**
	 * @param conRatTax the conRatTax to set
	 */
	public void setConRatTax(String conRatTax) {
		this.conRatTax = conRatTax;
	}
	public String getPoaAddress() {
		return poaAddress;
	}
	public void setPoaAddress(String poaAddress) {
		this.poaAddress = poaAddress;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public LocalDate getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}
    public static final String MAILSUBCLASS_LC = "LC";

	public static final String MAILSUBCLASS_CP = "CP";
	public static final String MAILSUBCLASS_SV = "SV";
	public static final String MAILSUBCLASS_EMS = "EMS";
	/**
	 * @return Returns the actualSubCls.
	 */
	public String getActualSubCls() {
		return actualSubCls;
	}
	/**
	 * @param actualSubCls The actualSubCls to set.
	 */
	public void setActualSubCls(String actualSubCls) {
		this.actualSubCls = actualSubCls;
	}
	/**
	 * @return Returns the monthFlag.
	 */
	public String getMonthFlag() {
		return monthFlag;
	}
	/**
	 * @param monthFlag The monthFlag to set.
	 */
	public void setMonthFlag(String monthFlag) {
		this.monthFlag = monthFlag;
	}
	/**
	 * @return the mailCategoryCodedisp
	 */
	public String getMailCategoryCodedisp() {
		return mailCategoryCodedisp;
	}
	/**
	 * @param mailCategoryCodedisp the mailCategoryCodedisp to set
	 */
	public void setMailCategoryCodedisp(String mailCategoryCodedisp) {
		this.mailCategoryCodedisp = mailCategoryCodedisp;
	}
	/**
	 * @return Returns the sector.
	 */
	public String getSector() {
		return sector;
	}
	/**
	 * @param sector The sector to set.
	 */
	public void setSector(String sector) {
		this.sector = sector;
	}
	/**
	 * @return Returns the checkFlag.
	 */
	public String getCheckFlag() {
		return checkFlag;
	}
	/**
	 * @param checkFlag The checkFlag to set.
	 */
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
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
     * @return Returns the invoiceNumber.
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }
    /**
     * @param invoiceNumber The invoiceNumber to set.
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
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
     * @return Returns the gpaCode.
     */
    public String getGpaCode() {
        return gpaCode;
    }
    /**
     * @param gpaCode The gpaCode to set.
     */
    public void setGpaCode(String gpaCode) {
        this.gpaCode = gpaCode;
    }

    /**
     * @return Returns the billingCurrencyCode.
     */
    public String getBillingCurrencyCode() {
        return billingCurrencyCode;
    }
    /**
     * @param billingCurrencyCode The billingCurrencyCode to set.
     */
    public void setBillingCurrencyCode(String billingCurrencyCode) {
        this.billingCurrencyCode = billingCurrencyCode;
    }
    /**
     * @return Returns the destination.
     */
    public String getDestination() {
        return destination;
    }
    /**
     * @param destination The destination to set.
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }
    /**
     * @return Returns the mailCategoryCode.
     */
    public String getMailCategoryCode() {
        return mailCategoryCode;
    }
    /**
     * @param mailCategoryCode The mailCategoryCode to set.
     */
    public void setMailCategoryCode(String mailCategoryCode) {
        this.mailCategoryCode = mailCategoryCode;
    }
    /**
     * @return Returns the mailSubclass.
     */
    public String getMailSubclass() {
        return mailSubclass;
    }
    /**
     * @param mailSubclass The mailSubclass to set.
     */
    public void setMailSubclass(String mailSubclass) {
        this.mailSubclass = mailSubclass;
    }
    /**
     * @return Returns the origin.
     */
    public String getOrigin() {
        return origin;
    }
    /**
     * @param origin The origin to set.
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    /**
     * @return Returns the sequenceNumber.
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }
    /**
     * @param sequenceNumber The sequenceNumber to set.
     */
    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }



    /**
	 * @return Returns the applicableRate.
	 */
	public double getApplicableRate() {
		return applicableRate;
	}
	/**
	 * @param applicableRate The applicableRate to set.
	 */
	public void setApplicableRate(double applicableRate) {
		this.applicableRate = applicableRate;
	}
	
	/**
	 * @return Returns the totalPieces.
	 */
	public int getTotalPieces() {
		return totalPieces;
	}
	/**
	 * @param totalPieces The totalPieces to set.
	 */
	public void setTotalPieces(int totalPieces) {
		this.totalPieces = totalPieces;
	}
	/**
	 * @return Returns the totalWeight.
	 */
	public double getTotalWeight() {
		return totalWeight;
	}
	/**
	 * @param totalWeight The totalWeight to set.
	 */
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}
	/**
     * @return Returns the billingPeriod.
     */
    public String getBillingPeriod() {
        return billingPeriod;
    }
    /**
     * @param billingPeriod The billingPeriod to set.
     */
    public void setBillingPeriod(String billingPeriod) {
        this.billingPeriod = billingPeriod;
    }
	/**
	 * @return Returns the applicableRateCP.
	 */
	public double getApplicableRateCP() {
		return applicableRateCP;
	}
	/**
	 * @param applicableRateCP The applicableRateCP to set.
	 */
	public void setApplicableRateCP(double applicableRateCP) {
		this.applicableRateCP = applicableRateCP;
	}
	/**
	 * @return Returns the applicableRateLC.
	 */
	public double getApplicableRateLC() {
		return applicableRateLC;
	}
	/**
	 * @param applicableRateLC The applicableRateLC to set.
	 */
	public void setApplicableRateLC(double applicableRateLC) {
		this.applicableRateLC = applicableRateLC;
	}
	/**
	 * @return Returns the totalAmountCP.
	 */
	public double getTotalAmountCP() {
		return totalAmountCP;
	}
	/**
	 * @param totalAmountCP The totalAmountCP to set.
	 */
	public void setTotalAmountCP(double totalAmountCP) {
		this.totalAmountCP = totalAmountCP;
	}
	/**
	 * @return Returns the totalAmountLC.
	 */
	public double getTotalAmountLC() {
		return totalAmountLC;
	}
	/**
	 * @param totalAmountLC The totalAmountLC to set.
	 */
	public void setTotalAmountLC(double totalAmountLC) {
		this.totalAmountLC = totalAmountLC;
	}
	/**
	 * @return Returns the totalWeightCP.
	 */
	public double getTotalWeightCP() {
		return totalWeightCP;
	}
	/**
	 * @param totalWeightCP The totalWeightCP to set.
	 */
	public void setTotalWeightCP(double totalWeightCP) {
		this.totalWeightCP = totalWeightCP;
	}
	/**
	 * @return Returns the totalWeightLC.
	 */
	public double getTotalWeightLC() {
		return totalWeightLC;
	}
	/**
	 * @param totalWeightLC The totalWeightLC to set.
	 */
	public void setTotalWeightLC(double totalWeightLC) {
		this.totalWeightLC = totalWeightLC;
	}
	
	/**
	 * @return Returns the gpaName.
	 */
	public String getGpaName() {
		return gpaName;
	}
	/**
	 * @param gpaName The gpaName to set.
	 */
	public void setGpaName(String gpaName) {
		this.gpaName = gpaName;
	}
	/**
	 * @return Returns the totalAmount.
	 */
	public Money getTotalAmount() {
		return totalAmount;
	}
	/**
	 * @param totalAmount The totalAmount to set.
	 */
	public void setTotalAmount(Money totalAmount) {
		this.totalAmount = totalAmount;
	}
	/**
	 * @return Returns the totalBilledAmount.
	 */
	public Money getTotalBilledAmount() {
		return totalBilledAmount;
	}
	/**
	 * @param totalBilledAmount The totalBilledAmount to set.
	 */
	public void setTotalBilledAmount(Money totalBilledAmount) {
		this.totalBilledAmount = totalBilledAmount;
	}
	/**
	 * @return the vatAmount
	 */
	public Money getVatAmount() {
		return vatAmount;
	}
	/**
	 * @param vatAmount the vatAmount to set
	 */
	public void setVatAmount(Money vatAmount) {
		this.vatAmount = vatAmount;
	}
	/**
	 * @return the weightCP
	 */
	public double getWeightCP() {
		return weightCP;
	}
	/**
	 * @param weightCP the weightCP to set
	 */
	public void setWeightCP(double weightCP) {
		this.weightCP = weightCP;
	}
	/**
	 * @return the weightLC
	 */
	public double getWeightLC() {
		return weightLC;
	}
	/**
	 * @param weightLC the weightLC to set
	 */
	public void setWeightLC(double weightLC) {
		this.weightLC = weightLC;
	}
	/**
	 * @return the totalAmtinCP
	 */
	public Money getTotalAmtinCP() {
		return totalAmtinCP;
	}
	/**
	 * @param totalAmtinCP the totalAmtinCP to set
	 */
	public void setTotalAmtinCP(Money totalAmtinCP) {
		this.totalAmtinCP = totalAmtinCP;
	}
	/**
	 * @return the totalAmtinLC
	 */
	public Money getTotalAmtinLC() {
		return totalAmtinLC;
	}
	/**
	 * @param totalAmtinLC the totalAmtinLC to set
	 */
	public void setTotalAmtinLC(Money totalAmtinLC) {
		this.totalAmtinLC = totalAmtinLC;
	}
	/**
	 * @return the serviceTax
	 */
	public double getServiceTax() {
		return serviceTax;
	}
	/**
	 * @param serviceTax the serviceTax to set
	 */
	public void setServiceTax(double serviceTax) {
		this.serviceTax = serviceTax;
	}
	/**
	 * @return the tds
	 */
	public double getTds() {
		return tds;
	}
	/**
	 * @param tds the tds to set
	 */
	public void setTds(double tds) {
		this.tds = tds;
	}
	/**
	 * @return the netAmount
	 */
	public double getNetAmount() {
		return netAmount;
	}
	/**
	 * @param netAmount the netAmount to set
	 */
	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}
	/**
	 * @return the grossAmount
	 */
	public double getGrossAmount() {
		return grossAmount;
	}
	/**
	 * @param grossAmount the grossAmount to set
	 */
	public void setGrossAmount(double grossAmount) {
		this.grossAmount = grossAmount;
	}
	/**
	 * @return the totalWeightSV
	 */
	public double getTotalWeightSV() {
		return totalWeightSV;
	}
	/**
	 * @return the totalWeightEMS
	 */
	public double getTotalWeightEMS() {
		return totalWeightEMS;
	}
	/**
	 * @return the applicableRateSV
	 */
	public double getApplicableRateSV() {
		return applicableRateSV;
	}
	/**
	 * @return the applicableRateEMS
	 */
	public double getApplicableRateEMS() {
		return applicableRateEMS;
	}
	/**
	 * @return the totalAmountSV
	 */
	public double getTotalAmountSV() {
		return totalAmountSV;
	}
	/**
	 * @return the totalAmountEMS
	 */
	public double getTotalAmountEMS() {
		return totalAmountEMS;
	}
	/**
	 * @param totalWeightSV the totalWeightSV to set
	 */
	public void setTotalWeightSV(double totalWeightSV) {
		this.totalWeightSV = totalWeightSV;
	}
	/**
	 * @param totalWeightEMS the totalWeightEMS to set
	 */
	public void setTotalWeightEMS(double totalWeightEMS) {
		this.totalWeightEMS = totalWeightEMS;
	}
	/**
	 * @param applicableRateSV the applicableRateSV to set
	 */
	public void setApplicableRateSV(double applicableRateSV) {
		this.applicableRateSV = applicableRateSV;
	}
	/**
	 * @param applicableRateEMS the applicableRateEMS to set
	 */
	public void setApplicableRateEMS(double applicableRateEMS) {
		this.applicableRateEMS = applicableRateEMS;
	}
	/**
	 * @param totalAmountSV the totalAmountSV to set
	 */
	public void setTotalAmountSV(double totalAmountSV) {
		this.totalAmountSV = totalAmountSV;
	}
	/**
	 * @param totalAmountEMS the totalAmountEMS to set
	 */
	public void setTotalAmountEMS(double totalAmountEMS) {
		this.totalAmountEMS = totalAmountEMS;
	}
	/**
	 * @param airlineCode the airlineCode to set
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	/**
	 * @return the airlineCode
	 */
	public String getAirlineCode() {
		return airlineCode;
	}
	/**
	 * @param valCharges the valCharges to set
	 */
	public void setValCharges(double valCharges) {
		this.valCharges = valCharges;
	}
	/**
	 * @return the valCharges
	 */
	public double getValCharges() {
		return valCharges;
	}
	/**
	 * @return the totalNetAmount
	 */
	public double getTotalNetAmount() {
		return totalNetAmount;
	}
	/**
	 * @param totalNetAmount the totalNetAmount to set
	 */
	public void setTotalNetAmount(double totalNetAmount) {
		this.totalNetAmount = totalNetAmount;
	}
	/**
	 * @return the mailCharge
	 */
	public String getMailCharge() {
		return mailCharge;
	}
	/**
	 * @param mailCharge the mailCharge to set
	 */
	public void setMailCharge(String mailCharge) {
		this.mailCharge = mailCharge;
	}
	/**
	 * @return the surCharge
	 */
	public String getSurCharge() {
		return surCharge;
	}
	/**
	 * @param surCharge the surCharge to set
	 */
	public void setSurCharge(String surCharge) {
		this.surCharge = surCharge;
	}
	/**
	 * @return the overrideRounding
	 */
	public String getOverrideRounding() {
		return overrideRounding;
	}
	/**
	 * @param overrideRounding the overrideRounding to set
	 */
	public void setOverrideRounding(String overrideRounding) {
		this.overrideRounding = overrideRounding;
	}
	/**
	 * @return the scalarTotalBilledAmount
	 */
	public double getScalarTotalBilledAmount() {
		return scalarTotalBilledAmount;
	}
	/**
	 * @param scalarTotalBilledAmount the scalarTotalBilledAmount to set
	 */
	public void setScalarTotalBilledAmount(double scalarTotalBilledAmount) {
		this.scalarTotalBilledAmount = scalarTotalBilledAmount;
	}
	/**
	 * 	Getter for invoiceStatus 
	 *	Added by : A-6991 on 08-Sep-2017
	 * 	Used for : ICRD-211662
	 */
	public String getInvoiceStatus() {
		return invoiceStatus;
	}
	/**
	 *  @param invoiceStatus the invoiceStatus to set
	 * 	Setter for invoiceStatus 
	 *	Added by : A-6991 on 08-Sep-2017
	 * 	Used for : ICRD-211662
	 */
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	/**
	 * 	Getter for invSerialNumber 
	 *	Added by : A-6991 on 22-Sep-2017
	 * 	Used for :
	 */
	public int getInvSerialNumber() {
		return invSerialNumber;
	}
	/**
	 *  @param invSerialNumber the invSerialNumber to set
	 * 	Setter for invSerialNumber 
	 *	Added by : A-6991 on 22-Sep-2017
	 * 	Used for :
	 */
	public void setInvSerialNumber(int invSerialNumber) {
		this.invSerialNumber = invSerialNumber;
	}
	/**
	 * 	Getter for fileName
	 *	Added by : A-5219 on 25-May-2021
	 * 	Used for :
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 *  @param fileName the fileName to set
	 * 	Setter for fileName
	 *	Added by : A-5219 on 25-May-2021
	 * 	Used for :
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 	Getter for gpaType
	 *	Added by : A-5219 on 25-May-2021
	 * 	Used for :
	 */
	public String getGpaType() {
		return gpaType;
	}
	/**
	 *  @param gpaType the gpaType to set
	 * 	Setter for gpaType
	 *	Added by : A-5219 on 25-May-2021
	 * 	Used for :
	 */
	public void setGpaType(String gpaType) {
		this.gpaType = gpaType;
	}
	  private String poanam;
	  private String c51dtlinvdat;
	  private String aplrat;
	  private String c51smycrtcurcod;
	  private String c51smytotbldamt;

	public String getPoanam() {
		return poanam;
	}
	public void setPoanam(String poanam) {
		this.poanam = poanam;
	}
	public String getC51dtlinvdat() {
		return c51dtlinvdat;
	}
	public void setC51dtlinvdat(String c51dtlinvdat) {
		this.c51dtlinvdat = c51dtlinvdat;
	}
	
	public String getAplrat() {
		return aplrat;
	}
	public void setAplrat(String aplrat) {
		this.aplrat = aplrat;
	}
	public String getC51smycrtcurcod() {
		return c51smycrtcurcod;
	}
	public void setC51smycrtcurcod(String c51smycrtcurcod) {
		this.c51smycrtcurcod = c51smycrtcurcod;
	}
	public String getC51smytotbldamt() {
		return c51smytotbldamt;
	}
	public void setC51smytotbldamt(String c51smytotbldamt) {
		this.c51smytotbldamt = c51smytotbldamt;
	}

	private String vatNumber;
	private String invDateMMMformat;
	private String invNumberFinancial;
	public String getInvNumberFinancial() {
		return invNumberFinancial;
	}
	public void setInvNumberFinancial(String invNumberFinancial) {
		this.invNumberFinancial = invNumberFinancial;
	}
	public String getInvDateMMMformat() {
		return invDateMMMformat;
	}
	public void setInvDateMMMformat(String invDateMMMformat) {
		this.invDateMMMformat = invDateMMMformat;
	}
	public String getVatNumber() {
		return vatNumber;
	}
	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

}
