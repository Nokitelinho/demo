package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.mail.vo.OperationalFlightVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

/** 
 * Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.FlightsForClosureMapper.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-4809	:	Sep 3, 2016	:	Draft
 */
public class FlightsForClosureMapper implements Mapper<OperationalFlightVO> {
	/** 
	* Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet) Added by 			: A-4809 on Sep 3, 2016 Used for 	: Parameters	:	@param arg0 Parameters	:	@return Parameters	:	@throws SQLException 
	*/
	@Override
	public OperationalFlightVO map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode(rs.getString("CMPCOD"));
		if (rs.getString("FLTCARCOD") != null) {
			operationalFlightVO.setCarrierCode(rs.getString("FLTCARCOD"));
		}
		operationalFlightVO.setCarrierId(rs.getInt("FLTCARIDR"));
		operationalFlightVO.setFlightNumber(rs.getString("FLTNUM"));
		operationalFlightVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		operationalFlightVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		operationalFlightVO.setPol(rs.getString("LEGORG"));
		operationalFlightVO.setPou(rs.getString("LEGDST"));
		operationalFlightVO.setAirportCode(rs.getString("ARPCOD"));
		operationalFlightVO.setScanned(true);
		if (operationalFlightVO.getPol() != null && operationalFlightVO.getPou() != null) {
			operationalFlightVO
					.setFlightDate(localDateUtil.getLocalDate(operationalFlightVO.getPol(), rs.getDate("FLTDAT")));
			operationalFlightVO.setFlightRoute(rs.getString("FLTROU"));
			operationalFlightVO
					.setArrToDate(localDateUtil.getLocalDate(operationalFlightVO.getPou(), rs.getDate("STA")));
		} else {
			operationalFlightVO.setFlightDate(localDateUtil.getLocalDate(null, rs.getDate("FLTDAT")));
			operationalFlightVO.setArrToDate(localDateUtil.getLocalDate(null, rs.getDate("STA")));
		}
		operationalFlightVO.setFlightType(rs.getString("FLTTYP"));
		operationalFlightVO.setFltOwner(rs.getString("FLTOWN"));
		return operationalFlightVO;
	}
}
