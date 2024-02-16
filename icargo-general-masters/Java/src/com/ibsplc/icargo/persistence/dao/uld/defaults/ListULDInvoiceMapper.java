/*
 * ListULDInvoiceMapper.java Created on Jan 5, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDChargingInvoiceVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1883
 * This mapper is used for listing of Uld Charging Invoice
 */
public class ListULDInvoiceMapper implements Mapper<ULDChargingInvoiceVO> {
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	/**
	 * @param resultSet
	 * @return ULDChargingInvoiceVO
	 * @throws SQLException
	 */
public ULDChargingInvoiceVO map(ResultSet resultSet) throws SQLException {
		log.entering("ListULDInvoiceMapper","Map");
		ULDChargingInvoiceVO uLDChargingInvoiceVO = new ULDChargingInvoiceVO();
		uLDChargingInvoiceVO.setInvoiceRefNumber(
				resultSet.getString("INVREFNUM"));
		uLDChargingInvoiceVO.setTransactionType(resultSet.getString("TXNTYP"));
		Date date = resultSet.getDate("INVDAT");
		if(date != null){
			uLDChargingInvoiceVO.setInvoicedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,date));
		}
		uLDChargingInvoiceVO.setInvoicedToCode(resultSet.getString("INVCOD"));
		uLDChargingInvoiceVO.setInvoicedToCodeName(
				resultSet.getString("INVCODNAM"));
		uLDChargingInvoiceVO.setWaiverAmount(resultSet.getDouble("WVRAMT"));
		uLDChargingInvoiceVO.setTotalAmount(resultSet.getDouble("TOTAMT"));
		uLDChargingInvoiceVO.setNetAmount(resultSet.getDouble("NETAMT"));
		uLDChargingInvoiceVO.setPartyType(resultSet.getString("PTYTYP"));
		log.log(Log.FINE, "party type", uLDChargingInvoiceVO.getPartyType());
		log.exiting("ListULDInvoiceMapper","Map");
		return uLDChargingInvoiceVO;
}
}
