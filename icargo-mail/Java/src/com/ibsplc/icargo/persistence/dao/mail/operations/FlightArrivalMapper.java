/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.FlightArrivalMapper.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Sep 3, 2016
 *
 *  Copyright 2016 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.FlightArrivalMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Sep 3, 2016	:	Draft
 */
public class FlightArrivalMapper implements Mapper<OperationalFlightVO>{

	/**
	 *	Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 *	Added by 			: A-4809 on Sep 3, 2016
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SQLException 
	 */
	@Override
	public OperationalFlightVO map(ResultSet rs) throws SQLException {
		
		OperationalFlightVO flightVO = new OperationalFlightVO();
		flightVO.setCompanyCode(rs.getString("CMPCOD"));
		flightVO.setFlightNumber(rs.getString("FLTNUM"));
		flightVO.setCarrierId(rs.getInt("FLTCARIDR"));
		flightVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		flightVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		flightVO.setPol(rs.getString("LEGORG"));
		flightVO.setPou(rs.getString("POU"));
		flightVO.setAirportCode(rs.getString("POU"));
		flightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getDate("FLTDAT")));
		flightVO.setSegSerNum(rs.getInt("SEGSERNUM"));
		return flightVO;
	}

}
