 /*
 * MailBagAuditHistoryMapper.java Created on Nov 5 2015 by A-5945 for ICRD-119569
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
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.MailBagAuditHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagAuditVO;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

public class MailBagAuditHistoryMapper   implements MultiMapper<MailBagAuditHistoryVO>{
	public List<MailBagAuditHistoryVO> map(ResultSet rs) throws SQLException {
		List<MailBagAuditHistoryVO> mailauditHistoryVOs = new ArrayList<MailBagAuditHistoryVO> (); 
		
	//	String currContainerKey = null;    
	//	String prevContainerKey = null;          
		while(rs.next()){
			/*Long sequenceNumber =rs.getLong("HISSEQNUM");
			currContainerKey = new StringBuilder().append(
					rs.getString("CMPCOD")).append(rs.getString("MALIDR"))
					.append((sequenceNumber==0)?0:rs.getLong("HISSEQNUM")).append(
							rs.getString("ACTCOD")).toString();	*/
		
			MailBagAuditHistoryVO mailBagAuditHistoryVO = new MailBagAuditHistoryVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
			mailBagAuditHistoryVO.setNewValue(rs.getString("HISFLDNEWVAL"));
			mailBagAuditHistoryVO.setOldValue(rs.getString("HISFLDOLDVAL"));
			mailBagAuditHistoryVO.setHistoryEvent(rs.getString("HISFLDNAM"));
			if(!"MODIFYMAL".equals(rs.getString("ACTCOD")))
				{
				mailBagAuditHistoryVO.setAdditionalInformation(rs.getString("ADLINF"));
				}
			else
				{
				mailBagAuditHistoryVO.setAdditionalInformation("Mail bag Modified");
				}
			String auditField = rs.getString("HISFLDNAM");
			if(auditField!=null && ("MAILCOMPANYCODE").equals(auditField)){
				mailBagAuditHistoryVO.setAuditField("Company Code")	;
			}else
			{
			mailBagAuditHistoryVO.setAuditField(rs.getString("HISFLDNAM"));
			}
			mailBagAuditHistoryVO.setUserId(rs.getString("UPDUSR"));
			LocalDate date = new LocalDate(rs.getString("STNCOD"),Location.ARP,rs.getTimestamp("UPDTXNTIMUTC"));
			String stationCode = rs.getString("STNCOD");
			mailBagAuditHistoryVO.setTxnLocalTime(date);
			 GMTDate lastUpdateDate = new GMTDate(rs.getTimestamp("UPDTXNTIMUTC")); 
			 LocalDate lastUpdDate = new LocalDate(lastUpdateDate,stationCode,Location.STN);
			 mailBagAuditHistoryVO.setLastUpdateTime(lastUpdDate) ;
			mailBagAuditHistoryVO.setTxnTime(new LocalDate(rs.getString("STNCOD"),Location.ARP,rs.getTimestamp("UPDTXNTIMUTC")).toGMTDate());
			mailBagAuditHistoryVO.setStationCode(rs.getString("STNCOD"));
			mailBagAuditHistoryVO.setActionCode(rs.getString("ACTCODDES"));//Added For ICRD-140584
			
			mailauditHistoryVOs.add(mailBagAuditHistoryVO);
			
			
			
		}
		
		return mailauditHistoryVOs;
		
	}

}
