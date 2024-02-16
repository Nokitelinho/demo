package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.vo.DSNVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import com.ibsplc.neoicargo.framework.util.currency.Money;

/** 
 * Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.CarditDsnEnquiryMapper.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-8164	:	21-Oct-2019		:	Draft
 */
@Slf4j
public class CarditDsnEnquiryMapper implements MultiMapper<DSNVO> {
	public ArrayList<DSNVO> map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("CarditDsnEnquiryMapper" + " : " + "map" + " Entering");
		ArrayList<DSNVO> dsnVos = new ArrayList<>();
		while (rs.next()) {
			DSNVO dsnvo = new DSNVO();
			dsnvo.setFlightNumber(rs.getString("FLTNUM"));
			if (rs.getTimestamp("FLTDAT") != null)
				dsnvo.setFlightDate(localDateUtil.getLocalDate(null, rs.getTimestamp("FLTDAT")));
			dsnvo.setCarrierCode(rs.getString("CARCOD"));
			dsnvo.setOriginExchangeOffice(rs.getString("ORGEXGOFF"));
			dsnvo.setDestinationExchangeOffice(rs.getString("DSTEXGOFF"));
			dsnvo.setYear(rs.getInt("DSPYER"));
			dsnvo.setDsn(rs.getString("DSPSRLNUM"));
			dsnvo.setMailCategoryCode(rs.getString("MALCTGCOD"));
			dsnvo.setMailSubclass(rs.getString("MALSUBCLS"));
			dsnvo.setBags(rs.getInt("MALCNT"));
			dsnvo.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("RCPWGT"))));
			dsnvo.setCompanyCode(rs.getString("CMPCOD"));
			dsnvo.setShipmentCode(rs.getString("SHPPFX"));
			dsnvo.setMasterDocumentNumber(rs.getString("MSTDOCNUM"));
			dsnvo.setCsgDocNum(rs.getString("CSGDOCNUM"));
			if (rs.getTimestamp("CSGDAT") != null)
				dsnvo.setConsignmentDate(localDateUtil.getLocalDate(null, rs.getTimestamp("CSGDAT")));
			dsnvo.setPaCode(rs.getString("POACOD"));
			dsnvo.setContainerNumber(rs.getString("CONNUM"));
			dsnvo.setAcceptanceStatus(rs.getString("ACPSTA"));
			if (rs.getTimestamp("REQDLVTIM") != null)
				dsnvo.setReqDeliveryTime(localDateUtil.getLocalDate(null, rs.getTimestamp("REQDLVTIM")));
			dsnVos.add(dsnvo);
		}
		return dsnVos;
	}
}
