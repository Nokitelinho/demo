package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ReceptacleInformationVO;
import lombok.extern.slf4j.Slf4j;

/** 
 * @author A-1739
 */
@Slf4j
public class ReceptanceInformationMapper implements Mapper<ReceptacleInformationVO> {
	/** 
	* TODO Purpose Sep 12, 2006, a-1739
	* @param rs
	* @return 
	* @throws SQLException
	* @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	*/
	public ReceptacleInformationVO map(ResultSet rs) throws SQLException {
		log.debug("ReceptanceInformationMapper" + " : " + "map" + " Entering");
		ReceptacleInformationVO receptacleInformationVO = new ReceptacleInformationVO();
		receptacleInformationVO.setReceptacleID(rs.getString("MALIDR"));
		receptacleInformationVO.setPartnerId(rs.getString("POACARCOD"));
		receptacleInformationVO.setOriginExgOffice(rs.getString("MALIDR").substring(0, 6));
		receptacleInformationVO.setCarrierCode(rs.getString("FLTCARCOD"));
		receptacleInformationVO.setEventDate(new GMTDate(rs.getTimestamp("EVTDATUTC")));
		receptacleInformationVO.setEventSequenceNumber(rs.getLong("SEQNUM"));
		receptacleInformationVO.setJourneyID(rs.getString("DSPIDR"));
		receptacleInformationVO.setEquipmentQualifier(rs.getString("EQPQLF"));
		receptacleInformationVO.setPartyIdentifier(rs.getString("PTYIDR"));
		receptacleInformationVO.setMailboxID(rs.getString("MALBOXIDR"));
		receptacleInformationVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		receptacleInformationVO.setMailBagOrigin(rs.getString("ORGCOD"));
		receptacleInformationVO.setMailBagDestination(rs.getString("DSTCOD"));
		log.debug("ReceptanceInformationMapper" + " : " + "map" + " Exiting");
		return receptacleInformationVO;
	}
}
