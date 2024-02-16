/*
 * CarditTransportationVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;


import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 *
 * @author A-5991
 *
 */
/*
 *  Revision History
 *--------------------------------------------------------------------------
 *  Revision 	Date      	           		   Author			Description
 * -------------------------------------------------------------------------
 *  0.1			Jun 30, 2016				   A-5991			First Draft
 */
public class CarditTransportationVO extends AbstractVO {
    
    /**
     * Arrival place of flight
     */

    private String arrivalPort;
    
    /**
     * arrivalPlaceName of flight
     */

    private String arrivalPlaceName;
    
    /**
     * Departure place of flight
     */
    private String departurePort;

	/**
	 * Departure place name of flight
	 */
	private String departurePlaceName;

    /**
     * Departure time of flight
     */
    private LocalDate departureTime;
	
	/**
	 * Arrival date of flight
	 */
	private LocalDate arrivalDate;

    /**
     * carrier identification
     */
    private int carrierID;

    /**
     * This field is a code list qualifier indicating carrier code
     */
    private String carrierCode;
	
	/**
	 * carrier name
	 */
	private String carrierName;

    /**
     * This field indicates code list responsible agency for place of arrival
     */
    private String arrivalCodeListAgency;


    /**
     * This field indicates code list responsible agency for place of departure
     */
    private String departureCodeListAgency;

    /**
     * This field indicates code list responsible agency for carrier
     * eg: 3 -IATA, 139 - UPU
     */
    private String agencyForCarrierCodeList;

    /**
     * This field indicates conveyance reference number
     */
    private String conveyanceReference;

    /**
     * This field indicates mode of transport
     * eg: air, road , rail
     */
    private String modeOfTransport;

    /**
     * This field indicates transport stage qualifier
     * eg: 10 - pre carriage transport
     * eg: 20 - main carriage transport
     */
    private String transportStageQualifier;

	/**
	 * This field is Transport Leg Rate
	 */
	private String transportLegRate;
        
    private String flightNumber;
    
    private long flightSequenceNumber;
    
    private int legSerialNumber;
    
    private int transportSerialNum;

    private int segmentSerialNum;
    private String transportIdentification;

    private String contractReference;
    
    /**
	 * @return the contractReference
	 */
	public String getContractReference() {
		return contractReference;
	}

	/**
	 * @param contractReference the contractReference to set
	 */
	public void setContractReference(String contractReference) {
		this.contractReference = contractReference;
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
        return legSerialNumber;
    }

    /**
     * @param legSerialNumber The legSerialNumber to set.
     */
    public void setLegSerialNumber(int legSerialNumber) {
        this.legSerialNumber = legSerialNumber;
    }

    /**
     * @return Returns the agencyForCarrierCodeList.
     */
    public String getAgencyForCarrierCodeList() {
        return agencyForCarrierCodeList;
    }

    /**
     * @param agencyForCarrierCodeList The agencyForCarrierCodeList to set.
     */
    public void setAgencyForCarrierCodeList(String agencyForCarrierCodeList) {
        this.agencyForCarrierCodeList = agencyForCarrierCodeList;
    }

    /**
     * @return Returns the arrivalCodeListAgency.
     */
    public String getArrivalCodeListAgency() {
        return arrivalCodeListAgency;
    }

    /**
     * @param arrivalCodeListAgency The arrivalCodeListAgency to set.
     */
    public void setArrivalCodeListAgency(String arrivalCodeListAgency) {
        this.arrivalCodeListAgency = arrivalCodeListAgency;
    }

    /**
     * @return Returns the arrivalPort.
     */
    public String getArrivalPort() {
        return arrivalPort;
    }

    /**
     * @param arrivalPort The arrivalPort to set.
     */
    public void setArrivalPort(String arrivalPort) {
        this.arrivalPort = arrivalPort;
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
     * @return Returns the carrierID.
     */
    public int getCarrierID() {
        return carrierID;
    }

    /**
     * @param carrierID The carrierID to set.
     */
    public void setCarrierID(int carrierID) {
        this.carrierID = carrierID;
    }
    
    /**
     * @return Returns the conveyanceReference.
     */
    public String getConveyanceReference() {
        return conveyanceReference;
    }

    /**
     * @param conveyanceReference The conveyanceReference to set.
     */
    public void setConveyanceReference(String conveyanceReference) {
        this.conveyanceReference = conveyanceReference;
    }

    /**
     * @return Returns the departureCodeListAgency.
     */
    public String getDepartureCodeListAgency() {
        return departureCodeListAgency;
    }

    /**
     * @param departureCodeListAgency The departureCodeListAgency to set.
     */
    public void setDepartureCodeListAgency(String departureCodeListAgency) {
        this.departureCodeListAgency = departureCodeListAgency;
    }

    /**
     * @return Returns the departurePort.
     */
    public String getDeparturePort() {
        return departurePort;
    }

    /**
     * @param departurePort The departurePort to set.
     */
    public void setDeparturePort(String departurePort) {
        this.departurePort = departurePort;
    }

    /**
     * @return Returns the departureTime.
     */
    public LocalDate getDepartureTime() {
        return departureTime;
    }

    /**
     * @param departureTime The departureTime to set.
     */
    public void setDepartureTime(LocalDate departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * @return Returns the modeOfTransport.
     */
    public String getModeOfTransport() {
        return modeOfTransport;
    }

    /**
     * @param modeOfTransport The modeOfTransport to set.
     */
    public void setModeOfTransport(String modeOfTransport) {
        this.modeOfTransport = modeOfTransport;
    }

    /**
     * @return Returns the transportStageQualifier.
     */
    public String getTransportStageQualifier() {
        return transportStageQualifier;
    }

    /**
     * @param transportStageQualifier The transportStageQualifier to set.
     */
    public void setTransportStageQualifier(String transportStageQualifier) {
        this.transportStageQualifier = transportStageQualifier;
    }

	/**
	 * @return Returns the transportSerialNum.
	 */
	public int getTransportSerialNum() {
		return transportSerialNum;
	}

	/**
	 * @param transportSerialNum The transportSerialNum to set.
	 */
	public void setTransportSerialNum(int transportSerialNum) {
		this.transportSerialNum = transportSerialNum;
	}

	/**
	 * @return Returns the segmentSerialNum.
	 */
	public int getSegmentSerialNum() {
		return segmentSerialNum;
	}

	/**
	 * @param segmentSerialNum The segmentSerialNum to set.
	 */
	public void setSegmentSerialNum(int segmentSerialNum) {
		this.segmentSerialNum = segmentSerialNum;
	}

	/**
	 * @return the arrivalDate
	 */
	public LocalDate getArrivalDate() {
		return arrivalDate;
	}

	/**
	 * @param arrivalDate the arrivalDate to set
	 */
	public void setArrivalDate(LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	/**
	 * @return the arrivalPlaceName
	 */
	public String getArrivalPlaceName() {
		return arrivalPlaceName;
	}

	/**
	 * @param arrivalPlaceName the arrivalPlaceName to set
	 */
	public void setArrivalPlaceName(String arrivalPlaceName) {
		this.arrivalPlaceName = arrivalPlaceName;
	}

	/**
	 * @return the departurePlaceName
	 */
	public String getDeparturePlaceName() {
		return departurePlaceName;
	}

	/**
	 * @param departurePlaceName the departurePlaceName to set
	 */
	public void setDeparturePlaceName(String departurePlaceName) {
		this.departurePlaceName = departurePlaceName;
	}

	/**
	 * @return the transportLegRate
	 */
	public String getTransportLegRate() {
		return transportLegRate;
	}

	/**
	 * @param transportLegRate the transportLegRate to set
	 */
	public void setTransportLegRate(String transportLegRate) {
		this.transportLegRate = transportLegRate;
	}

	/**
	 * @return the carrierName
	 */
	public String getCarrierName() {
		return carrierName;
	}

	/**
	 * @param carrierName the carrierName to set
	 */
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	/**
	 * @return Returns the transportIdentification.
	 */
	public String getTransportIdentification() {
		return transportIdentification;
	}

	/**
	 * @param transportIdentification The transportIdentification to set.
	 */
	public void setTransportIdentification(String transportIdentification) {
		this.transportIdentification = transportIdentification;
	}


}
