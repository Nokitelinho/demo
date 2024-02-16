/*
 * PartnerCarrierMapper.java Created on August 11, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.ibsplc.icargo.business.mail.operations.vo.PartnerCarrierVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
/**
 * 
 * @author A-1876
 *
 */
public class PartnerCarrierMapper implements Mapper<PartnerCarrierVO>{
	 /**
     * @param rs
     * @return 
     * @throws SQLException
     */
   public PartnerCarrierVO map(ResultSet rs) throws SQLException {
	   PartnerCarrierVO partnerCarrierVO=new PartnerCarrierVO();
	   partnerCarrierVO.setCompanyCode(rs.getString("CMPCOD"));
	   partnerCarrierVO.setOwnCarrierCode(rs.getString("OWNCARCOD"));
	   partnerCarrierVO.setAirportCode(rs.getString("ARPCOD"));
	   partnerCarrierVO.setPartnerCarrierCode(rs.getString("PRTCARCOD"));
	   partnerCarrierVO.setPartnerCarrierId(rs.getString("PRTCARIDR"));
	   partnerCarrierVO.setPartnerCarrierName(rs.getString("PRTCARNAM"));
	   Timestamp lstUpdTime = rs.getTimestamp("LSTUPDTIM");
	     if(lstUpdTime != null) {
	    	 partnerCarrierVO.setLastUpdateTime(
	    		 new LocalDate(LocalDate.NO_STATION, Location.NONE,lstUpdTime));
	     }
	   partnerCarrierVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
	   partnerCarrierVO.setMldTfdReq(rs.getString("MLDTFDREQ"));
	   return partnerCarrierVO;
	   
   }
}
