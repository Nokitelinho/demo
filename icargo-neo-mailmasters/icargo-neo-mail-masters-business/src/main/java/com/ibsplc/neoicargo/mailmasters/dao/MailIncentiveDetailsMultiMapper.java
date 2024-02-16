package com.ibsplc.neoicargo.mailmasters.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.mailmasters.vo.IncentiveConfigurationDetailVO;
import com.ibsplc.neoicargo.mailmasters.vo.IncentiveConfigurationVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import java.time.format.DateTimeFormatter;
import com.ibsplc.neoicargo.framework.util.currency.Money;

@Slf4j
public class MailIncentiveDetailsMultiMapper implements MultiMapper<IncentiveConfigurationVO> {
	private static final String CLASS_NAME = "MailIncentiveDetailsMapper";

	public List<IncentiveConfigurationVO> map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		List<IncentiveConfigurationVO> incentiveVOs = new ArrayList<IncentiveConfigurationVO>();
		;
		Map<String, IncentiveConfigurationVO> incentiveMap = new LinkedHashMap<String, IncentiveConfigurationVO>();
		String currentKey = null;
		String previousKey = null;
		Collection<IncentiveConfigurationDetailVO> incentiveDetailVOs = null;
		IncentiveConfigurationVO incentiveConfigurationVO = null;
		IncentiveConfigurationDetailVO incentiveDetailVO = null;
		Date validFrom = null;
		Date validTo = null;
		while (rs.next()) {
			currentKey = new StringBuilder().append(rs.getString("CMPCOD")).append(rs.getString("INSSERNUM"))
					.append(rs.getString("POACOD")).toString();
			log.debug("" + "currentKey " + " " + currentKey);
			if (!incentiveMap.containsKey(currentKey)) {
				incentiveConfigurationVO = new IncentiveConfigurationVO();
				incentiveDetailVOs = new ArrayList<IncentiveConfigurationDetailVO>();
				incentiveDetailVO = new IncentiveConfigurationDetailVO();
				if (MailConstantsVO.FLAG_YES.equals(rs.getString("INCFLG"))) {
					incentiveConfigurationVO.setCompanyCode(rs.getString("CMPCOD"));
					incentiveConfigurationVO.setIncentiveSerialNumber(rs.getInt("INSSERNUM"));
					incentiveConfigurationVO.setPaCode(rs.getString("POACOD"));
					incentiveConfigurationVO.setIncentiveFlag(rs.getString("INCFLG"));
					incentiveConfigurationVO.setServiceRespFlag(rs.getString("SRVRSPLANFLG"));
					incentiveConfigurationVO.setIncPercentage(rs.getDouble("INCPER"));
					validFrom = rs.getDate("VLDFRMDAT");
					incentiveConfigurationVO.setIncValidFrom((localDateUtil.getLocalDate(null, validFrom))
							.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
					validTo = rs.getDate("VLDTOODAT");
					incentiveConfigurationVO.setIncValidTo((localDateUtil.getLocalDate(null, validTo))
							.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
					incentiveConfigurationVO.setFormula(rs.getString("DISPAREXP"));
					incentiveConfigurationVO.setBasis(rs.getString("BSSEXP"));
					incentiveDetailVO.setCompanyCode(rs.getString("CMPCOD"));
					incentiveDetailVO.setExcludeFlag(rs.getString("EXCFLG"));
					incentiveDetailVO.setIncentiveSerialNumber(rs.getInt("INSSERNUM"));
					incentiveDetailVO.setSequenceNumber(rs.getString("SEQNUM"));
					incentiveDetailVO.setIncParameterCode(rs.getString("PARCOD"));
					incentiveDetailVO.setIncParameterType(rs.getString("PARTYP"));
					incentiveDetailVO.setIncParameterValue(rs.getString("PARVAL"));
					incentiveDetailVOs.add(incentiveDetailVO);
					incentiveConfigurationVO.setIncentiveConfigurationDetailVOs(incentiveDetailVOs);
				} else if (MailConstantsVO.FLAG_NO.equals(rs.getString("INCFLG"))) {
					incentiveConfigurationVO.setCompanyCode(rs.getString("CMPCOD"));
					incentiveConfigurationVO.setIncentiveSerialNumber(rs.getInt("INSSERNUM"));
					incentiveConfigurationVO.setPaCode(rs.getString("POACOD"));
					incentiveConfigurationVO.setIncentiveFlag(rs.getString("INCFLG"));
					incentiveConfigurationVO.setServiceRespFlag(rs.getString("SRVRSPLANFLG"));
					incentiveConfigurationVO.setDisIncPercentage(rs.getDouble("INCPER"));
					validFrom = rs.getDate("VLDFRMDAT");
					incentiveConfigurationVO.setDisIncValidFrom((localDateUtil.getLocalDate(null, validFrom))
							.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
					validTo = rs.getDate("VLDTOODAT");
					incentiveConfigurationVO.setDisIncValidTo((localDateUtil.getLocalDate(null, validTo))
							.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
					incentiveConfigurationVO.setFormula(rs.getString("DISPAREXP"));
					incentiveConfigurationVO.setBasis(rs.getString("BSSEXP"));
					incentiveDetailVO.setCompanyCode(rs.getString("CMPCOD"));
					incentiveDetailVO.setExcludeFlag(rs.getString("EXCFLG"));
					incentiveDetailVO.setIncentiveSerialNumber(rs.getInt("INSSERNUM"));
					incentiveDetailVO.setSequenceNumber(rs.getString("SEQNUM"));
					incentiveDetailVO.setDisIncParameterCode(rs.getString("PARCOD"));
					incentiveDetailVO.setDisIncParameterType(rs.getString("PARTYP"));
					incentiveDetailVO.setDisIncParameterValue(rs.getString("PARVAL"));
					incentiveDetailVOs.add(incentiveDetailVO);
					incentiveConfigurationVO.setIncentiveConfigurationDetailVOs(incentiveDetailVOs);
				}
				incentiveMap.put(currentKey, incentiveConfigurationVO);
				previousKey = currentKey;
			} else {
				incentiveConfigurationVO = incentiveMap.get(currentKey);
				incentiveDetailVOs = incentiveConfigurationVO.getIncentiveConfigurationDetailVOs();
				incentiveDetailVO = new IncentiveConfigurationDetailVO();
				if (MailConstantsVO.FLAG_YES.equals(rs.getString("INCFLG"))) {
					incentiveDetailVO.setCompanyCode(rs.getString("CMPCOD"));
					incentiveDetailVO.setExcludeFlag(rs.getString("EXCFLG"));
					incentiveDetailVO.setIncentiveSerialNumber(rs.getInt("INSSERNUM"));
					incentiveDetailVO.setSequenceNumber(rs.getString("SEQNUM"));
					incentiveDetailVO.setIncParameterCode(rs.getString("PARCOD"));
					incentiveDetailVO.setIncParameterType(rs.getString("PARTYP"));
					incentiveDetailVO.setIncParameterValue(rs.getString("PARVAL"));
					incentiveDetailVOs.add(incentiveDetailVO);
				} else if (MailConstantsVO.FLAG_NO.equals(rs.getString("INCFLG"))) {
					incentiveDetailVO.setCompanyCode(rs.getString("CMPCOD"));
					incentiveDetailVO.setExcludeFlag(rs.getString("EXCFLG"));
					incentiveDetailVO.setIncentiveSerialNumber(rs.getInt("INSSERNUM"));
					incentiveDetailVO.setSequenceNumber(rs.getString("SEQNUM"));
					incentiveDetailVO.setDisIncParameterCode(rs.getString("PARCOD"));
					incentiveDetailVO.setDisIncParameterType(rs.getString("PARTYP"));
					incentiveDetailVO.setDisIncParameterValue(rs.getString("PARVAL"));
					incentiveDetailVOs.add(incentiveDetailVO);
				}
			}
		}
		for (IncentiveConfigurationVO incentiveVO : incentiveMap.values()) {
			incentiveVOs.add(incentiveVO);
		}
		return incentiveVOs;
	}
}
