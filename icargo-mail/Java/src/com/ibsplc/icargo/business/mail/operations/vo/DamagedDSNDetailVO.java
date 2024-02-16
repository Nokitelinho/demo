/*
 * DamagedDSNDetailVO.java Created on JUN 30, 2016
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
public class DamagedDSNDetailVO extends AbstractVO { 

    private String damageCode; 
    
    private String airportCode;
    
    private int damagedBags;
    
    //private double damagedWeight;
    private Measure damagedWeight;//added by A-7371
    
    private int totalreturnedBags;
    
    //private double totalreturnedWeight;
    private Measure totalreturnedWeight;//added by A-7371
    
    private int damageSequenceNumber;
    
    private int returnedBags;
    
    //private double returnedWeight;
    private Measure returnedWeight;//added by A-7371
    
    private LocalDate damageDate;
    
    private int latestDamagedBags;
    
    //private double latestDamagedWeight;
    private Measure latestDamagedWeight;//added by A-7371
    
    private int latestReturnedBags;
    
    //private double latestReturnedWeight;
    private Measure latestReturnedWeight;//added by A-7371
    
    private String damageUser;
    
    private String damageDescription;
    
    private String operationFlag;
    
    private String remarks;    
    
    private String returnedPaCode;
    
    private String mailClass;
    
    private String mailCategoryCode;
    
    private String mailSubclass; 
    
    private LocalDate  damageDSNLastUpdateTime;
/**
 * 
 * @return damagedWeight
 */
    public Measure getDamagedWeight() {
		return damagedWeight;
	}
/**
 * 
 * @param damagedWeight
 */
	public void setDamagedWeight(Measure damagedWeight) {
		this.damagedWeight = damagedWeight;
	}
/**
 * 
 * @return
 */
	public Measure getTotalreturnedWeight() {
		return totalreturnedWeight;
	}
/**
 * 
 * @param totalreturnedWeight
 */
	public void setTotalreturnedWeight(Measure totalreturnedWeight) {
		this.totalreturnedWeight = totalreturnedWeight;
	}

	public Measure getReturnedWeight() {
		return returnedWeight;
	}

	public void setReturnedWeight(Measure returnedWeight) {
		this.returnedWeight = returnedWeight;
	}

	public Measure getLatestDamagedWeight() {
		return latestDamagedWeight;
	}

	public void setLatestDamagedWeight(Measure latestDamagedWeight) {
		this.latestDamagedWeight = latestDamagedWeight;
	}

	public Measure getLatestReturnedWeight() {
		return latestReturnedWeight;
	}

	public void setLatestReturnedWeight(Measure latestReturnedWeight) {
		this.latestReturnedWeight = latestReturnedWeight;
	}

    public LocalDate getDamageDSNLastUpdateTime() {
		return damageDSNLastUpdateTime;
	}

	public void setDamageDSNLastUpdateTime(LocalDate damageDSNLastUpdateTime) {
		this.damageDSNLastUpdateTime = damageDSNLastUpdateTime;
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
     * @return Returns the damageCode.
     */
    public String getDamageCode() {
        return damageCode;
    }

    /**
     * @param damageCode The damageCode to set.
     */
    public void setDamageCode(String damageCode) {
        this.damageCode = damageCode;
    }

    /**
     * @return Returns the damageDate.
     */
    public LocalDate getDamageDate() {
        return damageDate;
    }

    /**
     * @param damageDate The damageDate to set.
     */
    public void setDamageDate(LocalDate damageDate) {
        this.damageDate = damageDate;
    }

    /**
     * @return Returns the damagedBags.
     */
    public int getDamagedBags() {
        return damagedBags;
    }

    /**
     * @param damagedBags The damagedBags to set.
     */
    public void setDamagedBags(int damagedBags) {
        this.damagedBags = damagedBags;
    }

    /**
     * @return Returns the damageDescription.
     */
    public String getDamageDescription() {
        return damageDescription;
    }

    /**
     * @param damageDescription The damageDescription to set.
     */
    public void setDamageDescription(String damageDescription) {
        this.damageDescription = damageDescription;
    }

    /**
     * @return Returns the damagedWeight.
     */
   /* public double getDamagedWeight() {
        return damagedWeight;
    }

    *//**
     * @param damagedWeight The damagedWeight to set.
     *//*
    public void setDamagedWeight(double damagedWeight) {
        this.damagedWeight = damagedWeight;
    }
*/
    /**
     * @return Returns the damageUser.
     */
    public String getDamageUser() {
        return damageUser;
    }

    /**
     * @param damageUser The damageUser to set.
     */
    public void setDamageUser(String damageUser) {
        this.damageUser = damageUser;
    }

    /**
     * @return Returns the returnedBags.
     */
    public int getReturnedBags() {
        return returnedBags;
    }

    /**
     * @param returnedBags The returnedBags to set.
     */
    public void setReturnedBags(int returnedBags) {
        this.returnedBags = returnedBags;
    }

    /**
     * @return Returns the returnedWeight.
     */
   /* public double getReturnedWeight() {
        return returnedWeight;
    }

    *//**
     * @param returnedWeight The returnedWeight to set.
     *//*
    public void setReturnedWeight(double returnedWeight) {
        this.returnedWeight = returnedWeight;
    }*/

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
     * @return Returns the returnedPaCode.
     */
    public String getReturnedPaCode() {
        return returnedPaCode;
    }

    /**
     * @param returnedPaCode The returnedPaCode to set.
     */
    public void setReturnedPaCode(String returnedPaCode) {
        this.returnedPaCode = returnedPaCode;
    }

	/**
	 * @return Returns the airportCode.
	 */
	public String getAirportCode() {
		return airportCode;
	}

	/**
	 * @param airportCode The airportCode to set.
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

    /**
     * @return Returns the latestDamagedBags.
     */
    public int getLatestDamagedBags() {
        return latestDamagedBags;
    }

    /**
     * @param latestDamagedBags The latestDamagedBags to set.
     */
    public void setLatestDamagedBags(int latestDamagedBags) {
        this.latestDamagedBags = latestDamagedBags;
    }

    /**
     * @return Returns the latestDamagedWeight.
     */
   /* public double getLatestDamagedWeight() {
        return latestDamagedWeight;
    }

    *//**
     * @param latestDamagedWeight The latestDamagedWeight to set.
     *//*
    public void setLatestDamagedWeight(double latestDamagedWeight) {
        this.latestDamagedWeight = latestDamagedWeight;
    }

    *//**
     * @return Returns the latestDeturnedWeight.
     *//*
    public double getLatestReturnedWeight() {
        return latestReturnedWeight;
    }

    *//**
     * 
     * @param latestReturnedWeight Sets the LatestReturnedWeight
     *//*
    public void setLatestReturnedWeight(double latestReturnedWeight) {
        this.latestReturnedWeight = latestReturnedWeight;
    }
*/
    /**
     * @return Returns the latestReturnedBags.
     */
    public int getLatestReturnedBags() {
        return latestReturnedBags;
    }

    /**
     * @param latestReturnedBags The latestReturnedBags to set.
     */
    public void setLatestReturnedBags(int latestReturnedBags) {
        this.latestReturnedBags = latestReturnedBags;
    }

	/**
	 * @return Returns the totalreturnedBags.
	 */
	public int getTotalreturnedBags() {
		return totalreturnedBags;
	}

	/**
	 * @param totalreturnedBags The totalreturnedBags to set.
	 */
	public void setTotalreturnedBags(int totalreturnedBags) {
		this.totalreturnedBags = totalreturnedBags;
	}

	/**
	 * @return Returns the totalreturnedWeight.
	 */
	/*public double getTotalreturnedWeight() {
		return totalreturnedWeight;
	}

	*//**
	 * @param totalreturnedWeight The totalreturnedWeight to set.
	 *//*
	public void setTotalreturnedWeight(double totalreturnedWeight) {
		this.totalreturnedWeight = totalreturnedWeight;
	}
*/
	/**
	 * @return Returns the damageSequenceNumber.
	 */
	public int getDamageSequenceNumber() {
		return damageSequenceNumber;
	}

	/**
	 * @param damageSequenceNumber The damageSequenceNumber to set.
	 */
	public void setDamageSequenceNumber(int damageSequenceNumber) {
		this.damageSequenceNumber = damageSequenceNumber;
	}
}
