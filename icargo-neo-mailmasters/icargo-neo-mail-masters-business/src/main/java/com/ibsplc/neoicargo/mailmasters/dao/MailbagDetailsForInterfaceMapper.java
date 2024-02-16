package com.ibsplc.neoicargo.mailmasters.dao;

import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mailmasters.vo.MailbagDetailsVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import com.ibsplc.neoicargo.framework.util.currency.Money;

@Slf4j
public class MailbagDetailsForInterfaceMapper implements Mapper<MailbagDetailsVO> {
	private static final String MAILTRACKING_DEFAULTS = "MAILTRACKING_DEFAULTS";

	/** 
	* Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet) Added by 			: 204082 on 20-Oct-2022 Used for 	: Parameters	:	@param rs Parameters	:	@return MailbagDetailsVO Parameters	:	@throws SQLException
	*/
	@Override
	public MailbagDetailsVO map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug(MAILTRACKING_DEFAULTS + " : " + "MailbagMasterDataDetailsMapper" + " Entering");
		MailbagDetailsVO mailbagDetailsVO = new MailbagDetailsVO();
		mailbagDetailsVO.setMailbagId(rs.getString("MALIDR"));
		mailbagDetailsVO
				.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT"))));
		mailbagDetailsVO.setMailSequenceNumber(Long.parseLong(rs.getString("MALSEQNUM")));
		mailbagDetailsVO.setFlightCarrierCode(rs.getString("TWOAPHCOD"));
		mailbagDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		mailbagDetailsVO.setSegmentOrigin(rs.getString("POL"));
		mailbagDetailsVO.setSegmentDestination(rs.getString("POU"));
		if (rs.getString("POL") != null && rs.getTimestamp("STD") != null) {
			mailbagDetailsVO.setStd(localDateUtil.getLocalDate(rs.getString("POL"), rs.getTimestamp("STD")));
		}
		if (rs.getTimestamp("STDUTC") != null) {
			mailbagDetailsVO.setStdutc(localDateUtil.getLocalDate(null, rs.getTimestamp("STDUTC")));
		}
		if (rs.getString("POU") != null && rs.getTimestamp("STA") != null) {
			mailbagDetailsVO.setSta(localDateUtil.getLocalDate(rs.getString("POU"), rs.getTimestamp("STA")));
		}
		if (rs.getTimestamp("STAUTC") != null) {
			mailbagDetailsVO.setStautc(localDateUtil.getLocalDate(null, rs.getTimestamp("STAUTC")));
		}
		mailbagDetailsVO.setInboundResditEvtCode(rs.getString("INBEVTCOD"));
		mailbagDetailsVO.setInboundSource(rs.getString("INBSRC"));
		mailbagDetailsVO.setArrivalAirport(rs.getString("INBSCNPRT"));
		mailbagDetailsVO.setOutboundResditEvtCode(rs.getString("OUTEVTCOD"));
		mailbagDetailsVO.setOutboundSource(rs.getString("OUTSRC"));
		mailbagDetailsVO.setDepartureAirport(rs.getString("OUTSCNPRT"));
		mailbagDetailsVO.setContainerId(rs.getString("CONNUM"));
		mailbagDetailsVO.setHbaType(rs.getString("HBATYP"));
		mailbagDetailsVO.setHbaPosition(rs.getString("HBAPOS"));
		log.debug("" + "mailbagDetailsVO from mapper++++" + " " + mailbagDetailsVO);
		log.debug(MAILTRACKING_DEFAULTS + " : " + "MailbagMasterDataDetailsMapper" + " Exiting");
		return mailbagDetailsVO;
	}
}
