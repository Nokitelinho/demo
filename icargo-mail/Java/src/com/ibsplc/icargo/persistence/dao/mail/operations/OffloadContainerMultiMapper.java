/*
 * OffloadContainerMultiMapper.java Created on Jun 15, 2006
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
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1936
 * 
 */
public class OffloadContainerMultiMapper implements MultiMapper<ContainerVO> {

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

	/**
	 * This method is used to map the result set in to the Vo
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */

	public List<ContainerVO> map(ResultSet rs) throws SQLException {
		log.entering("inside the mapper", "map(ResultSet rs )");
		List<ContainerVO> containersVos = null;

		ContainerVO containerVo = null;

		while (rs.next()) {
			if (containersVos == null) {
				containersVos = new ArrayList<ContainerVO>();
			}
			/*
			 * Enters this block when the offloadType enterd by the User is Uld
			 * This block acts as a simple mapper in case of CONNUM and ULDNUM
			 * are same in the resultset else for each resultset get the no of
			 * bags and collect the total bags in case of Barrows .
			 */
			if (rs.getString("CONNUM").equals(rs.getString("ULDNUM"))) {
				containerVo = new ContainerVO();
				containerVo.setCompanyCode(rs.getString("CMPCOD"));
				containerVo.setContainerNumber(rs.getString("CONNUM"));
				containerVo.setFlightNumber(rs.getString("FLTNUM"));
				containerVo.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
				containerVo.setAssignedPort(rs.getString("ASGPRT"));
				containerVo.setCarrierId(rs.getInt("FLTCARIDR"));
				containerVo.setLegSerialNumber(rs.getInt("LEGSERNUM"));
				containerVo.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
				containerVo.setPou(rs.getString("POU"));
				containerVo.setFinalDestination(rs.getString("DSTCOD"));
				containerVo.setType(rs.getString("CONTYP"));
				containerVo.setAcceptanceFlag(rs.getString("ACPFLG"));
				containerVo.setPaBuiltFlag(rs.getString("POAFLG"));
				containerVo.setBags(rs.getInt("BAGS"));
				containerVo.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT")));//added by A-7371
				containerVo.setCarrierCode(rs.getString("FLTCARCOD"));
				containerVo.setPaBuiltFlag(rs.getString("POAFLG"));
				containerVo.setContainerJnyID(rs.getString("CONJRNIDR"));
				containerVo.setShipperBuiltCode(rs.getString("SBCODE"));
				containerVo.setUldFulIndFlag(rs.getString("ULDFULIND"));
				containerVo.setUldReferenceNo(rs.getLong("ULDREFNUM"));
				containerVo.setTransactionCode(rs.getString("TXNCOD"));
				containerVo.setActWgtSta(rs.getString("ACTWGTSTA"));
				String airport = rs.getString("ASGPRT");
			   if (rs.getDate("FLTDAT") != null) {
				   containerVo.setFlightDate(new LocalDate(airport,
							Location.ARP, rs.getDate("FLTDAT")));
				}
				containersVos.add(containerVo);

			} else {

					log.log(Log.INFO, "WILL BE ENTERED ONLY ONCE");
					containerVo = new ContainerVO();
					containerVo.setCompanyCode(rs.getString("CMPCOD"));
					containerVo.setContainerNumber(rs.getString("CONNUM"));
					containerVo.setFlightNumber(rs.getString("FLTNUM"));
					containerVo
							.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
					containerVo.setAssignedPort(rs.getString("ASGPRT"));
					containerVo.setCarrierId(rs.getInt("FLTCARIDR"));
					containerVo.setLegSerialNumber(rs.getInt("LEGSERNUM"));
					containerVo.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
					containerVo.setPou(rs.getString("POU"));
					containerVo.setFinalDestination(rs.getString("DSTCOD"));
					containerVo.setType(rs.getString("CONTYP"));
					containerVo.setAcceptanceFlag(rs.getString("ACPFLG"));
					containerVo.setCarrierCode(rs.getString("FLTCARCOD"));
					containerVo.setContainerJnyID(rs.getString("CONJRNIDR"));
					containerVo.setActWgtSta(rs.getString("ACTWGTSTA"));
					String airport = rs.getString("ASGPRT");
				   if (rs.getDate("FLTDAT") != null) {
					   containerVo.setFlightDate(new LocalDate(airport,
								Location.ARP, rs.getDate("FLTDAT")));
					}

				   containerVo.setBags(rs.getInt("BAGS"));
				   containerVo.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT")));//added by A-7371
			       containersVos.add(containerVo);
				}

		}
		return containersVos;
	}
}
