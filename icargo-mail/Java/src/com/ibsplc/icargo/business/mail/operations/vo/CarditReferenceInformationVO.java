/*
 * CarditReferenceInformationVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 *
 * @author A-5991 
 *
 */
/*
 *  Revision History
 *--------------------------------------------------------------------------
 *  Revision    Date                           Author           Description
 * -------------------------------------------------------------------------
 *  0.1         Jun 30, 2016                   A-5991     		First Draft
 */
public class CarditReferenceInformationVO extends AbstractVO {

	/**
	 * Transport Contract Reference Qualifier
	 */
	private String transportContractReferenceQualifier;
	/**
	 * Consignment Contract Reference Number
	 */
	private String consignmentContractReferenceNumber;
	/**
	 * CARDIT Type
	 */
	private String carditType;
	/**
	 * CARDIT Key
	 */
	private String carditKey;
	
	/**
	 * Reference Number
	 */
	private String refNumber;
	
	/**
	 * Reference Qualifier
	 */
	private String rffQualifier;  
	private String orgin; 
	private String destination;	
	private String contractRef;
	
    /**
	 * @return the contractRef
	 */
	public String getContractRef() {
		return contractRef;
	}
	/**
	 * @param contractRef the contractRef to set
	 */
	public void setContractRef(String contractRef) {
		this.contractRef = contractRef;
	}

	/**
	 * @return the consignmentContractReferenceNumber
	 */
	public String getConsignmentContractReferenceNumber() {
		return consignmentContractReferenceNumber;
	}
	/**
	 * @param consignmentContractReferenceNumber the consignmentContractReferenceNumber to set
	 */
	public void setConsignmentContractReferenceNumber(
			String consignmentContractReferenceNumber) {
		this.consignmentContractReferenceNumber = consignmentContractReferenceNumber;
	}
	/**
	 * @return the transportContractReferenceQualifier
	 */
	public String getTransportContractReferenceQualifier() {
		return transportContractReferenceQualifier;
	}
	/**
	 * @param transportContractReferenceQualifier the transportContractReferenceQualifier to set
	 */
	public void setTransportContractReferenceQualifier(
			String transportContractReferenceQualifier) {
		this.transportContractReferenceQualifier = transportContractReferenceQualifier;
	}
	/**
	 * @return the carditType
	 */
	public String getCarditType() {
		return carditType;
	}
	/**
	 * @param carditType the carditType to set
	 */
	public void setCarditType(String carditType) {
		this.carditType = carditType;
	}
	/**
	 * @return the carditKey
	 */
	public String getCarditKey() {
		return carditKey;
	}
	/**
	 * @param carditKey the carditKey to set
	 */
	public void setCarditKey(String carditKey) {
		this.carditKey = carditKey;
	}
	/**
	 * @return the rffQalifier
	 */
	public String getRffQualifier() {
		return rffQualifier;
	}
	/**
	 * @param rffQalifier the rffQalifier to set
	 */
	public void setRffQualifier(String rffQualifier) {
		this.rffQualifier = rffQualifier;
	}
	/**
	 * @return the orgin
	 */
	public String getOrgin() {
		return orgin;
	}
	/**
	 * @param orgin the orgin to set
	 */
	public void setOrgin(String orgin) {
		this.orgin = orgin;
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
	 * @return the refNumber
	 */
	public String getRefNumber() {
		return refNumber;
	}
	/**
	 * @param refNumber the refNumber to set
	 */
	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}

}
