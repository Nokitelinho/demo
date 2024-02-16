/*
 * GenerateUCMMessageMultiMapper.java Created on jul 08, 2006
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



import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.UCMFlightIdentificationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.UCMFlightMovementVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.UCMIncomingULDDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.UCMIncomingULDHeaderVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.UCMMessageIdentifierVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.UCMMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.UCMOutgoingULDDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.UCMOutgoingULDHeaderVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.ULDDetailsVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-2048
 *
 */
public class GenerateUCMMessageMultiMapper implements MultiMapper<UCMMessageVO>{
	/**
	 * @param rs 
	 * @return 
	 * @throws SQLException 
	 * 
	 */
	public List<UCMMessageVO> map(ResultSet rs) throws SQLException{
		Log log =LogFactory.getLogger("ULD.DEFAULTS");
		List<UCMMessageVO> uCMMessageVOs = new ArrayList<UCMMessageVO>();
		UCMMessageVO ucmMessageVO = new UCMMessageVO();
		UCMFlightIdentificationVO ucmFlightIdentificationVO = null;
		UCMMessageIdentifierVO ucmMessageIdentifierVO = null;
		UCMFlightMovementVO ucmFlightMovementVO = null;
		UCMIncomingULDHeaderVO ucmIncomingULDHeaderVO = null;
		UCMIncomingULDDetailsVO ucmIncomingULDDetailsVO = null;
		Collection<UCMIncomingULDDetailsVO> ucmIncomingULDDetailsVOs =null;
		UCMOutgoingULDHeaderVO ucmOutgoingULDHeaderVO = null;
		UCMOutgoingULDDetailsVO ucmOutgoingULDDetailsVO = null;
		Collection<UCMOutgoingULDDetailsVO> ucmOutgoingULDDetailsVOs = null;
		
		ULDDetailsVO inUldDetailsVO = null;
		Collection<ULDDetailsVO> inUldDetailsVOs = null;
		
		ULDDetailsVO outUldDetailsVO = null;
		Collection<ULDDetailsVO> outUldDetailsVOs = null;
		
		
		String 	presId ="";
    	String prevId = ""; 
    	
    	String childPresIdOne = "";
		String childPrevIdOne = "";
		
		String childPresIdTwo = "";
		String childPrevIdTwo = "";
		int numberCode =0;
		
		while (rs.next()) {
			presId =new StringBuffer(rs.getString("CMPCOD"))
			            .append(rs.getInt("FLTCARIDR"))
			            .append(rs.getString("FLTNUM"))
			            .append(rs.getInt("FLTSEQNUM"))
			            .append(rs.getInt("LEGSERNUM"))
			            .append(rs.getString("ARPCOD"))
			            .toString();
			if(!presId.equals(prevId)){
				ucmMessageVO = new UCMMessageVO();
				ucmMessageVO.setCompanyCode(rs.getString("CMPCOD"));
				ucmMessageVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
				
				ucmMessageVO.setMessageStandard("AHM");
				ucmMessageVO.setMessageType("UCM");
				ucmMessageVO.setStationCode(rs.getString("ARPCOD"));
				ucmMessageVO.setAirportCode(rs.getString("ARPCOD"));
				
				ucmMessageIdentifierVO = new UCMMessageIdentifierVO();
				ucmMessageIdentifierVO.setUcmMessageIdentifier("UCM");
				ucmMessageVO.setUcmMessageIdentifierVO(ucmMessageIdentifierVO);
				
				ucmFlightIdentificationVO = new UCMFlightIdentificationVO();
				ucmFlightIdentificationVO.setCarrierCode(rs.getString("ARLCOD"));
				if(rs.getDate("FLTDAT") != null) {
				    String dateOfMonth = rs.getDate("FLTDAT").toString();
				    int day = Integer.parseInt(dateOfMonth.substring(9, 10));
					ucmFlightIdentificationVO .setDateOfMonth(day);
				}
				ucmFlightIdentificationVO.setFirstFlightnumber(rs.getString("FLTNUM")); 
				ucmMessageVO.setUcmFlightIdentificationVO(ucmFlightIdentificationVO);
				
				
				ucmFlightMovementVO = new UCMFlightMovementVO() ;
				ucmFlightMovementVO.setAirportCode(rs.getString("ARPCOD"));
				ucmMessageVO.setUcmFlightMovementVO(ucmFlightMovementVO);
				
				ucmIncomingULDHeaderVO = new UCMIncomingULDHeaderVO();
				ucmIncomingULDHeaderVO.setUldIdentifier("IN");
				ucmMessageVO.setUcmIncomingULDHeaderVO(ucmIncomingULDHeaderVO);
				
				ucmOutgoingULDHeaderVO = new UCMOutgoingULDHeaderVO();
				ucmOutgoingULDHeaderVO.setUldIdentifier("OUT");
				ucmMessageVO.setUcmOutgoingULDHeaderVO(ucmOutgoingULDHeaderVO);
				
				
				
				prevId = presId ;
				ucmIncomingULDDetailsVOs =new HashSet<UCMIncomingULDDetailsVO>() ;
				ucmOutgoingULDDetailsVOs = new HashSet<UCMOutgoingULDDetailsVO>();
			}
			childPresIdOne = new StringBuffer(rs.getString("CMPCOD"))
					            .append(rs.getInt("FLTCARIDR"))
					            .append(rs.getString("FLTNUM"))
					            .append(rs.getInt("FLTSEQNUM"))
					            .append(rs.getInt("LEGSERNUM"))
					            .append(rs.getString("ARPCOD"))
					            .append(rs.getString("ULDNUM"))
					            .toString();
			if(!childPresIdOne.equalsIgnoreCase(childPrevIdOne)	) {
			/*	if(inUldDetailsVOs != null && inUldDetailsVOs.size() >0) {
					ucmIncomingULDDetailsVO.setUldDetails(
							new ArrayList<ULDDetailsVO>(inUldDetailsVOs));
				}*/
				if(ucmIncomingULDDetailsVO != null) {
					ucmIncomingULDDetailsVOs.add(ucmIncomingULDDetailsVO);	
				}
				ucmIncomingULDDetailsVO = new UCMIncomingULDDetailsVO();
			//	inUldDetailsVOs = new HashSet<ULDDetailsVO>();
			//	ucmIncomingULDDetailsVO.get
				
				childPrevIdOne = childPresIdOne;
			}
			
			/*if(rs.getString("ULDNUM") != null &&
					rs.getString("ULDNUM").trim().length() >0) {
				
				inUldDetailsVO = new ULDDetailsVO();
				String serialNum ="";
				String uldnum = rs.getString("ULDNUM");
				inUldDetailsVO.setUldType(uldnum.substring(0,3));
				uldnum = uldnum.substring(3,uldnum.length());
				if(rs.getString("ARLCOD") != null && rs.getString("ARLCOD").trim().length() >0) {
					numberCode = rs.getString("ARLCOD").trim().length();
				}
				serialNum =uldnum.substring(0,uldnum.length()-numberCode);
				log.log(Log.FINE, "serial num"+serialNum);
				
				inUldDetailsVO.setUldSerialNumber(serialNum);
				uldnum = uldnum.substring(uldnum.length()-numberCode,uldnum.length());
				log.log(Log.FINE, "owner code"+uldnum);
				
				inUldDetailsVO.setUldOwnerCode(uldnum);
				
				inUldDetailsVO.setContentCode(rs.getString("CNT"));
				inUldDetailsVO.setAirportCode(rs.getString("POU"));
				
				
				inUldDetailsVOs.add(inUldDetailsVO);
			}*/
			
			
			/////////// outuld
			childPresIdTwo = new StringBuffer(rs.getString("CMPCOD"))
            .append(rs.getInt("FLTCARIDR"))
            .append(rs.getString("FLTNUM"))
            .append(rs.getInt("FLTSEQNUM"))
            .append(rs.getInt("LEGSERNUM"))
            .append(rs.getString("ARPCOD"))
            .append(rs.getString("ULDNUM"))
            .toString();
			if(!childPresIdTwo.equalsIgnoreCase(childPrevIdTwo)	) {
			/*	if(outUldDetailsVOs != null && outUldDetailsVOs.size() >0) {
					ucmOutgoingULDDetailsVO.setUldDetails(
							new ArrayList<ULDDetailsVO>(outUldDetailsVOs));
				}*/
				String serialNum ="";
				if(ucmOutgoingULDDetailsVO != null) {
					ucmOutgoingULDDetailsVOs.add(ucmOutgoingULDDetailsVO);	
				}
				ucmOutgoingULDDetailsVO = new UCMOutgoingULDDetailsVO();
				
				String uldnum = rs.getString("ULDNUM");
				ucmOutgoingULDDetailsVO.setUldType(uldnum.substring(0,3));
				uldnum = uldnum.substring(3,uldnum.length());
				if(rs.getString("ARLCOD") != null && rs.getString("ARLCOD").trim().length() >0) {
					numberCode = rs.getString("ARLCOD").trim().length();
				}
				serialNum =uldnum.substring(0,uldnum.length()-numberCode);
				log.log(Log.FINE, "serial num"+serialNum);
				ucmOutgoingULDDetailsVO.setUldSerialNumber(serialNum);
				uldnum = uldnum.substring(uldnum.length()-numberCode,uldnum.length());
				log.log(Log.FINE, "owner code"+uldnum);
				
				ucmOutgoingULDDetailsVO.setUldOwnerCode(uldnum);
			//	outUldDetailsVOs = new HashSet<ULDDetailsVO>();
				
				
				
				
				
				
				ucmOutgoingULDDetailsVO.setContentCode(rs.getString("CNT"));
				ucmOutgoingULDDetailsVO.setAirportCode(rs.getString("POU"));
				
				
				childPrevIdTwo = childPresIdTwo;
			}
			
		/*	if(rs.getString("ULDNUM") != null &&
					rs.getString("ULDNUM").trim().length() >0) {
				outUldDetailsVO = new ULDDetailsVO();
				String serialNum ="";
				String uldnum = rs.getString("ULDNUM");
				outUldDetailsVO.setUldType(uldnum.substring(0,3));
				uldnum = uldnum.substring(3,uldnum.length());
				if(rs.getString("ARLCOD") != null && rs.getString("ARLCOD").trim().length() >0) {
					numberCode = rs.getString("ARLCOD").trim().length();
				}
				
				serialNum =uldnum.substring(0,uldnum.length()-numberCode);
				log.log(Log.FINE, "serial num"+serialNum);
				outUldDetailsVO.setUldSerialNumber(serialNum);
				
				uldnum = uldnum.substring(uldnum.length()-numberCode,uldnum.length());
				log.log(Log.FINE, "owner code"+uldnum);
				outUldDetailsVO.setUldOwnerCode(uldnum);
				
				outUldDetailsVO.setContentCode(rs.getString("CNT"));
				outUldDetailsVO.setAirportCode(rs.getString("POU"));
				outUldDetailsVOs.add(outUldDetailsVO);
			}*/
			
			
		}
		if(ucmMessageVO != null) {
			if(inUldDetailsVOs != null && inUldDetailsVOs.size() >0) {
				ucmIncomingULDDetailsVO.setUldDetails(
						new ArrayList<ULDDetailsVO>(inUldDetailsVOs));
			}
			if(ucmIncomingULDDetailsVO != null) {
				ucmIncomingULDDetailsVOs.add(ucmIncomingULDDetailsVO);	
			}
		//	if(outUldDetailsVOs != null && outUldDetailsVOs.size() >0) {
		//		ucmOutgoingULDDetailsVO.setUldDetails(
		//				new ArrayList<ULDDetailsVO>(outUldDetailsVOs));
		//	}
			if(ucmOutgoingULDDetailsVO != null) {
				ucmOutgoingULDDetailsVOs.add(ucmOutgoingULDDetailsVO);	
			}
			if(ucmIncomingULDDetailsVOs != null && ucmIncomingULDDetailsVOs.size() >0) {
				ucmMessageVO.setUcmIncomingULDDetailsVOs(
						new ArrayList<UCMIncomingULDDetailsVO>(ucmIncomingULDDetailsVOs));
			}
			if(ucmOutgoingULDDetailsVOs != null && ucmOutgoingULDDetailsVOs.size() >0) {
				ucmMessageVO.setUcmOutgoingULDDetailsVOs(
						new ArrayList<UCMOutgoingULDDetailsVO>(ucmOutgoingULDDetailsVOs));	
			}
			uCMMessageVOs.add(ucmMessageVO);
		}
		
		
	    return 	uCMMessageVOs;
	}
}