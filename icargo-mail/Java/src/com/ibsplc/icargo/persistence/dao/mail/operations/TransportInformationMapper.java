/*
 * TransportInformationMapper.java Created on Sep 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ResditMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.TransportInformationVO;
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
 * 0.1     		  Sep 12, 2006			a-1739		Created
 */
public class TransportInformationMapper implements
		Mapper<TransportInformationVO> {

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	/**
	 * TODO Purpose
	 * Sep 12, 2006, a-1739
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 */
	public TransportInformationVO map(ResultSet rs) throws SQLException {
		log.entering("TransportInformationMapper", "map");
		TransportInformationVO transportInformationVO = 
			new TransportInformationVO();
		transportInformationVO.setDeparturePlace(rs.getString("EVTPRT"));
		transportInformationVO.setDepartureLocationQualifier(
				ResditMessageVO.PLACE_OF_DEPARTURE);
		transportInformationVO.setConveyanceReference(rs.getString("FLIGHTID"));
		transportInformationVO.setCarrierName(rs.getString("ARLNAM"));		
		log.exiting("TransportInformationMapper", "map");
		return transportInformationVO;
	}

}
