/**
 *
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-5219
 *
 */
public class ForceMajeureRequestMapper implements Mapper<ForceMajeureRequestVO>{

	private static final String OPRSRC = "OPRSRC";

	/**
	 *
	 */
	public ForceMajeureRequestVO map(ResultSet  rs) throws SQLException {

		ForceMajeureRequestVO requestVO = new ForceMajeureRequestVO();
		if(rs.getString("FILPAR") != null){
			requestVO.setCompanyCode(rs.getString("CMPCOD"));
			requestVO.setForceMajuereID(rs.getString("FORMJRIDR"));
			if(rs.getDate("FRMDAT") != null){
				requestVO.setFromDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs
						.getDate("FRMDAT")));
			}
			if(rs.getDate("TOODAT") != null){
				requestVO.setToDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs
						.getDate("TOODAT")));
			}
			if(rs.getDate("REQDAT") != null){
				requestVO.setRequestDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs
						.getDate("REQDAT")));
			}
			requestVO.setFilterParameters(rs.getString("FILPAR"));
			requestVO.setSequenceNumber(rs.getLong("SEQNUM"));
			requestVO.setStatus(rs.getString("FORMJRSTA"));
			requestVO.setApprovalRemarks(rs.getString("APRRMK"));
			requestVO.setRequestRemarks(rs.getString("REQRMK"));
		}else{
			requestVO.setCompanyCode(rs.getString("CMPCOD"));
			requestVO.setMailID(rs.getString("MALIDR"));
			requestVO.setMailSeqNumber(rs.getLong("MALSEQNUM"));
			requestVO.setForceMajuereID(rs.getString("FORMJRIDR"));
			requestVO.setSequenceNumber(rs.getLong("SEQNUM"));
			requestVO.setStatus(rs.getString("FORMJRSTA"));
			requestVO.setApprovalRemarks(rs.getString("APRRMK"));
			requestVO.setRequestRemarks(rs.getString("REQRMK"));
			requestVO.setAirportCode(rs.getString("SCNPRT"));
			if(!("-1".equals(rs.getString("FLTNUM")))){
			requestVO.setFlightNumber(rs.getString("FLTNUM"));
			}else{
				requestVO.setFlightNumber("");	
			}
			requestVO.setCarrierID(rs.getInt("FLTCARIDR"));
			requestVO.setFlightSeqNum(rs.getInt("FLTSEQNUM"));
			requestVO.setCarrierCode(rs.getString("FLTCARCOD"));
			if(rs.getDate("FLTDAT") != null){
				requestVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs
						.getDate("FLTDAT")));
			}
			requestVO.setType(rs.getString("OPRTYP"));
			if(rs.getString(OPRSRC) != null){
				requestVO.setSource(rs.getString(OPRSRC));
			}
			requestVO.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT")));
			requestVO.setOriginAirport(rs.getString("ORGARPCOD"));
			requestVO.setDestinationAirport(rs.getString("DSTARPCOD"));        
			requestVO.setConsignmentDocNumber(rs.getString("CSGDOCNUM"));
			requestVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
			requestVO.setRecieveScan(rs.getString("RCVSCNFRCMJRFLG"));
			requestVO.setLoadScan(rs.getString("LODSCNFRCMJRFLG"));
			requestVO.setDeliveryScan(rs.getString("DLVSCNFRCMJRFLG"));
			requestVO.setLateDeliveryScan(rs.getString("LATDLVFRCMJRFLG"));
			requestVO.setAllScan(rs.getString("ALLSCNFRCMJRFLG"));
		}
		return requestVO;

	}

}
