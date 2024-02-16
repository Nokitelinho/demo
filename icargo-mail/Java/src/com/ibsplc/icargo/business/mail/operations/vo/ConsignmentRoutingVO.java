/*
 * ConsignmentRoutingVO.java Created on JUN 30, 2016
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
public class ConsignmentRoutingVO  extends AbstractVO {

	private String companyCode;	
	private String consignmentDocNumber;	
	private int consignmentSeqNumber;	
	private String poaCode;	
	private int routingSerialNumber;
	private String flightCarrierCode;
	private int flightCarrierId;
	private String flightNumber;
	private LocalDate flightDate;
	private String pol;
	private String pou;
	
	
	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return the flightCarrierCode
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}
	/**
	 * @param flightCarrierCode the flightCarrierCode to set
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}
	/**
	 * @return the flightCarrierId
	 */
	public int getFlightCarrierId() {
		return flightCarrierId;
	}
	/**
	 * @param flightCarrierId the flightCarrierId to set
	 */
	public void setFlightCarrierId(int flightCarrierId) {
		this.flightCarrierId = flightCarrierId;
	}
	/**
	 * @return the flightDate
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}
	/**
	 * @param flightDate the flightDate to set
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}
	/**
	 * @return the flightNumber
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
	/**
	 * @param flightNumber the flightNumber to set
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	/**
	 * @return the poaCode
	 */
	public String getPoaCode() {
		return poaCode;
	}
	/**
	 * @param poaCode the poaCode to set
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}
	/**
	 * @return the pol
	 */
	public String getPol() {
		return pol;
	}
	/**
	 * @param pol the pol to set
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}
	/**
	 * @return the pou
	 */
	public String getPou() {
		return pou;
	}
	/**
	 * @param pou the pou to set
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}
	/**
	 * @return the routingSerialNumber
	 */
	public int getRoutingSerialNumber() {
		return routingSerialNumber;
	}
	/**
	 * @param routingSerialNumber the routingSerialNumber to set
	 */
	public void setRoutingSerialNumber(int routingSerialNumber) {
		this.routingSerialNumber = routingSerialNumber;
	}
	/**
	 * @return the consignmentDocNumber
	 */
	public String getConsignmentDocNumber() {
		return consignmentDocNumber;
	}
	/**
	 * @param consignmentDocNumber the consignmentDocNumber to set
	 */
	public void setConsignmentDocNumber(String consignmentDocNumber) {
		this.consignmentDocNumber = consignmentDocNumber;
	}
	/**
	 * @return the consignmentSeqNumber
	 */
	public int getConsignmentSeqNumber() {
		return consignmentSeqNumber;
	}
	/**
	 * @param consignmentSeqNumber the consignmentSeqNumber to set
	 */
	public void setConsignmentSeqNumber(int consignmentSeqNumber) {
		this.consignmentSeqNumber = consignmentSeqNumber;
	}
}
