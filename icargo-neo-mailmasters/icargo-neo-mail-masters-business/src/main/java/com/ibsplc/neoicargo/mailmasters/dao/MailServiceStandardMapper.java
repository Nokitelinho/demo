package com.ibsplc.neoicargo.mailmasters.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.neoicargo.mailmasters.vo.MailServiceStandardVO;
import lombok.extern.slf4j.Slf4j;

/** 
 * @author A-8149
 */
@Slf4j
public class MailServiceStandardMapper implements Mapper<MailServiceStandardVO> {
	public MailServiceStandardVO map(ResultSet rs) throws SQLException {
		MailServiceStandardVO mailServiceStandardVO = new MailServiceStandardVO();
		mailServiceStandardVO.setCompanyCode(rs.getString("CMPCOD"));
		mailServiceStandardVO.setGpaCode(rs.getString("GPACOD"));
		mailServiceStandardVO.setOriginCode(rs.getString("ORGCOD"));
		mailServiceStandardVO.setDestinationCode(rs.getString("DSTCOD"));
		mailServiceStandardVO.setServicelevel(rs.getString("SRVLVL"));
		mailServiceStandardVO.setScanWaived(rs.getString("SCNWVDFLG"));
		mailServiceStandardVO.setServicestandard(rs.getString("SRVSTD"));
		mailServiceStandardVO.setContractid(rs.getString("CTRIDR"));
		log.debug("" + "\n\n mailServiceStandardVO listed !! ----------> " + " " + mailServiceStandardVO.getGpaCode()
				+ " ");
		if (mailServiceStandardVO != null) {
			mailServiceStandardVO.setOperationFlag("U");
		}
		return mailServiceStandardVO;
	}
}
