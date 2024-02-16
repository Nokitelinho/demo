/*
 * ContainerDetailsVO.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;


import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-5991
 * This Vo is returned to the client in MailAcceptance
 */
public class ContainerDetailsVO extends AbstractVO {
	private String companyCode;
    private String containerNumber;
    private String pou;
    private String pol;
    private LocalDate assignmentDate;
    private String route;
    private int totalBags;
    //private double totalWeight;
    private Measure totalWeight;//added by A-7317
    private String wareHouse;
    private String location;    
    private String remarks;
    private String undoArrivalFlag;
    private int carrierId;
    private String flightNumber;
    private long flightSequenceNumber;
    private int segmentSerialNumber;
    private String containerType; 
    private Collection<DSNVO> dsnVOs;
    private Collection<MailbagVO> mailDetails;
    private Collection<MailbagVO> deletedMailDetails;
 // Added as part of CRQ ICRD-118163 by A-5526 
   
    private Collection<DespatchDetailsVO> desptachDetailsVOs;
    private Collection<OnwardRouteForSegmentVO> onwardRoutingForSegmentVOs;
    private Collection<MailSummaryVO> mailSummaryVOs;
    
    private LocalDate flightDate; 
    private String carrierCode;
    
    private String destination;
    private String paBuiltFlag;
    private String acceptedFlag; 
    
    private String offloadFlag;
    
    private String operationFlag;
    
    private int receivedBags;
    
    //private double receivedWeight;
    private Measure receivedWeight;//added by A-7371
    
    private int deliveredBags;
    private Measure deliveredWeight;
   private Money  provosionalCharge;
   private String baseCurrency;
   private String rateAvailforallMailbags;
   private String actWgtSta;


public Money getProvosionalCharge() {
	return provosionalCharge;
}
public void setProvosionalCharge(Money provosionalCharge) {
	this.provosionalCharge = provosionalCharge;
}
public String getBaseCurrency() {
	return baseCurrency;
}
public void setBaseCurrency(String baseCurrency) {
	this.baseCurrency = baseCurrency;
}
public String getRateAvailforallMailbags() {
	return rateAvailforallMailbags;
}
public void setRateAvailforallMailbags(String rateAvailforallMailbags) {
	this.rateAvailforallMailbags = rateAvailforallMailbags;
}
    public int getDeliveredBags() {
		return deliveredBags;
	}
	public void setDeliveredBags(int deliveredBags) {
		this.deliveredBags = deliveredBags;
	}
	public Measure getDeliveredWeight() {
		return deliveredWeight;
	}
	public void setDeliveredWeight(Measure deliveredWeight) {
		this.deliveredWeight = deliveredWeight;
	}
    private String ownAirlineCode;
    
    private String arrivedStatus;
    
    private int legSerialNumber;
    
    private String transferFlag;
    
    private String containerOperationFlag;
    
    private boolean isReassignFlag;
    
    private boolean isMailUpdateFlag;
    
    public boolean isMailUpdateFlag() {
		return isMailUpdateFlag;
	}
	public void setMailUpdateFlag(boolean isMailUpdateFlag) {
		this.isMailUpdateFlag = isMailUpdateFlag;
	}
    private String transferFromCarrier;
    
    private String assignedUser;
    
    private boolean isPreassignFlag;
    
    private LocalDate lastUpdateTime;
    
    private LocalDate uldLastUpdateTime;
    
    /**
     * ADDED BY RENO K ABRAHAM FOR SB
     */
    private String flagPAULDResidit;
    private String deliveredStatus;
    
    private String  onwardFlights;
    
    private String deliverPABuiltContainer;
   
    private String fromScreen;
    /**
     * ADDED BY RENO K ABRAHAM FOR INTACT
     */
    private String intact;
    
    //Added by paulson for Inbound acceptance change - mtk552
    private String transactionCode;
    /**
     * AirNZ - 985
     */
    private String bellyCartId;
    private String containerJnyId;
    
    /**
     * paCode - Contains the PA Code(Shipper Code),
     * who build the SB ULD.
     */
    private String paCode;
    //
    private Page<MailDetailVO> mailDetailPageVOs;
    /**
     * Transit Flag
     */
    private String transitFlag;

    /**
     * ULD RELEASE FLAG
     * This flag is used to determine whether 
     * the uld is released from a segment.
     * 
     * ULD will be released at the time of 
     * Inbound Flight Closure.
     */
    private String releasedFlag;
    private LocalDate scanDate;
    
    private String flightStatus;
    
    //private String tareWeight;
    private Measure tareWeight;//added by A-7371
    private boolean mailModifyflag;
    private boolean oflToRsnFlag;
    
    
    private String contour;//A-8061 ICRD-318033
    private String airportCode;
    
    
//Added by A-8176 for mail outbound
	 private String containerGroup;
    private int containercount;
    private int mailbagcount;
    private Measure mailbagwt;
    private Measure receptacleWeight;
    private int receptacleCount;
    private LocalDate assignedDate;
    private String assignedPort;
    private String contentId;//Added for ICRD-239331
    private String containerPureTransfer;//Added by A-8464 for ICRD-328502
    private Measure actualWeight;//added by A-8353 for IASCB-27572
    private boolean foundTransfer;//Added by A-8164 for IASCB-34167 starts
    private LocalDate minReqDelveryTime; 
	
    private int totalRecordSize;
    
    private MailbagEnquiryFilterVO additionalFilters;
    
    private Collection<String> offloadedInfo;   
    private int offloadCount;
    private boolean aquitULDFlag;
    private String activeTab;
    private boolean fromContainerTab;
    private String uldFulIndFlag;
    private long uldReferenceNo;
    private String fromDetachAWB;
    private String transistPort;
	public String getFromDetachAWB() {
		return fromDetachAWB;
	}
	public void setFromDetachAWB(String fromDetachAWB) {
		this.fromDetachAWB = fromDetachAWB;
	}
	/**
     * 
     * @return totalWeight
     */
	public Measure getTotalWeight() {
		return totalWeight;
	}
	/**
	 * 
	 * @param totalWeight
	 */
	public void setTotalWeight(Measure totalWeight) {
		this.totalWeight = totalWeight;
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
	 * @return tareWeight
	 */
	public Measure getTareWeight() {
		return tareWeight;
	}
	/**
	 * 
	 * @param tareWeight
	 */
	public void setTareWeight(Measure tareWeight) {
		this.tareWeight = tareWeight;
	}
	/**
	 * @return Returns the mailDetailPageVOs.
	 */
	public Page<MailDetailVO> getMailDetailPageVOs() {
		return mailDetailPageVOs;
	}
	/**
	 * @param mailDetailPageVOs The mailDetailPageVOs to set.
	 */
	public void setMailDetailPageVOs(Page<MailDetailVO> mailDetailPageVOs) {
		this.mailDetailPageVOs = mailDetailPageVOs;
	}
	/**
	 * @return acceptedFlag
	 */
	public String getAcceptedFlag() {
		return acceptedFlag;
	}
	/**
	 * @param acceptedFlag
	 */
	public void setAcceptedFlag(String acceptedFlag) {
		this.acceptedFlag = acceptedFlag;
	}
	/**
	 * @return destination
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * @return paBuiltFlag
	 */
	public String getPaBuiltFlag() {
		return paBuiltFlag;
	}
	/**
	 * @param paBuiltFlag
	 */
	public void setPaBuiltFlag(String paBuiltFlag) {
		this.paBuiltFlag = paBuiltFlag;
	}
	/**
	 * @return assignmentDate
	 */
	public LocalDate getAssignmentDate() {
		return assignmentDate;
	}
	/**
	 * @param assignmentDate
	 */
	public void setAssignmentDate(LocalDate assignmentDate) {
		this.assignmentDate = assignmentDate;
	}
	/**
	 * @return carrierId
	 */
	public int getCarrierId() {
		return carrierId;
	}
	/**
	 * @param carrierId
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}
	/**
	 * @return containerNumber
	 */
	public String getContainerNumber() {
		return containerNumber;
	}
	/**
	 * @param containerNumber
	 */
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	/**
	 * @return containerType
	 */
	public String getContainerType() {
		return containerType;
	}
	/**
	 * @param containerType
	 */
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}
	
	/**
	 * @return flightNumber
	 */
	public String getFlightNumber() {
		return flightNumber; 
	}
	/**
	 * @param flightNumber
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	/**
	 * @return flightSequenceNumber
	 */
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}
	/**
	 * @param flightSequenceNumber
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}
	/**
	 * @return location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return mailDetails
	 */
	public Collection<MailbagVO> getMailDetails() {
		return mailDetails;
	}
	/**
	 * @param mailDetails
	 */
	public void setMailDetails(Collection<MailbagVO> mailDetails) {
		this.mailDetails = mailDetails;
	}
	/**
	 * @return pol
	 */
	public String getPol() {
		return pol;
	}
	/**
	 * @param pol
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}
	/**
	 * @return pou
	 */
	public String getPou() {
		return pou;
	}
	/**
	 * @param pou
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}
	/**
	 * @return remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return route
	 */
	public String getRoute() {
		return route;
	}
	/**
	 * @param route
	 */
	public void setRoute(String route) {
		this.route = route;
	}
	/**
	 * @return segmentSerialNumber
	 */
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}
	/**
	 * @param segmentSerialNumber
	 */
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}
	/**
	 * @return totalBags
	 */
	public int getTotalBags() {
		return totalBags;
	}
	/**
	 * @param totalBags
	 */
	public void setTotalBags(int totalBags) {
		this.totalBags = totalBags;
	}
	/**
	 * @return totalWeight
	 */
	/*public double getTotalWeight() {
		return totalWeight;
	}
	*//**
	 * @param totalWeight
	 *//*
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}*/
	/**
	 * @return wareHouse
	 */
	public String getWareHouse() {
		return wareHouse;
	}
	/**
	 * @param wareHouse
	 */
	public void setWareHouse(String wareHouse) {
		this.wareHouse = wareHouse;
	}
	/**
	 * @return companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return dsnVOs
	 */
	public Collection<DSNVO> getDsnVOs() {
		return dsnVOs;
	}
	/**
	 * @param dsnVOs
	 */
	public void setDsnVOs(Collection<DSNVO> dsnVOs) {
		this.dsnVOs = dsnVOs;
	}
	/**
	 * @return desptachDetailsVOs
	 */
	public Collection<DespatchDetailsVO> getDesptachDetailsVOs() {
		return desptachDetailsVOs;
	}
	/**
	 * @param desptachDetailsVOs
	 */
	public void setDesptachDetailsVOs(
			Collection<DespatchDetailsVO> desptachDetailsVOs) {
		this.desptachDetailsVOs = desptachDetailsVOs;
	}
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
	 * @return Returns the onwardRoutingForSegmentVOs.
	 */
	public Collection<OnwardRouteForSegmentVO> getOnwardRoutingForSegmentVOs() {
		return onwardRoutingForSegmentVOs;
	}
	/**
	 * @param onwardRoutingForSegmentVOs The onwardRoutingForSegmentVOs to set.
	 */
	public void setOnwardRoutingForSegmentVOs(
			Collection<OnwardRouteForSegmentVO> onwardRoutingForSegmentVOs) {
		this.onwardRoutingForSegmentVOs = onwardRoutingForSegmentVOs;
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
    /**
     * @return Returns the carrierCode.
     */
    public String getCarrierCode() {
        return carrierCode;
    }
    /**
     * @param carrierCode The carrierCode to set.
     */
    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }
    /**
     * @return Returns the flightDate.
     */
    public LocalDate getFlightDate() {
        return flightDate;
    }
    /**
     * @param flightDate The flightDate to set.
     */
    public void setFlightDate(LocalDate flightDate) {
        this.flightDate = flightDate;
    }
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
   /* public double getReceivedWeight() {
        return receivedWeight;
    }
    *//**
     * @param receivedWeight The receivedWeight to set.
     *//*
    public void setReceivedWeight(double receivedWeight) {
        this.receivedWeight = receivedWeight;
    }*/
    /**
     * 
     * @return
     */
	public String getOwnAirlineCode() {
		return ownAirlineCode;
	}
	/**
	 * 
	 * @param ownAirlineCode
	 */
	public void setOwnAirlineCode(String ownAirlineCode) {
		this.ownAirlineCode = ownAirlineCode;
	}
	/**
	 * @return Returns the arrivedStatus.
	 */
	public String getArrivedStatus() {
		return this.arrivedStatus;
	}
	/**
	 * @param arrivedStatus The arrivedStatus to set.
	 */
	public void setArrivedStatus(String arrivedStatus) {
		this.arrivedStatus = arrivedStatus;
	}

  public int getLegSerialNumber() {
    return legSerialNumber;
  }

  public void setLegSerialNumber(int legSerialNumber) {
    this.legSerialNumber = legSerialNumber;
  }
/**
 * @return Returns the mailSummaryVOs.
 */
public Collection<MailSummaryVO> getMailSummaryVOs() {
	return mailSummaryVOs;
}
/**
 * @param mailSummaryVOs The mailSummaryVOs to set.
 */
public void setMailSummaryVOs(Collection<MailSummaryVO> mailSummaryVOs) {
	this.mailSummaryVOs = mailSummaryVOs;
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
 * @return Returns the containerOperationFlag.
 */
public String getContainerOperationFlag() {
	return containerOperationFlag;
}
/**
 * @param containerOperationFlag The containerOperationFlag to set.
 */
public void setContainerOperationFlag(String containerOperationFlag) {
	this.containerOperationFlag = containerOperationFlag;
}
/**
 * @return Returns the isReassignFlag.
 */
public boolean isReassignFlag() {
	return isReassignFlag;
}
/**
 * @param isReassignFlag The isReassignFlag to set.
 */
public void setReassignFlag(boolean isReassignFlag) {
	this.isReassignFlag = isReassignFlag;
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
 * @return Returns the isPreassignFlag.
 */
public boolean isPreassignFlag() {
	return isPreassignFlag;
}
/**
 * @param isPreassignFlag The isPreassignFlag to set.
 */
public void setPreassignFlag(boolean isPreassignFlag) {
	this.isPreassignFlag = isPreassignFlag;
}
/**
 * @return Returns the assignedUser.
 */
public String getAssignedUser() {
	return this.assignedUser;
}
/**
 * @param assignedUser The assignedUser to set.
 */
public void setAssignedUser(String assignedUser) {
	this.assignedUser = assignedUser;
}
/**
 * @return Returns the lastUpdateTime.
 */
public LocalDate getLastUpdateTime() {
	return lastUpdateTime;
}
/**
 * @param lastUpdateTime The lastUpdateTime to set.
 */
public void setLastUpdateTime(LocalDate lastUpdateTime) {
	this.lastUpdateTime = lastUpdateTime;
}
/**
 * @return Returns the uldLastUpdateTime.
 */
public LocalDate getUldLastUpdateTime() {
	return uldLastUpdateTime;
}
/**
 * @param uldLastUpdateTime The uldLastUpdateTime to set.
 */
public void setUldLastUpdateTime(LocalDate uldLastUpdateTime) {
	this.uldLastUpdateTime = uldLastUpdateTime;
}
/**
 * @return Returns the flagPAULDResidit.
 */
public String getFlagPAULDResidit() {
	return flagPAULDResidit;
}
/**
 * @param flagPAULDResidit The flagPAULDResidit to set.
 */
public void setFlagPAULDResidit(String flagPAULDResidit) {
	this.flagPAULDResidit = flagPAULDResidit;
}
/**
 * @return the deliveredStatus
 */
public String getDeliveredStatus() {
	return deliveredStatus;
}
/**
 * @param deliveredStatus the deliveredStatus to set
 */
public void setDeliveredStatus(String deliveredStatus) {
	this.deliveredStatus = deliveredStatus;
}
public String getOnwardFlights() {
	return onwardFlights;
}
public void setOnwardFlights(String onwardFlights) {
	this.onwardFlights = onwardFlights;
}
/**
 * @return the intact
 */
public String getIntact() {
	return intact;
}
/**
 * @param intact the intact to set
 */
public void setIntact(String intact) {
	this.intact = intact;
}
public String getTransactonCode() {
	return transactionCode;
}
public void setTransactionCode(String transactionCode) {
	this.transactionCode = transactionCode;
}
public String getDeliverPABuiltContainer() {
	return deliverPABuiltContainer;
}
public void setDeliverPABuiltContainer(String deliverPABuiltContainer) {
	this.deliverPABuiltContainer = deliverPABuiltContainer;
}
public String getBellyCartId() {
	return bellyCartId;
}
public void setBellyCartId(String bellyCartId) {
	this.bellyCartId = bellyCartId;
}
public String getContainerJnyId() {
	return containerJnyId;
}
public void setContainerJnyId(String containerJnyId) {
	this.containerJnyId = containerJnyId;
}
public String getPaCode() {
	return paCode;
}
public void setPaCode(String paCode) {
	this.paCode = paCode;
}
/**
 * @return the transitFlag
 */
public String getTransitFlag() {
	return transitFlag;
}
/**
 * @param transitFlag the transitFlag to set
 */
public void setTransitFlag(String transitFlag) {
	this.transitFlag = transitFlag;
}
public String getReleasedFlag() {
	return releasedFlag;
}
public void setReleasedFlag(String releasedFlag) {
	this.releasedFlag = releasedFlag;
}
/**
 * @return the scanDate
 */
public LocalDate getScanDate() {
	return scanDate;
}
/**
 * @param scanDate the scanDate to set
 */
public void setScanDate(LocalDate scanDate) {
	this.scanDate = scanDate;
}
/**
 * @return the transactionCode
 */
public String getTransactionCode() {
	return transactionCode;
}
/**
 * 	Getter for flightStatus 
 *	Added by : a-4809 on Nov 20, 2014
 * 	Used for :
 */
public String getFlightStatus() {
	return flightStatus;
}
/**
 *  @param flightStatus the flightStatus to set
 * 	Setter for flightStatus 
 *	Added by : a-4809 on Nov 20, 2014
 * 	Used for :
 */
public void setFlightStatus(String flightStatus) {
	this.flightStatus = flightStatus;
}

/**
 * Method to retrieve the value of deletedMailDetails
 */
public Collection<MailbagVO> getDeletedMailDetails() {
	return deletedMailDetails;
}
/**
 * Method to set the value of deletedMailDetails
 * @param deletedMailDetails
 */
public void setDeletedMailDetails(Collection<MailbagVO> deletedMailDetails) {
	this.deletedMailDetails = deletedMailDetails;
}	
/**
 * Method to get the value of undoArrivalFlag
 * @return
 */
	public String getUndoArrivalFlag() {
		return undoArrivalFlag;
	}
	/**
	 * Method to set the value of undoArrivalFlag
	 * @param undoArrivalFlag
	 */
	public void setUndoArrivalFlag(String undoArrivalFlag) {
		this.undoArrivalFlag = undoArrivalFlag;
}		
	/**
	 * @param mailModifyflag the mailModifyflag to set
	 */
	public void setMailModifyflag(boolean mailModifyflag) {
		this.mailModifyflag = mailModifyflag;
	}
	/**
	 * @return the mailModifyflag
	 */
	public boolean isMailModifyflag() {
		return mailModifyflag;
	}		
	/*public String getTareWeight() {
		return tareWeight;
	}
	public void setTareWeight(String tareWeight) {
		this.tareWeight = tareWeight;
	}*/
	public boolean isOflToRsnFlag() {
		return oflToRsnFlag;
	}
	public void setOflToRsnFlag(boolean oflToRsnFlag) {
		this.oflToRsnFlag = oflToRsnFlag;
	}
	public String getContour() {
		return contour;
	}
	public void setContour(String contour) {
		this.contour = contour;
	}
//Added by A-8176
	public String getContainerGroup() {
		return containerGroup;
	}
	public void setContainerGroup(String containerGroup) {
		this.containerGroup = containerGroup;
	}
	public int getContainercount() {
		return containercount;
	}
	public void setContainercount(int containercount) {
		this.containercount = containercount;
	}
	public int getMailbagcount() {
		return mailbagcount;
	}
	public void setMailbagcount(int mailbagcount) {
		this.mailbagcount = mailbagcount;
	}
	public Measure getMailbagwt() {
		return mailbagwt;
	}
	public void setMailbagwt(Measure mailbagwt) {
		this.mailbagwt = mailbagwt;
	}
	public Measure getReceptacleWeight() {
		return receptacleWeight;
	}
	public void setReceptacleWeight(Measure receptacleWeight) {
		this.receptacleWeight = receptacleWeight;
	}
	public int getReceptacleCount() {
		return receptacleCount;
	}
	public void setReceptacleCount(int receptacleCount) {
		this.receptacleCount = receptacleCount;
	}
	public LocalDate getAssignedDate() {
		return assignedDate;
	}
	public void setAssignedDate(LocalDate assignedDate) {
		this.assignedDate = assignedDate;
	}
	public String getAssignedPort() {
		return assignedPort;
	}
	public void setAssignedPort(String assignedPort) {
		this.assignedPort = assignedPort;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public String getContainerPureTransfer() {
		return containerPureTransfer;
	}
	public void setContainerPureTransfer(String containerPureTransfer) {
		this.containerPureTransfer = containerPureTransfer;
	}
	/* This was added to handle the bulk attach AWB from mail outbound screen by A-8176*/
	public String getFromScreen() {
		return fromScreen;
	}
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}
	public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	
	public Measure getActualWeight() {
		return actualWeight;
	}
	public void setActualWeight(Measure actualWeight) {
		this.actualWeight = actualWeight;
	}
	public boolean isFoundTransfer() {
		return foundTransfer;
	}
	public void setFoundTransfer(boolean foundTransfer) {
		this.foundTransfer = foundTransfer;
	}
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public LocalDate getMinReqDelveryTime() {
		return minReqDelveryTime;
	}
	public void setMinReqDelveryTime(LocalDate minReqDelveryTime) {
		this.minReqDelveryTime = minReqDelveryTime;
	}
	public Collection<String> getOffloadedInfo() {
		return offloadedInfo;
	}
	public void setOffloadedInfo(Collection<String> offloadedInfo) {
		this.offloadedInfo = offloadedInfo;
	}
	public int getOffloadCount() {
		return offloadCount;
	}
	public void setOffloadCount(int offloadCount) {
		this.offloadCount = offloadCount;
	}
	/**
	 * @return the aquitULDFlag
	 */
	public boolean isAquitULDFlag() {
		return aquitULDFlag;
	}
	/**
	 * @param aquitULDFlag the aquitULDFlag to set
	 */
	public void setAquitULDFlag(boolean aquitULDFlag) {
		this.aquitULDFlag = aquitULDFlag;
	}
	
	public MailbagEnquiryFilterVO getAdditionalFilters() {
		return additionalFilters;
	}
	public void setAdditionalFilters(MailbagEnquiryFilterVO additionalFilters) {
		this.additionalFilters = additionalFilters;
	}
	public String getActiveTab() {
		return activeTab;
	}
	public void setActiveTab(String activeTab) {
		this.activeTab = activeTab;
	}
	public boolean isFromContainerTab() {
		return fromContainerTab;
	}
	public void setFromContainerTab(boolean fromContainerTab) {
		this.fromContainerTab = fromContainerTab;
	}
	public String getUldFulIndFlag() {
		return uldFulIndFlag;
	}
	public void setUldFulIndFlag(String uldFulIndFlag) {
		this.uldFulIndFlag = uldFulIndFlag;
	}
	public long getUldReferenceNo() {
		return uldReferenceNo;
	}
	
	public void setUldReferenceNo(long uldReferenceNo) {
		this.uldReferenceNo = uldReferenceNo;
	}
	public String getTransistPort() {
		return transistPort;
	}
	public void setTransistPort(String transistPort) {
		this.transistPort = transistPort;
	}
	private Measure grossWeight;
	
	private Measure netWeight;
	/**
	 * @return the grossWeight
	 */
	public Measure getGrossWeight() {
		return grossWeight;
	}
	/**
	 * @param grossWeight the grossWeight to set
	 */
	public void setGrossWeight(Measure grossWeight) {
		this.grossWeight = grossWeight;
	}
	/**
	 * @return the netWeight
	 */
	public Measure getNetWeight() {
		return netWeight;
	}
	/**
	 * @param netWeight the netWeight to set
	 */
	public void setNetWeight(Measure netWeight) {
		this.netWeight = netWeight;
	}

	public String getActWgtSta() {
		return actWgtSta;
	}

	public void setActWgtSta(String actWgtSta) {
		this.actWgtSta = actWgtSta;
	}
	
}
