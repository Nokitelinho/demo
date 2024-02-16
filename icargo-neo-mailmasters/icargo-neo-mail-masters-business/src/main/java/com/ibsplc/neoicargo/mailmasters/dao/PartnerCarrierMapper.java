package com.ibsplc.neoicargo.mailmasters.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.mailmasters.vo.PartnerCarrierVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

/** 
 * @author A-1876
 */
public class PartnerCarrierMapper implements Mapper<PartnerCarrierVO> {
	/** 
	* @param rs
	* @return 
	* @throws SQLException
	*/
	public PartnerCarrierVO map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		PartnerCarrierVO partnerCarrierVO = new PartnerCarrierVO();
		partnerCarrierVO.setCompanyCode(rs.getString("CMPCOD"));
		partnerCarrierVO.setOwnCarrierCode(rs.getString("OWNCARCOD"));
		partnerCarrierVO.setAirportCode(rs.getString("ARPCOD"));
		partnerCarrierVO.setPartnerCarrierCode(rs.getString("PRTCARCOD"));
		partnerCarrierVO.setPartnerCarrierId(rs.getString("PRTCARIDR"));
		partnerCarrierVO.setPartnerCarrierName(rs.getString("PRTCARNAM"));
		Timestamp lstUpdTime = rs.getTimestamp("LSTUPDTIM");
		if (lstUpdTime != null) {
			partnerCarrierVO.setLastUpdateTime(localDateUtil.getLocalDate(null, lstUpdTime));
		}
		partnerCarrierVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
		return partnerCarrierVO;
	}
}
