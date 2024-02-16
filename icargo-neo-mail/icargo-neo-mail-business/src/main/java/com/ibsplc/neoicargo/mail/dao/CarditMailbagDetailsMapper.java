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

/** 
 * @author a-2107This class is used to map the ResultSet into Vos returned to the Request...
 */
public class CarditMailbagDetailsMapper implements Mapper<MailbagVO> {
	public MailbagVO map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailbagId(rs.getString("MALIDR"));
		mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
		mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
		mailbagVO.setMailCategoryCode(rs.getString("MALCTG"));
		mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
		mailbagVO.setYear(rs.getInt("YER"));
		mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
		mailbagVO.setReceptacleSerialNumber(rs.getString("RSN"));
		mailbagVO.setHighestNumberedReceptacle(rs.getString("HSN"));
		mailbagVO.setRegisteredOrInsuredIndicator(rs.getString("REGIND"));
		Quantity strWt = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT")));
		mailbagVO.setStrWeight(strWt);
		mailbagVO.setWeight(strWt);
		mailbagVO.setMailOrigin(rs.getString("ORGCOD"));
		mailbagVO.setMailDestination(rs.getString("DSTCOD"));
		mailbagVO.setScannedDate(localDateUtil.getLocalDate(null, rs.getTimestamp("SCNDAT")));
		mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
		return mailbagVO;
	}
}
