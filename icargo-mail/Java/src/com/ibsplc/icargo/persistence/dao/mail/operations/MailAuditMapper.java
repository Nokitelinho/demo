/**
 * 
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-7794
 *
 */
public class MailAuditMapper implements Mapper<AuditDetailsVO>{

	@Override
	public AuditDetailsVO map(ResultSet result) throws SQLException {
		AuditDetailsVO auditDetailsVO = new AuditDetailsVO();
		auditDetailsVO.setCompanyCode(result.getString("CMPCOD"));
		auditDetailsVO.setAdditionalInformation(result
				.getString("ADLINF"));
		auditDetailsVO.setAction(result.getString("ACTCOD"));
		auditDetailsVO
				.setLastUpdateUser(result.getString("UPDUSR"));
		auditDetailsVO.setStationCode(result.getString("STNCOD"));
		// lastUpdateTime can be null
		java.sql.Timestamp lastUpdateTime = result
				.getTimestamp("UPDTXNTIM");
		if (lastUpdateTime != null) {
			auditDetailsVO.setLastUpdateTime(new LocalDate(
					LocalDate.NO_STATION, Location.NONE,
					lastUpdateTime));
		}
		return auditDetailsVO;
	}

}
