package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.MailMonitorSummaryVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ForceMajeureCountMapper  implements MultiMapper<MailMonitorSummaryVO> {
	private Log log = LogFactory.getLogger("MAIL operations");
	private static final String CLASS_NAME = "MailPerformanceMonitorMapper";

	@Override
	public List<MailMonitorSummaryVO> map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "ForceMajeureCountMapper");
		
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
