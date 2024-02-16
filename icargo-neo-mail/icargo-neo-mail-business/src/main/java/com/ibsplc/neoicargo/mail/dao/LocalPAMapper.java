package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.neoicargo.mail.vo.PostalAdministrationVO;

/** 
 * @author A-2037
 */
public class LocalPAMapper implements Mapper<PostalAdministrationVO> {
	/** 
	* @param rs
	* @return
	* @throws SQLException
	*/
	public PostalAdministrationVO map(ResultSet rs) throws SQLException {
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		postalAdministrationVO.setAddress(rs.getString("POAADR"));
		postalAdministrationVO.setCompanyCode(rs.getString("CMPCOD"));
		postalAdministrationVO.setCountryCode(rs.getString("CNTCOD"));
		postalAdministrationVO.setPaCode(rs.getString("POACOD"));
		postalAdministrationVO.setPaName(rs.getString("POANAM"));
		return postalAdministrationVO;
	}
}
