package com.ibsplc.neoicargo.mailmasters.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.neoicargo.mailmasters.vo.PostalAdministrationVO;

/** 
 * @author A-2037
 */
public class PALovMapper implements Mapper<PostalAdministrationVO> {
	/** 
	* @param rs
	* @return 
	* @throws SQLException
	*/
	public PostalAdministrationVO map(ResultSet rs) throws SQLException {
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		postalAdministrationVO.setCompanyCode(rs.getString("CMPCOD"));
		postalAdministrationVO.setPaCode(rs.getString("POACOD"));
		postalAdministrationVO.setPaName(rs.getString("POANAM"));
		postalAdministrationVO.setPartialResdit(PostalAdministrationVO.FLAG_YES.equals(rs.getString("PRTRDT")));
		postalAdministrationVO
				.setMsgEventLocationNeeded(PostalAdministrationVO.FLAG_YES.equals(rs.getString("MSGEVTLOC")));
		return postalAdministrationVO;
	}
}
