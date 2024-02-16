package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentScreeningVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

public class MailbagSecurityDetailsMapper implements MultiMapper<MailbagVO> {

	public List<MailbagVO> map(ResultSet rs) throws SQLException {
		

		ArrayList<MailbagVO> mailbagVOs = new ArrayList<>();
		MailbagVO mailbagVO = new MailbagVO();
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		while (rs.next()) {
			mailbagVO.setOrigin(rs.getString("ORGCOD"));
			mailbagVO.setDestination(rs.getString("DSTCOD"));
			mailbagVO.setMailbagId(rs.getString("MALIDR"));
			mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM")); 
			mailbagVO.setSecurityStatusCode(rs.getString("SECSTACOD"));
			if(null!=rs.getString("SHPPFX")&&null!=rs.getString("MSTDOCNUM")){
				mailbagVO.setAwbNumber(rs.getString("SHPPFX")+"-"+rs.getString("MSTDOCNUM"));
				}
			ConsignmentScreeningVO consignmentScreeningVo = new ConsignmentScreeningVO();
			consignmentScreeningVo.setScreeningLocation(rs.getString("SCRLOC"));
			consignmentScreeningVo.setScreeningMethodCode(rs.getString("SECSCRMTHCOD"));
			consignmentScreeningVo.setSecurityStatusParty(rs.getString("SECSTAPTY"));
			if (rs.getTimestamp("SECSTADAT") != null) {
				consignmentScreeningVo.setSecurityStatusDate(
						new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getTimestamp("SECSTADAT")));
			}
			consignmentScreeningVo.setSource(rs.getString("CSGSRC"));
			consignmentScreeningVo.setResult(rs.getString("SCRRES"));
			consignmentScreeningVo.setAgentType(rs.getString("AGTTYP"));
			consignmentScreeningVo.setIsoCountryCode(rs.getString("CNTCOD"));
			consignmentScreeningVo.setAgentID(rs.getString("AGTIDR"));
			consignmentScreeningVo.setScreenDetailType(rs.getString("SCRDTLTYP"));
			consignmentScreeningVo.setSerialNumber(rs.getLong("SERNUM"));
			consignmentScreeningVo.setExpiryDate(rs.getString("EXPDAT"));
			consignmentScreeningVo.setSecurityStatusCode(rs.getString("SECSTACOD")); 
			
			
			
			if (rs.getString("CSGDOCNUM") != null) {
				consignmentScreeningVo.setConsignmentNumber(rs.getString("CSGDOCNUM"));
			}
			if (rs.getString("POACOD") != null) {
				consignmentScreeningVo.setPaCode(rs.getString("POACOD"));
			}
			if (rs.getString("SCRLVL") != null) {
				consignmentScreeningVo.setScreenLevelValue(rs.getString("SCRLVL"));
			}
			consignmentScreeningVo.setCompanyCode(rs.getString("CMPCOD"));
			consignmentScreeningVo.setAgentSerialNumber(rs.getLong("AGTSERNUM"));
			consignmentScreeningVOs.add(consignmentScreeningVo);
		}
		mailbagVO.setConsignmentScreeningVO(consignmentScreeningVOs);
		mailbagVOs.add(mailbagVO);
		return mailbagVOs;
	}

}
