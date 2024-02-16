/*
 * DSNInConsignmentForULDAtAirportVO.java Created on JUN 30, 2016
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
public class DSNInConsignmentForULDAtAirportVO extends AbstractVO {

    private String consignmentNumber;
    
    private LocalDate consignmentDate;
    
    private int acceptedBags;
    
    //private double acceptedWeight;
    private Measure acceptedWeight;//added by A-7371
    
    private int statedBags;
    
    //private double statedWeight;
    private Measure statedWeight; //added by A-7371
    
    private String acceptedUser;
    
    private String mailClass;
    
    private LocalDate acceptedDate;
    
    private String paCode;
    
    private int sequenceNumber;
    
    private String offloadFlag;
    
    //private double statedVolume;
    private Measure statedVolume;//added by A-7371
    
    //private double acceptedVolume;
    private Measure acceptedVolume;//added by A-7371
    
   
    private String transactionCode;
    
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
 * @return statedVolume
 */
	public Measure getStatedVolume() {
		return statedVolume;
	}
/**
 * 
 * @param statedVolume
 */
	public void setStatedVolume(Measure statedVolume) {
		this.statedVolume = statedVolume;
	}
/**
 * 
 * @return acceptedVolume
 */
	public Measure getAcceptedVolume() {
		return acceptedVolume;
	}
/**
 * 
 * @param acceptedVolume
 */
	public void setAcceptedVolume(Measure acceptedVolume) {
		this.acceptedVolume = acceptedVolume;
	}

    /**
     * @return Returns the consignmentDate.
     */
    public LocalDate getConsignmentDate() {
        return consignmentDate;
    }

    /**
     * @param consignmentDate The consignmentDate to set.
     */
    public void setConsignmentDate(LocalDate consignmentDate) {
        this.consignmentDate = consignmentDate;
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
     * @return Returns the acceptedBags.
     */
    public int getAcceptedBags() {
        return acceptedBags;
    }

    /**
     * @param acceptedBags The acceptedBags to set.
     */
    public void setAcceptedBags(int acceptedBags) {
        this.acceptedBags = acceptedBags;
    }

    /**
     * @return Returns the acceptedUser.
     */
    public String getAcceptedUser() {
        return acceptedUser;
    }

    /**
     * @param acceptedUser The acceptedUser to set.
     */
    public void setAcceptedUser(String acceptedUser) {
        this.acceptedUser = acceptedUser;
    }

    /**
     * @return Returns the acceptedWeight.
     */
  /*  public double getAcceptedWeight() {
        return acceptedWeight;
    }

    *//**
     * @param acceptedWeight The acceptedWeight to set.
     *//*
    public void setAcceptedWeight(double acceptedWeight) {
        this.acceptedWeight = acceptedWeight;
    }*/

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
     *//*
    public double getStatedWeight() {
        return statedWeight;
    }

    *//**
     * @param statedWeight The statedWeight to set.
     *//*
    public void setStatedWeight(double statedWeight) {
        this.statedWeight = statedWeight;
    }*/

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
     * @return Returns the acceptedDate.
     */
    public LocalDate getAcceptedDate() {
        return acceptedDate;
    }

    /**
     * @param acceptedDate The acceptedDate to set.
     */
    public void setAcceptedDate(LocalDate acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    /**
     * @return Returns the paCode.
     */
    public String getPaCode() {
        return paCode;
    }

    /**
     * @param paCode The paCode to set.
     */
    public void setPaCode(String paCode) {
        this.paCode = paCode;
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
     * @return Returns the offloadFlag.
     */
    public String getOffloadFlag() {
        return offloadFlag;
    }

    /**
     * @param offloadFlag The offloadFlag to set.
     */
    public void setOffloadFlag(String offloadFlag) {
        this.offloadFlag = offloadFlag;
    }

	/*public double getStatedVolume() {
		return statedVolume;
	}

	public void setStatedVolume(double statedVolume) {
		this.statedVolume = statedVolume;
	}
*/
	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	/*public double getAcceptedVolume() {
		return acceptedVolume;
	}

	public void setAcceptedVolume(double acceptedVolume) {
		this.acceptedVolume = acceptedVolume;
	}*/
}
