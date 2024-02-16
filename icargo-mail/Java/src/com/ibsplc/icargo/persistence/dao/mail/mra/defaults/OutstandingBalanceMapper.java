/* OutstandingBalanceMapper.java Created on Jan 20, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;


import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.OutstandingBalanceVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-3434
 *
 */
public class OutstandingBalanceMapper implements Mapper<OutstandingBalanceVO> {

	private Log log = LogFactory.getLogger("AirlineCN51DetailsMapper");

	private static final String CLASS_NAME = "AirlineCN51DetailsMapper";

	/**
	 * @return AirlineCN51DetailsVO
	 * @param rs
	 * @throws SQLException
	 */
	public OutstandingBalanceVO map(ResultSet rs) 
	throws SQLException {
		log.entering(CLASS_NAME, "map");
		OutstandingBalanceVO outstandingBalanceVO = new OutstandingBalanceVO();
		outstandingBalanceVO.setCurrencyCode(rs.getString("BASCUR"));
		try{
			Money totCredit=CurrencyHelper.getMoney(outstandingBalanceVO.getCurrencyCode());
			totCredit.setAmount(rs.getDouble("CRR"));
			outstandingBalanceVO.setCredit(totCredit);
			Money totDebit=CurrencyHelper.getMoney(outstandingBalanceVO.getCurrencyCode());
			totDebit.setAmount(rs.getDouble("DRR"));
			outstandingBalanceVO.setDebit(totDebit);
			
		}
		catch(CurrencyException currencyException){
			log.log(Log.INFO,"CurrencyException found");
		}
		
		outstandingBalanceVO.setAccountName(rs.getString("ACCNAM"));
		outstandingBalanceVO.setAccountString(rs.getString("ACCSTR"));
		
		log.exiting(CLASS_NAME, "map");
		return outstandingBalanceVO;
	}

}
