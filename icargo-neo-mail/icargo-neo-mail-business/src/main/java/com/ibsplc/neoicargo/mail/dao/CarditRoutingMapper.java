package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ResditMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.TransportInformationVO;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;

/** 
 * @author A-3732
 */
@Slf4j
public class CarditRoutingMapper implements Mapper<TransportInformationVO> {
	private static final String COMP_CODE = "QF";

	public TransportInformationVO map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("TransportInformationMapper" + " : " + "map" + " Entering");
		TransportInformationVO transportInformationVO = new TransportInformationVO();
		transportInformationVO.setCarrierCode(rs.getString("CARCOD"));
		transportInformationVO.setCarrierID(rs.getInt("CARIDR"));
		transportInformationVO.setFlightNumber(rs.getString("FLTNUM"));
		transportInformationVO.setArrivalPlace(rs.getString("POU"));
		transportInformationVO.setDeparturePlace(rs.getString("POL"));
		if (transportInformationVO.getDeparturePlace() != null && rs.getTimestamp("DEPTIM") != null) {
			ZonedDateTime depDat = localDateUtil.getLocalDate(transportInformationVO.getDeparturePlace(),
					rs.getTimestamp("DEPTIM"));
			transportInformationVO.setDepartureTime(LocalDateMapper.toLocalDate(depDat));
			ZonedDateTime departureDate = localDateUtil.getLocalDate(transportInformationVO.getDeparturePlace(), false);
			departureDate = LocalDate.withDate(departureDate,
					transportInformationVO.getDepartureTime().toDisplayDateOnlyFormat());
			transportInformationVO.setDepartureDate(LocalDateMapper.toLocalDate(departureDate));
		}
		if (transportInformationVO.getArrivalPlace() != null && rs.getTimestamp("ARRDAT") != null) {
			ZonedDateTime arrDat = localDateUtil.getLocalDate(transportInformationVO.getArrivalPlace(),
					rs.getTimestamp("ARRDAT"));
			transportInformationVO.setArrivalTime(LocalDateMapper.toLocalDate(arrDat));
			ZonedDateTime arrivalDate = localDateUtil.getLocalDate(transportInformationVO.getArrivalPlace(), false);
			arrivalDate = LocalDate.withDate(arrivalDate,
					transportInformationVO.getArrivalTime().toDisplayDateOnlyFormat());
			transportInformationVO.setArrivalDate(LocalDateMapper.toLocalDate(arrivalDate));
		}
		transportInformationVO.setConveyanceReference(rs.getString("CARCOD").concat(rs.getString("FLTNUM")));
		transportInformationVO.setModeOfTransport(ResditMessageVO.AIR_TRANSPORT);
		if (COMP_CODE.equals(rs.getString("CARCOD"))) {
			transportInformationVO.setTransportStageQualifier(ResditMessageVO.MAIN_CARRIAGE_TRT);
		} else {
			transportInformationVO.setTransportStageQualifier(ResditMessageVO.ON_CARRIAGE_TRT);
		}
		log.debug("TransportInformationMapper" + " : " + "map" + " Exiting");
		return transportInformationVO;
	}
}
