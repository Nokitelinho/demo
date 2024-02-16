package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagInULDForSegmentVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class InboundFlightDetailsForAutoArrivalMapper implements Mapper<MailbagInULDForSegmentVO> {

	private Log log = LogFactory.getLogger("MAIL_OPERATIONS");

	/**
	 * @author a-8353 
	 * @param rs
	 * @return  
	 * @throws SQLException
	 */
	public MailbagInULDForSegmentVO map(ResultSet rs) throws SQLException {
		log.log(Log.INFO, "Entering the MailbagEnquiryMapper");
		MailbagInULDForSegmentVO mailbagInULDForSegmentVO = new MailbagInULDForSegmentVO();
		mailbagInULDForSegmentVO.setCompanyCode(rs.getString("CMPCOD"));
		mailbagInULDForSegmentVO.setCarrierId(rs.getInt("FLTCARIDR"));
		mailbagInULDForSegmentVO.setFlightNumber(rs.getString("FLTNUM"));
		mailbagInULDForSegmentVO.setContainerNumber(rs.getString("ULDNUM")); 
		mailbagInULDForSegmentVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		mailbagInULDForSegmentVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		mailbagInULDForSegmentVO.setAcceptanceFlag(rs.getString("ACPSTA"));
		mailbagInULDForSegmentVO.setArrivalFlag(rs.getString("ARRSTA"));
		mailbagInULDForSegmentVO.setAssignedPort(rs.getString("ASGPRT"));
		mailbagInULDForSegmentVO.setContainerType(rs.getString("CONTYP"));
		mailbagInULDForSegmentVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		mailbagInULDForSegmentVO.setPaBuiltFlag(rs.getString("POAFLG"));
		return mailbagInULDForSegmentVO;
		
	

}
}