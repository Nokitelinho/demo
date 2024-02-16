/*
 * CN66DetailsVO.java Created on Jan 8, 2007
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
 * @author a-2270  Aug 09 2007 For optimistic Locking 
 *
 */
public class CN66DetailsVO extends AbstractVO {

    private String companyCode;
    private String billingPeriod;
    private String gpaCode;
    private int sequenceNumber;
    private int invSerialNumber;
    private LocalDate receivedDate;

    /**
     * The billing basis
     * The value of this field may be a despatch number or
     * a mail bag number
     */
    private String billingBasis;
    private String origin;
    private String destination;
    private String mailCategoryCode;
    private String mailSubclass;
    private double totalWeight;
    private int totalPieces;
    private String dsn;
    private String receptacleSerialNumber;
    private String remarks;
    private String invoiceNumber;
    private String sector;
    private String flightCarrierCode;
    private String flightNumber;
    private LocalDate flightDate;

    private String containerNumber;
    private String paBuiltFlag ;
    private double actualUldWeight ;
    private String actualUldWeightUnit ;
    
    private double applicableRate;
	private double  amount;
	private double totalAmount;
    private String  billingStatus;
    
    private String sectorForReport;
    //Added for optmistic locking
     private LocalDate lastupdatedTime;
     private String lastUpdatedUser;
     
     private String consDocNo;
     private String consSeqNo;
     private String ccaRefNo;
     
     private Money  actualAmount;
     private String actualSubCls;
     private String billFrm;
     private String billTo;
     
     private String fuelSurchargeRateIndicator;
     private double fuelSurcharge;
     private String blgPrd;
     private String monthFlag;

     private Money vatAmount;
     
     private double weightLC;
     private double weightCP;
     private double weightSV;
     private double weightEMS;
     
     //added by a-3434 for bug 87354
     private String currencyCode;
     
     private Money totalAmtinLC;
     private Money totalAmtinCP;
   //added for cr ICRD-7370
 	private double serviceTax;
 	private double tds;
 	private Money netAmount;
 	private String rsn;
 	private String hsn;
 	
	private String regInd;
 	private long mailSequenceNumber;
	//Added by A-8527 for ICRD-351434
 	private double db_netAmount;
 	//Added by A-8527 for ICRD-324283
 	private String settlementCurrencyCode;
 	//Added as  a part of ICRD-193493 by A-7540
 	private String flightDateInString;
 	public String getFlightDateInString() {
		return flightDateInString;
	}
	public void setFlightDateInString(String flightDateInString) {
		this.flightDateInString = flightDateInString;
	}
	public String getRegInd() {
		return regInd;
	}
	public void setRegInd(String regInd) {
		this.regInd = regInd;
	}
 	private Money totalWgtinSV;
 	private Money totalWgtinEMS;
 	private double valCharges;
 	//Added by A-6991 for ICRD-213474
  	private double declaredValue;
 	private String mailCharge;
 	private String surCharge;
 	private String overrideRounding;
 	private double scalarNetAmount;
 // Added by A-8527 for IASCB-22915
 	private double weight;
 	private String unitcode;
 	//a-10383
 	private String cn66orgcod;
	private String cn66dstcod;
	private String cn66malctgcod;
	private String cn66dsn;
	private String cntidr;
	private String fltdat;
	private String fltnum;
	private String cn66malsubcls;
	private String cn66totwgt;
	private String bldamt;
	private String totamtcp;
	private String srvtax;
	private String netamt;
	private String c51smyblgcurcod;
	private String bldprd;
	private String ponam;
	private String poaadr;
	private String invdat;
	private String cn66gpacod;
	private String cn66invnum;
	private String rate;
	private String mca;
	private String total;
	private double netamtdouble;
	private String c51smyctrcurcod;
	private String totnetblgcur;
	private String totnetctrcur;
	
	public String getTotnetblgcur() {
		return totnetblgcur;
	}
	public void setTotnetblgcur(String totnetblgcur) {
		this.totnetblgcur = totnetblgcur;
	}
	public String getTotnetctrcur() {
		return totnetctrcur;
	}
	public void setTotnetctrcur(String totnetctrcur) {
		this.totnetctrcur = totnetctrcur;
	}
	public String getC51smyctrcurcod() {
		return c51smyctrcurcod;
	}
	public void setC51smyctrcurcod(String c51smyctrcurcod) {
		this.c51smyctrcurcod = c51smyctrcurcod;
	}
	public double getNetamtdouble() {
		return netamtdouble;
	}
	public void setNetamtdouble(double netamtdouble) {
		this.netamtdouble = netamtdouble;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getCn66orgcod() {
		return cn66orgcod;
	}
	public void setCn66orgcod(String cn66orgcod) {
		this.cn66orgcod = cn66orgcod;
	}
	public String getCn66dstcod() {
		return cn66dstcod;
	}
	public void setCn66dstcod(String cn66dstcod) {
		this.cn66dstcod = cn66dstcod;
	}
	public String getCn66malctgcod() {
		return cn66malctgcod;
	}
	public void setCn66malctgcod(String cn66malctgcod) {
		this.cn66malctgcod = cn66malctgcod;
	}
	public String getCn66dsn() {
		return cn66dsn;
	}
	public void setCn66dsn(String cn66dsn) {
		this.cn66dsn = cn66dsn;
	}

	public String getCntidr() {
		return cntidr;
	}
	public void setCntidr(String cntidr) {
		this.cntidr = cntidr;
	}
	public String getFltdat() {
		return fltdat;
	}
	public void setFltdat(String fltdat) {
		this.fltdat = fltdat;
	}
	public String getFltnum() {
		return fltnum;
	}
	public void setFltnum(String fltnum) {
		this.fltnum = fltnum;
	}
	public String getCn66malsubcls() {
		return cn66malsubcls;
	}
	public void setCn66malsubcls(String cn66malsubcls) {
		this.cn66malsubcls = cn66malsubcls;
	}
	public String getCn66totwgt() {
		return cn66totwgt;
	}
	public void setCn66totwgt(String cn66totwgt) {
		this.cn66totwgt = cn66totwgt;
	}
	public String getBldamt() {
		return bldamt;
	}
	public void setBldamt(String bldamt) {
		this.bldamt = bldamt;
	}
	public String getTotamtcp() {
		return totamtcp;
	}
	public void setTotamtcp(String totamtcp) {
		this.totamtcp = totamtcp;
	}
	public String getSrvtax() {
		return srvtax;
	}
	public void setSrvtax(String srvtax) {
		this.srvtax = srvtax;
	}
	public String getNetamt() {
		return netamt;
	}
	public void setNetamt(String netamt) {
		this.netamt = netamt;
	}
	public String getC51smyblgcurcod() {
		return c51smyblgcurcod;
	}
	public void setC51smyblgcurcod(String c51smyblgcurcod) {
		this.c51smyblgcurcod = c51smyblgcurcod;
	}
	public String getBldprd() {
		return bldprd;
	}
	public void setBldprd(String bldprd) {
		this.bldprd = bldprd;
	}
	public String getPonam() {
		return ponam;
	}
	public void setPonam(String ponam) {
		this.ponam = ponam;
	}
	public String getPoaadr() {
		return poaadr;
	}
	public void setPoaadr(String poaadr) {
		this.poaadr = poaadr;
	}
	public static String getMailsubclassLc() {
		return MAILSUBCLASS_LC;
	}
	public static String getMailsubclassCp() {
		return MAILSUBCLASS_CP;
	}
	public String getInvdat() {
		return invdat;
	}
	public void setInvdat(String invdat) {
		this.invdat = invdat;
	}
	public String getCn66gpacod() {
		return cn66gpacod;
	}
	public void setCn66gpacod(String cn66gpacod) {
		this.cn66gpacod = cn66gpacod;
	}
	public String getCn66invnum() {
		return cn66invnum;
	}
	public void setCn66invnum(String cn66invnum) {
		this.cn66invnum = cn66invnum;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	
	public String getMca() {
		return mca;
	}
	public void setMca(String mca) {
		this.mca = mca;
	}
	//a-10383
	
 	
 	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}	
 	/**
	 * @return the rsn
	 */
	public String getRsn() {
		return rsn;
	}
	/**
	 * @param rsn the rsn to set
	 */
	public void setRsn(String rsn) {
		this.rsn = rsn;
	}
	/**
	 * @return the hsn
	 */
	public String getHsn() {
		return hsn;
	}
	/**
	 * @param hsn the hsn to set
	 */
	public void setHsn(String hsn) {
		this.hsn = hsn;
	}
	/**
	 * @return the totalWgtinSV
	 */
	public Money getTotalWgtinSV() {
		return totalWgtinSV;
	}
	/**
	 * @param totalWgtinSV the totalWgtinSV to set
	 */
	public void setTotalWgtinSV(Money totalWgtinSV) {
		this.totalWgtinSV = totalWgtinSV;
	}
	/**
	 * @return the totalWgtinEMS
	 */
	public Money getTotalWgtinEMS() {
		return totalWgtinEMS;
	}
	/**
	 * @param totalWgtinEMS the totalWgtinEMS to set
	 */
	public void setTotalWgtinEMS(Money totalWgtinEMS) {
		this.totalWgtinEMS = totalWgtinEMS;
	}
	/**
	 * @return the netAmount
	 */
	public Money getNetAmount() {
		return netAmount;
	}
	/**
	 * @param netAmount the netAmount to set
	 */
	public void setNetAmount(Money netAmount) {
		this.netAmount = netAmount;
	}
 	public double getDb_netAmount() {
		return db_netAmount;
	}
	public void setDb_netAmount(double db_netAmount) {
		this.db_netAmount = db_netAmount;
	}
 	private double grossAmount;
    //added by A-4823
 	private String distance;
     public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
     public static final String MAILSUBCLASS_LC = "LC";

 	 public static final String MAILSUBCLASS_CP = "CP";

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
	 * @return Returns the blgPrd.
	 */
	public String getBlgPrd() {
		return blgPrd;
	}
	/**
	 * @param blgPrd The blgPrd to set.
	 */
	public void setBlgPrd(String blgPrd) {
		this.blgPrd = blgPrd;
	}
	/**
	 * @return Returns the billFrm.
	 */
	public String getBillFrm() {
		return billFrm;
	}
	/**
	 * @param billFrm The billFrm to set.
	 */
	public void setBillFrm(String billFrm) {
		this.billFrm = billFrm;
	}
	/**
	 * @return Returns the billTo.
	 */
	public String getBillTo() {
		return billTo;
	}
	/**
	 * @param billTo The billTo to set.
	 */
	public void setBillTo(String billTo) {
		this.billTo = billTo;
	}
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
	 * @return Returns the ccaRefNo.
	 */
	public String getCcaRefNo() {
		return ccaRefNo;
	}
	/**
	 * @param ccaRefNo The ccaRefNo to set.
	 */
	public void setCcaRefNo(String ccaRefNo) {
		this.ccaRefNo = ccaRefNo;
	}
	/**
	 * @return Returns the amount.
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(double amount) {
		this.amount = amount;
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
	 * @return Returns the totalAmount.
	 */
	public double getTotalAmount() {
		return totalAmount;
	}
	/**
	 * @param totalAmount The totalAmount to set.
	 */
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	/**
	 * @return the lastupdatedTime
	 */
	public LocalDate getLastupdatedTime() {
		return lastupdatedTime;
	}
	/**
	 * @param lastupdatedTime the lastupdatedTime to set
	 */
	public void setLastupdatedTime(LocalDate lastupdatedTime) {
		this.lastupdatedTime = lastupdatedTime;
	}
	/**
	 * @return the lastUpdatedUser
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	/**
	 * @return Returns the sectorForReport.
	 */
	public String getSectorForReport() {
		return sectorForReport;
	}
	/**
	 * @param sectorForReport The sectorForReport to set.
	 */
	public void setSectorForReport(String sectorForReport) {
		this.sectorForReport = sectorForReport;
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
	public String getConsDocNo() {
		return consDocNo;
	}
	public void setConsDocNo(String consDocNo) {
		this.consDocNo = consDocNo;
	}
	public String getConsSeqNo() {
		return consSeqNo;
	}
	public void setConsSeqNo(String consSeqNo) {
		this.consSeqNo = consSeqNo;
	}
	/**
	 * @return Returns the actualAmount.
	 */
	public Money getActualAmount() {
		return actualAmount;
	}
	/**
	 * @param actualAmount The actualAmount to set.
	 */
	public void setActualAmount(Money actualAmount) {
		this.actualAmount = actualAmount;
	}
	/**
	 * @return the fuelSurcharge
	 */
	public double getFuelSurcharge() {
		return fuelSurcharge;
	}
	/**
	 * @param fuelSurcharge the fuelSurcharge to set
	 */
	public void setFuelSurcharge(double fuelSurcharge) {
		this.fuelSurcharge = fuelSurcharge;
	}
	/**
	 * @return the fuelSurchargeRateIndicator
	 */
	public String getFuelSurchargeRateIndicator() {
		return fuelSurchargeRateIndicator;
	}
	/**
	 * @param fuelSurchargeRateIndicator the fuelSurchargeRateIndicator to set
	 */
	public void setFuelSurchargeRateIndicator(String fuelSurchargeRateIndicator) {
		this.fuelSurchargeRateIndicator = fuelSurchargeRateIndicator;
	}
	/**
	 * @return the flightCarrierCode
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}
	/**
	 * @param flightCarrierCode the flightCarrierCode to set
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
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
	 * @param valCharges the valCharges to set
	 */
	public void setValCharges(double valCharges) {
		this.valCharges = valCharges;
	}
	/**
	 * 	Getter for declaredValue 
	 *	Added by : A-6991 on 21-Aug-2017
	 * 	Used for : ICRD-213474
	 */
	public double getDeclaredValue() {
		return declaredValue;
	}
	/**
	 *  @param declaredValue the declaredValue to set
	 * 	Setter for declaredValue 
	 *	Added by : A-6991 on 21-Aug-2017
	 * 	Used for : ICRD-213474
	 */
	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
	}
	/**
	 * @return the valCharges
	 */
	public double getValCharges() {
		return valCharges;
	}
	/**
	 * 
	 * @return weightSV
	 */
	public double getWeightSV() {
		return weightSV;
	}
	/**
	 * 
	 * @param weightSV
	 */
	public void setWeightSV(double weightSV) {
		this.weightSV = weightSV;
	}
	/**
	 * 
	 * @return weightEMS
	 */
	public double getWeightEMS() {
		return weightEMS;
	}
	/**
	 * 
	 * @param weightEMS
	 */
	public void setWeightEMS(double weightEMS) {
		this.weightEMS = weightEMS;
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
	 * @return the scalarNetAmount
	 */
	public double getScalarNetAmount() {
		return scalarNetAmount;
	}
	/**
	 * @param scalarNetAmount the scalarNetAmount to set
	 */
	public void setScalarNetAmount(double scalarNetAmount) {
		this.scalarNetAmount = scalarNetAmount;
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
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}
	public void setSettlementCurrencyCode(String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}
	public String getUnitcode() {
		return unitcode;
	}
	public void setUnitcode(String unitcode) {
		this.unitcode = unitcode;
	}
	public String getContainerNumber() {
		return containerNumber;
	}
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	public String getPaBuiltFlag() {
		return paBuiltFlag;
	}
	public void setPaBuiltFlag(String paBuiltFlag) {
		this.paBuiltFlag = paBuiltFlag;
	}
	public double getActualUldWeight() {
		return actualUldWeight;
	}
	public void setActualUldWeight(double actualUldWeight) {
		this.actualUldWeight = actualUldWeight;
	}
	public String getActualUldWeightUnit() {
		return actualUldWeightUnit;
	}
	public void setActualUldWeightUnit(String actualUldWeightUnit) {
		this.actualUldWeightUnit = actualUldWeightUnit;
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
