package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import java.util.Collection;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.vo.TruckOrderMailVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-8061	:	17-Jul-2018	:	Draft
 */
public class TruckOrderMailVO extends AbstractVO{

	private String companyCode;
	private String truckOrderNumber;
	private int flightCarrierIdr;
	private String flightNumber;

	private int flightSequenceNumber;

	private Collection<TruckOrderMailAWBVO> truckOrderMailAWBVO;
	private Collection<TruckOrderMailBagVO> truckOrderMailBagVO;
	
	private String currencyCode;
	
	private String truckOrderStatus;
	
	
	/**
	 * 	Getter for currencyCode 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}
	/**
	 *  @param currencyCode the currencyCode to set
	 * 	Setter for currencyCode 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	/**
	 * 	Getter for companyCode 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 *  @param companyCode the companyCode to set
	 * 	Setter for companyCode 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * 	Getter for truckOrderNumber 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public String getTruckOrderNumber() {
		return truckOrderNumber;
	}
	/**
	 *  @param truckOrderNumber the truckOrderNumber to set
	 * 	Setter for truckOrderNumber 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public void setTruckOrderNumber(String truckOrderNumber) {
		this.truckOrderNumber = truckOrderNumber;
	}
	/**
	 * 	Getter for flightCarrierIdr 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public int getFlightCarrierIdr() {
		return flightCarrierIdr;
	}
	/**
	 *  @param flightCarrierIdr the flightCarrierIdr to set
	 * 	Setter for flightCarrierIdr 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public void setFlightCarrierIdr(int flightCarrierIdr) {
		this.flightCarrierIdr = flightCarrierIdr;
	}
	/**
	 * 	Getter for flightNumber 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
	/**
	 *  @param flightNumber the flightNumber to set
	 * 	Setter for flightNumber 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	/**
	 * 	Getter for flightSequenceNumber 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public int getFlightSequenceNumber() {
		return flightSequenceNumber;
	}
	/**
	 *  @param flightSequenceNumber the flightSequenceNumber to set
	 * 	Setter for flightSequenceNumber 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public void setFlightSequenceNumber(int flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}
	/**
	 * 	Getter for truckOrderMailAWBVO 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public Collection<TruckOrderMailAWBVO> getTruckOrderMailAWBVO() {
		return truckOrderMailAWBVO;
	}
	/**
	 *  @param truckOrderMailAWBVO the truckOrderMailAWBVO to set
	 * 	Setter for truckOrderMailAWBVO 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public void setTruckOrderMailAWBVO(Collection<TruckOrderMailAWBVO> truckOrderMailAWBVO) {
		this.truckOrderMailAWBVO = truckOrderMailAWBVO;
	}
	/**
	 * 	Getter for truckOrderMailBagVO 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public Collection<TruckOrderMailBagVO> getTruckOrderMailBagVO() {
		return truckOrderMailBagVO;
	}
	/**
	 *  @param truckOrderMailBagVO the truckOrderMailBagVO to set
	 * 	Setter for truckOrderMailBagVO 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public void setTruckOrderMailBagVO(Collection<TruckOrderMailBagVO> truckOrderMailBagVO) {
		this.truckOrderMailBagVO = truckOrderMailBagVO;
	}
	/**
	 * @return the truckOrderStatus
	 */
	public String getTruckOrderStatus() {
		return truckOrderStatus;
	}
	/**
	 * @param truckOrderStatus the truckOrderStatus to set
	 */
	public void setTruckOrderStatus(String truckOrderStatus) {
		this.truckOrderStatus = truckOrderStatus;
	}
	

	
}
