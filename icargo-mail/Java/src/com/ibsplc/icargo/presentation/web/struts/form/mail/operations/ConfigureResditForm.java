/*
 * ConfigureResditForm.java Created on Jul 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2001
 *
 */
public class ConfigureResditForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.configureresdit";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "configureresditResources";

	
	private String airline ;
    private String reserved;
    private String assigned;
    private String uplifted;
    private String handedoverRecieved;
    private String handedover;
    private String departed;
    private String delivered;
    private String readyForDelivery;	
	private String transportCompleted;	
	private String mailArrived;
    private String returned;
    
    private String[] receivedResditFlag;
    
	private String[] assignedResditFlag;
    
	private String[] upliftedResditFlag;
	
	private String[] handedOverResditFlag;
	
	private String[] handedOverReceivedResditFlag;
    
	private String[] departedResditFlag;
    
	private String[] deliveredResditFlag;
	private String[] readyForDeliveryFlag;
	private String[] transportationCompletedResditFlag;
	private String[] arrivedResditFlag;
    
	private String[] returnedResditFlag;
    
	
	

	/**
	 * @return Returns the airline.
	 */
	public String getAirline() {
		return airline;
	}

	/**
	 * @param airline The airline to set.
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}
	

	/**
     * @return SCREEN_ID - String
     */
    public String getScreenId() {
        return SCREEN_ID;
    }

    /**
     * @return PRODUCT_NAME - String
     */
    public String getProduct() {
        return PRODUCT_NAME;
    }

    /**
     * @return SUBPRODUCT_NAME - String
     */
    public String getSubProduct() {
        return SUBPRODUCT_NAME;
    }

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @return Returns the assignedResditFlag.
	 */
	public String[] getAssignedResditFlag() {
		return assignedResditFlag;
	}

	/**
	 * @param assignedResditFlag The assignedResditFlag to set.
	 */
	public void setAssignedResditFlag(String[] assignedResditFlag) {
		this.assignedResditFlag = assignedResditFlag;
	}

	/**
	 * @return Returns the departedResditFlag.
	 */
	public String[] getDepartedResditFlag() {
		return departedResditFlag;
	}

	/**
	 * @param departedResditFlag The departedResditFlag to set.
	 */
	public void setDepartedResditFlag(String[] departedResditFlag) {
		this.departedResditFlag = departedResditFlag;
	}

	/**
	 * @return Returns the handedOverResditFlag.
	 */
	public String[] getHandedOverResditFlag() {
		return handedOverResditFlag;
	}

	/**
	 * @param handedOverResditFlag The handedOverResditFlag to set.
	 */
	public void setHandedOverResditFlag(String[] handedOverResditFlag) {
		this.handedOverResditFlag = handedOverResditFlag;
	}

	/**
	 * @return Returns the receivedResditFlag.
	 */
	public String[] getReceivedResditFlag() {
		return receivedResditFlag;
	}

	/**
	 * @param receivedResditFlag The receivedResditFlag to set.
	 */
	public void setReceivedResditFlag(String[] receivedResditFlag) {
		this.receivedResditFlag = receivedResditFlag;
	}

	/**
	 * @return Returns the upliftedResditFlag.
	 */
	public String[] getUpliftedResditFlag() {
		return upliftedResditFlag;
	}

	/**
	 * @param upliftedResditFlag The upliftedResditFlag to set.
	 */
	public void setUpliftedResditFlag(String[] upliftedResditFlag) {
		this.upliftedResditFlag = upliftedResditFlag;
	}

	/**
	 * @param assigned The assigned to set.
	 */
	public void setAssigned(String assigned) {
		this.assigned = assigned;
	}

	/**
	 * @param departed The departed to set.
	 */
	public void setDeparted(String departed) {
		this.departed = departed;
	}	

	/**
	 * @param handedoverRecieved The handedoverRecieved to set.
	 */
	public void setHandedoverRecieved(String handedoverRecieved) {
		this.handedoverRecieved = handedoverRecieved;
	}

	/**
	 * @param reserved The reserved to set.
	 */
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	/**
	 * @param uplifted The uplifted to set.
	 */
	public void setUplifted(String uplifted) {
		this.uplifted = uplifted;
	}

	/**
	 * @return Returns the assigned.
	 */
	public String getAssigned() {
		return assigned;
	}

	/**
	 * @return Returns the departed.
	 */
	public String getDeparted() {
		return departed;
	}
	

	/**
	 * @return Returns the handedoverRecieved.
	 */
	public String getHandedoverRecieved() {
		return handedoverRecieved;
	}

	/**
	 * @return Returns the reserved.
	 */
	public String getReserved() {
		return reserved;
	}

	/**
	 * @return Returns the uplifted.
	 */
	public String getUplifted() {
		return uplifted;
	}

	/**
	 * @return Returns the handedover.
	 */
	public String getHandedover() {
		return handedover;
	}

	/**
	 * @param handedover The handedover to set.
	 */
	public void setHandedover(String handedover) {
		this.handedover = handedover;
	}

	/**
	 * @return Returns the handedOverReceivedResditFlag.
	 */
	public String[] getHandedOverReceivedResditFlag() {
		return handedOverReceivedResditFlag;
	}

	/**
	 * @param handedOverReceivedResditFlag The handedOverReceivedResditFlag to set.
	 */
	public void setHandedOverReceivedResditFlag(
			String[] handedOverReceivedResditFlag) {
		this.handedOverReceivedResditFlag = handedOverReceivedResditFlag;
	}

	/**
	 * @return the delivered
	 */
	public String getDelivered() {
		return delivered;
	}

	/**
	 * @param delivered the delivered to set
	 */
	public void setDelivered(String delivered) {
		this.delivered = delivered;
	}

	/**
	 * 	Getter for readyForDelivery 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public String getReadyForDelivery() {
		return readyForDelivery;
	}
	/**
	 *  @param readyForDelivery the readyForDelivery to set
	 * 	Setter for readyForDelivery 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public void setReadyForDelivery(String readyForDelivery) {
		this.readyForDelivery = readyForDelivery;
	}
	/**
	 * 	Getter for transportCompleted 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public String getTransportCompleted() {
		return transportCompleted;
	}
	/**
	 *  @param transportCompleted the transportCompleted to set
	 * 	Setter for transportCompleted 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public void setTransportCompleted(String transportCompleted) {
		this.transportCompleted = transportCompleted;
	}
	/**
	 * 	Getter for mailArrived 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public String getMailArrived() {
		return mailArrived;
	}
	/**
	 *  @param mailArrived the mailArrived to set
	 * 	Setter for mailArrived 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public void setMailArrived(String mailArrived) {
		this.mailArrived = mailArrived;
	}
	/**
	 * @return the returned
	 */
	public String getReturned() {
		return returned;
	}

	/**
	 * @param returned the returned to set
	 */
	public void setReturned(String returned) {
		this.returned = returned;
	}

	/**
	 * @return the deliveredResditFlag
	 */
	public String[] getDeliveredResditFlag() {
		return deliveredResditFlag;
	}

	/**
	 * @param deliveredResditFlag the deliveredResditFlag to set
	 */
	public void setDeliveredResditFlag(String[] deliveredResditFlag) {
		this.deliveredResditFlag = deliveredResditFlag;
	}	
	/**
	 * 	Getter for arrivedResditFlag 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public String[] getArrivedResditFlag() {
		return arrivedResditFlag;
	}
	/**
	 *  @param arrivedResditFlag the arrivedResditFlag to set
	 * 	Setter for arrivedResditFlag 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public void setArrivedResditFlag(String[] arrivedResditFlag) {
		this.arrivedResditFlag = arrivedResditFlag;
	}	
	/**
	 * 	Getter for readyForDeliveryFlag 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public String[] getReadyForDeliveryFlag() {
		return readyForDeliveryFlag;
	}
	/**
	 *  @param readyForDeliveryFlag the readyForDeliveryFlag to set
	 * 	Setter for readyForDeliveryFlag 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public void setReadyForDeliveryFlag(String[] readyForDeliveryFlag) {
		this.readyForDeliveryFlag = readyForDeliveryFlag;
	}
	/**
	 * 	Getter for transportationCompletedResditFlag 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public String[] getTransportationCompletedResditFlag() {
		return transportationCompletedResditFlag;
	}
	/**
	 *  @param transportationCompletedResditFlag the transportationCompletedResditFlag to set
	 * 	Setter for transportationCompletedResditFlag 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public void setTransportationCompletedResditFlag(
			String[] transportationCompletedResditFlag) {
		this.transportationCompletedResditFlag = transportationCompletedResditFlag;
	}

	/**
	 * @return the returnedResditFlag
	 */
	public String[] getReturnedResditFlag() {
		return returnedResditFlag;
	}

	/**
	 * @param returnedResditFlag the returnedResditFlag to set
	 */
	public void setReturnedResditFlag(String[] returnedResditFlag) {
		this.returnedResditFlag = returnedResditFlag;
	}

}
