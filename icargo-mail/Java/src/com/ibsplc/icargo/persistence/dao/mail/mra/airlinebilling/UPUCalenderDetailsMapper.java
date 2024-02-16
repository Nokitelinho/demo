/*

 * UPUCalenderDetailsMapper.java Created on Sep 25, 2006

 *

 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.

 *

 * This software is the proprietary information of IBS Software Services (P) Ltd.

 * Use is subject to license terms.

 */

package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;



import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.UPUCalendarVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2521
 * 
 * Mapper for getting UPU details
 */

public class UPUCalenderDetailsMapper implements Mapper<UPUCalendarVO> {

	private Log log = LogFactory.getLogger("UPUCalenderDetailsMapper");

	/**
	 * @param rs
	 * @throws SQLException
	 */

	public UPUCalendarVO map(ResultSet rs) throws SQLException {
		
		log.entering("UPUCalenderDetailsMapper---------", "Map Method");
		
		String statusFlag = "N";
		
		UPUCalendarVO upuCalendarVO 	= new UPUCalendarVO();
		
		upuCalendarVO.setClearingHouse( rs.getString("CLRHUS") );
		
		upuCalendarVO.setCompanyCode( rs.getString("CMPCOD") );
		
		upuCalendarVO.setBillingPeriod( rs.getString("CLRPRD" ));
		
		if(rs.getDate("FRMDAT") == null) {
			upuCalendarVO.setFromDate(null);
			
		}else{
			upuCalendarVO.setFromDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, rs.getDate("FRMDAT")));
		}	
		
		if(rs.getDate("TOODAT") == null) {
			upuCalendarVO.setToDate(null);
			
		}else{
			upuCalendarVO.setToDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, rs.getDate("TOODAT")));
		}	
		
		if(rs.getDate("SUBDAT") == null) {
			upuCalendarVO.setSubmissionDate(null);
			
		}else{
			upuCalendarVO.setSubmissionDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, rs.getDate("SUBDAT")));
		}	
		
		upuCalendarVO.setGenerateAfterToDate( rs.getInt("GENAFT") );
		
		upuCalendarVO.setOperationalFlag( statusFlag );
		
		upuCalendarVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
		
		log.log(Log.FINE, "lstupdtim ", rs.getTimestamp("LSTUPDTIM"));
		if(rs.getTimestamp("LSTUPDTIM")!=null){
			upuCalendarVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("LSTUPDTIM")));
        }
		log.exiting("UPUCalenderDetailsMapper", "Map Method");
		
		return upuCalendarVO;
	}

}

