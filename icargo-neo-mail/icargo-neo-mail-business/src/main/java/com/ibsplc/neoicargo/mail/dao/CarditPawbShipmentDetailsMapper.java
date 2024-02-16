package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.currency.Money;

public class CarditPawbShipmentDetailsMapper implements MultiMapper<MailbagVO> {
	@Override
	public List<MailbagVO> map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		List<MailbagVO> mailbagVOs = new ArrayList<>();
		while (rs.next()) {
			MailbagVO mailbagVO = new MailbagVO();
			mailbagVO.setDocumentNumber(rs.getString("MSTDOCNUM"));
			Quantity wgt = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT")));
			mailbagVO.setWeight(wgt);
			mailbagVO.setMailbagId(rs.getString("MALIDR"));
			mailbagVOs.add(mailbagVO);
		}
		return mailbagVOs;
	}
}
