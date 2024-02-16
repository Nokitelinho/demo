package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.framework.util.unit.MeasureMapper;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.MailAcceptanceVO;
import com.ibsplc.neoicargo.mail.vo.PreAdviceVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@Slf4j
public class OutboundFlightPreAdviceMapper implements Mapper<MailAcceptanceVO> {
	@Override
	public MailAcceptanceVO map(ResultSet rs) throws SQLException {
		MeasureMapper measureMapper = ContextUtil.getInstance().getBean(MeasureMapper.class);
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
		mailAcceptanceVO.setCompanyCode(rs.getString("CMPCOD"));
		mailAcceptanceVO.setCarrierId(rs.getInt("FLTCARIDR"));
		mailAcceptanceVO.setFlightNumber(rs.getString("FLTNUM"));
		mailAcceptanceVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
		PreAdviceVO preadvicevo = new PreAdviceVO();
		preadvicevo.setTotalBags(rs.getInt("RCPIDRCNT"));
		if (rs.getString("RCPWGT") != null) {
			String dspwgtunt = rs.getString("DSPWGTUNT");
			String rcpwgt = rs.getString("RCPWGT");
//			Quantity totalWeight = Objects.nonNull(dspwgtunt)?(quantities.getQuantity(Quantities.MAIL_WGT,
//					BigDecimal.valueOf(Double.parseDouble(rs.getString("RCPWGT"))),
//					BigDecimal.valueOf(Double.parseDouble(rs.getString("RCPWGT"))),dspwgtunt)):
//					quantities.getQuantity(Quantities.MAIL_WGT,BigDecimal.valueOf(Double.parseDouble(rs.getString("RCPWGT"))));
			Quantity totalWeight = quantities.getQuantity(Quantities.MAIL_WGT,
					BigDecimal.valueOf(Double.parseDouble(rs.getString("RCPWGT"))));
			if (Objects.nonNull(preadvicevo.getTotalWeight())) {
				preadvicevo.setTotalWeight(preadvicevo.getTotalWeight().add(totalWeight));
			} else {
				preadvicevo.setTotalWeight(totalWeight);
			}

		}
		mailAcceptanceVO.setPreadvice(preadvicevo);
		return mailAcceptanceVO;
	}
}
