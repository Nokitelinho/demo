/*
 * AirlineAddressMapper.java Created on Dec 18,2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

/**
 * @author a-3447
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3447
 * 
 */
public class AirlineAddressMapper implements Mapper<AirlineVO> {
	private Log log = LogFactory.getLogger("MRA GPABILLING");

	/**
	 * @author a-3447
	 * @param resultSet
	 * @throws SQLException
	 * @return billingSummaryDetailsVO
	 */
	public AirlineVO map(ResultSet resultSet) throws SQLException {
		log.entering("BillingSummaryDetailsMapper", "map");
		AirlineVO airlineVO = new AirlineVO();
		if (resultSet.getString("ARLNAM") != null) {
			airlineVO.setAirlineName(resultSet.getString("ARLNAM"));
		}
		if (resultSet.getString("BLGADR") != null) {
			airlineVO.setBillingAddress(resultSet.getString("BLGADR"));
		}

		if (resultSet.getString("BLGPHNONE") != null) {
			airlineVO.setBillingPhone1(resultSet.getString("BLGPHNONE"));
		}

		if (resultSet.getString("BLGPHNTWO") != null) {
			airlineVO.setBillingPhone2(resultSet.getString("BLGPHNTWO"));
		}

		if (resultSet.getString("BLGFAX") != null) {
			airlineVO.setBillingFax(resultSet.getString("BLGFAX"));
		}

		log.log(Log.INFO, "vos returned ", airlineVO);
		log.exiting("BillingSummaryDetailsMapper", "map");
		return airlineVO;
	}

}
