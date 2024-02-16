/*
 * MailbagMapper.java Created on Jun 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author a-1303
 */
public class MailbagDetailsForValidationMapper implements Mapper<MailbagVO> {

	private static final String MAILTRACKING_DEFAULTS = "MAILTRACKING_DEFAULTS";
	private static final Log LOGGER = LogFactory.getLogger(MAILTRACKING_DEFAULTS);

	/**
	 * @author a-1936 This method is used to set the values from the resultset
	 *         into Mapper
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public MailbagVO map(ResultSet rs) throws SQLException {
		LOGGER.entering(MAILTRACKING_DEFAULTS, "MailbagDetailsForValidationMapper");
		//SELECT latacttim,MST.DSPDAT
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailbagId(rs.getString("MALIDR"));
		mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM")); /*added by A-8149 for ICRD-248207*/
		mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
		mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
		mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
		mailbagVO.setMailCategoryCode(rs.getString("MALCTG"));
		mailbagVO.setPaCode(rs.getString("POACOD"));
		mailbagVO.setMailDestination(rs.getString("DSTCOD"));
		if (rs.getTimestamp("LATACTTIM") != null) {
			mailbagVO.setLatestAcceptanceTime(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, rs.getTimestamp("LATACTTIM")));
		}
		if (rs.getTimestamp("LSTUPDTIM") != null) {
			mailbagVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, rs.getTimestamp("LSTUPDTIM")));
		}
		if (rs.getTimestamp("DSPDAT") != null) {
			mailbagVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, rs.getTimestamp("DSPDAT")));
		}
		mailbagVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
		mailbagVO.setOrigin(rs.getString("ORGCOD"));
		mailbagVO.setDestination(rs.getString("DSTCOD"));
		mailbagVO.setMailOrigin(rs.getString("ORGCOD"));
		mailbagVO.setSecurityStatusCode(rs.getString("SECSTACOD"));
		LOGGER.log(Log.FINE, "mailbagVO from mapper++++", mailbagVO);
		LOGGER.exiting(MAILTRACKING_DEFAULTS, "MailbagDetailsForValidationMapper");
		return mailbagVO;
	}
}
