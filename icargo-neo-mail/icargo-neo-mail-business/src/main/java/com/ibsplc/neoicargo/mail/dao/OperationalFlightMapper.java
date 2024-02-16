package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.mail.vo.OperationalFlightVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

/** 
 * Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.OperationalFlightMapper.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-4809	:	Sep 3, 2016	:	Draft
 */
@Slf4j
public class OperationalFlightMapper implements Mapper<OperationalFlightVO> {
	/** 
	* Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet) Added by 			: A-4809 on Sep 3, 2016 Used for 	: Parameters	:	@param arg0 Parameters	:	@return Parameters	:	@throws SQLException 
	*/
	@Override
	public OperationalFlightVO map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("OperationalFlightMapper ---->Entering");
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode(rs.getString("CMPCOD"));
		operationalFlightVO.setCarrierCode(rs.getString("FLTCARCOD"));
		operationalFlightVO.setCarrierId(rs.getInt("FLTCARIDR"));
		operationalFlightVO.setFlightNumber(rs.getString("FLTNUM"));
		operationalFlightVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
		operationalFlightVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		operationalFlightVO.setPol(rs.getString("POL"));
		operationalFlightVO.setPou(rs.getString("POU"));
		operationalFlightVO.setAirportCode(rs.getString("POU"));
		if (rs.getDate("FLTDAT") != null) {
			operationalFlightVO.setFlightDate(localDateUtil.getLocalDate(null, rs.getDate("FLTDAT")));
		}
		if (rs.getDate("ARRIVALTIME") != null) {
			operationalFlightVO.setActualArrivalTime(localDateUtil.getLocalDate(null, rs.getTimestamp("ARRIVALTIME")));
		}
		operationalFlightVO.setLegDestination(rs.getString("LEGDST"));
		log.info("" + "OperationalFlightVO -->" + " " + operationalFlightVO);
		return operationalFlightVO;
	}
}
