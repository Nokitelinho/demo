package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.mail.vo.CarditTransportationVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

/** 
 * @author A-1739
 */
@Slf4j
public class CarditTransportationMapper implements Mapper<CarditTransportationVO> {
	/** 
	* TODO Purpose Feb 6, 2007, A-1739
	* @param rs
	* @return
	* @throws SQLException
	* @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	*/
	public CarditTransportationVO map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("CarditTransportationMapper" + " : " + "map" + " Entering");
		CarditTransportationVO transportationVO = new CarditTransportationVO();
		transportationVO.setDeparturePort(rs.getString("ORGCOD"));
		transportationVO.setArrivalPort(rs.getString("DSTCOD"));
		if (rs.getTimestamp("DEPTIM") != null) {
			transportationVO.setDepartureTime(
					localDateUtil.getLocalDate(transportationVO.getDeparturePort(), rs.getTimestamp("DEPTIM")));
		}
		transportationVO.setCarrierID(rs.getInt("CARIDR"));
		transportationVO.setFlightNumber(rs.getString("FLTNUM"));
		transportationVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		transportationVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		transportationVO.setSegmentSerialNum(rs.getInt("SEGSERNUM"));
		transportationVO.setModeOfTransport(rs.getString("TRTMOD"));
		transportationVO.setTransportStageQualifier(rs.getString("TRTSTGQLF"));
		transportationVO.setTransportSerialNum(rs.getInt("TRTSERNUM"));
		log.debug("CarditTransportationMapper" + " : " + "map" + " Exiting");
		return transportationVO;
	}
}
