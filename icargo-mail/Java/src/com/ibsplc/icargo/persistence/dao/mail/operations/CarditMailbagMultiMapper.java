/*
 * CarditMailbagMultiMapper.java Created on Jan 24, 2007
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  
 * @author A-1739
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  Jan 24, 2007			A-1739		Created
 */
public class CarditMailbagMultiMapper implements MultiMapper<MailbagVO> {

	private static final String RESDIT_DISP_SEP = "/";
	
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	/**
	 * TODO Purpose
	 * Jan 24, 2007, A-1739
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
	 */
	public List<MailbagVO> map(ResultSet rs) throws SQLException {
		log.entering("CarditMailbagMultiMapper", "map");
		
		Map<String, String> resditEventMap = initResditEventMap();
		
		List<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		
		MailbagVO mailbagVO = null;
		
		String currentMailbagId = null;
		String prevMailbagId = null;
		
		String currentEventId = null;
		String prevEventId = null;

		String event = null;
		String eventPort = null;
		
		StringBuilder eventStringBuilder = null;
		
		while(rs.next()) {			
			currentMailbagId = rs.getString("MALIDR");
			
			if(!currentMailbagId.equals(prevMailbagId)) {
				prevMailbagId = currentMailbagId;
				mailbagVO = new MailbagVO();
				mailbagVO.setMailbagId(currentMailbagId);
				populateMailbagDetails(rs, mailbagVO);
				eventStringBuilder = new StringBuilder();
				mailbagVOs.add(mailbagVO);
			}
			
			event = rs.getString("EVTCOD");
			if(event != null) {
				eventPort = rs.getString("EVTPRT");
				currentEventId = new StringBuilder(currentMailbagId).append(event).
									append(eventPort).toString();
				
				if(!currentEventId.equals(prevEventId)) {
					prevEventId = currentEventId;
					if(eventStringBuilder.length() > 0) {
						eventStringBuilder.append(RESDIT_DISP_SEP);
					}
					eventStringBuilder = eventStringBuilder.append(resditEventMap.get(event));
					//append("(").append(eventPort).append(")");
					mailbagVO.setResditEventString(eventStringBuilder.toString());
				}
			}
		}
		
		log.exiting("CarditMailbagMultiMapper", "map");
		return mailbagVOs;
	}
	
	/**
	 * TODO Purpose
	 * Jan 25, 2007, A-1739
	 * @return
	 */
	private Map<String, String> initResditEventMap() {
		Map<String, String> resditEventMap = new HashMap<String, String>();	
		resditEventMap.put(MailConstantsVO.RESDIT_RECEIVED, 
				MailConstantsVO.RESDIT_ACRON_RECV);
		resditEventMap.put(MailConstantsVO.RESDIT_ASSIGNED, 
				MailConstantsVO.RESDIT_ACRON_ASG);
		resditEventMap.put(MailConstantsVO.RESDIT_UPLIFTED, 
				MailConstantsVO.RESDIT_ACRON_UPL);
		resditEventMap.put(MailConstantsVO.RESDIT_PENDING, 
				MailConstantsVO.RESDIT_ACRON_PEND);
		resditEventMap.put(MailConstantsVO.RESDIT_RETURNED, 
				MailConstantsVO.RESDIT_ACRON_RET);
		resditEventMap.put(MailConstantsVO.RESDIT_HANDOVER_OFFLINE, 
				MailConstantsVO.RESDIT_ACRON_HND);
		resditEventMap.put(MailConstantsVO.RESDIT_DELIVERED, 
				MailConstantsVO.RESDIT_ACRON_DELV);
		resditEventMap.put(MailConstantsVO.RESDIT_HANDOVER_ONLINE, 
				MailConstantsVO.RESDIT_ACRON_HND_ONL);
		resditEventMap.put(MailConstantsVO.RESDIT_LOADED, 
				MailConstantsVO.RESDIT_ACRON_LOAD);
		return resditEventMap;
	}


	/**
	 * TODO Purpose
	 * Jan 24, 2007, A-1739
	 * @param rs
	 * @param mailbagVO
	 * @throws SQLException 
	 */
	private void populateMailbagDetails(ResultSet rs, MailbagVO mailbagVO) 
	throws SQLException {
		log.entering("CarditMailbagMultiMapper", "populateMailbagDetails");
		mailbagVO.setContainerNumber(rs.getString("CONNUM"));
		mailbagVO.setPaCode(rs.getString("SDRIDR"));
		mailbagVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		//can be null
		java.sql.Date consignDate = rs.getDate("CSGCMPDAT");
		if(consignDate != null) {
			mailbagVO.setConsignmentDate(
					new LocalDate(LocalDate.NO_STATION, Location.NONE,
							rs.getDate("CSGCMPDAT")));
		}
		mailbagVO.setResditEventPort(rs.getString("RDTEVTPRT"));
		Timestamp eventTime = rs.getTimestamp("EVTDATUTC");
		if(eventTime != null) {
			mailbagVO.setResditEventUTCDate(
					new GMTDate(rs.getTimestamp("EVTDATUTC")));
		}
		mailbagVO.setCarrierId(rs.getInt("FLTCARIDR"));
		mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
		mailbagVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		mailbagVO.setCarditRecipientId(rs.getString("RCTIDR"));
		mailbagVO.setHandoverPartner(rs.getString("POACARCOD"));
		mailbagVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));		
		mailbagVO.setCarrierCode(rs.getString("FLTCARCOD"));
		mailbagVO.setResditEventSeqNum(rs.getInt("SEQNUM"));
		log.exiting("CarditMailbagMultiMapper", "populateMailbagDetails");			
	}

}
