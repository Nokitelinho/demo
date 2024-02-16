package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.neoicargo.mail.vo.MailTransitVO;

public class MailTransitMapper implements MultiMapper<MailTransitVO> {
	@Override
	public List<MailTransitVO> map(ResultSet rs) throws SQLException {
		List<MailTransitVO> mailTransitVOs = new ArrayList<>();
		MailTransitVO mailTransitVO = null;
		while (rs.next()) {
			mailTransitVO = new MailTransitVO();
			mailTransitVO.setCarrierCode(rs.getString("FLTCARCOD"));
			mailTransitVO.setMailBagDestination(rs.getString("MALDSTCOD"));
			mailTransitVO.setTotalNoImportBags(rs.getString("TOTIMPMAL"));
			mailTransitVO.setTotalWeightImportBags(rs.getString("TOTIMPMALWGT"));
			mailTransitVO.setCountOfExportNonAssigned(rs.getString("NOTASGMALCNT"));
			mailTransitVO.setTotalWeightOfExportNotAssigned(rs.getString("NOTASGMALWGT"));
			mailTransitVOs.add(mailTransitVO);
		}
		return mailTransitVOs;
	}
}
