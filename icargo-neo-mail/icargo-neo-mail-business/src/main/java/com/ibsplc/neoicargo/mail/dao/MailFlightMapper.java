package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MailFlightMapper implements Mapper<FlightValidationVO> {
		@Override
		public FlightValidationVO map(ResultSet rs) throws SQLException {
			LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
			FlightValidationVO flightValidationVO = new FlightValidationVO();
			flightValidationVO.setCompanyCode(rs.getString("CMPCOD"));
			flightValidationVO.setFlightNumber(rs.getString("FLTNUM"));
			flightValidationVO.setAirportCode(rs.getString("ARPCOD"));
			java.sql.Date flightDate = rs.getDate("FLTDAT");
            flightValidationVO.setFlightDate(new  com.ibsplc.icargo.framework.util.time.LocalDate(flightValidationVO.getAirportCode(), Location.ARP,rs.getDate("FLTDAT")));
			flightValidationVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
			flightValidationVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
			flightValidationVO.setFlightCarrierId(rs.getInt("FLTCARIDR"));
			flightValidationVO.setCarrierCode(rs.getString("FLTCARCOD"));
			return flightValidationVO;

		}
	}