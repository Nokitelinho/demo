/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.FlightsForClosureMapper.java
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
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.FlightsForClosureMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Sep 3, 2016	:	Draft
 */
public class FlightsForClosureMapper implements Mapper<OperationalFlightVO>{

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
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		
		operationalFlightVO.setCompanyCode(rs.getString("CMPCOD"));
		if (rs.getString("FLTCARCOD") != null)
			{
			operationalFlightVO.setCarrierCode(rs.getString("FLTCARCOD"));
			}
		operationalFlightVO.setCarrierId(rs.getInt("FLTCARIDR"));
		operationalFlightVO.setFlightNumber(rs.getString("FLTNUM"));
		operationalFlightVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		operationalFlightVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		operationalFlightVO.setPol(rs.getString("LEGORG"));
		operationalFlightVO.setPou(rs.getString("LEGDST"));
		operationalFlightVO.setAirportCode(rs.getString("ARPCOD"));
		operationalFlightVO.setScanned(true);
		if (operationalFlightVO.getPol() != null && operationalFlightVO.getPou() != null) {
			operationalFlightVO.setFlightDate(
					new LocalDate(operationalFlightVO.getPol(), Location.ARP, rs.getDate("FLTDAT")));
		operationalFlightVO.setFlightRoute(rs.getString("FLTROU"));
		operationalFlightVO.setArrToDate(
				new LocalDate(operationalFlightVO.getPou(), Location.ARP, rs.getDate("STA")));
		
		} else {
			operationalFlightVO.setFlightDate(
					new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getDate("FLTDAT")));
			operationalFlightVO.setArrToDate(
					new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getDate("STA")));
		}
		operationalFlightVO.setFlightType(rs.getString("FLTTYP"));
		operationalFlightVO.setFltOwner(rs.getString("FLTOWN"));
		return operationalFlightVO;
	}

}
