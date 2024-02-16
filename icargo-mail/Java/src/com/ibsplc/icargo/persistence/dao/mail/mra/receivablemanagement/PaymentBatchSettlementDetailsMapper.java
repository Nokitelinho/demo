/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement.PaymentBatchSettlementDetailsMapper.java
 *
 *	Created by	:	A-3429
 *	Created on	:	18-Nov-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchSettlementDetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Java file :
 * com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement.PaymentBatchSettlementDetailsMapper.java
 * Version : Name : Date : Updation
 * --------------------------------------------------- 0.1 : A-3429 :
 * 18-Nov-2021 : Draft
 */
public class PaymentBatchSettlementDetailsMapper implements Mapper<PaymentBatchSettlementDetailsVO> {

	private static final String CLASS_NAME = "PaymentBatchDetailsMapper";

	private static final Log LOGGER  = LogFactory.getLogger("MRA:RCVMNG");

	/**
	 * Overriding Method : @see
	 * com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 * Added by : A-3429 on 18-Nov-2021 Used for : Parameters : @param arg0
	 * Parameters : @return Parameters : @throws SQLException
	 */
	@Override
	public PaymentBatchSettlementDetailsVO map(ResultSet rs) throws SQLException {
		LOGGER.entering(CLASS_NAME, "map");
		PaymentBatchSettlementDetailsVO paymentBatchSettlementDetailsVO = new PaymentBatchSettlementDetailsVO();
		paymentBatchSettlementDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		paymentBatchSettlementDetailsVO.setBatchID(rs.getString("BTHSTLIDR"));
		paymentBatchSettlementDetailsVO.setPaCode(rs.getString("POACOD"));
		paymentBatchSettlementDetailsVO.setBatchSequenceNum(rs.getLong("BTHSTLSEQNUM"));
		paymentBatchSettlementDetailsVO.setMailbagId(rs.getString("MALIDR"));
		paymentBatchSettlementDetailsVO.setMailSeqNum(rs.getLong("MALSEQNUM"));
		paymentBatchSettlementDetailsVO.setAccountNumber(rs.getString("ACCNUM"));
		paymentBatchSettlementDetailsVO.setInvoiceNumber(rs.getString("INVNUM"));
		paymentBatchSettlementDetailsVO
				.setPayDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getDate("STLDAT")));
		paymentBatchSettlementDetailsVO.setCurrencyCode(rs.getString("STLCURCOD"));
		if (paymentBatchSettlementDetailsVO.getCurrencyCode() != null) {
			try {
				Money batchAmt = CurrencyHelper.getMoney(paymentBatchSettlementDetailsVO.getCurrencyCode());
				Money appliedAmount = CurrencyHelper.getMoney(paymentBatchSettlementDetailsVO.getCurrencyCode());
				Money unappliedAmount = CurrencyHelper.getMoney(paymentBatchSettlementDetailsVO.getCurrencyCode());
				batchAmt.setAmount(rs.getDouble("BTHTOTAMT"));
				appliedAmount.setAmount(rs.getDouble("APLAMT"));
				unappliedAmount.setAmount(rs.getDouble("UNNAPLAMT"));
				paymentBatchSettlementDetailsVO.setBatchAmount(batchAmt);
				paymentBatchSettlementDetailsVO.setAppliedAmount(appliedAmount);
				paymentBatchSettlementDetailsVO.setUnappliedAmount(unappliedAmount);
			} catch (CurrencyException currencyException) {
				LOGGER.log(Log.INFO, currencyException);
			}
		}
		paymentBatchSettlementDetailsVO.setReasonCode(rs.getString("RSN"));
		paymentBatchSettlementDetailsVO.setRemarks(rs.getString("RMK"));

		LOGGER.exiting(CLASS_NAME, "map");
		return paymentBatchSettlementDetailsVO;
	}

}
