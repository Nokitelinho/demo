package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

public class CarditPawbShipmentDetailsMapper implements MultiMapper<MailbagVO> {

	@Override
	public List<MailbagVO> map(ResultSet rs) throws SQLException {
		List<MailbagVO> mailbagVOs = new ArrayList<>();
		while (rs.next()) {
			MailbagVO mailbagVO = new MailbagVO();
			mailbagVO.setDocumentNumber(rs.getString("MSTDOCNUM"));
			Measure wgt=new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT"));
			mailbagVO.setWeight(wgt);
			mailbagVO.setMailbagId(rs.getString("MALIDR"));
			mailbagVOs.add(mailbagVO);

		}
		return mailbagVOs;
	}

}
