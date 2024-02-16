/*
 * DSNEnquiryFilterVO.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.ArrayList;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-5991
 *
 */
public class DSNEnquiryFilterVO extends AbstractVO {
    private String companyCode; 
    private String dsn;
    private String originCity;
    private String destinationCity;
    private String mailClass;
    private String mailCategoryCode;
    private String mailSubClass;
    private int year;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String consignmentNumber;
    private String carrierCode;
    private String flightNumber;
    private LocalDate flightDate; 
    private int carrierId; 
    private String pltEnabledFlag;
    private String capNotAcpEnabledFlag;
    private String containerNumber;
    private String containerType;
    private String airportCode;
    private String paCode;
    private String operationType; 
    private String transitFlag;
    private String doe;
    private String ooe;
   
    private String status;
   
    private int absoluteIndex;
  
    private String userIdentifier;
    private int totalRecords;
    
    
    
    private int pageNumber;
    
    private LocalDate consignmentDate;
    private LocalDate rdt;
    private String flightType;
    private int pageSize;
    private String shipmentPrefix;
    private String documentNumber;
    private String awbAttached;
    public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	private String originOfficeOfExchange;
    private String destinationOfficeOfExchange;

    
    public String getOriginOfficeOfExchange() {
		return originOfficeOfExchange;
	}
	public void setOriginOfficeOfExchange(String originOfficeOfExchange) {
		this.originOfficeOfExchange = originOfficeOfExchange;
	}
	public String getDestinationOfficeOfExchange() {
		return destinationOfficeOfExchange;
	}
	public void setDestinationOfficeOfExchange(String destinationOfficeOfExchange) {
		this.destinationOfficeOfExchange = destinationOfficeOfExchange;
	}
	
	/**
     * @author A-3227
     * This is exclusively used for server side listing
     */
    private ArrayList<Object> parameters;
    
    public String getTransitFlag() {
		return transitFlag;
	}
	public void setTransitFlag(String transitFlag) {
		this.transitFlag = transitFlag;
	}
	/**
	 * @return Returns the operationType.
	 */
	public String getOperationType() {
		return operationType;
	}
	/**
	 * @param operationType The operationType to set.
	 */
	public void setOperationType(String operationType) {
		this.operationType = operationType;
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
     * @return Returns the consignmentNumber.
     */
    public String getConsignmentNumber() {
        return consignmentNumber;
    }
    /**
     * @param consignmentNumber The consignmentNumber to set.
     */
    public void setConsignmentNumber(String consignmentNumber) {
        this.consignmentNumber = consignmentNumber;
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
     * @return Returns the destinationCity.
     */
    public String getDestinationCity() {
        return destinationCity;
    }
    /**
     * @param destinationCity The destinationCity to set.
     */
    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
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
     * @return Returns the fromDate.
     */
    public LocalDate getFromDate() {
        return fromDate;
    }
    /**
     * @param fromDate The fromDate to set.
     */
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
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
     * @return Returns the originCity.
     */
    public String getOriginCity() {
        return originCity;
    }
    /**
     * @param originCity The originCity to set.
     */
    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }
    /**
     * @return Returns the pltEnabledFlag.
     */
    public String getPltEnabledFlag() {
        return pltEnabledFlag;
    }
    /**
     * @param pltEnabledFlag The pltEnabledFlag to set.
     */
    public void setPltEnabledFlag(String pltEnabledFlag) {
        this.pltEnabledFlag = pltEnabledFlag;
    }
    /**
     * @return Returns the toDate.
     */
    public LocalDate getToDate() {
        return toDate;
    }
    /**
     * @param toDate The toDate to set.
     */
    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
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
	 * @return the doe
	 */
	public String getDoe() {
		return doe;
	}
	/**
	 * @param doe the doe to set
	 */
	public void setDoe(String doe) {
		this.doe = doe;
	}
	/**
	 * @return the ooe
	 */
	public String getOoe() {
		return ooe;
	}
	/**
	 * @param ooe the ooe to set
	 */
	public void setOoe(String ooe) {
		this.ooe = ooe;
	}
	/**
	 * @return the mailSubClass
	 */
	public String getMailSubClass() {
		return mailSubClass;
	}
	/**
	 * @param mailSubClass the mailSubClass to set
	 */
	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}
    /**
     * @author A-3227
	 * @return the parameters
     * This is exclusively used for server side listing 
     */
	public ArrayList<Object> getParameters() {
		return parameters;
	}
	/**
     * @author A-3227
	 * @param parameters the parameters to set
     * This is exclusively used for server side listing 
	 */
	public void setParameters(ArrayList<Object> parameters) {
		this.parameters = parameters;
	}
	/**
	 * @return the capNotAcpEnabledFlag
	 */
	public String getCapNotAcpEnabledFlag() {
		return capNotAcpEnabledFlag;
	}
	/**
	 * @param capNotAcpEnabledFlag the capNotAcpEnabledFlag to set
	 */
	public void setCapNotAcpEnabledFlag(String capNotAcpEnabledFlag) {
		this.capNotAcpEnabledFlag = capNotAcpEnabledFlag;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the absoluteIndex
	 */
	public int getAbsoluteIndex() {
		return absoluteIndex;
	}
	/**
	 * @param absoluteIndex the absoluteIndex to set
	 */
	public void setAbsoluteIndex(int absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
	}
	public String getUserIdentifier() {
		return userIdentifier;
	}
	public void setUserIdentifier(String userIdentifier) {
		this.userIdentifier = userIdentifier;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public LocalDate getConsignmentDate() {
		return consignmentDate;
	}
	public void setConsignmentDate(LocalDate consignmentDate) {
		this.consignmentDate = consignmentDate;
	}
	public LocalDate getRdt() {
		return rdt;
	}
	public void setRdt(LocalDate rdt) {
		this.rdt = rdt;
	}
	public String getFlightType() {
		return flightType;
	}
	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getAwbAttached() {
		return awbAttached;
	}
	public void setAwbAttached(String awbAttached) {
		this.awbAttached = awbAttached;
	}
}
