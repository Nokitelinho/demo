/*
 * UCMFlightDetailsMultiMapper.java Created on jul 13, 2006
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 */

package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDInFlightVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
/**
 * 
 * @author A-2048
 *
 */
public class UCMFlightDetailsMultiMapper implements MultiMapper<FlightDetailsVO>{
	/**
	 * @param rs 
	 * @return 
	 * @throws SQLException 
	 * 
	 */
	public List<FlightDetailsVO> map(ResultSet rs)throws SQLException{
	  
		List<FlightDetailsVO> flightDetailsVOs = new ArrayList<FlightDetailsVO>();
		FlightDetailsVO flightDetailsVO = null;
		
		Collection<ULDInFlightVO> uldInFlightVOs = null;
		ULDInFlightVO uldInFlightVO =null;
		
		String 	presId = null;
    	String prevId = null;
    	
    	String childPresId = null;
    	String childPrevId = null;
		
		while(rs.next()) {
			presId =new StringBuffer(rs.getString("CMPCOD"))
						            .append(rs.getInt("FLTCARIDR"))
						            .append(rs.getString("FLTNUM"))
						            .append(rs.getInt("FLTSEQNUM"))
						            .append(rs.getInt("LEGSERNUM"))
						            .append(rs.getString("ARPCOD"))
						            .toString();
			if(!presId.equals(prevId)){
				flightDetailsVO = new FlightDetailsVO();
				
				flightDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
				flightDetailsVO.setFlightCarrierIdentifier(rs.getInt("FLTCARIDR"));
				flightDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
				flightDetailsVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
				flightDetailsVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
			//	flightDetailsVO.get
				if(rs.getDate("FLTDAT") != null) {
					flightDetailsVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("FLTDAT")));
				}
				
				flightDetailsVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
				
				uldInFlightVOs = new HashSet<ULDInFlightVO>();
				prevId = presId ;
			}
			childPresId = new StringBuffer(rs.getString("CMPCOD"))
            .append(rs.getInt("FLTCARIDR"))
            .append(rs.getString("FLTNUM"))
            .append(rs.getInt("FLTSEQNUM"))
            .append(rs.getInt("LEGSERNUM"))
            .append(rs.getString("ARPCOD"))
            .append(rs.getString("ULDNUM"))
            .toString();
			if(!childPresId.equalsIgnoreCase(childPrevId)	) {
				if(uldInFlightVO != null) {
					uldInFlightVOs.add(uldInFlightVO);
				}
				uldInFlightVO = new ULDInFlightVO();
				
				uldInFlightVO.setContent(rs.getString("CNT"));
				uldInFlightVO.setPointOfLading(rs.getString("ARPCOD"));
				uldInFlightVO.setPointOfUnLading(rs.getString("POU"));
				uldInFlightVO.setUldNumber(rs.getString("ULDNUM"));
				
				childPrevId = childPresId;
			}
			
		}
		if(flightDetailsVO != null) {
			if(uldInFlightVO != null) {
				uldInFlightVOs.add(uldInFlightVO);
			}
			if(uldInFlightVOs != null && uldInFlightVOs.size() >0) {
				flightDetailsVO.setUldInFlightVOs(uldInFlightVOs);
			}
			flightDetailsVOs.add(flightDetailsVO);
		}
		
		return flightDetailsVOs;
	}
}