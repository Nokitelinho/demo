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
public class ForceMajeureCountMapper implements MultiMapper<MailMonitorSummaryVO> {
	private static final String CLASS_NAME = "MailPerformanceMonitorMapper";

	@Override
	public List<MailMonitorSummaryVO> map(ResultSet rs) throws SQLException {
		log.debug(CLASS_NAME + " : " + "ForceMajeureCountMapper" + " Entering");
		List<MailMonitorSummaryVO> mailMonitorSummaryVOs = new ArrayList<MailMonitorSummaryVO>();
		while (rs.next()) {
			MailMonitorSummaryVO totalsSummaryVO = new MailMonitorSummaryVO();
			totalsSummaryVO.setMonitoringType("FORCE_MAJEURE");
			totalsSummaryVO.setSector("TOTAL");
			totalsSummaryVO.setValue(rs.getDouble("TOTAL"));
			mailMonitorSummaryVOs.add(totalsSummaryVO);
			MailMonitorSummaryVO approvedSummaryVO = new MailMonitorSummaryVO();
			approvedSummaryVO.setMonitoringType("FORCE_MAJEURE");
			approvedSummaryVO.setSector("Approved");
			approvedSummaryVO.setValue(rs.getDouble("APPROVED"));
			mailMonitorSummaryVOs.add(approvedSummaryVO);
			MailMonitorSummaryVO rejectedSummaryVO = new MailMonitorSummaryVO();
			rejectedSummaryVO.setMonitoringType("FORCE_MAJEURE");
			rejectedSummaryVO.setSector("Rejected");
			rejectedSummaryVO.setValue(rs.getDouble("REJECTED"));
			mailMonitorSummaryVOs.add(rejectedSummaryVO);
			MailMonitorSummaryVO raisedSummaryVO = new MailMonitorSummaryVO();
			raisedSummaryVO.setMonitoringType("FORCE_MAJEURE");
			raisedSummaryVO.setSector("Raised");
			raisedSummaryVO.setValue(rs.getDouble("RAISED"));
			mailMonitorSummaryVOs.add(raisedSummaryVO);
		}
		return mailMonitorSummaryVOs;
	}
}
