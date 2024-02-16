/*
 * DespatchDetailsVO.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-5991 Despatch Details entered for Acceptance
 */
public class DespatchDetailsVO extends AbstractVO {

	private String companyCode;

	private String dsn;

	private int acceptedBags;

	//private double acceptedWeight;
	private Measure acceptedWeight;//added by A-7371

	private String mailClass;

	private String mailCategoryCode;

	private String containerType;

	private String mailSubclass;

	private String acceptanceFlag;

	

	private String containerNumber;

	private String uldNumber;

	private String pou;

	private String destination;

	private boolean isOffload;

	private String ownAirlineCode;

	private int legSerialNumber;

	/**
	 * Accepted Date and time
	 */
	private LocalDate acceptedDate;

	private String acceptedUser;

	private String pltEnabledFlag;

	private int carrierId;

	private String flightNumber;

	private long flightSequenceNumber;

	private int segmentSerialNumber;

	private String operationalFlag;

	private String consignmentNumber;

	private LocalDate consignmentDate;

	private String originOfficeOfExchange;

	private String destinationOfficeOfExchange;

	private int statedBags;

	//private double statedWeight;
	private Measure statedWeight;//added by A-7371

	private int year;

	private int prevAcceptedBags;

	//private double prevAcceptedWeight;
	private Measure prevAcceptedWeight;//added by A-7371
 
	
	/**
	  *  Added By Karthick V as the part of the NCA Mail Tracking Cr 
	  */
	private String containerForInventory;
	
	private String containerTypeAtAirport;
	
	private int prevStatedBags;

	//private double prevStatedWeight;
	private Measure prevStatedWeight;//added by A-7371

	private LocalDate receivedDate;

	private String offloadedReason;

	private String offloadedRemarks;

	private String offloadedDescription;

	private String airportCode;

	private String carrierCode;

	private LocalDate flightDate;

	private String paCode;

	private int consignmentSequenceNumber;

	private int deliveredBags;

	//private double deliveredWeight;
	private Measure deliveredWeight;//added by A-7371

	private int receivedBags;

	//private double receivedWeight;
	private Measure receivedWeight;//added by A-7371

	private int prevDeliveredBags;

	//private double prevDeliveredWeight;
	private Measure prevDeliveredWeight;//added by A-7371

	private int prevReceivedBags;

	//private double prevReceivedWeight;
	private Measure prevReceivedWeight;//added by A-7371

	private String offloadFlag;

	private String operationType;

	private String transferFlag;
	
	private String paBuiltFlag; 
	
	private Collection<String> containers;
	
	//private double statedVolume;
	private Measure statedVolume;//added by A-7371
	
	
	private String capNotAcceptedStatus;
	
	private String latestStatus;
 
	private int offloadedBags;

	private String rsn; // Added By A-10543 for Airmail portlet
	//private double offloadedWeight;
	private Measure offloadedWeight;//added by A-7371
	
	private boolean delivered;
	/** 
	 * @author a-2107
	 * For BUGID :- 28977
	 */
	
	//private double acceptedVolume;
	private Measure acceptedVolume;//added by A-7371
	
	//Added by Paulson for Inbound accepatance change - mtk552
	private String transactionCode;
	/**
	 * These properties are used in ReassignDSN client,
	 * to show the Available Bag Count and weight.
	 */
	private int acceptedPcsToDisplay;	
	//private double acceptedWgtToDisplay;
	private Measure acceptedWgtToDisplay;
	/**
	 * Indicates that this despatch was newly arrived
	 */
	private boolean isNewInbound;

    /*
     * Added by RENO For CR : AirNZ 404 : Mail Allocation
     */
    private String ubrNumber;
    private LocalDate bookingLastUpdateTime;
    private LocalDate bookingFlightDetailLastUpdTime;
    

    
    
    /**
     * Added for HHT
     * if exception occurs to set error Code and err description
     */
    
    private String errorType;
    
    private String errorDescription;
    
    /**
     * This is to For HHT to ack of
     */
    private String acknowledge;
	
	/*
	 * Added For ANZ BUG : 37646
	 * Done in regard with Performance Improvement
	 */
	private String displayLabel;
	//Added by A-4810 for bug-fix-icrd-9151.
	 private int transferredPieces;
     //private double transferredWeight;
	 private Measure transferredWeight;//added by A-7371
     private boolean istransfermail;
     private int alreadyTransferredPieces;
     //private double alreadyTransferredWeight;
     private Measure alreadyTransferredWeight;//added by A-7371
     private boolean isDomesticTransfer;
     
     /*remarks to be saved in MTKDSNCSGCONSEG
       for indigo domestic mail cr 11170
     */
     private String remarks;
     
     private String csgOrigin; //added by a-5133 as part of CR ICRD-19158 
     private String csgDestination; //added by a-5133 as part of CR ICRD-19158
     
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
     
     public String getCsgOrigin() {
		return csgOrigin;
	}
     
	public void setCsgOrigin(String csgOrigin) {
		this.csgOrigin = csgOrigin;
	}
	public String getCsgDestination() {
		return csgDestination;
	}
	public void setCsgDestination(String csgDestination) {
		this.csgDestination = csgDestination;
	}
	
     
     
	/**
	 * @return the isDomesticTransfer
	 */
	public boolean isDomesticTransfer() {
		return isDomesticTransfer;
	}
	/**
	 * @param isDomesticTransfer the isDomesticTransfer to set
	 */
	public void setDomesticTransfer(boolean isDomesticTransfer) {
		this.isDomesticTransfer = isDomesticTransfer;
	}
    
	public String getAcknowledge() {
		return acknowledge;
	}

	public void setAcknowledge(String acknowledge) {
		this.acknowledge = acknowledge;
	}

	/**
	 * 
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
	 * @return Returns the airportCode.
	 */
	public String getAirportCode() {
		return airportCode;
	}

	/**
	 * @param airportCode
	 *            The airportCode to set.
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	/**
	 * @return Returns the prevAcceptedBags.
	 */
	public int getPrevAcceptedBags() {
		return prevAcceptedBags;
	}

	/**
	 * @param prevAcceptedBags
	 *            The prevAcceptedBags to set.
	 */
	public void setPrevAcceptedBags(int prevAcceptedBags) {
		this.prevAcceptedBags = prevAcceptedBags;
	}

	/**
	 * @return Returns the prevAccptedWeight.
	 */
	/*public double getPrevAcceptedWeight() {
		return prevAcceptedWeight;
	}

	*//**
	 * @param prevAccptedWeight
	 *            The prevAccptedWeight to set.
	 *//*
	public void setPrevAcceptedWeight(double prevAccptedWeight) {
		this.prevAcceptedWeight = prevAccptedWeight;
	}*/

	/**
	 * @return Returns the prevStatedBags.
	 */
	public int getPrevStatedBags() {
		return prevStatedBags;
	}
/**
 * 
 * 	Method		:	DespatchDetailsVO.getPrevAcceptedWeight
 *	Added by 	:	A-7371 on 21-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@return 
 *	Return type	: 	Measure
 */
	public Measure getPrevAcceptedWeight() {
		return prevAcceptedWeight;
	}
/**
 * 
 * 	Method		:	DespatchDetailsVO.setPrevAcceptedWeight
 *	Added by 	:	A-7371 on 21-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@param prevAcceptedWeight 
 *	Return type	: 	void
 */
	public void setPrevAcceptedWeight(Measure prevAcceptedWeight) {
		this.prevAcceptedWeight = prevAcceptedWeight;
	}

	/**
	 * @param prevStatedBags
	 *            The prevStatedBags to set.
	 */
	public void setPrevStatedBags(int prevStatedBags) {
		this.prevStatedBags = prevStatedBags;
	}

	/**
	 * @return Returns the prevStatedWeight.
	 */
	/*public double getPrevStatedWeight() {
		return prevStatedWeight;
	}

	*//**
	 * @param prevStatedWeight
	 *            The prevStatedWeight to set.
	 *//*
	public void setPrevStatedWeight(double prevStatedWeight) {
		this.prevStatedWeight = prevStatedWeight;
	}
*/
	/**
	 * @return acceptedBags
	 */
	public int getAcceptedBags() {
		return acceptedBags;
	}
/**
 * 
 * 	Method		:	DespatchDetailsVO.getPrevStatedWeight
 *	Added by 	:	A-7371 on 21-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@return 
 *	Return type	: 	Measure
 */
	public Measure getPrevStatedWeight() {
		return prevStatedWeight;
	}
/**
 * 
 * 	Method		:	DespatchDetailsVO.setPrevStatedWeight
 *	Added by 	:	A-7371 on 21-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@param prevStatedWeight 
 *	Return type	: 	void
 */
	public void setPrevStatedWeight(Measure prevStatedWeight) {
		this.prevStatedWeight = prevStatedWeight;
	}

	/**
	 * @param acceptedBags
	 */
	public void setAcceptedBags(int acceptedBags) {
		this.acceptedBags = acceptedBags;
	}

	/**
	 * @return acceptedWeight
	 *//*
	public double getAcceptedWeight() {
		return acceptedWeight;
	}

	*//**
	 * @param acceptedWeight
	 *//*
	public void setAcceptedWeight(double acceptedWeight) {
		this.acceptedWeight = acceptedWeight;
	}

	*//**
	 * @return companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}
/**
 * 
 * 	Method		:	DespatchDetailsVO.getAcceptedWeight
 *	Added by 	:	A-7371 on 21-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@return 
 *	Return type	: 	Measure
 */
	public Measure getAcceptedWeight() {
		return acceptedWeight;
	}
/**
 * 
 * 	Method		:	DespatchDetailsVO.setAcceptedWeight
 *	Added by 	:	A-7371 on 21-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@param acceptedWeight 
 *	Return type	: 	void
 */
	public void setAcceptedWeight(Measure acceptedWeight) {
		this.acceptedWeight = acceptedWeight;
	}

	/**
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return destinationOfficeOfExchange
	 */
	public String getDestinationOfficeOfExchange() {
		return destinationOfficeOfExchange;
	}

	/**
	 * @param destinationOfficeOfExchange
	 */
	public void setDestinationOfficeOfExchange(
			String destinationOfficeOfExchange) {
		this.destinationOfficeOfExchange = destinationOfficeOfExchange;
	}

	/**
	 * @return originOfficeOfExchange
	 */
	public String getOriginOfficeOfExchange() {
		return originOfficeOfExchange;
	}

	/**
	 * @param originOfficeOfExchange
	 */
	public void setOriginOfficeOfExchange(String originOfficeOfExchange) {
		this.originOfficeOfExchange = originOfficeOfExchange;
	}

	/**
	 * @return
	 */
	public int getStatedBags() {
		return statedBags;
	}

	/**
	 * @param statedBags
	 */
	public void setStatedBags(int statedBags) {
		this.statedBags = statedBags;
	}

	/**
	 * @return statedWeight
	 */
	/*public double getStatedWeight() {
		return statedWeight;
	}

	*//**
	 * @param statedWeight
	 *//*
	public void setStatedWeight(double statedWeight) {
		this.statedWeight = statedWeight;
	}*/

	/**
	 * @return acceptedDate
	 */
	public LocalDate getAcceptedDate() {
		return acceptedDate;
	}
/**
 * 
 * 	Method		:	DespatchDetailsVO.getStatedWeight
 *	Added by 	:	A-7371 on 21-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@return 
 *	Return type	: 	Measure
 */
	public Measure getStatedWeight() {
		return statedWeight;
	}
/**
 * 
 * 	Method		:	DespatchDetailsVO.setStatedWeight
 *	Added by 	:	A-7371 on 21-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@param statedWeight 
 *	Return type	: 	void
 */
	public void setStatedWeight(Measure statedWeight) {
		this.statedWeight = statedWeight;
	}

	/**
	 * @param acceptedDate
	 */
	public void setAcceptedDate(LocalDate acceptedDate) {
		this.acceptedDate = acceptedDate;
	}

	/**
	 * @return acceptedUser
	 */
	public String getAcceptedUser() {
		return acceptedUser;
	}

	/**
	 * @param acceptedUser
	 */
	public void setAcceptedUser(String acceptedUser) {
		this.acceptedUser = acceptedUser;
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
	 * @return consignmentDate
	 */
	public LocalDate getConsignmentDate() {
		return consignmentDate;
	}

	/**
	 * @param consignmentDate
	 */
	public void setConsignmentDate(LocalDate consignmentDate) {
		this.consignmentDate = consignmentDate;
	}

	/**
	 * @return consignmentNumber
	 */
	public String getConsignmentNumber() {
		return consignmentNumber;
	}

	/**
	 * @param consignmentNumber
	 */
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}

	/**
	 * @return dsn
	 */
	public String getDsn() {
		return dsn;
	}

	/**
	 * @param dsn
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
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
	 * @return mailClass
	 */
	public String getMailClass() {
		return mailClass;
	}

	/**
	 * @param mailClass
	 */
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}

	/**
	 * @return operationalFlag
	 */
	public String getOperationalFlag() {
		return operationalFlag;
	}

	/**
	 * @param operationalFlag
	 */
	public void setOperationalFlag(String operationalFlag) {
		this.operationalFlag = operationalFlag;
	}

	/**
	 * @return pltEnabledFlag
	 */
	public String getPltEnabledFlag() {
		return pltEnabledFlag;
	}

	/**
	 * @param pltEnabledFlag
	 */
	public void setPltEnabledFlag(String pltEnabledFlag) {
		this.pltEnabledFlag = pltEnabledFlag;
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
	 * @return Returns the year.
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year
	 *            The year to set.
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return Returns the containerType.
	 */
	public String getContainerType() {
		return containerType;
	}

	/**
	 * @param containerType
	 *            The containerType to set.
	 */
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	/**
	 * @return Returns the acceptanceFlag.
	 */
	public String getAcceptanceFlag() {
		return acceptanceFlag;
	}

	/**
	 * @param acceptanceFlag
	 *            The acceptanceFlag to set.
	 */
	public void setAcceptanceFlag(String acceptanceFlag) {
		this.acceptanceFlag = acceptanceFlag;
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
	 * @return Returns the uldNumber.
	 */
	public String getUldNumber() {
		return uldNumber;
	}

	/**
	 * @param uldNumber
	 *            The uldNumber to set.
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	/**
	 * @return Returns the destination.
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination
	 *            The destination to set.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
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
	 * @return Returns the mailCategoryCode.
	 */
	public String getMailCategoryCode() {
		return this.mailCategoryCode;
	}

	/**
	 * @param mailCategoryCode
	 *            The mailCategoryCode to set.
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
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
	 * @return Returns the consignmentSerialNumber.
	 */
	public int getConsignmentSequenceNumber() {
		return consignmentSequenceNumber;
	}

	/**
	 * @param consignmentSerialNumber
	 *            The consignmentSerialNumber to set.
	 */
	public void setConsignmentSequenceNumber(int consignmentSerialNumber) {
		this.consignmentSequenceNumber = consignmentSerialNumber;
	}

	/**
	 * @return Returns the paCode.
	 */
	public String getPaCode() {
		return paCode;
	}

	/**
	 * @param paCode
	 *            The paCode to set.
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
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
	 * @return Returns the deliveredBags.
	 */
	public int getDeliveredBags() {
		return deliveredBags;
	}

	/**
	 * @param deliveredBags
	 *            The deliveredBags to set.
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
	 * @param deliveredWeight
	 *            The deliveredWeight to set.
	 *//*
	public void setDeliveredWeight(double deliveredWeight) {
		this.deliveredWeight = deliveredWeight;
	}*/
/**
 * 
 * 	Method		:	DespatchDetailsVO.getDeliveredWeight
 *	Added by 	:	A-7371 on 21-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@return 
 *	Return type	: 	Measure
 */
	public Measure getDeliveredWeight() {
		return deliveredWeight;
	}
/**
 * 
 * 	Method		:	DespatchDetailsVO.setDeliveredWeight
 *	Added by 	:	A-7371 on 21-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@param deliveredWeight 
 *	Return type	: 	void
 */
	public void setDeliveredWeight(Measure deliveredWeight) {
		this.deliveredWeight = deliveredWeight;
	}

	/**
	 * @return Returns the prevDeliveredBags.
	 */
	public int getPrevDeliveredBags() {
		return prevDeliveredBags;
	}

	/**
	 * @param prevDeliveredBags
	 *            The prevDeliveredBags to set.
	 */
	public void setPrevDeliveredBags(int prevDeliveredBags) {
		this.prevDeliveredBags = prevDeliveredBags;
	}

	/**
	 * @return Returns the prevDeliveredWeight.
	 */
	/*public double getPrevDeliveredWeight() {
		return prevDeliveredWeight;
	}

	*//**
	 * @param prevDeliveredWeight
	 *            The prevDeliveredWeight to set.
	 *//*
	public void setPrevDeliveredWeight(double prevDeliveredWeight) {
		this.prevDeliveredWeight = prevDeliveredWeight;
	}*/
/**
 * 
 * 	Method		:	DespatchDetailsVO.getPrevDeliveredWeight
 *	Added by 	:	A-7371 on 21-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@return 
 *	Return type	: 	Measure
 */
	public Measure getPrevDeliveredWeight() {
		return prevDeliveredWeight;
	}
/**
 * 
 * 	Method		:	DespatchDetailsVO.setPrevDeliveredWeight
 *	Added by 	:	A-7371 on 21-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@param prevDeliveredWeight 
 *	Return type	: 	void
 */
	public void setPrevDeliveredWeight(Measure prevDeliveredWeight) {
		this.prevDeliveredWeight = prevDeliveredWeight;
	}

	/**
	 * @return Returns the prevReceivedBags.
	 */
	public int getPrevReceivedBags() {
		return prevReceivedBags;
	}

	/**
	 * @param prevReceivedBags
	 *            The prevReceivedBags to set.
	 */
	public void setPrevReceivedBags(int prevReceivedBags) {
		this.prevReceivedBags = prevReceivedBags;
	}

	/**
	 * @return Returns the prevReceivedWeight.
	 */
	/*public double getPrevReceivedWeight() {
		return prevReceivedWeight;
	}

	*//**
	 * @param prevReceivedWeight
	 *            The prevReceivedWeight to set.
	 *//*
	public void setPrevReceivedWeight(double prevReceivedWeight) {
		this.prevReceivedWeight = prevReceivedWeight;
	}*/
/**
 * 
 * 	Method		:	DespatchDetailsVO.getPrevReceivedWeight
 *	Added by 	:	A-7371 on 21-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@return 
 *	Return type	: 	Measure
 */
	public Measure getPrevReceivedWeight() {
		return prevReceivedWeight;
	}
/**
 * 
 * 	Method		:	DespatchDetailsVO.setPrevReceivedWeight
 *	Added by 	:	A-7371 on 21-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@param prevReceivedWeight 
 *	Return type	: 	void
 */
	public void setPrevReceivedWeight(Measure prevReceivedWeight) {
		this.prevReceivedWeight = prevReceivedWeight;
	}

	/**
	 * @return Returns the receivedBags.
	 */
	public int getReceivedBags() {
		return receivedBags;
	}

	/**
	 * @param receivedBags
	 *            The receivedBags to set.
	 */
	public void setReceivedBags(int receivedBags) {
		this.receivedBags = receivedBags;
	}
/**
 * 
 * 	Method		:	DespatchDetailsVO.getReceivedWeight
 *	Added by 	:	A-7371 on 21-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@return 
 *	Return type	: 	Measure
 */
	public Measure getReceivedWeight() {
		return receivedWeight;
	}
/**
 * 
 * 	Method		:	DespatchDetailsVO.setReceivedWeight
 *	Added by 	:	A-7371 on 21-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@param receivedWeight 
 *	Return type	: 	void
 */
	public void setReceivedWeight(Measure receivedWeight) {
		this.receivedWeight = receivedWeight;
	}

	/**
	 * @return Returns the receivedWeight.
	 */
	/*public double getReceivedWeight() {
		return receivedWeight;
	}

	*//**
	 * @param receivedWeight
	 *            The receivedWeight to set.
	 *//*
	public void setReceivedWeight(double receivedWeight) {
		this.receivedWeight = receivedWeight;
	}
*/
	/**
	 * @return Returns the ownAirlineCode.
	 */
	public String getOwnAirlineCode() {
		return ownAirlineCode;
	}

	/**
	 * @param ownAirlineCode
	 *            The ownAirlineCode to set.
	 */
	public void setOwnAirlineCode(String ownAirlineCode) {
		this.ownAirlineCode = ownAirlineCode;
	}

	/**
	 * @return Returns the receivedDate.
	 */
	public LocalDate getReceivedDate() {
		return receivedDate;
	}

	/**
	 * @param receivedDate
	 *            The receivedDate to set.
	 */
	public void setReceivedDate(LocalDate receivedDate) {
		this.receivedDate = receivedDate;
	}

	/**
	 * @return Returns the legSerialNumber.
	 */
	public int getLegSerialNumber() {
		return this.legSerialNumber;
	}

	/**
	 * @param legSerialNumber
	 *            The legSerialNumber to set.
	 */
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}

	/**
	 * @return Returns the operationType.
	 */
	public final String getOperationType() {
		return operationType;
	}

	/**
	 * @param operationType
	 *            The operationType to set.
	 */
	public final void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	/**
	 * @return Returns the transferFlag.
	 */
	public String getTransferFlag() {
		return transferFlag;
	}

	/**
	 * @param transferFlag
	 *            The transferFlag to set.
	 */
	public void setTransferFlag(String transferFlag) {
		this.transferFlag = transferFlag;
	}

	/**
	 * @return Returns the isNewInbound.
	 */
	public boolean isNewInbound() {
		return isNewInbound;
	}

	/**
	 * @param isNewInbound
	 *            The isNewInbound to set.
	 */
	public void setNewInbound(boolean isNewInbound) {
		this.isNewInbound = isNewInbound;
	}

	/**
	 * @return Returns the mailSubclass.
	 */
	public String getMailSubclass() {
		return mailSubclass;
	}

	/**
	 * @param mailSubclass
	 *            The mailSubclass to set.
	 */
	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}

	public String getContainerForInventory() {
		return containerForInventory;
	}

	public void setContainerForInventory(String containerForInventory) {
		this.containerForInventory = containerForInventory;
	}

	/**
	 * @return Returns the containerTypeAtAirport.
	 */
	public String getContainerTypeAtAirport() {
		return containerTypeAtAirport;
	}

	/**
	 * @param containerTypeAtAirport The containerTypeAtAirport to set.
	 */
	public void setContainerTypeAtAirport(String containerTypeAtAirport) {
		this.containerTypeAtAirport = containerTypeAtAirport;
	}

	public String getPaBuiltFlag() {
		return paBuiltFlag;
	}

	public void setPaBuiltFlag(String paBuiltFlag) {
		this.paBuiltFlag = paBuiltFlag;
	}

	public Collection<String> getContainers() {
		return containers;
	}

	public void setContainers(Collection<String> containers) {
		this.containers = containers;
	}

	/*public double getStatedVolume() {
		return statedVolume;
	}

	public void setStatedVolume(double statedVolume) {
		this.statedVolume = statedVolume;
	}
*/
	/**
	 * @return the acceptedPcsToDisplay
	 */
	public int getAcceptedPcsToDisplay() {
		return acceptedPcsToDisplay;
	}

	/**
	 * @param acceptedPcsToDisplay the acceptedPcsToDisplay to set
	 */
	public void setAcceptedPcsToDisplay(int acceptedPcsToDisplay) {
		this.acceptedPcsToDisplay = acceptedPcsToDisplay;
	}
/**
 * 
 * 	Method		:	DespatchDetailsVO.getAcceptedWgtToDisplay
 *	Added by 	:	A-7371 on 21-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@return 
 *	Return type	: 	Measure
 */
	public Measure getAcceptedWgtToDisplay() {
		return acceptedWgtToDisplay;
	}
/**
 * 
 * 	Method		:	DespatchDetailsVO.setAcceptedWgtToDisplay
 *	Added by 	:	A-7371 on 21-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@param acceptedWgtToDisplay 
 *	Return type	: 	void
 */
	public void setAcceptedWgtToDisplay(Measure acceptedWgtToDisplay) {
		this.acceptedWgtToDisplay = acceptedWgtToDisplay;
	}

	/**
	 * @return the acceptedWgtToDisplay
	 */
	/*public double getAcceptedWgtToDisplay() {
		return acceptedWgtToDisplay;
	}

	*//**
	 * @param acceptedWgtToDisplay the acceptedWgtToDisplay to set
	 *//*
	public void setAcceptedWgtToDisplay(double acceptedWgtToDisplay) {
		this.acceptedWgtToDisplay = acceptedWgtToDisplay;
	}*/

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	/**
	 * @return the bookingLastUpdateTime
	 */
	public LocalDate getBookingLastUpdateTime() {
		return bookingLastUpdateTime;
	}

	/**
	 * @param bookingLastUpdateTime the bookingLastUpdateTime to set
	 */
	public void setBookingLastUpdateTime(LocalDate bookingLastUpdateTime) {
		this.bookingLastUpdateTime = bookingLastUpdateTime;
	}

	/**
	 * @return the ubrNumber
	 */
	public String getUbrNumber() {
		return ubrNumber;
	}

	/**
	 * @param ubrNumber the ubrNumber to set
	 */
	public void setUbrNumber(String ubrNumber) {
		this.ubrNumber = ubrNumber;
	}

	/**
	 * @return the bookingFlightDetailLastUpdTime
	 */
	public LocalDate getBookingFlightDetailLastUpdTime() {
		return bookingFlightDetailLastUpdTime;
	}

	/**
	 * @param bookingFlightDetailLastUpdTime the bookingFlightDetailLastUpdTime to set
	 */
	public void setBookingFlightDetailLastUpdTime(
			LocalDate bookingFlightDetailLastUpdTime) {
		this.bookingFlightDetailLastUpdTime = bookingFlightDetailLastUpdTime;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	/*public double getAcceptedVolume() {
		return acceptedVolume;
	}

	public void setAcceptedVolume(double acceptedVolume) {
		this.acceptedVolume = acceptedVolume;
	}*/

	/**
	 * @return the capNotAcceptedStatus
	 */
	public String getCapNotAcceptedStatus() {
		return capNotAcceptedStatus;
	}

	/**
	 * @param capNotAcceptedStatus the capNotAcceptedStatus to set
	 */
	public void setCapNotAcceptedStatus(String capNotAcceptedStatus) {
		this.capNotAcceptedStatus = capNotAcceptedStatus;
	}

	/**
	 * @return the displayLabel
	 */
	public String getDisplayLabel() {
		return displayLabel;
	}

	/**
	 * @param displayLabel the displayLabel to set
	 */
	public void setDisplayLabel(String displayLabel) {
		this.displayLabel = displayLabel;
	}
	public int getTransferredPieces() {
		return transferredPieces;
	}
	/**
	 * 
	 * 	Method		:	DespatchDetailsVO.getTransferredWeight
	 *	Added by 	:	A-7371 on 21-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Measure
	 */
	public Measure getTransferredWeight() {
		return transferredWeight;
	}

	/**
	 * 
	 * 	Method		:	DespatchDetailsVO.setTransferredWeight
	 *	Added by 	:	A-7371 on 21-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param transferredWeight 
	 *	Return type	: 	void
	 */
	public void setTransferredWeight(Measure transferredWeight) {
		this.transferredWeight = transferredWeight;
	}

	public void setTransferredPieces(int transferredPieces) {
		this.transferredPieces = transferredPieces;
	}
	/*public double getTransferredWeight() {
		return transferredWeight;
	}
	public void setTransferredWeight(double transferredWeight) {
		this.transferredWeight = transferredWeight;
	}*/
	public boolean isIstransfermail() {
		return istransfermail;
	}
	public void setIstransfermail(boolean istransfermail) {
		this.istransfermail = istransfermail;
	}
	public int getAlreadyTransferredPieces() {
		return alreadyTransferredPieces;
	}
	/**
	 * 
	 * 	Method		:	DespatchDetailsVO.getAlreadyTransferredWeight
	 *	Added by 	:	A-7371 on 21-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Measure
	 */
	public Measure getAlreadyTransferredWeight() {
		return alreadyTransferredWeight;
	}
/**
 * 
 * 	Method		:	DespatchDetailsVO.setAlreadyTransferredWeight
 *	Added by 	:	A-7371 on 21-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@param alreadyTransferredWeight 
 *	Return type	: 	void
 */
	public void setAlreadyTransferredWeight(Measure alreadyTransferredWeight) {
		this.alreadyTransferredWeight = alreadyTransferredWeight;
	}

	public void setAlreadyTransferredPieces(int alreadyTransferredPieces) {
		this.alreadyTransferredPieces = alreadyTransferredPieces;
	}
	/*public double getAlreadyTransferredWeight() {
		return alreadyTransferredWeight;
	}
	public void setAlreadyTransferredWeight(double alreadyTransferredWeight) {
		this.alreadyTransferredWeight = alreadyTransferredWeight;
	}*/
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
	/**
	 * @return the latestStatus
	 */
	public String getLatestStatus() {
		return latestStatus;
	}
	/**
	 * @param latestStatus the latestStatus to set
	 */
	public void setLatestStatus(String latestStatus) {
		this.latestStatus = latestStatus;
	}
	/**
	 * @return the offloadedBags
	 */
	public int getOffloadedBags() {
		return offloadedBags;
	}
	/**
	 * @param offloadedBags the offloadedBags to set
	 */
	public void setOffloadedBags(int offloadedBags) {
		this.offloadedBags = offloadedBags;
	}
	/**
	 * 
	 * 	Method		:	DespatchDetailsVO.getOffloadedWeight
	 *	Added by 	:	A-7371 on 21-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Measure
	 */
	public Measure getOffloadedWeight() {
		return offloadedWeight;
	}
/**
 * 
 * 	Method		:	DespatchDetailsVO.setOffloadedWeight
 *	Added by 	:	A-7371 on 21-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@param offloadedWeight 
 *	Return type	: 	void
 */
	public void setOffloadedWeight(Measure offloadedWeight) {
		this.offloadedWeight = offloadedWeight;
	}

	/**
	 * @return the offloadedWeight
	 */
	/*public double getOffloadedWeight() {
		return offloadedWeight;
	}
	*//**
	 * @param offloadedWeight the offloadedWeight to set
	 *//*
	public void setOffloadedWeight(double offloadedWeight) {
		this.offloadedWeight = offloadedWeight;
	}*/
	/**
	 * @param delivered the delivered to set
	 */
	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}
	/**
	 * @return the delivered
	 */
	public boolean isDelivered() {
		return delivered;
	}
	/**
	 * @return rsn 
	 */
	public String getRsn() {
		return rsn;
	}
	public void setRsn(String rsn) {
		this.rsn = rsn;
	}

	
	

}
