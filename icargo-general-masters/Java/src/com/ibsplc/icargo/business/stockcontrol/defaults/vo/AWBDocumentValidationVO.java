/*
 * AWBDocumentValidationVO.java Created on Jul 31, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
/**
 * 
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import java.util.Collection;

/**
 * @author A-1954
 *
 */
public class AWBDocumentValidationVO extends DocumentValidationVO {

	private Collection<AgentDetailVO> agentDetails;
	
	/**
	 * @return Returns the agentDetails.
	 */
	public Collection<AgentDetailVO> getAgentDetails() {
		return agentDetails;
	}
	/**
	 * @param agentDetails The agentDetails to set.
	 */
	public void setAgentDetails(Collection<AgentDetailVO> agentDetails) {
		this.agentDetails = agentDetails;
	}
	/**
	 * @return Returns the documentNumber.
	 */
	public String getDocumentNumber() {
		return documentNumber;
	}
	/**
	 * @param documentNumber The documentNumber to set.
	 */
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	/**
	 * @return Returns the documentType.
	 */
	public String getDocumentType() {
		return documentType;
	}
	/**
	 * @param documentType The documentType to set.
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	/**
	 * @return Returns the stockHolderCode.
	 */
	public String getStockHolderCode() {
		return stockHolderCode;
	}
	/**
	 * @param stockHolderCode The stockHolderCode to set.
	 */
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}
	
	
}
