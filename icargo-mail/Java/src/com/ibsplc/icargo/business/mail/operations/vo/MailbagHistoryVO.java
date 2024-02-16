/*
 * MailbagHistoryVO.java Created on JUN 30, 2016
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
 * @author a-5991
 *
 */
public class MailbagHistoryVO extends AbstractVO {


    private String companyCode; 

    private String dsn;

    private String originExchangeOffice;

    private String destinationExchangeOffice;

    private String mailClass;
    

    private int year;

    private String mailbagId;

    private String mailStatus;

    private int historySequenceNumber;

    private LocalDate scanDate;

    private String scanUser;

    private String flightNumber;
    private int carrierId;
    private long flightSequenceNumber;
    private int segmentSerialNumber;

    private String scannedPort;
    private String containerNumber;
    private String containerType;
    private String carrierCode;
    private LocalDate flightDate;
    private String pou;
    private LocalDate utcScandate;
   //Added to include the DSN PK 
     private String mailCategoryCode;
	private String mailSubclass;

	private LocalDate messageTime;
	
	private String paBuiltFlag;
	
	private String interchangeControlReference;
	
	private LocalDate messageTimeUTC;
	
	private String mailSource;//Added for ICRD-156218
	
    private  LocalDate eventDate;


	private  LocalDate utcEventDate;

	private String residitFailReasonCode;
	private String residitSend;
	private String eventCode;
	private String processedStatus;
	private String mailBoxId;
	private String carditKey;
	private String paCarrierCode;
	private int messageSequenceNumber;	
	private LocalDate secondFlightDate;
	
	//Added for ICRD-205027 starts
	private String rsn;
	//private double weight;
	private Measure weight;//added by A-7371
	//Added for ICRD-205027 ends
	//Added for ICRD-214795 starts
	private LocalDate reqDeliveryTime;
	//Added as a part of ICRD-197419 
	private String mailRemarks;
	//Added for ICRD-245211 starts
	private String masterDocumentNumber;
	private String malClass;
	private String malType;
	private String origin;
	private String destination;
	private int pieces;	
	private String airportCode;
	private String deliveryStatus;
	private String firstFlight;
	private String secondFlight;
	//Added for ICRD-245211 ends
	private String onTimeDelivery;//Added for ICRD-243421
	private String additionalInfo;//Added by a-7871 for ICRD-240184
	private Measure actualWeight;//Added by A-8164 for ICRD-323182
	private String mailSerLvl;//added by A-8353 for ICRD-ICRD-327150
	private long mailSequenceNumber;
	private String consignmentNumber;
	private  LocalDate consignmentDate;
	private LocalDate transportSrvWindow;
	private String poacod;
	private String screeningUser;//Added by A-9498 as part of IASCB-44577
	private String storageUnit;//A-9619 for IASCB-44572
	
	public String getStorageUnit() {
		return storageUnit;
	}
	public void setStorageUnit(String storageUnit) {
		this.storageUnit = storageUnit;
	}
	public String getScreeningUser() {
		return screeningUser;
	}
	public void setScreeningUser(String screeningUser) {
		this.screeningUser = screeningUser;
	}

	private boolean fomDeviationList;//added by A-8353 for IASCB-52564
	private String messageVersion;//Added by A-8527 for IASCB-58918
	
	private String billingStatus;
	private String acceptancePostalContainerNumber;
	/**
	 * @return the additionalInfo
	 */
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	/**
	 * @param additionalInfo the additionalInfo to set
	 */
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
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
	 * 	Getter for mailRemarks 
	 *	Added by : a-7540 on 20-Jul-2017
	 * 	Used for :ICRD-197419 for new field 'remarks'
	 */
	public String getMailRemarks() {
		return mailRemarks;
	}

	/**
	 *  @param mailRemarks the mailRemarks to set
	 * 	Setter for mailRemarks 
	 *	Added by : a-7540 on 20-Jul-2017
	 * 	Used for :ICRD-197419 for new field 'remarks'
	 */
	public void setMailRemarks(String mailRemarks) {
		this.mailRemarks = mailRemarks;
	}

	public LocalDate getEventDate() {
		return eventDate;
	}

	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}

	public LocalDate getUtcEventDate() {
		return utcEventDate;
	}

	public void setUtcEventDate(LocalDate utcEventDate) {
		this.utcEventDate = utcEventDate;
	}

	public String getResiditFailReasonCode() {
		return residitFailReasonCode;
	}

	public void setResiditFailReasonCode(String residitFailReasonCode) {
		this.residitFailReasonCode = residitFailReasonCode;
	}

	public String getResiditSend() {
		return residitSend;
	}

	public void setResiditSend(String residitSend) {
		this.residitSend = residitSend;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getProcessedStatus() {
		return processedStatus;
	}

	public void setProcessedStatus(String processedStatus) {
		this.processedStatus = processedStatus;
	}

	public String getMailBoxId() {
		return mailBoxId;
	}

	public void setMailBoxId(String mailBoxId) {
		this.mailBoxId = mailBoxId;
	}

	public String getCarditKey() {
		return carditKey;
	}

	public void setCarditKey(String carditKey) {
		this.carditKey = carditKey;
	}

	public String getPaCarrierCode() {
		return paCarrierCode;
	}

	public void setPaCarrierCode(String paCarrierCode) {
		this.paCarrierCode = paCarrierCode;
	}

	public int getMessageSequenceNumber() {
		return messageSequenceNumber;
	}

	public void setMessageSequenceNumber(int messageSequenceNumber) {
		this.messageSequenceNumber = messageSequenceNumber;
	}

	
	public LocalDate getUtcScanDate() {
		return utcScandate;
	}
		
	/**
	 * @param utcScandate
	 */
	public void setUtcScanDate(LocalDate utcScandate) {
		this.utcScandate = utcScandate;
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
     * @return Returns the carrierId.
     */
    public int getCarrierId() {
        return carrierId;
    }
    /**
     * @param carrierId The carrierId to set.
     */
    public void setCarrierId(int carrierId) {
        this.carrierId = carrierId;
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
     * @return Returns the flightNumber.
     */
    public String getFlightNumber() {
        return flightNumber;
    }
    /**
     * @param flightNumber The flightNumber to set.
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
     * @param flightSequenceNumber The flightSequenceNumber to set.
     */
    public void setFlightSequenceNumber(long flightSequenceNumber) {
        this.flightSequenceNumber = flightSequenceNumber;
    }
    /**
     * @return Returns the historySequenceNumber.
     */
    public int getHistorySequenceNumber() {
        return historySequenceNumber;
    }
    /**
     * @param historySequenceNumber The historySequenceNumber to set.
     */
    public void setHistorySequenceNumber(int historySequenceNumber) {
        this.historySequenceNumber = historySequenceNumber;
    }
    /**
     * @return Returns the mailbagId.
     */
    public String getMailbagId() {
        return mailbagId;
    }
    /**
     * @param mailbagId The mailbagId to set.
     */
    public void setMailbagId(String mailbagId) {
        this.mailbagId = mailbagId;
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
     * @return Returns the mailStatus.
     */
    public String getMailStatus() {
        return mailStatus;
    }
    /**
     * @param mailStatus The mailStatus to set.
     */
    public void setMailStatus(String mailStatus) {
        this.mailStatus = mailStatus;
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
     * @return Returns the scanDate.
     */
    public LocalDate getScanDate() {
        return scanDate;
    }
    /**
     * @param scanDate The scanDate to set.
     */
    public void setScanDate(LocalDate scanDate) {
        this.scanDate = scanDate;
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
	 * @return Returns the scanUser.
	 */
	public String getScanUser() {
		return scanUser;
	}
	/**
	 * @param scanUser The scanUser to set.
	 */
	public void setScanUser(String scanUser) {
		this.scanUser = scanUser;
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
	 * @return Returns the messageTime.
	 */
	public LocalDate getMessageTime() {
		return messageTime;
	}
	/**
	 * @param messageTime The messageTime to set.
	 */
	public void setMessageTime(LocalDate messageTime) {
		this.messageTime = messageTime;
	}
	public String getPaBuiltFlag() {
		return paBuiltFlag;
	}
	public void setPaBuiltFlag(String paBuiltFlag) {
		this.paBuiltFlag = paBuiltFlag;
	}
	/**
	 * @return Returns the interchangeControlReference.
	 */
	public String getInterchangeControlReference() {
		return interchangeControlReference;
	}
	/**
	 * @param interchangeControlReference The interchangeControlReference to set.
	 */
	public void setInterchangeControlReference(String interchangeControlReference) {
		this.interchangeControlReference = interchangeControlReference;
	}

	public void setMessageTimeUTC(LocalDate messageTimeUTC) {
		this.messageTimeUTC = messageTimeUTC;
		
	}

	/**
	 * 	Getter for messageTimeUTC 
	 *	Added by : A-4803 on 02-Dec-2014
	 * 	Used for :
	 */
	public LocalDate getMessageTimeUTC() {
		return messageTimeUTC;
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
	 * 	Getter for rsn 
	 *	Added by : a-6245 on 27-Jun-2017
	 * 	Used for :
	 */
	public String getRsn() {
		return rsn;
	}

	/**
	 *  @param rsn the rsn to set
	 * 	Setter for rsn 
	 *	Added by : a-6245 on 27-Jun-2017
	 * 	Used for :
	 */
	public void setRsn(String rsn) {
		this.rsn = rsn;
	}

	/**
	 * 	Getter for weight 
	 *	Added by : a-6245 on 27-Jun-2017
	 * 	Used for :
	 */
	/*public double getWeight() {
		return weight;
	}

	*//**
	 *  @param weight the weight to set
	 * 	Setter for weight 
	 *	Added by : a-6245 on 27-Jun-2017
	 * 	Used for :
	 *//*
	public void setWeight(double weight) {
		this.weight = weight;
	}*/
	
	/**
	 * 	Getter for reqDeliveryTime 
	 *	Added by : A-6245 on 31-Jul-2017
	 * 	Used for :
	 */
	public LocalDate getReqDeliveryTime() {
		return reqDeliveryTime;
	}

	/**
	 *  @param reqDeliveryTime the reqDeliveryTime to set
	 * 	Setter for reqDeliveryTime 
	 *	Added by : A-6245 on 31-Jul-2017
	 * 	Used for :
	 */
	public void setReqDeliveryTime(LocalDate reqDeliveryTime) {
		this.reqDeliveryTime = reqDeliveryTime;
	}
	/**
	 * @return the masterDocumentNumber
	 */
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}
	/**
	 * @param masterDocumentNumber the masterDocumentNumber to set
	 */
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}
	/**
	 * @return the malClass
	 */
	public String getMalClass() {
		return malClass;
	}
	/**
	 * @param malClass the malClass to set
	 */
	public void setMalClass(String malClass) {
		this.malClass = malClass;
	}
	/**
	 * @return the malType
	 */
	public String getMalType() {
		return malType;
	}
	/**
	 * @param malType the malType to set
	 */
	public void setMalType(String malType) {
		this.malType = malType;
	}
	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}
	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * @return the pieces
	 */
	public int getPieces() {
		return pieces;
	}
	/**
	 * @param pieces the pieces to set
	 */
	public void setPieces(int pieces) {
		this.pieces = pieces;
	}
	/**
	 * @return the airportCode
	 */
	public String getAirportCode() {
		return airportCode;
	}
	/**
	 * @param airportCode the airportCode to set
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	/**
	 * @return the deliveryStatus
	 */
	public String getDeliveryStatus() {
		return deliveryStatus;
	}
	/**
	 * @param deliveryStatus the deliveryStatus to set
	 */
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	/**
	 * @return the firstFlight
	 */
	public String getFirstFlight() {
		return firstFlight;
	}
	/**
	 * @param firstFlight the firstFlight to set
	 */
	public void setFirstFlight(String firstFlight) {
		this.firstFlight = firstFlight;
	}
	/**
	 * @return the secondFlight
	 */
	public String getSecondFlight() {
		return secondFlight;
	}
	/**
	 * @param secondFlight the secondFlight to set
	 */
	public void setSecondFlight(String secondFlight) {
		this.secondFlight = secondFlight;
	}
	/**
	 * @return the onTimeDelivery
	 */
	public String getOnTimeDelivery() {
		return onTimeDelivery;
	}
	/**
	 * @param onTimeDelivery the onTimeDelivery to set
	 */
	public void setOnTimeDelivery(String onTimeDelivery) {
		this.onTimeDelivery = onTimeDelivery;
	}
	public LocalDate getSecondFlightDate() {
		return secondFlightDate;
	}
	public void setSecondFlightDate(LocalDate secondFlightDate) {
		this.secondFlightDate = secondFlightDate;
	}
	public Measure getActualWeight() {
		return actualWeight;
	}
	public void setActualWeight(Measure actualWeight) {
		this.actualWeight = actualWeight;
	}
	
	/**
	 * @author A-8353
	 * @return String
	 */
	public String getMailSerLvl() {
		return mailSerLvl;
	}
	
	/**
	 * @author A-8353
	 * @param mailSerLvl
	 */
	public void setMailSerLvl(String mailSerLvl) {
		this.mailSerLvl = mailSerLvl;
	}
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	public String getConsignmentNumber() {
		return consignmentNumber;
	}
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}
	public LocalDate getConsignmentDate() {
		return consignmentDate;
	}
	public void setConsignmentDate(LocalDate consignmentDate) {
		this.consignmentDate = consignmentDate;
	}
	public LocalDate getTransportSrvWindow() {
		return transportSrvWindow;
	}
	public void setTransportSrvWindow(LocalDate transportSrvWindow) {
		this.transportSrvWindow = transportSrvWindow;
	}
	public String getPoacod() {
		return poacod;
	}
	public void setPoacod(String poacod) {
		this.poacod = poacod;
	}
	public boolean isFomDeviationList() {
		return fomDeviationList;
	}
	public void setFomDeviationList(boolean fomDeviationList) {
		this.fomDeviationList = fomDeviationList;
	}
	public String getMessageVersion() {
		return messageVersion;
	}
	public void setMessageVersion(String messageVersion) {
		this.messageVersion = messageVersion;
	}
	public String getBillingStatus() {
		return billingStatus;
	}
	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}
	public String getAcceptancePostalContainerNumber() {
		return acceptancePostalContainerNumber;
	}
	public void setAcceptancePostalContainerNumber(String acceptancePostalContainerNumber) {
		this.acceptancePostalContainerNumber = acceptancePostalContainerNumber;
	}
	
	
}
