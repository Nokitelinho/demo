package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailOnHandDetailsVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MailOnHandListMultiMapper implements MultiMapper<MailOnHandDetailsVO> {
	/** 
	* @param rs
	* @return List<MailOnHandDetailsVO>
	* @throws SQLException
	*/
	public List<MailOnHandDetailsVO> map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("MailOnHandListMultiMapper " + " : " + "map" + " Entering");
		List<MailOnHandDetailsVO> mailOnHandDetailsVOs = new ArrayList<MailOnHandDetailsVO>();
		String key = null;
		MailOnHandDetailsVO mailOnHandDetailsVO = null;
		Map<String, MailOnHandDetailsVO> mailOnHandDtls = new LinkedHashMap<String, MailOnHandDetailsVO>();
		log.debug("-----MailOnHandListMultiMapper---- " + " : " + "mapping" + " Entering");
		while (rs.next()) {
			String uldCount = null;
			double totalMailBagVolume = 0;
			double noOfMTSpace = 0;
			key = new StringBuilder().append(rs.getString("CMPCOD")).append("-").append(rs.getString("ASGPRT"))
					.append("-").append(rs.getString("DSTCOD")).append("-").append(rs.getString("SUBCLSGRP"))
					.toString();
			if (!mailOnHandDtls.containsKey(key)) {
				mailOnHandDetailsVO = new MailOnHandDetailsVO();
				mailOnHandDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
				mailOnHandDetailsVO.setCurrentAirport(rs.getString("ASGPRT"));
				mailOnHandDetailsVO.setDestination(rs.getString("DSTCOD"));
				mailOnHandDetailsVO.setSubclassGroup(rs.getString("SUBCLSGRP"));
				if (!MailConstantsVO.CONST_BULK.equals(rs.getString("CONTYP"))) {
					if (rs.getString("COUNTOFULD") != null && rs.getString("COUNTOFULD").trim().length() > 0) {
						uldCount = new StringBuilder().append(rs.getString("COUNTOFULD")).append(rs.getString("CONTYP"))
								.toString();
						mailOnHandDetailsVO.setUldCount(uldCount);
					}
				} else {
					mailOnHandDetailsVO.setMailTrolleyCount(rs.getInt("NOMT"));
				}
				if (MailConstantsVO.CONST_BULK.equals(rs.getString("CONTYP"))) {
					totalMailBagVolume = rs.getDouble("SUM(VOL)");
					log.debug("" + " totalMailBagVolume " + " " + totalMailBagVolume);
					if (totalMailBagVolume > 0) {
						noOfMTSpace = totalMailBagVolume / 4;
					}
					log.debug("" + " noOfMTSpace " + " " + noOfMTSpace);
					Double MTNum = Math.ceil(noOfMTSpace);
					int MTNumber = MTNum.intValue();
					log.debug("" + " MTNumber " + " " + MTNumber);
					mailOnHandDetailsVO.setNoOfMTSpace(MTNumber);
					log.debug("" + " mailOnHandDetailsVO.setNoOfMTSpace " + " " + mailOnHandDetailsVO.getNoOfMTSpace());
				}
				mailOnHandDetailsVO.setScanPort(rs.getString("SCNPRT"));
				mailOnHandDetailsVO
						.setScnDate(localDateUtil.getLocalDate(rs.getString("SCNPRT"), rs.getTimestamp("SCNDAT")));
				mailOnHandDetailsVO.setSubclassGroup(rs.getString("SUBCLSGRP"));
				mailOnHandDtls.put(key, mailOnHandDetailsVO);
			} else {
				mailOnHandDetailsVO = mailOnHandDtls.get(key);
				if (!MailConstantsVO.CONST_BULK.equals(rs.getString("CONTYP"))) {
					if (rs.getString("COUNTOFULD") != null && rs.getString("COUNTOFULD").trim().length() > 0) {
						if (mailOnHandDetailsVO.getUldCount() != null) {
							uldCount = new StringBuilder().append(mailOnHandDetailsVO.getUldCount()).append(", ")
									.append(rs.getString("COUNTOFULD")).append(rs.getString("CONTYP")).toString();
						} else {
							uldCount = new StringBuilder().append(rs.getString("COUNTOFULD"))
									.append(rs.getString("CONTYP")).toString();
						}
						mailOnHandDetailsVO.setUldCount(uldCount);
					}
				} else {
					if (MailConstantsVO.CONST_BULK.equals(rs.getString("CONTYP"))) {
						totalMailBagVolume = rs.getDouble("SUM(VOL)");
						log.debug("" + " totalMailBagVolume " + " " + totalMailBagVolume);
						if (totalMailBagVolume > 0) {
							noOfMTSpace = totalMailBagVolume / 4;
						}
						log.debug("" + " noOfMTSpace inside else part " + " " + noOfMTSpace);
						Double MTNum = Math.ceil(noOfMTSpace);
						int MTNumber = MTNum.intValue();
						log.debug("" + " MTNumber  inside else part  " + " " + MTNumber);
						mailOnHandDetailsVO.setNoOfMTSpace(MTNumber);
						log.debug("" + " mailOnHandDetailsVO.setNoOfMTSpace   inside else part " + " "
								+ mailOnHandDetailsVO.getNoOfMTSpace());
					}
					mailOnHandDetailsVO.setMailTrolleyCount(rs.getInt("NOMT"));
				}
				ZonedDateTime scnDate = localDateUtil.getLocalDate(rs.getString("SCNPRT"), rs.getTimestamp("SCNDAT"));
				mailOnHandDetailsVO.setScanPort(rs.getString("SCNPRT"));
				if (mailOnHandDetailsVO.getScnDate().isAfter(scnDate)) {
					mailOnHandDetailsVO.setScnDate(scnDate);
				}
			}
		}
		for (MailOnHandDetailsVO mailOnHandDtlVO : mailOnHandDtls.values()) {
			ZonedDateTime currentDate = localDateUtil.getLocalDate(mailOnHandDtlVO.getScanPort(), true);
			//TODO: Neo to verify the code
			long timeDiff = currentDate.toEpochSecond() -(mailOnHandDtlVO.getScnDate().toEpochSecond());
			int timediffIndays = (int) (timeDiff / (1000 * 60 * 60 * 24));
			if (timeDiff % (1000 * 60 * 60 * 24) > 0) {
				timediffIndays = timediffIndays + 1;
			}
			mailOnHandDtlVO.setNoOfDaysInCurrentLoc(timediffIndays);
			if (mailOnHandDtlVO.getUldCount() != null && mailOnHandDtlVO.getUldCount().trim().length() > 1) {
				String[] uldType = mailOnHandDtlVO.getUldCount().split(",");
				StringBuilder uldDisplay = new StringBuilder();
				if (uldType.length > 3) {
					for (int count = 0; count < 3; count++) {
						uldDisplay.append(uldType[count]);
						if (count < 2) {
							uldDisplay.append(",");
						}
					}
					uldDisplay.append("..");
					mailOnHandDtlVO.setUldCountDisplay(uldDisplay.toString());
				} else {
					mailOnHandDtlVO.setUldCountDisplay(mailOnHandDtlVO.getUldCount());
				}
			}
			mailOnHandDetailsVOs.add(mailOnHandDtlVO);
		}
		return mailOnHandDetailsVOs;
	}
}
