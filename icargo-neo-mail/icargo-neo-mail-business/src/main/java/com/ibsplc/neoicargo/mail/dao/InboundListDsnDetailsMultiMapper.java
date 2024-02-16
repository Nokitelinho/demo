package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.vo.DSNVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.currency.Money;

/** 
 * Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.InboundListDsnDetailsMultiMapper.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-8164	:	29-Dec-2018		:	Draft
 */
@Slf4j
public class InboundListDsnDetailsMultiMapper implements MultiMapper<DSNVO> {
	private static final String CLASS_NAME = "InboundListDsnDetailsMultiMapper";

	public List<DSNVO> map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		log.debug(CLASS_NAME + " : " + "map" + " Entering");
		List<DSNVO> dsnvos = new ArrayList<DSNVO>();
		while (rs.next()) {
			DSNVO dsnvo = new DSNVO();
			if (rs.getString("DSN") != null) {
				dsnvo.setDsn(rs.getString("DSN"));
				dsnvo.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
				dsnvo.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
				dsnvo.setMailClass(rs.getString("MALCLS"));
				dsnvo.setMailSubclass(rs.getString("MALSUBCLS"));
				dsnvo.setMailCategoryCode(rs.getString("MALCTGCOD"));
				dsnvo.setYear(rs.getInt("YER"));
				dsnvo.setBags(rs.getInt("DSNACPCNT"));
				dsnvo.setWeight(
						quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("DSNACPWGT"))));
				dsnvo.setReceivedBags(rs.getInt("DSNRCVCNT"));
				dsnvo.setReceivedWeight(
						quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("DSNRCVWGT"))));
				dsnvo.setDeliveredBags(rs.getInt("DSNACPCNT"));
				dsnvo.setDeliveredWeight(
						quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("DSNACPWGT"))));
				dsnvo.setRemarks(rs.getString("RMK"));
				dsnvo.setContainerNumber(rs.getString("CSGDOCNUM"));
				dsnvo.setMasterDocumentNumber(rs.getString("MSTDOCNUM"));
				dsnvo.setDocumentOwnerCode(rs.getString("DOCOWRCOD"));
				dsnvo.setOrigin(rs.getString("ORGCOD"));
				dsnvo.setDestination(rs.getString("DSTCOD"));
				dsnvo.setPaCode(rs.getString("POACOD"));
				dsnvo.setPltEnableFlag(rs.getString("PLTENBFLG"));
				dsnvo.setRoutingAvl(rs.getString("RTGAVL"));
				dsnvos.add(dsnvo);
			}
		}
		return dsnvos;
	}
}
