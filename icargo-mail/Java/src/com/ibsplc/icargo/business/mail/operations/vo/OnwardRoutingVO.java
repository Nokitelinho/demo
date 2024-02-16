/*
 * OnwardRoutingVO.java Created on Jun 30, 2016
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
 * @author A-3109
 * Onward Routing for ULD
 */
public class OnwardRoutingVO extends AbstractVO {
    private String operationFlag;
    private String onwardCarrierCode;
    private int onwardCarrierId;
    private String onwardFlightNumber;
    private LocalDate onwardFlightDate;
    private String pou;
    private String assignmenrPort;
    private String companyCode;
    private String containerNumber;
    private int carrierId;
    private String flightNumber;
    private long flightSequenceNumber;
    private int legSerialNumber;
    private int routingSerialNumber;
    
    
    
	/**
	 * @return Returns the routingSerialNumber.
	 */
	public int getRoutingSerialNumber() {
		return routingSerialNumber;
	}
	/**
	 * @param routingSerialNumber The routingSerialNumber to set.
	 */
	public void setRoutingSerialNumber(int routingSerialNumber) {
		this.routingSerialNumber = routingSerialNumber;
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
	 * @return Returns the assignmenrPort.
	 */
	public String getAssignmenrPort() {
		return assignmenrPort;
	}
	/**
	 * @param assignmenrPort The assignmenrPort to set.
	 */
	public void setAssignmenrPort(String assignmenrPort) {
		this.assignmenrPort = assignmenrPort;
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
	 * @return Returns the carrierCode.
	 */
	public String getOnwardCarrierCode() {
		return onwardCarrierCode;
	}
	/**
	 * @param carrierCode The carrierCode to set.
	 */
	public void setOnwardCarrierCode(String carrierCode) {
		this.onwardCarrierCode = carrierCode;
	}
	/**
	 * @return Returns the carrierId.
	 */
	public int getOnwardCarrierId() {
		return onwardCarrierId;
	}
	/**
	 * @param onwardCarrierId The onwardCarrierId to set.
	 */
	public void setOnwardCarrierId(int onwardCarrierId) {
		this.onwardCarrierId = onwardCarrierId;
	}
	/**
	 * @return Returns the flightDate.
	 */
	public LocalDate getOnwardFlightDate() {
		return onwardFlightDate;
	}
	/**
	 * @param onwardFlightDate The onwardFlightDate to set.
	 */
	public void setOnwardFlightDate(LocalDate onwardFlightDate) {
		this.onwardFlightDate = onwardFlightDate;
	}
	/**
	 * @return Returns the onwardFightNumber.
	 */
	public String getOnwardFlightNumber() {
		return onwardFlightNumber;
	}
	/**
	 * @param onwardFightNumber The onwardFightNumber to set.
	 */
	public void setOnwardFlightNumber(String onwardFightNumber) {
		this.onwardFlightNumber = onwardFightNumber;
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
     * @return Returns the flightLegSerialNumber.
     */
    public int getLegSerialNumber() {
        return legSerialNumber;
    }
    /**
     * @param flightLegSerialNumber The flightLegSerialNumber to set.
     */
    public void setLegSerialNumber(int flightLegSerialNumber) {
        this.legSerialNumber = flightLegSerialNumber;
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
    

}
