package com.ibsplc.neoicargo.mailmasters.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.neoicargo.mailmasters.vo.MailEventVO;

public class MailboxIdMapper implements MultiMapper<MailEventVO> {
	public ArrayList<MailEventVO> map(ResultSet rs) throws SQLException {
		ArrayList<MailEventVO> mailEventVOs = new ArrayList<MailEventVO>();
		while (rs.next()) {
			MailEventVO mailEventVO = new MailEventVO();
			mailEventVO.setMailCategory(rs.getString("MALCTG"));
			mailEventVO.setMailClass(rs.getString("MALSUBCLS"));
			mailEventVO.setReceived(rs.getBoolean("RCVFLG"));
			mailEventVO.setUplifted(rs.getBoolean("UPLFLG"));
			mailEventVO.setAssigned(rs.getBoolean("ASGFLG"));
			mailEventVO.setPending(rs.getBoolean("PNDFLG"));
			mailEventVO.setReadyForDelivery(rs.getBoolean("RDYDLVFLG"));
			mailEventVO.setTransportationCompleted(rs.getBoolean("TRTCPLFLG"));
			mailEventVO.setArrived(rs.getBoolean("ARRFLG"));
			mailEventVO.setHandedOver(rs.getBoolean("HNDOVRFLG"));
			mailEventVO.setReturned(rs.getBoolean("RTNFLG"));
			mailEventVO.setDelivered(rs.getBoolean("DLVFLG"));
			mailEventVO.setLoadedResditFlag(rs.getBoolean("LODFLG"));
			mailEventVO.setOnlineHandedOverResditFlag(rs.getBoolean("HNDOVRONLFLG"));
			mailEventVO.setHandedOverReceivedResditFlag(rs.getBoolean("HNDOVRRCVFLG"));
			mailEventVO.setFoundFlag(rs.getBoolean("FNDFLG"));
			mailEventVO.setLostFlag(rs.getBoolean("LSTFLG"));
			mailEventVOs.add(mailEventVO);
		}
		return mailEventVOs;
	}
}
