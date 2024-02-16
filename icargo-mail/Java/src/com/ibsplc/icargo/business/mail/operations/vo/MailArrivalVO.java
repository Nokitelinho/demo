/*
 * MailArrivalVO.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-5991
 *
 */
public class MailArrivalVO extends AbstractVO {
    private String companyCode;

    private String airportCode;

    private String flightNumber;

    private String flightCarrierCode ;

    private int carrierId;

    private long flightSequenceNumber;

    private int legSerialNumber;

    private LocalDate arrivalDate ;

    private String arrivedUser;

    private boolean isScanned;

    private String flightStatus;

    private String mailStatus;
	private String transferCarrier;
	private int transferCarrierId;
	private String arrivalPA;
	private String mailSource;
	private boolean isDeliveryCheckNeeded;

	private LocalDate scanDate ;

	private String ownAirlineCode ;

	private int ownAirlineId ;

	private boolean isPartialDelivery;

	/**
	 * This attribute is implemented to incorporate
	 * Delivery Functionality in HHT Arrival Screen
	 */
	private boolean isDeliveryNeeded;
	private int segmentSerialNumber;
	private LocalDate flightDate;
    private String pou;
    private String pol;
    private String route;
    private String changeFlightFlag;
    private boolean isOfflineJob;//Added for ICRD-156218
    private boolean mailVOUpdated;
    private String isFromTruck;//Added by a-7871 for ICRD-240184
    /**
	 * @return the isFromTruck
	 */
    
  //Added for MailInbound screen begins
    private String aircraftType;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String arrivalTimeType;
    private String operationalStatus;
    private String gateInfo;
    private String manifestInfo;
    private String recievedInfo;
    private Double containerCount;
    private Double totalWeight;
    private Double mailCount;
    private int defaultPageSize;
    private int pageNumber;
    private boolean mailFlightChecked;//Added by A-8464 for ICRD-328502
  //Added for MailInbound screen ends
    private String mailDataSource;
    private boolean flightBypassFlag;
    private String paBuiltFlag;
    //added by A-7815 as part of IASCB-36551
    private String operatingReference;
    private String messageVersion;//Added by A-8527 for IASCB-58918
    
    private String deliveryRemarks;
  //added by a-8952 as part of CR ICRD-327979 
    private int offset;
    private boolean isArrivalAndDeliveryMarkedTogether;
  	private LocalDate actualArrivalTime;
  	private String legDestination;
  	
  	private String mailCompanyCode;
  	private String  onlineAirportParam;
	private Collection<EmbargoDetailsVO> embargoDetails;
    private String flightType;
    private boolean isFoundResditSent;
	public String getOnlineAirportParam() {
		return onlineAirportParam;
	}
	public void setOnlineAirportParam(String onlineAirportParam) {
		this.onlineAirportParam = onlineAirportParam;
	}
	public String getIsFromTruck() {
		return isFromTruck;
	}

	/**
	 * @param isFromTruck the isFromTruck to set
	 */
	public void setIsFromTruck(String isFromTruck) {
		this.isFromTruck = isFromTruck;
	}
    /*
     * Collection
     */
    private Collection<ContainerDetailsVO> containerDetails;
    private boolean flightChange;
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
     * @return Returns the containerDetails.
     */
    public Collection<ContainerDetailsVO> getContainerDetails() {
        return containerDetails;
    }

    /**
     * @param containerDetails The containerDetails to set.
     */
    public void setContainerDetails(Collection<ContainerDetailsVO> containerDetails) {
        this.containerDetails = containerDetails;
    }

    /**
     * @return Returns the departureDate.
     */
    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    /**
     * @param departureDate The departureDate to set.
     */
    public void setArrivalDate(LocalDate departureDate) {
        this.arrivalDate = departureDate;
    }

    /**
     * @return Returns the flightCarrierCode.
     */
    public String getFlightCarrierCode() {
        return flightCarrierCode;
    }

    /**
     * @param flightCarrierCode The flightCarrierCode to set.
     */
    public void setFlightCarrierCode(String flightCarrierCode) {
        this.flightCarrierCode = flightCarrierCode;
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
	 * @return Returns the legSerialNumber.
	 */
	public int getLegSerialNumber() {
		return this.legSerialNumber;
	}

	/**
	 * @param legSerialNumber The legSerialNumber to set.
	 */
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}

    /**
     * @return Returns the arrivedUser.
     */
    public String getArrivedUser() {
        return arrivedUser;
    }

    /**
     * @param arrivedUser The arrivedUser to set.
     */
    public void setArrivedUser(String arrivedUser) {
        this.arrivedUser = arrivedUser;
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
	 * @return Returns the arrivalPA.
	 */
	public String getArrivalPA() {
		return this.arrivalPA;
	}

	/**
	 * @param arrivalPA The arrivalPA to set.
	 */
	public void setArrivalPA(String arrivalPA) {
		this.arrivalPA = arrivalPA;
	}

	/**
	 * @return Returns the mailStatus.
	 */
	public String getMailStatus() {
		return this.mailStatus;
	}

	/**
	 * @param mailStatus The mailStatus to set.
	 */
	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}

	/**
	 * @return Returns the transferCarrier.
	 */
	public String getTransferCarrier() {
		return this.transferCarrier;
	}

	/**
	 * @param transferCarrier The transferCarrier to set.
	 */
	public void setTransferCarrier(String transferCarrier) {
		this.transferCarrier = transferCarrier;
	}

	/**
	 * @return Returns the transferCarrierId.
	 */
	public int getTransferCarrierId() {
		return this.transferCarrierId;
	}

	/**
	 * @param transferCarrierId The transferCarrierId to set.
	 */
	public void setTransferCarrierId(int transferCarrierId) {
		this.transferCarrierId = transferCarrierId;
	}

	/**
	 * @return Returns the isDeliveryCheckNeeded.
	 */
	public boolean isDeliveryCheckNeeded() {
		return isDeliveryCheckNeeded;
	}

	/**
	 * @param isDeliveryCheckNeeded The isDeliveryCheckNeeded to set.
	 */
	public void setDeliveryCheckNeeded(boolean isDeliveryCheckNeeded) {
		this.isDeliveryCheckNeeded = isDeliveryCheckNeeded;
	}

	/**
	 * @return Returns the scanDate.
	 */
	public LocalDate getScanDate() {
		return this.scanDate;
	}

	/**
	 * @param scanDate The scanDate to set.
	 */
	public void setScanDate(LocalDate scanDate) {
		this.scanDate = scanDate;
	}

	/**
	 * @return Returns the ownAirlineId.
	 */
	public int getOwnAirlineId() {
		return this.ownAirlineId;
	}

	/**
	 * @param ownAirlineId The ownAirlineId to set.
	 */
	public void setOwnAirlineId(int ownAirlineId) {
		this.ownAirlineId = ownAirlineId;
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
	 * @return Returns the isPartialDelivery.
	 */
	public boolean isPartialDelivery() {
		return isPartialDelivery;
	}

	/**
	 * @param isPartialDelivery The isPartialDelivery to set.
	 */
	public void setPartialDelivery(boolean isPartialDelivery) {
		this.isPartialDelivery = isPartialDelivery;
	}

	/**
	 * @return the isDeliveryNeeded
	 */
	public boolean isDeliveryNeeded() {
		return isDeliveryNeeded;
	}

	/**
	 * @param isDeliveryNeeded the isDeliveryNeeded to set
	 */
	public void setDeliveryNeeded(boolean isDeliveryNeeded) {
		this.isDeliveryNeeded = isDeliveryNeeded;
	}



	public String getMailSource() {
		return mailSource;
	}

	public void setMailSource(String mailSource) {
		this.mailSource = mailSource;
	}
	/**
	 * 	Getter for segmentSerialNumber
	 *	Added by : A-4809 on Oct 5, 2015
	 * 	Used for :
	 */
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}
	/**
	 *  @param segmentSerialNumber the segmentSerialNumber to set
	 * 	Setter for segmentSerialNumber
	 *	Added by : A-4809 on Oct 5, 2015
	 * 	Used for :
	 */
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}
	/**
	 * 	Getter for flightDate
	 *	Added by : A-4809 on Oct 5, 2015
	 * 	Used for :
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}
	/**
	 *  @param flightDate the flightDate to set
	 * 	Setter for flightDate
	 *	Added by : A-4809 on Oct 5, 2015
	 * 	Used for :
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}
	/**
	 * 	Getter for pou
	 *	Added by : A-4809 on Oct 5, 2015
	 * 	Used for :
	 */
	public String getPou() {
		return pou;
	}
	/**
	 *  @param pou the pou to set
	 * 	Setter for pou
	 *	Added by : A-4809 on Oct 5, 2015
	 * 	Used for :
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}
	/**
	 * 	Getter for pol
	 *	Added by : A-4809 on Oct 5, 2015
	 * 	Used for :
	 */
	public String getPol() {
		return pol;
	}
	/**
	 *  @param pol the pol to set
	 * 	Setter for pol
	 *	Added by : A-4809 on Oct 5, 2015
	 * 	Used for :
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}
	/**
	 * 	Getter for route
	 *	Added by : A-4809 on Oct 5, 2015
	 * 	Used for :
	 */
	public String getRoute() {
		return route;
	}
	/**
	 *  @param route the route to set
	 * 	Setter for route
	 *	Added by : A-4809 on Oct 5, 2015
	 * 	Used for :
	 */
	public void setRoute(String route) {
		this.route = route;
	}
	/**
	 * @return the changeFlightFlag
	 */
	public String getChangeFlightFlag() {
		return changeFlightFlag;
	}
	/**
	 * @param changeFlightFlag the changeFlightFlag to set
	 */
	public void setChangeFlightFlag(String changeFlightFlag) {
		this.changeFlightFlag = changeFlightFlag;
	}

	/**
	 * @return the isOfflineJob
	 */
	public boolean isOfflineJob() {
		return isOfflineJob;
	}

	/**
	 * @param isOfflineJob the isOfflineJob to set
	 */
	public void setOfflineJob(boolean isOfflineJob) {
		this.isOfflineJob = isOfflineJob;
	}
	/**
	 * @param mailVOUpdated the mailVOUpdated to set
	 */
	public void setMailVOUpdated(boolean mailVOUpdated) {
		this.mailVOUpdated = mailVOUpdated;
	}
	/**
	 * @return the mailVOUpdated
	 */
	public boolean isMailVOUpdated() {
		return mailVOUpdated;
	}
	/**
	 * @param flightChange the flightChange to set
	 */
	public void setFlightChange(boolean flightChange) {
		this.flightChange = flightChange;
	}
	/**
	 * @return the flightChange
	 */
	public boolean isFlightChange() {
		return flightChange;
	}

	public String getAircraftType() {
		return aircraftType;
	}

	public void setAircraftType(String aircraftType) {
		this.aircraftType = aircraftType;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public String getArrivalTimeType() {
		return arrivalTimeType;
	}

	public void setArrivalTimeType(String arrivalTimeType) {
		this.arrivalTimeType = arrivalTimeType;
	}

	public String getOperationalStatus() {
		return operationalStatus;
	}

	public void setOperationalStatus(String operationalStatus) {
		this.operationalStatus = operationalStatus;
	}

	public String getGateInfo() {
		return gateInfo;
	}

	public void setGateInfo(String gateInfo) {
		this.gateInfo = gateInfo;
	}

	public String getManifestInfo() {
		return manifestInfo;
	}

	public void setManifestInfo(String manifestInfo) {
		this.manifestInfo = manifestInfo;
	}

	public String getRecievedInfo() {
		return recievedInfo;
	}

	public void setRecievedInfo(String recievedInfo) {
		this.recievedInfo = recievedInfo;
	}

	public Double getContainerCount() {
		return containerCount;
	}

	public void setContainerCount(Double containerCount) {
		this.containerCount = containerCount;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Double getMailCount() {
		return mailCount;
	}

	public void setMailCount(Double mailCount) {
		this.mailCount = mailCount;
	}

	public int getDefaultPageSize() {
		return defaultPageSize;
	}

	public void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getMailDataSource() {
		return mailDataSource;
	}

	public void setMailDataSource(String mailDataSource) {
		this.mailDataSource = mailDataSource;
	}

	public boolean isMailFlightChecked() {
		return mailFlightChecked;
	}

	public void setMailFlightChecked(boolean mailFlightChecked) {
		this.mailFlightChecked = mailFlightChecked;
	}

	public boolean isFlightBypassFlag() {
		return flightBypassFlag;
	}

	public void setFlightBypassFlag(boolean flightBypassFlag) {
		this.flightBypassFlag = flightBypassFlag;
	}
	public String getPaBuiltFlag() {
		return paBuiltFlag;
	}
	public void setPaBuiltFlag(String paBuiltFlag) {
		this.paBuiltFlag = paBuiltFlag;
	}

	public String getOperatingReference() {
		return operatingReference;
	}

	public void setOperatingReference(String operatingReference) {
		this.operatingReference = operatingReference;
	}
	public String getMessageVersion() {
		return messageVersion;
	}
	public void setMessageVersion(String messageVersion) {
		this.messageVersion = messageVersion;
	}
	public String getDeliveryRemarks() {
		return deliveryRemarks;
	}
	public void setDeliveryRemarks(String deliveryRemarks) {
		this.deliveryRemarks = deliveryRemarks;
	}
	
	
	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * @return the isArrivalAndDeliveryMarkedTogether
	 */
	public boolean isArrivalAndDeliveryMarkedTogether() {
		return isArrivalAndDeliveryMarkedTogether;
	}

	/**
	 * @param isArrivalAndDeliveryMarkedTogether the isArrivalAndDeliveryMarkedTogether to set
	 */
	public void setArrivalAndDeliveryMarkedTogether(boolean isArrivalAndDeliveryMarkedTogether) {
		this.isArrivalAndDeliveryMarkedTogether = isArrivalAndDeliveryMarkedTogether;
	}

	/**
	 * @return the actualArrivalTime
	 */
	public LocalDate getActualArrivalTime() {
		return actualArrivalTime;
	}

	/**
	 * @param actualArrivalTime the actualArrivalTime to set
	 */
	public void setActualArrivalTime(LocalDate actualArrivalTime) {
		this.actualArrivalTime = actualArrivalTime;
	}

	/**
	 * @return the legDestination
	 */
	public String getLegDestination() {
		return legDestination;
	}

	/**
	 * @param legDestination the legDestination to set
	 */
	public void setLegDestination(String legDestination) {
		this.legDestination = legDestination;
	}
	public String getMailCompanyCode() {
		return mailCompanyCode;
	}
	public void setMailCompanyCode(String mailCompanyCode) {
		this.mailCompanyCode = mailCompanyCode;
	}

	public Collection<EmbargoDetailsVO> getEmbargoDetails() {
		return embargoDetails;
	}

	public void setEmbargoDetails(Collection<EmbargoDetailsVO> embargoDetails) {
		this.embargoDetails = embargoDetails;
	}
	public String getFlightType() {
		return flightType;
	}
	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}
	public boolean isFoundResditSent() {
		return isFoundResditSent;
	}
	public void setFoundResditSent(boolean isFoundResditSent) {
		this.isFoundResditSent = isFoundResditSent;
	}
	
}
