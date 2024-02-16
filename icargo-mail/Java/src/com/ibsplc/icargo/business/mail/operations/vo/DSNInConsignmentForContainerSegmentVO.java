/*
 * DSNInConsignmentForContainerSegmentVO.java Created on JUN 30, 2016
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
public class DSNInConsignmentForContainerSegmentVO extends AbstractVO {

    private String consignmentNumber;
    
    private LocalDate consignmentDate;    

    private int acceptedBags;
    
    //private double acceptedWeight;
    private Measure acceptedWeight;//added by A-7371
    
    private int statedBags;
    
    //private double statedWeight;
    private Measure statedWeight;//added by A-7371
    
    private String acceptedUser;
    
    private String mailCategoryCode;
    
    private LocalDate acceptedDate; 
    
    private LocalDate receivedDate;
    
    private String paCode;
    
    private int sequenceNumber; 
    
    
    private int receivedBags;
   // private double receivedWeight; 
    private Measure receivedWeight;//added by A-7371
    
    private int deliveredBags;
    
    //private double deliveredWeight; 
    private Measure deliveredWeight; //added by A-7371
    
    private String mailClass; 
    
    private String mailSubclass;
    
    private double statedVolume;
    
    private double acceptedVolume;
    
    
    /*
	 * Added By Karthick V to include the status that could be used  in importing the 
	 * data from the mailoperations to be used in MRA
	 * 
	 * 
	 */
	private String mraStatus;
	
	private String remarks;

	/**
	 * @return Returns the mraStatus.
	 */
	/**
	 * 
	 * @return
	 */
	public String getMraStatus() {
		return mraStatus;
	}
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
	 * @param mraStatus The mraStatus to set.
	 */
	public void setMraStatus(String mraStatus) {
		this.mraStatus = mraStatus;
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
	}
*/
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
    /*public double getAcceptedWeight() {
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
     */
    /*public double getStatedWeight() {
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

	public double getStatedVolume() {
		return statedVolume;
	}

	public void setStatedVolume(double statedVolume) {
		this.statedVolume = statedVolume;
	}

	public double getAcceptedVolume() {
		return acceptedVolume;
	}

	public void setAcceptedVolume(double acceptedVolume) {
		this.acceptedVolume = acceptedVolume;
	}

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
}
