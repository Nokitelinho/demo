/*
 * ReceptanceInformationMapper.java Created on Sep 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ReceptacleInformationVO;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
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
 * 0.1     		  Sep 12, 2006			a-1739		Created
 */
public class ReceptanceInformationMapper implements
		Mapper<ReceptacleInformationVO> {

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	/**
	 * TODO Purpose
	 * Sep 12, 2006, a-1739
	 * @param rs
	 * @return 
	 * @throws SQLException
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 */
	public ReceptacleInformationVO map(ResultSet rs) throws SQLException {
		log.entering("ReceptanceInformationMapper", "map");
		ReceptacleInformationVO receptacleInformationVO = 
			new ReceptacleInformationVO();
		receptacleInformationVO.setReceptacleID(rs.getString("MALIDR"));
//		receptacleInformationVO.setContainerNumber(rs.getString("CONNUM"));
		receptacleInformationVO.setPartnerId(rs.getString("POACARCOD"));
		receptacleInformationVO.setOriginExgOffice(rs.getString("MALIDR").substring(0, 6));
		receptacleInformationVO.setCarrierCode(rs.getString("FLTCARCOD"));
		receptacleInformationVO.setEventDate(
				new GMTDate(rs.getTimestamp("EVTDATUTC")));
		receptacleInformationVO.setEventSequenceNumber(
				rs.getLong("SEQNUM"));
		receptacleInformationVO.setJourneyID(rs.getString("DSPIDR"));
		receptacleInformationVO.setEquipmentQualifier(rs.getString("EQPQLF"));
		//Added for CRQ ICRD-111886 by A-5526 starts
		receptacleInformationVO.setPartyIdentifier(rs.getString("PTYIDR"));
		receptacleInformationVO.setMailboxID(rs.getString("MALBOXIDR"));    
		receptacleInformationVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		//Added for CRQ ICRD-111886 by A-5526 ends
		receptacleInformationVO.setMailBagOrigin(rs.getString("ORGCOD"));  
		receptacleInformationVO.setMailBagDestination(rs.getString("DSTCOD")); 
		log.exiting("ReceptanceInformationMapper", "map");
		return receptacleInformationVO;
	}

}
