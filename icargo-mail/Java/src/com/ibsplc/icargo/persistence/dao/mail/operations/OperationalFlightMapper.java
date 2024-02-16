/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.OperationalFlightMapper.java
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
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.OperationalFlightMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Sep 3, 2016	:	Draft
 */
public class OperationalFlightMapper implements Mapper<OperationalFlightVO>{

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
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
		/**
		 * Mapper modified by A-4809 as query changed
		 */
		log.log(log.FINE, "OperationalFlightMapper ---->Entering");
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode(rs.getString("CMPCOD"));
		operationalFlightVO.setCarrierCode(rs.getString("FLTCARCOD"));
		operationalFlightVO.setCarrierId(rs.getInt("FLTCARIDR"));
		operationalFlightVO.setFlightNumber(rs.getString("FLTNUM"));
		operationalFlightVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
		operationalFlightVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		operationalFlightVO.setPol(rs.getString("POL"));
		operationalFlightVO.setPou(rs.getString("POU"));  
		operationalFlightVO.setAirportCode(rs.getString("POU"));
			if (rs.getDate("FLTDAT") != null) {
				operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs
						.getDate("FLTDAT")));
		}
		if (rs.getDate("ARRIVALTIME") != null) {
			operationalFlightVO.setActualArrivalTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs
							.getTimestamp("ARRIVALTIME")));
		}
		operationalFlightVO.setLegDestination(rs.getString("LEGDST"));
		log.log(Log.INFO, "OperationalFlightVO -->", operationalFlightVO);
		return operationalFlightVO;
	}

}
