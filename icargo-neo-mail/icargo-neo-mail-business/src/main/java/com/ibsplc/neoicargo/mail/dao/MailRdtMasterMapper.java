package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.neoicargo.mail.vo.MailRdtMasterVO;
import lombok.extern.slf4j.Slf4j;

/** 
 * Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.MailRdtMasterMapper.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-6991	:	18-Jul-2018	:	Draft
 */
@Slf4j
public class MailRdtMasterMapper implements Mapper<MailRdtMasterVO> {
	public MailRdtMasterVO map(ResultSet rs) throws SQLException {
		MailRdtMasterVO mailRdtMasterVO = new MailRdtMasterVO();
		mailRdtMasterVO.setCompanyCode(rs.getString("CMPCOD"));
		mailRdtMasterVO.setGpaCode(rs.getString("GPACOD"));
		mailRdtMasterVO.setAirportCodes(rs.getString("DSTARPCOD"));
		mailRdtMasterVO.setOriginAirportCodes(rs.getString("ORGARPCOD"));
		mailRdtMasterVO.setSeqnum(rs.getLong("SERNUM"));
		mailRdtMasterVO.setMailClass(rs.getString("MALCLS"));
		mailRdtMasterVO.setMailServiceLevel(rs.getString("MALSRVLVL"));
		mailRdtMasterVO.setMailType(rs.getString("MALTYP"));
		mailRdtMasterVO.setRdtDay(rs.getInt("RDTDAY"));
		mailRdtMasterVO.setRdtOffset(rs.getInt("RDTOFT"));
		mailRdtMasterVO.setRdtRule(rs.getString("RDTRUL"));
		mailRdtMasterVO.setRdtCfgType(rs.getString("RDTCFGTYP"));
		log.debug("" + "\n\n mailRdtMasterVO listed !! ----------> " + " " + mailRdtMasterVO.getAirportCodes());
		return mailRdtMasterVO;
	}
}
