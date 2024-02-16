package com.ibsplc.icargo.persistence.dao.addons.mail.operations;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.xaddons.oz.mail.operations.BookingDetailsMapper.java
 *
 *	Created by	:	A-7531
 *	Created on	:	25-Sep-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */


import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.xaddons.oz.mail.operations.BookingDetailsMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	25-Sep-2017	:	Draft
 */
public class BookingDetailsMapper implements Mapper<MailBookingDetailVO>{

	
	public MailBookingDetailVO map(ResultSet rs) throws SQLException {
		
		MailBookingDetailVO mailBookingDetailVO = new MailBookingDetailVO();
		mailBookingDetailVO.setCompanyCode(rs.getString("CMPCOD"));
		mailBookingDetailVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		mailBookingDetailVO.setSerialNumber(rs.getInt("SERNUM"));
		mailBookingDetailVO.setBookingFlightCarrierid(rs.getInt("FLTCARIDR"));
		mailBookingDetailVO.setBookingFlightNumber(rs.getString("FLTNUM"));
		mailBookingDetailVO.setBookingFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
		mailBookingDetailVO.setSegementserialNumber(rs.getInt("SEGSERNUM"));
		mailBookingDetailVO.setBookingStatus(rs.getString("BKGSTA"));
		return mailBookingDetailVO;
	}

}

