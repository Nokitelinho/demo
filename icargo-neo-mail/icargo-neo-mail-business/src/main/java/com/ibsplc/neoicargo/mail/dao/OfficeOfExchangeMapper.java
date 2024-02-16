package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.OfficeOfExchangeVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

/** 
 * @author A-2037This class is used to map the ResultSet to OfficeOfExchangeVO
 */
public class OfficeOfExchangeMapper implements Mapper<OfficeOfExchangeVO> {
	/** 
	* @param rs
	* @return 
	* @throws SQLException
	*/
	public OfficeOfExchangeVO map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		if (MailConstantsVO.FLAG_ACTIVE.equals(rs.getString("ACTFLG"))) {
			officeOfExchangeVO.setActive(true);
		} else {
			officeOfExchangeVO.setActive(false);
		}
		officeOfExchangeVO.setCityCode(rs.getString("CTYCOD"));
		officeOfExchangeVO.setCode(rs.getString("EXGOFCCOD"));
		officeOfExchangeVO.setCodeDescription(rs.getString("EXGCODDES"));
		officeOfExchangeVO.setCompanyCode(rs.getString("CMPCOD"));
		officeOfExchangeVO.setCountryCode(rs.getString("CNTCOD"));
		officeOfExchangeVO.setAirportCode(rs.getString("ARPCOD"));
		officeOfExchangeVO.setOfficeCode(rs.getString("OFCCOD"));
		officeOfExchangeVO.setPoaCode(rs.getString("POACOD"));
		Timestamp lstUpdTime = rs.getTimestamp("LSTUPDTIM");
		if (lstUpdTime != null) {
			officeOfExchangeVO.setLastUpdateTime(localDateUtil.getLocalDate(null, lstUpdTime));
		}
		officeOfExchangeVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
		officeOfExchangeVO.setMailboxId(rs.getString("MALBOXID"));
		return officeOfExchangeVO;
	}
}
