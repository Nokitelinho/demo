package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.vo.MailArrivalVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import com.ibsplc.icargo.framework.util.unit.MeasureMapper;
import com.ibsplc.neoicargo.framework.util.currency.Money;

import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;

/** 
 * Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.ListFlightDetailsMultiMapper.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-8164	:	25-Sep-2018		:	Draft
 */
@Slf4j
public class ListFlightDetailsMultiMapper implements MultiMapper<MailArrivalVO> {
	private static final String CLASS_NAME = "ListFlightDetailsMultiMapper";

	public ArrayList<MailArrivalVO> map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);

		log.debug(CLASS_NAME + " : " + "map" + " Entering");
		ArrayList<MailArrivalVO> mailArrivalVOs = new ArrayList<MailArrivalVO>();
		while (rs.next()) {
			MailArrivalVO mailArrivalVO = new MailArrivalVO();
			String recievedInfo = null;
			if (null != rs.getString("CMPCOD")) {
				mailArrivalVO.setCompanyCode(rs.getString("CMPCOD"));
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
			if (null != rs.getString("FLTCARCOD")) {
				mailArrivalVO.setFlightCarrierCode((rs.getString("FLTCARCOD")));
			}
			if (null != rs.getString("LEGDST")) {
				mailArrivalVO.setAirportCode(rs.getString("LEGDST"));
			}
			if (null != rs.getString("FLTSTA")) {
				mailArrivalVO.setFlightStatus(rs.getString("FLTSTA"));
			}
			if (null != rs.getString("FLTTYP")) {
				mailArrivalVO.setFlightStatus(rs.getString("FLTTYP"));
			}
			if (null != rs.getString("FLTROU")) {
				mailArrivalVO.setRoute(rs.getString("FLTROU"));
			}
			if (0 != rs.getInt("LEGSERNUM")) {
				mailArrivalVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
			}
			if (null != rs.getString("LEGORG")) {
				mailArrivalVO.setPol(rs.getString("LEGORG"));
			}
			if (null != rs.getDate("STA")) {
				mailArrivalVO.setFlightDate(localDateUtil.getLocalDate(null, rs.getTimestamp("STA")));
				mailArrivalVO.setArrivalTimeType(" (S)");
				mailArrivalVO.setArrivalDate(mailArrivalVO.getFlightDate());
			}
			if (null != rs.getDate("ETA")) {
				mailArrivalVO.setFlightDate(localDateUtil.getLocalDate(null, rs.getTimestamp("ETA")));
				mailArrivalVO.setArrivalTimeType(" (E)");
			}
			if (null != rs.getDate("ATA")) {
				mailArrivalVO.setFlightDate(localDateUtil.getLocalDate(null, rs.getTimestamp("ATA")));
				mailArrivalVO.setArrivalTimeType(" (A)");
			}
			if (null != rs.getString("ACRTYP")) {
				mailArrivalVO.setAircraftType(rs.getString("ACRTYP"));
			}
			if (null != rs.getString("ARVGTE")) {
				mailArrivalVO.setGateInfo(rs.getString("ARVGTE"));
			}
			if (null != rs.getString("IMPCLSFLG")) {
				mailArrivalVO.setOperationalStatus(rs.getString("IMPCLSFLG"));
			} else {
				if (rs.getString("TOTACPBAG") != null && rs.getString("TOTRCVBAG") != null) {
					if (Integer.parseInt(rs.getString("TOTACPBAG")) + Integer.parseInt(rs.getString("TOTRCVBAG")) > 0) {
						mailArrivalVO.setOperationalStatus("O");
					}
				} else if (rs.getString("ULDCHK") != null) {
					mailArrivalVO.setOperationalStatus("O");
				} else
					mailArrivalVO.setOperationalStatus("N");
			}
			if (null != rs.getString("FLTSEQNUM")) {
				recievedInfo = new StringBuffer()
						.append(rs.getString("TOTACPBAG") != null ? rs.getString("TOTACPBAG") : '0').append("/")
						.append(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("TOTACPWGT"))).getRoundedDisplayValue()
								.doubleValue())
						.append("-").append(rs.getString("TOTRCVBAG") != null ? rs.getString("TOTRCVBAG") : '0')
						.append("/").append(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("TOTRCVWGT")))
								.getRoundedDisplayValue().doubleValue())
						.toString();
				mailArrivalVO.setRecievedInfo(recievedInfo);
			}
			if (null != rs.getString("ONLARPPAR")) {
				mailArrivalVO.setOnlineAirportParam(rs.getString("ONLARPPAR"));
			}
			mailArrivalVOs.add(mailArrivalVO);
		}
		return mailArrivalVOs;
	}
}
