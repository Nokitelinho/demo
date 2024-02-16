/*
 * DamageMailReportMapper.java Created on July 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-3227 RENO K ABRAHAM
 *
 */
public class DamageMailReportMapper  implements Mapper<DamagedMailbagVO> {

	// private Log log= LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public DamagedMailbagVO map(ResultSet rs) throws SQLException {

		String airportCode = rs.getString("ARPCOD");
		String doe=rs.getString("DSTEXGOFC");
		String ooe=rs.getString("ORGEXGOFC");
		DamagedMailbagVO damagedMailbagVO = new DamagedMailbagVO();
		damagedMailbagVO.setAirportCode(rs.getString("ARPCOD"));
		damagedMailbagVO.setDamageCode(rs.getString("DMGCOD"));
		if (rs.getTimestamp("DMGDAT") != null) {
			damagedMailbagVO.setDamageDate(new LocalDate(airportCode,
					Location.ARP, rs.getTimestamp("DMGDAT")));
		}
		damagedMailbagVO.setDamageDescription(rs.getString("DMGDES"));		
		damagedMailbagVO.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
		damagedMailbagVO.setMailbagId(rs.getString("MALIDR"));
		damagedMailbagVO.setRemarks(rs.getString("RMK"));
		damagedMailbagVO.setCarrierCode(rs.getString("TWOAPHCOD"));
		damagedMailbagVO.setFlightNumber(rs.getString("FLTNUM"));
		//added by A-5844 for ICRD-67196 starts
		damagedMailbagVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
		//damagedMailbagVO.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
		damagedMailbagVO.setPaCode(rs.getString("POACOD"));
		damagedMailbagVO.setDsn(rs.getString("DSN"));
		damagedMailbagVO.setSubClassCode(rs.getString("SUBCLSCOD"));
		damagedMailbagVO.setSubClassGroup(rs.getString("SUBCLSGRP"));
		//damagedMailbagVO.setWeight(rs.getDouble("WGT"));
		damagedMailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(rs.getString("WGT")),0.0,MailConstantsVO.WEIGHTCODE_KILO));//added by A-8353 for ICRD-274933
		damagedMailbagVO.setDeclaredValue(rs.getDouble("DCLVAL"));
		damagedMailbagVO.setFlightOrigin(rs.getString("POL"));
		damagedMailbagVO.setFlightDestination(rs.getString("POU"));
		damagedMailbagVO.setCurrencyCode(rs.getString("CURCOD"));
		damagedMailbagVO.setDeclaredValueTot(rs.getDouble("DCLVALCONVERTED"));
		damagedMailbagVO.setTotCurrencyCode(rs.getString("TOTDCLVALCUR"));
		if (rs.getTimestamp("FLTDAT") != null) {
		damagedMailbagVO.setFlightDate(new LocalDate(airportCode,
					Location.ARP, rs.getTimestamp("FLTDAT")));
		}		
		//damagedMailbagVO.setFlightDate(new LocalDate(airportCode,Location.ARP, rs.getTimestamp("FLTDAT")));
		//added by A-5844 for ICRD-67196 ends
		return damagedMailbagVO;
	}

}
