package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;

/** 
 * @author A-7794
 */
public class MailAuditMapper implements Mapper<AuditDetailsVO> {
	@Override
	public AuditDetailsVO map(ResultSet result) throws SQLException {
		AuditDetailsVO auditDetailsVO = new AuditDetailsVO();
		auditDetailsVO.setCompanyCode(result.getString("CMPCOD"));
		auditDetailsVO.setAdditionalInformation(result.getString("ADLINF"));
		auditDetailsVO.setAction(result.getString("ACTCOD"));
		auditDetailsVO.setLastUpdateUser(result.getString("UPDUSR"));
		auditDetailsVO.setStationCode(result.getString("STNCOD"));
		java.sql.Timestamp lastUpdateTime = result.getTimestamp("UPDTXNTIM");
		if (lastUpdateTime != null) {
			auditDetailsVO.setLastUpdateTime(new com.ibsplc.icargo.framework.util.time.LocalDate(
					NO_STATION, Location.NONE, lastUpdateTime));
		}
		return auditDetailsVO;
	}
}
