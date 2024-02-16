/**
 * 
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ResditMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.TransportInformationVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3732
 *
 */
public class CarditRoutingMapper implements
	Mapper<TransportInformationVO> {
	
	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	
	private static final String COMP_CODE = "QF";
	
	public TransportInformationVO map(ResultSet rs) throws SQLException {
		log.entering("TransportInformationMapper", "map");
		TransportInformationVO transportInformationVO = 
			new TransportInformationVO();
		
		
		transportInformationVO.setCarrierCode(rs.getString("CARCOD"));
		transportInformationVO.setCarrierID(rs.getInt("CARIDR"));
		transportInformationVO.setFlightNumber(rs.getString("FLTNUM"));
		
		transportInformationVO.setArrivalPlace(rs.getString("POU"));
		transportInformationVO.setDeparturePlace(rs.getString("POL"));
		
		if(transportInformationVO.getDeparturePlace()!=null && rs.getTimestamp("DEPTIM")!=null ){
			LocalDate depDat = new LocalDate(transportInformationVO.getDeparturePlace(),	Location.ARP, rs.getTimestamp("DEPTIM"));
			transportInformationVO.setDepartureTime(depDat);
			LocalDate departureDate = new LocalDate(transportInformationVO.getDeparturePlace(),	Location.ARP, false);
			departureDate.setDate(transportInformationVO.getDepartureTime().toDisplayDateOnlyFormat());
			transportInformationVO.setDepartureDate(departureDate);
		}
		
		
		if(transportInformationVO.getArrivalPlace()!=null && rs.getTimestamp("ARRDAT")!=null ){
			LocalDate arrDat = new LocalDate(transportInformationVO.getArrivalPlace(),	Location.ARP, rs.getTimestamp("ARRDAT"));			
			transportInformationVO.setArrivalTime(arrDat);		
			LocalDate arrivalDate = new LocalDate(transportInformationVO.getArrivalPlace(),	Location.ARP, false);
			arrivalDate.setDate(transportInformationVO.getArrivalTime().toDisplayDateOnlyFormat());
			transportInformationVO.setArrivalDate(arrivalDate);
		}
		
		
		transportInformationVO.setConveyanceReference(
				rs.getString("CARCOD").concat(rs.getString("FLTNUM")));

		

		transportInformationVO.setModeOfTransport(
				ResditMessageVO.AIR_TRANSPORT);
		if(COMP_CODE.equals(rs.getString("CARCOD"))){
			transportInformationVO.setTransportStageQualifier(
					ResditMessageVO.MAIN_CARRIAGE_TRT);
		}else{
			transportInformationVO.setTransportStageQualifier(
					ResditMessageVO.ON_CARRIAGE_TRT);
		}
		
		log.exiting("TransportInformationMapper", "map");
		return transportInformationVO;
	}
	

}
