/*
 * MailbagInULDAtAirportVO.java Created on JUN 30, 2016
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
 * TODO Add the purpose of this class
 *
 * @author A-5991
 *
 */
/*
 *  Revision History
 *--------------------------------------------------------------------------
 *  Revision 	Date      	           		   Author			Description
 * -------------------------------------------------------------------------
 *  0.1			JUN 30, 2016				   A-5991			First Draft
 */
public class MailbagInULDAtAirportVO extends AbstractVO {

    private String mailId;
    private String containerNumber;
    private String damageFlag;
    private LocalDate scannedDate;
   //private double weight; 
    private Measure weight;//added by A-7371
    
    private String mailClass;
    private String mailSubclass;
    private String mailCategoryCode;
    private String transferFromCarrier;    
    private String sealNumber;
    private long mailSequenceNumber;
    private int  statedBags;
    //private double statedWgt;
    private Measure statedWgt;//added by A-7371
    private int acceptedBags;
   // private double acceptedWgt;
    private Measure acceptedWgt;//added by A-7371
    private int deliveredPcs;
    //private double deliveredWgt;
    private Measure deliveredWgt;//added by A-7371
    private int recievedPcs;
   // private double recievedWgt;
    private Measure recievedWgt;//added by A-7371
    private String comapnyCode;
    private int carrierId;
    private String airportCode;
    private String uldNumber;
    private String arrivedFlag;
    private String deliveredFlag;
    private String acceptedFlag;
    private String mailSource;
    
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
	 * 
	 * @return statedWgt
	 */
	public Measure getStatedWgt() {
		return statedWgt;
	}
	/**
	 * 
	 * @param statedWgt
	 */
	public void setStatedWgt(Measure statedWgt) {
		this.statedWgt = statedWgt;
	}
	/**
	 * 
	 * @return acceptedWgt
	 */
	public Measure getAcceptedWgt() {
		return acceptedWgt;
	}
	/**
	 * 
	 * @param acceptedWgt
	 */
	public void setAcceptedWgt(Measure acceptedWgt) {
		this.acceptedWgt = acceptedWgt;
	}
	/**
	 * 
	 * @return deliveredWgt
	 */
	public Measure getDeliveredWgt() {
		return deliveredWgt;
	}
	/**
	 * 
	 * @param deliveredWgt
	 */
	public void setDeliveredWgt(Measure deliveredWgt) {
		this.deliveredWgt = deliveredWgt;
	}
	/**
	 * 
	 * @return recievedWgt
	 */
	public Measure getRecievedWgt() {
		return recievedWgt;
	}
	/**
	 * 
	 * @param recievedWgt
	 */
	public void setRecievedWgt(Measure recievedWgt) {
		this.recievedWgt = recievedWgt;
	}
    /**
	 * @return the arrivedFlag
	 */
	public String getArrivedFlag() {
		return arrivedFlag;
	}
	/**
	 * @param arrivedFlag the arrivedFlag to set
	 */
	public void setArrivedFlag(String arrivedFlag) {
		this.arrivedFlag = arrivedFlag;
	}
	/**
	 * @return the deliveredFlag
	 */
	public String getDeliveredFlag() {
		return deliveredFlag;
	}
	/**
	 * @param deliveredFlag the deliveredFlag to set
	 */
	public void setDeliveredFlag(String deliveredFlag) {
		this.deliveredFlag = deliveredFlag;
	}
	/**
	 * @return the acceptedFlag
	 */
	public String getAcceptedFlag() {
		return acceptedFlag;
	}
	/**
	 * @param acceptedFlag the acceptedFlag to set
	 */
	public void setAcceptedFlag(String acceptedFlag) {
		this.acceptedFlag = acceptedFlag;
	}
    /**
	 * @return the statedPcs
	 */
	public int getStatedBags() {
		return statedBags;
	}
	/**
	 * @param statedPcs the statedPcs to set
	 */
	public void setStatedBags(int statedBags) {
		this.statedBags = statedBags;
	}
	/**
	 * @return the statedWgt
	 */
	/*public double getStatedWgt() {
		return statedWgt;
	}
	*//**
	 * @param statedWgt the statedWgt to set
	 *//*
	public void setStatedWgt(double statedWgt) {
		this.statedWgt = statedWgt;
	}*/
	/**
	 * @return the acceptedPcs
	 */
	public int getAcceptedBags() {
		return acceptedBags;
	}
	/**
	 * @param acceptedPcs the acceptedPcs to set
	 */
	public void setAcceptedBags(int acceptedBags) {
		this.acceptedBags = acceptedBags;
	}
	/**
	 * @return the acceptedWgt
	 */
	/*public double getAcceptedWgt() {
		return acceptedWgt;
	}
	*//**
	 * @param acceptedWgt the acceptedWgt to set
	 *//*
	public void setAcceptedWgt(double acceptedWgt) {
		this.acceptedWgt = acceptedWgt;
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
     * @return Returns the damageFlag.
     */
    public String getDamageFlag() {
        return damageFlag;
    }
    /**
     * @param damageFlag The damageFlag to set.
     */
    public void setDamageFlag(String damageFlag) {
        this.damageFlag = damageFlag;
    }
    /**
     * @return Returns the mailId.
     */
    public String getMailId() {
        return mailId;
    }
    /**
     * @param mailId The mailId to set.
     */
    public void setMailId(String mailId) {
        this.mailId = mailId;
    }
    /**
     * @return Returns the scannedDate.
     */
    public LocalDate getScannedDate() {
        return scannedDate;
    }
    /**
     * @param scannedDate The scannedDate to set.
     */
    public void setScannedDate(LocalDate scannedDate) {
        this.scannedDate = scannedDate;
    }
    
    /**
     * @return Returns the weight.
     */
    /*public double getWeight() {
        return weight;
    }
    *//**
     * @param weight The weight to set.
     *//*
    public void setWeight(double weight) {
        this.weight = weight;
    }*/
	/**
	 * @return Returns the fromCarrierCode.
	 */
	public String getTransferFromCarrier() {
		return transferFromCarrier;
	}
	/**
	 * @param fromCarrierCode The fromCarrierCode to set.
	 */
	public void setTransferFromCarrier(String fromCarrierCode) {
		this.transferFromCarrier = fromCarrierCode;
	}
	/**
	 * @return the sealNumber
	 */
	public String getSealNumber() {
		return sealNumber;
	}
	/**
	 * @param sealNumber the sealNumber to set
	 */
	public void setSealNumber(String sealNumber) {
		this.sealNumber = sealNumber;
	}
	/**
	 * @return the mailSequenceNumber
	 */
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	/**
	 * @param mailSequenceNumber the mailSequenceNumber to set
	 */
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	/**
	 * @return the deliveredPcs
	 */
	public int getDeliveredPcs() {
		return deliveredPcs;
	}
	/**
	 * @param deliveredPcs the deliveredPcs to set
	 */
	public void setDeliveredPcs(int deliveredPcs) {
		this.deliveredPcs = deliveredPcs;
	}
	/**
	 * @return the deliveredWgt
	 */
	/*public double getDeliveredWgt() {
		return deliveredWgt;
	}
	*//**
	 * @param deliveredWgt the deliveredWgt to set
	 *//*
	public void setDeliveredWgt(double deliveredWgt) {
		this.deliveredWgt = deliveredWgt;
	}*/
	/**
	 * @return the recievedPcs
	 */
	public int getRecievedPcs() {
		return recievedPcs;
	}
	/**
	 * @param recievedPcs the recievedPcs to set
	 */
	public void setRecievedPcs(int recievedPcs) {
		this.recievedPcs = recievedPcs;
	}
	/**
	 * @return the recievedWgt
	 */
	/*public double getRecievedWgt() {
		return recievedWgt;
	}
	*//**
	 * @param recievedWgt the recievedWgt to set
	 *//*
	public void setRecievedWgt(double recievedWgt) {
		this.recievedWgt = recievedWgt;
	}*/
	/**
	 * @return the comapnyCode
	 */
	public String getComapnyCode() {
		return comapnyCode;
	}
	/**
	 * @param comapnyCode the comapnyCode to set
	 */
	public void setComapnyCode(String comapnyCode) {
		this.comapnyCode = comapnyCode;
	}

	/**
	 * @return the airportCode
	 */
	public String getAirportCode() {
		return airportCode;
	}
	/**
	 * @param airportCode the airportCode to set
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	/**
	 * @return the uldNumber
	 */
	public String getUldNumber() {
		return uldNumber;
	}
	/**
	 * @param uldNumber the uldNumber to set
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
	/**
	 * @return the carrierId
	 */
	public int getCarrierId() {
		return carrierId;
	}
	/**
	 * @param carrierId the carrierId to set
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}
	/**
	 * 	Getter for mailSource 
	 *	Added by : A-8061 on 29-Mar-2021
	 * 	Used for :
	 */
	public String getMailSource() {
		return mailSource;
	}
	/**
	 *  @param mailSource the mailSource to set
	 * 	Setter for mailSource 
	 *	Added by : A-8061 on 29-Mar-2021
	 * 	Used for :
	 */
	public void setMailSource(String mailSource) {
		this.mailSource = mailSource;
	}
	
	
    
    
}
