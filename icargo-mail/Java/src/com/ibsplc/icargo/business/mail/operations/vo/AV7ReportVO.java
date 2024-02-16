/*
 * AV7ReportVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-5991
 *
 */
public class AV7ReportVO extends AbstractVO {
    
    private String originAirport;    
    private String destinationAirport;
    private String destinationAirportName;
    
    private String controlDocumentNumber;
    private String flightCarrierCode;
    private String flightNumber;
    private LocalDate flightDate;
    private Collection<MailInReportVO> mailInReportVOs;
    private int totalLetterBags;
    private int totalParcelBags;    
       
    //private double totalLetterWeight;
    //private double totalParcelWeight;
    private Measure totalLetterWeight;//added by A-7371
    private Measure totalParcelWeight;//added by A-7371
    
    private boolean isAPO;
	/**
	 * @return Returns the isAPO.
	 */
	public boolean isAPO() {
		return isAPO;
	}
	/**
	 * 
	 * @return totalLetterWeight
	 */
	public Measure getTotalLetterWeight() {
		return totalLetterWeight;
	}
	/**
	 * 
	 * @param totalLetterWeight
	 */
	public void setTotalLetterWeight(Measure totalLetterWeight) {
		this.totalLetterWeight = totalLetterWeight;
	}
	/**
	 * 
	 * @return totalParcelWeight
	 */
	public Measure getTotalParcelWeight() {
		return totalParcelWeight;
	}
	/**
	 * 
	 * @param totalParcelWeight
	 */
	public void setTotalParcelWeight(Measure totalParcelWeight) {
		this.totalParcelWeight = totalParcelWeight;
	}
	/**
	 * @param isAPO The isAPO to set.
	 */
	public void setAPO(boolean isAPO) {
		this.isAPO = isAPO;
	}
	/**
	 * @return Returns the controlDocumentNumber.
	 */
	public String getControlDocumentNumber() {
		return controlDocumentNumber;
	}
	/**
	 * @param controlDocumentNumber The controlDocumentNumber to set.
	 */
	public void setControlDocumentNumber(String controlDocumentNumber) {
		this.controlDocumentNumber = controlDocumentNumber;
	}
	/**
	 * @return Returns the destinationAirport.
	 */
	public String getDestinationAirport() {
		return destinationAirport;
	}
	/**
	 * @param destinationAirport The destinationAirport to set.
	 */
	public void setDestinationAirport(String destinationAirport) {
		this.destinationAirport = destinationAirport;
	}
	/**
	 * @return Returns the flightCarrierCode.
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}
	/**
	 * @param flightCarrierCode The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
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
	 * @return Returns the mailInReportVOs.
	 */
	public Collection<MailInReportVO> getMailInReportVOs() {
		return mailInReportVOs;
	}
	/**
	 * @param mailInReportVOs The mailInReportVOs to set.
	 */
	public void setMailInReportVOs(Collection<MailInReportVO> mailInReportVOs) {
		this.mailInReportVOs = mailInReportVOs;
	}
	/**
	 * @return Returns the originAirport.
	 */
	public String getOriginAirport() {
		return originAirport;
	}
	/**
	 * @param originAirport The originAirport to set.
	 */
	public void setOriginAirport(String originAirport) {
		this.originAirport = originAirport;
	}
	/**
	 * @return Returns the totalLetterBags.
	 */
	public int getTotalLetterBags() {
		return totalLetterBags;
	}
	/**
	 * @param totalLetterBags The totalLetterBags to set.
	 */
	public void setTotalLetterBags(int totalLetterBags) {
		this.totalLetterBags = totalLetterBags;
	}
	/**
	 * @return Returns the totalLetterWeight.
	 */
	/*public double getTotalLetterWeight() {
		return totalLetterWeight;
	}
	*//**
	 * @param totalLetterWeight The totalLetterWeight to set.
	 *//*
	public void setTotalLetterWeight(double totalLetterWeight) {
		this.totalLetterWeight = totalLetterWeight;
	}
	*//**
	 * @return Returns the totalParcelBags.
	 */
	public int getTotalParcelBags() {
		return totalParcelBags;
	}
	/**
	 * @param totalParcelBags The totalParcelBags to set.
	 */
	public void setTotalParcelBags(int totalParcelBags) {
		this.totalParcelBags = totalParcelBags;
	}
	/**
	 * @return Returns the totalParcelWeight.
	 */
	/*public double getTotalParcelWeight() {
		return totalParcelWeight;
	}
	*//**
	 * @param totalParcelWeight The totalParcelWeight to set.
	 *//*
	public void setTotalParcelWeight(double totalParcelWeight) {
		this.totalParcelWeight = totalParcelWeight;
	}*/
	/**
	 * @return Returns the destinationAirportName.
	 */
	public String getDestinationAirportName() {
		return destinationAirportName;
	}
	/**
	 * @param destinationAirportName The destinationAirportName to set.
	 */
	public void setDestinationAirportName(String destinationAirportName) {
		this.destinationAirportName = destinationAirportName;
	}
   
    

}
