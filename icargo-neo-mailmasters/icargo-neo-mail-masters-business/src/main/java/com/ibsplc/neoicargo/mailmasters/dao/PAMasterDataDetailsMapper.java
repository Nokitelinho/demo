package com.ibsplc.neoicargo.mailmasters.dao;

import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.mailmasters.vo.PostalAdministrationVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

@Slf4j
public class PAMasterDataDetailsMapper implements Mapper<PostalAdministrationVO> {
	private static final String MAILTRACKING_DEFAULTS = "MAILTRACKING_DEFAULTS";

	/** 
	* Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet) Added by 			: 204082 on 27-Sep-2022 Used for 	: Parameters	:	@param rs Parameters	:	@return PostalAdministrationVO Parameters	:	@throws SQLException
	*/
	@Override
	public PostalAdministrationVO map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug(MAILTRACKING_DEFAULTS + " : " + "PAMasterDataDetailsMapper" + " Entering");
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		postalAdministrationVO.setPaCode(rs.getString("POACOD"));
		postalAdministrationVO.setPaName(rs.getString("POANAM"));
		postalAdministrationVO.setLastUpdateTime(localDateUtil.getLocalDate(null, rs.getTimestamp("LSTUPDTIM")));
		log.debug("" + "postalAdministrationVO from mapper++++" + " " + postalAdministrationVO);
		log.debug(MAILTRACKING_DEFAULTS + " : " + "PAMasterDataDetailsMapper" + " Exiting");
		return postalAdministrationVO;
	}
}
