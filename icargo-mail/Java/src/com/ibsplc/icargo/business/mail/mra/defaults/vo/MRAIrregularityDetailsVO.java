/*
 * MRAIrregularityDetailsVO.java Created on 21 Oct 2008
*
* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
*
* This software is the proprietary information of IBS Software Services (P) Ltd.
* Use is subject to license terms.
*/
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
* @author A-3229
*
*/
public class MRAIrregularityDetailsVO extends AbstractVO{
	
	private String dsn;
	private String flightNumber;
	private LocalDate flightDate;
	private LocalDate offLoadedDate;
	private String offloadedStation;
	private double offloadedWeight;
	private String rescheduledFlightNumber;
	private LocalDate rescheduledFlightDate;
	private String route;
	private double weight;
	private String irregularityStatus;
	
	/**
	 * @return flightNumber
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
	/**
	 * @param flightNumber
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	/**
	 * @return flightDate
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}
	/**
	 * @param flightDate
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}
	/**
	 * @return offLoadedDate
	 */
	public LocalDate getOffLoadedDate() {
		return offLoadedDate;
	}
	/**
	 * @param offLoadedDate
	 */
	public void setOffLoadedDate(LocalDate offLoadedDate) {
		this.offLoadedDate = offLoadedDate;
	}
	/**
	 * @return offloadedStation
	 */ 
	public String getOffloadedStation() {
		return offloadedStation;
	}
	/**
	 * @param offloadedStation
	 */
	public void setOffloadedStation(String offloadedStation) {
		this.offloadedStation = offloadedStation;
	}
	/**
	 * @return offloadedWeight
	 */
	public double getOffloadedWeight() {
		return offloadedWeight;
	}
	/**
	 * @param offloadedWeight
	 */
	public void setOffloadedWeight(double offloadedWeight) {
		this.offloadedWeight = offloadedWeight;
	}
	/**
	 * @return rescheduledFlightNumber
	 */
	public String getRescheduledFlightNumber() {
		return rescheduledFlightNumber;
	}
	/**
	 * @param rescheduledFlightNumber
	 */
	public void setRescheduledFlightNumber(String rescheduledFlightNumber) {
		this.rescheduledFlightNumber = rescheduledFlightNumber;
	}
	/**
	 * @return rescheduledFlightDate
	 */
	public LocalDate getRescheduledFlightDate() {
		return rescheduledFlightDate;
	}
	/**
	 * @param rescheduledFlightDate
	 */
	public void setRescheduledFlightDate(LocalDate rescheduledFlightDate) {
		this.rescheduledFlightDate = rescheduledFlightDate;
	}
	/**
	 * @return route
	 */
	public String getRoute() {
		return route;
	}
	/**
	 * @param route
	 */
	public void setRoute(String route) {
		this.route = route;
	}
	/**
	 * @return weight
	 */
	public double getWeight() {
		return weight;
	}
	/**
	 * @param weight
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
	/**
	 * @return irregularityStatus
	 */
	public String getIrregularityStatus() {
		return irregularityStatus;
	}
	/**
	 * @param irregularityStatus
	 */
	public void setIrregularityStatus(String irregularityStatus) {
		this.irregularityStatus = irregularityStatus;
	}
	/**
	 * @return
	 */
	public String getDsn() {
		return dsn;
	}
	/**
	 * @param dsn
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}
	

}


