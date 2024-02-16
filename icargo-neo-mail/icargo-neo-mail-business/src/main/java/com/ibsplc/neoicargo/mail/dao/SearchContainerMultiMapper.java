/*
 * SearchContainerMultiMapper.java  Created on May 30, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.currency.Money;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.ContainerVO;
import com.ibsplc.neoicargo.mail.vo.OnwardRoutingVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


/**
 * 
 * @author a-1936
 * This class is used to map the ResultSet into the VOS
 */
public class SearchContainerMultiMapper implements MultiMapper<ContainerVO> { //Added by A-5201 as part from the ICRD-20507 


	private static final Log LOG = LogFactory.getLogger("Mail Operations");
	private static final String SCAN_PORT = "SCNPRT";

	/**
	 * This method is used to map the ResultSet into the Vos
	 * 
	 * @author a-1936
	 * @param rs
	 * @return List<ContainerVO>
	 * @throws SQLException
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
	 */
	public List<ContainerVO> map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		List<ContainerVO> containerVosList = null;
		ContainerVO containerVo = null;
		String parentId = "";
		String prevParentId = "";
		String childId = "";
		String prevChildId = "";
		StringBuilder onwardFlightBuilder = null;
		String pou = null;
		String flightNumber = null;
		String carrierCode = null;
		Collection<OnwardRoutingVO> onwardRoutingVos = null;

		while (rs.next()) {
//			log.log(Log.FINEST, "INSIDE THE WHILE LOOP");
			/*
			 * generate the ParentID corresponding to this resultset
			 */
			parentId = new StringBuilder(rs.getString("CMPCOD")).append(
					rs.getString("CONNUM")).append(rs.getString("FLTCARIDR"))
					.append(rs.getString("FLTNUM")).append(
							rs.getString("FLTSEQNUM")).append(
							rs.getString("LEGSERNUM")).append(
							rs.getString("ASGPRT")).toString();

			String airport = rs.getString("ASGPRT");
//			log.log(Log.FINEST, "The  NEW ParentID is Found to be " + parentId);

			/*
			 * 1.Whenever a new Parent comes add the OldParent to the List
			 * containing the ContainerVos 2.Set the generatedString for the
			 * OnwardFlights in to the containerVO
			 */

			if (!parentId.equals(prevParentId) && containerVo != null) {
				if (onwardFlightBuilder != null) {
					containerVo.setOnwardRoute(onwardFlightBuilder
							.deleteCharAt(onwardFlightBuilder.length() - 1)
							.toString());
//					log.log(Log.FINEST, "MORE THAN ONE PARENT EXISTS");
//					log.log(Log.FINEST, "THE ONWARDFLIGHTSTRING"
//							+ containerVo.getOnwardFlights());
				}

				if (onwardRoutingVos != null && onwardRoutingVos.size() > 0) {
					containerVo.setOnwardRoutings(onwardRoutingVos);
				}

				if (containerVosList == null) {
					containerVosList = new ArrayList<ContainerVO>();
				}
				containerVosList.add(containerVo);
				onwardFlightBuilder = null;
				onwardRoutingVos = null;
			}

			/*
			 * 
			 * Enters inside whenever a new Parent Comes Create a new
			 * ContainerVO
			 * 
			 */
			if (!parentId.equals(prevParentId)) {
//				log.log(Log.FINEST, "NEW PARENT COMES");
				containerVo = new ContainerVO();
				containerVo.setCompanyCode(rs.getString("CMPCOD"));
				containerVo.setContainerNumber(rs.getString("CONNUM"));
				containerVo.setCarrierId(rs.getInt("FLTCARIDR"));
				containerVo.setFlightNumber(rs.getString("FLTNUM"));
				containerVo.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
				containerVo.setLegSerialNumber(rs.getInt("LEGSERNUM"));
				containerVo.setAssignedPort(rs.getString("ASGPRT"));
				containerVo.setFinalDestination(rs.getString("DSTCOD"));
				if (rs.getTimestamp("ASGDAT") != null) {
					containerVo.setAssignedDate(localDateUtil.getLocalDate(airport, rs.getTimestamp("ASGDAT")));
				}
				containerVo.setCarrierId(rs.getInt("FLTCARIDR"));
				containerVo.setAssignedUser(rs.getString("USRCOD"));
				containerVo.setAcceptanceFlag(rs.getString("ACPFLG"));
				containerVo.setPaBuiltFlag(rs.getString("POAFLG"));
				containerVo.setRemarks(rs.getString("RMK"));
				containerVo.setPou(rs.getString("POU"));
				//Added for icrd-95515 to fetch pol required for fetching importmainfestedmaildetail
				containerVo.setPol(rs.getString("POL"));
				containerVo.setType(rs.getString("CONTYP"));
				containerVo.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
				containerVo.setCarrierCode(rs.getString("MSTFLTCARCOD"));
				containerVo.setOffloadFlag(rs.getString("OFLFLG"));
				containerVo.setArrivedStatus(rs.getString("ARRSTA"));
				containerVo.setTransferFlag(rs.getString("TRAFLG"));
				containerVo.setContainerJnyID(rs.getString("CONJRNIDR"));
				containerVo.setShipperBuiltCode(rs.getString("SBCODE"));
				containerVo.setTransitFlag(rs.getString("TRNFLG"));
				containerVo.setReleasedFlag(rs.getString("RELFLG"));
				containerVo.setUldFulIndFlag(rs.getString("ULDFULIND"));
				containerVo.setUldReferenceNo(rs.getLong("ULDREFNUM"));
				containerVo.setExpClsflg(rs.getString("CLSFLG"));
				containerVo.setHbaMarked(rs.getString("HBAMARKED"));
				//Modified by A-7540 for IASCB-25432
				//Measure actualWeight = new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ACTULDWGT"));
				Quantity actualWeight = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ACTULDWGT")));
				containerVo.setActualWeight(actualWeight);
				if (rs.getDate("FLTDAT") != null) {
					containerVo.setFlightDate(localDateUtil.getLocalDate(airport, rs.getTimestamp("FLTDAT")));
				}

//				log.log(Log.FINE, "THE FLIGHT SEQUENCE NUMBER IS "
//						+ rs.getLong("FLTSEQNUM"));
               
				containerVo.setBags(rs.getInt("BAGCNT"));
				if(Objects.nonNull(rs.getString("DSPWGTUNT"))){
				containerVo.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("BAGWGT")),
						BigDecimal.valueOf(0.0), rs.getString("DSPWGTUNT")));}
				else {
					containerVo.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("BAGWGT"))));
				}
				//containerVo.setWeight(rs.getDouble("BAGWGT"));
				if(rs.getTimestamp("LSTUPDTIM") != null) {
					containerVo.setLastUpdateTime(localDateUtil.getLocalDate(airport, rs.getTimestamp("LSTUPDTIM")));
				}
				containerVo.setLastUpdateUser(rs.getString("LSTUPDUSR"));
				Timestamp uldLastUpdateTime = rs.getTimestamp("ULDLSTUPDTIM");
				if(uldLastUpdateTime != null) {
					containerVo.setULDLastUpdateTime(localDateUtil.getLocalDate(airport, rs.getTimestamp("ULDLSTUPDTIM")));
				}
				containerVo.setULDLastUpdateUser(rs.getString("ULDLSTUPDUSR"));
				//Added by A-3429 for ICRD-83340
				containerVo.setSubclassGroup(rs.getString("SUBCLSGRP"));
				containerVo.setAcceptedPort(rs.getString(SCAN_PORT)); 
				if(rs.getString(SCAN_PORT) != null && rs.getTimestamp("SCNDAT") != null 
						&& "O".equalsIgnoreCase(rs.getString("CLSFLG"))){
					// TO DO Sruthi

					//LocalDate scnDate = new LocalDate(rs.getString(SCAN_PORT) ,Location.ARP, rs.getTimestamp("SCNDAT"));
					ZonedDateTime scnDate = localDateUtil.getLocalDate(rs.getString(SCAN_PORT), rs.getTimestamp("SCNDAT"));
				//scnDate.setDateAndTime(rs.getString("SCNDAT"));
					//LocalDate currentDate = new LocalDate(rs.getString(SCAN_PORT) ,Location.ARP, true);
/*					ZonedDateTime currentDate = localDateUtil.getLocalDate(rs.getString(SCAN_PORT), rs.getTimestamp("SCNDAT"));
					long timeDiff = currentDate.findDifference(scnDate);
					int timediffIndays = (int)(timeDiff/(1000*60*60*24));
					if(timeDiff % (1000*60*60*24) > 0 ){
						timediffIndays = timediffIndays + 1;
					}*/
					containerVo.setNoOfDaysInCurrentLoc(0);
				}else{
					containerVo.setNoOfDaysInCurrentLoc(0);
				}
				 if(rs.getString("CNTIDR") != null && rs.getString("CNTIDR").trim().length()>0){
					 containerVo.setContentId(rs.getString("CNTIDR"));  //Added by A-7929 as part of ICRD-219699
					 }
					containerVo.setPlannedFlightNum(rs.getInt("PLNFLTNUM"));
					containerVo.setPlannedFlightCarrierCode(rs.getString("PLNFLTCARCOD"));
					if (rs.getTimestamp("PLNFLTDAT") != null) {
						containerVo.setPlannedFlightDate(localDateUtil.getLocalDate(airport, rs.getTimestamp("PLNFLTDAT")));
					}
				prevParentId = parentId;
				//increment();
//				log.log(Log.FINE, "THE OLD PARENT ID IS " + prevParentId);
			}

			/**
			 * 
			 * For each RoutingSerialNum create a String like EK
			 * 1303-30/6/2006-DXB,EK 1305-30/6/2006-TRV (FLTNUM-FLTDAT-POU)
			 * 
			 */

			if (rs.getInt("RTGSERNUM") > 0) {
				childId = new StringBuffer(parentId).append(
						rs.getString("RTGSERNUM")).toString();
//				log.log(Log.FINE, "THE CHILD ID IS FOUND TO BE " + childId);

				if (!childId.equals(prevChildId)) {
					flightNumber = rs.getString("ONWFLTNUM");
					pou = rs.getString("RTGPOU");
					carrierCode = rs.getString("ONWFLTCARCOD");

					if (onwardRoutingVos == null) {
						onwardRoutingVos = new ArrayList<OnwardRoutingVO>();
					}

					OnwardRoutingVO onwardRoutingVo = new OnwardRoutingVO();
					onwardRoutingVo.setCompanyCode(rs.getString("CMPCOD"));
					onwardRoutingVo.setOnwardCarrierId(rs
							.getInt("ONWFLTCARIDR"));
					onwardRoutingVo.setContainerNumber(rs.getString("CONNUM"));

					onwardRoutingVo.setCarrierId(rs.getInt("RTGFLTCARIDR"));
					onwardRoutingVo.setFlightNumber(rs.getString("FLTNUM"));
					onwardRoutingVo.setFlightSequenceNumber(rs
							.getLong("FLTSEQNUM"));
					onwardRoutingVo.setLegSerialNumber(rs.getInt("LEGSERNUM"));

					onwardRoutingVo.setRoutingSerialNumber(rs
							.getInt("RTGSERNUM"));

					if (rs.getString("ONWFLTDAT") != null) {
			/*			LocalDate localdate = new LocalDate(airport,
								Location.ARP, true);
						localdate.setDate(rs.getString("ONWFLTDAT"));*/
						onwardRoutingVo.setOnwardFlightDate(localDateUtil.getLocalDate(airport, rs.getTimestamp("ONWFLTDAT")));
					}

					onwardRoutingVo.setOnwardFlightNumber(flightNumber);
					onwardRoutingVo.setPou(pou);
					onwardRoutingVo.setOnwardCarrierCode(carrierCode);
					onwardRoutingVo.setAssignmenrPort(rs.getString("ASGPRT"));

					onwardRoutingVos.add(onwardRoutingVo);

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

						if (onwardFlightBuilder == null) {
							onwardFlightBuilder = new StringBuilder();
						}

						onwardFlightBuilder.append(carrierCode).append("-")
								.append(flightNumber).append("-").append(
										rs.getString("ONWFLTDAT")).append("-")
								.append(pou).append(",");
					}
					childId = prevChildId;

				}
			}
			//Money amount;
			if(Objects.nonNull(rs.getString("PROCHG"))){
			Money amount = Money.of(rs.getString("PROCHG"));
			containerVo.setProvosionalCharge(amount);}
			 /*try {
					amount = CurrencyHelper.getMoney(rs.getString("BASCURCOD"));
					amount.setAmount(rs.getDouble("PROCHG"));   
					containerVo.setProvosionalCharge(amount);

				} catch (CurrencyException e) {
					LOG.log(Log.INFO,e);
				}*/
			 if(containerVo.getBags()!=rs.getInt("RATEDCOUNT")) {
				 containerVo.setRateAvailforallMailbags("N"); 
			 }
			 else {
				 containerVo.setRateAvailforallMailbags("Y");  
			 }
			 containerVo.setBaseCurrency(rs.getString("BASCURCOD"));
		}

		/*
		 * 
		 * This block is used to insert the Last Record in the ResultSet say the
		 * Last ContainerVO in to the List If there is Only one parent it will
		 * always enter this block
		 * 
		 */
		if (containerVo != null) {
			if (onwardFlightBuilder != null) {
				containerVo.setOnwardRoute(onwardFlightBuilder.deleteCharAt(
						onwardFlightBuilder.length() - 1).toString());
//				log.log(Log.INFO, "ONLY ONE PARENT EXISTS");
//				log.log(Log.FINE, "THE ONWARDFLIGHTSTRING"
//						+ containerVo.getOnwardFlights());
			}
			if (onwardRoutingVos != null && onwardRoutingVos.size() > 0) {
				containerVo.setOnwardRoutings(onwardRoutingVos);
			}

			if (containerVosList == null) {
				containerVosList = new ArrayList<ContainerVO>();
			}
			
			containerVosList.add(containerVo);

		}

		return containerVosList;
	}

}