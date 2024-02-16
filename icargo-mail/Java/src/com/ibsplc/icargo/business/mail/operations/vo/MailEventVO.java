/*
 * MailEventVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-3109
 *
 */
public class MailEventVO extends AbstractVO {
    private String companyCode;
    private String mailboxId;
    private String paCode;
    private String mailCategory;
    private String mailClass;
    private boolean isReceived;
    private boolean isUplifted;
    private boolean isAssigned;
    private boolean isPending;
    //Added by A-5201 for ICRD-85233,ICRD-79018,ICRD-80366 starts
    private boolean isReadyForDelivery;
    private boolean isTransportationCompleted;
    private boolean isArrived;
    //Added by A-5201 for ICRD-85233,ICRD-79018,ICRD-80366 end
    private boolean isHandedOver;
    private boolean isReturned;
    private boolean isDelivered;
    private String operationFlag;
    private boolean isLoadedResditFlag;
	private boolean isOnlineHandedOverResditFlag;
	private boolean isHandedOverReceivedResditFlag;
	private boolean isLostFlag;
	private boolean isFoundFlag;
	

    /**
     * @return operationFlag
     */
    public String getOperationFlag() {
		return operationFlag;
	}
	/**
	 * @param operationFlag
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
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
     * @return Returns the isAssigned.
     */
    public boolean isAssigned() {
        return isAssigned;
    }
    /**
     * @param isAssigned The isAssigned to set.
     */
    public void setAssigned(boolean isAssigned) {
        this.isAssigned = isAssigned;
    }
    /**
     * @return Returns the isHandedOver.
     */
    public boolean isHandedOver() {
        return isHandedOver;
    }
    /**
     * @param isHandedOver The isHandedOver to set.
     */
    public void setHandedOver(boolean isHandedOver) {
        this.isHandedOver = isHandedOver;
    }
    /**
     * @return Returns the isPending.
     */
    public boolean isPending() {
        return isPending;
    }
    /**
     * @param isPending The isPending to set.
     */
    public void setPending(boolean isPending) {
        this.isPending = isPending;
    }
    /**
	 * 	Getter for isReadyForDelivery 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public boolean isReadyForDelivery() {
		return isReadyForDelivery;
	}
	/**
	 *  @param isReadyForDelivery the isReadyForDelivery to set
	 * 	Setter for isReadyForDelivery 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public void setReadyForDelivery(boolean isReadyForDelivery) {
		this.isReadyForDelivery = isReadyForDelivery;
	}
	/**
	 * 	Getter for isTransportationCompleted 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public boolean isTransportationCompleted() {
		return isTransportationCompleted;
	}
	/**
	 *  @param isTransportationCompleted the isTransportationCompleted to set
	 * 	Setter for isTransportationCompleted 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public void setTransportationCompleted(boolean isTransportationCompleted) {
		this.isTransportationCompleted = isTransportationCompleted;
	}
	/**
	 * 	Getter for isArrived 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public boolean isArrived() {
		return isArrived;
	}
	/**
	 *  @param isArrived the isArrived to set
	 * 	Setter for isArrived 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public void setArrived(boolean isArrived) {
		this.isArrived = isArrived;
	}
	/**
     * @return Returns the isReceived.
     */
    public boolean isReceived() {
        return isReceived;
    }
    /**
     * @param isReceived The isReceived to set.
     */
    public void setReceived(boolean isReceived) {
        this.isReceived = isReceived;
    }
    /**
     * @return Returns the isReturned.
     */
    public boolean isReturned() {
        return isReturned;
    }
    /**
     * @param isReturned The isReturned to set.
     */
    public void setReturned(boolean isReturned) {
        this.isReturned = isReturned;
    }
    /**
     * @return Returns the isUplifted.
     */
    public boolean isUplifted() {
        return isUplifted;
    }
    /**
     * @param isUplifted The isUplifted to set.
     */
    public void setUplifted(boolean isUplifted) {
        this.isUplifted = isUplifted;
    }
    /**
     * @return Returns the mailCategory.
     */
    public String getMailCategory() {
        return mailCategory;
    }
    /**
     * @param mailCategory The mailCategory to set.
     */
    public void setMailCategory(String mailCategory) {
        this.mailCategory = mailCategory;
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
     * @return Returns the paCode.
     */
    public String getMailboxId() {
        return mailboxId;
    }
    /**
     * @param paCode The paCode to set.
     */
    public void setMailboxId(String mailboxId) {
        this.mailboxId = mailboxId;
    }
	/**
	 * @return Returns the isDelivered.
	 */
	public boolean isDelivered() {
		return this.isDelivered;
	}
	/**
	 * @param isDelivered The isDelivered to set.
	 */
	public void setDelivered(boolean isDelivered) {
		this.isDelivered = isDelivered;
	}
	/**
	 * @return Returns the isHandedOverReceivedResditFlag.
	 */
	public boolean isHandedOverReceivedResditFlag() {
		return isHandedOverReceivedResditFlag;
	}
	/**
	 * @param isHandedOverReceivedResditFlag The isHandedOverReceivedResditFlag to set.
	 */
	public void setHandedOverReceivedResditFlag(
			boolean isHandedOverReceivedResditFlag) {
		this.isHandedOverReceivedResditFlag = isHandedOverReceivedResditFlag;
	}
	/**
	 * @return Returns the isLoadedResditFlag.
	 */
	public boolean isLoadedResditFlag() {
		return isLoadedResditFlag;
	}
	/**
	 * @param isLoadedResditFlag The isLoadedResditFlag to set.
	 */
	public void setLoadedResditFlag(boolean isLoadedResditFlag) {
		this.isLoadedResditFlag = isLoadedResditFlag;
	}
	/**
	 * @return Returns the isOnlineHandedOverResditFlag.
	 */
	public boolean isOnlineHandedOverResditFlag() {
		return isOnlineHandedOverResditFlag;
	}
	/**
	 * @param isOnlineHandedOverResditFlag The isOnlineHandedOverResditFlag to set.
	 */
	public void setOnlineHandedOverResditFlag(boolean isOnlineHandedOverResditFlag) {
		this.isOnlineHandedOverResditFlag = isOnlineHandedOverResditFlag;
	
	}
	public String getPaCode() {
		return paCode;
	}
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	public boolean isLostFlag() {
		return isLostFlag;
	}
	public void setLostFlag(boolean isLostFlag) {
		this.isLostFlag = isLostFlag;
	}
	public boolean isFoundFlag() {
		return isFoundFlag;
	}
	public void setFoundFlag(boolean isFoundFlag) {
		this.isFoundFlag = isFoundFlag;
	}
	
	
}
