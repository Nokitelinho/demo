/*
 * ResditEventMapper.java Created on Sep 8, 2006
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
import com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

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
 * 0.1     		  Sep 8, 2006			a-1739		Created
 */
public class ResditEventMapper implements Mapper<ResditEventVO> {
	private static final String MSTDOCNUM ="MSTDOCNUM";
	private static final String SHPPFX = "SHPPFX";

	/**
	 * returns the VO
	 * @param rs
	 * @return ResditEventVO
	 * @throws SQLException
	 */
	public ResditEventVO map(ResultSet rs) throws SQLException {
		ResditEventVO resditEventVO = new ResditEventVO();
		if(MailConstantsVO.M49_1_1.equals(rs.getString("RDTVERNUM")))
			{
			resditEventVO.setM49Resdit(true);
			}
		resditEventVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		String eventCode = rs.getString("EVTCOD");
		resditEventVO.setResditEventCode(eventCode);
		resditEventVO.setEventPort(rs.getString("EVTPRT"));
		resditEventVO.setMessageSequenceNumber(rs.getInt("MSGSEQNUM"));
		String uniqueId = System.currentTimeMillis() + eventCode;
		resditEventVO.setUniqueIdForFlag(uniqueId);
		resditEventVO.setPaCode(rs.getString("POACOD"));
		resditEventVO.setResditVersion(rs.getString("MSGVERNUM"));
		resditEventVO.setEventPortName(rs.getString("ARPNAM"));
		resditEventVO.setCarditExist(rs.getString("CDTEXT"));
		resditEventVO.setActualSenderId(rs.getString("ACTSDRIDR"));
		resditEventVO.setReciever(rs.getString("RCTIDR"));
		//Added as part of ICRD-181309 starts
		if(rs.getTimestamp("EVTDAT")!=null&&rs.getString("EVTPRT")!=null&&
			rs.getString("EVTPRT").trim().length()>0){
		resditEventVO.setEventDate(new LocalDate(rs.getString("EVTPRT"), 
				Location.ARP, rs.getTimestamp("EVTDAT")));
		}
		//Added as part of ICRD-181309 ends
		
		if(MailConstantsVO.FLAG_YES.equals(rs.getString("MSGEVTLOC"))){
			resditEventVO.setMsgEventLocationEnabled(true);
		}
		if(MailConstantsVO.FLAG_YES.equals(rs.getString("PRTRDT"))){
			resditEventVO.setPartialResdit(true);
		}
		resditEventVO.setPartyName(rs.getString("MALBOXDES"));
		resditEventVO.setCarrierCode(rs.getString("FLTCARCOD"));
		resditEventVO.setCarrierId(rs.getInt("FLTCARIDR"));
		resditEventVO.setFlightNumber(rs.getString("FLTNUM"));
		resditEventVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		resditEventVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		if(rs.getString(MSTDOCNUM)!=null && !rs.getString(MSTDOCNUM).isEmpty()){
			resditEventVO.setMasterDocumentNumber(rs.getString(MSTDOCNUM));
		}
		if(rs.getString(SHPPFX)!=null && !rs.getString(SHPPFX).isEmpty()){
			resditEventVO.setShipmentPrefix(rs.getString(SHPPFX));
		}
		resditEventVO.setAdditionlAddressID(rs.getString("MSGADDSEQNUM"));
		return resditEventVO;
	}

}
