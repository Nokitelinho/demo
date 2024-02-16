package com.ibsplc.icargo.presentation.web.model.mail.mra.common;

import com.ibsplc.icargo.framework.util.time.LocalDate;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.mra.common.FlightDetail.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8061	:	10-Dec-2020	:	Draft
 */
public class FlightDetail {

	
	
	private String flightNumber;
	private String  carrierCode;
	private String flightDate;
	private String pol;
	private String pou;
	private boolean truck;
	private String blockSpace;
	private String agreementType;
	private int carrierId;
	private long flightSeqNum;
	private String flightType;
	private int segmentSerNum;
	
	 
	
	/**
	 * 	Getter for flightNumber 
	 *	Added by : A-8061 on 10-Dec-2020
	 * 	Used for :
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
	/**
	 *  @param flightNumber the flightNumber to set
	 * 	Setter for flightNumber 
	 *	Added by : A-8061 on 10-Dec-2020
	 * 	Used for :
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	/**
	 * 	Getter for carrierCode 
	 *	Added by : A-8061 on 10-Dec-2020
	 * 	Used for :
	 */
	public String getCarrierCode() {
		return carrierCode;
	}
	/**
	 *  @param carrierCode the carrierCode to set
	 * 	Setter for carrierCode 
	 *	Added by : A-8061 on 10-Dec-2020
	 * 	Used for :
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	/**
	 * 	Getter for flightDate 
	 *	Added by : A-8061 on 10-Dec-2020
	 * 	Used for :
	 */
	public String getFlightDate() {
		return flightDate;
	}
	/**
	 *  @param flightDate the flightDate to set
	 * 	Setter for flightDate 
	 *	Added by : A-8061 on 10-Dec-2020
	 * 	Used for :
	 */
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}
	/**
	 * 	Getter for pol 
	 *	Added by : A-8061 on 10-Dec-2020
	 * 	Used for :
	 */
	public String getPol() {
		return pol;
	}
	/**
	 *  @param pol the pol to set
	 * 	Setter for pol 
	 *	Added by : A-8061 on 10-Dec-2020
	 * 	Used for :
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}
	/**
	 * 	Getter for pou 
	 *	Added by : A-8061 on 10-Dec-2020
	 * 	Used for :
	 */
	public String getPou() {
		return pou;
	}
	/**
	 *  @param pou the pou to set
	 * 	Setter for pou 
	 *	Added by : A-8061 on 10-Dec-2020
	 * 	Used for :
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}
	/**
	 * 	Getter for truck 
	 *	Added by : A-8061 on 10-Dec-2020
	 * 	Used for :
	 */
	public boolean isTruck() {
		return truck;
	}
	/**
	 *  @param truck the truck to set
	 * 	Setter for truck 
	 *	Added by : A-8061 on 10-Dec-2020
	 * 	Used for :
	 */
	public void setTruck(boolean truck) {
		this.truck = truck;
	}
	/**
	 * 	Getter for blockSpace 
	 *	Added by : A-8061 on 10-Dec-2020
	 * 	Used for :
	 */
	public String getBlockSpace() {
		return blockSpace;
	}
	/**
	 *  @param blockSpace the blockSpace to set
	 * 	Setter for blockSpace 
	 *	Added by : A-8061 on 10-Dec-2020
	 * 	Used for :
	 */
	public void setBlockSpace(String blockSpace) {
		this.blockSpace = blockSpace;
	}
	/**
	 * 	Getter for agreementType 
	 *	Added by : A-8061 on 10-Dec-2020
	 * 	Used for :
	 */
	public String getAgreementType() {
		return agreementType;
	}
	/**
	 *  @param agreementType the agreementType to set
	 * 	Setter for agreementType 
	 *	Added by : A-8061 on 10-Dec-2020
	 * 	Used for :
	 */
	public void setAgreementType(String agreementType) {
		this.agreementType = agreementType;
	}
	/**
	 * 	Getter for carrierId 
	 *	Added by : A-8061 on 14-Dec-2020
	 * 	Used for :
	 */
	public int getCarrierId() {
		return carrierId;
	}
	/**
	 *  @param carrierId the carrierId to set
	 * 	Setter for carrierId 
	 *	Added by : A-8061 on 14-Dec-2020
	 * 	Used for :
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}
	/**
	 * 	Getter for flightSeqNum 
	 *	Added by : A-8061 on 14-Dec-2020
	 * 	Used for :
	 */
	public long getFlightSeqNum() {
		return flightSeqNum;
	}
	/**
	 *  @param flightSeqNum the flightSeqNum to set
	 * 	Setter for flightSeqNum 
	 *	Added by : A-8061 on 14-Dec-2020
	 * 	Used for :
	 */
	public void setFlightSeqNum(long flightSeqNum) {
		this.flightSeqNum = flightSeqNum;
	}
	/**
	 * 	Getter for flightType 
	 *	Added by : A-8061 on 14-Dec-2020
	 * 	Used for :
	 */
	public String getFlightType() {
		return flightType;
	}
	/**
	 *  @param flightType the flightType to set
	 * 	Setter for flightType 
	 *	Added by : A-8061 on 14-Dec-2020
	 * 	Used for :
	 */
	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}
	/**
	 * 	Getter for segmentSerNum 
	 *	Added by : A-8061 on 15-Dec-2020
	 * 	Used for :
	 */
	public int getSegmentSerNum() {
		return segmentSerNum;
	}
	/**
	 *  @param segmentSerNum the segmentSerNum to set
	 * 	Setter for segmentSerNum 
	 *	Added by : A-8061 on 15-Dec-2020
	 * 	Used for :
	 */
	public void setSegmentSerNum(int segmentSerNum) {
		this.segmentSerNum = segmentSerNum;
	}
	
	
	
	
	
	
}
