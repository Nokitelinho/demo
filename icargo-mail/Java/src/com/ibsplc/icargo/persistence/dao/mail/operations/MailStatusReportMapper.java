/*
 * MailStatusReportMapper.java Created on March 11, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailStatusVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * 
 * @author A-2553 This class is used to map the ResultSet into MailStatusVO
 * 
 */
public class MailStatusReportMapper implements Mapper<MailStatusVO> {
	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public MailStatusVO map(ResultSet rs) throws SQLException {

		MailStatusVO mailStatusVO = new MailStatusVO();
		mailStatusVO.setCarditAvailable(rs.getString("CDTAVL"));
		mailStatusVO.setDsn(rs.getString("DSN"));
		mailStatusVO.setFlightCarrierCode(rs.getString("FLTCARCOD"));
		mailStatusVO.setFlightNumber(rs.getString("FLTNUM"));
		mailStatusVO.setIncommingFlightCarrierCode(rs.getString("INFLTCARCOD"));
		mailStatusVO.setIncommingFlightNumber(rs.getString("INFLTNUM"));
		mailStatusVO.setMailBagId(rs.getString("MALIDR"));
		mailStatusVO.setPol(rs.getString("POL"));
		mailStatusVO.setPou(rs.getString("POU"));
		//mailStatusVO.setWeight(rs.getString("WGT"));
		mailStatusVO.setWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(rs.getString("WGT")),0,MailConstantsVO.WEIGHTCODE_KILO));//added by A-7371
		mailStatusVO.setCompanyCode(rs.getString("CMPCOD"));
		mailStatusVO.setContainerNumber(rs.getString("CONNUM"));
		mailStatusVO.setLegStatus(rs.getString("LEGSTA"));
		mailStatusVO.setFlightRoute(rs.getString("FLTROU"));		
		if (rs.getTimestamp("STD") != null) {
			mailStatusVO.setScheduledDepartureTime(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, rs.getTimestamp("STD")));
		}
		if (rs.getTimestamp("FLTDAT") != null) {
			mailStatusVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, rs.getTimestamp("FLTDAT")));
		}
		if (rs.getTimestamp("INFLTDAT") != null) {
			mailStatusVO.setIncommingFlightDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, rs.getTimestamp("INFLTDAT")));
		}
		
		return mailStatusVO;
	}

}
