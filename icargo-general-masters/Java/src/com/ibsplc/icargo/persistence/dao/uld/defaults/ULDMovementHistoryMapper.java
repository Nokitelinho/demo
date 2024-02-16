/*
 * ULDMovementHistoryMapper.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;


import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementDetailVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1940
 *
 */

/**
 * This class implements a Mapper<ULDMovementDetailVO>
 * @author A-1940
 *
 */
public class ULDMovementHistoryMapper implements Mapper<ULDMovementDetailVO> {

	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	private static final String YES = "Y";
    /**
     *
     * @param rs
     * @return
     * @throws SQLException
     */
	public ULDMovementDetailVO map(ResultSet rs) 
	throws SQLException {
	   log.entering("ULDMovementHistoryMapper","map");
	   ULDMovementDetailVO uldMovementDetailVO = new
	   		ULDMovementDetailVO();
	   uldMovementDetailVO.setCompanyCode(rs.getString("CMPCOD"));
	   uldMovementDetailVO.setCarrierCode(rs.getString("FLTCOD"));
	   uldMovementDetailVO.setFlightCarrierIdentifier(rs.getInt("FLTCARIDR"));
	   uldMovementDetailVO.setFlightNumber(rs.getString("FLTNUM"));
	   uldMovementDetailVO.setPointOfLading(rs.getString("POL"));
	   uldMovementDetailVO.setPointOfUnLading(rs.getString("POU"));
	   uldMovementDetailVO.setRemark(rs.getString("RMK"));
	   uldMovementDetailVO.setMovementSequenceNumber(rs.getLong("MVTSEQNUM"));
	   uldMovementDetailVO.setMovementSerialNumber(rs.getLong("MVTSERNUM"));
	   //added by a-3045 for CR AirNZ267 starts
	   uldMovementDetailVO.setAgentCode(rs.getString("AGTCOD"));
	   uldMovementDetailVO.setAgentName(rs.getString("AGTNAM"));
	   //added by a-3045 for CR AirNZ267 ends
	   LocalDate date = null;
	  /* if(uldMovementDetailVO.getPointOfUnLading() != null){
		   date = new LocalDate(uldMovementDetailVO.getPointOfUnLading(),
				   												Location.ARP,rs.getTimestamp("LSTMVTDAT"));   
	   }else if(uldMovementDetailVO.getPointOfUnLading() == null){
		   date = new LocalDate(LocalDate.NO_STATION,
						Location.NONE,rs.getTimestamp("LSTMVTDAT"));   
	   }*/
	   //added by a-3045 for bug 29933 starts
	   date = new LocalDate(LocalDate.NO_STATION,
				Location.NONE,rs.getTimestamp("LSTMVTDAT")); 
	   log.log(Log.FINE, "Date at ULDMovementDetailVO", date);
	if(date != null){
		   uldMovementDetailVO.setLastUpdatedTime(date);
	   }
	   uldMovementDetailVO.setFlightDate(
			   (rs.getDate("FLTDAT")==null?null:new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("FLTDAT"))));
	   //Modified by A-8368 as part of CR - IASCB-29023
	   //setting the flight ATA from FLTATA column
	   uldMovementDetailVO.setFlightATA(
	   (rs.getDate("FLTATA")==null?null:new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("FLTATA"))));
	   uldMovementDetailVO.setContent(rs.getString("CNT"));
	   if(YES.equals(rs.getString("DUMMVTFLG"))){
		   uldMovementDetailVO.setIsDummyMovement(true);
	   }else{
		   uldMovementDetailVO.setIsDummyMovement(false);
	   }
	   log.log(Log.FINE, "uldMovementDetailVO", uldMovementDetailVO);
	log.exiting("ULDMovementHistoryMapper","map");
		return uldMovementDetailVO;
	}

}
