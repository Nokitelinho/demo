/**
 * 
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5125
 *
 */
public class ULDFltMsgReconsileDtlsMapper implements Mapper<ULDFlightMessageReconcileDetailsVO> {
	private Log log = LogFactory.getLogger("ULD_DEFAULTS");
	
public ULDFlightMessageReconcileDetailsVO map(ResultSet rs) throws SQLException{
    	
    	log.entering("ListULDFltMsgReconsileDtlsMapper","List");
    	
    	ULDFlightMessageReconcileDetailsVO uldFltMsgRecDtlsVO = new ULDFlightMessageReconcileDetailsVO();    	
    	uldFltMsgRecDtlsVO.setFlightCarrierIdentifier((rs.getInt("FLTCARIDR")));
    	uldFltMsgRecDtlsVO.setAirportCode(rs.getString("ARPCOD"));
    	uldFltMsgRecDtlsVO.setCompanyCode(rs.getString("CMPCOD"));
    	uldFltMsgRecDtlsVO.setMessageType(rs.getString("MSGTYP"));
    	uldFltMsgRecDtlsVO.setUldNumber(rs.getString("ULDNUM"));
    	uldFltMsgRecDtlsVO.setPou(rs.getString("POU"));
    	uldFltMsgRecDtlsVO.setFlightNumber(rs.getString("FLTNUM"));
    	uldFltMsgRecDtlsVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
    	uldFltMsgRecDtlsVO.setSequenceNumber(rs.getString("SEQNUM"));
    	uldFltMsgRecDtlsVO.setUldFlightStatus(rs.getString("ULDFLTSTA"));
    	uldFltMsgRecDtlsVO.setContent(rs.getString("CNT"));
    	log.log(Log.INFO, "!!!!!ULDFlightMessageReconcileDetailsVO",
				uldFltMsgRecDtlsVO);
		return uldFltMsgRecDtlsVO;
    }
}


