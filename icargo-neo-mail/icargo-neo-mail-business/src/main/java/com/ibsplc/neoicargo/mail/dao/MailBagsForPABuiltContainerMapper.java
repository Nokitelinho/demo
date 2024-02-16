package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import com.ibsplc.neoicargo.framework.util.currency.Money;

public class MailBagsForPABuiltContainerMapper implements Mapper<MailbagVO> {
	public MailbagVO map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		MailbagVO mailBagVo = new MailbagVO();
		mailBagVo.setCompanyCode(rs.getString("CMPCOD"));
		mailBagVo.setMailbagId(rs.getString("MALIDR"));
		mailBagVo.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		mailBagVo.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		mailBagVo.setContainerNumber(rs.getString("CONNUM"));
		mailBagVo.setPou(rs.getString("POU"));
		mailBagVo.setDoe(rs.getString("DSTEXGOFC"));
		mailBagVo.setOoe(rs.getString("ORGEXGOFC"));
		mailBagVo.setMailCategoryCode(rs.getString("MALCTG"));
		mailBagVo.setMailSubclass(rs.getString("MALSUBCLS"));
		mailBagVo.setOrigin(rs.getString("ORGCOD"));
		mailBagVo.setDestination(rs.getString("DSTCOD"));
		mailBagVo.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		mailBagVo.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
		mailBagVo.setFlightNumber(rs.getString("FLTNUM"));
		mailBagVo.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
		mailBagVo.setCarrierId(rs.getInt("FLTCARIDR"));
		mailBagVo.setScannedDate(localDateUtil.getLocalDate(null, false));
		mailBagVo.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT"))));
		mailBagVo.setContainerJourneyId((rs.getString("MALJNRIDR")));
		return mailBagVo;
	}
}
