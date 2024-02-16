package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

public class MailConsumedMapper implements Mapper<MailbagVO> {

	@Override
	public MailbagVO map(ResultSet rs) throws SQLException {
		MailbagVO mailbagVO=new MailbagVO();
		double displayStrWt = rs.getDouble("TOTWGT");
		Measure strWt = new Measure(UnitConstants.MAIL_WGT, displayStrWt);
		mailbagVO.setWeight(strWt);
		double volume = rs.getDouble("TOTVOL");
		Measure volumeMail = new Measure(UnitConstants.MAIL_WGT, volume);
		mailbagVO.setVolume(volumeMail);
		mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
		return mailbagVO;
	}

}
