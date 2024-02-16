/*
 * MailbagInULDForSegmentVO.java Created on JUN 30, 2016
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
public class MailbagInULDForSegmentVO extends AbstractVO {
	
    private String mailId;
        /*
     * ULDNumber for ULD and barrow number for Bulk
     */
    private String containerNumber;
    private String damageFlag;
    private LocalDate  scannedDate;       
   // private double weight;
    private Measure weight;//added by A-7371
    private String mailClass;   
    private String mailSubclass;   
    private String mailCategoryCode;
    private String transferFromCarrier;
    private String sealNumber;
    private String acceptanceFlag;    
    private String arrivalFlag; 
    private String deliveredStatus;    
    private String transferFlag;   
    private String scannedPort;   
    private String ubrNumber;   
    private String controlDocumentNumber;   
    private String transferToCarrier;    
    private LocalDate lastUpdateTime;    
    private String lastUpdateUser;   
    private String arrivalSealNumber; 
    private long mailSequenceNumber;
    private String recievedFlag;
    private String deliveredFlag;
   // private double deliveredWeight;
    private Measure deliveredWeight;
    
    private LocalDate ghttim;
    private String flightNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private int carrierId;
	private String companyCode;
	private String assignedPort;
	private String containerType;
	private int legSerialNumber;
	private String paBuiltFlag;
	
	private String isFromTruck; 
	
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
	 * @return the deliveredWeight
	 */
	/*public double getDeliveredWeight() {
		return deliveredWeight;
	}
	*//**
	 * @param deliveredWeight the deliveredWeight to set
	 *//*
	public void setDeliveredWeight(double deliveredWeight) {
		this.deliveredWeight = deliveredWeight;
	}*/
	/**
	 * @return the recievedFlag
	 */
	public String getRecievedFlag() {
		return recievedFlag;
	}
	/**
	 * @param recievedFlag the recievedFlag to set
	 */
	public void setRecievedFlag(String recievedFlag) {
		this.recievedFlag = recievedFlag;
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
    /*
	 * Added By Karthick V to include the status that could be used  in importing the 
	 * data from the mailoperations to be used in MRA
	 * 
	 * 
	 */
	private String mraStatus;

	/**
	 * @return Returns the mraStatus.
	 */
	public String getMraStatus() {
		return mraStatus;
	}

	/**
	 * @param mraStatus The mraStatus to set.
	 */
	public void setMraStatus(String mraStatus) {
		this.mraStatus = mraStatus;
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
	 * @return Returns the deliveredStatus.
	 */
	public String getDeliveredStatus() {
		return deliveredStatus;
	}
	/**
	 * @param deliveredStatus The deliveredStatus to set.
	 */
	public void setDeliveredStatus(String deliveredStatus) {
		this.deliveredStatus = deliveredStatus;
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
     * @return Returns the scannedPort.
     */
    public String getScannedPort() {
        return scannedPort;
    }
    /**
     * @param scannedPort The scannedPort to set.
     */
    public void setScannedPort(String scannedPort) {
        this.scannedPort = scannedPort;
    }
    /**
     * @return Returns the acceptanceFlag.
     */
    public String getAcceptanceFlag() {
        return acceptanceFlag;
    }
    /**
     * @param acceptanceFlag The acceptanceFlag to set.
     */
    public void setAcceptanceFlag(String acceptanceFlag) {
        this.acceptanceFlag = acceptanceFlag;
    }
    /**
     * @return Returns the arrivalFlag.
     */
    public String getArrivalFlag() {
        return arrivalFlag;
    }
    /**
     * @param arrivalFlag The arrivalFlag to set.
     */
    public void setArrivalFlag(String arrivalFlag) {
        this.arrivalFlag = arrivalFlag;
    }
	/**
	 * @return Returns the transferFlag.
	 */
	public String getTransferFlag() {
		return transferFlag;
	}
	/**
	 * @param transferFlag The transferFlag to set.
	 */
	public void setTransferFlag(String transferFlag) {
		this.transferFlag = transferFlag;
	}
	/**
	 * @return Returns the controlDocumentNumber.
	 */
	public String getControlDocumentNumber() {
		return controlDocumentNumber;
	}
	/**
	 * @param controlDocumentNumber The controlDocumentNumber to set.
	 */
	public void setControlDocumentNumber(String controlDocumentNumber) {
		this.controlDocumentNumber = controlDocumentNumber;
	}
	/**
	 * @return Returns the ubrNumber.
	 */
	public String getUbrNumber() {
		return ubrNumber;
	}
	/**
	 * @param ubrNumber The ubrNumber to set.
	 */
	public void setUbrNumber(String ubrNumber) {
		this.ubrNumber = ubrNumber;
	}
	/**
	 * @return Returns the transferFromCarrier.
	 */
	public String getTransferFromCarrier() {
		return transferFromCarrier;
	}
	/**
	 * @param transferFromCarrier The transferFromCarrier to set.
	 */
	public void setTransferFromCarrier(String transferFromCarrier) {
		this.transferFromCarrier = transferFromCarrier;
	}
	/**
	 * @return Returns the transferToCarrier.
	 */
	public String getTransferToCarrier() {
		return transferToCarrier;
	}
	/**
	 * @param transferToCarrier The transferToCarrier to set.
	 */
	public void setTransferToCarrier(String transferToCarrier) {
		this.transferToCarrier = transferToCarrier;
	}

	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
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
	 * @return the arrivalSealNumber
	 */
	public String getArrivalSealNumber() {
		return arrivalSealNumber;
	}

	/**
	 * @param arrivalSealNumber the arrivalSealNumber to set
	 */
	public void setArrivalSealNumber(String arrivalSealNumber) {
		this.arrivalSealNumber = arrivalSealNumber;
	}

	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}

	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	public LocalDate getGhttim() {
		return ghttim;
	}
	public void setGhttim(LocalDate ghttim) {
		this.ghttim = ghttim;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}
	public int getCarrierId() {
		return carrierId;
	}
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * 	Getter for assignedPort 
	 *	Added by : A-8061 on 21-Apr-2020
	 * 	Used for :
	 */
	public String getAssignedPort() {
		return assignedPort;
	}
	/**
	 *  @param assignedPort the assignedPort to set
	 * 	Setter for assignedPort 
	 *	Added by : A-8061 on 21-Apr-2020
	 * 	Used for :
	 */
	public void setAssignedPort(String assignedPort) {
		this.assignedPort = assignedPort;
	}
	/**
	 * 	Getter for containerType 
	 *	Added by : A-8061 on 21-Apr-2020
	 * 	Used for :
	 */
	public String getContainerType() {
		return containerType;
	}
	/**
	 *  @param containerType the containerType to set
	 * 	Setter for containerType 
	 *	Added by : A-8061 on 21-Apr-2020
	 * 	Used for :
	 */
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}
	public int getLegSerialNumber() {
		return legSerialNumber;
	}
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}
	/**
	 * 	Getter for paBuiltFlag 
	 *	Added by : A-8061 on 14-Sep-2020
	 * 	Used for :
	 */
	public String getPaBuiltFlag() {
		return paBuiltFlag;
	}
	/**
	 *  @param paBuiltFlag the paBuiltFlag to set
	 * 	Setter for paBuiltFlag 
	 *	Added by : A-8061 on 14-Sep-2020
	 * 	Used for :
	 */
	public void setPaBuiltFlag(String paBuiltFlag) {
		this.paBuiltFlag = paBuiltFlag;
	}
	public String getIsFromTruck() {
		return isFromTruck;
	}
	public void setIsFromTruck(String isFromTruck) {
		this.isFromTruck = isFromTruck;
	}
	
	

}
