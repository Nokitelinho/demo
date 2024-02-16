
package com.ibsplc.icargo.persistence.dao.addons.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.time.DateUtilities;

public class MailBookingMapper implements MultiMapper<MailBookingDetailVO> {
	private static final String FLTNUM = "FLTNUM";
	private static final String FLTCARCOD = "FLTCARCOD";
	private static final String FLTCARIDR = "FLTCARIDR";
	private static final String FLTSEQNUM = "FLTSEQNUM";
	private static final String FLTORG = "FLTORG";
	private static final String FLTDST = "FLTDST";
	private static final String STNCOD = "STNCOD";
	private static final String FLTDAT = "FLTDAT";
	private static final String DISPLAY_FORMAT = "dd-MMM-yyyy";

	public List<MailBookingDetailVO> map(ResultSet rs) throws SQLException {

		Collection<MailBookingDetailVO> mailBookingDetailVOs = new ArrayList<>();
		HashMap<String, MailBookingDetailVO> mailBookingMap = new HashMap<>();
		String key = null;
		MailBookingDetailVO bookedFlight = null;
		Collection<MailBookingDetailVO> bookedFlights = null;
		while (rs.next()) {
			key = new StringBuilder().append(rs.getString("SHPPFX")).append(",").append(rs.getString("MSTDOCNUM"))
					.toString();

			if (mailBookingMap.containsKey(key)) {
				bookedFlight = new MailBookingDetailVO();
				bookedFlight.setBookingFlightNumber(rs.getString(FLTNUM));
				bookedFlight.setBookingCarrierCode(rs.getString(FLTCARCOD));
				bookedFlight.setBookingFlightCarrierid(rs.getInt(FLTCARIDR));
				bookedFlight.setBookingFlightSequenceNumber(rs.getInt(FLTSEQNUM));
				bookedFlight.setOrigin(rs.getString(FLTORG));
				bookedFlight.setDestination(rs.getString(FLTDST));
				if (bookedFlights != null && !bookedFlights.isEmpty()) {
					bookedFlights.add(bookedFlight);
				}
				MailBookingDetailVO mailBookingNewVO = null;
				mailBookingNewVO = mailBookingMap.get(key);
				String carrierCode = mailBookingNewVO.getBookingCarrierCode();
				String firstDate = mailBookingNewVO.getBookingFlightDate().toDisplayFormat(DISPLAY_FORMAT);
				String secondDate = new LocalDate(rs.getString(STNCOD), Location.STN, rs.getTimestamp(FLTDAT))
						.toDisplayFormat(DISPLAY_FORMAT);
				String nextFlightNum = rs.getString(FLTNUM);
				if (carrierCode != null && carrierCode.trim().length() > 0) {
					populateMailBookingDetailVO(mailBookingNewVO, firstDate, secondDate, nextFlightNum, rs);
				}
			} else {
				bookedFlights = new ArrayList<>();
				bookedFlight = new MailBookingDetailVO();

				bookedFlight.setBookingFlightNumber(rs.getString(FLTNUM));
				bookedFlight.setBookingCarrierCode(rs.getString(FLTCARCOD));
				bookedFlight.setBookingFlightCarrierid(rs.getInt(FLTCARIDR));
				bookedFlight.setBookingFlightSequenceNumber(rs.getInt(FLTSEQNUM));
				bookedFlight.setOrigin(rs.getString(FLTORG));
				bookedFlight.setDestination(rs.getString(FLTDST));

				bookedFlights.add(bookedFlight);
				MailBookingDetailVO mailBookingFlightDetailVO = new MailBookingDetailVO();

				mailBookingFlightDetailVO.setBookedFlights(bookedFlights);
				if ("Y".equals(rs.getString("SPLIND"))) {
					mailBookingFlightDetailVO.setSplitBooking(true);
				}
				populateMailBookingFlightDetailVO(mailBookingFlightDetailVO, rs);
				mailBookingDetailVOs.add(mailBookingFlightDetailVO);
				mailBookingMap.put(key, mailBookingFlightDetailVO);
			}
		}
		return (List<MailBookingDetailVO>) mailBookingDetailVOs;
	}

	private void populateMailBookingDetailVO(MailBookingDetailVO mailBookingNewVO, String firstDate, String secondDate,
			String nextFlightNum, ResultSet rs) throws SQLException {
		String newFlight = "";
		if (mailBookingNewVO.getBookingFlightDate() != null
				&& DateUtilities.isGreaterThan(firstDate, secondDate, DISPLAY_FORMAT)) {
			mailBookingNewVO
					.setBookingFlightDate(new LocalDate(rs.getString(STNCOD), Location.STN, rs.getTimestamp(FLTDAT)));
			mailBookingNewVO.setBookingFlightNumber(rs.getString(FLTNUM));
			mailBookingNewVO.setBookingCarrierCode(rs.getString(FLTCARCOD));
			mailBookingNewVO.setBookingFlightCarrierid(rs.getInt(FLTCARIDR));
			mailBookingNewVO.setBookingFlightSequenceNumber(rs.getInt(FLTSEQNUM));

		}
		if (nextFlightNum != null && nextFlightNum.trim().length() > 0) {
			newFlight = new StringBuilder().append(mailBookingNewVO.getSelectedFlightNumber()).append(",")
					.append(nextFlightNum).append(" ").append(secondDate).append(" ").append(rs.getString(FLTCARCOD))
					.append(" ").append(rs.getInt(FLTCARIDR)).append(" ").append(rs.getInt(FLTSEQNUM)).append(" ")
					.append(rs.getString(FLTORG)).append(" ").append(rs.getString(FLTDST)).toString();
		} else {
			newFlight = new StringBuilder().append(mailBookingNewVO.getSelectedFlightNumber()).append(",").append(" ")
					.append(secondDate).append(" ").append(rs.getString(FLTCARCOD)).append(" ")
					.append(rs.getInt(FLTCARIDR)).append(" ").append(rs.getInt(FLTSEQNUM)).append(" ")
					.append(rs.getString(FLTORG)).append(" ").append(rs.getString(FLTDST)).toString();

		}
		mailBookingNewVO.setSelectedFlightNumber(newFlight);
	}

	private MailBookingDetailVO populateMailBookingFlightDetailVO(MailBookingDetailVO mailBookingFlightDetailVO,
			ResultSet rs) throws SQLException {
		String firstFlightDetails = "";
		mailBookingFlightDetailVO.setCompanyCode(rs.getString("CMPCOD"));
		mailBookingFlightDetailVO.setShipmentPrefix(rs.getString("SHPPFX"));
		mailBookingFlightDetailVO.setOwnerId(rs.getInt("DOCOWRIDR"));
		mailBookingFlightDetailVO.setDuplicateNumber(rs.getInt("DUPNUM"));
		mailBookingFlightDetailVO.setMasterDocumentNumber(rs.getString("MSTDOCNUM"));
		mailBookingFlightDetailVO.setSequenceNumber(rs.getInt("SEQNUM"));
		mailBookingFlightDetailVO.setAwbOrgin(rs.getString("ORGCOD"));
		mailBookingFlightDetailVO.setAwbDestination(rs.getString("DSTCOD"));
		mailBookingFlightDetailVO.setBookingCarrierCode(rs.getString(FLTCARCOD));
		mailBookingFlightDetailVO.setBookingFlightCarrierid(rs.getInt(FLTCARIDR));
		mailBookingFlightDetailVO.setBookingFlightSequenceNumber(rs.getInt(FLTSEQNUM));
		mailBookingFlightDetailVO.setBookingFlightNumber(rs.getString(FLTNUM));
		if (rs.getDate(FLTDAT) != null) {
			mailBookingFlightDetailVO
					.setBookingFlightDate(new LocalDate(rs.getString(STNCOD), Location.STN, rs.getTimestamp(FLTDAT)));
			mailBookingFlightDetailVO.setSelectedFlightDate(mailBookingFlightDetailVO.getBookingFlightDate());
		}
		mailBookingFlightDetailVO.setAgentCode(rs.getString("AGTCOD"));
		mailBookingFlightDetailVO.setMailScc(rs.getString("SCCCOD"));
		if (rs.getDate("SHPDAT") != null) {
			mailBookingFlightDetailVO
					.setShipmentDate(new LocalDate(rs.getString(STNCOD), Location.STN, rs.getTimestamp("SHPDAT")));
		}
		mailBookingFlightDetailVO.setBookedPieces(rs.getInt("TOTNUMPCS"));
		mailBookingFlightDetailVO.setBookedWeight(rs.getDouble("TOTWGT"));
		mailBookingFlightDetailVO.setBookedVolume(rs.getDouble("TOTVOL"));
		mailBookingFlightDetailVO.setBookingStatus(rs.getString("BKGSTA"));
		mailBookingFlightDetailVO.setShipmentStatus(rs.getString("SHPSTA"));
		mailBookingFlightDetailVO.setBookingStation(rs.getString(STNCOD));
		if (rs.getDate("BKGDAT") != null) {
			mailBookingFlightDetailVO
					.setBookingDate(new LocalDate(rs.getString(STNCOD), Location.STN, rs.getTimestamp("BKGDAT")));
		}
		mailBookingFlightDetailVO.setRemarks(rs.getString("SHPRMK"));
		mailBookingFlightDetailVO.setStatedPieces(rs.getInt("STDPCS"));
		if (mailBookingFlightDetailVO.getBookingFlightNumber() != null
				&& mailBookingFlightDetailVO.getBookingFlightNumber().trim().length() > 0) {
			firstFlightDetails = new StringBuilder().append(mailBookingFlightDetailVO.getBookingFlightNumber())
					.append(" ")
					.append(mailBookingFlightDetailVO.getBookingFlightDate().toDisplayFormat(DISPLAY_FORMAT))
					.append(" ").append(rs.getString(FLTCARCOD)).append(" ").append(rs.getInt(FLTCARIDR)).append(" ")
					.append(rs.getInt(FLTSEQNUM)).append(" ").append(rs.getString(FLTORG)).append(" ")
					.append(rs.getString(FLTDST)).toString();
		} else {
			firstFlightDetails = new StringBuilder().append(" ")
					.append(mailBookingFlightDetailVO.getBookingFlightDate().toDisplayFormat(DISPLAY_FORMAT))
					.append(" ").append(rs.getString(FLTCARCOD)).append(" ").append(rs.getInt(FLTCARIDR)).append(" ")
					.append(rs.getInt(FLTSEQNUM)).append(" ").append(rs.getString(FLTORG)).append(" ")
					.append(rs.getString(FLTDST)).toString();
		}

		mailBookingFlightDetailVO.setSelectedFlightNumber(firstFlightDetails);
		return mailBookingFlightDetailVO;
	}
}
