/*
 * DailyMailStationMapper.java Created on Feb 28, 2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * A-3251 SREEJITH P.C.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.DailyMailStationReportVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-3251
 *
 */
public class DailyMailStationMapper implements Mapper<DailyMailStationReportVO>{
	
	private static final String MODULE = "mailtracking.defaults";
	private Log log = LogFactory.getLogger(MODULE);
	
	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public DailyMailStationReportVO map(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		
		DailyMailStationReportVO reportVO = new DailyMailStationReportVO();
		log.entering("DailyMailStationMapper","map");
		//mapping from DB to Details
		reportVO.setFlightNumber(rs.getString("FLTNUM"));
		reportVO.setCarrierCode(rs.getString("CARCOD"));
		reportVO.setUldnum(rs.getString("ULDNUM"));
		reportVO.setDestination(rs.getString("DEST"));
		//reportVO.setNetweight(Float.parseFloat(rs.getString("BAGWGT")));
		reportVO.setNetweight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(rs.getString("BAGWGT"))));//added by A-7371
		//reportVO.setGrossweight(Float.parseFloat(rs.getString("TOTWGT")));
		reportVO.setGrossweight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(rs.getString("TOTWGT"))));//added by A-7371
		reportVO.setRemark(rs.getString("RMK"));	
		reportVO.setBagCount(rs.getString("BAGCNT"));
		return reportVO;
		}
}
