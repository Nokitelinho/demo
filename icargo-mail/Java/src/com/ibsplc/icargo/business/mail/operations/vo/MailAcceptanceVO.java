/*
 * MailAcceptanceVO.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-5991
 *
 */
public class MailAcceptanceVO extends AbstractVO {
    private String companyCode;
    private String pol;
    private int carrierId;
    private String flightNumber;
    private int legSerialNumber;
    private long flightSequenceNumber;
    private LocalDate flightDate ;    
    private  boolean  isInventory; 
    
    private boolean  isInventoryForArrival;
    
    private Collection<ContainerDetailsVO> containerDetails;
    
    /**
     * This status will be set when operated by PC
     * after validation by client
     */
    private String flightStatus;
    
    private String ownAirlineCode; 
    private int ownAirlineId;
    
    // Added by Roopak
    private String flightCarrierCode; 
    private String destination;
    private String strFlightDate ; 
    
    private String acceptedUser;
    
    private boolean isScanned;
    
    private boolean isPreassignNeeded;
    
    private String duplicateMailOverride;
    
    //added by paulson for inbound acceptance change 
    private String transactionCode;
    private String mailSource;//Added for ICRD-156218
    private boolean isConsignmentGenerationNotNeeded;
    //Added by A-5160 for ICRD-92869 
    private HashMap<String, Collection<String>> polPouMap;
    private boolean mailModifyflag;
    private String isFromTruck;//Added by A-7871 for ICRD-240184
    //Added by A-8176
    private String flightRoute;
    private String flightOperationalStatus;	 
    private String flightOrigin;
    private String flightDestination;
    private String flightType;
	private String aircraftType;
    private String flightDateDesc;
    private String carrierCode;
    private int totalContainerCount;
    private Measure totalContainerWeight;
    //Added by A-8176 as part of ICRD-212021
    private String departureGate;
  //Added by A_7794 as part of ICRD-232299
    private String mailDataSource;
    private PreAdviceVO preadvice;
  //Added by A_8893 as part of IASCB-27535 starts
    private String DCSinfo;
    private String DCSregectionReason;
  //Added by A_8893 as part of IASCB-27535 ends
    private String paBuiltFlag;
    private boolean fromDeviationList;
    //added by A-7815 as part of IASCB-47327
    private  LocalDate flightDepartureDate ; 
    
    private LocalDate GHTtime;//IASCB-48967
    private boolean assignedToFlight;// added by A-8353 IASCB-55985
    private String messageVersion;//Added by A-8527 for IASCB-58918
    private boolean fromCarditList;
    private boolean fromOutboundScreen;
    private boolean transferOnModify;
    
    private boolean isFoundTransfer;
    private boolean modifyAfterExportOpr;
    private String popupAction;
    private Collection<EmbargoDetailsVO> embargoDetails;
    private boolean mailbagPresent;
     private String showWarning;
    private LocalDate std;

	public HashMap<String, Collection<String>> getPolPouMap() {
		return polPouMap;
	}
	public void setPolPouMap(HashMap<String, Collection<String>> polPouMap) {
		this.polPouMap = polPouMap;
	}
	
	/**
	 * @return the isFromTruck
	 */
	public String getIsFromTruck() {
		return isFromTruck;
	}
	/**
	 * @param isFromTruck the isFromTruck to set
	 */
	public void setIsFromTruck(String isFromTruck) {
		this.isFromTruck = isFromTruck;
	}
	public boolean isConsignmentGenerationNotNeeded() {
		return isConsignmentGenerationNotNeeded;
	}
	public void setConsignmentGenerationNotNeeded(
			boolean isConsignmentGenerationNotNeeded) {
		this.isConsignmentGenerationNotNeeded = isConsignmentGenerationNotNeeded;
	}
	public String getTransactionCode() {
		return transactionCode;
	}
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
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
	 * @return containerDetails
	 */
	public Collection<ContainerDetailsVO> getContainerDetails() {
		return containerDetails;
	}
	/**
	 * @param containerDetails
	 */
	public void setContainerDetails(Collection<ContainerDetailsVO> containerDetails) {
		this.containerDetails = containerDetails;
	}
	/**
	 * @return
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}
	/**
	 * @param flightDate
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
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
	 * @return
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
	 * @return legSerialNumber
	 */
	public int getLegSerialNumber() {
		return legSerialNumber;
	}
	/**
	 * @param legSerialNumber
	 */
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
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
	 * @return Returns the flightCarrierCode.
	 */
	public String getFlightCarrierCode() {
		return this.flightCarrierCode;
	}
	/**
	 * @param flightCarrierCode The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}
	/**
	 * @return Returns the strFlightDate.
	 */
	public String getStrFlightDate() {
		return this.strFlightDate;
	}
	/**
	 * @param strFlightDate The strFlightDate to set.
	 */
	public void setStrFlightDate(String strFlightDate) {
		this.strFlightDate = strFlightDate;
	}
    /**
     * @return Returns the flightStatus.
     */
    public String getFlightStatus() {
        return flightStatus;
    }
    /**
     * @param flightStatus The flightStatus to set.
     */
    public void setFlightStatus(String flightStatus) {
        this.flightStatus = flightStatus;
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
	 * @return Returns the ownAirlineCode.
	 */
	public String getOwnAirlineCode() {
		return this.ownAirlineCode;
	}
	/**
	 * @param ownAirlineCode The ownAirlineCode to set.
	 */
	public void setOwnAirlineCode(String ownAirlineCode) {
		this.ownAirlineCode = ownAirlineCode;
	}
	/**
	 * @return Returns the isScanned.
	 */
	public boolean isScanned() {
		return isScanned;
	}
	/**
	 * @param isScanned The isScanned to set.
	 */
	public void setScanned(boolean isScanned) {
		this.isScanned = isScanned;
	}
	/**
	 * @return Returns the ownAirlineId.
	 */
	public int getOwnAirlineId() {
		return ownAirlineId;
	}
	/**
	 * @param ownAirlineId The ownAirlineId to set.
	 */
	public void setOwnAirlineId(int ownAirlineId) {
		this.ownAirlineId = ownAirlineId;
	}
	/**
	 * @return Returns the isPreassignNeeded.
	 */
	public boolean isPreassignNeeded() {
		return isPreassignNeeded;
	}
	/**
	 * @param isPreassignNeeded The isPreassignNeeded to set.
	 */
	public void setPreassignNeeded(boolean isPreassignNeeded) {
		this.isPreassignNeeded = isPreassignNeeded;
	}
	/**
	 * @return Returns the isInventory.
	 */
	public boolean isInventory() {
		return isInventory;
	}
	/**
	 * @param isInventory The isInventory to set.
	 */
	public void setInventory(boolean isInventory) {
		this.isInventory = isInventory;
	}
	public boolean isInventoryForArrival() {
		return isInventoryForArrival;
	}
	public void setInventoryForArrival(boolean isInventoryForArrival) {
		this.isInventoryForArrival = isInventoryForArrival;
	}
	public String getDuplicateMailOverride() {
		return duplicateMailOverride;
	}
	public void setDuplicateMailOverride(String duplicateMailOverride) {
		this.duplicateMailOverride = duplicateMailOverride;
	}
	/**
	 * @return the mailSource
	 */
	public String getMailSource() {
		return mailSource;
	}
	/**
	 * @param mailSource the mailSource to set
	 */
	public void setMailSource(String mailSource) {
		this.mailSource = mailSource;
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
//Added by A-8176
	public String getFlightRoute() {
		return flightRoute;
	}
	public void setFlightRoute(String flightRoute) {
		this.flightRoute = flightRoute;
	}
	public String getFlightOperationalStatus() {
		return flightOperationalStatus;
	}
	public void setFlightOperationalStatus(String flightOperationalStatus) {
		this.flightOperationalStatus = flightOperationalStatus;
	}
	public String getFlightOrigin() {
		return flightOrigin;
	}
	public void setFlightOrigin(String flightOrigin) {
		this.flightOrigin = flightOrigin;
	}
	public String getFlightDestination() {
		return flightDestination;
	}
	public void setFlightDestination(String flightDestination) {
		this.flightDestination = flightDestination;
	}
	public String getFlightType() {
		return flightType;
	}
	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}
	public String getAircraftType() {
		return aircraftType;
	}
	public void setAircraftType(String aircraftType) {
		this.aircraftType = aircraftType;
	}
	/*public String getReceptacleWgt() {
		return receptacleWgt;
	}
	public void setReceptacleWgt(String receptacleWgt) {
		this.receptacleWgt = receptacleWgt;
	}*/
	public String getFlightDateDesc() {
		return flightDateDesc;
	}
	public void setFlightDateDesc(String flightDateDesc) {
		this.flightDateDesc = flightDateDesc;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public int getTotalContainerCount() {
		return totalContainerCount;
	}
	public void setTotalContainerCount(int totalContainerCount) {
		this.totalContainerCount = totalContainerCount;
	}
	public Measure getTotalContainerWeight() {
		return totalContainerWeight;
	}
	public void setTotalContainerWeight(Measure totalContainerWeight) {
		this.totalContainerWeight = totalContainerWeight;
	}
	public String getDepartureGate() {
		return departureGate;
	}
	public void setDepartureGate(String departureGate) {
		this.departureGate = departureGate;
	}
 //Ends added by A-8176
	//Added by A_7794 as part of ICRD-232299
	public String getMailDataSource() {
		return mailDataSource;
	}
	public void setMailDataSource(String mailDataSource) {
		this.mailDataSource = mailDataSource;
	}
	public PreAdviceVO getPreadvice() {
		return preadvice;
	}
	public void setPreadvice(PreAdviceVO preadvice) {
		this.preadvice = preadvice;
	}
	//Added by A_8893 as part of IASCB-27535 starts
	public String getDCSinfo() {
		return DCSinfo;
	}
	public void setDCSinfo(String dCSinfo) {
		DCSinfo = dCSinfo;
	}
	public String getDCSregectionReason() {
		return DCSregectionReason;
	}
	public void setDCSregectionReason(String dCSregectionReason) {
		DCSregectionReason = dCSregectionReason;
	}
	//Added by A_8893 as part of IASCB-27535 ends
	//Added as part of IASCB-24668 by A-8156
	public String getPaBuiltFlag() {
		return paBuiltFlag;
	}
	public void setPaBuiltFlag(String paBuiltFlag) {
		this.paBuiltFlag = paBuiltFlag;
	}
	public boolean isFromDeviationList() {
		return fromDeviationList;
	}
	public void setFromDeviationList(boolean fromDeviationList) {
		this.fromDeviationList = fromDeviationList;
	}
	public LocalDate getFlightDepartureDate() {
		return flightDepartureDate;
	}
	public void setFlightDepartureDate(LocalDate flightDepartureDate) {
		this.flightDepartureDate = flightDepartureDate;
	}
	/**
	 * 	Getter for gHTtime 
	 *	Added by : A-8061 on 27-Apr-2020
	 * 	Used for :
	 */
	public LocalDate getGHTtime() {
		return GHTtime;
	}
	/**
	 *  @param gHTtime the gHTtime to set
	 * 	Setter for gHTtime 
	 *	Added by : A-8061 on 27-Apr-2020
	 * 	Used for :
	 */
	public void setGHTtime(LocalDate gHTtime) {
		GHTtime = gHTtime;
	}
	/**
	 * 
	 * 	 isassignedToFlight 
	 *	Added by : A-8353 on 12-Jun-2020
	 * 
	 */
	public boolean isAssignedToFlight() {
		return assignedToFlight;
	}
	/**
	 * 
	 * 	Setter for assignedToFlight 
	 *	Added by : A-8353 on 12-Jun-2020
	 * 	
	 */
	public void setAssignedToFlight(boolean assignedToFlight) {
		this.assignedToFlight = assignedToFlight;
	}
	public String getMessageVersion() {
		return messageVersion;
	}
	public void setMessageVersion(String messageVersion) {
		this.messageVersion = messageVersion;
	}
	/**
	 * 	Getter for fromCarditList 
	 *	Added by : A-8061 on 23-Jun-2020
	 * 	Used for :
	 */
	public boolean isFromCarditList() {
		return fromCarditList;
	}
	/**
	 *  @param fromCarditList the fromCarditList to set
	 * 	Setter for fromCarditList 
	 *	Added by : A-8061 on 23-Jun-2020
	 * 	Used for :
	 */
	public void setFromCarditList(boolean fromCarditList) {
		this.fromCarditList = fromCarditList;
	}
	/**
	 * 	Getter for fromOutboundScreen 
	 *	Added by : A-8353 on 23-Jul-2020
	 * 	Used for :
	 */
	public boolean isFromOutboundScreen() {
		return fromOutboundScreen;
	}
	/**
	 *  @param fromOutboundScreen the fromOutboundScreen to set
	 * 	Setter for fromCarditList 
	 *	Added by : A-8353 on 23-Jul-2020
	 * 	Used for :
	 */
	public void setFromOutboundScreen(boolean fromOutboundScreen) {
		this.fromOutboundScreen = fromOutboundScreen;
	}
	/**
	 * 	Getter for isFoundTransfer 
	 *	Added by : A-8061 on 27-Aug-2020
	 * 	Used for :
	 */
	public boolean isFoundTransfer() {
		return isFoundTransfer;
	}
	/**
	 *  @param isFoundTransfer the isFoundTransfer to set
	 * 	Setter for isFoundTransfer 
	 *	Added by : A-8061 on 27-Aug-2020
	 * 	Used for :
	 */
	public void setFoundTransfer(boolean isFoundTransfer) {
		this.isFoundTransfer = isFoundTransfer;
	}
	public boolean isTransferOnModify() {
		return transferOnModify;
	}
	public void setTransferOnModify(boolean transferOnModify) {
		this.transferOnModify = transferOnModify;
	}
	public boolean isModifyAfterExportOpr() {
		return modifyAfterExportOpr;
	}
	public void setModifyAfterExportOpr(boolean modifyAfterExportOpr) {
		this.modifyAfterExportOpr = modifyAfterExportOpr;
	}
	public String getPopupAction() {
		return popupAction;
	}
	public void setPopupAction(String popupAction) {
		this.popupAction = popupAction;
	}
	public Collection<EmbargoDetailsVO> getEmbargoDetails() {
		return embargoDetails;
	}
	public void setEmbargoDetails(Collection<EmbargoDetailsVO> embargoDetails) {
		this.embargoDetails = embargoDetails;
	}
	public boolean isMailbagPresent() {
		return mailbagPresent;
	}
	public void setMailbagPresent(boolean mailbagPresent) {
		this.mailbagPresent = mailbagPresent;
	}
	public String getShowWarning() {
		return showWarning;
	}
	public void setShowWarning(String showWarning) {
		this.showWarning = showWarning;
	}
	public LocalDate getStd() {
		return std;
	}
	public void setStd(LocalDate std) {
		this.std = std;
	}
	
	

	
		
}
