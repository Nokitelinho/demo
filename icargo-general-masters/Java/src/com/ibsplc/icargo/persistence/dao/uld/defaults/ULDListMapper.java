/*
 * ULDListMapper.java Created on Jan 23, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/** 
 * 
 * @author A-1936
 *
 */
public class ULDListMapper implements Mapper<ULDListVO> {
	 private Log log = LogFactory.getLogger("ULD_DEFAULTS");
	 private static final String FACILITY_TYPE="FAC";
	 private static final String COUNTRY_CODE="CNT";
	 private static final String REGION="REG";
	 private static final String AIRPORT="ARP";
   /**
    * @param rs
    * @return ULDListVO
    * @throws SQLException
    */
	public ULDListVO map(ResultSet rs) throws SQLException{
		log.entering("INSIDE THE MAPPER","map(ResultSet rs)");
		
		  
		ULDListVO uldListVO =new ULDListVO();
	    uldListVO.setCompanyCode(rs.getString("CMPCOD")); 
		uldListVO.setUldNumber(rs.getString("ULDNUM"));
		uldListVO.setUldGroupCode(rs.getString("ULDGRPCOD"));
		uldListVO.setUldTypeCode(rs.getString("ULDTYP"));
		uldListVO.setPartyBorrowed(rs.getString("BORROWEDFROM")); 
		uldListVO.setPartyLoaned(rs.getString("LOANEDTO"));
		uldListVO.setLocation(rs.getString("LOC"));
		uldListVO.setOwnerStation(rs.getString("OWNARP"));
		uldListVO.setCurrentStation(rs.getString("CURARP"));
		if(rs.getTimestamp("LSTMVTDAT")!=null && uldListVO.getCurrentStation() != null){
			LocalDate localDate = new LocalDate(uldListVO.getCurrentStation() , 
					Location.ARP , rs.getTimestamp("LSTMVTDAT"));
			uldListVO.setLastMovementDate(new LocalDate(localDate,true));
		}else if(rs.getTimestamp("LSTMVTDAT")!=null &&
				uldListVO.getCurrentStation() == null){
			LocalDate localDate = new LocalDate(LocalDate.NO_STATION , 
					Location.NONE , rs.getTimestamp("LSTMVTDAT"));
			uldListVO.setLastMovementDate(
					new LocalDate(localDate,true));
		}
		log.log(Log.FINE, "uldListVO.getLastMovementDate()", uldListVO.getLastMovementDate());
		uldListVO.setManufacturer(rs.getString("MFR"));
		uldListVO.setOverallStatus(rs.getString("OVLSTA"));
		uldListVO.setDamageStatus(rs.getString("DMGSTA"));
		uldListVO.setFacilityType(rs.getString("FACTYP"));
		uldListVO.setTransitStatus(rs.getString("TRNSTA"));
		uldListVO.setUldNature(rs.getString("ULDNAT")); 
		uldListVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
		/*uldListVO.setLastUpdateTime(new LocalDate(uldListVO.getCurrentStation() , 
				Location.ARP , rs.getTimestamp("LSTUPDTIM")));*/
		uldListVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION , 
				Location.NONE , rs.getTimestamp("LSTUPDTIM")));
		uldListVO.setOperatingAirline(rs.getString("OPRARLCOD"));
		if(rs.getString("REGCOD")!=null){
			uldListVO.setRegionCode(rs.getString("REGCOD"));
		}
		if(rs.getString("CNTCOD")!=null){
			uldListVO.setCountryCode(rs.getString("CNTCOD"));
		}
		if(rs.getString("CNT")!=null){
			uldListVO.setContent(rs.getString("CNT"));
		}
		if(rs.getString("FACCOD")!=null){
			uldListVO.setFacilityCode(rs.getString("FACCOD"));
		}
		if("Y".equalsIgnoreCase(rs.getString("OFFSTA"))){
			//uldListVO.setOffAirport(true);
			uldListVO.setOffAirport("Y");
		}else{
			//uldListVO.setOffAirport(false);
			uldListVO.setOffAirport("N");
		}
		//boolean isoffAirport = ULDListVO.FLAG_YES.equals(rs.getString("OFFSTA"));
		
		//Added By A-6841 for CRQ ICRD-155382
		if("Y".equalsIgnoreCase(rs.getString("ULDOCPSTA"))){
			uldListVO.setOccupied("Y");
		}else{
			uldListVO.setOccupied("N");
		}
		uldListVO.setFlightInfo(rs.getString("FLTDTL"));
		//added as part of ICRD-232684 by A-4393 starts
		if(rs.getString("DAYREM")!=null){
		uldListVO.setRemainingDayToReturn(rs.getString("DAYREM"));
		}
		//added as part of ICRD-232684 by A-4393 ends 
		log.log(Log.INFO, "THe ULDListVO details are", uldListVO);
		log.exiting("ULDLISTMAPPER","ULDLISTMAPPER");
		return uldListVO;
	}
}
