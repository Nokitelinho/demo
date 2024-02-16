package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ResditMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.TransportInformationVO;
import lombok.extern.slf4j.Slf4j;

/** 
 * @author A-1739
 */
@Slf4j
public class TransportInformationMapper implements Mapper<TransportInformationVO> {
	/** 
	* TODO Purpose Sep 12, 2006, a-1739
	* @param rs
	* @return
	* @throws SQLException
	* @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	*/
	public TransportInformationVO map(ResultSet rs) throws SQLException {
		log.debug("TransportInformationMapper" + " : " + "map" + " Entering");
		TransportInformationVO transportInformationVO = new TransportInformationVO();
		transportInformationVO.setDeparturePlace(rs.getString("EVTPRT"));
		transportInformationVO.setDepartureLocationQualifier(ResditMessageVO.PLACE_OF_DEPARTURE);
		transportInformationVO.setConveyanceReference(rs.getString("FLIGHTID"));
		transportInformationVO.setCarrierName(rs.getString("ARLNAM"));
		log.debug("TransportInformationMapper" + " : " + "map" + " Exiting");
		return transportInformationVO;
	}
}
