/**
 * OutstandingDetailsMapper.java Created on Sep 10, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * 
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SettlementDetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class OutstandingDetailsMapper implements Mapper<SettlementDetailsVO> {
	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	/**
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public SettlementDetailsVO map(ResultSet rs) throws SQLException {
		log.entering("MRADefaultsSqlDAO", "map");
		SettlementDetailsVO settlementDetailsVO = new SettlementDetailsVO();
		settlementDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		settlementDetailsVO.setGpaCode(rs.getString("GPACOD"));
		settlementDetailsVO.setSerialNumber(rs.getInt("SERNUM"));
		settlementDetailsVO.setSettlementId(rs.getString("STLREFNUM"));
		settlementDetailsVO.setSettlementSequenceNumber(rs.getInt("SEQNUM"));
		settlementDetailsVO.setChequeCurrency(rs.getString("STLCUR"));
		if(rs.getString("CHQISSDEL")!=null){
			settlementDetailsVO.setIsDeleted(rs.getString("CHQISSDEL"));	
		}

		try {
			if(rs.getString("STLCUR")!=null && rs.getString("STLCUR").trim().length()>0){
				Money outStandingAmt = CurrencyHelper.getMoney(rs.getString("STLCUR"));
				outStandingAmt.setAmount(rs.getDouble("OUTCHQAMT"));
				settlementDetailsVO.setOutStandingChqAmt(outStandingAmt);	
				settlementDetailsVO.setChequeAmount(outStandingAmt);
			} 
		}
		catch (CurrencyException e) {			
			log.log(Log.FINE,  "CurrencyException");
		}	
		return settlementDetailsVO;
	}
}

