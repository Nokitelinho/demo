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

/** 
 * @author This method is used to Map the ResultSet into PreAdviceDetailsVO
 */
public class MailTagIDMapper implements Mapper<MailbagVO> {
	/** 
	* @param rs
	* @return
	* @throws SQLException
	*/
	public MailbagVO map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
		mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		mailbagVO.setMailbagId(rs.getString("MALIDR"));
		mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
		mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
		mailbagVO.setMailCategoryCode(rs.getString("MALCTG"));
		mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
		mailbagVO.setYear(rs.getInt("YER"));
		mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
		mailbagVO.setReceptacleSerialNumber(rs.getString("RSN"));
		mailbagVO.setHighestNumberedReceptacle(rs.getString("HSN"));
		mailbagVO.setRegisteredOrInsuredIndicator(rs.getString("REGIND"));
		Quantity wgt = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT")));
		mailbagVO.setWeight(wgt);
		return mailbagVO;
	}
}
