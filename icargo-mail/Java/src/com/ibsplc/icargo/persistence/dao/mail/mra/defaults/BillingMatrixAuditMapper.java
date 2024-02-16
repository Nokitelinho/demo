/*
 * BillingMatrixAudit.java Created on Aug 6, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5255 
 * @version	0.1, Aug 6, 2015
 * 
 *
 */
/**
 * Revision History Revision Date Author Description 0.1 Aug 6, 2015 A-5255
 * First draft
 */

public class BillingMatrixAuditMapper implements Mapper<AuditDetailsVO> {
	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	/**
	 * @author A-5255
	 * @param arg0
	 * @return
	 * @throws SQLException
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 */

	@Override
	public AuditDetailsVO map(ResultSet rs) throws SQLException {
		AuditDetailsVO auditDetailsVO = new AuditDetailsVO();
		auditDetailsVO.setAction(rs.getString("ACTCOD"));
		auditDetailsVO.setRemarks(rs.getString("AUDRMK"));
		auditDetailsVO.setAdditionalInformation(rs.getString("ADLINF"));
		auditDetailsVO.setStationCode(rs.getString("STNCOD"));
		auditDetailsVO.setLastUpdateUser(rs.getString("UPDUSR"));
		if (rs.getTimestamp("UPDTXNTIM") != null) {
			auditDetailsVO.setLastUpdateTime(new LocalDate(rs
					.getString("STNCOD"), Location.ARP, rs
					.getTimestamp("UPDTXNTIM")));
		} else if (rs.getTimestamp("UPDTXNTIMUTC") != null)
			{
				auditDetailsVO.setLastUpdateTime(new LocalDate("***", Location.NONE, rs.getTimestamp("UPDTXNTIMUTC")));
			}

		this.log.exiting("ULDAuditEnquiryMapper", "map");
		return auditDetailsVO;
	}

}
