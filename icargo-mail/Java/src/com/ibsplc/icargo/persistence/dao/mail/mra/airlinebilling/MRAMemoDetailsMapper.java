/*
 * MRAMemoDetailsMapper.java Created on Feb 22, 2007
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
 * @author a-2397
 *
 */
public class MRAMemoDetailsMapper implements Mapper<MemoInInvoiceVO>{

	/**
	 * @param resultSet
	 * @return MemoInInvoiceVO
	 * @exception SQLException
	 *
	 */
	
	public MemoInInvoiceVO map(ResultSet resultSet) throws SQLException {
		Log log = LogFactory.getLogger("MRA AIRLINEBILLING");
		log.entering("MRAMemoDetailsMapper", "map");
		MemoInInvoiceVO memoInInvoiceVO = new MemoInInvoiceVO();
		memoInInvoiceVO.setCompanyCode(resultSet.getString("CMPCOD"));
		memoInInvoiceVO.setMemoCode(resultSet.getString("MEMCOD"));
		memoInInvoiceVO.setAirlineIdentifier(resultSet.getInt("ARLIDR"));
		memoInInvoiceVO.setInvoiceNumber(resultSet.getString("INWINVNUM"));
		memoInInvoiceVO.setClearancePeriod(resultSet.getString("INWCLRPRD"));
		if(resultSet.getDate("MEMDAT") != null) {
			memoInInvoiceVO.setMemoDate(new LocalDate(LocalDate.NO_STATION,
                    Location.NONE, resultSet.getDate("MEMDAT")));
        }
		memoInInvoiceVO.setProvisionalAmount(resultSet.getDouble("PROVAMTLSTCUR"));  //Modified as part of ICRD_265471
		memoInInvoiceVO.setReportedAmount(resultSet.getDouble("RPDAMTLSTCUR"));  //Modified as part of ICRD_265471
		memoInInvoiceVO.setDifferenceAmount(resultSet.getDouble("BLGBLDAMT"));
		memoInInvoiceVO.setRemarks(resultSet.getString("RMK"));
		memoInInvoiceVO.setOperationalFlag("N");
		//Added By Deepthi as a part of capture invoice
		if(MemoInInvoiceVO.FLAG_YES.equals(resultSet.getString("PARVAL"))){
		memoInInvoiceVO.setDsn(resultSet.getString("DSN"));
		}else{
			memoInInvoiceVO.setDsn(resultSet.getString("BLGBAS"));
		}
		memoInInvoiceVO.setLastUpdatedUser(resultSet.getString("LSTUPDUSR"));
		if(resultSet.getDate("LSTUPDTIM") != null) {
			memoInInvoiceVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,
                    Location.NONE, resultSet.getTimestamp("LSTUPDTIM")));
        }
		log.exiting("MRAMemoDetailsMapper", "map");
		
		return memoInInvoiceVO;
	}
}

