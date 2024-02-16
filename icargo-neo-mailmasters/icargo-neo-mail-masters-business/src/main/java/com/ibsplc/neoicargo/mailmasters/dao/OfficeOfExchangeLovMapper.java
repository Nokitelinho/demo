package com.ibsplc.neoicargo.mailmasters.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.neoicargo.mailmasters.vo.OfficeOfExchangeVO;

/** 
 * This class is used to map the ResultSet to OfficeOfExchangeVO
 * @author A-2037
 */
public class OfficeOfExchangeLovMapper implements Mapper<OfficeOfExchangeVO> {
	/** 
	* @param rs
	* @return 
	* @throws SQLException
	*/
	public OfficeOfExchangeVO map(ResultSet rs) throws SQLException {
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		officeOfExchangeVO.setCode(rs.getString("EXGOFCCOD"));
		officeOfExchangeVO.setCodeDescription(rs.getString("EXGCODDES"));
		officeOfExchangeVO.setCompanyCode(rs.getString("CMPCOD"));
		officeOfExchangeVO.setAirportCode(rs.getString("ARPCOD"));
		officeOfExchangeVO.setPoaCode(rs.getString("POACOD"));
		return officeOfExchangeVO;
	}
}
