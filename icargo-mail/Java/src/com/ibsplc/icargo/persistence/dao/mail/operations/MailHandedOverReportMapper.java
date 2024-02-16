/*
 * MailHandedOverReportMapper.java Created on MAR 03,2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailHandedOverVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-3227 RENO K ABRAHAM
 *
 */
public class MailHandedOverReportMapper implements Mapper<MailHandedOverVO> {

	//private Log log= LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public MailHandedOverVO map(ResultSet rs) throws SQLException {

		//String airport=rs.getString("SCNPRT");
		MailHandedOverVO mailHandedOverVO = new MailHandedOverVO();
		mailHandedOverVO.setDoe(rs.getString("DESARPCOD"));		
		mailHandedOverVO.setOoe(rs.getString("ORGARPCOD"));	
		//mailHandedOverVO.setWeight(rs.getDouble("WGT"));
		mailHandedOverVO.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT")));//added by A-7371
		mailHandedOverVO.setDsn(rs.getString("DSN"));	
		mailHandedOverVO.setMailbagId(rs.getString("MALIDR"));	
		mailHandedOverVO.setInwardFlightCarrierCode(rs.getString("INBFLTCARCOD"));	
		mailHandedOverVO.setInwardFlightNum(rs.getString("INBFLTNUM"));	
		mailHandedOverVO.setOnwardCarrier(rs.getString("ONWFLTCARCOD"));
		int onwardFlightnumber=0;
		if(rs.getString("ONWFLTNUM")!=null){
		onwardFlightnumber=Integer.parseInt(rs.getString("ONWFLTNUM"));
		}
		if(onwardFlightnumber>0)      
		{
		mailHandedOverVO.setOnwardFlightNum(rs.getString("ONWFLTNUM"));	
		}	
		if (rs.getString("INBFLTDAT") != null) {
			mailHandedOverVO.setInwardFlightDate(new LocalDate(LocalDate.NO_STATION,
			Location.NONE, rs.getTimestamp("INBFLTDAT")));
		}		
		if (rs.getString("ONWFLTDAT") != null) {
			mailHandedOverVO.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION,
			Location.NONE, rs.getTimestamp("ONWFLTDAT")));			
		}		
		
		return mailHandedOverVO;
	}
	

}
