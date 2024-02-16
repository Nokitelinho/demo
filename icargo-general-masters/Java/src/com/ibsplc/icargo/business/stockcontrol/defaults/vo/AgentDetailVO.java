/*
 * AgentDetailVO.java Created on Aug 2, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1954
 * 
 */
public class AgentDetailVO extends AbstractVO {

	/**
	 * company code
	 */
	private String companyCode;

	/**
	 * agentCode
	 */
	private String agentCode;

	/**
	 * agent name
	 */
	private String agentName;

	private String customerCode;

	private String customerName;
	
	private String agentIataCode;
	
	private String agentCassCode;
	
	private String agentPlace;
	
	//Added by A-5807 for BUG ICRD-137563
	private String agentStatus;
	
	private LocalDate validFrom;
	
	private LocalDate validTo;


	/**
	 * @return Returns the agentCassCode.
	 */
	public String getAgentCassCode() {
		return agentCassCode;
	}

	/**
	 * @param agentCassCode The agentCassCode to set.
	 */
	public void setAgentCassCode(String agentCassCode) {
		this.agentCassCode = agentCassCode;
	}

	/**
	 * @return Returns the agentIataCode.
	 */
	public String getAgentIataCode() {
		return agentIataCode;
	}

	/**
	 * @param agentIataCode The agentIataCode to set.
	 */
	public void setAgentIataCode(String agentIataCode) {
		this.agentIataCode = agentIataCode;
	}

	/**
	 * @return Returns the customerCode.
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode
	 *            The customerCode to set.
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return Returns the customerName.
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName
	 *            The customerName to set.
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return Returns the agentCode.
	 */
	public String getAgentCode() {
		return agentCode;
	}

	/**
	 * @param agentCode
	 *            The agentCode to set.
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**
	 * @return Returns the agentName.
	 */
	public String getAgentName() {
		return agentName;
	}

	/**
	 * @param agentName
	 *            The agentName to set.
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the agentPlace
	 */
	public String getAgentPlace() {
		return agentPlace;
	}

	/**
	 * @param agentPlace the agentPlace to set
	 */
	public void setAgentPlace(String agentPlace) {
		this.agentPlace = agentPlace;
	}

	/**
	 * @return the agentStatus
	 */
	public String getAgentStatus() {
		return agentStatus;
	}

	/**
	 * @param agentStatus the agentStatus to set
	 */
	public void setAgentStatus(String agentStatus) {
		this.agentStatus = agentStatus;
	}

	/**
	 * @return the validFrom
	 */
	public LocalDate getValidFrom() {
		return validFrom;
	}

	/**
	 * @param validFrom the validFrom to set
	 */
	public void setValidFrom(LocalDate validFrom) {
		this.validFrom = validFrom;
	}

	/**
	 * @return the validTo
	 */
	public LocalDate getValidTo() {
		return validTo;
	}

	/**
	 * @param validTo the validTo to set
	 */
	public void setValidTo(LocalDate validTo) {
		this.validTo = validTo;
	}

}
