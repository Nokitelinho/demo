/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.MailDespatchListMapper.java
 *
 *	Created by	:	A-10543
 *	Created on	:	01-03-2023
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.xaddons.lh.mail.operations;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Java file :
 * com.ibsplc.icargo.persistence.dao.mail.operations.MailDespatchListMapper.java
 * Version : Name : Date : Updation
 * --------------------------------------------------- 0.1 : A-10543 :
 * 01/03/2023
 */
public class MailDespatchListMapper implements MultiMapper<DespatchDetailsVO> {

	 private static final Log LOGGER = LogFactory.getLogger("MailDespatchListMapper");

	public List<DespatchDetailsVO> map(ResultSet rs) throws SQLException {

		Collection<DespatchDetailsVO> despatchDetailsVOsList = new ArrayList<>();
		while (rs.next()) {
			DespatchDetailsVO despatchDetailsVO = new DespatchDetailsVO();
			despatchDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
			despatchDetailsVO.setMailCategoryCode(rs.getString("MALCTG"));
			despatchDetailsVO.setMailSubclass(rs.getString("MALSUBCLS"));
			despatchDetailsVO.setDsn(rs.getString("DSN"));
			despatchDetailsVO.setOriginOfficeOfExchange(rs.getString("ORGEXGOFC"));
			despatchDetailsVO.setDestinationOfficeOfExchange(rs.getString("DSTEXGOFC"));
			despatchDetailsVO.setRemarks(rs.getString("RMK"));
			Timestamp scannedDate = rs.getTimestamp("SCNDAT");
			String scannedPort = rs.getString("SCNPRT");
			if(scannedDate!=null && scannedPort!=null) {
			 despatchDetailsVO.setBookingLastUpdateTime(new com.ibsplc.icargo.framework.util.time.LocalDate(
					scannedPort,Location.ARP,scannedDate));
			}
			Date date = rs.getDate("STD");
			if(date!=null) { 	
			despatchDetailsVO.setFlightDate(new com.ibsplc.icargo.framework.util.time.LocalDate(
				com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION,Location.NONE,date));
			 despatchDetailsVO.setFlightNumber(rs.getString("POL")+"-"+rs.getString("POU")+" "+rs.getString("TWOAPHCOD")+rs.getString("FLTNUM")+" "+
					despatchDetailsVO.getFlightDate().toDisplayDateOnlyFormat().substring(0,6).toUpperCase());	
			}
			despatchDetailsVO.setAcceptedBags(rs.getInt("RECPTACLE_COUNT"));
			despatchDetailsVO.setAcceptedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT")));
			despatchDetailsVO.setAcceptanceFlag(rs.getString("DSPDAT"));
			despatchDetailsVO.setRsn(rs.getString("RSN"));
			despatchDetailsVO.setLatestStatus(rs.getString("MALSTA"));//Last Event
			despatchDetailsVO.setUbrNumber(rs.getString("MALIDR"));//MailIDR
			despatchDetailsVOsList.add(despatchDetailsVO);
		}

		LOGGER.log(Log.FINE, "\n\n despatchDetailsVOs listed !! ----------> ");
		return ( List<DespatchDetailsVO>) despatchDetailsVOsList;

	}

}
