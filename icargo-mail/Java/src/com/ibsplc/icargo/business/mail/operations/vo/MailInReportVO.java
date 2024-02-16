/*
 * MailInReportVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-3109
 *
 */
public class MailInReportVO extends AbstractVO {
    private String dsn;
    private String originOffice;
    private String destinationOffice;
    private String mailId;
    private String rsn;
    //private double weightOfLetters;
    private Measure weightOfLetters;//added by A-7371
    //private double weightOfParcels;
    private Measure weightOfParcels;//added by A-7371
    //private double weightOfExpressMails;
    private Measure weightOfExpressMails;//added by A-7371
    private int numberOfLetters;
    private int numberOfParcels;
    private int numberOfExpressMails;
    private String uldNumber;
    private String remarks;
    
	/**
     * 
     * @return weightOfLetters
     */
	public Measure getWeightOfLetters() {
		return weightOfLetters;
	}
	/**
	 * 
	 * @param weightOfLetters
	 */
	public void setWeightOfLetters(Measure weightOfLetters) {
		this.weightOfLetters = weightOfLetters;
	}
	/**
	 * 
	 * @return weightOfParcels
	 */
	public Measure getWeightOfParcels() {
		return weightOfParcels;
	}
	/**
	 * 
	 * @param weightOfParcels
	 */
	public void setWeightOfParcels(Measure weightOfParcels) {
		this.weightOfParcels = weightOfParcels;
	}
	/**
	 * 
	 * @return weightOfExpressMails
	 */
	public Measure getWeightOfExpressMails() {
		return weightOfExpressMails;
	}
	/**
	 * 
	 * @param weightOfExpressMails
	 */
	public void setWeightOfExpressMails(Measure weightOfExpressMails) {
		this.weightOfExpressMails = weightOfExpressMails;
	}
	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return Returns the destinationOffice.
	 */
	public String getDestinationOffice() {
		return destinationOffice;
	}
	/**
	 * @param destinationOffice The destinationOffice to set.
	 */
	public void setDestinationOffice(String destinationOffice) {
		this.destinationOffice = destinationOffice;
	}
	/**
	 * @return Returns the dsn.
	 */
	public String getDsn() {
		return dsn;
	}
	/**
	 * @param dsn The dsn to set.
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}
	/**
	 * @return Returns the mailId.
	 */
	public String getMailId() {
		return mailId;
	}
	/**
	 * @param mailId The mailId to set.
	 */
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	/**
	 * @return Returns the originOffice.
	 */
	public String getOriginOffice() {
		return originOffice;
	}
	/**
	 * @param originOffice The originOffice to set.
	 */
	public void setOriginOffice(String originOffice) {
		this.originOffice = originOffice;
	}
	/**
	 * @return Returns the rsn.
	 */
	public String getRsn() {
		return rsn;
	}
	/**
	 * @param rsn The rsn to set.
	 */
	public void setRsn(String rsn) {
		this.rsn = rsn;
	}
	/**
	 * @return Returns the uldNumber.
	 */
	public String getUldNumber() {
		return uldNumber;
	}
	/**
	 * @param uldNumber The uldNumber to set.
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
	/**
	 * @return Returns the weightOfExpressMails.
	 */
	/*public double getWeightOfExpressMails() {
		return weightOfExpressMails;
	}
	*//**
	 * @param weightOfExpressMails The weightOfExpressMails to set.
	 *//*
	public void setWeightOfExpressMails(double weightOfExpressMails) {
		this.weightOfExpressMails = weightOfExpressMails;
	}
	*//**
	 * @return Returns the weightOfLetters.
	 *//*
	public double getWeightOfLetters() {
		return weightOfLetters;
	}
	*//**
	 * @param weightOfLetters The weightOfLetters to set.
	 *//*
	public void setWeightOfLetters(double weightOfLetters) {
		this.weightOfLetters = weightOfLetters;
	}
	*//**
	 * @return Returns the weightOfParcels.
	 *//*
	public double getWeightOfParcels() {
		return weightOfParcels;
	}
	*//**
	 * @param weightOfParcels The weightOfParcels to set.
	 *//*
	public void setWeightOfParcels(double weightOfParcels) {
		this.weightOfParcels = weightOfParcels;
	}*/
	/**
	 * @return Returns the numberOfExpressMails.
	 */
	public int getNumberOfExpressMails() {
		return numberOfExpressMails;
	}
	/**
	 * @param numberOfExpressMails The numberOfExpressMails to set.
	 */
	public void setNumberOfExpressMails(int numberOfExpressMails) {
		this.numberOfExpressMails = numberOfExpressMails;
	}
	/**
	 * @return Returns the numberOfLetters.
	 */
	public int getNumberOfLetters() {
		return numberOfLetters;
	}
	/**
	 * @param numberOfLetters The numberOfLetters to set.
	 */
	public void setNumberOfLetters(int numberOfLetters) {
		this.numberOfLetters = numberOfLetters;
	}
	/**
	 * @return Returns the numberOfParcels.
	 */
	public int getNumberOfParcels() {
		return numberOfParcels;
	}
	/**
	 * @param numberOfParcels The numberOfParcels to set.
	 */
	public void setNumberOfParcels(int numberOfParcels) {
		this.numberOfParcels = numberOfParcels;
	}
    
    
    
    
    
    

}
