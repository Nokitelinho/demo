package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.mail.vo.OperationalFlightVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

/** 
 * Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.FlightArrivalMapper.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-4809	:	Sep 3, 2016	:	Draft
 */
public class FlightArrivalMapper implements Mapper<OperationalFlightVO> {
	/** 
	* Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet) Added by 			: A-4809 on Sep 3, 2016 Used for 	: Parameters	:	@param arg0 Parameters	:	@return Parameters	:	@throws SQLException 
	*/
	@Override
	public OperationalFlightVO map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		OperationalFlightVO flightVO = new OperationalFlightVO();
		flightVO.setCompanyCode(rs.getString("CMPCOD"));
		flightVO.setFlightNumber(rs.getString("FLTNUM"));
		flightVO.setCarrierId(rs.getInt("FLTCARIDR"));
		flightVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		flightVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		flightVO.setPol(rs.getString("LEGORG"));
		flightVO.setPou(rs.getString("POU"));
		flightVO.setAirportCode(rs.getString("POU"));
		flightVO.setFlightDate(localDateUtil.getLocalDate(null, rs.getDate("FLTDAT")));
		flightVO.setSegSerNum(rs.getInt("SEGSERNUM"));
		return flightVO;
	}
}
