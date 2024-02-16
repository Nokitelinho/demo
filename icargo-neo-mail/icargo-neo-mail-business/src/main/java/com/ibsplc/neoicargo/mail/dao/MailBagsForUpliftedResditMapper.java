package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

/** 
 * @author A-1936
 */
public class MailBagsForUpliftedResditMapper implements Mapper<MailbagVO> {
	/** 
	* This class is used to map the ResultSet into the MailBagVo
	* @param rs
	* @return 
	* @throws SQLException
	*/
	public MailbagVO map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		String airport = rs.getString("SCNPRT");
		MailbagVO mailBagVo = new MailbagVO();
		mailBagVo.setCompanyCode(rs.getString("CMPCOD"));
		mailBagVo.setMailbagId(rs.getString("MALIDR"));
		mailBagVo.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		mailBagVo.setScannedPort(airport);
		mailBagVo.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		mailBagVo.setContainerNumber(rs.getString("ULDNUM"));
		mailBagVo.setScannedDate(localDateUtil.getLocalDate(airport, rs.getDate("SCNDAT")));
		mailBagVo.setLatestStatus(rs.getString("MALSTA"));
		mailBagVo.setContainerType(rs.getString("CONTYP"));
		mailBagVo.setPou(rs.getString("POU"));
		mailBagVo.setDoe(rs.getString("DSTEXGOFC"));
		mailBagVo.setOoe(rs.getString("ORGEXGOFC"));
		mailBagVo.setMailCategoryCode(rs.getString("MALCTG"));
		mailBagVo.setMailSubclass(rs.getString("MALSUBCLS"));
		mailBagVo.setOrigin(rs.getString("ORGCOD"));
		mailBagVo.setDestination(rs.getString("DSTCOD"));
		mailBagVo.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		mailBagVo.setPaCode(rs.getString("POACOD"));
		mailBagVo.setCarrierCode(rs.getString("FLTCARCOD"));
		return mailBagVo;
	}
}
