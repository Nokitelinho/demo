/*
 * CarditTransportationMapper.java Created on Feb 6, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.CarditTransportationVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  
 * @author A-1739
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  Feb 6, 2007			A-1739		Created
 */
public class CarditTransportationMapper implements
		Mapper<CarditTransportationVO> {

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	/**
	 * TODO Purpose
	 * Feb 6, 2007, A-1739
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 */
	public CarditTransportationVO map(ResultSet rs) throws SQLException {
		log.entering("CarditTransportationMapper", "map");
		CarditTransportationVO transportationVO = new CarditTransportationVO();
		transportationVO.setDeparturePort(rs.getString("ORGCOD"));
		transportationVO.setArrivalPort(rs.getString("DSTCOD"));
		if(rs.getTimestamp("DEPTIM")!=null) {
		transportationVO.setDepartureTime(
				new LocalDate(transportationVO.getDeparturePort(),
						Location.ARP, rs.getTimestamp("DEPTIM")));
		}
		transportationVO.setCarrierID(rs.getInt("CARIDR"));
		transportationVO.setFlightNumber(rs.getString("FLTNUM"));
		transportationVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		transportationVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		transportationVO.setSegmentSerialNum(rs.getInt("SEGSERNUM"));
		transportationVO.setModeOfTransport(rs.getString("TRTMOD"));
		transportationVO.setTransportStageQualifier(rs.getString("TRTSTGQLF"));
		transportationVO.setTransportSerialNum(rs.getInt("TRTSERNUM"));
		log.exiting("CarditTransportationMapper", "map");
		return transportationVO;
	}

}
