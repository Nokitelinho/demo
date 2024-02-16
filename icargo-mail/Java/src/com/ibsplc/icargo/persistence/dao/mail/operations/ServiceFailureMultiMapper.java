package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.MailMonitorSummaryVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ServiceFailureMultiMapper  implements MultiMapper<MailMonitorSummaryVO> {
	private Log log = LogFactory.getLogger("MAIL operations");
	private static final String CLASS_NAME = "MailPerformanceMonitorMapper";

	@Override
	public List<MailMonitorSummaryVO> map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "ServiceFailureMultiMapper");
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
		log.exiting(CLASS_NAME, "ServiceFailureMultiMapper");
		return mailMonitorSummaryVOs;
	}

}
