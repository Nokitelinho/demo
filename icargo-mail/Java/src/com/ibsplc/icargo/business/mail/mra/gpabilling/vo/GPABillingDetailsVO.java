/*
 * GPABillingDetailsVO.java Created on Jan 8, 2007
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
 *
 */
public class GPABillingDetailsVO extends AbstractVO {

    private int sequenceNumber;
    private String dsn;
    private long mailSequenceNumber;
    private String billingBase;  //Updated by A-5931
    private String despatchId;
    private String companyCode;
    private String originOfficeOfExchange;
    private String destinationOfficeOfExchange;
    private String mailCategoryCode;
    private String mailSubclass;
    private int year;
    private String receptacleSerialNumber;
    private String registeredOrInsuredIndicator;
    private String highestNumberedReceptacle;
    private LocalDate receivedDate;
    private int piecesReceived;
    private double weightReceived;
    private double applicableRate;
    private Money amountBillable;
    private String currencyCode;
    private String billingStatus;
    private String invoiceNumber;
    private String countryCode;
    private String gpaCode;
    private String consignmentNumber;
    private String ownerGPACode;
    private String remarks;
    private String billingBasis;
    private int consignmentSequenceNumber;
    private int flightCarrierIdentifier; 
    private String flightNumber;
    private long flightSequenceNumber;
    private int segmentSerialNumber;
    private String uldNumber;
    private String containerNumber;
    /**
     * Added as part of ICRD-111958
     */
	private double netAmount;
	private double grossAmount;
	private double vatAmount;
	private double valCharges;
	private double netAmtBillingCurrency;
	private String billingCurrency;
	private double extraWeight;
	private double totalValCharges;
	private double totalVatAmount;
	private double totalGrsAmount;
	private double odtotalValCharges;
	private double odtotalVatAmount;
	private double odtotalGrsAmount;
	private double odnetAmtBillingCurrency;
	private String odString;
	private double odWeight;
	//Added by A-6991 for ICRD-213474
  	private double declaredValue;
  	private String isUSPSPerformed; //Added by A-7871 for ICRD-232381
  	private String displayWgtUnit; // Added by A-9002 for IASCB-22946
	public String getDisplayWgtUnit() {
		return displayWgtUnit;
	}
	public void setDisplayWgtUnit(String displayWgtUnit) {
		this.displayWgtUnit = displayWgtUnit;
	}
	  	  	  
    /**
	 * @return the isUSPSPerformed
	 */
	public String getIsUSPSPerformed() {
		return isUSPSPerformed;
	}
	/**
	 * @param isUSPSPerformed the isUSPSPerformed to set
	 */
	public void setIsUSPSPerformed(String isUSPSPerformed) {
		this.isUSPSPerformed = isUSPSPerformed;
	}
	/**
	 * @return the odtotalValCharges
	 */
	public double getOdtotalValCharges() {
		return odtotalValCharges;
	}
	/**
	 * @param odtotalValCharges the odtotalValCharges to set
	 */
	public void setOdtotalValCharges(double odtotalValCharges) {
		this.odtotalValCharges = odtotalValCharges;
	}
	/**
	 * @return the odtotalVatAmount
	 */
	public double getOdtotalVatAmount() {
		return odtotalVatAmount;
	}
	/**
	 * @param odtotalVatAmount the odtotalVatAmount to set
	 */
	public void setOdtotalVatAmount(double odtotalVatAmount) {
		this.odtotalVatAmount = odtotalVatAmount;
	}
	/**
	 * @return the odtotalGrsAmount
	 */
	public double getOdtotalGrsAmount() {
		return odtotalGrsAmount;
	}
	/**
	 * @param odtotalGrsAmount the odtotalGrsAmount to set
	 */
	public void setOdtotalGrsAmount(double odtotalGrsAmount) {
		this.odtotalGrsAmount = odtotalGrsAmount;
	}
	/**
	 * @return the odnetAmtBillingCurrency
	 */
	public double getOdnetAmtBillingCurrency() {
		return odnetAmtBillingCurrency;
	}
	/**
	 * @param odnetAmtBillingCurrency the odnetAmtBillingCurrency to set
	 */
	public void setOdnetAmtBillingCurrency(double odnetAmtBillingCurrency) {
		this.odnetAmtBillingCurrency = odnetAmtBillingCurrency;
	}
	/**
	 * @return the odString
	 */
	public String getOdString() {
		return odString;
	}
	/**
	 * @param odString the odString to set
	 */
	public void setOdString(String odString) {
		this.odString = odString;
	}
    /**
     * change done by indu for despatchEnquiry
     */
    private String ccaNo;
    private String blgPrdFrm;
    private String blgPrdTo;
    private String invDate;
    private String invStatus;
    private String gpaName;
    
    /**
     * Added by A-3434 for ccadetails
     */
    //private String ccaStatus;
    private String ccaType;
    private String ccaReferenceNo;
    private String poaCode;
    private LocalDate lastUpdatedtime;
    private String LastUpdateduser;
    
	private Money surchargeAmtBillable;
	//private Money grossAmount;
	private Money taxAmount;
	//private Money netAmount;
	/**
	 * @return Returns the ccaReferenceNo.
	 */
	public String getCcaReferenceNo() {
		return ccaReferenceNo;
	}
	/**
	 * @param ccaReferenceNo The ccaReferenceNo to set.
	 */
	public void setCcaReferenceNo(String ccaReferenceNo) {
		this.ccaReferenceNo = ccaReferenceNo;
	}
	/**
	 * @return Returns the lastUpdateduser.
	 */
	public String getLastUpdateduser() {
		return LastUpdateduser;
	}
	/**
	 * @param lastUpdateduser The lastUpdateduser to set.
	 */
	public void setLastUpdateduser(String lastUpdateduser) {
		LastUpdateduser = lastUpdateduser;
	}
	/**
	 * @return Returns the lastUpdatedtime.
	 */
	public LocalDate getLastUpdatedtime() {
		return lastUpdatedtime;
	}
	/**
	 * @param lastUpdatedtime The lastUpdatedtime to set.
	 */
	public void setLastUpdatedtime(LocalDate lastUpdatedtime) {
		this.lastUpdatedtime = lastUpdatedtime;
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
	 * @return Returns the ccaType.
	 */
	public String getCcaType() {
		return ccaType;
	}
	/**
	 * @param ccaType The ccaType to set.
	 */
	public void setCcaType(String ccaType) {
		this.ccaType = ccaType;
	}
	/**
	 * @return Returns the blgPrdFrm.
	 */
	public String getBlgPrdFrm() {
		return blgPrdFrm;
	}
	/**
	 * @param blgPrdFrm The blgPrdFrm to set.
	 */
	public void setBlgPrdFrm(String blgPrdFrm) {
		this.blgPrdFrm = blgPrdFrm;
	}
	/**
	 * @return Returns the blgPrdTo.
	 */
	public String getBlgPrdTo() {
		return blgPrdTo;
	}
	/**
	 * @param blgPrdTo The blgPrdTo to set.
	 */
	public void setBlgPrdTo(String blgPrdTo) {
		this.blgPrdTo = blgPrdTo;
	}
	/**
	 * @return Returns the ccaNo.
	 */
	public String getCcaNo() {
		return ccaNo;
	}
	/**
	 * @param ccaNo The ccaNo to set.
	 */
	public void setCcaNo(String ccaNo) {
		this.ccaNo = ccaNo;
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
	 * @return Returns the invDate.
	 */
	public String getInvDate() {
		return invDate;
	}
	/**
	 * @param invDate The invDate to set.
	 */
	public void setInvDate(String invDate) {
		this.invDate = invDate;
	}
	/**
	 * @return Returns the invStatus.
	 */
	public String getInvStatus() {
		return invStatus;
	}
	/**
	 * @param invStatus The invStatus to set.
	 */
	public void setInvStatus(String invStatus) {
		this.invStatus = invStatus;
	}
	
    /**
	 * @return the amountBillable
	 */
	public Money getAmountBillable() {
		return amountBillable;
	}
	/**
	 * @param amountBillable the amountBillable to set
	 */
	public void setAmountBillable(Money amountBillable) {
		this.amountBillable = amountBillable;
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
     * @return Returns the billingCurrencyCode.
     */
    public String getCurrencyCode() {
        return currencyCode;
    }
    /**
     * @param billingCurrencyCode The billingCurrencyCode to set.
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    /**
     * @return Returns the billingStatus.
     */
    public String getBillingStatus() {
        return billingStatus;
    }
    /**
     * @param billingStatus The billingStatus to set.
     */
    public void setBillingStatus(String billingStatus) {
        this.billingStatus = billingStatus;
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
     * @return Returns the destinationOfficeOfExchange.
     */
    public String getDestinationOfficeOfExchange() {
        return destinationOfficeOfExchange;
    }
    /**
     * @param destinationOfficeOfExchange The destinationOfficeOfExchange to set.
     */
    public void setDestinationOfficeOfExchange(
            String destinationOfficeOfExchange) {
        this.destinationOfficeOfExchange = destinationOfficeOfExchange;
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
     * @return Returns the highestNumberedReceptacle.
     */
    public String getHighestNumberedReceptacle() {
        return highestNumberedReceptacle;
    }
    /**
     * @param highestNumberedReceptacle The highestNumberedReceptacle to set.
     */
    public void setHighestNumberedReceptacle(String highestNumberedReceptacle) {
        this.highestNumberedReceptacle = highestNumberedReceptacle;
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
     * @return Returns the originOfficeOfExchange.
     */
    public String getOriginOfficeOfExchange() {
        return originOfficeOfExchange;
    }
    /**
     * @param originOfficeOfExchange The originOfficeOfExchange to set.
     */
    public void setOriginOfficeOfExchange(String originOfficeOfExchange) {
        this.originOfficeOfExchange = originOfficeOfExchange;
    }
    /**
     * @return Returns the piecesReceived.
     */
    public int getPiecesReceived() {
        return piecesReceived;
    }
    /**
     * @param piecesReceived The piecesReceived to set.
     */
    public void setPiecesReceived(int piecesReceived) {
        this.piecesReceived = piecesReceived;
    }
    /**
     * @return Returns the receivedDate.
     */
    public LocalDate getReceivedDate() {
        return receivedDate;
    }
    /**
     * @param receivedDate The receivedDate to set.
     */
    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }
    /**
     * @return Returns the receptacleSerialNumber.
     */
    public String getReceptacleSerialNumber() {
        return receptacleSerialNumber;
    }
    /**
     * @param receptacleSerialNumber The receptacleSerialNumber to set.
     */
    public void setReceptacleSerialNumber(String receptacleSerialNumber) {
        this.receptacleSerialNumber = receptacleSerialNumber;
    }
    /**
     * @return Returns the registeredOrInsuredIndicator.
     */
    public String getRegisteredOrInsuredIndicator() {
        return registeredOrInsuredIndicator;
    }
    /**
     * @param registeredOrInsuredIndicator The registeredOrInsuredIndicator to set.
     */
    public void setRegisteredOrInsuredIndicator(
            String registeredOrInsuredIndicator) {
        this.registeredOrInsuredIndicator = registeredOrInsuredIndicator;
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
     * @return Returns the weightReceived.
     */
    public double getWeightReceived() {
        return weightReceived;
    }
    /**
     * @param weightReceived The weightReceived to set.
     */
    public void setWeightReceived(double weightReceived) {
        this.weightReceived = weightReceived;
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
     * @return Returns the consignmentNumber.
     */
    public String getConsignmentNumber() {
        return consignmentNumber;
    }
    /**
     * @param consignmentNumber The consignmentNumber to set.
     */
    public void setConsignmentNumber(String consignmentNumber) {
        this.consignmentNumber = consignmentNumber;
    }


    /**
	 * @return Returns the ownerGPACode.
	 */
	public String getOwnerGPACode() {
		return ownerGPACode;
	}
	/**
	 * @param ownerGPACode The ownerGPACode to set.
	 */
	public void setOwnerGPACode(String ownerGPACode) {
		this.ownerGPACode = ownerGPACode;
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
	 * @return Returns the billingBasis.
	 */
	public String getBillingBasis() {
		return billingBasis;
	}
	/**
	 * @param billingBasis The billingBasis to set.
	 */
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
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
	 * @return Returns the consignmentSequenceNumber.
	 */
	public int getConsignmentSequenceNumber() {
		return consignmentSequenceNumber;
	}
	/**
	 * @param consignmentSequenceNumber The consignmentSequenceNumber to set.
	 */
	public void setConsignmentSequenceNumber(int consignmentSequenceNumber) {
		this.consignmentSequenceNumber = consignmentSequenceNumber;
	}
	/**
	 * @return Returns the containerNumber.
	 */
	public String getContainerNumber() {
		return containerNumber;
	}
	/**
	 * @param containerNumber The containerNumber to set.
	 */
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	/**
	 * @return Returns the flightCarrierIdentifier.
	 */
	public int getFlightCarrierIdentifier() {
		return flightCarrierIdentifier;
	}
	/**
	 * @param flightCarrierIdentifier The flightCarrierIdentifier to set.
	 */
	public void setFlightCarrierIdentifier(int flightCarrierIdentifier) {
		this.flightCarrierIdentifier = flightCarrierIdentifier;
	}
	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	/**
	 * @return Returns the flightSequenceNumber.
	 */
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}
	/**
	 * @param flightSequenceNumber The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}
	/**
	 * @return Returns the segmentSerialNumber.
	 */
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}
	/**
	 * @param segmentSerialNumber The segmentSerialNumber to set.
	 */
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
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
	 * @return the vatAmount
	 */
	public double getVatAmount() {
		return vatAmount;
	}
	/**
	 * @param vatAmount the vatAmount to set
	 */
	public void setVatAmount(double vatAmount) {
		this.vatAmount = vatAmount;
	}
	/**
	 * @return the valCharges
	 */
	public double getValCharges() {
		return valCharges;
	}
	/**
	 * @param valCharges the valCharges to set
	 */
	public void setValCharges(double valCharges) {
		this.valCharges = valCharges;
	}
	/**
	 * @return the netAmtBillingCurrency
	 */
	public double getNetAmtBillingCurrency() {
		return netAmtBillingCurrency;
	}
	/**
	 * @param netAmtBillingCurrency the netAmtBillingCurrency to set
	 */
	public void setNetAmtBillingCurrency(double netAmtBillingCurrency) {
		this.netAmtBillingCurrency = netAmtBillingCurrency;
	}
	/**
	 * @return the billingCurrency
	 */
	public String getBillingCurrency() {
		return billingCurrency;
	}
	/**
	 * @param billingCurrency the billingCurrency to set
	 */
	public void setBillingCurrency(String billingCurrency) {
		this.billingCurrency = billingCurrency;
	}
	/**
	 * @return the extraWeight
	 */
	public double getExtraWeight() {
		return extraWeight;
	}
	/**
	 * @param extraWeight the extraWeight to set
	 */
	public void setExtraWeight(double extraWeight) {
		this.extraWeight = extraWeight;
	}
	/**
	 * @return the totalValCharges
	 */
	public double getTotalValCharges() {
		return totalValCharges;
	}
	/**
	 * @param totalValCharges the totalValCharges to set
	 */
	public void setTotalValCharges(double totalValCharges) {
		this.totalValCharges = totalValCharges;
	}
	/**
	 * @return the totalVatAmount
	 */
	public double getTotalVatAmount() {
		return totalVatAmount;
	}
	/**
	 * @param totalVatAmount the totalVatAmount to set
	 */
	public void setTotalVatAmount(double totalVatAmount) {
		this.totalVatAmount = totalVatAmount;
	}
	/**
	 * @return the totalGrsAmount
	 */
	public double getTotalGrsAmount() {
		return totalGrsAmount;
	}
	/**
	 * @param totalGrsAmount the totalGrsAmount to set
	 */
	public void setTotalGrsAmount(double totalGrsAmount) {
		this.totalGrsAmount = totalGrsAmount;
	}
	/**
	 * @param odWeight the odWeight to set
	 */
	public void setOdWeight(double odWeight) {
		this.odWeight = odWeight;
	}
	/**
	 * @return the odWeight
	 */
	public double getOdWeight() {
		return odWeight;
	}   
/**
	 * @return the surchargeAmtBillable
	 */
	public Money getSurchargeAmtBillable() {
		return surchargeAmtBillable;
	}
	/**
	 * @param surchargeAmtBillable the surchargeAmtBillable to set
	 */
	public void setSurchargeAmtBillable(Money surchargeAmtBillable) {
		this.surchargeAmtBillable = surchargeAmtBillable;
	}
	/**
	 * @return the taxAmount
	 */
	public Money getTaxAmount() {
		return taxAmount;
	}
	/**
	 * @param taxAmount the taxAmount to set
	 */
	public void setTaxAmount(Money taxAmount) {
		this.taxAmount = taxAmount;
	}
	public String getBillingBase() {
		return billingBase;
	}
	public void setBillingBase(String billingBase) {
		this.billingBase = billingBase;
	}
	public String getDespatchId() {
		return despatchId;
	}
	public void setDespatchId(String despatchId) {
		this.despatchId = despatchId;
	}
	/**
	 * 	Getter for declaredValue 
	 *	Added by : A-6991 on 22-Aug-2017
	 * 	Used for :
	 */
	public double getDeclaredValue() {
		return declaredValue;
	}
	/**
	 *  @param declaredValue the declaredValue to set
	 * 	Setter for declaredValue 
	 *	Added by : A-6991 on 22-Aug-2017
	 * 	Used for :
	 */
	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
	}
	 public long getMailSequenceNumber() {
			return mailSequenceNumber;
		}
		public void setMailSequenceNumber(long mailSequenceNumber) {
			this.mailSequenceNumber = mailSequenceNumber;
	}
	
}
