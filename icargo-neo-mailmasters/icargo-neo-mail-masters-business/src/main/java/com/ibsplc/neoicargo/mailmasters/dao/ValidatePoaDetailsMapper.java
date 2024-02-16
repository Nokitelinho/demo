package com.ibsplc.neoicargo.mailmasters.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.neoicargo.mailmasters.vo.PostalAdministrationDetailsVO;

/** 
 * @author A-3251
 */
public class ValidatePoaDetailsMapper implements Mapper<PostalAdministrationDetailsVO> {
	public PostalAdministrationDetailsVO map(ResultSet rs) throws SQLException {
		PostalAdministrationDetailsVO postalAdministrationDetailsVO = new PostalAdministrationDetailsVO();
		postalAdministrationDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		postalAdministrationDetailsVO.setPoaCode(rs.getString("POACOD"));
		postalAdministrationDetailsVO.setParCode(rs.getString("PARCOD"));
		postalAdministrationDetailsVO.setSernum(rs.getString("SERNUM"));
		postalAdministrationDetailsVO.setBillingSource(rs.getString("BLGSRC"));
		postalAdministrationDetailsVO.setBillingFrequency(rs.getString("BLGFRQ"));
		return postalAdministrationDetailsVO;
	}
}
