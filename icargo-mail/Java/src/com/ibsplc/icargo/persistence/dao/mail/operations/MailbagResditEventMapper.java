/*
 * MailbagResditEventMapper.java Created on Feb 13, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * 
 * @author A-8061 
 * 
 */
public class MailbagResditEventMapper implements Mapper<MailbagHistoryVO> {
	
	 public static final String CONST_RESDIT_STATUS_SEND = "Sent";
	 public static final String CONST_RESDIT_STATUS_UNSEND = "Unsent";
	 public static final String CONST_RESDIT_STATUS_UNPROCESSED = "UP";
	 public static final String CONST_RESDIT_STATUS_NOT_INITIATED= "Not Initiated";
	 public static final String CONST_RESDIT_STATUS_NOT_REQUIRED= "Sending Not Required";
	 public static final String CONST_RESDIT_STATUS_SENT_FAILED= "Sending Failed";
	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public MailbagHistoryVO map(ResultSet rs) throws SQLException {
		
		MailbagHistoryVO mailbagHistoryVO = new MailbagHistoryVO();
		mailbagHistoryVO.setCompanyCode(rs.getString("CMPCOD"));
		mailbagHistoryVO.setMailbagId(rs.getString("MALIDR"));
		
		if (rs.getTimestamp("EVTDAT") != null) {
			mailbagHistoryVO.setMessageTime(new LocalDate(LocalDate.NO_STATION,	Location.NONE, 
					rs.getTimestamp("EVTDAT")));
		}
		mailbagHistoryVO.setEventCode(rs.getString("EVTCOD"));
		if(rs.getString("PROSTA") !=null && MailConstantsVO.FLAG_YES.equals(rs.getString("PROSTA")) && rs.getString("RDTSND") !=null && MailConstantsVO.RESDIT_EVENT_GENERATED.equals(rs.getString("RDTSND")) ){		
			mailbagHistoryVO.setProcessedStatus(CONST_RESDIT_STATUS_SEND);
		} else {
			//added by A-7815 as part of IASCB-33078 and reviewed by Binu
			 //to message not sent status
			 if(MailConstantsVO.FLAG_YES.equals(rs.getString("PROSTA")) && rs.getString("RDTSND") !=null && !MailConstantsVO.RESDIT_EVENT_GENERATED.equals(rs.getString("RDTSND")) && rs.getString("RDTFALRSNCOD") ==null  ){			 
				mailbagHistoryVO.setProcessedStatus(CONST_RESDIT_STATUS_SENT_FAILED);
			} else if(rs.getString("PROSTA") !=null && MailConstantsVO.FLAG_YES.equals(rs.getString("PROSTA")) && rs.getString("RDTSND") !=null && "N".equals(rs.getString("RDTSND")) && rs.getString("RDTFALRSNCOD") !=null ){
				mailbagHistoryVO.setProcessedStatus(CONST_RESDIT_STATUS_NOT_REQUIRED);
			}  else {
				mailbagHistoryVO.setProcessedStatus(CONST_RESDIT_STATUS_NOT_INITIATED);
			}
		}
		if(rs.getString("PROSTA") !=null && MailConstantsVO.FLAG_NO.equals(rs.getString("PROSTA")) && rs.getString("RDTSND") !=null && MailConstantsVO.FLAG_NO.equals(rs.getString("RDTSND")) ){
			
			mailbagHistoryVO.setMailStatus(CONST_RESDIT_STATUS_UNPROCESSED);
		}
		if(rs.getString("PROSTA") !=null && MailConstantsVO.FLAG_YES.equals(rs.getString("PROSTA")) && rs.getString("RDTSND") !=null && MailConstantsVO.FLAG_NO.equals(rs.getString("RDTSND")) ){
			
			mailbagHistoryVO.setMailStatus(CONST_RESDIT_STATUS_UNPROCESSED);
		}
      if(rs.getString("EVTPRT") !=null  ){
			mailbagHistoryVO.setAirportCode(rs.getString("EVTPRT"));
		}
		return mailbagHistoryVO;
	}
}