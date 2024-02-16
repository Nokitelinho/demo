/*
 * FlightAcceptanceDetailsMultiMapper.java Created on May 29, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRouteForSegmentVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-1936
 *
 */

public class FlightAcceptanceDetailsMultiMapper implements MultiMapper<ContainerDetailsVO> {
	 private Log log= LogFactory.getLogger("MAILTRACKING_DEFAULTS");
     
	 /**
	  * The Map method that can be used to construct the List containing the 
	  * ContainerDetailsVo
	  * @author a-1936
	  * @param rs
	  * @return
	  * @throws SQLException
	  */
	  public  List<ContainerDetailsVO>  map(ResultSet rs) throws SQLException {
		  List<ContainerDetailsVO> containerDetailsVOsList = new ArrayList<ContainerDetailsVO>();
		  ContainerDetailsVO containerDetailsVO = null; 
		  String currULDKey = "";
		  String prevULDKey = "";
		  String currOnwardKey = "";
		  String prevOnwardKey = "";
		  String pou = null;
		  String flightNumber = null;
		  String carrierCode = null;
		  StringBuilder onwardFlightBuilder = null;
		  String currDSNKey = null;
		  String prevDSNKey = null;
		  Collection<OnwardRouteForSegmentVO> onwardRoutingVos = null;
		  DSNVO dsnVO = null;
		  Collection<DSNVO> dsnVOs = null;
		  
		  while(rs.next()){
			 /*
			  * Added By Karthick V 
			  * Only the Container Number is part of the Parent Pk 
			  * since  the Container Number cannot repeat for the 
			  * Segment ...Under the assumption say 
			  * 
			  * Let us assume that the Flight F1 is From TRV-DXB-LHR 
			  * At TRV a  container say AKE12345QF can be assigned to F1 with 4 MailBags  
			  * At DXB again the same container AKE12345QF can be added up with the MailBags
			  * But its LegSerial Number  it will be different than the one at TRV 
			  * 
			  * Assuming this at TRV We can  see only the mailBags accepted at the TRV.
			  * Similarly at DXB  we can see the  mailBags which are accepted at the DXB in
			  * that container .
			  *  
			  * But at LHR both the Containers can be viewed as a part of the Arrival...
			  */
			 currULDKey = new StringBuilder(rs.getString("CONNUM")).toString();
			 log.log(Log.FINE, "The NEW ParentID is Found to be ", currULDKey);
			/*
			 * In following cases this container should be displayed 1.
			 * Container is accepted but not arrived 2 Container is accepted and
			 * arrived 3. Container is not accepted and not arrived but assigned
			 * DONOT DISPLAY IN FOLLOWING CASE If the Container is Arrived at
			 * the POU but was not accepted at the POL then DONOT display this
			 * container. 
			 * In other words all those Containers for which the Acceptance has been
			 * made  at the Particular Port alone has to be displayed .
			 */
			if (MailConstantsVO.FLAG_NO.equals(rs.getString("ACPFLG"))
					&& MailConstantsVO.FLAG_YES.equals(rs.getString("ARRSTA"))) {
				continue;
			}
			
			 //donont display trash in acceptance
			if(currULDKey.startsWith(MailConstantsVO.BULK_TRASH)){
				continue;
			}
		 	if(!currULDKey.equals(prevULDKey)) {
				  containerDetailsVO = new ContainerDetailsVO();
				  populateContainerDetails(containerDetailsVO, rs);
				  containerDetailsVOsList.add(containerDetailsVO);
				  prevULDKey = currULDKey;
				  onwardRoutingVos = new ArrayList<OnwardRouteForSegmentVO>();
				  onwardFlightBuilder = new StringBuilder();
				  dsnVOs = new ArrayList<DSNVO>();				
				  containerDetailsVO.setOnwardRoutingForSegmentVOs(onwardRoutingVos);
				  containerDetailsVO.setDsnVOs(dsnVOs);
			}
			/**
			 * 
			 *  For each RoutingSerialNum create a String like EK
			 *  1303-30/6/2006-DXB,EK 1305-30/6/2006-TRV (FLTNUM-FLTDAT-POU)
			 *  Collect all  the Routings for the Segment...
			 */
			if(rs.getInt("RTGSERNUM") > 0) {
				 currOnwardKey = new StringBuffer(currULDKey).append(
						rs.getString("RTGSERNUM")).toString();
				if(!currOnwardKey.equals(prevOnwardKey)) {
					flightNumber = rs.getString("ONWFLTNUM");
					pou = rs.getString("RTGPOU");
					carrierCode = rs.getString("ONWFLTCARCOD");

					OnwardRouteForSegmentVO onwardRouteForSegmentVO = new OnwardRouteForSegmentVO();
					onwardRouteForSegmentVO.setOnwardCarrierCode(carrierCode);
					onwardRouteForSegmentVO.setOnwardCarrierId(rs
							.getInt("ONWFLTCARIDR"));

					if (rs.getString("ONWFLTDAT") != null) {
						LocalDate localdate = new LocalDate(rs
								.getString("ASGPRT"), Location.ARP, false);
						localdate.setDate(rs.getString("ONWFLTDAT"));

						onwardRouteForSegmentVO.setOnwardFlightDate(localdate);
					}
					onwardRouteForSegmentVO.setOnwardFlightNumber(flightNumber);
					onwardRouteForSegmentVO.setPou(pou);
					onwardRouteForSegmentVO.setRoutingSerialNumber(rs
							.getInt("RTGSERNUM"));

					onwardRoutingVos.add(onwardRouteForSegmentVO);
					/*
					 * iF FlightNumber,FlightDate ,Pou exists For each
					 * RoutingSerialNum create a String like EK
					 * 1303-30/6/2006-DXB
					 */
					if (rs.getString("ONWFLTDAT") != null
							&& flightNumber != null
							&& flightNumber.trim().length() > 0
							&& carrierCode != null
							&& carrierCode.trim().length() > 0 && pou != null
							&& pou.trim().length() > 0) {

						if(onwardFlightBuilder.length() > 0) {
							onwardFlightBuilder.append(", ");
						}
						
						onwardFlightBuilder.append(carrierCode).append("-")
								.append(flightNumber).append("-").append(
										rs.getString("ONWFLTDAT")).append("-")
								.append(pou);
						
						containerDetailsVO.setRoute(onwardFlightBuilder.toString());
					}
					prevOnwardKey = currOnwardKey;
				}
			}
			String dsn = rs.getString("DSN");
			 /*
			  * 
			  * avoid all dsns which are having 0 accepted bags, ie., they are
			  * newly arrived only. but not accepted since there is no need to display the
			  * DSNS which are newly Arrived as they are not actually not Accepted at 
			  * the Particular Port 
			  */
			if(dsn != null && (rs.getInt("DSNACPBAG") > 0)) {
				 currDSNKey = new StringBuilder().
				   append(currULDKey)
				   .append(rs.getString("DSN"))
				   .append(rs.getString("ORGEXGOFC"))
				   .append(rs.getString("DSTEXGOFC")).append(
				   rs.getString("MALSUBCLS"))
				   .append(rs.getString("MALCTGCOD"))
				   .append(rs.getInt("YER")).toString();
				 log.log(Log.FINE, "currDSNKey ", currDSNKey);
				if(!currDSNKey.equals(prevDSNKey)) {
					dsnVO = new DSNVO();
					populateDSNDetails(dsnVO, rs,containerDetailsVO);
					dsnVOs.add(dsnVO);
					prevDSNKey = currDSNKey;
				 }else{
					 
					 if(MailConstantsVO.BULK_TYPE.equals(rs.getString("CONTYP"))){
						 if(dsnVO!=null){
							 dsnVO.setBags(dsnVO.getBags()+rs.getInt("DSNACPBAG"));
							 dsnVO.setPrevBagCount(dsnVO.getPrevBagCount()+rs.getInt("DSNACPBAG"));
							 //dsnVO.setWeight( dsnVO.getWeight()+rs.getDouble("DSNACPWGT"));
							 try {
								dsnVO.setWeight(Measure.addMeasureValues(dsnVO.getWeight(),new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNACPWGT"))));
							} catch (UnitException e3) {
								// TODO Auto-generated catch block
								log.log(Log.SEVERE, "UnitException",e3.getMessage());
							}//added by A-7371
							 //dsnVO.setPrevBagWeight(dsnVO.getPrevBagWeight()+rs.getDouble("DSNACPWGT"));
							 try {
								dsnVO.setPrevBagWeight(Measure.addMeasureValues(dsnVO.getPrevBagWeight(),new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNACPWGT"))));
							} catch (UnitException e2) {
								// TODO Auto-generated catch block
								log.log(Log.SEVERE, "UnitException",e2.getMessage());
							}//added by A-7371
							 dsnVO.setStatedBags(dsnVO.getStatedBags()+rs.getInt("DSNSTDBAG"));
							 dsnVO.setPrevStatedBags(dsnVO.getPrevStatedBags()+rs.getInt("DSNSTDBAG"));
							 //dsnVO.setStatedWeight(dsnVO.getStatedWeight()+rs.getDouble("DSNSTDWGT"));
							 try {
								dsnVO.setStatedWeight(Measure.addMeasureValues(dsnVO.getStatedWeight(), new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNSTDWGT"))));
							} catch (UnitException e1) {
								// TODO Auto-generated catch block
								log.log(Log.SEVERE, "UnitException",e1.getMessage());
							}//added by A-7371
							 //dsnVO.setPrevStatedWeight(dsnVO.getPrevStatedWeight()+rs.getDouble("DSNSTDWGT"));
							 try {
								dsnVO.setPrevStatedWeight(Measure.addMeasureValues(dsnVO.getPrevStatedWeight(), new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNSTDWGT"))));
							} catch (UnitException e) {
								// TODO Auto-generated catch block
								log.log(Log.SEVERE, "UnitException",e.getMessage());
							}//added by A-7371
						 }
						 containerDetailsVO.setTotalBags(containerDetailsVO.getTotalBags()+rs.getInt("DSNACPBAG"));
						 //containerDetailsVO.setTotalWeight(containerDetailsVO.getTotalWeight()+rs.getDouble("DSNACPWGT"));
						 try {
							containerDetailsVO.setTotalWeight(Measure.addMeasureValues(containerDetailsVO.getTotalWeight(), new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNACPWGT"))));
						} catch (UnitException e) {
							// TODO Auto-generated catch block
							log.log(Log.SEVERE, "UnitException",e.getMessage());
						}//added by A-7371
					 }else{
						 if(dsnVO!=null){
							 dsnVO.setBags(dsnVO.getBags()+rs.getInt("DSNACPBAG"));
							 dsnVO.setPrevBagCount(dsnVO.getPrevBagCount()+rs.getInt("DSNACPBAG"));
							 //dsnVO.setWeight( dsnVO.getWeight()+rs.getDouble("DSNACPWGT"));
							 try {
								dsnVO.setWeight(Measure.addMeasureValues(dsnVO.getWeight(),new Measure(UnitConstants.MAIL_WGT, rs.getDouble("DSNACPWGT"))));
							} catch (UnitException e3) {
								// TODO Auto-generated catch block
								log.log(Log.SEVERE, "UnitException",e3.getMessage());
							}//added by A-7371
							 //dsnVO.setPrevBagWeight(dsnVO.getPrevBagWeight()+rs.getDouble("DSNACPWGT"));
							 try {
								dsnVO.setPrevBagWeight(Measure.addMeasureValues(dsnVO.getPrevBagWeight(), new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNACPWGT"))));
							} catch (UnitException e2) {
								// TODO Auto-generated catch block
								log.log(Log.SEVERE, "UnitException",e2.getMessage());
							}//added by A-7371
							 dsnVO.setStatedBags(dsnVO.getStatedBags()+rs.getInt("DSNSTDBAG"));
							 dsnVO.setPrevStatedBags(dsnVO.getPrevStatedBags()+rs.getInt("DSNSTDBAG"));
							// dsnVO.setStatedWeight(dsnVO.getStatedWeight()+rs.getDouble("DSNSTDWGT"));
							 try {
								dsnVO.setStatedWeight(Measure.addMeasureValues(dsnVO.getStatedWeight(),new Measure(UnitConstants.MAIL_WGT, rs.getDouble("DSNSTDWGT"))));
							} catch (UnitException e1) {
								// TODO Auto-generated catch block
								log.log(Log.SEVERE, "UnitException",e1.getMessage());
							}//added by A-7371
							// dsnVO.setPrevStatedWeight(dsnVO.getPrevStatedWeight()+rs.getDouble("DSNSTDWGT"));
							 try {
								dsnVO.setPrevStatedWeight(Measure.addMeasureValues(dsnVO.getPrevStatedWeight(), new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNSTDWGT"))));
							} catch (UnitException e) {
								// TODO Auto-generated catch block
								log.log(Log.SEVERE, "UnitException",e.getMessage());
							}//added by A-7371
						 }
					 }
				 }
			}
		}
		return containerDetailsVOsList;
	}

	/**
	 * A-1936
	 * 
	 * @param dsnVO
	 * @param rs
	 * @throws SQLException
	 */
	private void populateDSNDetails(DSNVO dsnVO, ResultSet rs,ContainerDetailsVO containerDetailsVo)
			throws SQLException {
		 dsnVO.setCompanyCode(rs.getString("CMPCOD"));
		 dsnVO.setDsn(rs.getString("DSN"));
		 dsnVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
		 dsnVO.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
		 dsnVO.setMailClass(rs.getString("MALCLS"));
		 //Added to include the DSN PK 
		 dsnVO.setMailSubclass(rs.getString("MALSUBCLS"));
		 dsnVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		 dsnVO.setYear(rs.getInt("YER"));
		 dsnVO.setBags(rs.getInt("DSNACPBAG"));
		 dsnVO.setPrevBagCount(rs.getInt("DSNACPBAG"));
		 //dsnVO.setWeight(rs.getDouble("DSNACPWGT"));
		 dsnVO.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNACPWGT")));//added by A-7371
		 //dsnVO.setPrevBagWeight(rs.getDouble("DSNACPWGT"));
		 dsnVO.setPrevBagWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNACPWGT")));//added by A-7371

		 dsnVO.setStatedBags(rs.getInt("DSNSTDBAG"));
		 dsnVO.setPrevStatedBags(rs.getInt("DSNSTDBAG"));
		 //dsnVO.setStatedWeight(rs.getDouble("DSNSTDWGT"));
		 dsnVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNSTDWGT")));//added by A-7371
		// dsnVO.setPrevStatedWeight(rs.getDouble("DSNSTDWGT"));
		 dsnVO.setPrevStatedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNSTDWGT")));//added by A-7371
		 dsnVO.setPltEnableFlag(rs.getString("PLTENBFLG"));	 
		 //Added By Karthick To sum the Accepted Bags and Weights of the Container..
		 /*
		  * In case of the uld the Bag Count and the Bag Weight is fetched directly from the 
		  * MTKULDSEG ,In case of BULK say the 
		  * Containers Total Bag counts must be summation of all the DSNs acpBags and AcpWgt to the 
		  * Container ...Table (MTKDSNCONSEG)
		  */
		 if(MailConstantsVO.BULK_TYPE.equals(rs.getString("CONTYP"))){
		    containerDetailsVo.setTotalBags(containerDetailsVo.getTotalBags()+dsnVO.getBags());
		    //containerDetailsVo.setTotalWeight(containerDetailsVo.getTotalWeight()+dsnVO.getWeight());
		    try {
				containerDetailsVo.setTotalWeight(Measure.addMeasureValues(containerDetailsVo.getTotalWeight(), dsnVO.getWeight()));
			} catch (UnitException e) {
				// TODO Auto-generated catch block
				log.log(Log.SEVERE, "UnitException",e.getMessage());
			}//added by A-7371
		 }
		 /*
		  * Added By RENO K ABRAHAM for Mail Allocation
		  * UBR number,Currency Code,Mail Rate is taken from MTKDSNULDSEG & MTKDSNCONSEG 
		  * CAPBKGMST is joined with these tables for lastUpdateTime of booking.
		  * CAPBKGFLTDTL is joined with CAPBKGMST for lastUpdateTime of Flight booking.
		  */
		 dsnVO.setUbrNumber(rs.getString("UBRNUM"));
		 dsnVO.setCurrencyCode(rs.getString("CURCOD"));
		 dsnVO.setMailrate(rs.getDouble("MALRAT"));
		/* Timestamp bookingUpdateTime = rs.getTimestamp("BKGLSTUPDTIM");
		 Timestamp bookingFlightDetailUpdateTime = rs.getTimestamp("BKGFLTUPDTIM");
		 LocalDate bookingLastUpdateTime=new LocalDate(LocalDate.NO_STATION, Location.NONE,true); 
		 
		 if(bookingUpdateTime != null && 
				 bookingFlightDetailUpdateTime != null) {
			 if(bookingUpdateTime.after(bookingFlightDetailUpdateTime)) {
				 bookingLastUpdateTime = new LocalDate(LocalDate.NO_STATION, Location.NONE,
						 bookingUpdateTime);
			 }else {
				 bookingLastUpdateTime = new LocalDate(LocalDate.NO_STATION, Location.NONE,
						 bookingFlightDetailUpdateTime);
			 }
			 if(bookingLastUpdateTime!=null) {
				 dsnVO.setBookingLastUpdateTime(bookingLastUpdateTime);
				 dsnVO.setBookingFlightDetailLastUpdTime(bookingLastUpdateTime);
			 }
		 }*/
		 //END AirNZ CR : Mail Allocation
	}

	/**
	 * A-1936
	 * 
	 * @param containerDetailsVO
	 * @param rs
	 * @throws SQLException
	 */
	private void populateContainerDetails(
			ContainerDetailsVO containerDetailsVO,ResultSet rs)
			throws SQLException {
		 containerDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		 containerDetailsVO.setContainerNumber(rs.getString("CONNUM"));
		 containerDetailsVO.setPou(rs.getString("POU"));
		 containerDetailsVO.setPol(rs.getString("ASGPRT"));
		 containerDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		 containerDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		 containerDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		 containerDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		 containerDetailsVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		 containerDetailsVO.setLocation(rs.getString("LOCCOD"));
		 containerDetailsVO.setWareHouse(rs.getString("WHSCOD"));
		 containerDetailsVO.setRemarks(rs.getString("RMK"));
		 if(MailConstantsVO.ULD_TYPE.equals(rs.getString("CONTYP"))){
		  containerDetailsVO.setTotalBags(rs.getInt("ULDBAGCNT"));
		  //containerDetailsVO.setTotalWeight(rs.getDouble("ULDBAGWGT"));
		  containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ULDBAGWGT")));//added by A-7371
		}
		containerDetailsVO.setDestination(rs.getString("DSTCOD"));
		containerDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
		containerDetailsVO.setAcceptedFlag(rs.getString("ACPFLG"));
		containerDetailsVO.setOffloadFlag(rs.getString("OFLFLG"));
		containerDetailsVO.setContainerType(rs.getString("CONTYP"));
		containerDetailsVO.setArrivedStatus(rs.getString("ARRSTA"));
		containerDetailsVO.setTransferFromCarrier(rs.getString("ULDFRMCARCOD"));
		containerDetailsVO.setContainerJnyId(rs.getString("CONJRNIDR"));
		containerDetailsVO.setPaCode(rs.getString("POACOD"));
		containerDetailsVO.setTransitFlag(rs.getString("TRNFLG"));
		Timestamp lstUpdateTime = rs.getTimestamp("CONLSTUPDTIM");
		if(lstUpdateTime != null) {
			containerDetailsVO.setLastUpdateTime(
				new LocalDate(LocalDate.NO_STATION, Location.NONE,
					lstUpdateTime));
		}
		Timestamp uldLastUpdateTime = rs.getTimestamp("ULDLSTUPDTIM");
		if(uldLastUpdateTime != null) {
			containerDetailsVO.setUldLastUpdateTime(
					new LocalDate(LocalDate.NO_STATION, Location.NONE,
						uldLastUpdateTime));
		}
		containerDetailsVO.setContentId(rs.getString("CNTIDR"));//Added as part of ICRD-239331
	}
}

