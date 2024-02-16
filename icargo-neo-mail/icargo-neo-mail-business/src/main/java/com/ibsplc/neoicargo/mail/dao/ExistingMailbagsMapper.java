package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

/** 
 * @author A-2553
 */
public class ExistingMailbagsMapper implements Mapper<MailbagVO> {
	/** 
	* @param rs
	* @return
	* @throws SQLException
	*/
	public MailbagVO map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
		mailbagVO.setMailbagId(rs.getString("MALIDR"));
		mailbagVO.setCarrierId(rs.getInt("FLTCARIDR"));
		mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
		mailbagVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		mailbagVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		mailbagVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		mailbagVO.setCarrierCode(rs.getString("FLTCARCOD"));
		mailbagVO.setFlightStatus(rs.getString("CLSFLG"));
		mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
		mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
		mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
		mailbagVO.setMailClass(rs.getString("MALCLS"));
		mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
		mailbagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		mailbagVO.setYear(rs.getInt("YER"));
		mailbagVO.setContainerNumber(rs.getString("CONNUM"));
		mailbagVO.setContainerType(rs.getString("CONTYP"));
		mailbagVO.setLatestStatus(rs.getString("MALSTA"));
		mailbagVO.setScannedPort(rs.getString("SCNPRT"));
		if (rs.getDate("FLTDAT") != null) {
			mailbagVO.setFlightDate((localDateUtil.getLocalDate(null, rs.getDate("FLTDAT"))));
		}
		mailbagVO.setUbrNumber(rs.getString("UBRNUM"));
		Timestamp bookingUpdateTime = rs.getTimestamp("BKGLSTUPDTIM");
		Timestamp bookingFlightDetailUpdateTime = rs.getTimestamp("BKGFLTUPDTIM");
		ZonedDateTime bookingLastUpdateTime = localDateUtil.getLocalDate(null, true);
		if (bookingUpdateTime != null && bookingFlightDetailUpdateTime != null) {
			if (bookingUpdateTime.after(bookingFlightDetailUpdateTime)) {
				bookingLastUpdateTime = localDateUtil.getLocalDate(null, bookingUpdateTime);
			} else {
				bookingLastUpdateTime = localDateUtil.getLocalDate(null, bookingFlightDetailUpdateTime);
			}
			if (bookingLastUpdateTime != null) {
				mailbagVO.setBookingLastUpdateTime(bookingLastUpdateTime);
				mailbagVO.setBookingFlightDetailLastUpdTime(bookingLastUpdateTime);
			}
		}
		return mailbagVO;
	}
}
