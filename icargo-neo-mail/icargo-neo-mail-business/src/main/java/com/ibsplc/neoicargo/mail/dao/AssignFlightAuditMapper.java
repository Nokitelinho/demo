package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import java.time.ZonedDateTime;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.icargo.framework.util.time.Location;

public class AssignFlightAuditMapper implements MultiMapper<AuditDetailsVO> {
	public List<AuditDetailsVO> map(ResultSet rs) throws SQLException {
		ArrayList<AuditDetailsVO> flightAuditDetailVOs = new ArrayList<>();
		while (rs.next()) {
			AuditDetailsVO auditDetailsVO = new AuditDetailsVO();
			auditDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
			auditDetailsVO.setStationCode(rs.getString("STNCOD"));
			auditDetailsVO.setLastUpdateUser(rs.getString("UPDUSR"));
			auditDetailsVO.setLastUpdateTime(new com.ibsplc.icargo.framework.util.time.LocalDate(
					com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION, Location.NONE, rs.getTimestamp("UPDTXNTIM")));
			auditDetailsVO.setAction(rs.getString("ACTCOD"));
			auditDetailsVO.setAdditionalInformation(rs.getString("ADLINF"));
			auditDetailsVO.setRemarks(rs.getString("AUDRMK"));
			auditDetailsVO.setTriggerPoint(rs.getString("TRGPNT"));
			flightAuditDetailVOs.add(auditDetailsVO);
		}
		return flightAuditDetailVOs;
	}
}
