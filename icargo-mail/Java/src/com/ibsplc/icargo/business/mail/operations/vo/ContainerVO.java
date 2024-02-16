/*
 * ContainerVO.java Created on JUN 30, 2016
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
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-5991
 * 
 */
public class ContainerVO extends AbstractVO {

	private String containerNumber;

	private String type;

	private String finalDestination;

	private LocalDate assignedDate;

	private String assignedUser;

	private String onwardRoute;

	private String onwardFlights;

	private int bags;

	//private double weight;
	private Measure weight;//added by A-7371

	private String remarks;

	private String paBuiltFlag;

	private String operationFlag;

	private int carrierId;

	private String flightNumber; 
	
	/*
	 * Added By Karthick V to include the eventSequenceNumber...
	 * 
	 */
	private long  eventSequenceNumber;
	

	private long flightSequenceNumber;

	private int segmentSerialNumber;

	private int legSerialNumber;

	private String companyCode;

	private String assignedPort;

	private String assignmentFlag;

	private String lastUpdateUser;

	private String acceptanceFlag;

	private boolean isReassign;

	private String carrierCode;

	private LocalDate flightDate;

	private boolean isOffload;
	
	private String carditKey ;
	 
	private String consignmentDocumentNumber;
	
	
	private LocalDate consignmentDate;
	
	private String    carditRecipientId;
	
	private int  resditEventSeqNum;
	
	private String resditEventPort; 
	
	
	
	
	private String handedOverPartner;
	
	
	private boolean isFlightClosureCheckNeeded;

	private String offloadedReason;

	private String offloadedRemarks;

	private String offloadedDescription; 

	private String pou;
	 
	private String pol;
	private String warehouseCode;

	private String locationCode;

	/**
	 * This flag indicates whether this was an offloaded container
	 */
	private String offloadFlag;

	private String ownAirlineCode;
	
	private int ownAirlineId;

	private String arrivedStatus;
	
	private String transferFlag;
	
	private boolean isPreassignNeeded;

	private String paBuiltOpenedFlag;
	
	private boolean overrideUMSFlag;
	
	private LocalDate operationTime; 
	
	private String resditEventString; 
	
	private  int  resditEventSequenceNumber;
	
	private  String paCode;
	
	private LocalDate resditEventUTCDate;
	
	
	private String eventCode;  
	
	
	private LocalDate  eventTime;
	
	
	
	
	 private String  typeCode;
	 
	 private String  equipmentQualifier;
	 
	 //private String  containerWeight;
	 private Measure  containerWeight;//added by A-7371
	 
	 private String  measurementDimension;
	 
	 private String  codeListResponsibleAgency;
	 
	 private String  typeCodeListResponsibleAgency;
	 
	 private String  containerSealNumber;
	 
	 //private double actualWeight;
	 private Measure actualWeight;//added by A-7371
	
	 private String fromCarrier;
	 
	 private String fromFltNum;
	 
	 private LocalDate fromFltDat;
	 
	 private String contentId;
	 private String transactionCode;
	 
	 private boolean oflToRsnFlag;
	  private String flightStatus;
	//Added by A-8353 for IASCB-34167 starts,
	  //if its an found transfer(no scna infor present and try to do outbound scan at intermediate port), in order to block saveMailbagsInboundDtlsForTransfer
	  private boolean foundTransfer;
	  private String screeningUser; //Added by A-9498 as part of IASCB-44577
	  
	 private boolean fromDeviationList;
	 private LocalDate GHTtime;//IASCB-48967
	private boolean fromCarditList;
	private boolean fromWSCL;
	 

	private boolean deleteEmptyContainer;

	private String retainFlag; //Added by A-8672 for IASCB-46064 

	private long frmFltSeqNum;
	private int frmSegSerNum;
	private boolean exportTransfer;
	private boolean handoverReceived;
	private boolean remove;
    private boolean mailbagPresent;
    private LocalDate firstAssignDate;
    private String plannedFlightCarrierCode;
    private int plannedFlightNum;
    private LocalDate plannedFlightDate;
    private String plannedFlightAndDate;
    private double expectedOrProvisionalCharge;
    private String position;
    private String messageVersion;

	private String weighingDeviceName;
	private LocalDate weighingTime;
    
    private String transactionLevel;
    private Money  provosionalCharge;
	   private String baseCurrency;
	   private String rateAvailforallMailbags;
	 private String acceptedPort;
	 private String containerPosition;
	 private Collection<MailbagVO> mailDetails;
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
	 public double getExpectedOrProvisionalCharge() {
		return expectedOrProvisionalCharge;
	}

	public void setExpectedOrProvisionalCharge(double expectedOrProvisionalCharge) {
		this.expectedOrProvisionalCharge = expectedOrProvisionalCharge;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	 public String getScreeningUser() {
		return screeningUser;
	}

	public void setScreeningUser(String screeningUser) {
		this.screeningUser = screeningUser;
	}
	  public int getReceivedBags() {
		return receivedBags;
	}

	public void setReceivedBags(int receivedBags) {
		this.receivedBags = receivedBags;
	}

	/*public double getReceivedWeight() {
		return receivedWeight;
	}

	public void setReceivedWeight(double receivedWeight) {
		this.receivedWeight = receivedWeight;
	}
*/
	private int receivedBags;
		 
	/**
	 * 	 
	 * 	Method		:	ContainerVO.getReceivedWeight
	 *	Added by 	:	A-7371 on 18-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Measure
	 */
	public Measure getReceivedWeight() {
		return receivedWeight;
	}
/**
 * 
 * 	Method		:	ContainerVO.setReceivedWeight
 *	Added by 	:	A-7371 on 18-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@param receivedWeight 
 *	Return type	: 	void
 */
	public void setReceivedWeight(Measure receivedWeight) {
		this.receivedWeight = receivedWeight;
	}

	//private double receivedWeight;
	private Measure receivedWeight;//added by A-7371	  
	/**
	 * module
	 */
	public static final String MODULE= "mail";

	/**
	 * submodule
	 */
	public static final String SUBMODULE = "operations";

	/**
	 * entity
	 */
	public static final String ENTITY = "mail.operations.Container";

	private LocalDate lastUpdateTime;
	
	private LocalDate uldLastUpdateTime;
	
	private String uldLastUpdateUser;
	
	/**
	 * ADDED BY RENO K ABRAHAM FOR SB ULD DeliveryStatus
	 */
	private String deliveredStatus;
	
	/*
	 * Collection<OnwardRoutingVO>
	 */
	private Collection<OnwardRoutingVO> onwardRoutings;
	
	
	private Collection<BookingTimeVO> bookingTimeVOs;
	
	
	private String containerJnyID;
    
    /**
     * shipperBuiltCode - Contains the Shipper Code(PA Code),
     * who build the SB ULD.
     */
    private String shipperBuiltCode;
	
    /**
     * INTACT
     */
	private String intact;
    
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
    
    private LocalDate scannedDate;
	
    private Collection<String> offloadedInfo;
    private String subclassGroup;
    private int noOfDaysInCurrentLoc;
    private String mailSource; //Added for ICRD-156218
    
    private String actualWeightUnit;
    
    private boolean uldTobarrow;
    
    private boolean barrowToUld;
    
    private boolean transferAudit;
    
    private String prevFlightPou;
    
    private String uldFulIndFlag; 
    
    private long uldReferenceNo;

    private String expClsflg;
    private String hbaMarked;

    
    private String planStatus;
    
    private String actWgtSta;	

    private String sourceIndicator;
    
 
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
	 * @return the noOfDaysInCurrentLoc
	 */
	public int getNoOfDaysInCurrentLoc() {
		return noOfDaysInCurrentLoc;
	}

	/**
	 * @param noOfDaysInCurrentLoc the noOfDaysInCurrentLoc to set
	 */
	public void setNoOfDaysInCurrentLoc(int noOfDaysInCurrentLoc) {
		this.noOfDaysInCurrentLoc = noOfDaysInCurrentLoc;
	}

	private int offloadCount;
	
	private String source; 
	/**
	 * @return the offloadCount
	 */
	public int getOffloadCount() {
		return offloadCount;
	}

	/**
	 * @param offloadCount the offloadCount to set
	 */
	public void setOffloadCount(int offloadCount) {
		this.offloadCount = offloadCount;
	}

	/**
	 * @return the offloadedInfo
	 */
	public Collection<String> getOffloadedInfo() {
		return offloadedInfo;
	}

	/**
	 * @param offloadedInfo the offloadedInfo to set
	 */
	public void setOffloadedInfo(Collection<String> offloadedInfo) {
		this.offloadedInfo = offloadedInfo;
	}

	/**
	 * @return the subclassGroup
	 */
	public String getSubclassGroup() {
		return subclassGroup;
	}

	/**
	 * @param subclassGroup the subclassGroup to set
	 */
	public void setSubclassGroup(String subclassGroup) {
		this.subclassGroup = subclassGroup;
	}

	/**
	 * @return Returns the assignedDate.
	 */
	public LocalDate getAssignedDate() {
		return assignedDate;
	}

	/**
	 * @param assignedDate
	 *            The assignedDate to set.
	 */
	public void setAssignedDate(LocalDate assignedDate) {
		this.assignedDate = assignedDate;
	}

	/**
	 * @return Returns the assignedUser.
	 */
	public String getAssignedUser() {
		return assignedUser;
	}

	/**
	 * @param assignedUser
	 *            The assignedUser to set.
	 */
	public void setAssignedUser(String assignedUser) {
		this.assignedUser = assignedUser;
	}

	/**
	 * @return Returns the bags.
	 */
	public int getBags() {
		return bags;
	}

	/**
	 * @param bags
	 *            The bags to set.
	 */
	public void setBags(int bags) {
		this.bags = bags;
	}

	/**
	 * @return Returns the carrierId.
	 */
	public int getCarrierId() {
		return carrierId;
	}

	/**
	 * @param carrierId
	 *            The carrierId to set.
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}

	/**
	 * @return Returns the containerNumber.
	 */
	public String getContainerNumber() {
		return containerNumber;
	}

	/**
	 * @param containerNumber
	 *            The containerNumber to set.
	 */
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	/**
	 * @return Returns the finalDestination.
	 */
	public String getFinalDestination() {
		return finalDestination;
	}

	/**
	 * @param finalDestination
	 *            The finalDestination to set.
	 */
	public void setFinalDestination(String finalDestination) {
		this.finalDestination = finalDestination;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber
	 *            The flightNumber to set.
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
	 * @param flightSequenceNumber
	 *            The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	/**
	 * @return Returns the onwardFlights.
	 */
	public String getOnwardFlights() {
		return onwardFlights;
	}

	/**
	 * @param onwardFlights
	 *            The onwardFlights to set.
	 */
	public void setOnwardFlights(String onwardFlights) {
		this.onwardFlights = onwardFlights;
	}

	/**
	 * @return Returns the onwardRoute.
	 */
	public String getOnwardRoute() {
		return onwardRoute;
	}

	/**
	 * @param onwardRoute
	 *            The onwardRoute to set.
	 */
	public void setOnwardRoute(String onwardRoute) {
		this.onwardRoute = onwardRoute;
	}

	/**
	 * @return Returns the onwardRoutings.
	 */
	public Collection<OnwardRoutingVO> getOnwardRoutings() {
		return onwardRoutings;
	}

	/**
	 * @param onwardRoutings
	 *            The onwardRoutings to set.
	 */
	public void setOnwardRoutings(Collection<OnwardRoutingVO> onwardRoutings) {
		this.onwardRoutings = onwardRoutings;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag
	 *            The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return Returns the paBuiltFlag.
	 */
	public String getPaBuiltFlag() {
		return paBuiltFlag;
	}

	/**
	 * @param paBuiltFlag
	 *            The paBuiltFlag to set.
	 */
	public void setPaBuiltFlag(String paBuiltFlag) {
		this.paBuiltFlag = paBuiltFlag;
	}

	/**
	 * @return Returns the pou.
	 */
	public String getPou() {
		return pou;
	}

	/**
	 * @param pou
	 *            The pou to set.
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the segmentSerialNumber.
	 */
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}

	/**
	 * @param segmentSerialNumber
	 *            The segmentSerialNumber to set.
	 */
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

/*	*//**
	 * @return Returns the weight.
	 *//*
	public double getWeight() {
		return weight;
	}

	*//**
	 * @param weight
	 *            The weight to set.
	 *//*
	public void setWeight(double weight) {
		this.weight = weight;
	}
*/
	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}
/**
 * 
 * 	Method		:	ContainerVO.getWeight
 *	Added by 	:	A-7371 on 18-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@return 
 *	Return type	: 	Measure
 */
	public Measure getWeight() {
		return weight;
	}
/**
 * 
 * 	Method		:	ContainerVO.setWeight
 *	Added by 	:	A-7371 on 18-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@param weight 
 *	Return type	: 	void
 */
	public void setWeight(Measure weight) {
		this.weight = weight;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the assignedPort.
	 */
	public String getAssignedPort() {
		return assignedPort;
	}

	/**
	 * @param assignedPort
	 *            The assignedPort to set.
	 */
	public void setAssignedPort(String assignedPort) {
		this.assignedPort = assignedPort;
	}

	/**
	 * @return Returns the assignmentFlag.
	 */
	public String getAssignmentFlag() {
		return assignmentFlag;
	}

	/**
	 * @param assignmentFlag
	 *            The assignmentFlag to set.
	 */
	public void setAssignmentFlag(String assignmentFlag) {
		this.assignmentFlag = assignmentFlag;
	}

	/**
	 * @return Returns the legSerialNumber.
	 */
	public int getLegSerialNumber() {
		return legSerialNumber;
	}

	/**
	 * @param legSerialNumber
	 *            The legSerialNumber to set.
	 */
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}

	/**
	 * @return Returns the lastUpdateUser.
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser
	 *            The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return Returns the accepatanceFlag.
	 */
	public String getAcceptanceFlag() {
		return acceptanceFlag;
	}

	/**
	 * 
	 * @param acceptanceFlag
	 */
	public void setAcceptanceFlag(String acceptanceFlag) {
		this.acceptanceFlag = acceptanceFlag;
	}

	/**
	 * @return Returns the reassignFlag.
	 */
	public boolean isReassignFlag() {
		return isReassign;
	}

	/**
	 * 
	 * @param isReassign
	 */
	public void setReassignFlag(boolean isReassign) {
		this.isReassign = isReassign;
	}

	/**
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode
	 *            The carrierCode to set.
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
	 * @param flightDate
	 *            The flightDate to set.
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return Returns the isFlightClosureCheckNeeded.
	 */
	public boolean isFlightClosureCheckNeeded() {
		return isFlightClosureCheckNeeded;
	}

	/**
	 * @param isFlightClosureCheckNeeded
	 *            The isFlightClosureCheckNeeded to set.
	 */
	public void setFlightClosureCheckNeeded(boolean isFlightClosureCheckNeeded) {
		this.isFlightClosureCheckNeeded = isFlightClosureCheckNeeded;
	}

	/**
	 * @return Returns the isOffload.
	 */
	public boolean isOffload() {
		return isOffload;
	}

	/**
	 * @param isOffload
	 *            The isOffload to set.
	 */
	public void setOffload(boolean isOffload) {
		this.isOffload = isOffload;
	}

	/**
	 * @return Returns the offloadedReason.
	 */
	public String getOffloadedReason() {
		return offloadedReason;
	}

	/**
	 * @param offloadedReason
	 *            The offloadedReason to set.
	 */
	public void setOffloadedReason(String offloadedReason) {
		this.offloadedReason = offloadedReason;
	}

	/**
	 * @return Returns the offloadedRemarks.
	 */
	public String getOffloadedRemarks() {
		return offloadedRemarks;
	}

	/**
	 * @param offloadedRemarks
	 *            The offloadedRemarks to set.
	 */
	public void setOffloadedRemarks(String offloadedRemarks) {
		this.offloadedRemarks = offloadedRemarks;
	}

	/**
	 * @return Returns the offloadedDescription.
	 */
	public String getOffloadedDescription() {
		return offloadedDescription;
	}

	/**
	 * @param offloadedDescription
	 *            The offloadedDescription to set.
	 */
	public void setOffloadedDescription(String offloadedDescription) {
		this.offloadedDescription = offloadedDescription;
	}

	/**
	 * @return Returns the locationCode.
	 */
	public String getLocationCode() {
		return locationCode;
	}

	/**
	 * @param locationCode
	 *            The locationCode to set.
	 */
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	/**
	 * @return Returns the warehouseCode.
	 */
	public String getWarehouseCode() {
		return warehouseCode;
	}

	/**
	 * @param warehouseCode
	 *            The warehouseCode to set.
	 */
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	/**
	 * @return Returns the offloadFlag.
	 */
	public String getOffloadFlag() {
		return offloadFlag;
	}

	/**
	 * @param offloadFlag
	 *            The offloadFlag to set.
	 */
	public void setOffloadFlag(String offloadFlag) {
		this.offloadFlag = offloadFlag;
	}

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
	 * @param arrivedStatus
	 *            The arrivedStatus to set.
	 */
	public void setArrivedStatus(String arrivedStatus) {
		this.arrivedStatus = arrivedStatus;
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
	 * @return Returns the paBuiltOpenedFlag.
	 */
	public String getPaBuiltOpenedFlag() {
		return paBuiltOpenedFlag;
	}

	/**
	 * @param paBuiltOpenedFlag The paBuiltOpenedFlag to set.
	 */
	public void setPaBuiltOpenedFlag(String paBuiltOpenedFlag) {
		this.paBuiltOpenedFlag = paBuiltOpenedFlag;
	}

	/**
	 * @return the overrideUMSFlag
	 */
	public boolean isOverrideUMSFlag() {
		return overrideUMSFlag;
	}

	/**
	 * @param overrideUMSFlag the overrideUMSFlag to set
	 */
	public void setOverrideUMSFlag(boolean overrideUMSFlag) {
		this.overrideUMSFlag = overrideUMSFlag;
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
	public LocalDate getULDLastUpdateTime() {
		return uldLastUpdateTime;
	}

	/**
	 * @param uldLastUpdateTime The uldLastUpdateTime to set.
	 */
	public void setULDLastUpdateTime(LocalDate uldLastUpdateTime) {
		this.uldLastUpdateTime = uldLastUpdateTime;
	}

	/**
	 * @return Returns the uldLastUpdateUser.
	 */
	public String getULDLastUpdateUser() {
		return uldLastUpdateUser;
	}

	/**
	 * @param uldLastUpdateUser The uldLastUpdateUser to set.
	 */
	public void setULDLastUpdateUser(String uldLastUpdateUser) {
		this.uldLastUpdateUser = uldLastUpdateUser;
	}

	/**
	 * @return Returns the operationTime.
	 */
	public LocalDate getOperationTime() {
		return operationTime;
	}

	/**
	 * @param operationTime The operationTime to set.
	 */
	public void setOperationTime(LocalDate operationTime) {
		this.operationTime = operationTime;
	}

	public String getCarditKey() {
		return carditKey;
	}

	public void setCarditKey(String carditKey) {
		this.carditKey = carditKey;
	}

	public String getConsignmentDocumentNumber() {
		return consignmentDocumentNumber;
	}

	public void setConsignmentDocumentNumber(String consignmentDocumentNumber) {
		this.consignmentDocumentNumber = consignmentDocumentNumber;
	}

	public String getHandedOverPartner() {
		return handedOverPartner;
	}

	public void setHandedOverPartner(String handedOverPartner) {
		this.handedOverPartner = handedOverPartner;
	}

	public String getResditEventString() {
		return resditEventString;
	}

	public void setResditEventString(String resditEventString) {
		this.resditEventString = resditEventString;
	}

	public String getCarditRecipientId() {
		return carditRecipientId;
	}

	public void setCarditRecipientId(String carditRecipientId) {
		this.carditRecipientId = carditRecipientId;
	}

	public LocalDate getConsignmentDate() {
		return consignmentDate;
	}

	public void setConsignmentDate(LocalDate consignmentDate) {
		this.consignmentDate = consignmentDate;
	}

	public String getPaCode() {
		return paCode;
	}

	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	public String getResditEventPort() {
		return resditEventPort;
	}

	public void setResditEventPort(String resditEventPort) {
		this.resditEventPort = resditEventPort;
	}

	public int getResditEventSeqNum() {
		return resditEventSeqNum;
	}

	public void setResditEventSeqNum(int resditEventSeqNum) {
		this.resditEventSeqNum = resditEventSeqNum;
	}

	public int getResditEventSequenceNumber() {
		return resditEventSequenceNumber;
	}

	public void setResditEventSequenceNumber(int resditEventSequenceNumber) {
		this.resditEventSequenceNumber = resditEventSequenceNumber;
	}

	public LocalDate getResditEventUTCDate() {
		return resditEventUTCDate;
	}

	public void setResditEventUTCDate(LocalDate resditEventUTCDate) {
		this.resditEventUTCDate = resditEventUTCDate;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getCodeListResponsibleAgency() {
		return codeListResponsibleAgency;
	}

	public void setCodeListResponsibleAgency(String codeListResponsibleAgency) {
		this.codeListResponsibleAgency = codeListResponsibleAgency;
	}

	public String getContainerSealNumber() {
		return containerSealNumber;
	}

	public void setContainerSealNumber(String containerSealNumber) {
		this.containerSealNumber = containerSealNumber;
	}

	/*public String getContainerWeight() {
		return containerWeight;
	}

	public void setContainerWeight(String containerWeight) {
		this.containerWeight = containerWeight;
	}*/

	public String getEquipmentQualifier() {
		return equipmentQualifier;
	}
/**
 * 
 * 	Method		:	ContainerVO.getContainerWeight
 *	Added by 	:	A-7371 on 18-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@return 
 *	Return type	: 	Measure
 */
	public Measure getContainerWeight() {
		return containerWeight;
	}
/**
 * 
 * 	Method		:	ContainerVO.setContainerWeight
 *	Added by 	:	A-7371 on 18-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@param containerWeight 
 *	Return type	: 	void
 */
	public void setContainerWeight(Measure containerWeight) {
		this.containerWeight = containerWeight;
	}

	public void setEquipmentQualifier(String equipmentQualifier) {
		this.equipmentQualifier = equipmentQualifier;
	}

	public String getMeasurementDimension() {
		return measurementDimension;
	}

	public void setMeasurementDimension(String measurementDimension) {
		this.measurementDimension = measurementDimension;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeCodeListResponsibleAgency() {
		return typeCodeListResponsibleAgency;
	}

	public void setTypeCodeListResponsibleAgency(
			String typeCodeListResponsibleAgency) {
		this.typeCodeListResponsibleAgency = typeCodeListResponsibleAgency;
	}

	public LocalDate getEventTime() {
		return eventTime;
	}

	public void setEventTime(LocalDate eventTime) {
		this.eventTime = eventTime;
	}

	public long getEventSequenceNumber() {
		return eventSequenceNumber;
	}

	public void setEventSequenceNumber(long eventSequenceNumber) {
		this.eventSequenceNumber = eventSequenceNumber;
	}

	public String getFlightStatus() {
		return flightStatus;
	}

	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
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

	public String getFromCarrier() {
		return fromCarrier;
	}

	public void setFromCarrier(String fromCarrier) {
		this.fromCarrier = fromCarrier;
	}

	public LocalDate getFromFltDat() {
		return fromFltDat;
	}

	public void setFromFltDat(LocalDate fromFltDat) {
		this.fromFltDat = fromFltDat;
	}

	public String getFromFltNum() {
		return fromFltNum;
	}

	public void setFromFltNum(String fromFltNum) {
		this.fromFltNum = fromFltNum;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	/**
	 * @return the bookingTimeVOs
	 */
	public Collection<BookingTimeVO> getBookingTimeVOs() {
		return bookingTimeVOs;
	}

	/**
	 * @param bookingTimeVOs the bookingTimeVOs to set
	 */
	public void setBookingTimeVOs(Collection<BookingTimeVO> bookingTimeVOs) {
		this.bookingTimeVOs = bookingTimeVOs;
	}

	public String getContainerJnyID() {
		return containerJnyID;
	}

	public void setContainerJnyID(String containerJnyID) {
		this.containerJnyID = containerJnyID;
	}

	/**
	 * @return the shipperBuiltCode
	 */
	public String getShipperBuiltCode() {
		return shipperBuiltCode;
	}

	/**
	 * @param shipperBuiltCode the shipperBuiltCode to set
	 */
	public void setShipperBuiltCode(String shipperBuiltCode) {
		this.shipperBuiltCode = shipperBuiltCode;
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
	 * @return the scannedDate
	 */
	public LocalDate getScannedDate() {
		return scannedDate;
	}

	/**
	 * @param scannedDate the scannedDate to set
	 */
	public void setScannedDate(LocalDate scannedDate) {
		this.scannedDate = scannedDate;
	}

	/*public double getActualWeight() {
		return actualWeight;
	}

	public void setActualWeight(double actualWeight) {
		this.actualWeight = actualWeight;
	}*/
	/**
	 * 
	 * @return
	 */
	public String getPol() {
		return pol;
	}
	/**
	 * 
	 * 	Method		:	ContainerVO.getActualWeight
	 *	Added by 	:	A-7371 on 18-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Measure
	 */
	public Measure getActualWeight() {
		return actualWeight;
	}
/**
 * 
 * 	Method		:	ContainerVO.setActualWeight
 *	Added by 	:	A-7371 on 18-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@param actualWeight 
 *	Return type	: 	void
 */
	public void setActualWeight(Measure actualWeight) {
		this.actualWeight = actualWeight;
	}

	/**
	 * 
	 * @param pol
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public boolean isOflToRsnFlag() {
		return oflToRsnFlag;
	}
	public void setOflToRsnFlag(boolean oflToRsnFlag) {
		this.oflToRsnFlag = oflToRsnFlag;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getActualWeightUnit() {
		return actualWeightUnit;
	}

	public void setActualWeightUnit(String actualWeightUnit) {
		this.actualWeightUnit = actualWeightUnit;
	}
	public boolean isFoundTransfer() {
		return foundTransfer;
	}
	public void setFoundTransfer(boolean foundTransfer) {
		this.foundTransfer = foundTransfer;
	}
	public boolean isUldTobarrow() {
		return uldTobarrow;
	}
	public void setUldTobarrow(boolean uldTobarrow) {
		this.uldTobarrow = uldTobarrow;
	}

	public boolean isBarrowToUld() {
		return barrowToUld;
	}

	public void setBarrowToUld(boolean barrowToUld) {
		this.barrowToUld = barrowToUld;
	}
	
	   private boolean isContainerDestChanged;
	public boolean isContainerDestChanged() {
		return isContainerDestChanged;
	}
	public void setContainerDestChanged(boolean isContainerDestChanged) {
		this.isContainerDestChanged = isContainerDestChanged;
	}
	public boolean isFromDeviationList() {
		return fromDeviationList;
	}

	public void setFromDeviationList(boolean fromDeviationList) {
		this.fromDeviationList = fromDeviationList;
	}

	/**
	 * 	Getter for gHTtime 
	 *	Added by : A-8061 on 28-Apr-2020
	 * 	Used for :
	 */
	public LocalDate getGHTtime() {
		return GHTtime;
	}

	/**
	 *  @param gHTtime the gHTtime to set
	 * 	Setter for gHTtime 
	 *	Added by : A-8061 on 28-Apr-2020
	 * 	Used for :
	 */
	public void setGHTtime(LocalDate gHTtime) {
		GHTtime = gHTtime;
	}

	/**
	 * 	Getter for fromCarditList 
	 *	Added by : A-8061 on 24-Jun-2020
	 * 	Used for :
	 */
	public boolean isFromCarditList() {
		return fromCarditList;
	}

	/**
	 *  @param fromCarditList the fromCarditList to set
	 * 	Setter for fromCarditList 
	 *	Added by : A-8061 on 24-Jun-2020
	 * 	Used for :
	 */
	public void setFromCarditList(boolean fromCarditList) {
		this.fromCarditList = fromCarditList;
	}
	/**
	 * @return the retainFlag
	 */
	public String getRetainFlag() {
		return retainFlag;
	}
	/**
	 * @param retainFlag the retainFlag to set
	 */
	public void setRetainFlag(String retainFlag) {
		this.retainFlag = retainFlag;
	}
	public boolean isDeleteEmptyContainer() {
		return deleteEmptyContainer;
	}
	public void setDeleteEmptyContainer(boolean deleteEmptyContainer) {
		this.deleteEmptyContainer = deleteEmptyContainer;
	}
	public long getFrmFltSeqNum() {
		return frmFltSeqNum;
	}
	public void setFrmFltSeqNum(long frmFltSeqNum) {
		this.frmFltSeqNum = frmFltSeqNum;
	}
	public int getFrmSegSerNum() {
		return frmSegSerNum;
	}
	public void setFrmSegSerNum(int frmSegSerNum) {
		this.frmSegSerNum = frmSegSerNum;
	}
	public boolean isExportTransfer() {
		return exportTransfer;
	}
	public void setExportTransfer(boolean exportTransfer) {
		this.exportTransfer = exportTransfer;
	}
	public boolean isHandoverReceived() {
		return handoverReceived;
	}
	public void setHandoverReceived(boolean handoverReceived) {
		this.handoverReceived = handoverReceived;
	}

	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}
	
	public boolean isTransferAudit() {
		return transferAudit;
	}

	public void setTransferAudit(boolean transferAudit) {
		this.transferAudit = transferAudit;
	}

	public String getPrevFlightPou() {
		return prevFlightPou;
	}

	public void setPrevFlightPou(String prevFlightPou) {
		this.prevFlightPou = prevFlightPou;
	}

	public boolean isMailbagPresent() {
		return mailbagPresent;
	}

	public void setMailbagPresent(boolean mailbagPresent) {
		this.mailbagPresent = mailbagPresent;
	}

	public LocalDate getFirstAssignDate() {
		return firstAssignDate;
	}

	public void setFirstAssignDate(LocalDate firstAssignDate) {
		this.firstAssignDate = firstAssignDate;
	}
	
	public String getUldFulIndFlag() {
		return uldFulIndFlag;
	}

	public void setUldFulIndFlag(String uldFulIndFlag) {
		this.uldFulIndFlag = uldFulIndFlag;
	}
	 private boolean transStatus;
	public boolean isTransStatus() {
		return transStatus;
	}

	public void setTransStatus(boolean transStatus) {
		this.transStatus = transStatus;
	}
	public String getPlannedFlightCarrierCode() {
		return plannedFlightCarrierCode;
	}
	public void setPlannedFlightCarrierCode(String plannedFlightCarrierCode) {
		this.plannedFlightCarrierCode = plannedFlightCarrierCode;
	}
	public int getPlannedFlightNum() {
		return plannedFlightNum;
	}
	public void setPlannedFlightNum(int plannedFlightNum) {
		this.plannedFlightNum = plannedFlightNum;
	}
	public LocalDate getPlannedFlightDate() {
		return plannedFlightDate;
	}
	public void setPlannedFlightDate(LocalDate plannedFlightDate) {
		this.plannedFlightDate = plannedFlightDate;
	}
	public String getPlannedFlightAndDate() {
		return plannedFlightAndDate;
	}
	public void setPlannedFlightAndDate(String plannedFlightAndDate) {
		this.plannedFlightAndDate = plannedFlightAndDate;
    }
	public boolean isfromWSCL() {
		return fromWSCL;
	}

	public void setFromWSCL(boolean isfromWSCL) {
		this.fromWSCL = isfromWSCL;

	}
   	 public long getUldReferenceNo() {
		return uldReferenceNo;
	}
   
	public void setUldReferenceNo(long uldReferenceNo) {
		this.uldReferenceNo = uldReferenceNo;
	}
	public String getTransactionLevel() {
		return transactionLevel;
	}
	public void setTransactionLevel(String transactionLevel) {
		this.transactionLevel = transactionLevel;
	}
	
	/**
	 * @return the planStatus
	 */
	public String getPlanStatus() {
		return planStatus;
	}

	/**
	 * @param planStatus the planStatus to set
	 */
	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}
	
	public String getMessageVersion() {
		return messageVersion;
	}

	public void setMessageVersion(String messageVersion) {
		this.messageVersion = messageVersion;
	}  

	public String getExpClsflg() {
		return expClsflg;
	}

	public void setExpClsflg(String expClsflg) {
		this.expClsflg = expClsflg;
	}

	public String getHbaMarked() {
		return hbaMarked;
	}

	public void setHbaMarked(String hbaMarked) {
		this.hbaMarked = hbaMarked;
	}

	public String getWeighingDeviceName() {
		return weighingDeviceName;
	}

	public void setWeighingDeviceName(String weighingDeviceName) {
		this.weighingDeviceName = weighingDeviceName;
	}

	public LocalDate getWeighingTime() {
		return weighingTime;
	}

	public void setWeighingTime(LocalDate weighingTime) {
		this.weighingTime = weighingTime;
	}   
    public String getAcceptedPort() {
		return acceptedPort;
	}
	public void setAcceptedPort(String acceptedPort) {
		this.acceptedPort = acceptedPort;
	}
	public String getActWgtSta() {
		return actWgtSta;
	}

	public void setActWgtSta(String actWgtSta) {
		this.actWgtSta = actWgtSta;
	}
	public String getContainerPosition() {
		return containerPosition;
	}
	public void setContainerPosition(String containerPosition) {
		this.containerPosition = containerPosition;
	}
	
	private Measure netWeight;
	private String unit;
	private String weightStatus;
	private String containerType;
	
	public Measure getNetWeight() {
		return netWeight;
	}
	public void setNetWeight(Measure netWeight) {
		this.netWeight = netWeight;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	

	public String getWeightStatus() {
		return weightStatus;
	}

	public void setWeightStatus(String weightStatus) {
		this.weightStatus = weightStatus;
	}
	
	public String getContainerType() {
		return containerType;
	}
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}
	private Boolean isactualweightsaveflag;
	public Boolean getIsactualweightsaveflag() {
		return isactualweightsaveflag;
	}
	public void setIsactualweightsaveflag(Boolean isactualweightsaveflag) {
		this.isactualweightsaveflag = isactualweightsaveflag;
	}
	
	public String getSourceIndicator() {
		return sourceIndicator;
	}
	public void setSourceIndicator(String sourceIndicator) {
		this.sourceIndicator = sourceIndicator;
}
	public Collection<MailbagVO> getMailDetails() {
		return mailDetails;
	}
	public void setMailDetails(Collection<MailbagVO> mailDetails) {
		this.mailDetails = mailDetails;
	}
}
