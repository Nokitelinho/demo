package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.MailArrivalVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;

/** 
 * Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.ListManifestDetailsMultiMapper.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-8164	:	28-Dec-2018		:	Draft
 */
@Slf4j
public class ListManifestDetailsMultiMapper implements MultiMapper<MailArrivalVO> {
	private static final String CLASS_NAME = "ListManifestDetailsMultiMapper";
	Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);

	public ArrayList<MailArrivalVO> map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug(CLASS_NAME + " : " + "map" + " Entering");
		ArrayList<MailArrivalVO> mailArrivalVOs = new ArrayList<MailArrivalVO>();
		while (rs.next()) {
			MailArrivalVO mailArrivalVO = new MailArrivalVO();
			String manifestInfo = null;
			if (null != rs.getString("CMPCOD")) {
				mailArrivalVO.setFlightCarrierCode(rs.getString("CMPCOD"));
			}
			if (null != rs.getString("FLTNUM")) {
				mailArrivalVO.setFlightNumber(rs.getString("FLTNUM"));
			}
			if (0 != rs.getInt("FLTSEQNUM")) {
				mailArrivalVO.setFlightSequenceNumber((rs.getInt("FLTSEQNUM")));
			}
			if (0 != rs.getInt("FLTCARIDR")) {
				mailArrivalVO.setCarrierId((rs.getInt("FLTCARIDR")));
			}
			if (0 != rs.getInt("SEGSERNUM")) {
				mailArrivalVO.setSegmentSerialNumber(rs.getInt("LEGSERNUM"));
			}
			if (null != rs.getString("LEGORG")) {
				mailArrivalVO.setPol(rs.getString("LEGORG"));
			}
			if (null != rs.getDate("FLTDAT")) {
				mailArrivalVO.setFlightDate(localDateUtil.getLocalDate(NO_STATION, rs.getDate("FLTDAT")));
			}
			if (null != rs.getString("CONNUM")) {
				manifestInfo = new StringBuffer().append(rs.getString(MailConstantsVO.CONCOUNT)).append("-")
						.append(rs.getString("CONNUM")).append("(").append(rs.getInt("MALCOUNT")).append("/")
						.append(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("TOTWGT"))).getRoundedDisplayValue()
								.doubleValue())
						.append(")").toString();
				mailArrivalVO.setManifestInfo(manifestInfo);
			}
			if (null != rs.getString(MailConstantsVO.CONCOUNT)) {
				mailArrivalVO.setContainerCount(Double.parseDouble(rs.getString(MailConstantsVO.CONCOUNT)));
			}
			if (null != rs.getString("TOTWGT")) {
				mailArrivalVO.setTotalWeight(Double.parseDouble(rs.getString("TOTWGT")));
			}
			if (null != rs.getString(MailConstantsVO.CONCOUNT)) {
				mailArrivalVO.setMailCount(Double.parseDouble(rs.getString(MailConstantsVO.CONCOUNT)));
			}
			if (null != rs.getString("MALCOUNT")) {
				mailArrivalVO.setMailCount(Double.parseDouble(rs.getString("MALCOUNT")));
			}
			mailArrivalVOs.add(mailArrivalVO);
		}
		return mailArrivalVOs;
	}
}
