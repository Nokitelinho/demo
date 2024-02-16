package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.OperationalFlightVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

@Slf4j
public class MailInboundAutoAttachAWBFlightsMapper implements Mapper<OperationalFlightVO> {
	private static final String FLTDAT = "FLTDAT";
	private static final String ARPCOD = "ARPCOD";
	private static final String STA = "STA";
	private static final String STD = "STD";
	private static final String POU = "POU";
	private static final String POL = "POL";

	@Override
	public OperationalFlightVO map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug(getClass().getSimpleName() + " : " + "map" + " Entering");
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode(rs.getString("CMPCOD"));
		operationalFlightVO.setCarrierId(rs.getInt("FLTCARIDR"));
		operationalFlightVO.setFlightNumber(rs.getString("FLTNUM"));
		operationalFlightVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		operationalFlightVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		operationalFlightVO.setCarrierCode(rs.getString("FLTCARCOD"));
		operationalFlightVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
		if (Objects.nonNull(rs.getDate(FLTDAT)) && Objects.nonNull(rs.getString(ARPCOD))) {
			operationalFlightVO.setFlightDate(localDateUtil.getLocalDate(rs.getString(ARPCOD), rs.getDate(FLTDAT)));
		}
		operationalFlightVO.setAirportCode(rs.getString(ARPCOD));
		operationalFlightVO.setPol(rs.getString(POL));
		operationalFlightVO.setPou(rs.getString(POU));
		if (Objects.nonNull(rs.getDate(STD)) && Objects.nonNull(rs.getString(POL))) {
			operationalFlightVO.setDepFromDate(localDateUtil.getLocalDate(rs.getString(POL), rs.getDate(STD)));
		}
		if (Objects.nonNull(rs.getDate(STA)) && Objects.nonNull(rs.getString(POU))) {
			operationalFlightVO.setDepFromDate(localDateUtil.getLocalDate(rs.getString(POU), rs.getDate(STA)));
		}
		log.debug(getClass().getSimpleName() + " : " + "map" + " Exiting");
		return operationalFlightVO;
	}
}
