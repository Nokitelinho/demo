/*
 * MailHandedOverVO.java Created on Jun 30, 2016
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3109
 *
 */
public class MailHandedOverVO extends AbstractVO{

	private String mailbagId;
	private String dsn;
	private String inwardFlightCarrierCode;
	private String inwardFlightNum;
	private LocalDate inwardFlightDate;
	private LocalDate onwardFlightDate; 
	private String onwardCarrier;
	private String onwardFlightNum;
	private String ooe;
	private String doe;
	//private Double weight;
	private Measure weight;//added by A-7371
	
	/**
	 * 
	 * @return weight
	 */
	public Measure getWeight() {
		return weight;
	}
	/**
	 * 
	 * @param weight
	 */
	public void setWeight(Measure weight) {
		this.weight = weight;
	}
	/**
	 * @return the doe
	 */
	public String getDoe() {
		return doe;
	}
	/**
	 * @param doe the doe to set
	 */
	public void setDoe(String doe) {
		this.doe = doe;
	}
	/**
	 * @return the dsn
	 */
	public String getDsn() {
		return dsn;
	}
	/**
	 * @param dsn the dsn to set
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}
	/**
	 * @return the inwardFlightCarrierCode
	 */
	public String getInwardFlightCarrierCode() {
		return inwardFlightCarrierCode;
	}
	/**
	 * @param inwardFlightCarrierCode the inwardFlightCarrierCode to set
	 */
	public void setInwardFlightCarrierCode(String inwardFlightCarrierCode) {
		this.inwardFlightCarrierCode = inwardFlightCarrierCode;
	}
	/**
	 * @return the inwardFlightDate
	 */
	public LocalDate getInwardFlightDate() {
		return inwardFlightDate;
	}
	/**
	 * @param inwardFlightDate the inwardFlightDate to set
	 */
	public void setInwardFlightDate(LocalDate inwardFlightDate) {
		this.inwardFlightDate = inwardFlightDate;
	}
	/**
	 * @return the onwardFlightDate
	 */
	public LocalDate getOnwardFlightDate() {
		return onwardFlightDate;
	}
	/**
	 * @param onwardFlightDate the onwardFlightDate to set
	 */
	public void setOnwardFlightDate(LocalDate onwardFlightDate) {
		this.onwardFlightDate = onwardFlightDate;
	}
	/**
	 * @return the inwardFlightNum
	 */
	public String getInwardFlightNum() {
		return inwardFlightNum;
	}
	/**
	 * @param inwardFlightNum the inwardFlightNum to set
	 */
	public void setInwardFlightNum(String inwardFlightNum) {
		this.inwardFlightNum = inwardFlightNum;
	}
	/**
	 * @return the onwardCarrier
	 */
	public String getOnwardCarrier() {
		return onwardCarrier;
	}
	/**
	 * @param onwardCarrier the onwardCarrier to set
	 */
	public void setOnwardCarrier(String onwardCarrier) {
		this.onwardCarrier = onwardCarrier;
	}
	/**
	 * @return the onwardFlightNum
	 */
	public String getOnwardFlightNum() {
		return onwardFlightNum;
	}
	/**
	 * @param onwardFlightNum the onwardFlightNum to set
	 */
	public void setOnwardFlightNum(String onwardFlightNum) {
		this.onwardFlightNum = onwardFlightNum;
	}
	/**
	 * @return the ooe
	 */
	public String getOoe() {
		return ooe;
	}
	/**
	 * @param ooe the ooe to set
	 */
	public void setOoe(String ooe) {
		this.ooe = ooe;
	}
	/**
	 * @return the weight
	 */
	/*public Double getWeight() {
		return weight;
	}
	*//**
	 * @param weight the weight to set
	 *//*
	public void setWeight(Double weight) {
		this.weight = weight;
	}*/
	/**
	 * @return the mailbagId
	 */
	public String getMailbagId() {
		return mailbagId;
	}
	/**
	 * @param mailbagId the mailbagId to set
	 */
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}
	
}
