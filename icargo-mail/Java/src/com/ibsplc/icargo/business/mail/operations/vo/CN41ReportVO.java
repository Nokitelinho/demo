/*
 * CN41ReportVO.java Created on Jun 30, 2016
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
public class CN41ReportVO extends AbstractVO {
    /* Office of originof the CN38 bill*/
    private String originAirport;
    
    /* Airport of direct transhipment*/
    private String pou;
    
    /* Airport of offloading*/
    private String destinationAirport;
    
    /* Destination Office of CN38 bill. Set description*/
    private String destinationAirportName;
    private String pouName;
    
    private String controlDocumentNumber;
    private String flightCarrierCode;
    private String flightNumber;
    private LocalDate flightDate; 
    private LocalDate atd;
    
    private Collection<MailInReportVO> mailInReportVOs;
    
    private int totalLetterBags;
    private int totalParcelBags;
    private int totalEMSBags;
    
  /*  private double totalLetterWeight;
    private double totalParcelWeight;
    private double totalEMSWeight;*/
    
    private Measure totalLetterWeight;//added by A-7371
    private Measure totalParcelWeight;
    private Measure totalEMSWeight;
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
	 * 
	 * @return totalEMSWeight
	 */
	public Measure getTotalEMSWeight() {
		return totalEMSWeight;
	}
	/**
	 * 
	 * @param totalEMSWeight
	 */
	public void setTotalEMSWeight(Measure totalEMSWeight) {
		this.totalEMSWeight = totalEMSWeight;
	}
	/**
	 * @return Returns the atd.
	 */
	public LocalDate getAtd() {
		return atd;
	}
	/**
	 * @param atd The atd to set.
	 */
	public void setAtd(LocalDate atd) {
		this.atd = atd;
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
	 * @return Returns the pouName.
	 */
	public String getPouName() {
		return pouName;
	}
	/**
	 * @param pouName The pouName to set.
	 */
	public void setPouName(String pouName) {
		this.pouName = pouName;
	}
	/**
	 * @return Returns the totalEMSBags.
	 */
	public int getTotalEMSBags() {
		return totalEMSBags;
	}
	/**
	 * @param totalEMSBags The totalEMSBags to set.
	 */
	public void setTotalEMSBags(int totalEMSBags) {
		this.totalEMSBags = totalEMSBags;
	}
	/**
	 * @return Returns the totalEMSWeight.
	 */
	/*public double getTotalEMSWeight() {
		return totalEMSWeight;
	}
	*//**
	 * @param totalEMSWeight The totalEMSWeight to set.
	 *//*
	public void setTotalEMSWeight(double totalEMSWeight) {
		this.totalEMSWeight = totalEMSWeight;
	}*/
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
	}*/
	/**
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

}
