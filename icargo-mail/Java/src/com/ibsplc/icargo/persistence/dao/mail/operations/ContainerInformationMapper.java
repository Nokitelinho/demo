/*
 * ContainerInformationMapper.java Created on Sep 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ContainerInformationVO;
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
public class ContainerInformationMapper implements Mapper<ContainerInformationVO> {

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	/**
	 * TODO Purpose
	 * Sep 12, 2006, a-1739
	 * @param rs
	 * @return collection of containerinfoVOs
	 * @throws SQLException
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 */
	public ContainerInformationVO map(ResultSet rs) throws SQLException {
		log.entering("ContainerInformationMapper", "map");
		ContainerInformationVO containerInformationVO =
			new ContainerInformationVO();
		containerInformationVO.setContainerNumber(rs.getString("ULDNUM"));
		containerInformationVO.setEquipmentQualifier(rs.getString("EQPQLF"));
		containerInformationVO.setContainerType(rs.getString("TYPCOD"));
		containerInformationVO.setCodeListResponsibleAgency(
				rs.getString("NUMCODLSTAGY"));
		containerInformationVO.setCarrierCode(rs.getString("FLTCARCOD"));
		containerInformationVO.setPartnerId(rs.getString("POACARCOD"));
		if(rs.getTimestamp("EVTDATUTC")!=null){                
		containerInformationVO.setEventDate(
				new GMTDate(rs.getTimestamp("EVTDATUTC")));
		}
		containerInformationVO.setEventSequenceNumber(
				rs.getLong("SEQNUM"));
		containerInformationVO.setJourneyID(rs.getString("CONJRNIDR"));
		
		log.exiting("ContainerInformationMapper", "map");
		return containerInformationVO;
	}

}
