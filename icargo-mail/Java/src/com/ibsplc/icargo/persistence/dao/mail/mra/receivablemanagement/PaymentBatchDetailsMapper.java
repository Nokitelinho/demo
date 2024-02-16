/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement.PaymentBatchDetailsMapper.java
 *
 *	Created by	:	A-4809
 *	Created on	:	12-Nov-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchDetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement.PaymentBatchDetailsMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	12-Nov-2021	:	Draft
 */
public class PaymentBatchDetailsMapper implements Mapper<PaymentBatchDetailsVO>{
	
	private static final String CLASS_NAME = "PaymentBatchDetailsMapper";

	private static final Log LOG = LogFactory.getLogger("MRA:RCVMNG");

	/**
	 *	Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 *	Added by 			: A-4809 on 12-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SQLException 
	 */
	@Override
	public PaymentBatchDetailsVO map(ResultSet rs) throws SQLException {
		LOG.entering(CLASS_NAME, "map");
		PaymentBatchDetailsVO paymentBatchDetailsVO = new PaymentBatchDetailsVO();
		paymentBatchDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		paymentBatchDetailsVO.setBatchID(rs.getString("BTHSTLIDR"));
		paymentBatchDetailsVO.setPaCode(rs.getString("POACOD"));
		paymentBatchDetailsVO.setBatchSequenceNum(rs.getLong("BTHSTLSEQNUM"));   
		paymentBatchDetailsVO.setBatchDate(new LocalDate(
                LocalDate.NO_STATION, Location.NONE, rs.getDate("STLDAT")));
		paymentBatchDetailsVO.setBatchCurrency(rs.getString("STLCURCOD"));
		paymentBatchDetailsVO.setFileName(rs.getString("FILNAM"));
		paymentBatchDetailsVO.setBatchStatus(rs.getString("BTHSTA"));
		if(paymentBatchDetailsVO.getBatchCurrency()!=null){
			try{
		Money batchAmt =  CurrencyHelper.getMoney(paymentBatchDetailsVO.getBatchCurrency());
		Money appliedAmount =  CurrencyHelper.getMoney(paymentBatchDetailsVO.getBatchCurrency());
		Money unappliedAmount = CurrencyHelper.getMoney(paymentBatchDetailsVO.getBatchCurrency());
		batchAmt.setAmount(rs.getDouble("BTHTOTAMT"));
		appliedAmount.setAmount(rs.getDouble("APLAMT"));
		unappliedAmount.setAmount(rs.getDouble("UNNAPLAMT"));
		paymentBatchDetailsVO.setBatchAmount(batchAmt);
		paymentBatchDetailsVO.setAppliedAmount(appliedAmount);
		paymentBatchDetailsVO.setUnappliedAmount(unappliedAmount);
			}catch(CurrencyException currencyException){
				LOG.log(Log.INFO, currencyException);
			}
		}
		paymentBatchDetailsVO.setRemarks(rs.getString("RMK"));
		paymentBatchDetailsVO.setProcessID(rs.getString("PCRIDR"));
		paymentBatchDetailsVO.setRecordCount(rs.getInt("RECCNT"));
		paymentBatchDetailsVO.setSource(rs.getString("SRC"));
		paymentBatchDetailsVO.setLstUpdatedUser(rs.getString("LSTUPDUSR"));
		paymentBatchDetailsVO.setFileName(rs.getString("FILNAM"));
		if(rs.getTimestamp("LSTUPDTIM")!=null){
			paymentBatchDetailsVO.setLstUpdatedTime(new LocalDate(
                    LocalDate.NO_STATION, Location.NONE, rs.getTimestamp("LSTUPDTIM")));
        	}
		LOG.exiting(CLASS_NAME, "map");
		return paymentBatchDetailsVO;
	}

}
