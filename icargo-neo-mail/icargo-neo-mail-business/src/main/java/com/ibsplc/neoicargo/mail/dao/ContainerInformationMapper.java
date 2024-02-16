package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ContainerInformationVO;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;

/** 
 * @author A-1739
 */
@Slf4j
public class ContainerInformationMapper implements Mapper<ContainerInformationVO> {
	/** 
	* TODO Purpose Sep 12, 2006, a-1739
	* @param rs
	* @return collection of containerinfoVOs
	* @throws SQLException
	* @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	*/
	public ContainerInformationVO map(ResultSet rs) throws SQLException {
		log.debug("ContainerInformationMapper" + " : " + "map" + " Entering");
		ContainerInformationVO containerInformationVO = new ContainerInformationVO();
		containerInformationVO.setContainerNumber(rs.getString("ULDNUM"));
		containerInformationVO.setEquipmentQualifier(rs.getString("EQPQLF"));
		containerInformationVO.setContainerType(rs.getString("TYPCOD"));
		containerInformationVO.setCodeListResponsibleAgency(rs.getString("NUMCODLSTAGY"));
		containerInformationVO.setCarrierCode(rs.getString("FLTCARCOD"));
		containerInformationVO.setPartnerId(rs.getString("POACARCOD"));
		if (rs.getTimestamp("EVTDATUTC") != null) {
			containerInformationVO.setEventDate(new GMTDate(rs.getTimestamp("EVTDATUTC")));

		}
		containerInformationVO.setEventSequenceNumber(rs.getLong("SEQNUM"));
		containerInformationVO.setJourneyID(rs.getString("CONJRNIDR"));
		log.debug("ContainerInformationMapper" + " : " + "map" + " Exiting");
		return containerInformationVO;
	}
}
