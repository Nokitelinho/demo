/*
 * ULDDamageListMapper.java Created on Mar 20, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageDetailsListVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @author A-1347
 *
 */


public class ULDDamageListMapper implements Mapper<ULDDamageDetailsListVO>{

    private Log log = LogFactory.getLogger("ULD");
	/**
     *
     * @param rs
     * @return List<ULDDamageVO>
     * @throws SQLException
     */
	public ULDDamageDetailsListVO map(ResultSet rs) throws SQLException {
	  
	   log.entering("ULDDamageListMultiMapper","map");
	   ULDDamageDetailsListVO listVO = new ULDDamageDetailsListVO();
	   listVO.setCompanyCode(rs.getString("CMPCOD"));
	   listVO.setUldNumber(rs.getString("ULDNUM"));
	   listVO.setDamageReferenceNumber(rs.getLong("DMGREFNUM"));
	   listVO.setReportedStation(rs.getString("RPTARP"));
	   listVO.setCurrentStation(rs.getString("CURARP"));
	   listVO.setDamageStatus(rs.getString("DMGSTA"));
	   listVO.setOverallStatus(rs.getString("OVLSTA"));
	   listVO.setRemarks(rs.getString("RMK"));
	   listVO.setSequenceNumber(rs.getLong("DMGSEQNUM"));
	   listVO.setImageCount(rs.getString("IMGCNT"));
	   Date datOne = rs.getDate("DMGRPTDAT");
	   if(datOne != null && listVO.getReportedStation() == null){
		  listVO.setReportedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,datOne));
	   }else if(datOne != null && listVO.getReportedStation() != null){
		   listVO.setReportedDate(new LocalDate(listVO.getReportedStation(),
				   													Location.ARP,datOne));
	   }
	   Date datTwo = rs.getDate("CLSDAT");
	   if(datTwo != null && listVO.getReportedStation() == null){
	 	  listVO.setRepairDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,datTwo));
	   }else if(datTwo != null && listVO.getReportedStation() != null){
		   listVO.setRepairDate(new LocalDate(listVO.getReportedStation(),Location.ARP,datTwo));
	   }
	   if("Y".equalsIgnoreCase(rs.getString("PICFLG"))){
		   listVO.setPicurePresent(true);
	   }else{
		   listVO.setPicurePresent(false);
	   }
	   listVO.setParty(rs.getString("PTYCOD"));
	   listVO.setPartyType(rs.getString("PTYTYP"));
	   listVO.setLocation(rs.getString("LOC"));
	   //added by a-3045 for bug 20387 starts
	   listVO.setSection(rs.getString("DMGSEC"));
	   listVO.setDamageDescription(rs.getString("DMGDES"));
	   //added by a-3045 for bug 20387 starts
	   return listVO;
	 }
}
