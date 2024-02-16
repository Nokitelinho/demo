/*
 * ULDInvoiceDetailsMapper.java Created on Sep 16, 2009
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDChargingInvoiceVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3278
 *
 */
public class ULDInvoiceDetailsMapper implements Mapper<ULDChargingInvoiceVO> {

	private Log log = LogFactory.getLogger("ULD_DEFAULTS");

	/**
	 * @author a-3278
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public ULDChargingInvoiceVO map(ResultSet rs) throws SQLException {
		log.entering("INSIDE THE MAPPER", "ULDInvoiceDetailsMapper");
		ULDChargingInvoiceVO uldChargingInvoiceVO = new ULDChargingInvoiceVO();
		uldChargingInvoiceVO.setInvoiceRefNumber(rs.getString("INVREFNUM"));
		uldChargingInvoiceVO.setInvoicedToCode(rs.getString("INVCOD"));
		uldChargingInvoiceVO.setInvoicedToCodeName(rs.getString("INVCODNAM"));
		uldChargingInvoiceVO.setCurrencyCode(rs.getString("CURCOD"));
		if (rs.getDate("INVDAT") != null) {
			uldChargingInvoiceVO.setInvoicedDate(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, rs.getDate("INVDAT")));
		}
		uldChargingInvoiceVO.setTransactionType(rs.getString("TXNTYP"));
		if (rs.getString("CRN") != null) {
			uldChargingInvoiceVO.setControlRecieptNumber(rs.getString("CRN"));
		}
		if (rs.getDate("TXNDAT") != null) {
			uldChargingInvoiceVO.setDate1(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, rs.getDate("TXNDAT")));
		}
		if (rs.getDate("RTNDAT") != null) {
			uldChargingInvoiceVO.setDate2(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, rs.getDate("RTNDAT")));
		}
		if (rs.getString("PTYTYP") != null) {
			uldChargingInvoiceVO.setPartyType(rs.getString("PTYTYP"));
		}
		if (rs.getString("ULDTYP") != null) {
			uldChargingInvoiceVO.setUldType(rs.getString("ULDTYP"));
		}
		//Added as a part of CR HA138-1
		if (rs.getString("INTFCDFLG") != null) {
			uldChargingInvoiceVO.setInterfacedFlag(rs.getString("INTFCDFLG"));
		}
		//Added as a part of CR HA138-1 ends
		uldChargingInvoiceVO.setDemurrageAmount(rs.getDouble("TOTAMT"));
		uldChargingInvoiceVO.setLocation(rs.getString("TXNARPCOD"));
		uldChargingInvoiceVO.setUldNumber(rs.getString("ULDNUM"));

		return uldChargingInvoiceVO;

	}

}
