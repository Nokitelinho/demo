/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.defaults.USPSIncentiveJobDetailsMapper.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Nov 28, 2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.defaults.USPSIncentiveJobDetailsMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Nov 28, 2018	:	Draft
 */
public class USPSIncentiveJobDetailsMapper implements Mapper<USPSPostalCalendarVO>{
	private static final String CLASS_NAME = "USPSIncentiveJobDetailsMapper";
	private Log log = LogFactory.getLogger("MRA:DEFAULTS");
	
	@Override
	public USPSPostalCalendarVO map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME,"map");
		USPSPostalCalendarVO uspsPostalCalendarVO =new USPSPostalCalendarVO();
		uspsPostalCalendarVO.setCompanyCode(rs.getString("CMPCOD"));
		uspsPostalCalendarVO.setGpacod(rs.getString("GPACOD"));
		if(rs.getString("CLMGENDAT")!=null){
		uspsPostalCalendarVO.setClmGenarationDate(new LocalDate(LocalDate.NO_STATION, Location.NONE,rs.getDate("CLMGENDAT")));
		}
		if(rs.getString("INCCALDAT")!=null){
			uspsPostalCalendarVO.setIncCalcDate(new LocalDate(LocalDate.NO_STATION, Location.NONE,rs.getDate("INCCALDAT")));	
		}
		if(rs.getString("PRDFRM")!=null){
		uspsPostalCalendarVO.setPeriodFrom(new LocalDate(LocalDate.NO_STATION, Location.NONE,rs.getDate("PRDFRM")));
		}
		if(rs.getString("PRDTOO")!=null){
		uspsPostalCalendarVO.setPeriodTo(new LocalDate(LocalDate.NO_STATION, Location.NONE,rs.getDate("PRDTOO")));	
		}
		log.exiting(CLASS_NAME,"map");
		return uspsPostalCalendarVO;
	}

}
