package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

/** 
 * TODO Add the purpose of this class
 * @author A-1739
 */
public class MailbagPresentMapper implements Mapper<MailbagVO> {
	/** 
	* @param rs
	* @return
	* @throws SQLException
	* @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	*/
	public MailbagVO map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
		mailbagVO.setMailbagId(rs.getString("MALIDR"));
		mailbagVO.setContainerNumber(rs.getString("CONNUM"));
		mailbagVO.setUldNumber(rs.getString("ULDNUM"));
		mailbagVO.setContainerType(rs.getString("CONTYP"));
		mailbagVO.setPol(rs.getString("POL"));
		mailbagVO.setFinalDestination(rs.getString("DST"));
		mailbagVO.setCarrierId(rs.getInt("FLTCARIDR"));
		mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
		mailbagVO.setCarrierCode(rs.getString("FLTCARCOD"));
		mailbagVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		mailbagVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		mailbagVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		mailbagVO.setOperationalStatus(rs.getString("OPRSTA"));
		mailbagVO.setCarrierCode(rs.getString("FLTCARCOD"));
		if (mailbagVO.getFlightSequenceNumber() > 0 && mailbagVO.getPol() != null && rs.getDate("FLTDAT") != null) {
			ZonedDateTime flightDate = localDateUtil.getLocalDate(mailbagVO.getPol(), rs.getDate("FLTDAT"));
			mailbagVO.setFlightDate(localDateUtil.getLocalDate(flightDate.toLocalDate()));
		}
		mailbagVO.setLatestStatus(rs.getString("MALSTA"));
		mailbagVO.setPou(rs.getString("POU"));
		mailbagVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		mailbagVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
		mailbagVO.setPaCode(rs.getString("POACOD"));
		mailbagVO.setTransferFlag(rs.getString("TRAFLG"));
		mailbagVO.setScannedPort(rs.getString("SCNPRT"));
		mailbagVO.setDamageFlag(rs.getString("DMGFLG"));
		mailbagVO.setArrivedFlag(rs.getString("ARRSTA"));
		if (rs.getString("INVULDNUM") != null) {
			if ((rs.getString("INVULDNUM").equals(rs.getString("INVCONNUM")))
					&& !(rs.getString("INVULDNUM").startsWith(MailConstantsVO.CONST_BULK))) {
				mailbagVO.setInventoryContainerType(MailConstantsVO.ULD_TYPE);
			} else {
				mailbagVO.setInventoryContainerType(MailConstantsVO.BULK_TYPE);
			}
		}
		if (rs.getString("INVCONNUM") != null) {
			mailbagVO.setInventoryContainer(rs.getString("INVCONNUM"));
		} else {
			mailbagVO.setInventoryContainer(rs.getString("CONNUM"));
			mailbagVO.setInventoryContainerType(rs.getString("CONTYP"));
		}
		mailbagVO.setFlightStatus(rs.getString("CLSFLG"));
		mailbagVO.setConsignmentDate(localDateUtil.getLocalDate(null, rs.getDate("DSPDAT")));
		return mailbagVO;
	}
}
