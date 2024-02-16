/*
 * MailResditMapper.java Created on May 22, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailResditVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;


/**
 * @author A-1739
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * -------------------------------------------------------------------------
 * 0.1     		   May 22, 2008			  	 A-1739		Created
 */
public class MailResditMapper implements Mapper<MailResditVO> {

	/* (non-Javadoc)
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 */
	public MailResditVO map(ResultSet rs) throws SQLException {
		MailResditVO mailResditVO = new MailResditVO();
		mailResditVO.setCarrierId(rs.getInt("FLTCARIDR"));
		mailResditVO.setFlightNumber(rs.getString("FLTNUM"));
		mailResditVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		mailResditVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		mailResditVO.setEventAirport(rs.getString("EVTPRT"));
		mailResditVO.setResditSentFlag(rs.getString("RDTSND"));
		mailResditVO.setProcessedStatus(rs.getString("PROSTA"));
		return mailResditVO;
	}
}
