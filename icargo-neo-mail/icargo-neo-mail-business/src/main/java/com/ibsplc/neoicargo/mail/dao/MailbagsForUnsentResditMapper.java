package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/** 
 * @author A-1936The Class tat is used to map all the ResultSet into Vo 
 */
public class MailbagsForUnsentResditMapper implements Mapper<MailbagVO> {

	LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);

	/** 
	* @author A-1936This method is used to map the Result set into MailBagVo
	* @param rs
	* @return
	* @throws SQLException
	*/
	public MailbagVO map(ResultSet rs) throws SQLException {
		MailbagVO mailBagVo = new MailbagVO();
		mailBagVo.setCompanyCode(rs.getString("CMPCOD"));
		mailBagVo.setOoe(rs.getString("ORGEXGOFC"));
		mailBagVo.setDoe(rs.getString("DSTEXGOFC"));
		mailBagVo.setMailSubclass(rs.getString("MALSUBCLS"));
		mailBagVo.setMailCategoryCode(rs.getString("MALCTG"));
		mailBagVo.setMailbagId(rs.getString("MALIDR"));
		mailBagVo.setDespatchSerialNumber(rs.getString("DSN"));
		mailBagVo.setYear(rs.getInt("YER"));
		mailBagVo.setFlightNumber(rs.getString("FLTNUM"));
		mailBagVo.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
		mailBagVo.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		mailBagVo.setCarrierId(rs.getInt("FLTCARIDR"));
		mailBagVo.setContainerNumber(rs.getString("CONNUM"));
		mailBagVo.setResditEventPort(rs.getString("EVTPRT"));
		mailBagVo.setResditEventString(rs.getString("EVTCOD"));
		mailBagVo.setResditEventSeqNum(rs.getInt("MSGSEQNUM"));
		if (rs.getTimestamp("EVTDATUTC") != null) {
			mailBagVo.setResditEventUTCDate(
					localDateUtil.getLocalDateTimeForZone(rs.getTimestamp("EVTDATUTC").toLocalDateTime(), "GMT"));

		}
		mailBagVo.setCarditKey(rs.getString("CDTKEY"));
		mailBagVo.setHandoverPartner(rs.getString("POACARCOD"));
		mailBagVo.setCarditRecipientId(rs.getString("RCTIDR"));
		mailBagVo.setPaCode(rs.getString("SDRIDR"));
		mailBagVo.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		mailBagVo.setCarrierCode(rs.getString("TWOAPHCOD"));
		return mailBagVo;
	}
}
