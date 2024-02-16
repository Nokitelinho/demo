/*
 * DSNVO.java Created on JUN 30, 2016
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
 * @author a-5991
 *
 */

public class DSNVO extends AbstractVO {
    
   /**
    * The ModuleName
    */
    public static final String MODULE_NAME = "mail";
    /**
     * The SubModuleName
     */
    public static final String SUBMODULE_NAME = "operations";
    /**
     * The EntityName
     */
    public static final String ENTITY_NAME = "mail.operations.DSN";
    
    private String companyCode; 
    private String dsn;
    private String originExchangeOffice;
    private String destinationExchangeOffice;
    private String mailClass;
    private int year;
    
    private String mailCategoryCode;
    private String mailSubclass;
    
    private int bags;
   // private double weight;
    private Measure weight;//added by A-7371
    
    /*
     * For Cargo operations
     */
    private int shipmentCount;
    //private double shipmentWeight;
    private Measure shipmentWeight;//added by A-7371
   // private double shipmentVolume;
    private Measure shipmentVolume;//added by A-7371
    
    private int prevBagCount;
   // private double prevBagWeight;
    private Measure prevBagWeight;//added by A-7371
    
    private int statedBags;
    //private double statedWeight;
    private Measure statedWeight;//added by A-7371
    private int prevStatedBags;
    //private double prevStatedWeight;
    private Measure prevStatedWeight;//added by A-7371
    
    private Collection<MailbagVO> mailbags;
    
    private Collection<DSNAtAirportVO> dsnAtAirports;
    
    private String pltEnableFlag;
       
     private String operationFlag; 
     
     private String containerType;
     
     private String acceptanceFlag; 
     
    
     
     private int segmentSerialNumber;
     
     private String pou;
     
     private String destination;    
     
     
     private String carrierCode;
     
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
    
    /* AWB Details */
    private int documentOwnerIdentifier;
    private String masterDocumentNumber;
    private int duplicateNumber;
    private int sequenceNumber;
    private String documentOwnerCode;
    
    private String shipmentCode;
    private String shipmentDescription;

    //for AWB manifest
    private String origin;
    
    private String transferFlag;
    
    private Collection<String> dsnContainers; 
    
    private LocalDate dsnUldSegLastUpdateTime;
        
    private String mailbagId;
    
    private String upliftAirport;
    
    private String consignmentNumber;
    
    /*added for Auto attach AWB */
    private String containerNumber;
    
    /*Added by RENO for MRA integration
     *CR : AirNZ865 
     */
    private String routingAvl;
    
    /*
     * Added For CR : AirNZ 404 : Mail Allocation
     */
    private String pol;
    private int carrierId;
    private String flightNumber;
    private long flightSequenceNumber;
    private int legSerialNumber;
    private double mailrate;
    private String currencyCode;
    private double chargeInBase;
    private double chargeInUSD;
    private String ubrNumber;
    private LocalDate bookingLastUpdateTime;
    private LocalDate bookingFlightDetailLastUpdTime;

    /*
     * FOR ANZ BUG 35717
     */
   // private double acceptedVolume;
    private Measure acceptedVolume;//added by A-7371
    //private double statedVolume;
    private Measure statedVolume;//added by A-7371
    /*
     * For anz bug 50584
     */
    private String csgDocNum;
    private String paCode;
    //Added for CR ICRD-2878
    private int csgSeqNum;
    private LocalDate consignmentDate;
    private LocalDate acceptedDate;
    private LocalDate receivedDate;
    
    private String currentPort;
    //Added by A-4810 for bug-fix-icrd-9151.
    private int transferredPieces;
   // private double transferredWeight;
    private Measure transferredWeight;//added by A-7371
    private String remarks;
    private long mailSequenceNumber; 
    
    private  String awbNumber;
    
    private LocalDate flightDate;   //Added by A-8164 for ICRD-333302
    private String acceptanceStatus;
    private LocalDate reqDeliveryTime;
    
    private String status;
    private String scannedUser;
    
    public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    /**
     * 
     * @return shipmentVolume
     */
	public Measure getShipmentVolume() {
		return shipmentVolume;
	}
	/**
	 * 
	 * @param shipmentVolume
	 */
	public void setShipmentVolume(Measure shipmentVolume) {
		this.shipmentVolume = shipmentVolume;
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
     * @return Returns the bags.
     */
    public int getBags() {
        return bags;
    }
    /**
     * @param bags The bags to set.
     */
    public void setBags(int bags) {
        this.bags = bags;
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
     * @return Returns the destinationExchangeOffice.
     */
    public String getDestinationExchangeOffice() {
        return destinationExchangeOffice;
    }
    /**
     * @param destinationExchangeOffice The destinationExchangeOffice to set.
     */
    public void setDestinationExchangeOffice(String destinationExchangeOffice) {
        this.destinationExchangeOffice = destinationExchangeOffice;
    }
    /**
     * @return Returns the dsn.
     */
    public String getDsn() {
        return dsn;
    }
    /**
     * @param dsn The dsn to set.
     */
    public void setDsn(String dsn) {
        this.dsn = dsn;
    }

    /**
     * @return Returns the mailbags.
     */
    public Collection<MailbagVO> getMailbags() {
        return mailbags;
    }
    /**
     * @param mailbags The mailbags to set.
     */
    public void setMailbags(Collection<MailbagVO> mailbags) {
        this.mailbags = mailbags;
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
     * @return Returns the originExchangeOffice.
     */
    public String getOriginExchangeOffice() {
        return originExchangeOffice;
    }
    /**
     * @param originExchangeOffice The originExchangeOffice to set.
     */
    public void setOriginExchangeOffice(String originExchangeOffice) {
        this.originExchangeOffice = originExchangeOffice;
    }
    /**
     * @return Returns the pltEnableFlag.
     */
    public String getPltEnableFlag() {
        return pltEnableFlag;
    }
    /**
     * @param pltEnableFlag The pltEnableFlag to set.
     */
    public void setPltEnableFlag(String pltEnableFlag) {
        this.pltEnableFlag = pltEnableFlag;
    }
/**
 * 
 * 	Method		:	DSNVO.getWeight
 *	Added by 	:	A-7371 on 22-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@return 
 *	Return type	: 	Measure
 */
    public Measure getWeight() {
		return weight;
	}
    /**
     * 
     * 	Method		:	DSNVO.setWeight
     *	Added by 	:	A-7371 on 22-Aug-2017
     * 	Used for 	:
     *	Parameters	:	@param weight 
     *	Return type	: 	void
     */
	public void setWeight(Measure weight) {
		this.weight = weight;
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
     * @return Returns the year.
     */
    public int getYear() {
        return year;
    }
    /**
     * @param year The year to set.
     */
    public void setYear(int year) {
        this.year = year;
    }
    /**
     * @return Returns the dsnAtAirports.
     */
    public Collection<DSNAtAirportVO> getDsnAtAirports() {
        return dsnAtAirports;
    }
    /**
     * @param dsnAtAirports The dsnAtAirports to set.
     */
    public void setDsnAtAirports(Collection<DSNAtAirportVO> dsnAtAirports) {
        this.dsnAtAirports = dsnAtAirports;
    }
	/**
	 * @return statedBags
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
	 * 
	 * 	Method		:	DSNVO.getStatedWeight
	 *	Added by 	:	A-7371 on 22-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Measure
	 */
	public Measure getStatedWeight() {
		return statedWeight;
	}
	/**
	 * 
	 * 	Method		:	DSNVO.setStatedWeight
	 *	Added by 	:	A-7371 on 22-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param statedWeight 
	 *	Return type	: 	void
	 */
	public void setStatedWeight(Measure statedWeight) {
		this.statedWeight = statedWeight;
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
	 * @return Returns the containerType.
	 */
	public String getContainerType() {
		return containerType;
	}
	/**
	 * @param containerType The containerType to set.
	 */
	public void setContainerType(String containerType) {
		this.containerType = containerType;
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
	 * @return Returns the pou.
	 */
	public String getPou() {
		return pou;
	}
	/**
	 * @param pou The pou to set.
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}
	/**
	 * @return Returns the segmentSerialNumber.
	 */
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}
	/**
	 * @param segmentSerialNumber The segmentSerialNumber to set.
	 */
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}
	
    /**
     * @return Returns the prevBagCount.
     */
    public int getPrevBagCount() {
        return prevBagCount;
    }
    /**
     * @param prevBagCount The prevBagCount to set.
     */
    public void setPrevBagCount(int prevBagCount) {
        this.prevBagCount = prevBagCount;
    }
    /**
     * 
     * 	Method		:	DSNVO.getPrevBagWeight
     *	Added by 	:	A-7371 on 22-Aug-2017
     * 	Used for 	:
     *	Parameters	:	@return 
     *	Return type	: 	Measure
     */
    public Measure getPrevBagWeight() {
		return prevBagWeight;
	}
    /**
     * 
     * 	Method		:	DSNVO.setPrevBagWeight
     *	Added by 	:	A-7371 on 22-Aug-2017
     * 	Used for 	:
     *	Parameters	:	@param prevBagWeight 
     *	Return type	: 	void
     */
	public void setPrevBagWeight(Measure prevBagWeight) {
		this.prevBagWeight = prevBagWeight;
	}
	/**
     * @return Returns the prevBagWeight.
     */
   /* public double getPrevBagWeight() {
        return prevBagWeight;
    }
    *//**
     * @param prevBagWeight The prevBagWeight to set.
     *//*
    public void setPrevBagWeight(double prevBagWeight) {
        this.prevBagWeight = prevBagWeight;
    }*/
    /**
     * @return Returns the prevStatedBags.
     */
    public int getPrevStatedBags() {
        return prevStatedBags;
    }
    /**
     * @param prevStatedBags The prevStatedBags to set.
     */
    public void setPrevStatedBags(int prevStatedBags) {
        this.prevStatedBags = prevStatedBags;
    }
    /**
     * 
     * 	Method		:	DSNVO.getPrevStatedWeight
     *	Added by 	:	A-7371 on 22-Aug-2017
     * 	Used for 	:
     *	Parameters	:	@return 
     *	Return type	: 	Measure
     */
    public Measure getPrevStatedWeight() {
		return prevStatedWeight;
	}
    /**
     * 
     * 	Method		:	DSNVO.setPrevStatedWeight
     *	Added by 	:	A-7371 on 22-Aug-2017
     * 	Used for 	:
     *	Parameters	:	@param prevStatedWeight 
     *	Return type	: 	void
     */
	public void setPrevStatedWeight(Measure prevStatedWeight) {
		this.prevStatedWeight = prevStatedWeight;
	}
	/**
     * @return Returns the prevStatedWeight.
     */
    /*public double getPrevStatedWeight() {
        return prevStatedWeight;
    }
    *//**
     * @param prevStatedWeight The prevStatedWeight to set.
     *//*
    public void setPrevStatedWeight(double prevStatedWeight) {
        this.prevStatedWeight = prevStatedWeight;
    }*/
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
     * 
     * 	Method		:	DSNVO.getDeliveredWeight
     *	Added by 	:	A-7371 on 22-Aug-2017
     * 	Used for 	:
     *	Parameters	:	@return 
     *	Return type	: 	Measure
     */
    public Measure getDeliveredWeight() {
		return deliveredWeight;
	}
    /**
     * 
     * 	Method		:	DSNVO.setDeliveredWeight
     *	Added by 	:	A-7371 on 22-Aug-2017
     * 	Used for 	:
     *	Parameters	:	@param deliveredWeight 
     *	Return type	: 	void
     */
	public void setDeliveredWeight(Measure deliveredWeight) {
		this.deliveredWeight = deliveredWeight;
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
    }*/
    /**
     * @return Returns the prevDeliveredBags.
     */
    public int getPrevDeliveredBags() {
        return prevDeliveredBags;
    }
    /**
     * @param prevDeliveredBags The prevDeliveredBags to set.
     */
    public void setPrevDeliveredBags(int prevDeliveredBags) {
        this.prevDeliveredBags = prevDeliveredBags;
    }
    /**
     * 
     * 	Method		:	DSNVO.getPrevDeliveredWeight
     *	Added by 	:	A-7371 on 22-Aug-2017
     * 	Used for 	:
     *	Parameters	:	@return 
     *	Return type	: 	Measure
     */
    public Measure getPrevDeliveredWeight() {
		return prevDeliveredWeight;
	}
    /**
     * 
     * 	Method		:	DSNVO.setPrevDeliveredWeight
     *	Added by 	:	A-7371 on 22-Aug-2017
     * 	Used for 	:
     *	Parameters	:	@param prevDeliveredWeight 
     *	Return type	: 	void
     */
	public void setPrevDeliveredWeight(Measure prevDeliveredWeight) {
		this.prevDeliveredWeight = prevDeliveredWeight;
	}
	/**
     * @return Returns the prevDeliveredWeight.
     */
   /* public double getPrevDeliveredWeight() {
        return prevDeliveredWeight;
    }
    *//**
     * @param prevDeliveredWeight The prevDeliveredWeight to set.
     *//*
    public void setPrevDeliveredWeight(double prevDeliveredWeight) {
        this.prevDeliveredWeight = prevDeliveredWeight;
    }*/
    /**
     * @return Returns the prevReceivedBags.
     */
    public int getPrevReceivedBags() {
        return prevReceivedBags;
    }
    /**
     * @param prevReceivedBags The prevReceivedBags to set.
     */
    public void setPrevReceivedBags(int prevReceivedBags) {
        this.prevReceivedBags = prevReceivedBags;
    }
    /**
     * 
     * 	Method		:	DSNVO.getPrevReceivedWeight
     *	Added by 	:	A-7371 on 22-Aug-2017
     * 	Used for 	:
     *	Parameters	:	@return 
     *	Return type	: 	Measure
     */
    public Measure getPrevReceivedWeight() {
		return prevReceivedWeight;
	}
    /**
     * 
     * 	Method		:	DSNVO.setPrevReceivedWeight
     *	Added by 	:	A-7371 on 22-Aug-2017
     * 	Used for 	:
     *	Parameters	:	@param prevReceivedWeight 
     *	Return type	: 	void
     */
	public void setPrevReceivedWeight(Measure prevReceivedWeight) {
		this.prevReceivedWeight = prevReceivedWeight;
	}
	/**
     * @return Returns the prevReceivedWeight.
     */
   /* public double getPrevReceivedWeight() {
        return prevReceivedWeight;
    }
    *//**
     * @param prevReceivedWeight The prevReceivedWeight to set.
     *//*
    public void setPrevReceivedWeight(double prevReceivedWeight) {
        this.prevReceivedWeight = prevReceivedWeight;
    }*/
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
     * 
     * 	Method		:	DSNVO.getReceivedWeight
     *	Added by 	:	A-7371 on 22-Aug-2017
     * 	Used for 	:
     *	Parameters	:	@return 
     *	Return type	: 	Measure
     */
    public Measure getReceivedWeight() {
		return receivedWeight;
	}
    /**
     * 
     * 	Method		:	DSNVO.setReceivedWeight
     *	Added by 	:	A-7371 on 22-Aug-2017
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
     * @param receivedWeight The receivedWeight to set.
     *//*
    *//**
     * @param receivedWeight
     *//*
    public void setReceivedWeight(double receivedWeight) {
        this.receivedWeight = receivedWeight;
    }*/
    /**
     * 
     * @return Returns the DocumentOwnerCode
     */
	public String getDocumentOwnerCode() {
		return documentOwnerCode;
	}
	/**
	 * @param documentOwnerCode The DocumentOwnerCode to set
	 */
	
	public void setDocumentOwnerCode(String documentOwnerCode) {
		this.documentOwnerCode = documentOwnerCode;
	}
	/**
	 * 
	 * @return Returns the DocumentOwnerIdentifier
	 */
	public int getDocumentOwnerIdentifier() {
		return documentOwnerIdentifier;
	}
	/**
	 * 
	 * @param documentOwnerIdentifier The DocumentOwnerIdentifier to set
	 */
	public void setDocumentOwnerIdentifier(int documentOwnerIdentifier) {
		this.documentOwnerIdentifier = documentOwnerIdentifier;
	}
	/**
	 * 
	 * @return Returns the DuplicateNumber
	 */
	public int getDuplicateNumber() {
		return duplicateNumber;
	}
	/**
	 * 
	 * @param duplicateNumber The DuplicateNumber to set
	 */
	public void setDuplicateNumber(int duplicateNumber) {
		this.duplicateNumber = duplicateNumber;
	}
	/**
	 * 
	 * @return Returns the MasterDocumentNumber
	 */
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}
	/**
	 * 
	 * @param masterDocumentNumber The MasterDocumentNumber to be Set
	 */
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}
	/**
	 * 
	 * @return Returns the SequenceNumber
	 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	/**
	 * 
	 * @param sequenceNumber The Sequence to set
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
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
	 * @return Returns the origin.
	 */
	public String getOrigin() {
		return origin;
	}
	/**
	 * @param origin The origin to set.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

/**
	 * @return Returns the dsnContainers.
	 */
	public Collection<String> getDsnContainers() {
		return dsnContainers;
	}
	/**
	 * @param dsnContainers The dsnContainers to set.
	 */
	public void setDsnContainers(Collection<String> dsnContainers) {
		this.dsnContainers = dsnContainers;
	}
	public String getTransferFlag() {
		return transferFlag;
	}
	public void setTransferFlag(String transferFlag) {
		this.transferFlag = transferFlag;
	}
	public LocalDate getDsnUldSegLastUpdateTime() {
		return dsnUldSegLastUpdateTime;
	}
	public void setDsnUldSegLastUpdateTime(LocalDate dsnUldSegLastUpdateTime) {
		this.dsnUldSegLastUpdateTime = dsnUldSegLastUpdateTime;
	}
	/**
	 * @return the routingAvl
	 */
	public String getRoutingAvl() {
		return routingAvl;
	}
	/**
	 * @param routingAvl the routingAvl to set
	 */
	public void setRoutingAvl(String routingAvl) {
		this.routingAvl = routingAvl;
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
	 * @return the mailrate
	 */
	public double getMailrate() {
		return mailrate;
	}
	/**
	 * @param mailrate the mailrate to set
	 */
	public void setMailrate(double mailrate) {
		this.mailrate = mailrate;
	}
	/**
	 * @return the chargeInBase
	 */
	public double getChargeInBase() {
		return chargeInBase;
	}
	/**
	 * @param chargeInBase the chargeInBase to set
	 */
	public void setChargeInBase(double chargeInBase) {
		this.chargeInBase = chargeInBase;
	}
	/**
	 * @return the chargeInUSD
	 */
	public double getChargeInUSD() {
		return chargeInUSD;
	}
	/**
	 * @param chargeInUSD the chargeInUSD to set
	 */
	public void setChargeInUSD(double chargeInUSD) {
		this.chargeInUSD = chargeInUSD;
	}
	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}
	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	/**
	 * @return the carrierId
	 */
	public int getCarrierId() {
		return carrierId;
	}
	/**
	 * @param carrierId the carrierId to set
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}
	/**
	 * @return the flightNumber
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
	/**
	 * @param flightNumber the flightNumber to set
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	/**
	 * @return the flightSequenceNumber
	 */
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}
	/**
	 * @param flightSequenceNumber the flightSequenceNumber to set
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}
	/**
	 * @return the pol
	 */
	public String getPol() {
		return pol;
	}
	/**
	 * @param pol the pol to set
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}
	/**
	 * @return the legSerialNumber
	 */
	public int getLegSerialNumber() {
		return legSerialNumber;
	}
	/**
	 * @param legSerialNumber the legSerialNumber to set
	 */
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
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
	/**
	 * @return the acceptedVolume
	 */
	/*public double getAcceptedVolume() {
		return acceptedVolume;
	}
	*//**
	 * @param acceptedVolume the acceptedVolume to set
	 *//*
	public void setAcceptedVolume(double acceptedVolume) {
		this.acceptedVolume = acceptedVolume;
	}
	*//**
	 * @return the statedVolume
	 *//*
	public double getStatedVolume() {
		return statedVolume;
	}
	*//**
	 * @param statedVolume the statedVolume to set
	 *//*
	public void setStatedVolume(double statedVolume) {
		this.statedVolume = statedVolume;
	}*/
	/**
	 * @return the shipmentCode
	 */
	public String getShipmentCode() {
		return shipmentCode;
	}
	/**
	 * @param shipmentCode the shipmentCode to set
	 */
	public void setShipmentCode(String shipmentCode) {
		this.shipmentCode = shipmentCode;
	}
	/**
	 * @return the shipmentDescription
	 */
	public String getShipmentDescription() {
		return shipmentDescription;
	}
	/**
	 * @param shipmentDescription the shipmentDescription to set
	 */
	public void setShipmentDescription(String shipmentDescription) {
		this.shipmentDescription = shipmentDescription;
	}
	/**
	 * @return the shipmentCount
	 */
	public int getShipmentCount() {
		return shipmentCount;
	}
	/**
	 * @param shipmentCount the shipmentCount to set
	 */
	public void setShipmentCount(int shipmentCount) {
		this.shipmentCount = shipmentCount;
	}
	/**
	 * 
	 * 	Method		:	DSNVO.getShipmentWeight
	 *	Added by 	:	A-7371 on 22-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Measure
	 */
	public Measure getShipmentWeight() {
		return shipmentWeight;
	}
	/**
	 * 
	 * 	Method		:	DSNVO.setShipmentWeight
	 *	Added by 	:	A-7371 on 22-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param shipmentWeight 
	 *	Return type	: 	void
	 */
	public void setShipmentWeight(Measure shipmentWeight) {
		this.shipmentWeight = shipmentWeight;
	}
	/**
	 * @return the shipmentWeight
	 */
	/*public double getShipmentWeight() {
		return shipmentWeight;
	}
	*//**
	 * @param shipmentWeight the shipmentWeight to set
	 *//*
	public void setShipmentWeight(double shipmentWeight) {
		this.shipmentWeight = shipmentWeight;
	}*/
	/**
	 * @return the shipmentVolume
	 */
	/*public double getShipmentVolume() {
		return shipmentVolume;
	}
	*//**
	 * @param shipmentVolume the shipmentVolume to set
	 *//*
	public void setShipmentVolume(double shipmentVolume) {
		this.shipmentVolume = shipmentVolume;
	}*/
	/**
	 * @return Returns the csgDocNum.
	 */
	public String getCsgDocNum() {
		return csgDocNum;
	}
	/**
	 * @param csgDocNum The csgDocNum to set.
	 */
	public void setCsgDocNum(String csgDocNum) {
		this.csgDocNum = csgDocNum;
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
	 * @return the csgSeqNum
	 */
	public int getCsgSeqNum() {
		return csgSeqNum;
	}
	/**
	 * @param csgSeqNum the csgSeqNum to set
	 */
	public void setCsgSeqNum(int csgSeqNum) {
		this.csgSeqNum = csgSeqNum;
	}
	/**
	 * @return the consignmentDate
	 */
	public LocalDate getConsignmentDate() {
		return consignmentDate;
	}
	/**
	 * @param consignmentDate the consignmentDate to set
	 */
	public void setConsignmentDate(LocalDate consignmentDate) {
		this.consignmentDate = consignmentDate;
	}
	/**
	 * @return the acceptedDate
	 */
	public LocalDate getAcceptedDate() {
		return acceptedDate;
	}
	/**
	 * @param acceptedDate the acceptedDate to set
	 */
	public void setAcceptedDate(LocalDate acceptedDate) {
		this.acceptedDate = acceptedDate;
	}
	/**
	 * 
	 * @return receivedDate
	 */
	public LocalDate getReceivedDate() {
		return receivedDate;
	}
	/**
	 * 
	 * @param receivedDate the receivedDate to set
	 */
	public void setReceivedDate(LocalDate receivedDate) {
		this.receivedDate = receivedDate;
	}
	public String getCurrentPort() {
		return currentPort;
	}
	public void setCurrentPort(String currentPort) {
		this.currentPort = currentPort;
	}
	public int getTransferredPieces() {
		return transferredPieces;
	}
	/**
	 * 
	 * 	Method		:	DSNVO.getTransferredWeight
	 *	Added by 	:	A-7371 on 22-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Measure
	 */
	public Measure getTransferredWeight() {
		return transferredWeight;
	}
	/**
	 * 
	 * 	Method		:	DSNVO.setTransferredWeight
	 *	Added by 	:	A-7371 on 22-Aug-2017
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
	 * 	Getter for mailSequenceNumber 
	 *	Added by : A-4809 on Sep 2, 2016
	 * 	Used for :
	 */
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	/**
	 *  @param mailSequenceNumber the mailSequenceNumber to set
	 * 	Setter for mailSequenceNumber 
	 *	Added by : A-4809 on Sep 2, 2016
	 * 	Used for :
	 */
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	/**
	 * 	Getter for awbNumber 
	 *	Added by : A-8061 on 09-Nov-2020
	 * 	Used for :
	 */
	public String getAwbNumber() {
		return awbNumber;
	}
	/**
	 *  @param awbNumber the awbNumber to set
	 * 	Setter for awbNumber 
	 *	Added by : A-8061 on 09-Nov-2020
	 * 	Used for :
	 */
	public void setAwbNumber(String awbNumber) {
		this.awbNumber = awbNumber;
	}
	/**
	 * 	Getter for mailbagId 
	 *	Added by : A-8061 on 10-Nov-2020
	 * 	Used for :
	 */
	public String getMailbagId() {
		return mailbagId;
	}
	/**
	 *  @param mailbagId the mailbagId to set
	 * 	Setter for mailbagId 
	 *	Added by : A-8061 on 10-Nov-2020
	 * 	Used for :
	 */
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}
	/**
	 * 	Getter for upliftAirport 
	 *	Added by : A-8061 on 10-Nov-2020
	 * 	Used for :
	 */
	public String getUpliftAirport() {
		return upliftAirport;
	}
	/**
	 *  @param upliftAirport the upliftAirport to set
	 * 	Setter for upliftAirport 
	 *	Added by : A-8061 on 10-Nov-2020
	 * 	Used for :
	 */
	public void setUpliftAirport(String upliftAirport) {
		this.upliftAirport = upliftAirport;
	}
	/**
	 * 	Getter for consignmentNumber 
	 *	Added by : A-8061 on 10-Nov-2020
	 * 	Used for :
	 */
	public String getConsignmentNumber() {
		return consignmentNumber;
	}
	/**
	 *  @param consignmentNumber the consignmentNumber to set
	 * 	Setter for consignmentNumber 
	 *	Added by : A-8061 on 10-Nov-2020
	 * 	Used for :
	 */
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}

	public LocalDate getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}
	public String getAcceptanceStatus() {
		return acceptanceStatus;
	}
	public void setAcceptanceStatus(String acceptanceStatus) {
		this.acceptanceStatus = acceptanceStatus;
	}
	public LocalDate getReqDeliveryTime() {
		return reqDeliveryTime;
	}
	public void setReqDeliveryTime(LocalDate reqDeliveryTime) {
		this.reqDeliveryTime = reqDeliveryTime;
	}
	public String getScannedUser() {
		return scannedUser;
	}
	public void setScannedUser(String scannedUser) {
		this.scannedUser = scannedUser;
	}
	

}
