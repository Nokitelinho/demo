package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.neoicargo.mail.vo.MailMonitorSummaryVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.currency.Money;

@Slf4j
public class OntimeMailbagsMapper implements MultiMapper<MailMonitorSummaryVO> {
	private static final String CLASS_NAME = "MailPerformanceMonitorMapper";

	@Override
	public List<MailMonitorSummaryVO> map(ResultSet rs) throws SQLException {
		log.debug(CLASS_NAME + " : " + "OntimeMailbagsMapper" + " Entering");
		List<MailMonitorSummaryVO> mailMonitorSummaryVOs = new ArrayList<MailMonitorSummaryVO>();
		while (rs.next()) {
			MailMonitorSummaryVO totalsSummaryVO = new MailMonitorSummaryVO();
			totalsSummaryVO.setMonitoringType("ON_TIME_MAILBAGS");
			totalsSummaryVO.setSector("TOTAL");
			totalsSummaryVO.setValue(rs.getDouble("TOTAL"));
			mailMonitorSummaryVOs.add(totalsSummaryVO);
			MailMonitorSummaryVO ontimeSummaryVO = new MailMonitorSummaryVO();
			ontimeSummaryVO.setMonitoringType("ON_TIME_MAILBAGS");
			ontimeSummaryVO.setSector("Mailbags delivered on time");
			ontimeSummaryVO.setValue(rs.getDouble("ONTIMDLV"));
			mailMonitorSummaryVOs.add(ontimeSummaryVO);
			MailMonitorSummaryVO destScanSummaryVO = new MailMonitorSummaryVO();
			destScanSummaryVO.setMonitoringType("ON_TIME_MAILBAGS");
			destScanSummaryVO.setSector("Mailbags with delayed delivery time");
			destScanSummaryVO.setValue(rs.getDouble("NOTONTIMDLV"));
			mailMonitorSummaryVOs.add(destScanSummaryVO);
		}
		return mailMonitorSummaryVOs;
	}
}
