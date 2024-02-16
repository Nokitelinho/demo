/*
 * OperationalFlightVO.java Created on Jun 30, 2016
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
 * @author a-3109
 *
 */
public class OperationalFlightVO extends AbstractVO {
    private String companyCode;
    private String pol;
    private String pou;
    private int carrierId;
    private String flightNumber;
    private int legSerialNumber;
    private long flightSequenceNumber;
    private String carrierCode ;
    private LocalDate flightDate ;
    private String direction;
    private String ownAirlineCode;
    private int ownAirlineId;
    private  LocalDate operationTime;
    private String flightStatus;
    private LocalDate arrivaltime;	  
   // Added by A-8176 for mail outbound
    private LocalDate fromDate;
    private LocalDate toDate;
    private String flightOperationStatus;
    private int recordsPerPage;
    private String containerNumber;
    //added by A-7815 as part of IASCB-36551
    private String operatingReference;
    
    private LocalDate reqDeliveryTime;
    /*
     * Added for HHT AirNZ432
     */
    private boolean isScanned;



    /*
     * Added By Karthick V as the part of the NCA Mail Tracking Cr (AUTO CREATE UBRS)...
     *
     */
    private String flightRoute;




    // added for Transfer Container
    private String operator;

    private String airportCode;
    
    private LocalDate actualTimeOfDeparture;
    //Search Flight
    private int segSerNum;
    private String mailStatus;
    private int totalRecords;
	private Integer pageNumber;
    private Integer absoluteIndex;
    private LocalDate depFromDate ;
    private LocalDate depToDate ;
    private LocalDate arrFromDate ;
    private LocalDate arrToDate ;
    private String lastUpdatedUser;
	private LocalDate lastUpdatedTime;
	private String tailNumber; //added by a-5133 as part of CR ICRD-22342 
	private boolean forUCMSendChk;
	
	private boolean includeAllMailTrucks;
	
	//added by a-8952 as part of CR ICRD-327979 start
	private LocalDate actualArrivalTime;
	private String legDestination;
	
	//added by a-8952 as part of CR ICRD-327979 end
	private boolean atdCaptured;
	private boolean ataCaptured;
	
	private String destination;
	
	private boolean mailFlightOnly;
	private boolean transferOutOperation;
	private boolean reassignInternally;// this will indicate reassign without any history or resdit
	private boolean transferStatus;// for oal LH
	private String countryCode;
	private String flightType;
	private String fltOwner;
	private boolean requireAllLegs;
	private String consignemntPresent;
	private String fromScreen;
	public String getFromScreen() {
		return fromScreen;
	}
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}
	public String getConsignemntPresent() {
		return consignemntPresent;
	}
	public void setConsignemntPresent(String consignemntPresent) {
		this.consignemntPresent = consignemntPresent;
	}
	public boolean isTransferStatus() {
		return transferStatus;
	}
	public void setTransferStatus(boolean transferStatus) {
		this.transferStatus = transferStatus;
	}
	
	private boolean cstatus;
	
	public boolean isCstatus() {
		return cstatus;
	}
	public void setCstatus(boolean cstatus) {
		this.cstatus = cstatus;
	}
    /**
	 * 	Getter for forUCMSendChk 
	 *	Added by : A-6991 on 04-May-2017
	 * 	Used for : ICRD-77772
	 */
	public boolean isForUCMSendChk() {
		return forUCMSendChk;
	}
	/**
	 *  @param forUCMSendChk the forUCMSendChk to set
	 * 	Setter for forUCMSendChk 
	 *	Added by : A-6991 on 04-May-2017
	 * 	Used for : ICRD-77772
	 */
	public void setForUCMSendChk(boolean forUCMSendChk) {
		this.forUCMSendChk = forUCMSendChk;
	}
    public String getTailNumber() {
		return tailNumber;
	}
	public void setTailNumber(String tailNumber) {
		this.tailNumber = tailNumber;
	}
    /**
	 * @return Returns the lastUpdatedTime.
	 */
	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	/**
	 * @return Returns the lastUpdatedUser.
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	/**
	 * @param lastUpdatedUser The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	/**
	 * @return Returns the absoluteIndex.
	 */
	public Integer getAbsoluteIndex() {
		return absoluteIndex;
	}
	/**
	 * @param absoluteIndex The absoluteIndex to set.
	 */
	public void setAbsoluteIndex(Integer absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
	}
	/**
	 * @return Returns the arrFromDate.
	 */
	public LocalDate getArrFromDate() {
		return arrFromDate;
	}
	/**
	 * @param arrFromDate The arrFromDate to set.
	 */
	public void setArrFromDate(LocalDate arrFromDate) {
		this.arrFromDate = arrFromDate;
	}
	/**
	 * @return Returns the arrToDate.
	 */
	public LocalDate getArrToDate() {
		return arrToDate;
	}
	/**
	 * @param arrToDate The arrToDate to set.
	 */
	public void setArrToDate(LocalDate arrToDate) {
		this.arrToDate = arrToDate;
	}
	/**
	 * @return Returns the depFromDate.
	 */
	public LocalDate getDepFromDate() {
		return depFromDate;
	}
	/**
	 * @param depFromDate The depFromDate to set.
	 */
	public void setDepFromDate(LocalDate depFromDate) {
		this.depFromDate = depFromDate;
	}
	/**
	 * @return Returns the depToDate.
	 */
	public LocalDate getDepToDate() {
		return depToDate;
	}
	/**
	 * @param depToDate The depToDate to set.
	 */
	public void setDepToDate(LocalDate depToDate) {
		this.depToDate = depToDate;
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
	 * @return Returns the pageNumber.
	 */
	public Integer getPageNumber() {
		return pageNumber;
	}
	/**
	 * @param pageNumber The pageNumber to set.
	 */
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	/**
	 * @return Returns the totalRecords.
	 */
	public int getTotalRecords() {
		return totalRecords;
	}
	/**
	 * @param totalRecords The totalRecords to set.
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	/**
	 * @return Returns the actualTimeOfDeparture.
	 */
	public LocalDate getActualTimeOfDeparture() {
		return actualTimeOfDeparture;
	}
	/**
	 * @param actualTimeOfDeparture The actualTimeOfDeparture to set.
	 */
	public void setActualTimeOfDeparture(LocalDate actualTimeOfDeparture) {
		this.actualTimeOfDeparture = actualTimeOfDeparture;
	}
	/**
	 * @return Returns the operator.
	 */
	public String getOperator() {
		return operator;
	}
	/**
	 * @param operator The operator to set.
	 */
	public void setOperator(String operator) {
		this.operator = operator;
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
     * @return Returns the pol.
     */
    public String getPol() {
        return pol;
    }
    /**
     * @param pol The pol to set.
     */
    public void setPol(String pol) {
        this.pol = pol;
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
     * @return Returns the direction.
     */
    public String getDirection() {
        return direction;
    }
    /**
     * @param direction The direction to set.
     */
    public void setDirection(String direction) {
        this.direction = direction;
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
     *
     * @return Returns the ownAirlineCode
     */
	public String getOwnAirlineCode() {
		return ownAirlineCode;
	}
	/**
	 *
	 * @param ownAirlineCode The ownAirlineCode to set
	 */
	public void setOwnAirlineCode(String ownAirlineCode) {
		this.ownAirlineCode = ownAirlineCode;
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
	 *
	 * @return
	 */
	public String getFlightRoute() {
		return flightRoute;
	}
	/**
	 *
	 * @param flightRoute
	 */
	public void setFlightRoute(String flightRoute) {
		this.flightRoute = flightRoute;
	}
	public LocalDate getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(LocalDate operationTime) {
		this.operationTime = operationTime;
	}
	public String getFlightStatus() {
		return flightStatus;
	}
	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	}
	/**
	 * @return the isScanned
	 */
	public boolean isScanned() {
		return isScanned;
	}
	/**
	 * @param isScanned the isScanned to set
	 */
	public void setScanned(boolean isScanned) {
		this.isScanned = isScanned;
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
		 * @return the arrivaltime
	 */

	public LocalDate getArrivaltime() {
			return arrivaltime;
		}
	/**
	 * @param arrivaltime the arrivaltime to set
	 */
	public void setArrivaltime(LocalDate arrivaltime) {
			this.arrivaltime = arrivaltime;
	}
	/**
	 * @return Returns the segSerNum.
	 */
	public int getSegSerNum() {
		return segSerNum;
	}
	/**
	 * @param segSerNum The segSerNum to set.
	 */
	public void setSegSerNum(int segSerNum) {
		this.segSerNum = segSerNum;
	}	  
//Added by A-8176
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
	public String getFlightOperationStatus() {
		return flightOperationStatus;
	}
	public void setFlightOperationStatus(String flightOperationStatus) {
		this.flightOperationStatus = flightOperationStatus;
	}
	public int getRecordsPerPage() {
		return recordsPerPage;
	}
	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}
	public String getContainerNumber() {
		return containerNumber;
	}
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	public String getOperatingReference() {
		return operatingReference;
	}
	public void setOperatingReference(String operatingReference) {
		this.operatingReference = operatingReference;
	}
	public boolean isIncludeAllMailTrucks() {
		return includeAllMailTrucks;
	}
	public void setIncludeAllMailTrucks(boolean includeAllMailTrucks) {
		this.includeAllMailTrucks = includeAllMailTrucks;
	}
	public boolean isAtdCaptured() {
		return atdCaptured;
	}
	public void setAtdCaptured(boolean atdCaptured) {
		this.atdCaptured = atdCaptured;
	}
	public boolean isAtaCaptured() {
		return ataCaptured;
	}
	public void setAtaCaptured(boolean ataCaptured) {
		this.ataCaptured = ataCaptured;
	}
	public LocalDate getReqDeliveryTime() {
		return reqDeliveryTime;
	}
	public void setReqDeliveryTime(LocalDate reqDeliveryTime) {
		this.reqDeliveryTime = reqDeliveryTime;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public boolean isMailFlightOnly() {
		return mailFlightOnly;
	}
	public void setMailFlightOnly(boolean mailFlightOnly) {
		this.mailFlightOnly = mailFlightOnly;
	}
	public boolean isTransferOutOperation() {
		return transferOutOperation;
	}
	public void setTransferOutOperation(boolean transferOutOperation) {
		this.transferOutOperation = transferOutOperation;
	}
	public boolean isReassignInternally() {
		return reassignInternally;
	}
	public void setReassignInternally(boolean reassignInternally) {
		this.reassignInternally = reassignInternally;
	}


	//added by a-8952 as part of CR ICRD-327979 start
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
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getFlightType() {
		return flightType;
	}
	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}
	public String getFltOwner() {
		return fltOwner;
	}
	public void setFltOwner(String fltOwner) {
		this.fltOwner = fltOwner;
	}
	public boolean isRequireAllLegs() {
		return requireAllLegs;
	}
	public void setRequireAllLegs(boolean requireAllLegs) {
		this.requireAllLegs = requireAllLegs;
	}
	
	
	//added by a-8952 as part of CR ICRD-327979 End
}
