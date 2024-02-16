package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.neoicargo.mail.vo.MailInConsignmentVO;

/** 
 * @author A-5219
 */
public class ConsignmentsOfMailbagsMultiMapper implements MultiMapper<HashMap<Long, MailInConsignmentVO>> {
	public List<HashMap<Long, MailInConsignmentVO>> map(ResultSet rs) throws SQLException {
		HashMap<Long, MailInConsignmentVO> historyMap = new HashMap<Long, MailInConsignmentVO>();
		Long key;
		while (rs.next()) {
			key = rs.getLong("MALSEQNUM");
			MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
			mailInConsignmentVO.setCompanyCode(rs.getString("CMPCOD"));
			mailInConsignmentVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
			mailInConsignmentVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
			mailInConsignmentVO.setPaCode(rs.getString("POACOD"));
			mailInConsignmentVO.setMailId(rs.getString("MALIDR"));
			mailInConsignmentVO.setMailSequenceNumber(key);
			historyMap.put(key, mailInConsignmentVO);
		}
		List<HashMap<Long, MailInConsignmentVO>> map = new ArrayList<HashMap<Long, MailInConsignmentVO>>();
		map.add(historyMap);
		return map;
	}
}
