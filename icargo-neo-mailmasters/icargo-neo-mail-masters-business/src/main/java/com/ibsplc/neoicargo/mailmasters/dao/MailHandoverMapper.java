package com.ibsplc.neoicargo.mailmasters.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.neoicargo.mailmasters.vo.MailHandoverVO;
import lombok.extern.slf4j.Slf4j;

/** 
 * @author A-6986
 */
@Slf4j
public class MailHandoverMapper implements Mapper<MailHandoverVO> {
	private static final String CLASS_NAME = "MailHandoverMapper";

	public MailHandoverVO map(ResultSet rs) throws SQLException {
		log.debug(CLASS_NAME + " : " + "map" + " Entering");
		MailHandoverVO mailHandoverVO = new MailHandoverVO();
		mailHandoverVO.setCompanyCode(rs.getString("CMPCOD"));
		mailHandoverVO.setHoAirportCodes(rs.getString("ARPCOD"));
		mailHandoverVO.setGpaCode(rs.getString("GPACOD"));
		if (rs.getString("MALCLS") != null) {
			mailHandoverVO.setMailClass(rs.getString("MALCLS"));
		} else {
			mailHandoverVO.setMailClass("");
		}
		mailHandoverVO.setExchangeOffice(rs.getString("EXGOFC"));
		mailHandoverVO.setHandoverTimes(rs.getString("HNDTIM"));
		mailHandoverVO.setMailSubClass(rs.getString("MALSUBCLS"));
		mailHandoverVO.setSerialNumber(rs.getInt("SERNUM"));
		return mailHandoverVO;
	}
}
