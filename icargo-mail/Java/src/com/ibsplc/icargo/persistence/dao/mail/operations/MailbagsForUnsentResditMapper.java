/*
 * MailbagsForUnsentResditMapper.java Created on Feb 09, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
/**
 * 
 * @author A-1936
 * The Class tat is used to map all the ResultSet into Vo 
 */
public class MailbagsForUnsentResditMapper   implements Mapper<MailbagVO>{
	/**
	 * 
	 * 
	 * @author A-1936
	 * This method is used to map the Result set into MailBagVo
	 * @param rs
	 * @return
	 * @throws SQLException
	 * 
	 */
	public MailbagVO map(ResultSet rs) throws SQLException{
		
		MailbagVO mailBagVo= new MailbagVO();
		mailBagVo.setCompanyCode(rs.getString("CMPCOD"));
		mailBagVo.setOoe(rs.getString("ORGEXGOFC"));
		mailBagVo.setDoe(rs.getString("DSTEXGOFC"));
		mailBagVo.setMailSubclass(rs.getString("MALSUBCLS"));
		mailBagVo.setMailCategoryCode(rs.getString("MALCTG"));
		mailBagVo.setMailbagId(rs.getString("MALIDR"));
		mailBagVo.setDespatchSerialNumber(rs.getString("DSN"));
		mailBagVo.setYear(rs.getInt("YER"));
		mailBagVo.setFlightNumber(
				rs.getString("FLTNUM"));
		mailBagVo.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
		mailBagVo.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		mailBagVo.setCarrierId(rs.getInt("FLTCARIDR"));
		mailBagVo.setContainerNumber(rs.getString("CONNUM"));
		mailBagVo.setResditEventPort(rs.getString("EVTPRT"));
		mailBagVo.setResditEventString(rs.getString("EVTCOD"));
		mailBagVo.setResditEventSeqNum(rs.getInt("MSGSEQNUM"));//a-8061 changed from SEQNUM to 
		if(rs.getTimestamp("EVTDATUTC")!=null){
		   mailBagVo.setResditEventUTCDate(new GMTDate(rs.getTimestamp("EVTDATUTC")));
		}
		mailBagVo.setCarditKey(rs.getString("CDTKEY"));
		mailBagVo.setHandoverPartner(rs.getString("POACARCOD"));
		mailBagVo.setCarditRecipientId(rs.getString("RCTIDR"));
		mailBagVo.setPaCode(rs.getString("SDRIDR"));
		mailBagVo.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		mailBagVo.setCarrierCode(rs.getString("TWOAPHCOD"));		
		return mailBagVo;
	}

}
