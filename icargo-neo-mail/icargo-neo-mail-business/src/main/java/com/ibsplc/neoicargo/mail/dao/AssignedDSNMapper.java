package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.vo.DSNEnquiryFilterVO;
import com.ibsplc.neoicargo.mail.vo.DespatchDetailsVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import com.ibsplc.neoicargo.framework.util.currency.Money;

/** 
 */
@Slf4j
public class AssignedDSNMapper implements Mapper<DespatchDetailsVO> {
	private DSNEnquiryFilterVO dSNEnquiryFilterVO;

	/** 
	* @param dSNEnquiryFilterVO
	*/
	public AssignedDSNMapper(DSNEnquiryFilterVO dSNEnquiryFilterVO) {
		super();
		this.dSNEnquiryFilterVO = dSNEnquiryFilterVO;
	}

	/** 
	* Constructor
	*/
	public AssignedDSNMapper() {
		super();
	}

	/** 
	* @param rs
	* @return
	* @throws SQLException
	*/
	public DespatchDetailsVO map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		String airport = rs.getString("ARPCOD");
		log.debug("inside mapper==&&&>");
		DespatchDetailsVO despatchDetailsVO = new DespatchDetailsVO();
		despatchDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		despatchDetailsVO.setOriginOfficeOfExchange(rs.getString("ORGEXGOFC"));
		despatchDetailsVO.setDestinationOfficeOfExchange(rs.getString("DSTEXGOFC"));
		despatchDetailsVO.setMailClass(rs.getString("MALCLS"));
		despatchDetailsVO.setMailSubclass(rs.getString("MALSUBCLS"));
		despatchDetailsVO.setYear(rs.getInt("YER"));
		despatchDetailsVO.setDsn(rs.getString("DSN"));
		despatchDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		despatchDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		despatchDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		despatchDetailsVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		despatchDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		despatchDetailsVO.setContainerNumber(rs.getString("CONNUM"));
		despatchDetailsVO.setUldNumber(rs.getString("ULDNUM"));
		despatchDetailsVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		despatchDetailsVO.setAcceptedBags(rs.getInt("ACPBAG"));
		despatchDetailsVO.setAcceptedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ACPWGT"))));
		despatchDetailsVO.setReceivedBags(rs.getInt("RCVBAG"));
		despatchDetailsVO.setReceivedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("RCVWGT"))));
		despatchDetailsVO.setPrevReceivedBags(rs.getInt("RCVBAG"));
		despatchDetailsVO.setDeliveredWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("DLVWGT"))));
		despatchDetailsVO.setDeliveredBags(rs.getInt("DLVBAG"));
		despatchDetailsVO.setPrevReceivedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("RCVWGT"))));
		despatchDetailsVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		despatchDetailsVO.setAirportCode(airport);
		despatchDetailsVO.setCarrierCode(rs.getString("FLTCARCOD"));
		despatchDetailsVO.setContainerType(rs.getString("CONTYP"));
		despatchDetailsVO.setDestination(rs.getString("DSTCOD"));
		despatchDetailsVO.setCapNotAcceptedStatus(rs.getString("DSNSTA"));
		despatchDetailsVO.setDelivered("Y".equals(rs.getString("DLVFLG")) ? true : false);
		despatchDetailsVO.setPltEnabledFlag("Y".equals(rs.getString("PLTENBFLG")) ? "true" : "false");
		despatchDetailsVO.setCsgOrigin(rs.getString("ORGARPCOD"));
		despatchDetailsVO.setCsgDestination(rs.getString("DSTARPCOD"));
		if (rs.getString("POU") != null) {
			despatchDetailsVO.setPou(rs.getString("POU"));
		} else if (rs.getString("DSTCOD") != null) {
			despatchDetailsVO.setPou(rs.getString("DSTCOD"));
		} else {
			despatchDetailsVO.setPou(airport);
		}
		if (rs.getTimestamp("FLTDAT") != null && airport != null && airport.trim().length() > 0) {
			despatchDetailsVO.setFlightDate(localDateUtil.getLocalDate(airport, rs.getDate("FLTDAT")));
		}
		despatchDetailsVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
		despatchDetailsVO.setPaCode(rs.getString("POACOD"));
		if (rs.getTimestamp("CSGDAT") != null && airport != null && airport.trim().length() > 0) {
			despatchDetailsVO.setConsignmentDate(localDateUtil.getLocalDate(airport, rs.getTimestamp("CSGDAT")));
		}
		if (rs.getDate("ACPDAT") != null && airport != null && airport.trim().length() > 0) {
			despatchDetailsVO.setAcceptedDate(localDateUtil.getLocalDate(airport, rs.getDate("ACPDAT")));
		}
		if (rs.getDate("RCVDAT") != null && airport != null && airport.trim().length() > 0) {
			despatchDetailsVO.setReceivedDate(localDateUtil.getLocalDate(airport, rs.getDate("RCVDAT")));
		}
		despatchDetailsVO.setStatedBags(rs.getInt("STDBAG"));
		despatchDetailsVO.setStatedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("STDWGT"))));
		despatchDetailsVO.setAcceptedUser(rs.getString("ACPUSR"));
		if (dSNEnquiryFilterVO != null && dSNEnquiryFilterVO.getOperationType() != null) {
			despatchDetailsVO.setOperationType(dSNEnquiryFilterVO.getOperationType());
		}
		despatchDetailsVO.setOffloadFlag(rs.getString("OFLFLG"));
		despatchDetailsVO.setTransferFlag(rs.getString("TRAFLG"));
		despatchDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
		despatchDetailsVO.setRemarks(rs.getString("RMKS"));
		despatchDetailsVO.setTransferredPieces(rs.getInt("TRFBAG"));
		despatchDetailsVO.setTransferredWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("TRFWGT"))));
		despatchDetailsVO.setAlreadyTransferredPieces(rs.getInt("TRFBAG"));
		if (rs.getString("LATSTA") != null) {
			despatchDetailsVO.setLatestStatus(rs.getString("LATSTA"));
		}
		if (rs.getString("OFLBAG") != null) {
			despatchDetailsVO.setOffloadedBags(rs.getInt("OFLBAG"));
		}
		if (rs.getString("OFLWGT") != null) {
			despatchDetailsVO.setOffloadedWeight(
					quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("OFLWGT"))));
		}
		despatchDetailsVO.setUbrNumber(rs.getString("UBRNUM"));
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
				despatchDetailsVO.setBookingLastUpdateTime(bookingLastUpdateTime);
				despatchDetailsVO.setBookingFlightDetailLastUpdTime(bookingLastUpdateTime);
			}
		}
		return despatchDetailsVO;
	}
}
