package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class MailInboundAutoAttachAWBFlightsMapper implements Mapper<OperationalFlightVO> {

	private static final String FLTDAT = "FLTDAT";
	private static final String ARPCOD = "ARPCOD";
	private static final String STA = "STA";
	private static final String STD = "STD";
	private static final String POU = "POU";
	private static final String POL = "POL";
	private static final Log LOGGER = LogFactory.getLogger("MAIL OPERATIONS");

	@Override
	public OperationalFlightVO map(ResultSet rs) throws SQLException {
		LOGGER.entering(getClass().getSimpleName(), "map");
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode(rs.getString("CMPCOD"));
		operationalFlightVO.setCarrierId(rs.getInt("FLTCARIDR"));
		operationalFlightVO.setFlightNumber(rs.getString("FLTNUM"));
		operationalFlightVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		operationalFlightVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		operationalFlightVO.setCarrierCode(rs.getString("FLTCARCOD"));
		operationalFlightVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
		if (Objects.nonNull(rs.getDate(FLTDAT)) && Objects.nonNull(rs.getString(ARPCOD))) {
			operationalFlightVO.setFlightDate(new LocalDate(rs.getString(ARPCOD), Location.ARP, rs.getDate(FLTDAT)));
		}
		operationalFlightVO.setAirportCode(rs.getString(ARPCOD));
		operationalFlightVO.setPol(rs.getString(POL));
		operationalFlightVO.setPou(rs.getString(POU));
		if (Objects.nonNull(rs.getDate(STD)) && Objects.nonNull(rs.getString(POL))) {
			operationalFlightVO.setDepFromDate(new LocalDate(rs.getString(POL), Location.ARP, rs.getDate(STD)));
		}
		if (Objects.nonNull(rs.getDate(STA)) && Objects.nonNull(rs.getString(POU))) {
			operationalFlightVO.setDepFromDate(new LocalDate(rs.getString(POU), Location.ARP, rs.getDate(STA)));
		}
		LOGGER.exiting(getClass().getSimpleName(), "map");
		return operationalFlightVO;
	}

}
