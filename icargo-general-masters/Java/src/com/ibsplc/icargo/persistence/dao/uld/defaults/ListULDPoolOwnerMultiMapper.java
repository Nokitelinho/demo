/*
 * ListULDPoolOwnerMultiMapper.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolSegmentExceptionsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2048
 *
 */
public class ListULDPoolOwnerMultiMapper  implements MultiMapper<ULDPoolOwnerVO>{
	
	private Log log = LogFactory.getLogger("ULD MANAGEMENT");
	/**
	 * @param resultSet
	 * @return ULDPoolOwnerVO
	 * @throws SQLException
	 */
	public List<ULDPoolOwnerVO> map(ResultSet resultSet) 
	throws SQLException {
		log.entering("ListULDPoolOwnerMultiMapper","Map");
		String previousKey = "";
		String currentKey = "";
		String currrentChildKey ="";
		String previousChildkey="";
		List<ULDPoolOwnerVO> uLDPoolOwnerVOs=new ArrayList <ULDPoolOwnerVO>();
		ULDPoolOwnerVO uLDPoolOwnerVO =null;
		Collection <ULDPoolSegmentExceptionsVO> segmentExceptionsVO = null;
		ULDPoolSegmentExceptionsVO 	uLDPoolSegmentExceptionsVO =null;
		
		while(resultSet.next()){
			
			currentKey = new StringBuilder(resultSet.getString("CMPCOD"))
            .append(resultSet.getString("AIRLINEONECOD"))
            .append(resultSet.getString("AIRLINETWOCOD"))
            .append(resultSet.getString("SERNUM"))
            .toString();
			log.log(Log.FINE, "currentKey------->>>>", currentKey);
			boolean flag=false;
			if(!currentKey.equals(previousKey)){
				flag=true;
			uLDPoolOwnerVO=new ULDPoolOwnerVO() ;
			uLDPoolOwnerVO.setCompanyCode(resultSet.getString("CMPCOD"));
			uLDPoolOwnerVO.setAirlineOne(resultSet.getString("AIRLINEONECOD"));
			uLDPoolOwnerVO.setAirlineTwo(resultSet.getString("AIRLINETWOCOD"));
			uLDPoolOwnerVO.setAirport(resultSet.getString("ARPCOD"));
			uLDPoolOwnerVO.setAirlineIdentifierOne(resultSet.getInt("ARLONE"));
			uLDPoolOwnerVO.setAirlineIdentifierTwo(resultSet.getInt("ARLTWO"));
			uLDPoolOwnerVO.setLastUpdatedUser(resultSet.getString("LSTUPDUSR"));
			uLDPoolOwnerVO.setSerialNumber(resultSet.getLong("SERNUM"));
						
			uLDPoolOwnerVO.setLastUpdatedTime(
					new LocalDate(
							LocalDate.NO_STATION,Location.NONE,resultSet.getTimestamp("LSTUPDTIM")));
			uLDPoolOwnerVO.setRemarks(resultSet.getString("RMK"));	
			segmentExceptionsVO = new ArrayList<ULDPoolSegmentExceptionsVO>();
			previousKey = currentKey;
			}
			else{
				flag=false;
			
				currrentChildKey = new StringBuilder(resultSet.getString("CMPCOD"))
	            									.append(resultSet.getString("AIRLINEONECOD"))
	            									.append(resultSet.getString("AIRLINETWOCOD"))
	            									.append(resultSet.getString("ARPCOD"))
	            									.append(resultSet.getString("SEQNUM"))
	            									.append(resultSet.getString("SERNUM"))
	            									.toString();
	
				if(!currrentChildKey.equals(previousChildkey)){
					uLDPoolSegmentExceptionsVO = new ULDPoolSegmentExceptionsVO();
					
					uLDPoolSegmentExceptionsVO.setCompanyCode(resultSet.getString("CMPCOD"));
					uLDPoolSegmentExceptionsVO.setAirlineOne(resultSet.getString("AIRLINEONECOD"));
					uLDPoolSegmentExceptionsVO.setAirlineTwo(resultSet.getString("AIRLINETWOCOD"));
					uLDPoolSegmentExceptionsVO.setAirport(resultSet.getString("ARPCOD"));
					uLDPoolSegmentExceptionsVO.setOrigin(resultSet.getString("ORGCOD"));
					uLDPoolSegmentExceptionsVO.setDestination(resultSet.getString("DSTCOD"));
					
					uLDPoolSegmentExceptionsVO.setSequenceNumber(resultSet.getString("SEQNUM"));
					uLDPoolSegmentExceptionsVO.setAirlineIdentifierOne(resultSet.getInt("ARLONEIDR"));
					uLDPoolSegmentExceptionsVO.setAirlineIdentifierTwo(resultSet.getInt("ARLTWOIDR"));
					uLDPoolSegmentExceptionsVO.setSerialNumber(resultSet.getLong("SERNUM"));
					//log.log(Log.FINE,"ULDPoolSegmentExceptionVOs--------------->>>>"+uLDPoolSegmentExceptionsVO);
					segmentExceptionsVO.add(uLDPoolSegmentExceptionsVO);
					
					previousChildkey = currrentChildKey;
				}
			
			
				uLDPoolOwnerVO.setPoolSegmentsExceptionsVOs(segmentExceptionsVO);
				
			}
			log.log(Log.INFO, "Sequence Number*&%@@@", resultSet.getString("SEQNUM"));
			currrentChildKey = new StringBuilder(resultSet.getString("CMPCOD"))
										.append(resultSet.getString("AIRLINEONECOD"))
										.append(resultSet.getString("AIRLINETWOCOD"))
										.append(resultSet.getString("ARPCOD"))
										.append(resultSet.getString("SEQNUM"))
										.append(resultSet.getString("SERNUM"))
										.toString();

			if(!currrentChildKey.equals(previousChildkey)&&((resultSet.getString("SEQNUM"))!=null)){
			uLDPoolSegmentExceptionsVO = new ULDPoolSegmentExceptionsVO();
			log.log(Log.INFO, "Sequence Number not null*", resultSet.getString("SEQNUM"));
			uLDPoolSegmentExceptionsVO.setCompanyCode(resultSet.getString("CMPCOD"));
			uLDPoolSegmentExceptionsVO.setAirlineOne(resultSet.getString("AIRLINEONECOD"));
			uLDPoolSegmentExceptionsVO.setAirlineTwo(resultSet.getString("AIRLINETWOCOD"));
			uLDPoolSegmentExceptionsVO.setAirport(resultSet.getString("ARPCOD"));
			uLDPoolSegmentExceptionsVO.setOrigin(resultSet.getString("ORGCOD"));
			uLDPoolSegmentExceptionsVO.setDestination(resultSet.getString("DSTCOD"));
			uLDPoolSegmentExceptionsVO.setAirlineIdentifierOne(resultSet.getInt("ARLONEIDR"));
			uLDPoolSegmentExceptionsVO.setAirlineIdentifierTwo(resultSet.getInt("ARLTWOIDR"));
			uLDPoolSegmentExceptionsVO.setSequenceNumber(resultSet.getString("SEQNUM"));
			uLDPoolSegmentExceptionsVO.setSerialNumber(resultSet.getLong("SERNUM"));
			//log.log(Log.FINE,"ULDPoolSegmentExceptionVOs--------------->>>>"+uLDPoolSegmentExceptionsVO);
			segmentExceptionsVO.add(uLDPoolSegmentExceptionsVO);
			
			previousChildkey = currrentChildKey;
			}
						
			uLDPoolOwnerVO.setPoolSegmentsExceptionsVOs(segmentExceptionsVO);
			if(uLDPoolOwnerVO!=null && flag){
			uLDPoolOwnerVOs.add(uLDPoolOwnerVO);
			}
			log.log(Log.FINE, "uLDPoolOwnerVOs from multimapper--------->>>>",
					uLDPoolOwnerVOs);
		}
		return uLDPoolOwnerVOs;
	}
}
