package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.framework.util.unit.MeasureMapper;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class OutboundCarditGroupMapper implements Mapper<MailbagVO> {
	/** 
	* @author a-2553 This method is used to set the values from the resultsetinto Mapper
	* @param rs
	* @return
	* @throws SQLException 
	*/
	public MailbagVO map(ResultSet rs) throws SQLException {
		MeasureMapper measureMapper = ContextUtil.getInstance().getBean(MeasureMapper.class);
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		log.info("Entering the OutboundCarditGroupMapper");
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setDestCityDesc(rs.getString("DSTCOD"));
		mailbagVO.setCount(rs.getInt("COUNT"));
		mailbagVO.setAcceptedBags(rs.getInt("ACCPCNT"));
		mailbagVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf((rs.getDouble("TOTWGT")))));
		mailbagVO.setAcceptedWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ACCPWGT"))));
		return mailbagVO;
	}
}
