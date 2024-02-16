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
public class ServiceFailureMultiMapper implements MultiMapper<MailMonitorSummaryVO> {
	private static final String CLASS_NAME = "MailPerformanceMonitorMapper";

	@Override
	public List<MailMonitorSummaryVO> map(ResultSet rs) throws SQLException {
		log.debug(CLASS_NAME + " : " + "ServiceFailureMultiMapper" + " Entering");
		List<MailMonitorSummaryVO> mailMonitorSummaryVOs = new ArrayList<MailMonitorSummaryVO>();
		while (rs.next()) {
			MailMonitorSummaryVO totalsSummaryVO = new MailMonitorSummaryVO();
			totalsSummaryVO.setMonitoringType("SERVICE_FAILURE");
			totalsSummaryVO.setSector("TOTAL");
			totalsSummaryVO.setValue(rs.getDouble("TOTAL"));
			mailMonitorSummaryVOs.add(totalsSummaryVO);
			MailMonitorSummaryVO originScanSummaryVO = new MailMonitorSummaryVO();
			originScanSummaryVO.setMonitoringType("SERVICE_FAILURE");
			originScanSummaryVO.setSector("Missing origin scan");
			originScanSummaryVO.setValue(rs.getDouble("MISSINGORGSCAN"));
			mailMonitorSummaryVOs.add(originScanSummaryVO);
			MailMonitorSummaryVO destScanSummaryVO = new MailMonitorSummaryVO();
			destScanSummaryVO.setMonitoringType("SERVICE_FAILURE");
			destScanSummaryVO.setSector("Missing delivery scan");
			destScanSummaryVO.setValue(rs.getDouble("MISSINGDLVSCAN"));
			mailMonitorSummaryVOs.add(destScanSummaryVO);
			MailMonitorSummaryVO bothScanSummaryVO = new MailMonitorSummaryVO();
			bothScanSummaryVO.setMonitoringType("SERVICE_FAILURE");
			bothScanSummaryVO.setSector("Missing both origin and delivery scan");
			bothScanSummaryVO.setValue(rs.getDouble("MISSINGBOTHSCAN"));
			mailMonitorSummaryVOs.add(bothScanSummaryVO);
		}
		log.debug(CLASS_NAME + " : " + "ServiceFailureMultiMapper" + " Exiting");
		return mailMonitorSummaryVOs;
	}
}
