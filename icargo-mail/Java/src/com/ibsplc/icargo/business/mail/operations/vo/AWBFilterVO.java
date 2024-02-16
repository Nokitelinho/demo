/*
 * AWBFilterVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-5991
 * 
 */
public class AWBFilterVO extends AbstractVO {

	private String companyCode;

	private int documentOwnerIdentifier;

	private String masterDocumentNumber;

	private String uldNumber;
	
	private String origin;
	
	private String destination;

	private String agentCode;

	/**
	 * @return Returns the companyCode.
	 */
	public  String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public  void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the documentOwnerIdentifier.
	 */
	public  int getDocumentOwnerIdentifier() {
		return documentOwnerIdentifier;
	}

	/**
	 * @param documentOwnerIdentifier
	 *            The documentOwnerIdentifier to set.
	 */
	public  void setDocumentOwnerIdentifier(int documentOwnerIdentifier) {
		this.documentOwnerIdentifier = documentOwnerIdentifier;
	}

	/**
	 * @return Returns the masterDocumentNumber.
	 */
	public  String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}

	/**
	 * @param masterDocumentNumber
	 *            The masterDocumentNumber to set.
	 */
	public  void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}

	/**
	 * @return Returns the uldNumber.
	 */
	public  String getUldNumber() {
		return uldNumber;
	}

	/**
	 * @param uldNumber
	 *            The uldNumber to set.
	 */
	public  void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	/**
	 * @return Returns the destination.
	 */
	public String getDestination() {
		return this.destination;
	}

	/**
	 * @param destination The destination to set.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return Returns the origin.
	 */
	public String getOrigin() {
		return this.origin;
	}

	/**
	 * @param origin The origin to set.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * 	Getter for agentCode 
	 *	Added by : U-1267 on 07-Nov-2017
	 * 	Used for : ICRD-211205
	 */
	public String getAgentCode() {
		return agentCode;
	}

	/**
	 *  @param agentCode the agentCode to set
	 * 	Setter for agentCode 
	 *	Added by : U-1267 on 07-Nov-2017
	 * 	Used for : ICRD-211205
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

}
