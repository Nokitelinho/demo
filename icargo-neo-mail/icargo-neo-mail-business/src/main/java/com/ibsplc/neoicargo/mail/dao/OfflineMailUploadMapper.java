package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import org.apache.poi.ss.usermodel.DateUtil;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailUploadVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** 
 * Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.OfflineMailUploadMapper.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-5526	:	May 3, 2017	:	Draft
 */
public class OfflineMailUploadMapper implements Mapper<MailUploadVO> {
	private static final String BLANKSPACE = "";

	public MailUploadVO map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ContextUtil contextUtil = ContextUtil.getInstance();
		MailUploadVO mailUploadVO = new MailUploadVO();
		String EMPTY_STRING = "";
		LoginProfile logonAttributes = null;
		try {
			logonAttributes = contextUtil.callerLoginProfile();
		} finally {
		}
		mailUploadVO.setScannedPort(logonAttributes.getAirportCode());
		mailUploadVO.setCompanyCode(rs.getString("CMPCOD"));
		String uldType = rs.getString("REF001");
		if (uldType != null) {
			if ("ULD".equalsIgnoreCase(uldType.toUpperCase())) {
				mailUploadVO.setContainerType(MailConstantsVO.ULD_TYPE);
			} else {
				mailUploadVO.setContainerType(MailConstantsVO.BULK_TYPE);
			}
		}
		mailUploadVO.setContainerNumber(rs.getString("REF002"));
		mailUploadVO.setMailTag(rs.getString("REF003"));
		mailUploadVO.setContainerPol(rs.getString("REF004"));
		mailUploadVO.setContainerPOU(rs.getString("REF005"));
		String flightNumberWithCarrierCode = "";
		String date = "";
		String fileType = rs.getString("FILTYP");
		if ("MALINBOUND".equals(fileType)) {
			flightNumberWithCarrierCode = rs.getString("REF006");
			date = rs.getString("REF007");
			String deliverFlag = rs.getString("REF008");
			if (deliverFlag != null) {
				if ("N".equalsIgnoreCase(deliverFlag)) {
					mailUploadVO.setScanType(MailConstantsVO.MAIL_STATUS_ARRIVED);
					mailUploadVO.setDeliverFlag(deliverFlag);
				} else {
					mailUploadVO.setScanType(MailConstantsVO.MAIL_STATUS_ARRIVED);
					mailUploadVO.setDeliverd(true);
					mailUploadVO.setDeliverFlag(deliverFlag);
				}
			} else {
				mailUploadVO.setDeliverFlag(deliverFlag);
			}
		} else {
			mailUploadVO.setDestination(rs.getString("REF006"));
			mailUploadVO.setScanType(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			flightNumberWithCarrierCode = rs.getString("REF007");
			date = rs.getString("REF008");
		}
		mailUploadVO.setMailCompanyCode(rs.getString("REF009"));
		if (rs.getString("REF010") != null && MailConstantsVO.YES.equals(rs.getString("REF010"))) {
			mailUploadVO.setPaCode(MailConstantsVO.FLAG_YES);
		} else {
			mailUploadVO.setPaCode(MailConstantsVO.FLAG_NO);
		}
		if (rs.getString("REF005") != null && rs.getString("REF005").trim().length() > 0) {
			mailUploadVO.setContainerPOU(rs.getString("REF005"));
		}
		String[] splitData = null;
		if (flightNumberWithCarrierCode != null && flightNumberWithCarrierCode.contains("-")) {
			splitData = flightNumberWithCarrierCode.split("-");
			String flightNo = "";
			mailUploadVO.setFromCarrierCode(splitData[0].toUpperCase());
			try {
				if (splitData[1].toString() != null && !BLANKSPACE.equalsIgnoreCase(splitData[1].toString())) {
					flightNo = validateFlightNumberFormat(splitData[1].toString());
				}
			} finally {
			}
			mailUploadVO.setCarrierCode(splitData[0].toUpperCase());
			mailUploadVO.setFlightNumber(flightNo);
		} else if (flightNumberWithCarrierCode != null) {
			mailUploadVO.setFromCarrierCode(flightNumberWithCarrierCode.substring(0, 2).toUpperCase());
			mailUploadVO.setCarrierCode(flightNumberWithCarrierCode.substring(0, 2).toUpperCase());
			String flightNumber = flightNumberWithCarrierCode.substring(2, flightNumberWithCarrierCode.length());
			String flightNo = "";
			try {
				if (flightNumber != null && !BLANKSPACE.equalsIgnoreCase(flightNumber)) {
					flightNo = validateFlightNumberFormat(flightNumber);
				}
			} finally {
			}
			mailUploadVO.setFlightNumber(flightNo);
		} else {
			mailUploadVO.setFromCarrierCode(flightNumberWithCarrierCode);
			mailUploadVO.setFlightNumber("");
		}
		if (mailUploadVO.getDestination() == null && mailUploadVO.getContainerPOU() != null) {
			mailUploadVO.setDestination(mailUploadVO.getContainerPOU());
		}
		mailUploadVO.setRemarks(EMPTY_STRING);
		mailUploadVO.setDamageCode(EMPTY_STRING);
		mailUploadVO.setDamageRemarks(EMPTY_STRING);
		mailUploadVO.setOffloadReason(EMPTY_STRING);
		mailUploadVO.setReturnCode(EMPTY_STRING);
		mailUploadVO.setToContainer(EMPTY_STRING);
		mailUploadVO.setToCarrierCode(EMPTY_STRING);
		mailUploadVO.setToFlightNumber(EMPTY_STRING);
		if (mailUploadVO.getContainerPOU() != null) {
			mailUploadVO.setToPOU(mailUploadVO.getContainerPOU());
			mailUploadVO.setToDestination(mailUploadVO.getContainerPOU());
		} else {
			if (mailUploadVO.getMailTag() != null && mailUploadVO.getMailTag().trim().length() == 29) {
				mailUploadVO.setToPOU(mailUploadVO.getMailTag().substring(8, 11));
				mailUploadVO.setToDestination(mailUploadVO.getMailTag().substring(8, 11));
			}
		}
		if (mailUploadVO.getMailTag() != null && mailUploadVO.getMailTag().trim().length() == 29) {
			mailUploadVO.setOrginOE(mailUploadVO.getMailTag().substring(0, 6));
			mailUploadVO.setDestinationOE(mailUploadVO.getMailTag().substring(6, 12));
			mailUploadVO.setCategory(mailUploadVO.getMailTag().substring(12, 13));
			mailUploadVO.setSubClass(mailUploadVO.getMailTag().substring(13, 15));
			mailUploadVO.setYear(Integer.parseInt(mailUploadVO.getMailTag().substring(15, 16)));
		}
		mailUploadVO.setScanUser("EXCELUPL");
		mailUploadVO.setConsignmentDocumentNumber(EMPTY_STRING);
		mailUploadVO.setCirCode(EMPTY_STRING);
		mailUploadVO.setFromCarrierCode(EMPTY_STRING);
		mailUploadVO.setMailSource("EXCELUPL");
		ZonedDateTime dateTime = localDateUtil.getLocalDate(logonAttributes.getAirportCode(), true);
		mailUploadVO.setDateTime(dateTime.format(DateTimeFormatter.ofPattern(LocalDate.DATE_TIME_FORMAT)));
		if (date != null) {
			ZonedDateTime flightDate = localDateUtil.getLocalDate(null, false);
			java.text.DateFormat formatter = new java.text.SimpleDateFormat("dd-MMM-yyyy");
			Date parseDate = null;
			try {
				parseDate = formatter.parse(date);
			} catch (ParseException e) {
				parseDate = DateUtil.getJavaDate(Double.parseDouble(date));
			}
			//TODO: Below code to be verified in Neo
			flightDate = new LocalDate().getLocalDate(null, parseDate);
			mailUploadVO.setFlightDate(flightDate);
		}
		return mailUploadVO;
	}

	/** 
	* @@author A-7604 for 290115validateFlightNumberFormat
	* @param flight
	* @return String
	* @throws SystemException
	*/
	public String validateFlightNumberFormat(String flight) {
		StringBuilder flightnumber = new StringBuilder();
		if (flight != null && flight.length() == 1) {
			flightnumber = flightnumber.append("000").append(flight);
		} else if (flight.length() == 2) {
			flightnumber = flightnumber.append("00").append(flight);
		} else if (flight.length() == 3) {
			flightnumber = flightnumber.append("0").append(flight);
		} else {
			return flight;
		}
		return flightnumber.toString();
	}
}
