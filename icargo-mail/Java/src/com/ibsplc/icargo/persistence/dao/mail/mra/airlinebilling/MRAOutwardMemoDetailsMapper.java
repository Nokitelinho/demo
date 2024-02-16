/*
 * MRAOutwardMemoDetailsMapper.java Created on Mar 16, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoInInvoiceVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2122
 *
 */
public class MRAOutwardMemoDetailsMapper implements Mapper<MemoInInvoiceVO>{

	/**
	 * @param resultSet
	 * @return MemoInInvoiceVO
	 * @exception SQLException
	 *
	 */
	
	public MemoInInvoiceVO map(ResultSet resultSet) throws SQLException {
		Log log = LogFactory.getLogger("MRA AIRLINEBILLING");
		log.entering("MRAOutwardMemoDetailsMapper", "map");
		MemoInInvoiceVO memoInInvoiceVO = new MemoInInvoiceVO();
		memoInInvoiceVO.setCompanyCode(resultSet.getString("CMPCOD"));
		memoInInvoiceVO.setMemoCode(resultSet.getString("MEMCOD"));
		memoInInvoiceVO.setAirlineIdentifier(resultSet.getInt("ARLIDR"));
		memoInInvoiceVO.setAirlineName(resultSet.getString("ARLNAM"));
		memoInInvoiceVO.setInvoiceNumber(resultSet.getString("INBINVNUM"));
		memoInInvoiceVO.setOutwardInvNumber(resultSet.getString("INVNUM"));
		memoInInvoiceVO.setClearancePeriod(resultSet.getString("CLRPRD"));
		memoInInvoiceVO.setInterlineBlgType(resultSet.getString("INTBLGTYP"));
		if(resultSet.getDate("MEMDAT") != null) {
			memoInInvoiceVO.setMemoDate(new LocalDate(LocalDate.NO_STATION,
                    Location.NONE, resultSet.getDate("MEMDAT")));
        }
		memoInInvoiceVO.setProvisionalAmount(resultSet.getDouble("PVNAMT"));
		memoInInvoiceVO.setReportedAmount(resultSet.getDouble("RPDAMT"));
		memoInInvoiceVO.setDifferenceAmount(resultSet.getDouble("DIFAMT"));
		memoInInvoiceVO.setContractCurrCode(resultSet.getString("CRTCURCOD"));
		memoInInvoiceVO.setPreviousDifferenceAmount(resultSet.getDouble("DIFAMT"));
		memoInInvoiceVO.setRemarks(resultSet.getString("RMK"));
		log.exiting("MRAOutwardMemoDetailsMapper", "map");
		
		return memoInInvoiceVO;
	}
}

