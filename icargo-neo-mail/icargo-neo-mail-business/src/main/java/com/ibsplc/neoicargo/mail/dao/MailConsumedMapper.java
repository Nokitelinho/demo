package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.currency.Money;

public class MailConsumedMapper implements Mapper<MailbagVO> {
	@Override
	public MailbagVO map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		MailbagVO mailbagVO = new MailbagVO();
		double displayStrWt = rs.getDouble("TOTWGT");
		Quantity strWt = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(displayStrWt));
		mailbagVO.setWeight(strWt);
		double volume = rs.getDouble("TOTVOL");
		Quantity volumeMail = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(volume));
		mailbagVO.setVolume(volumeMail);
		mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
		return mailbagVO;
	}
}
