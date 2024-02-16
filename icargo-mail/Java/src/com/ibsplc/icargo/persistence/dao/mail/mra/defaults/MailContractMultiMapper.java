/*
 * MailContractMultiMapper.java Created on Apr 02, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2518
 * 
 */
public class MailContractMultiMapper implements MultiMapper<MailContractVO> {
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	/**
	 * @param rs
	 * @return List<MailContractVO>
	 * @throws SQLException
	 */
	public List<MailContractVO> map(ResultSet rs) throws SQLException {
		log.entering("MailContractMultiMapper", "map");
		Map<String, MailContractVO> mailContractMap = new HashMap<String, MailContractVO>();
		Map<String, MailContractDetailsVO> mailContractDetailsMap = new HashMap<String, MailContractDetailsVO>();
		Map<String, String> billingMatrixMap = new HashMap<String, String>();
		List<MailContractVO> mailContractVos = new ArrayList<MailContractVO>();
		MailContractVO mailContractVo = null;
		Collection<MailContractDetailsVO> mailContractDetailsVos = null;
		Collection<String> billingMatrixDetails = null;
		MailContractDetailsVO mailContractDetailsVo = null;
		String mailContractKey = null;
		String mailContractDetailsKey = null;
		String billingMatrixKey = null;
		while (rs.next()) {
			mailContractKey = new StringBuilder()
					.append(rs.getString("CMPCOD")).append(
							rs.getString("CTRREFNUM")).toString();
			if (rs.getInt("SERNUM") != 0) {
				mailContractDetailsKey = new StringBuilder().append(
						mailContractKey).append(rs.getInt("SERNUM")).toString();
			}
			billingMatrixKey = new StringBuilder().append(mailContractKey)
					.append(rs.getString("BLGMTXCOD")).toString();
			if (!mailContractMap.containsKey(mailContractKey)) {
				mailContractVo = new MailContractVO();
				mailContractDetailsVos = new ArrayList<MailContractDetailsVO>();
				billingMatrixDetails = new ArrayList<String>();
				mailContractVo.setAgreementStatus(rs.getString("AGRSTA"));
				mailContractVo.setAgreementType(rs.getString("AGRTYP"));
				mailContractVo.setAirlineCode(rs.getString("ARLCOD"));
				mailContractVo.setAirlineIdentifier(rs.getInt("ARLIDR"));
				mailContractVo.setCompanyCode(rs.getString("CMPCOD"));
				mailContractVo.setContractDescription(rs.getString("CTRDSC"));
				mailContractVo.setContractReferenceNumber(rs
						.getString("CTRREFNUM"));
				mailContractVo.setGpaCode(rs.getString("GPACOD"));
				mailContractVo.setVersionNumber(rs.getString("VERNUM"));
				if (rs.getDate("CREDAT") != null) {
					mailContractVo.setCreationDate(new LocalDate(NO_STATION,
							NONE, rs.getDate("CREDAT")));
				}
				if (rs.getDate("VLDFRMDAT") != null) {
					mailContractVo.setValidFromDate(new LocalDate(NO_STATION,
							NONE, rs.getDate("VLDFRMDAT")));
				}
				if (rs.getDate("VLDTOODAT") != null) {
					mailContractVo.setValidToDate(new LocalDate(NO_STATION,
							NONE, rs.getDate("VLDTOODAT")));
				}
				mailContractVos.add(mailContractVo);
				mailContractVo
						.setMailContractDetailsVos(mailContractDetailsVos);
				mailContractVo.setBillingDetails(billingMatrixDetails);
				mailContractMap.put(mailContractKey, mailContractVo);
			}
			if (mailContractDetailsKey != null) {
				if (!mailContractDetailsMap.containsKey(mailContractDetailsKey)) {
					mailContractDetailsVo = new MailContractDetailsVO();
					mailContractDetailsVo.setAcceptanceToDeparture(rs
							.getString("SLAACTONE"));
					mailContractDetailsVo.setArrivalToDelivery(rs
							.getString("SLAACTTWO"));
					mailContractDetailsVo
							.setCompanyCode(rs.getString("CMPCOD"));
					mailContractDetailsVo.setContractReferenceNumber(rs
							.getString("CTRREFNUM"));
					mailContractDetailsVo.setDestinationCode(rs
							.getString("DSTCOD"));
					mailContractDetailsVo.setOriginCode(rs.getString("ORGCOD"));
					mailContractDetailsVo.setRemarks(rs.getString("RMK"));
					mailContractDetailsVo.setSerialNumber(rs.getInt("SERNUM"));
					if (mailContractDetailsVos != null) {
						mailContractDetailsVos.add(mailContractDetailsVo);
					}
					mailContractDetailsMap.put(mailContractDetailsKey,
							mailContractDetailsVo);
				}
			}
			if (!billingMatrixMap.containsKey(billingMatrixKey)) {
				if (billingMatrixDetails != null) {
					billingMatrixDetails.add(rs.getString("BLGMTXCOD"));
				}
				billingMatrixMap.put(billingMatrixKey, rs
						.getString("BLGMTXCOD"));
			}
		}
		log.exiting("MailContractMultiMapper", "map");
		return mailContractVos;
	}
}
