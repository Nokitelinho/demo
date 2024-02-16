/*
 * DailyMailStationReportVO.java Created onJUN 30, 2016
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author a-5991
 * 
 */
public class DailyMailStationReportVO extends AbstractVO {
	
	private String flightNumber;
	private int flightCarrireID;
	private int flightSeqNumber;
	private int segsernum;
	private String carrierCode;
	private String uldnum;
	private String destination;
	//private float netweight;
	//private float grossweight;
	private Measure netweight;//added by A-7371
	private Measure grossweight;
	private String remark;
	private String bagCount;
	/**
	 * 
	 * @return netweight
	 */
	public Measure getNetweight() {
		return netweight;
	}
	/**
	 * 
	 * @param netweight
	 */
	public void setNetweight(Measure netweight) {
		this.netweight = netweight;
	}
	/**
	 * 
	 * @return grossweight
	 */
	public Measure getGrossweight() {
		return grossweight;
	}
	/**
	 * 
	 * @param grossweight
	 */
	public void setGrossweight(Measure grossweight) {
		this.grossweight = grossweight;
	}
	/**
	 * @return the carrierCode
	 */
	public String getCarrierCode() {
		return carrierCode;
	}
	/**
	 * @param carrierCode the carrierCode to set
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
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
	 * @return the flightCarrireID
	 */
	public int getFlightCarrireID() {
		return flightCarrireID;
	}
	/**
	 * @param flightCarrireID the flightCarrireID to set
	 */
	public void setFlightCarrireID(int flightCarrireID) {
		this.flightCarrireID = flightCarrireID;
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
	 * @return the flightSeqNumber
	 */
	public int getFlightSeqNumber() {
		return flightSeqNumber;
	}
	/**
	 * @param flightSeqNumber the flightSeqNumber to set
	 */
	public void setFlightSeqNumber(int flightSeqNumber) {
		this.flightSeqNumber = flightSeqNumber;
	}
	/**
	 * @return the grossweight
	 */
	/*public float getGrossweight() {
		return grossweight;
	}
	*//**
	 * @param grossweight the grossweight to set
	 *//*
	public void setGrossweight(float grossweight) {
		this.grossweight = grossweight;
	}*/
	/**
	 * @return the netweight
	 */
	/*public float getNetweight() {
		return netweight;
	}
	*//**
	 * @param netweight the netweight to set
	 *//*
	public void setNetweight(float netweight) {
		this.netweight = netweight;
	}*/
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the uldnum
	 */
	public String getUldnum() {
		return uldnum;
	}
	/**
	 * @param uldnum the uldnum to set
	 */
	public void setUldnum(String uldnum) {
		this.uldnum = uldnum;
	}
	/**
	 * @return the segsernum
	 */
	public int getSegsernum() {
		return segsernum;
	}
	/**
	 * @param segsernum the segsernum to set
	 */
	public void setSegsernum(int segsernum) {
		this.segsernum = segsernum;
	}
	/**
	 * @return Returns the bagCount.
	 */
	public String getBagCount() {
		return bagCount;
	}
	/**
	 * @param bagCount The bagCount to set.
	 */
	public void setBagCount(String bagCount) {
		this.bagCount = bagCount;
	}
	
}
