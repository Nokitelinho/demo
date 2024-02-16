/*
 * DSNForSegmentVO.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;


import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-5991
 *
 */
public class DSNForSegmentVO extends AbstractVO {
    private int acceptedBags;
    //private double acceptedWeight;
    private Measure acceptedWeight;//added by A-7371
    private int statedBags;
    //private double statedWeight;
    private Measure statedWeight;//added by A-7371
    private String companyCode;    
    private int carrierId;
    private String flightNumber;
    private long flightSequenceNumber;
    private int segmentSerialNumber;
        
    private String dsn;
    private String originOfficeOfExchange;
    private String destinationOfficeOfExchange;
    private String mailClass;
    private int year; 
    private int receivedBags;
    //private double receivedWeight;
    private Measure receivedWeight; //added by A-7371
    private int deliveredBags; 
    
    /**
     * The mailSubclass
     */
    private String mailSubclass;
    /**
     * The mailCategoryCode
     */
    private String mailCategoryCode;
    
    //private double deliveredWeight;
    private Measure deliveredWeight;//added by A-7371
    /**
     * 
     * @return acceptedWeight
     */
    public Measure getAcceptedWeight() {
		return acceptedWeight;
	}
    /**
     * 
     * @param acceptedWeight
     */
	public void setAcceptedWeight(Measure acceptedWeight) {
		this.acceptedWeight = acceptedWeight;
	}
	/**
	 * 
	 * @return statedWeight
	 */
	public Measure getStatedWeight() {
		return statedWeight;
	}
	/**
	 * 
	 * @param statedWeight
	 */
	public void setStatedWeight(Measure statedWeight) {
		this.statedWeight = statedWeight;
	}
	/**
	 * 
	 * @return receivedWeight
	 */
	public Measure getReceivedWeight() {
		return receivedWeight;
	}
	/**
	 * 
	 * @param receivedWeight
	 */
	public void setReceivedWeight(Measure receivedWeight) {
		this.receivedWeight = receivedWeight;
	}
	/**
	 * 
	 * @return deliveredWeight
	 */
	public Measure getDeliveredWeight() {
		return deliveredWeight;
	}
	/**
	 * 
	 * @param deliveredWeight
	 */
	public void setDeliveredWeight(Measure deliveredWeight) {
		this.deliveredWeight = deliveredWeight;
	}
    /**
	 * @return Returns the deliveredBags.
	 */
	public int getDeliveredBags() {
		return deliveredBags;
	}
	/**
	 * @param deliveredBags The deliveredBags to set.
	 */
	public void setDeliveredBags(int deliveredBags) {
		this.deliveredBags = deliveredBags;
	}
	/**
	 * @return Returns the deliveredWeight.
	 */
	/*public double getDeliveredWeight() {
		return deliveredWeight;
	}
	*//**
	 * @param deliveredWeight The deliveredWeight to set.
	 *//*
	public void setDeliveredWeight(double deliveredWeight) {
		this.deliveredWeight = deliveredWeight;
	}*/
	/**
	 * @return Returns the receivedBags.
	 */
	public int getReceivedBags() {
		return receivedBags;
	}
	/**
	 * @param receivedBags The receivedBags to set.
	 */
	public void setReceivedBags(int receivedBags) {
		this.receivedBags = receivedBags;
	}
	/**
	 * @return Returns the receivedWeight.
	 */
	/*public double getReceivedWeight() {
		return receivedWeight;
	}
	*//**
	 * @param receivedWeight The receivedWeight to set.
	 *//*
	public void setReceivedWeight(double receivedWeight) {
		this.receivedWeight = receivedWeight;
	}*/
	/**
     * @return Returns the carrierId.
     */
    public int getCarrierId() {
        return carrierId;
    }
    /**
     * @param carrierId The carrierId to set.
     */
    public void setCarrierId(int carrierId) {
        this.carrierId = carrierId;
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
     * @return Returns the destinationCity.
     */
    public String getDestinationOfficeOfExchange() {
        return destinationOfficeOfExchange;
    }
    /**
     * @param destinationCity The destinationCity to set.
     */
    public void setDestinationOfficeOfExchange(String destinationCity) {
        this.destinationOfficeOfExchange = destinationCity;
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
     * @return Returns the originCity.
     */
    public String getOriginOfficeOfExchange() {
        return originOfficeOfExchange;
    }
    /**
     * @param originCity The originCity to set.
     */
    public void setOriginOfficeOfExchange(String originCity) {
        this.originOfficeOfExchange = originCity;
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
     * @return Returns the totalBags.
     */
    public int getAcceptedBags() {
        return acceptedBags;
    }
    /**
     * @param totalBags The totalBags to set.
     */
    public void setAcceptedBags(int totalBags) {
        this.acceptedBags = totalBags;
    }
    /**
     * @return Returns the totalWeight.
     */
   /* public double getAcceptedWeight() {
        return acceptedWeight;
    }
    *//**
     * @param totalWeight The totalWeight to set.
     *//*
    public void setAcceptedWeight(double totalWeight) {
        this.acceptedWeight = totalWeight;
    }
    *//**
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
     * @return Returns the statedBags.
     */
    public int getStatedBags() {
        return statedBags;
    }
    /**
     * @param statedBags The statedBags to set.
     */
    public void setStatedBags(int statedBags) {
        this.statedBags = statedBags;
    }
    /**
     * @return Returns the statedWeight.
     */
  /*  public double getStatedWeight() {
        return statedWeight;
    }
    *//**
     * @param statedWeight The statedWeight to set.
     *//*
    public void setStatedWeight(double statedWeight) {
        this.statedWeight = statedWeight;
    }*/
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
    
    
}
