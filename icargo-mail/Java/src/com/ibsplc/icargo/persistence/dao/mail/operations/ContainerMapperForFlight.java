/*
 * ContainerMapperForFlight.java  Created on May 30, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-1936
 * This class is used to map the ResultSet into the VOS
 */
public class ContainerMapperForFlight implements MultiMapper<ContainerVO> {

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

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
			log.log(Log.INFO, "INSIDE THE WHILE LOOP");
			/*
			 * generate the ParentID corresponding to this resultset
			 */
			parentId = new StringBuilder(rs.getString("CMPCOD")).append(
					rs.getString("CONNUM")).append(rs.getString("FLTCARIDR"))
					.append(rs.getString("FLTNUM")).append(
							rs.getString("FLTSEQNUM")).append(
							rs.getString("LEGSERNUM")).append(
							rs.getString("ASGPRT")).toString();
			String assignedPort = rs.getString("ASGPRT");
			log.log(Log.FINE, "The  NEW ParentID is Found to be ", parentId);
			log.log(Log.FINE, "The AssignedPort  is Found to be ", parentId);
			if (!parentId.equals(prevParentId) && containerVo != null) {
				if (onwardFlightBuilder != null) {
					containerVo.setOnwardFlights(onwardFlightBuilder
							.deleteCharAt(onwardFlightBuilder.length() - 1)
							.toString());
					log.log(Log.INFO, "MORE THAN ONE PARENT EXISTS");
					log.log(Log.FINE, "THE ONWARDFLIGHTSTRING", containerVo.getOnwardFlights());
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
				log.log(Log.INFO, "NEW PARENT COMES");
				containerVo = new ContainerVO();
				containerVo.setCompanyCode(rs.getString("CMPCOD"));
				containerVo.setContainerNumber(rs.getString("CONNUM"));
				containerVo.setCarrierCode(rs.getString("FLTCARCOD"));
				containerVo.setCarrierId(rs.getInt("FLTCARIDR"));
				containerVo.setFlightNumber(rs.getString("FLTNUM"));
				containerVo.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
				containerVo.setLegSerialNumber(rs.getInt("LEGSERNUM"));
				containerVo.setAssignedPort(rs.getString("ASGPRT"));
				containerVo.setFinalDestination(rs.getString("DSTCOD"));
				if (rs.getTimestamp("ASGDAT") != null) {
					containerVo.setAssignedDate(new LocalDate(assignedPort,
							Location.ARP, rs.getTimestamp("ASGDAT")));
				}
				containerVo.setCarrierId(rs.getInt("FLTCARIDR"));
				containerVo.setAssignedUser(rs.getString("USRCOD"));
				containerVo.setRemarks(rs.getString("RMK"));
				containerVo.setPaBuiltFlag(rs.getString("POAFLG"));
				containerVo.setPou(rs.getString("POU"));
				containerVo.setType(rs.getString("CONTYP"));
				containerVo.setAcceptanceFlag(rs.getString("ACPFLG"));
				containerVo.setBags(rs.getInt("BAGCNT"));
				containerVo.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
				containerVo.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("BAGWGT")));//added by A-7371
				//containerVo.setWeight(rs.getDouble("BAGWGT"));
				containerVo.setContainerJnyID(rs.getString("CONJRNIDR"));
				containerVo.setShipperBuiltCode(rs.getString("SBCODE"));	
				containerVo.setWarehouseCode(rs.getString("WHSCOD"));
				containerVo.setTransitFlag(rs.getString("TRNFLG"));
		
				if (rs.getDate("FLTDAT") != null) {
					containerVo.setFlightDate(new LocalDate(assignedPort,
							Location.ARP, rs.getDate("FLTDAT")));
				}
				containerVo.setOffloadFlag(rs.getString("OFLFLG"));
				containerVo.setArrivedStatus(rs.getString("ARRSTA"));
				java.sql.Timestamp uldlastUpdateTime = rs.getTimestamp("ULDLSTUPDTIM");
				if(uldlastUpdateTime != null) {
					containerVo.setULDLastUpdateTime(
						new LocalDate(LocalDate.NO_STATION, Location.NONE, 
								uldlastUpdateTime)); 
				}
				java.sql.Timestamp  lastUpdateTime = rs.getTimestamp("LSTUPDTIM");
				if(lastUpdateTime != null) {
					containerVo.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE,lastUpdateTime)); 
				}
				
				containerVo.setLastUpdateUser(rs.getString("LSTUPDUSR"));
				containerVo.setULDLastUpdateUser(rs.getString("ULDLSTUPDUSR"));
				prevParentId = parentId;
				log.log(Log.FINE, "THE OLD PARENT ID IS ", prevParentId);
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
				log.log(Log.FINE, "THE CHILD ID IS FOUND TO BE ", childId);
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
						LocalDate localdate = new LocalDate(assignedPort,
								Location.ARP, true);
						localdate.setDate(rs.getString("ONWFLTDAT"));
						onwardRoutingVo.setOnwardFlightDate(localdate);
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
				containerVo.setOnwardFlights(onwardFlightBuilder.deleteCharAt(
						onwardFlightBuilder.length() - 1).toString());
				log.log(Log.INFO, "ONLY ONE PARENT EXISTS");
				log.log(Log.FINE, "THE ONWARDFLIGHTSTRING", containerVo.getOnwardFlights());
			}
			if (onwardRoutingVos != null && onwardRoutingVos.size() > 0) {
				containerVo.setOnwardRoutings(onwardRoutingVos);
			}

			if (containerVosList == null) {
				containerVosList = new ArrayList<ContainerVO>();
			}

			containerVosList.add(containerVo);

		}
		log.log(Log.FINE, "THE List of the ContainerVos are  ",
				containerVosList);
		return containerVosList;
	}

}
