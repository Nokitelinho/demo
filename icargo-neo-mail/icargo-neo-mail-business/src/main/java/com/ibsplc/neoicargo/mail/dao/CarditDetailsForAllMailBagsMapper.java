package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import lombok.extern.slf4j.Slf4j;

/** 
 * @author A-1936 This class is used to map the Resultset into MailBagvo
 */
@Slf4j
public class CarditDetailsForAllMailBagsMapper implements Mapper<MailbagVO> {
	/** 
	* @author A-1936
	* @param rs
	* @throws SQLException
	*/
	public MailbagVO map(ResultSet rs) throws SQLException {
		log.debug("CarditDetailsForAllMailBagsMapper" + " : " + "Map Method" + " Entering");
		MailbagVO mailBagVo = new MailbagVO();
		mailBagVo.setCompanyCode(rs.getString("CMPCOD"));
		mailBagVo.setCarditKey(rs.getString("CDTKEY"));
		mailBagVo.setCarditSequenceNumber(rs.getInt("CDTSEQNUM"));
		mailBagVo.setStationCode(rs.getString("STNCOD"));
		mailBagVo.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		return mailBagVo;
	}
}
