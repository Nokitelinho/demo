/*
 * FlightLoanPlanContainerPK.java Created on July1 , 2022
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * @author a-3429
 * 
 */
@Embeddable
public class FlightLoadPlanContainerPK implements Serializable {
	private String companyCode;
	private String containerNumber;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private String segOrigin;
	private String segDestination;
	private int loadPlanVersion;


	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	public int getCarrierId() {
		return carrierId;
	}

	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	public String getSegOrigin() {
		return segOrigin;
	}

	public void setSegOrigin(String segOrigin) {
		this.segOrigin = segOrigin;
	}

	public String getSegDestination() {
		return segDestination;
	}

	public void setSegDestination(String segDestination) {
		this.segDestination = segDestination;
	}

	public int getLoadPlanVersion() {
		return loadPlanVersion;
	}

	public void setLoadPlanVersion(int loadPlanVersion) {
		this.loadPlanVersion = loadPlanVersion;
	}

	
}
