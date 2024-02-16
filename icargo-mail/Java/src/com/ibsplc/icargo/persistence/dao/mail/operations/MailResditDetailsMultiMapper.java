/*
 * MailResditDetailsMultiMapper.java Created on August 02, 2011
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.MailResditVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author SAP15
 *
 */
public class MailResditDetailsMultiMapper implements 
	MultiMapper<HashMap<String,Collection<MailResditVO>>>{
	
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	private static final String CLASS_NAME = "MailResditDetailsMultiMapper";
	

	/**
	 * 
	 */
	public List<HashMap<String,Collection<MailResditVO>>> map(ResultSet rs) throws SQLException {
		List<HashMap<String,String>> statusMaps = new ArrayList<HashMap<String,String>>();
		log.entering(CLASS_NAME, "map");
		HashMap<String,Collection<MailResditVO>> resditDetailsMap 
					= new HashMap<String,Collection<MailResditVO>>();
		Collection<MailResditVO> mailResditVOs = null;
		StringBuilder keyBuilder 	= null;
		MailResditVO mailResditVO 	= null;
		
		while(rs.next()) {
			
			keyBuilder = new StringBuilder(rs.getString("MALIDR"))
				.append(rs.getString("EVTPRT")).append(rs.getString("EVTCOD"));
			mailResditVOs = resditDetailsMap.get(keyBuilder.toString());
			if(mailResditVOs == null) {
				mailResditVOs = new ArrayList<MailResditVO>();
				resditDetailsMap.put(keyBuilder.toString(), mailResditVOs);
			}
			
			mailResditVO = new MailResditVO();
			mailResditVO.setCarrierId(rs.getInt("FLTCARIDR"));
			mailResditVO.setFlightNumber(rs.getString("FLTNUM"));
			mailResditVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
			mailResditVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
			
			mailResditVO.setCompanyCode(rs.getString("CMPCOD"));
			mailResditVO.setMailId (rs.getString("MALIDR"));
			mailResditVO.setEventAirport(rs.getString("EVTPRT"));
			mailResditVO.setEventCode (rs.getString("EVTCOD"));
			mailResditVO.setResditSequenceNum (rs.getInt("SEQNUM"));
			mailResditVO.setResditSentFlag(rs.getString("RDTSND"));
			mailResditVO.setProcessedStatus(rs.getString("PROSTA"));
					mailResditVOs.add(mailResditVO);
		}
		
		List<HashMap<String,Collection<MailResditVO>>> map = 
			new ArrayList<HashMap<String,Collection<MailResditVO>>>();
		map.add(resditDetailsMap);
		
		return map;
	}
}
