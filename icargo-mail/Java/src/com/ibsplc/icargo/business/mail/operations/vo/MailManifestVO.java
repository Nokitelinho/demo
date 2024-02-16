/*
 * MailManifestVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * TODO Add the purpose of this class
 *
 * @author A-3109
 *
 */

public class MailManifestVO extends AbstractVO {

	private String companyCode;

	private String depPort;

	private String flightNumber;

	private String flightCarrierCode;

	private int carrierId;

	private LocalDate depDate;

	private String strDepDate;

	private String flightStatus;
	
	private String flightRoute;
	
	private int totalbags;
	
	private int totalAcceptedBags;
	
	private Measure totalAcceptedWeight;
	//private double totalWeight;
	private Measure totalWeight;//added by A-7371

	private String airlineName;
	private String paName;
	private Collection<ContainerDetailsVO> containerDetails;
	
	private Collection<MailSummaryVO> mailSummaryVOs;
	
	 //Added by A-5160 for ICRD-92869 
	private HashMap<String, Collection<String>> polPouMap;
	    
	public HashMap<String, Collection<String>> getPolPouMap() {
		return polPouMap;
	}
	public void setPolPouMap(HashMap<String, Collection<String>> polPouMap) {
		this.polPouMap = polPouMap;
	}

	/**
	 * @return Returns the flightCarrierCode.
	 */
	public String getFlightCarrierCode() {
		return this.flightCarrierCode;
	}

	/**
	 * @param flightCarrierCode
	 *            The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the depDate.
	 */
	public LocalDate getDepDate() {
		return this.depDate;
	}

	/**
	 * @param depDate
	 *            The depDate to set.
	 */
	public void setDepDate(LocalDate depDate) {
		this.depDate = depDate;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return this.flightNumber;
	}

	/**
	 * @param flightNumber
	 *            The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the depPort.
	 */
	public String getDepPort() {
		return this.depPort;
	}

	/**
	 * @param depPort
	 *            The depPort to set.
	 */
	public void setDepPort(String depPort) {
		this.depPort = depPort;
	}

	/**
	 *
	 * @return
	 * Returns the ContainerDetails
	 */
	public Collection<ContainerDetailsVO> getContainerDetails() {
		return containerDetails;
	}

	/**
	 * @param containerDetails
	 *            The containerDetails to set.
	 */
	public void setContainerDetails(
			Collection<ContainerDetailsVO> containerDetails) {
		this.containerDetails = containerDetails;
	}

	/**
	 * @return Returns the strDepDate.
	 */
	public String getStrDepDate() {
		return this.strDepDate;
	}

	/**
	 * @param strDepDate
	 *            The strDepDate to set.
	 */
	public void setStrDepDate(String strDepDate) {
		this.strDepDate = strDepDate;
	}

	/**
	 * @return Returns the carrierId.
	 */
	public int getCarrierId() {
		return this.carrierId;
	}

	/**
	 * @param carrierId
	 *            The carrierId to set.
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
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
	 * @return Returns the totalbags.
	 */
	public int getTotalbags() {
		return totalbags;
	}

	/**
	 * @param totalbags The totalbags to set.
	 */
	public void setTotalbags(int totalbags) {
		this.totalbags = totalbags;
	}

	/**
	 * @return Returns the totalWeight.
	 *//*
	public double getTotalWeight() {
		return totalWeight;
	}

	*//**
	 * @param totalWeight The totalWeight to set.
	 *//*
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}*/

	public Collection<MailSummaryVO> getMailSummaryVOs() {
		return mailSummaryVOs;
	}
/**
 * 
 * 	Method		:	MailManifestVO.getTotalWeight
 *	Added by 	:	A-7371 on 17-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@return 
 *	Return type	: 	Measure
 */
	public Measure getTotalWeight() {
		return totalWeight;
	}
	/**
	 * 
	 * 	Method		:	MailManifestVO.setTotalWeight
	 *	Added by 	:	A-7371 on 17-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param totalWeight 
	 *	Return type	: 	void
	 */
	public void setTotalWeight(Measure totalWeight) {
		this.totalWeight = totalWeight;
	}
	public void setMailSummaryVOs(Collection<MailSummaryVO> mailSummaryVOs) {
		this.mailSummaryVOs = mailSummaryVOs;
	}

	public String getFlightRoute() {
		return flightRoute;
	}

	public void setFlightRoute(String flightRoute) {
		this.flightRoute = flightRoute;
	}
	public int getTotalAcceptedBags() {
		return totalAcceptedBags;
	}
	public void setTotalAcceptedBags(int totalAcceptedBags) {
		this.totalAcceptedBags = totalAcceptedBags;
	}
	public Measure getTotalAcceptedWeight() {
		return totalAcceptedWeight;
	}
	public void setTotalAcceptedWeight(Measure totalAcceptedWeight) {
		this.totalAcceptedWeight = totalAcceptedWeight;
	}
	
	public String getAirlineName() {
		return airlineName;
	}
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
	public String getPaName() {
		return paName;
	}
	public void setPaName(String paName) {
		this.paName = paName;
	}
}
