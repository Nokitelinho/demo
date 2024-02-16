/*
 * MailAlertMessageVO.java Created on JUN 30, 2016
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * 
 */
package com.ibsplc.icargo.business.mail.operations.vo;


import java.util.Collection;

import com.ibsplc.icargo.business.msgbroker.message.vo.BaseMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;

/**
 * @author A-5991
 *
 */
public class MailAlertMessageVO extends BaseMessageVO{

	
	/**
     * The Message Types
     */
	public static final String MAILALERT = "MAILALERT" ;
	
	/**
     * The Party Type
     */
	public static final String PARTY_TYPE = "US" ;
	
	/**
     * The Party Type
     */
	public static final String MESSAGE_STANDARD = "IFCSTD" ;
	
	/**
     * The HEADL
     */
	public static final String HEADL = "Mail Alert - Flight Closed" ;
	
	
	/**
     * The FLTNUML
     */
	public static final String FLTNUML = "Flight No" ;
	/**
     * The Space
     */
	public static final String SPC1 = "           " ;
	/**
     * The FLTNUMD
     */
	public static final String FLTNUMD = "FLTNUMD" ;
	/**
     * The FLTDATL
     */
	public static final String FLTDATL = "Departure Date" ;
	/**
     * The Space
     */
	public static final String SPC2 = "      " ;
	/**
     * The FLTNUMD
     */
	public static final String FLTDATD = "FLTDATD" ;
	/**
     * The DEPTPORTL
     */
	public static final String DEPTPORTL = "Departure Port" ;
	/**
     * The SPC3
     */
	public static final String SPC3 = "      " ;
	/**
     * The DEPTPORTD
     */
	public static final String DEPTPORTD = "DEPTPORTD" ;

	/**
     * The ROUTEL
     */
	public static final String ROUTEL = "Route" ;
	/**
     * The SPC4
     */
	public static final String SPC4 = "               " ;
	/**
     * The ROUTED
     */
	public static final String ROUTED = "ROUTED" ;
	
	/**
     * The PAR1
     */
	public static final String PAR1 = "PAR1" ;
	
	//header 2
	/**
     * The CONDETL
     */
	public static final String CONDETL = "Container Details(Container Name,POU,No of Bags,Weight)";
	/**
     * The FLTNUMD
     */
	public static final String UNDL1    = "-------------------------------------------------------";
	
	//data ids
	/**
     * The CONTNAMED
     */
	public static final String CONTNAMED = "CONTNAMED" ;
	/**
     * The POUD
     */
	public static final String POUD = "POUD" ;
	/**
     * The NOBD
     */
	public static final String NOBD = "NOBD" ;
	/**
     * The WGTD
     */
	public static final String WGTD = "WGTD" ;
	/**
     * The PAR2
     */	
	public static final String PAR2 = "PAR2" ;
		
	
	//	header 3
	/**
     * The DISINFOL
     */
	public static final String DISINFOL = "Dispatch Information(DSN,Origin OE,Dest OE,Category,No Of Bags,Weight(Unit),AWB Number,POU)";
	/**
     * The UNDL2
     */
	public static final String UNDL2    = "-------------------------------------------------------------------------------------------";
	
	//data ids
	/**
     * The DSND
     */
	public static final String DSND = "DSND" ;
	/**
     * The OOED
     */
	public static final String OOED = "OOED" ;
	/**
     * The DOED
     */
	public static final String DOED = "DOED" ;
	/**
     * The CATD
     */
	public static final String CATD = "CATD" ;
	/**
     * The NOIBD
     */
	public static final String NOIBD = "NOIBD" ;
	/**
     * The WGTUNTD
     */
	public static final String WGTUNTD = "WGTUNTD" ;
	/**
     * The AWBNOD
     */
	public static final String AWBNOD = "AWBNOD" ;
	/**
     * The POUIND
     */
	public static final String POUIND = "POUIND" ;
	
	//data values
	private String  flightnum;
	private LocalDate departureDate ;
	private String  deptport;
	private String  route;
	private Collection<ContainerDetailsVO> condatails;
	private String  airlinecode;
	
	private Collection<MessageDespatchDetailsVO> messageDespatchDetailsVOs;
	
	private Collection<String> stations; 

	/**
	 * @return the stations
	 */
	public Collection<String> getStations() {
		return stations;
	}
	/**
	 * @param stations the stations to set
	 */
	public void setStations(Collection<String> stations) {
		this.stations = stations;
	}
	/**
	 * @return the messageDespatchDetailsVOs
	 */
	public Collection<MessageDespatchDetailsVO> getMessageDespatchDetailsVOs() {
		return messageDespatchDetailsVOs;
	}
	/**
	 * @param messageDespatchDetailsVOs the messageDespatchDetailsVOs to set
	 */
	public void setMessageDespatchDetailsVOs(
			Collection<MessageDespatchDetailsVO> messageDespatchDetailsVOs) {
		this.messageDespatchDetailsVOs = messageDespatchDetailsVOs;
	}
	/**
	 * @return the airlinecode
	 */
	public String getAirlinecode() {
		return airlinecode;
	}
	/**
	 * @param airlinecode the airlinecode to set
	 */
	public void setAirlinecode(String airlinecode) {
		this.airlinecode = airlinecode;
	}
	/**
	 * @return the condatails
	 */
	public Collection<ContainerDetailsVO> getCondatails() {
		return condatails;
	}
	/**
	 * @param condatails the condatails to set
	 */
	public void setCondatails(Collection<ContainerDetailsVO> condatails) {
		this.condatails = condatails;
	}
	/**
	 * @return the departureDate
	 */
	public LocalDate getDepartureDate() {
		return departureDate;
	}
	/**
	 * @param departureDate the departureDate to set
	 */
	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}
	/**
	 * @return the deptport
	 */
	public String getDeptport() {
		return deptport;
	}
	/**
	 * @param deptport the deptport to set
	 */
	public void setDeptport(String deptport) {
		this.deptport = deptport;
	}
	/**
	 * @return the flightnum
	 */
	public String getFlightnum() {
		return flightnum;
	}
	/**
	 * @param flightnum the flightnum to set
	 */
	public void setFlightnum(String flightnum) {
		this.flightnum = flightnum;
	}
	/**
	 * @return the route
	 */
	public String getRoute() {
		return route;
	}
	/**
	 * @param route the route to set
	 */
	public void setRoute(String route) {
		this.route = route;
	}
	
}
