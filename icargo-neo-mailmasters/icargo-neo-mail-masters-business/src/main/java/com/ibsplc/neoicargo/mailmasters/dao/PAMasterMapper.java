package com.ibsplc.neoicargo.mailmasters.dao;

import com.ibsplc.neoicargo.mailmasters.vo.PostalAdministrationVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class PAMasterMapper implements Mapper<PostalAdministrationVO> {

	/** 
	* Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet) Added by 			: 204082 on 27-Sep-2022 Used for 	: Parameters	:	@param rs Parameters	:	@return PostalAdministrationVO Parameters	:	@throws SQLException
	*/
	@Override
	public PostalAdministrationVO map(ResultSet rs) throws SQLException {
		PostalAdministrationVO postalAdminVO = new PostalAdministrationVO();
		postalAdminVO.setPaCode(rs.getString("POACOD"));
		postalAdminVO.setBasisType(rs.getString("BASTYP"));
		return postalAdminVO;
	}
}
